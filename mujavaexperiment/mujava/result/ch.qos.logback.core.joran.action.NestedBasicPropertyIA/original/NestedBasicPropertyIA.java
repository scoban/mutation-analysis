// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import java.util.Stack;
import ch.qos.logback.core.joran.spi.ElementPath;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.util.PropertySetter;
import ch.qos.logback.core.joran.util.beans.BeanDescriptionCache;
import ch.qos.logback.core.util.AggregationType;


public class NestedBasicPropertyIA extends ch.qos.logback.core.joran.action.ImplicitAction
{

    java.util.Stack<IADataForBasicProperty> actionDataStack = new java.util.Stack<IADataForBasicProperty>();

    private final ch.qos.logback.core.joran.util.beans.BeanDescriptionCache beanDescriptionCache;

    public NestedBasicPropertyIA( ch.qos.logback.core.joran.util.beans.BeanDescriptionCache beanDescriptionCache )
    {
        this.beanDescriptionCache = beanDescriptionCache;
    }

    public  boolean isApplicable( ch.qos.logback.core.joran.spi.ElementPath elementPath, org.xml.sax.Attributes attributes, ch.qos.logback.core.joran.spi.InterpretationContext ec )
    {
        java.lang.String nestedElementTagName = elementPath.peekLast();
        if (ec.isEmpty()) {
            return false;
        }
        java.lang.Object o = ec.peekObject();
        ch.qos.logback.core.joran.util.PropertySetter parentBean = new ch.qos.logback.core.joran.util.PropertySetter( beanDescriptionCache, o );
        parentBean.setContext( context );
        ch.qos.logback.core.util.AggregationType aggregationType = parentBean.computeAggregationType( nestedElementTagName );
        switch (aggregationType) {
        case NOT_FOUND :
        case AS_COMPLEX_PROPERTY :
        case AS_COMPLEX_PROPERTY_COLLECTION :
            return false;

        case AS_BASIC_PROPERTY :
        case AS_BASIC_PROPERTY_COLLECTION :
            ch.qos.logback.core.joran.action.IADataForBasicProperty ad = new ch.qos.logback.core.joran.action.IADataForBasicProperty( parentBean, aggregationType, nestedElementTagName );
            actionDataStack.push( ad );
            return true;

        default  :
            addError( "PropertySetter.canContainComponent returned " + aggregationType );
            return false;

        }
    }

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName, org.xml.sax.Attributes attributes )
    {
    }

    public  void body( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String body )
    {
        java.lang.String finalBody = ec.subst( body );
        ch.qos.logback.core.joran.action.IADataForBasicProperty actionData = (ch.qos.logback.core.joran.action.IADataForBasicProperty) actionDataStack.peek();
        switch (actionData.aggregationType) {
        case AS_BASIC_PROPERTY :
            actionData.parentBean.setProperty( actionData.propertyName, finalBody );
            break;

        case AS_BASIC_PROPERTY_COLLECTION :
            actionData.parentBean.addBasicProperty( actionData.propertyName, finalBody );
            break;

        default  :
            addError( "Unexpected aggregationType " + actionData.aggregationType );

        }
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String tagName )
    {
        actionDataStack.pop();
    }

}
