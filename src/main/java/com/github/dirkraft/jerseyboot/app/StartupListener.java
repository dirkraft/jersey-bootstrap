package com.github.dirkraft.jerseyboot.app;

/**
* @author jason
*/
public interface StartupListener {
    /**
     * Initialization that should occur after singleton instantiation.
     */
    void onStartup();
}
