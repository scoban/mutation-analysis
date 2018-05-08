// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import ch.qos.logback.core.joran.action.ActionUtil.Scope;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.util.OptionHelper;
import ch.qos.logback.core.spi.PropertyDefiner;
import org.xml.sax.Attributes;


public class DefinePropertyAction extends ch.qos.logback.core.joran.action.Action
{

    java.lang.String scopeStr;

    ch.qos.logback.core.joran.action.ActionUtil.Scope scope;

    java.lang.String propertyName;

    ch.qos.logback.core.spi.PropertyDefiner definer;

    boolean inError;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        scopeStr = null;
        scope = null;
        propertyName = null;
        definer = null;
        inError = false;
        propertyName = attributes.getValue( NAME_ATTRIBUTE );
        scopeStr = attributes.getValue( SCOPE_ATTRIBUTE );
        scope = ActionUtil.stringToScope( scopeStr );
        if (OptionHelper.isEmpty( propertyName )) {
            addError( "Missing property name for property definer. Near [" + localName + "] line " + getLineNumber( ec ) );
            inError = true;
            return;
        }
        java.lang.String className = attributes.getValue( CLASS_ATTRIBUTE );
        if (OptionHelper.isEmpty( className )) {
            addError( "Missing class name for property definer. Near [" + localName + "] line " + getLineNumber( ec ) );
            inError = true;
            return;
        }
        try {
            addInfo( "About to instantiate property definer of type [" + className + "]" );
            definer = (ch.qos.logback.core.spi.PropertyDefiner) OptionHelper.instantiateByClassName( className, ch.qos.logback.core.spi.PropertyDefiner.class, context );
            definer.setContext( context );
            if (definer instanceof ch.qos.logback.core.spi.LifeCycle) {
                ((ch.qos.logback.core.spi.LifeCycle) definer).start();
            }
            ec.pushObject( definer );
        } catch ( java.lang.Exception oops ) {
            inError = true;
            addError( "Could not create an PropertyDefiner of type [" + className + "].", oops );
            throw new ch.qos.logback.core.joran.spi.ActionException( oops );
        }
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name )
    {
        if (inError) {
            return;
        }
        java.lang.Object o = ec.peekObject();
        if (o != definer) {
            addWarn( "The object at the of the stack is not the property definer for property named [" + propertyName + "] pushed earlier." );
        } else {
            addInfo( "Popping property definer for property named [" + propertyName + "] from the object stack" );
            ec.popObject();
            java.lang.String propertyValue = definer.getPropertyValue();
            if (propertyValue != null) {
                ActionUtil.setProperty( ec, propertyName, propertyValue, scope );
            }
        }
    }

}
