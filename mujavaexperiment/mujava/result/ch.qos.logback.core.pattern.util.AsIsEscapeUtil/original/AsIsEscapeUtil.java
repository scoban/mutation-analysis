// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.util;


public class AsIsEscapeUtil implements ch.qos.logback.core.pattern.util.IEscapeUtil
{

    public  void escape( java.lang.String escapeChars, java.lang.StringBuffer buf, char next, int pointer )
    {
        buf.append( "\\" );
        buf.append( next );
    }

}
