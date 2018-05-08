// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import ch.qos.logback.core.spi.ContextAware;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.status.StatusListener;
import ch.qos.logback.core.util.OptionHelper;


public class StatusListenerAction extends ch.qos.logback.core.joran.action.Action
{

    boolean inError = false;

    java.lang.Boolean effectivelyAdded = null;

    ch.qos.logback.core.status.StatusListener statusListener = null;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        inError = false;
        effectivelyAdded = null;
        java.lang.String className = attributes.getValue( CLASS_ATTRIBUTE );
        if (OptionHelper.isEmpty( className )) {
            addError( "Missing class name for statusListener. Near [" + name + "] line " + getLineNumber( ec ) );
            inError = true;
            return;
        }
        try {
            statusListener = (ch.qos.logback.core.status.StatusListener) OptionHelper.instantiateByClassName( className, ch.qos.logback.core.status.StatusListener.class, context );
            effectivelyAdded = ec.getContext().getStatusManager().add( statusListener );
            if (statusListener instanceof ch.qos.logback.core.spi.ContextAware) {
                ((ch.qos.logback.core.spi.ContextAware) statusListener).setContext( context );
            }
            addInfo( "Added status listener of type [" + className + "]" );
            ec.pushObject( statusListener );
        } catch ( java.lang.Exception e ) {
            inError = true;
            addError( "Could not create an StatusListener of type [" + className + "].", e );
            throw new ch.qos.logback.core.joran.spi.ActionException( e );
        }
    }

    public  void finish( ch.qos.logback.core.joran.spi.InterpretationContext ec )
    {
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String e )
    {
        if (inError) {
            return;
        }
        if (isEffectivelyAdded() && statusListener instanceof ch.qos.logback.core.spi.LifeCycle) {
            ((ch.qos.logback.core.spi.LifeCycle) statusListener).start();
        }
        java.lang.Object o = ec.peekObject();
        if (o != statusListener) {
            addWarn( "The object at the of the stack is not the statusListener pushed earlier." );
        } else {
            ec.popObject();
        }
    }

    private  boolean isEffectivelyAdded()
    {
        if (effectivelyAdded == null) {
            return false;
        }
        return effectivelyAdded;
    }

}
