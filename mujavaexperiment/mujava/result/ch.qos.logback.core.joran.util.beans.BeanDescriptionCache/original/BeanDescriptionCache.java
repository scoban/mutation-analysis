// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.util.beans;


import java.util.HashMap;
import java.util.Map;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;


public class BeanDescriptionCache extends ch.qos.logback.core.spi.ContextAwareBase
{

    private java.util.Map<Class<?>,BeanDescription> classToBeanDescription = new java.util.HashMap<Class<?>,BeanDescription>();

    private ch.qos.logback.core.joran.util.beans.BeanDescriptionFactory beanDescriptionFactory;

    public BeanDescriptionCache( ch.qos.logback.core.Context context )
    {
        setContext( context );
    }

    private  ch.qos.logback.core.joran.util.beans.BeanDescriptionFactory getBeanDescriptionFactory()
    {
        if (beanDescriptionFactory == null) {
            beanDescriptionFactory = new ch.qos.logback.core.joran.util.beans.BeanDescriptionFactory( getContext() );
        }
        return beanDescriptionFactory;
    }

    public  ch.qos.logback.core.joran.util.beans.BeanDescription getBeanDescription( java.lang.Class<?> clazz )
    {
        if (!classToBeanDescription.containsKey( clazz )) {
            ch.qos.logback.core.joran.util.beans.BeanDescription beanDescription = getBeanDescriptionFactory().create( clazz );
            classToBeanDescription.put( clazz, beanDescription );
        }
        return classToBeanDescription.get( clazz );
    }

}
