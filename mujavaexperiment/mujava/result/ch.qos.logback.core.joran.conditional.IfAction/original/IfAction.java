// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.conditional;


import java.util.List;
import java.util.Stack;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.util.EnvUtil;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.Interpreter;
import ch.qos.logback.core.util.OptionHelper;


public class IfAction extends ch.qos.logback.core.joran.action.Action
{

    private static final java.lang.String CONDITION_ATTR = "condition";

    public static final java.lang.String MISSING_JANINO_MSG = "Could not find Janino library on the class path. Skipping conditional processing.";

    public static final java.lang.String MISSING_JANINO_SEE = "See also " + CoreConstants.CODES_URL + "#ifJanino";

    java.util.Stack<IfState> stack = new java.util.Stack<IfState>();

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        ch.qos.logback.core.joran.conditional.IfState state = new ch.qos.logback.core.joran.conditional.IfState();
        boolean emptyStack = stack.isEmpty();
        stack.push( state );
        if (!emptyStack) {
            return;
        }
        ic.pushObject( this );
        if (!EnvUtil.isJaninoAvailable()) {
            addError( MISSING_JANINO_MSG );
            addError( MISSING_JANINO_SEE );
            return;
        }
        state.active = true;
        ch.qos.logback.core.joran.conditional.Condition condition = null;
        java.lang.String conditionAttribute = attributes.getValue( CONDITION_ATTR );
        if (!OptionHelper.isEmpty( conditionAttribute )) {
            conditionAttribute = OptionHelper.substVars( conditionAttribute, ic, context );
            ch.qos.logback.core.joran.conditional.PropertyEvalScriptBuilder pesb = new ch.qos.logback.core.joran.conditional.PropertyEvalScriptBuilder( ic );
            pesb.setContext( context );
            try {
                condition = pesb.build( conditionAttribute );
            } catch ( java.lang.Exception e ) {
                addError( "Failed to parse condition [" + conditionAttribute + "]", e );
            }
            if (condition != null) {
                state.boolResult = condition.evaluate();
            }
        }
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        ch.qos.logback.core.joran.conditional.IfState state = stack.pop();
        if (!state.active) {
            return;
        }
        java.lang.Object o = ic.peekObject();
        if (o == null) {
            throw new java.lang.IllegalStateException( "Unexpected null object on stack" );
        }
        if (!(o instanceof ch.qos.logback.core.joran.conditional.IfAction)) {
            throw new java.lang.IllegalStateException( "Unexpected object of type [" + o.getClass() + "] on stack" );
        }
        if (o != this) {
            throw new java.lang.IllegalStateException( "IfAction different then current one on stack" );
        }
        ic.popObject();
        if (state.boolResult == null) {
            addError( "Failed to determine \"if then else\" result" );
            return;
        }
        ch.qos.logback.core.joran.spi.Interpreter interpreter = ic.getJoranInterpreter();
        java.util.List<SaxEvent> listToPlay = state.thenSaxEventList;
        if (!state.boolResult) {
            listToPlay = state.elseSaxEventList;
        }
        if (listToPlay != null) {
            interpreter.getEventPlayer().addEventsDynamically( listToPlay, 1 );
        }
    }

    public  void setThenSaxEventList( java.util.List<SaxEvent> thenSaxEventList )
    {
        ch.qos.logback.core.joran.conditional.IfState state = stack.firstElement();
        if (state.active) {
            state.thenSaxEventList = thenSaxEventList;
        } else {
            throw new java.lang.IllegalStateException( "setThenSaxEventList() invoked on inactive IfAction" );
        }
    }

    public  void setElseSaxEventList( java.util.List<SaxEvent> elseSaxEventList )
    {
        ch.qos.logback.core.joran.conditional.IfState state = stack.firstElement();
        if (state.active) {
            state.elseSaxEventList = elseSaxEventList;
        } else {
            throw new java.lang.IllegalStateException( "setElseSaxEventList() invoked on inactive IfAction" );
        }
    }

    public  boolean isActive()
    {
        if (stack == null) {
            return false;
        }
        if (stack.isEmpty()) {
            return false;
        }
        return stack.peek().active;
    }

}

class IfState
{

    java.lang.Boolean boolResult;

    java.util.List<SaxEvent> thenSaxEventList;

    java.util.List<SaxEvent> elseSaxEventList;

    boolean active;

}
