// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.boolex;


import java.util.ArrayList;
import java.util.List;
import org.codehaus.janino.ScriptEvaluator;


public abstract class JaninoEventEvaluatorBase<E> extends ch.qos.logback.core.boolex.EventEvaluatorBase<E>
{

    static java.lang.Class<?> EXPRESSION_TYPE = boolean.class;

    static java.lang.Class<?>[] THROWN_EXCEPTIONS = new java.lang.Class[1];

    public static final int ERROR_THRESHOLD = 4;

    static {
        THROWN_EXCEPTIONS[0] = ch.qos.logback.core.boolex.EvaluationException.class;
    }

    private java.lang.String expression;

    org.codehaus.janino.ScriptEvaluator scriptEvaluator;

    private int errorCount = 0;

    protected abstract  java.lang.String getDecoratedExpression();

    protected abstract  java.lang.String[] getParameterNames();

    protected abstract  java.lang.Class<?>[] getParameterTypes();

    protected abstract  java.lang.Object[] getParameterValues( E event );

    protected java.util.List<Matcher> matcherList = new java.util.ArrayList<Matcher>();

    public  void start()
    {
        try {
             assert context != null;
            scriptEvaluator = new org.codehaus.janino.ScriptEvaluator( getDecoratedExpression(), EXPRESSION_TYPE, getParameterNames(), getParameterTypes(), THROWN_EXCEPTIONS );
            super.start();
        } catch ( java.lang.Exception e ) {
            addError( "Could not start evaluator with expression [" + expression + "]", e );
        }
    }

    public  boolean evaluate( E event )
        throws ch.qos.logback.core.boolex.EvaluationException
    {
        if (!isStarted()) {
            throw new java.lang.IllegalStateException( "Evaluator [" + name + "] was called in stopped state" );
        }
        try {
            java.lang.Boolean result = (java.lang.Boolean) scriptEvaluator.evaluate( getParameterValues( event ) );
            return result.booleanValue();
        } catch ( java.lang.Exception ex ) {
            errorCount++;
            if (errorCount >= ERROR_THRESHOLD) {
                stop();
            }
            throw new ch.qos.logback.core.boolex.EvaluationException( "Evaluator [" + name + "] caused an exception", ex );
        }
    }

    public  java.lang.String getExpression()
    {
        return expression;
    }

    public  void setExpression( java.lang.String expression )
    {
        this.expression = expression;
    }

    public  void addMatcher( ch.qos.logback.core.boolex.Matcher matcher )
    {
        matcherList.add( matcher );
    }

    public  java.util.List<Matcher> getMatcherList()
    {
        return matcherList;
    }

}
