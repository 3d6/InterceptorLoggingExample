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
