package de.nocnoc.impl.logging;

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
