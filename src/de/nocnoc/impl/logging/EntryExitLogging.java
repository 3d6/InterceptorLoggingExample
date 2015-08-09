package de.nocnoc.impl.logging;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * EntryExitLogging automaticly logs entering and exiting a method.
 * If you want to use this annotation, your class hass to implement
 * the <code>HasLogger</code> interface to provide its own logger.
 */

@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface EntryExitLogging {

    @Nonbinding LogLevel value() default LogLevel.FINER;

    @Nonbinding boolean resolveArray() default true;

    @Nonbinding String entryIndicator() default ">>";

    @Nonbinding String exitIndicator() default "<<";
}
