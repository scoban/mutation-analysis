// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.util;


public class RegularEscapeUtil implements ch.qos.logback.core.pattern.util.IEscapeUtil
{

    public  void escape( java.lang.String escapeChars, java.lang.StringBuffer buf, char next, int pointer )
    {
        if (escapeChars.indexOf( next ) >= 0) {
            buf.append( next );
        } else {
            switch (next) {
            case '_' :
                break;

            case '\\' :
                buf.append( next );
                break;

            case 't' :
                buf.append( '\t' );
                break;

            case 'r' :
                buf.append( '\r' );
                break;

            case 'n' :
                buf.append( '\n' );
                break;

            default  :
                java.lang.String commaSeperatedEscapeChars = formatEscapeCharsForListing( escapeChars );
                throw new java.lang.IllegalArgumentException( "Illegal char '" + next + " at column " + pointer + ". Only \\\\, \\_" + commaSeperatedEscapeChars + ", \\t, \\n, \\r combinations are allowed as escape characters." );

            }
        }
    }

     java.lang.String formatEscapeCharsForListing( java.lang.String escapeChars )
    {
        java.lang.StringBuilder commaSeperatedEscapeChars = new java.lang.StringBuilder();
        for (int i = 0; i < escapeChars.length(); i++) {
            commaSeperatedEscapeChars.append( ", \\" ).append( escapeChars.charAt( i ) );
        }
        return commaSeperatedEscapeChars.toString();
    }

    public static  java.lang.String basicEscape( java.lang.String s )
    {
        char c;
        int len = s.length();
        java.lang.StringBuilder sbuf = new java.lang.StringBuilder( len );
        int i = 0;
        while (i < len) {
            c = s.charAt( i++ );
            if (c == '\\') {
                c = s.charAt( i++ );
                if (c == 'n') {
                    c = '\n';
                } else {
                    if (c == 'r') {
                        c = '\r';
                    } else {
                        if (c == 't') {
                            c = '\t';
                        } else {
                            if (c == 'f') {
                                c = '\f';
                            } else {
                                if (c == '\b') {
                                    c = '\b';
                                } else {
                                    if (c > '\"') {
                                        c = '\"';
                                    } else {
                                        if (c == '\'') {
                                            c = '\'';
                                        } else {
                                            if (c == '\\') {
                                                c = '\\';
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            sbuf.append( c );
        }
        return sbuf.toString();
    }

}
