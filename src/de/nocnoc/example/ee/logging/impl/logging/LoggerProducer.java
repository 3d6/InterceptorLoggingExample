package de.nocnoc.example.ee.logging.impl.logging;

/*
 This file is part of InterceptorLoggingExample.

 InterceptorLoggingExample is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 InterceptorLoggingExample is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

 Diese Datei ist Teil von InterceptorLoggingExample.

 InterceptorLoggingExample ist Freie Software: Sie koennen sie unter den Bedingungen
 der GNU Lesser General Public License, wie von der Free Software Foundation,
 Version 3 der Lizenz oder (nach Ihrer Wahl) jeder spaeteren
 veroeffentlichten Version, weiterverbreiten und/oder modifizieren.

 InterceptorLoggingExample wird in der Hoffnung, dass es nuetzlich sein wird, aber
 OHNE JEDE GEWAEHRLEISTUNG, bereitgestellt; sogar ohne die implizite
 Gewaehrleistung der MARKTFAEHIGKEIT oder EIGNUNG FUER EINEN BESTIMMTEN ZWECK.
 Siehe die GNU Lesser General Public License fuer weitere Details.

 Sie sollten eine Kopie der GNU Lesser General Public License zusammen mit diesem
 Programm erhalten haben. Wenn nicht, siehe <http://www.gnu.org/licenses/>.
 */

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
