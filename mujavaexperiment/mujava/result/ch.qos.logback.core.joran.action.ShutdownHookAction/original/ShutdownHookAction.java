// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import org.xml.sax.Attributes;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.hook.DefaultShutdownHook;
import ch.qos.logback.core.hook.ShutdownHookBase;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.util.OptionHelper;


public class ShutdownHookAction extends ch.qos.logback.core.joran.action.Action
{

    ch.qos.logback.core.hook.ShutdownHookBase hook;

    private boolean inError;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        hook = null;
        inError = false;
        java.lang.String className = attributes.getValue( CLASS_ATTRIBUTE );
        if (OptionHelper.isEmpty( className )) {
            className = (ch.qos.logback.core.hook.DefaultShutdownHook.class).getName();
            addInfo( "Assuming className [" + className + "]" );
        }
        try {
            addInfo( "About to instantiate shutdown hook of type [" + className + "]" );
            hook = (ch.qos.logback.core.hook.ShutdownHookBase) OptionHelper.instantiateByClassName( className, ch.qos.logback.core.hook.ShutdownHookBase.class, context );
            hook.setContext( context );
            ic.pushObject( hook );
        } catch ( java.lang.Exception e ) {
            inError = true;
            addError( "Could not create a shutdown hook of type [" + className + "].", e );
            throw new ch.qos.logback.core.joran.spi.ActionException( e );
        }
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        if (inError) {
            return;
        }
        java.lang.Object o = ic.peekObject();
        if (o != hook) {
            addWarn( "The object at the of the stack is not the hook pushed earlier." );
        } else {
            ic.popObject();
            java.lang.Thread hookThread = new java.lang.Thread( hook, "Logback shutdown hook [" + context.getName() + "]" );
            addInfo( "Registeting shuthown hook with JVM runtime." );
            context.putObject( CoreConstants.SHUTDOWN_HOOK_THREAD, hookThread );
            Runtime.getRuntime().addShutdownHook( hookThread );
        }
    }

}
