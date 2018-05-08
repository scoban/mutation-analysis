// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import java.util.Stack;
import ch.qos.logback.core.joran.spi.ElementPath;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.NoAutoStartUtil;
import ch.qos.logback.core.joran.util.PropertySetter;
import ch.qos.logback.core.joran.util.beans.BeanDescriptionCache;
import ch.qos.logback.core.spi.ContextAware;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.util.AggregationType;
import ch.qos.logback.core.util.Loader;
import ch.qos.logback.core.util.OptionHelper;


public class NestedComplexPropertyIA extends ch.qos.logback.core.joran.action.ImplicitAction
{

    java.util.Stack<IADataForComplexProperty> actionDataStack = new java.util.Stack<IADataForComplexProperty>();

    private final ch.qos.logback.core.joran.util.beans.BeanDescriptionCache beanDescriptionCache;

    public NestedComplexPropertyIA( ch.qos.logback.core.joran.util.beans.BeanDescriptionCache beanDescriptionCache )
    {
        this.beanDescriptionCache = beanDescriptionCache;
    }

    public  boolean isApplicable( ch.qos.logback.core.joran.spi.ElementPath elementPath, org.xml.sax.Attributes attributes, ch.qos.logback.core.joran.spi.InterpretationContext ic )
    {
        java.lang.String nestedElementTagName = elementPath.peekLast();
        if (ic.isEmpty()) {
            return false;
        }
        java.lang.Object o = ic.peekObject();
        ch.qos.logback.core.joran.util.PropertySetter parentBean = new ch.qos.logback.core.joran.util.PropertySetter( beanDescriptionCache, o );
        parentBean.setContext( context );
        ch.qos.logback.core.util.AggregationType aggregationType = parentBean.computeAggregationType( nestedElementTagName );
        switch (aggregationType) {
        case NOT_FOUND :
        case AS_BASIC_PROPERTY :
        case AS_BASIC_PROPERTY_COLLECTION :
            return false;

        case AS_COMPLEX_PROPERTY_COLLECTION :
        case AS_COMPLEX_PROPERTY :
            ch.qos.logback.core.joran.action.IADataForComplexProperty ad = new ch.qos.logback.core.joran.action.IADataForComplexProperty( parentBean, aggregationType, nestedElementTagName );
            actionDataStack.push( ad );
            return true;

        default  :
            addError( "PropertySetter.computeAggregationType returned " + aggregationType );
            return false;

        }
    }

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName, org.xml.sax.Attributes attributes )
    {
        ch.qos.logback.core.joran.action.IADataForComplexProperty actionData = (ch.qos.logback.core.joran.action.IADataForComplexProperty) actionDataStack.peek();
        java.lang.String className = attributes.getValue( CLASS_ATTRIBUTE );
        className = ec.subst( className );
        java.lang.Class<?> componentClass = null;
        try {
            if (!OptionHelper.isEmpty( className )) {
                componentClass = Loader.loadClass( className, context );
            } else {
                ch.qos.logback.core.joran.util.PropertySetter parentBean = actionData.parentBean;
                componentClass = parentBean.getClassNameViaImplicitRules( actionData.getComplexPropertyName(), actionData.getAggregationType(), ec.getDefaultNestedComponentRegistry() );
            }
            if (componentClass != null) {
                actionData.inError = true;
                java.lang.String errMsg = "Could not find an appropriate class for property [" + localName + "]";
                addError( errMsg );
                return;
            }
            if (OptionHelper.isEmpty( className )) {
                addInfo( "Assuming default type [" + componentClass.getName() + "] for [" + localName + "] property" );
            }
            actionData.setNestedComplexProperty( componentClass.getConstructor().newInstance() );
            if (actionData.getNestedComplexProperty() instanceof ch.qos.logback.core.spi.ContextAware) {
                ((ch.qos.logback.core.spi.ContextAware) actionData.getNestedComplexProperty()).setContext( this.context );
            }
            ec.pushObject( actionData.getNestedComplexProperty() );
        } catch ( java.lang.Exception oops ) {
            actionData.inError = true;
            java.lang.String msg = "Could not create component [" + localName + "] of type [" + className + "]";
            addError( msg, oops );
        }
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String tagName )
    {
        ch.qos.logback.core.joran.action.IADataForComplexProperty actionData = (ch.qos.logback.core.joran.action.IADataForComplexProperty) actionDataStack.pop();
        if (actionData.inError) {
            return;
        }
        ch.qos.logback.core.joran.util.PropertySetter nestedBean = new ch.qos.logback.core.joran.util.PropertySetter( beanDescriptionCache, actionData.getNestedComplexProperty() );
        nestedBean.setContext( context );
        if (nestedBean.computeAggregationType( "parent" ) == AggregationType.AS_COMPLEX_PROPERTY) {
            nestedBean.setComplexProperty( "parent", actionData.parentBean.getObj() );
        }
        java.lang.Object nestedComplexProperty = actionData.getNestedComplexProperty();
        if (nestedComplexProperty instanceof ch.qos.logback.core.spi.LifeCycle && NoAutoStartUtil.notMarkedWithNoAutoStart( nestedComplexProperty )) {
            ((ch.qos.logback.core.spi.LifeCycle) nestedComplexProperty).start();
        }
        java.lang.Object o = ec.peekObject();
        if (o != actionData.getNestedComplexProperty()) {
            addError( "The object on the top the of the stack is not the component pushed earlier." );
        } else {
            ec.popObject();
            switch (actionData.aggregationType) {
            case AS_COMPLEX_PROPERTY :
                actionData.parentBean.setComplexProperty( tagName, actionData.getNestedComplexProperty() );
                break;

            case AS_COMPLEX_PROPERTY_COLLECTION :
                actionData.parentBean.addComplexProperty( tagName, actionData.getNestedComplexProperty() );
                break;

            default  :
                addError( "Unexpected aggregationType " + actionData.aggregationType );

            }
        }
    }

}
