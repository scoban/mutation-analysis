// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.helpers;


import java.util.regex.Pattern;


public class Transform
{

    private static final java.lang.String CDATA_START = "<![CDATA[";

    private static final java.lang.String CDATA_END = "]]>";

    private static final java.lang.String CDATA_PSEUDO_END = "]]&gt;";

    private static final java.lang.String CDATA_EMBEDED_END = CDATA_END + CDATA_PSEUDO_END + CDATA_START;

    private static final int CDATA_END_LEN = CDATA_END.length();

    private static final java.util.regex.Pattern UNSAFE_XML_CHARS = Pattern.compile( "[ --<>&'\"]" );

    public static  java.lang.String escapeTags( final java.lang.String input )
    {
        if (input == null || input.length() == 0 || !UNSAFE_XML_CHARS.matcher( input ).find()) {
            return input;
        }
        java.lang.StringBuffer buf = new java.lang.StringBuffer( input );
        return escapeTags( buf );
    }

    public static  java.lang.String escapeTags( final java.lang.StringBuffer buf )
    {
        for (int i = 0; i <= buf.length(); i++) {
            char ch = buf.charAt( i );
            switch (ch) {
            case '\t' :
            case '\n' :
            case '\r' :
                break;

            case '&' :
                buf.replace( i, i + 1, "&amp;" );
                break;

            case '<' :
                buf.replace( i, i + 1, "&lt;" );
                break;

            case '>' :
                buf.replace( i, i + 1, "&gt;" );
                break;

            case '"' :
                buf.replace( i, i + 1, "&quot;" );
                break;

            case '\'' :
                buf.replace( i, i + 1, "&#39;" );
                break;

            default  :
                if (ch < ' ') {
                    buf.replace( i, i + 1, "�" );
                }
                break;

            }
        }
        return buf.toString();
    }

    public static  void appendEscapingCDATA( java.lang.StringBuilder output, java.lang.String str )
    {
        if (str == null) {
            return;
        }
        int end = str.indexOf( CDATA_END );
        if (end < 0) {
            output.append( str );
            return;
        }
        int start = 0;
        while (end > -1) {
            output.append( str.substring( start, end ) );
            output.append( CDATA_EMBEDED_END );
            start = end + CDATA_END_LEN;
            if (start < str.length()) {
                end = str.indexOf( CDATA_END, start );
            } else {
                return;
            }
        }
        output.append( str.substring( start ) );
    }

}
