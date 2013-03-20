package com.github.dirkraft.jerseyboot.app.scan;

import java.util.List;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public interface ScannerHelper {

    /**
     * @param superclass target to match resources against
     * @param pkgs to restrict search to, if any
     * @param <T> generic type param of 'superclass'
     * @return list of resources/providers that inherit the given 'superclass'
     */
    public <T> List<T> findImplementing(Class<T> superclass, String... pkgs);
}
