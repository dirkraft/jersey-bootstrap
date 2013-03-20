package com.github.dirkraft.jerseyboot.base;

import com.github.dirkraft.propslive.dynamic.DynamicPropsSets;
import org.eclipse.jetty.util.resource.ResourceCollection;

/**
 * @author jason
 */
public class JJConfig {

    public static final DynamicPropsSets $ = new DynamicPropsSets();

    /** Same as jetty property to use a custom port. Read by {@link BasePackagesResourceConfig} when starting embedded Jetty. */
    public static final String PROP_JETTY_PORT = "jetty.port";

    public static final int DEF_JETTY_PORT = 8080;

    /**
     * Development use: Set this property to look for static files in the specified locations, separated by ';'.
     * Normally this means pointing jetty directly at the source static files in the base-webapp and in the concrete
     * webapp. e.g. a relative paths config for embedded Jetty runs of Primordia and Energia might look like this, where
     * the first path is the concrete app followed by the base app. The concrete comes first so that any like-named
     * static files will shadow any in the base-webapp.
     *
     * <pre>
     *     src/main/webapp/static/;../base-webapp/src/main/webapp/static/
     * </pre>
     *
     * <p/>
     *
     * The Gradle build will actually merge static resources from base-webapp into the concrete webapp's
     * static files in the Fat Jar. So this property should not be set at runtime. But for development (e.g. in IntelliJ)
     * we need a Jetty {@link ResourceCollection} that virtually merges at least two directories, the concrete app's
     * static files on top of the base-webapp's static files.
     *
     * <hr/>
     *
     * Gradle note: This property was chosen over an alternate approach, this build.gradle closure:
     * <pre>
     *     idea {
     *         module {
     *             sourceDirs += file('../base-webapp/src/main/webapp')
     *         }
     *     }
     * </pre>
     * This approach unfortunately has some negative effects on IntelliJ functionality, particularly in the
     * Project Structure dialogs.
     */
    public static final String PROP_STATIC_DIRS = "base_app.static_dirs";

}
