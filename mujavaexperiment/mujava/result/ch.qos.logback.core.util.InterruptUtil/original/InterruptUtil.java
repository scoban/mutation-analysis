// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;


public class InterruptUtil extends ch.qos.logback.core.spi.ContextAwareBase
{

    final boolean previouslyInterrupted;

    public InterruptUtil( ch.qos.logback.core.Context context )
    {
        super();
        setContext( context );
        previouslyInterrupted = Thread.currentThread().isInterrupted();
    }

    public  void maskInterruptFlag()
    {
        if (previouslyInterrupted) {
            Thread.interrupted();
        }
    }

    public  void unmaskInterruptFlag()
    {
        if (previouslyInterrupted) {
            try {
                Thread.currentThread().interrupt();
            } catch ( java.lang.SecurityException se ) {
                addError( "Failed to intrreupt current thread", se );
            }
        }
    }

}
