package de.nocnoc.impl.controller;

import de.nocnoc.impl.logging.EntryExitLogger;
import de.nocnoc.impl.logging.EntryExitLogging;
import de.nocnoc.impl.logging.HasLogger;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Logger;

import static de.nocnoc.impl.logging.LogLevel.FINER;
import static de.nocnoc.impl.logging.LogLevel.WARNING;

/**
 * Managing String Formats for Dates
 */
@ApplicationScoped
public class DateMessageUtils implements HasLogger {

    @Inject
    @EntryExitLogger(value = FINER, handlerLogLevel = FINER)
    private transient Logger logger;

    @EntryExitLogging(FINER)
    public String getSimpleDateMessage(Date date) {

        date.setTime(date.getTime() + 12345);

        ZonedDateTime zdt = date.toInstant().atZone(ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return formatter.format(zdt);
    }

    @EntryExitLogging(WARNING)
    public void noReturn(Object object) {

    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

}
