// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran;


import java.util.HashMap;
import java.util.Map;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.joran.action.ActionConst;
import ch.qos.logback.core.joran.action.AppenderAction;
import ch.qos.logback.core.joran.action.AppenderRefAction;
import ch.qos.logback.core.joran.action.ContextPropertyAction;
import ch.qos.logback.core.joran.action.ConversionRuleAction;
import ch.qos.logback.core.joran.action.DefinePropertyAction;
import ch.qos.logback.core.joran.action.NestedBasicPropertyIA;
import ch.qos.logback.core.joran.action.NestedComplexPropertyIA;
import ch.qos.logback.core.joran.action.NewRuleAction;
import ch.qos.logback.core.joran.action.ParamAction;
import ch.qos.logback.core.joran.action.PropertyAction;
import ch.qos.logback.core.joran.action.ShutdownHookAction;
import ch.qos.logback.core.joran.action.StatusListenerAction;
import ch.qos.logback.core.joran.action.TimestampAction;
import ch.qos.logback.core.joran.spi.ElementSelector;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.Interpreter;
import ch.qos.logback.core.joran.spi.RuleStore;


public abstract class JoranConfiguratorBase<E> extends ch.qos.logback.core.joran.GenericConfigurator
{

    protected  void addInstanceRules( ch.qos.logback.core.joran.spi.RuleStore rs )
    {
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/variable" ), new ch.qos.logback.core.joran.action.PropertyAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/property" ), new ch.qos.logback.core.joran.action.PropertyAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/substitutionProperty" ), new ch.qos.logback.core.joran.action.PropertyAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/timestamp" ), new ch.qos.logback.core.joran.action.TimestampAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/shutdownHook" ), new ch.qos.logback.core.joran.action.ShutdownHookAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/define" ), new ch.qos.logback.core.joran.action.DefinePropertyAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/contextProperty" ), new ch.qos.logback.core.joran.action.ContextPropertyAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/conversionRule" ), new ch.qos.logback.core.joran.action.ConversionRuleAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/statusListener" ), new ch.qos.logback.core.joran.action.StatusListenerAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/appender" ), new ch.qos.logback.core.joran.action.AppenderAction<E>() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/appender/appender-ref" ), new ch.qos.logback.core.joran.action.AppenderRefAction<E>() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/newRule" ), new ch.qos.logback.core.joran.action.NewRuleAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "*/param" ), new ch.qos.logback.core.joran.action.ParamAction( getBeanDescriptionCache() ) );
    }

    protected  void addImplicitRules( ch.qos.logback.core.joran.spi.Interpreter interpreter )
    {
        ch.qos.logback.core.joran.action.NestedComplexPropertyIA nestedComplexPropertyIA = new ch.qos.logback.core.joran.action.NestedComplexPropertyIA( getBeanDescriptionCache() );
        nestedComplexPropertyIA.setContext( context );
        interpreter.addImplicitAction( nestedComplexPropertyIA );
        ch.qos.logback.core.joran.action.NestedBasicPropertyIA nestedBasicIA = new ch.qos.logback.core.joran.action.NestedBasicPropertyIA( getBeanDescriptionCache() );
        nestedBasicIA.setContext( context );
        interpreter.addImplicitAction( nestedBasicIA );
    }

    protected  void buildInterpreter()
    {
        super.buildInterpreter();
        java.util.Map<String,Object> omap = interpreter.getInterpretationContext().getObjectMap();
        omap.put( ActionConst.APPENDER_BAG, new java.util.HashMap<String,Appender<?>>() );
    }

    public  ch.qos.logback.core.joran.spi.InterpretationContext getInterpretationContext()
    {
        return interpreter.getInterpretationContext();
    }

}
