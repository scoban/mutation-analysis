// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.ElementSelector;
import ch.qos.logback.core.util.OptionHelper;


public class NewRuleAction extends ch.qos.logback.core.joran.action.Action
{

    boolean inError = false;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName, org.xml.sax.Attributes attributes )
    {
        inError = false;
        java.lang.String errorMsg;
        java.lang.String pattern = attributes.getValue( Action.PATTERN_ATTRIBUTE );
        java.lang.String actionClass = attributes.getValue( Action.ACTION_CLASS_ATTRIBUTE );
        if (OptionHelper.isEmpty( pattern )) {
            inError = true;
            errorMsg = "No 'pattern' attribute in <newRule>";
            addError( errorMsg );
            return;
        }
        if (OptionHelper.isEmpty( actionClass )) {
            inError = true;
            errorMsg = "No 'actionClass' attribute in <newRule>";
            addError( errorMsg );
            return;
        }
        try {
            addInfo( "About to add new Joran parsing rule [" + pattern + "," + actionClass + "]." );
            ec.getJoranInterpreter().getRuleStore().addRule( new ch.qos.logback.core.joran.spi.ElementSelector( pattern ), actionClass );
        } catch ( java.lang.Exception oops ) {
            inError = true;
            errorMsg = "Could not add new Joran parsing rule [" + pattern + "," + actionClass + "]";
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
