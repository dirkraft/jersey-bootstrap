package com.github.dirkraft.jerseyboot.app.scan;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.core.spi.component.ProviderServices;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

/**
 * Utility to scan jersey-registered resources for implementors of some arbitrary class
 * (see {@link #findImplementing(Class, String...)}). Jersey sort of keeps a "bean registry" like Spring
 * does, but not really. This is a pretty rough search, but I think it works given all the constructor args.
 *
 * @author jason
 */
@Provider
public class JerseyScannerHelper implements ScannerHelper {

    private final ResourceConfig resourceConfig;
    private final ResourceContext resourceContext;
    private final ProviderServices providerServices;

    public JerseyScannerHelper(@Context ResourceConfig resourceConfig,
                               @Context ResourceContext resourceContext,
                               @Context ProviderServices providerServices) {
        this.resourceConfig = resourceConfig;
        this.resourceContext = resourceContext;
        this.providerServices = providerServices;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findImplementing(Class<T> superclass, String... pkgs) {
        String pkgsPattern = pkgs == null || pkgs.length == 0 ? ".*" : format("^(%s)\\..*", StringUtils.join(pkgs, "|"));

        List<T> matchingSingletons = new ArrayList<>();
        for (Class<?> klass : getMergedResourceProviderClasses(pkgsPattern)) {
            if (klass.getPackage().getName().matches(pkgsPattern)) {
                if (superclass.isAssignableFrom(klass)) {
                    Object singleton = resourceContext.getResource(klass);
                    matchingSingletons.add((T) singleton);
                }
            }
        }
        return matchingSingletons;
    }

    /**
     * @return resource and provider classes matching the packages pattern
     */
    private Collection<Class<?>> getMergedResourceProviderClasses(final String packagesPattern) {
        return Collections2.filter(Sets.union(resourceConfig.getRootResourceClasses(), resourceConfig.getProviderClasses()),
                new Predicate<Class<?>>() {
                    @Override
                    public boolean apply(Class<?> inputClass) {
                        return inputClass.getPackage().getName().matches(packagesPattern);
                    }
                }
        );
    }

}
