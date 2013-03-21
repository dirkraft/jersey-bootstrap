package com.github.dirkraft.jerseyboot.base;

import com.github.dirkraft.jerseyboot.RunServer;
import com.github.dirkraft.jerseyboot.app.StartupListener;
import com.github.dirkraft.jerseyboot.app.scan.JerseyScannerHelper;
import com.github.dirkraft.jerseyboot.app.scan.ScannerHelper;
import com.github.dirkraft.propslive.set.ease.PropSetAsMap;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.api.core.PackagesResourceConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;

import javax.ws.rs.ApplicationPath;

import static com.github.dirkraft.jerseyboot.base.BaseConfig.$;
import static com.github.dirkraft.jerseyboot.base.BaseConfig.DEFAULTS;

/**
 * @author jason
 */
@ApplicationPath("/*")
public class BasePackagesResourceConfig extends PackagesResourceConfig {

    public BasePackagesResourceConfig(@InjectParam JerseyScannerHelper scannerHelper) {
        this(scannerHelper, (String[]) null);
    }

    public BasePackagesResourceConfig(final ScannerHelper scannerHelper, String... basePkgs) {
        super(combine(new String[]{
                "org.codehaus.jackson.jaxrs", // json serialization
                BaseConst.BASE_PKG // base components
        }, basePkgs));
        // Support for initialization after context building but before serving requests.
        RunServer.SERVER.getHandler().addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
            @Override
            public void lifeCycleStarted(LifeCycle event) {
                for (StartupListener startupListener : scannerHelper.findImplementing(StartupListener.class)) {
                    startupListener.onStartup();
                }
            }
        });
        init();
    }

    /**
     * Last thing called by any constructor in this class.
     */
    protected void init() {
        ToStringBuilder.setDefaultStyle(BaseConst.DEFAULT_TO_STRING_STYLE);
    }

    protected static String[] combine(String pkg, String... moar) {
        return combine(new String[]{pkg}, moar);
    }

    protected static String[] combine(String[] pkgs, String... moar) {
        if (pkgs == null || moar == null) {
            return pkgs != null ? pkgs : (moar != null ? moar : null);
        }
        String[] combined = new String[pkgs.length + moar.length];
        System.arraycopy(pkgs, 0, combined, 0, pkgs.length);
        System.arraycopy(moar, 0, combined, pkgs.length, moar.length);
        return combined;
    }
}

