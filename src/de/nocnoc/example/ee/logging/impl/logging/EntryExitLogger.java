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

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This  qualifier is used to get an entry/exit method logger.
 * This logger is primarily used to simplify basic method logging.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface EntryExitLogger {

    /**
     * Log level of the logger
     *
     * @return the logLevel (as enum)
     */
    @Nonbinding LogLevel value() default LogLevel.FINER;

    /**
     * Log level of the loggers handler
     *
     * @return the logLevel (as enum)
     */
    @Nonbinding LogLevel handlerLogLevel() default LogLevel.KEEP;
}
