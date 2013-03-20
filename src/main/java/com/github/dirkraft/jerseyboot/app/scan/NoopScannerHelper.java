package com.github.dirkraft.jerseyboot.app.scan;

import java.util.Collections;
import java.util.List;

/**
 * @author Jason Dunkelberger (dirkraft)
 */
public class NoopScannerHelper extends JerseyScannerHelper {
    public NoopScannerHelper() {
        super(null, null, null);
    }

    @Override
    public <T> List<T> findImplementing(Class<T> superclass, String... pkgs) {
        return Collections.emptyList();
    }
}
