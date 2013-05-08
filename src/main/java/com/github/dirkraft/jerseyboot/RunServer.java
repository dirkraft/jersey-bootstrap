package com.github.dirkraft.jerseyboot;

import com.github.dirkraft.jerseyboot.app.StaticResourceUTF8CharEncodingFilterHolder;
import com.github.dirkraft.jerseyboot.base.BaseConfig;
import com.github.dirkraft.jerseyboot.base.BasePackagesResourceConfig;
import com.github.dirkraft.propslive.core.LivePropSet;
import com.github.dirkraft.propslive.dynamic.listen.PropChange;
import com.github.dirkraft.propslive.set.ease.PropsSlice;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.server.impl.resource.SingletonFactory;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.github.dirkraft.jerseyboot.base.BaseConfig.$;
import static com.github.dirkraft.jerseyboot.base.BaseConfig.DEF_JETTY_PORT;
import static com.github.dirkraft.jerseyboot.base.BaseConfig.PROP_JETTY_PORT;
import static com.github.dirkraft.jerseyboot.base.BaseConfig.PROP_RESTART_TRIGGER;
import static com.github.dirkraft.jerseyboot.base.BaseConfig.PROP_STATIC_DIRS;

/**
 * Sets up Jetty and Jersey with some sane defaults
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class RunServer {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public static Server SERVER;

    /**
     * Through the 'props-live' library, atomically receives updates to all properties in the set:
     * <ul>
     *     <li>{@link BaseConfig#PROP_STATIC_DIRS}</li>
     *     <li>{@link BaseConfig#PROP_JETTY_PORT}</li>
     *     <li>{@link BaseConfig#PROP_RESTART_TRIGGER}</li>
     * </ul>
     *
     * When a change is made to any of these properties, which directly affect the running Jetty instance, Jetty is
     * restarted with the new settings.
     */
    private final LivePropSet propSet = new LivePropSet(PROP_STATIC_DIRS, PROP_JETTY_PORT, PROP_RESTART_TRIGGER) {
        {
            // Init with current values and subscribe to changes.
            $.to(this).getVals(this);
        }

        /** Reload cannot happen in the same thread as one serving a request. */
        final ExecutorService jettyStopForRestartExecutor = new ThreadPoolExecutor(0, 1, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        @Override
        public void reload(PropChange<PropsSlice> values) {
            jettyStopForRestartExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // This will automatically recover and restart, which will look up fresh config.
                        // accomplished by run() { while (!Thread.interrupted()) ...
                        SERVER.stop();
                    } catch (Exception e) {
                        LOG.error("Critical error: Exception restarting the server.", e);
                    }
                }
            });
        }
    };

    private final Class<? extends BasePackagesResourceConfig> prcCls;

    public RunServer() {
        this(BasePackagesResourceConfig.class);
    }

    /**
     * Initializes a new Jetty instance running the specified Jersey application class.
     */
    public RunServer(Class<? extends BasePackagesResourceConfig> prcCls) {
        this.prcCls = prcCls;
        try {
            run();
        } catch (Exception e) {
            // This would indicate the restart loop failed.
            LOG.error("Failed to restart jersey", e);
        }
    }

    public void run() throws Exception {
        while (!Thread.interrupted()) { // probably indicates shutdown

            WebAppContext webApp = new WebAppContext();
            // Effectively removes the "no-JSP support" warning, because we don't want dirty, stinkin' JSP support ever.
            webApp.setDefaultsDescriptor("webdefault-nojsp.xml");

            // Fixes serving static resources correctly. Without this, no charset is set in the Content-Type header and
            // 'good' browsers do a terrible job at guessing.
            webApp.addFilter(new StaticResourceUTF8CharEncodingFilterHolder(), "/*", EnumSet.allOf(DispatcherType.class));

            FilterHolder jerseyFilter = new FilterHolder(ServletContainer.class);
            jerseyFilter.setName(prcCls.getName());
            jerseyFilter.setInitParameter(ServletContainer.APPLICATION_CONFIG_CLASS, prcCls.getCanonicalName());
            jerseyFilter.setInitParameter(ServletContainer.PROPERTY_WEB_PAGE_CONTENT_REGEX, ".*\\.(html|xml|css|js|gif|jpg|png|ico|eot|svg|ttf|woff|otf)");
            jerseyFilter.setInitParameter(ResourceConfig.PROPERTY_DEFAULT_RESOURCE_COMPONENT_PROVIDER_FACTORY_CLASS, SingletonFactory.class.getCanonicalName());
            jerseyFilter.setInitParameter(ResourceConfig.FEATURE_DISABLE_WADL, "true");
            jerseyFilter.setInitParameter(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
            webApp.addFilter(jerseyFilter, "/*", EnumSet.allOf(DispatcherType.class));

            final Resource staticResources;
            String explicitStaticDirs = propSet.getString(PROP_STATIC_DIRS);
            if (explicitStaticDirs == null) {
                // Single static resource directory in the classpath:static/ folder.
                //noinspection ConstantConditions
                staticResources = Resource.newResource(prcCls.getClassLoader().getResource("static").toExternalForm());
            } else {
                // Explicitly named static resource directories. There may be multiple. First match wins.
                staticResources = new ResourceCollection(explicitStaticDirs.split(";"));
            }
            webApp.setBaseResource(staticResources);

            SERVER = new Server(propSet.getInt(PROP_JETTY_PORT));
            SERVER.setHandler(webApp);
            SERVER.start();
            SERVER.join(); // blocks

        }
    }

    public static void main(String[] args) throws Exception {
        new RunServer().run();
    }
}