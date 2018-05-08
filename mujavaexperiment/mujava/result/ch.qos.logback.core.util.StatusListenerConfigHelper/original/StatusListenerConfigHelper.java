// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.spi.ContextAware;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.StatusListener;


public class StatusListenerConfigHelper
{

    public static  void installIfAsked( ch.qos.logback.core.Context context )
    {
        java.lang.String slClass = OptionHelper.getSystemProperty( CoreConstants.STATUS_LISTENER_CLASS_KEY );
        if (!OptionHelper.isEmpty( slClass )) {
            addStatusListener( context, slClass );
        }
    }

    private static  void addStatusListener( ch.qos.logback.core.Context context, java.lang.String listenerClassName )
    {
        ch.qos.logback.core.status.StatusListener listener = null;
        if (CoreConstants.SYSOUT.equalsIgnoreCase( listenerClassName )) {
            listener = new ch.qos.logback.core.status.OnConsoleStatusListener();
        } else {
            listener = createListenerPerClassName( context, listenerClassName );
        }
        initAndAddListener( context, listener );
    }

    private static  void initAndAddListener( ch.qos.logback.core.Context context, ch.qos.logback.core.status.StatusListener listener )
    {
        if (listener != null) {
            if (listener instanceof ch.qos.logback.core.spi.ContextAware) {
                ((ch.qos.logback.core.spi.ContextAware) listener).setContext( context );
            }
            boolean effectivelyAdded = context.getStatusManager().add( listener );
            if (effectivelyAdded && listener instanceof ch.qos.logback.core.spi.LifeCycle) {
                ((ch.qos.logback.core.spi.LifeCycle) listener).start();
            }
        }
    }

    private static  ch.qos.logback.core.status.StatusListener createListenerPerClassName( ch.qos.logback.core.Context context, java.lang.String listenerClass )
    {
        try {
            return (ch.qos.logback.core.status.StatusListener) OptionHelper.instantiateByClassName( listenerClass, ch.qos.logback.core.status.StatusListener.class, context );
        } catch ( java.lang.Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public static  void addOnConsoleListenerInstance( ch.qos.logback.core.Context context, ch.qos.logback.core.status.OnConsoleStatusListener onConsoleStatusListener )
    {
        onConsoleStatusListener.setContext( context );
        boolean effectivelyAdded = context.getStatusManager().add( onConsoleStatusListener );
        if (effectivelyAdded) {
            onConsoleStatusListener.start();
        }
    }

}
