package de.nocnoc.impl.logging;

import java.util.logging.Level;

/**
 * The level with which the message will be logged.
 * <code>LogLevel</code> is an <code>enum</code> representation of <code>Level</code>
 */
public enum LogLevel {
    ALL(Level.ALL),
    OFF(Level.OFF),
    CONFIG(Level.CONFIG),
    WARNING(Level.WARNING),
    INFO(Level.INFO),
    FINE(Level.FINE),
    FINER(Level.FINER),
    FINEST(Level.FINEST),
    SEVERE(Level.SEVERE),

    /**
     * this is not to be used directly, but to show, that the
     * loglevel of the loggers handlers should not be changed
     */
    KEEP(Level.INFO);

    private final Level level;

    LogLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }
}
