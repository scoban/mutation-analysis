// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.util.beans;


import java.lang.reflect.Method;


public class BeanUtil
{

    public static final java.lang.String PREFIX_GETTER_IS = "is";

    public static final java.lang.String PREFIX_GETTER_GET = "get";

    public static final java.lang.String PREFIX_SETTER = "set";

    public static final java.lang.String PREFIX_ADDER = "add";

    public static  boolean isAdder( java.lang.reflect.Method method )
    {
        int parameterCount = getParameterCount( method );
        if (parameterCount < 1) {
            return false;
        }
        java.lang.Class<?> returnType = method.getReturnType();
        if (returnType != void.class) {
            return false;
        }
        java.lang.String methodName = method.getName();
        return methodName.startsWith( PREFIX_ADDER );
    }

    public static  boolean isGetter( java.lang.reflect.Method method )
    {
        int parameterCount = getParameterCount( method );
        if (parameterCount > 0) {
            return false;
        }
        java.lang.Class<?> returnType = method.getReturnType();
        if (returnType == void.class) {
            return false;
        }
        java.lang.String methodName = method.getName();
        if (!methodName.startsWith( PREFIX_GETTER_GET ) && !methodName.startsWith( PREFIX_GETTER_IS )) {
            return false;
        }
        if (methodName.startsWith( PREFIX_GETTER_IS )) {
            if (!returnType.equals( boolean.class ) && !returnType.equals( java.lang.Boolean.class )) {
                return false;
            }
        }
        return true;
    }

    private static  int getParameterCount( java.lang.reflect.Method method )
    {
        return method.getParameterTypes().length;
    }

    public static  boolean isSetter( java.lang.reflect.Method method )
    {
        int parameterCount = getParameterCount( method );
        if (parameterCount != 1) {
            return false;
        }
        java.lang.Class<?> returnType = method.getReturnType();
        if (returnType != void.class) {
            return false;
        }
        java.lang.String methodName = method.getName();
        if (!methodName.startsWith( PREFIX_SETTER )) {
            return false;
        }
        return true;
    }

    public static  java.lang.String getPropertyName( java.lang.reflect.Method method )
    {
        java.lang.String methodName = method.getName();
        java.lang.String rawPropertyName = getSubstringIfPrefixMatches( methodName, PREFIX_GETTER_GET );
        if (rawPropertyName == null) {
            rawPropertyName = getSubstringIfPrefixMatches( methodName, PREFIX_SETTER );
        }
        if (rawPropertyName == null) {
            rawPropertyName = getSubstringIfPrefixMatches( methodName, PREFIX_GETTER_IS );
        }
        if (rawPropertyName == null) {
            rawPropertyName = getSubstringIfPrefixMatches( methodName, PREFIX_ADDER );
        }
        return toLowerCamelCase( rawPropertyName );
    }

    public static  java.lang.String toLowerCamelCase( java.lang.String string )
    {
        if (string == null) {
            return null;
        }
        if (string.isEmpty()) {
            return string;
        }
        if (string.length() > 1 && Character.isUpperCase( string.charAt( 1 ) ) && Character.isUpperCase( string.charAt( 0 ) )) {
            return string;
        }
        char[] chars = string.toCharArray();
        chars[0] = Character.toLowerCase( chars[0] );
        return new java.lang.String( chars );
    }

    private static  java.lang.String getSubstringIfPrefixMatches( java.lang.String wholeString, java.lang.String prefix )
    {
        if (wholeString.startsWith( prefix )) {
            return wholeString.substring( prefix.length() );
        }
        return null;
    }

}
