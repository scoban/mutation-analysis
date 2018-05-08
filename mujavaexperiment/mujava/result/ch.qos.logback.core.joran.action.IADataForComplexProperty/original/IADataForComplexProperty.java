// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import ch.qos.logback.core.joran.util.PropertySetter;
import ch.qos.logback.core.util.AggregationType;


public class IADataForComplexProperty
{

    final ch.qos.logback.core.joran.util.PropertySetter parentBean;

    final ch.qos.logback.core.util.AggregationType aggregationType;

    final java.lang.String complexPropertyName;

    private java.lang.Object nestedComplexProperty;

    boolean inError;

    public IADataForComplexProperty( ch.qos.logback.core.joran.util.PropertySetter parentBean, ch.qos.logback.core.util.AggregationType aggregationType, java.lang.String complexPropertyName )
    {
        this.parentBean = parentBean;
        this.aggregationType = aggregationType;
        this.complexPropertyName = complexPropertyName;
    }

    public  ch.qos.logback.core.util.AggregationType getAggregationType()
    {
        return aggregationType;
    }

    public  java.lang.Object getNestedComplexProperty()
    {
        return nestedComplexProperty;
    }

    public  java.lang.String getComplexPropertyName()
    {
        return complexPropertyName;
    }

    public  void setNestedComplexProperty( java.lang.Object nestedComplexProperty )
    {
        this.nestedComplexProperty = nestedComplexProperty;
    }

}
