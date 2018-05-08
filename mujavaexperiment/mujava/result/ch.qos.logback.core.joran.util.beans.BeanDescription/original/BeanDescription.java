// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.util.beans;


import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;


public class BeanDescription
{

    private final java.lang.Class<?> clazz;

    private final java.util.Map<String,Method> propertyNameToGetter;

    private final java.util.Map<String,Method> propertyNameToSetter;

    private final java.util.Map<String,Method> propertyNameToAdder;

    protected BeanDescription( java.lang.Class<?> clazz, java.util.Map<String,Method> propertyNameToGetter, java.util.Map<String,Method> propertyNameToSetter, java.util.Map<String,Method> propertyNameToAdder )
    {
        this.clazz = clazz;
        this.propertyNameToGetter = Collections.unmodifiableMap( propertyNameToGetter );
        this.propertyNameToSetter = Collections.unmodifiableMap( propertyNameToSetter );
        this.propertyNameToAdder = Collections.unmodifiableMap( propertyNameToAdder );
    }

    public  java.lang.Class<?> getClazz()
    {
        return clazz;
    }

    public  java.util.Map<String,Method> getPropertyNameToGetter()
    {
        return propertyNameToGetter;
    }

    public  java.util.Map<String,Method> getPropertyNameToSetter()
    {
        return propertyNameToSetter;
    }

    public  java.lang.reflect.Method getGetter( java.lang.String propertyName )
    {
        return propertyNameToGetter.get( propertyName );
    }

    public  java.lang.reflect.Method getSetter( java.lang.String propertyName )
    {
        return propertyNameToSetter.get( propertyName );
    }

    public  java.util.Map<String,Method> getPropertyNameToAdder()
    {
        return propertyNameToAdder;
    }

    public  java.lang.reflect.Method getAdder( java.lang.String propertyName )
    {
        return propertyNameToAdder.get( propertyName );
    }

}
