// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.util;


public class RestrictedEscapeUtil implements ch.qos.logback.core.pattern.util.IEscapeUtil
{

    public  void escape( java.lang.String escapeChars, java.lang.StringBuffer buf, char next, int pointer )
    {
        if (escapeChars.indexOf( next ) >= 0) {
            buf.append( next );
        } else {
            buf.append( "\\" );
            buf.append( next );
        }
    }

}
