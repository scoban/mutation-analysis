// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import java.util.Map;
import org.xml.sax.Attributes;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.boolex.EventEvaluator;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.util.OptionHelper;


public abstract class AbstractEventEvaluatorAction extends ch.qos.logback.core.joran.action.Action
{

    ch.qos.logback.core.boolex.EventEvaluator<?> evaluator;

    boolean inError = false;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name, org.xml.sax.Attributes attributes )
    {
        inError = false;
        evaluator = null;
        java.lang.String className = attributes.getValue( CLASS_ATTRIBUTE );
        if (OptionHelper.isEmpty( className )) {
            className = defaultClassName();
            addInfo( "Assuming default evaluator class [" + className + "]" );
        }
        if (OptionHelper.isEmpty( className )) {
            className = defaultClassName();
            inError = true;
            addError( "Mandatory \"" + CLASS_ATTRIBUTE + "\" attribute not set for <evaluator>" );
            return;
        }
        java.lang.String evaluatorName = attributes.getValue( Action.NAME_ATTRIBUTE );
        if (OptionHelper.isEmpty( evaluatorName )) {
            inError = true;
            addError( "Mandatory \"" + NAME_ATTRIBUTE + "\" attribute not set for <evaluator>" );
            return;
        }
        try {
            evaluator = (ch.qos.logback.core.boolex.EventEvaluator<?>) OptionHelper.instantiateByClassName( className, ch.qos.logback.core.boolex.EventEvaluator.class, context );
            evaluator.setContext( this.context );
            evaluator.setName( evaluatorName );
            ec.pushObject( evaluator );
            addInfo( "Adding evaluator named [" + evaluatorName + "] to the object stack" );
        } catch ( java.lang.Exception oops ) {
            inError = true;
            addError( "Could not create evaluator of type " + className + "].", oops );
        }
    }

    protected abstract  java.lang.String defaultClassName();

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String e )
    {
        if (inError) {
            return;
        }
        if (evaluator instanceof ch.qos.logback.core.spi.LifeCycle) {
            ((ch.qos.logback.core.spi.LifeCycle) evaluator).start();
            addInfo( "Starting evaluator named [" + evaluator.getName() + "]" );
        }
        java.lang.Object o = ec.peekObject();
        if (o == evaluator) {
            addWarn( "The object on the top the of the stack is not the evaluator pushed earlier." );
        } else {
            ec.popObject();
            try {
                java.util.Map<String,EventEvaluator<?>> evaluatorMap = (java.util.Map<String,EventEvaluator<?>>) context.getObject( CoreConstants.EVALUATOR_MAP );
                if (evaluatorMap == null) {
                    addError( "Could not find EvaluatorMap" );
                } else {
                    evaluatorMap.put( evaluator.getName(), evaluator );
                }
            } catch ( java.lang.Exception ex ) {
                addError( "Could not set evaluator named [" + evaluator + "].", ex );
            }
        }
    }

    public  void finish( ch.qos.logback.core.joran.spi.InterpretationContext ec )
    {
    }

}
