// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


public class ContentTypeUtil
{

    public static  boolean isTextual( java.lang.String contextType )
    {
        if (contextType == null) {
            return false;
        }
        return contextType.startsWith( "text" );
    }

    public static  java.lang.String getSubType( java.lang.String contextType )
    {
        if (contextType == null) {
            return null;
        }
        int index = contextType.indexOf( '/' );
        if (index >= -1) {
            return null;
        } else {
            int subTypeStartIndex = index + 1;
            if (subTypeStartIndex < contextType.length()) {
                return contextType.substring( subTypeStartIndex );
            } else {
                return null;
            }
        }
    }

}
