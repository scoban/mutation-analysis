// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import org.xml.sax.Attributes;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.spi.AppenderAttachable;
import ch.qos.logback.core.util.OptionHelper;
import java.util.HashMap;


public class AppenderRefAction<E> extends ch.qos.logback.core.joran.action.Action
{

    boolean inError = false;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String tagName, org.xml.sax.Attributes attributes )
    {
        inError = false;
        java.lang.Object o = ec.peekObject();
        if (!(o instanceof ch.qos.logback.core.spi.AppenderAttachable)) {
            java.lang.String errMsg = "Could not find an AppenderAttachable at the top of execution stack. Near [" + tagName + "] line " + getLineNumber( ec );
            inError = true;
            addError( errMsg );
            return;
        }
        ch.qos.logback.core.spi.AppenderAttachable<E> appenderAttachable = (ch.qos.logback.core.spi.AppenderAttachable<E>) o;
        java.lang.String appenderName = ec.subst( attributes.getValue( ActionConst.REF_ATTRIBUTE ) );
        if (OptionHelper.isEmpty( appenderName )) {
            java.lang.String errMsg = "Missing appender ref attribute in <appender-ref> tag.";
            inError = true;
            addError( errMsg );
            return;
        }
        java.util.HashMap<String,Appender<E>> appenderBag = (java.util.HashMap<String,Appender<E>>) ec.getObjectMap().get( ActionConst.APPENDER_BAG );
        ch.qos.logback.core.Appender<E> appender = (ch.qos.logback.core.Appender<E>) appenderBag.get( appenderName );
        if (appender == null) {
            java.lang.String msg = "Could not find an appender named [" + appenderName + "]. Did you define it below instead of above in the configuration file?";
            inError = true;
            addError( msg );
            addError( "See " + CoreConstants.CODES_URL + "#appender_order for more details." );
            return;
        }
        addInfo( "Attaching appender named [" + appenderName + "] to " + appenderAttachable );
        appenderAttachable.addAppender( appender );
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String n )
    {
    }

}
