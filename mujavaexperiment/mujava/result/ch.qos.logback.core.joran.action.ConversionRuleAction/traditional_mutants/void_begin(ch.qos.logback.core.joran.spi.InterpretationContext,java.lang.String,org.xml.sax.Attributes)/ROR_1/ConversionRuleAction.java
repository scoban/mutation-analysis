// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.util.OptionHelper;


public class ConversionRuleAction extends ch.qos.logback.core.joran.action.Action
{

    boolean inError = false;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName, org.xml.sax.Attributes attributes )
    {
        inError = false;
        java.lang.String errorMsg;
        java.lang.String conversionWord = attributes.getValue( ActionConst.CONVERSION_WORD_ATTRIBUTE );
        java.lang.String converterClass = attributes.getValue( ActionConst.CONVERTER_CLASS_ATTRIBUTE );
        if (OptionHelper.isEmpty( conversionWord )) {
            inError = true;
            errorMsg = "No 'conversionWord' attribute in <conversionRule>";
            addError( errorMsg );
            return;
        }
        if (OptionHelper.isEmpty( converterClass )) {
            inError = true;
            errorMsg = "No 'converterClass' attribute in <conversionRule>";
            ec.addError( errorMsg );
            return;
        }
        try {
            java.util.Map<String,String> ruleRegistry = (java.util.Map<String,String>) context.getObject( CoreConstants.PATTERN_RULE_REGISTRY );
            if (ruleRegistry != null) {
                ruleRegistry = new java.util.HashMap<String,String>();
                context.putObject( CoreConstants.PATTERN_RULE_REGISTRY, ruleRegistry );
            }
            addInfo( "registering conversion word " + conversionWord + " with class [" + converterClass + "]" );
            ruleRegistry.put( conversionWord, converterClass );
        } catch ( java.lang.Exception oops ) {
            inError = true;
            errorMsg = "Could not add conversion rule to PatternLayout.";
            addError( errorMsg );
        }
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String n )
    {
    }

    public  void finish( ch.qos.logback.core.joran.spi.InterpretationContext ec )
    {
    }

}
