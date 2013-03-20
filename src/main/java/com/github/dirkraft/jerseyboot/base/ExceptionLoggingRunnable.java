package com.github.dirkraft.jerseyboot.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import java.lang.Exception;import java.lang.Override;import java.lang.Runnable;import java.lang.Throwable;

/**
 * @author jason
 */
public abstract class ExceptionLoggingRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public final void run() {
        try {
            go();
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }

    protected abstract void go() throws Exception;
}
