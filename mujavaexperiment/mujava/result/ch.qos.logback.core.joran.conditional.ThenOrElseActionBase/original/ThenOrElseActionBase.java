// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.conditional;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.event.InPlayListener;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;


public abstract class ThenOrElseActionBase extends ch.qos.logback.core.joran.action.Action
{

    java.util.Stack<ThenActionState> stateStack = new java.util.Stack<ThenActionState>();

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        if (!weAreActive( ic )) {
            return;
        }
        ch.qos.logback.core.joran.conditional.ThenActionState state = new ch.qos.logback.core.joran.conditional.ThenActionState();
        if (ic.isListenerListEmpty()) {
            ic.addInPlayListener( state );
            state.isRegistered = true;
        }
        stateStack.push( state );
    }

     boolean weAreActive( ch.qos.logback.core.joran.spi.InterpretationContext ic )
    {
        java.lang.Object o = ic.peekObject();
        if (!(o instanceof ch.qos.logback.core.joran.conditional.IfAction)) {
            return false;
        }
        ch.qos.logback.core.joran.conditional.IfAction ifAction = (ch.qos.logback.core.joran.conditional.IfAction) o;
        return ifAction.isActive();
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        if (!weAreActive( ic )) {
            return;
        }
        ch.qos.logback.core.joran.conditional.ThenActionState state = stateStack.pop();
        if (state.isRegistered) {
            ic.removeInPlayListener( state );
            java.lang.Object o = ic.peekObject();
            if (o instanceof ch.qos.logback.core.joran.conditional.IfAction) {
                ch.qos.logback.core.joran.conditional.IfAction ifAction = (ch.qos.logback.core.joran.conditional.IfAction) o;
                removeFirstAndLastFromList( state.eventList );
                registerEventList( ifAction, state.eventList );
            } else {
                throw new java.lang.IllegalStateException( "Missing IfAction on top of stack" );
            }
        }
    }

    abstract  void registerEventList( ch.qos.logback.core.joran.conditional.IfAction ifAction, java.util.List<SaxEvent> eventList );

     void removeFirstAndLastFromList( java.util.List<SaxEvent> eventList )
    {
        eventList.remove( 0 );
        eventList.remove( eventList.size() - 1 );
    }

}

class ThenActionState implements ch.qos.logback.core.joran.event.InPlayListener
{

    java.util.List<SaxEvent> eventList = new java.util.ArrayList<SaxEvent>();

    boolean isRegistered = false;

    public  void inPlay( ch.qos.logback.core.joran.event.SaxEvent event )
    {
        eventList.add( event );
    }

}
