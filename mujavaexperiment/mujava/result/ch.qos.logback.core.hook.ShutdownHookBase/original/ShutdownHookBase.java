// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.hook;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.ContextBase;
import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class ShutdownHookBase extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.hook.ShutdownHook
{

    public ShutdownHookBase()
    {
    }

    protected  void stop()
    {
        addInfo( "Logback context being closed via shutdown hook" );
        ch.qos.logback.core.Context hookContext = getContext();
        if (hookContext instanceof ch.qos.logback.core.ContextBase) {
            ch.qos.logback.core.ContextBase context = (ch.qos.logback.core.ContextBase) hookContext;
            context.stop();
        }
    }

}
