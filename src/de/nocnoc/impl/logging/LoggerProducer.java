package de.nocnoc.impl.logging;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger Produces
 */
public class LoggerProducer {

    /**
     * Returns the log handlers
     *
     * @param logger the logger, which handlers you want to get
     * @return the loggers handlers
     */
    private static Handler[] getHandlers(final Logger logger) {

        if (logger == null) {
            return null;
        }

        Logger upLogger = logger;

        // goes up the parents until there is no parent or there are the handlers found
        while (upLogger.getParent() != null && upLogger.getHandlers().length <= 0) {
            upLogger = upLogger.getParent();
        }

        return upLogger.getHandlers();
    }

    /**
     * Setup the loggers handler to show logs
     *
     * @param handlers the handlers will be set up
     * @param level    the level which they will log
     */
    private static void setupLoggingHandlerLevel(Handler[] handlers, Level level) {
        if (handlers == null || level == null) {
            return;
        }

        for (Handler handler : handlers) {
            handler.setLevel(level);
        }
    }

    @Produces
    @EntryExitLogger
    public Logger produceEntryExitMethodLogger(InjectionPoint injectionPoint) {
        Logger logger = Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getCanonicalName());

        // get the log level for the logger
        LogLevel loggerLevel = injectionPoint.getAnnotated().getAnnotation(EntryExitLogger.class).value();

        // get the log level for the loggers handler
        LogLevel handlerLevel = injectionPoint.getAnnotated().getAnnotation(EntryExitLogger.class).handlerLogLevel();

        if (!LogLevel.KEEP.equals(handlerLevel) && logger.getUseParentHandlers()) {
            Handler[] handlers = getHandlers(logger);
            setupLoggingHandlerLevel(handlers, handlerLevel.getLevel());
        }

        if (!LogLevel.KEEP.equals(loggerLevel)) {
            logger.setLevel(loggerLevel.getLevel());
        }

        return logger;
    }
}
