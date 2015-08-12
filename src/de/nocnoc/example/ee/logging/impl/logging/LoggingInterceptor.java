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

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TraceLogging Interceptor for project wide easy method entry and exit logging
 */
@Interceptor
@TraceLogging
@Priority(Interceptor.Priority.APPLICATION)
@Dependent
public class LoggingInterceptor {

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {

        // Logging is only available on classes implementing HasLogger
        if (!(context.getTarget() instanceof HasLogger)) {
            throw new IllegalArgumentException("Logging is only available on classes implementing '" + HasLogger.class.getCanonicalName() + "'!");
        }

        // don't log anything if the invocation context is not the method, but the class
        Method method = context.getMethod();
        if (method == null) {
            return context.proceed();
        }

        // exception: don't log getting the logger
        if (method.equals(HasLogger.class.getMethod("getLogger"))) {
            return context.proceed();
        }

        TraceLogging annotation;

        if (method.isAnnotationPresent(TraceLogging.class)) {
            // get the logging level from the methods annotation
            annotation = method.getAnnotation(TraceLogging.class);
        } else if (context.getTarget().getClass().isAnnotationPresent(TraceLogging.class)) {
            // if the annotation is not at the method but the class, get it from there
            annotation = context.getTarget().getClass().getAnnotation(TraceLogging.class);
        } else {
            // if there is no annotation there is something wrong
            throw new IllegalArgumentException("No valid context found!");
        }

        // get the logger from the calling class
        Logger logger = ((HasLogger) context.getTarget()).getLogger();


        Level level = annotation.value().getLevel();

        boolean isLoggable = logger.isLoggable(level);

        boolean resolveArray = annotation.resolveArray();

        String parameter = "";

        if (isLoggable) {
            // building the formal parameter list
            parameter = buildParameterList(method.getParameters(), context.getParameters().length);

            // convert arrays parameters to strings to get them shown
            resolveArray = annotation.resolveArray();
            final Object[] actualInputParameters = convertArrayParams(context.getParameters(), resolveArray);

            String entryIndicator = annotation.entryIndicator();

            // log the entry message
            logger.log(level, entryIndicator + " " + method.getName() + "(" + String.format(parameter, actualInputParameters) + ")");
        }

        // do the methods actions
        Object result = context.proceed();

        if (isLoggable) {

            String returnLog = prepareReturnLog(method, result);

            // log the exit message. the actual parameter are retrieved again, to show changes on non final paramters
            final Object[] actualOutputParameters = convertArrayParams(context.getParameters(), resolveArray);

            String exitIndicator = annotation.exitIndicator();
            logger.log(level, exitIndicator + " " + method.getName() + "(" + String.format(parameter, actualOutputParameters) + ")" + returnLog);
        }

        return result;
    }

    /**
     * Assambles the formal parameters to a placeholders string. so the actual parameter can be changed afterwards.
     * The form of the string is : "Type name = \%s"
     *
     * @param parameters       the formal parameter
     * @param actualParamCount the number of actual parameters. must be equal to the size of formal parameters
     * @return A string which contains all the formal parameters and types and placeholders.
     */
    private String buildParameterList(final Parameter[] parameters, final int actualParamCount) {

        if (parameters == null || parameters.length < 1 || parameters.length != actualParamCount) {
            return " ";
        }

        StringBuilder paramLog = new StringBuilder();

        for (int i = 0; i < parameters.length; i++) {

            Parameter param = parameters[i];
            paramLog.append(param.getType().getSimpleName());
            paramLog.append(" ");
            paramLog.append(param.getName());
            paramLog.append(" = '%s'");

            if (i < parameters.length - 1) {
                paramLog.append("; ");
            }
        }

        return paramLog.length() > 0 ? paramLog.toString() : " ";
    }

    /**
     * If a param is an array, the <code>toString()</code> method will result
     * in the
     *
     * @param params method actual parameters
     * @return method parameters where arrays are converted to strings in the first depth
     */
    private Object[] convertArrayParams(final Object[] params, boolean resolveArray) {

        if (!resolveArray) {
            return params;
        }

        // check if at least one of the params needs to be converted
        boolean converting = false;
        for (Object param : params) {
            if (param != null && param.getClass() != null && param.getClass().isArray()) {
                converting = true;
                break;
            }
        }

        // if converting is not necessary return the original params to speed up
        if (!converting) {
            return params;
        }

        // else, if converting is necessary create a new params array with on-depth-strings
        Object[] result = new Object[params.length];

        for (int i = 0; i < params.length; i++) {
            final Object param = params[i];
            if (safeIsArray(param)) {
                result[i] = Arrays.toString((Object[]) param);
            } else {
                result[i] = param;
            }
        }

        return result;
    }

    private boolean safeIsArray(Object param) {
        return param != null && param.getClass() != null && param.getClass().isArray();
    }


    private String prepareReturnLog(Method method, Object returnValue) {

        Class returnType = method.getReturnType();

        if (void.class.equals(returnType)) {
            return "";
        }

        return " : (" + returnType.getSimpleName() + ") " + returnValue;
    }
}
