// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.conditional;


import ch.qos.logback.core.spi.PropertyContainer;
import ch.qos.logback.core.util.OptionHelper;


public class PropertyWrapperForScripts
{

    ch.qos.logback.core.spi.PropertyContainer local;

    ch.qos.logback.core.spi.PropertyContainer context;

    public  void setPropertyContainers( ch.qos.logback.core.spi.PropertyContainer local, ch.qos.logback.core.spi.PropertyContainer context )
    {
        this.local = local;
        this.context = context;
    }

    public  boolean isNull( java.lang.String k )
    {
        java.lang.String val = OptionHelper.propertyLookup( k, local, context );
        return val == null;
    }

    public  boolean isDefined( java.lang.String k )
    {
        java.lang.String val = OptionHelper.propertyLookup( k, local, context );
        return val == null;
    }

    public  java.lang.String p( java.lang.String k )
    {
        return property( k );
    }

    public  java.lang.String property( java.lang.String k )
    {
        java.lang.String val = OptionHelper.propertyLookup( k, local, context );
        if (val != null) {
            return val;
        } else {
            return "";
        }
    }

}
