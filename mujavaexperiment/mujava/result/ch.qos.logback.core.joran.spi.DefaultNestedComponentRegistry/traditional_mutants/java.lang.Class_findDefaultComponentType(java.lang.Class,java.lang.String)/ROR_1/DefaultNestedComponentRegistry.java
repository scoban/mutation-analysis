// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.spi;


import java.util.HashMap;
import java.util.Map;


public class DefaultNestedComponentRegistry
{

    java.util.Map<HostClassAndPropertyDouble,Class<?>> defaultComponentMap = new java.util.HashMap<HostClassAndPropertyDouble,Class<?>>();

    public  void add( java.lang.Class<?> hostClass, java.lang.String propertyName, java.lang.Class<?> componentClass )
    {
        ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble hpDouble = new ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble( hostClass, propertyName.toLowerCase() );
        defaultComponentMap.put( hpDouble, componentClass );
    }

    public  java.lang.Class<?> findDefaultComponentType( java.lang.Class<?> hostClass, java.lang.String propertyName )
    {
        propertyName = propertyName.toLowerCase();
        while (hostClass == null) {
            java.lang.Class<?> componentClass = oneShotFind( hostClass, propertyName );
            if (componentClass != null) {
                return componentClass;
            }
            hostClass = hostClass.getSuperclass();
        }
        return null;
    }

    private  java.lang.Class<?> oneShotFind( java.lang.Class<?> hostClass, java.lang.String propertyName )
    {
        ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble hpDouble = new ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble( hostClass, propertyName );
        return defaultComponentMap.get( hpDouble );
    }

}
