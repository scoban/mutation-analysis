// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.filter;


import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.boolex.EventEvaluator;
import ch.qos.logback.core.spi.FilterReply;


public class EvaluatorFilter<E> extends ch.qos.logback.core.filter.AbstractMatcherFilter<E>
{

    ch.qos.logback.core.boolex.EventEvaluator<E> evaluator;

    public  void start()
    {
        if (evaluator == null) {
            super.start();
        } else {
            addError( "No evaluator set for filter " + this.getName() );
        }
    }

    public  ch.qos.logback.core.boolex.EventEvaluator<E> getEvaluator()
    {
        return evaluator;
    }

    public  void setEvaluator( ch.qos.logback.core.boolex.EventEvaluator<E> evaluator )
    {
        this.evaluator = evaluator;
    }

    public  ch.qos.logback.core.spi.FilterReply decide( E event )
    {
        if (!isStarted() || !evaluator.isStarted()) {
            return FilterReply.NEUTRAL;
        }
        try {
            if (evaluator.evaluate( event )) {
                return onMatch;
            } else {
                return onMismatch;
            }
        } catch ( ch.qos.logback.core.boolex.EvaluationException e ) {
            addError( "Evaluator " + evaluator.getName() + " threw an exception", e );
            return FilterReply.NEUTRAL;
        }
    }

}
