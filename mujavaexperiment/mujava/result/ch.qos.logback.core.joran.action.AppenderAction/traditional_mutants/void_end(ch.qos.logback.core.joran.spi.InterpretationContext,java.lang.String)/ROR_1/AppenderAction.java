// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import java.util.HashMap;
import org.xml.sax.Attributes;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.util.OptionHelper;


public class AppenderAction<E> extends ch.qos.logback.core.joran.action.Action
{

    ch.qos.logback.core.Appender<E> appender;

    private boolean inError = false;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        appender = null;
        inError = false;
        java.lang.String className = attributes.getValue( CLASS_ATTRIBUTE );
        if (OptionHelper.isEmpty( className )) {
            addError( "Missing class name for appender. Near [" + localName + "] line " + getLineNumber( ec ) );
            inError = true;
            return;
        }
        try {
            addInfo( "About to instantiate appender of type [" + className + "]" );
            appender = (ch.qos.logback.core.Appender<E>) OptionHelper.instantiateByClassName( className, ch.qos.logback.core.Appender.class, context );
            appender.setContext( context );
            java.lang.String appenderName = ec.subst( attributes.getValue( NAME_ATTRIBUTE ) );
            if (OptionHelper.isEmpty( appenderName )) {
                addWarn( "No appender name given for appender of type " + className + "]." );
            } else {
                appender.setName( appenderName );
                addInfo( "Naming appender as [" + appenderName + "]" );
            }
            java.util.HashMap<String,Appender<E>> appenderBag = (java.util.HashMap<String,Appender<E>>) ec.getObjectMap().get( ActionConst.APPENDER_BAG );
            appenderBag.put( appenderName, appender );
            ec.pushObject( appender );
        } catch ( java.lang.Exception oops ) {
            inError = true;
            addError( "Could not create an Appender of type [" + className + "].", oops );
            throw new ch.qos.logback.core.joran.spi.ActionException( oops );
        }
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name )
    {
        if (inError) {
            return;
        }
        if (appender instanceof ch.qos.logback.core.spi.LifeCycle) {
            ((ch.qos.logback.core.spi.LifeCycle) appender).start();
        }
        java.lang.Object o = ec.peekObject();
        if (o == appender) {
            addWarn( "The object at the of the stack is not the appender named [" + appender.getName() + "] pushed earlier." );
        } else {
            ec.popObject();
        }
    }

}
