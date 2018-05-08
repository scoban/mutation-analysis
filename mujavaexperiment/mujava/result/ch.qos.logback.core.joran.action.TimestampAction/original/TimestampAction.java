// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import ch.qos.logback.core.util.CachingDateFormatter;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.action.ActionUtil.Scope;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.util.OptionHelper;


public class TimestampAction extends ch.qos.logback.core.joran.action.Action
{

    static java.lang.String DATE_PATTERN_ATTRIBUTE = "datePattern";

    static java.lang.String TIME_REFERENCE_ATTRIBUTE = "timeReference";

    static java.lang.String CONTEXT_BIRTH = "contextBirth";

    boolean inError = false;

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
        java.lang.String keyStr = attributes.getValue( KEY_ATTRIBUTE );
        if (OptionHelper.isEmpty( keyStr )) {
            addError( "Attribute named [" + KEY_ATTRIBUTE + "] cannot be empty" );
            inError = true;
        }
        java.lang.String datePatternStr = attributes.getValue( DATE_PATTERN_ATTRIBUTE );
        if (OptionHelper.isEmpty( datePatternStr )) {
            addError( "Attribute named [" + DATE_PATTERN_ATTRIBUTE + "] cannot be empty" );
            inError = true;
        }
        java.lang.String timeReferenceStr = attributes.getValue( TIME_REFERENCE_ATTRIBUTE );
        long timeReference;
        if (CONTEXT_BIRTH.equalsIgnoreCase( timeReferenceStr )) {
            addInfo( "Using context birth as time reference." );
            timeReference = context.getBirthTime();
        } else {
            timeReference = System.currentTimeMillis();
            addInfo( "Using current interpretation time, i.e. now, as time reference." );
        }
        if (inError) {
            return;
        }
        java.lang.String scopeStr = attributes.getValue( SCOPE_ATTRIBUTE );
        ch.qos.logback.core.joran.action.ActionUtil.Scope scope = ActionUtil.stringToScope( scopeStr );
        ch.qos.logback.core.util.CachingDateFormatter sdf = new ch.qos.logback.core.util.CachingDateFormatter( datePatternStr );
        java.lang.String val = sdf.format( timeReference );
        addInfo( "Adding property to the context with key=\"" + keyStr + "\" and value=\"" + val + "\" to the " + scope + " scope" );
        ActionUtil.setProperty( ec, keyStr, val, scope );
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
    }

}
