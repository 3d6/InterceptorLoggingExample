package de.nocnoc.example.ee.logging.impl.controller;

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

import de.nocnoc.example.ee.logging.impl.logging.HasLogger;
import de.nocnoc.example.ee.logging.impl.logging.TraceLogger;
import de.nocnoc.example.ee.logging.impl.logging.TraceLogging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Logger;

import static de.nocnoc.example.ee.logging.impl.logging.LogLevel.FINER;

/**
 * Managing String Formats for Dates
 */
@ApplicationScoped
@TraceLogging(FINER)
public class CopyDateMessageUtils implements HasLogger {

    @Inject
    @TraceLogger(value = FINER, handlerLogLevel = FINER)
    private transient Logger logger;


    public String getSimpleDateMessage(Date date) {

        date.setTime(date.getTime() + 12345);

        ZonedDateTime zdt = date.toInstant().atZone(ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String result = formatter.format(zdt);

        noReturn(result);

        return result;
    }

    @TraceLogging(FINER)
    public void noReturn(Object object) {

    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

}
