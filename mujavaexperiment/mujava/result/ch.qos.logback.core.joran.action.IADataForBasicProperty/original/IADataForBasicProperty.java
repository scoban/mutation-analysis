// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import ch.qos.logback.core.joran.util.PropertySetter;
import ch.qos.logback.core.util.AggregationType;


class IADataForBasicProperty
{

    final ch.qos.logback.core.joran.util.PropertySetter parentBean;

    final ch.qos.logback.core.util.AggregationType aggregationType;

    final java.lang.String propertyName;

    boolean inError;

    IADataForBasicProperty( ch.qos.logback.core.joran.util.PropertySetter parentBean, ch.qos.logback.core.util.AggregationType aggregationType, java.lang.String propertyName )
    {
        this.parentBean = parentBean;
        this.aggregationType = aggregationType;
        this.propertyName = propertyName;
    }

}
