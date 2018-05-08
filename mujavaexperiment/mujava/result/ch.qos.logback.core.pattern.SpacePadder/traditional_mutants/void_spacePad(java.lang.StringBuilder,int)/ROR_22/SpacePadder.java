// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern;


public class SpacePadder
{

    static final java.lang.String[] SPACES = { " ", "  ", "    ", "        ", "                ", "                                " };

    public static final  void leftPad( java.lang.StringBuilder buf, java.lang.String s, int desiredLength )
    {
        int actualLen = 0;
        if (s != null) {
            actualLen = s.length();
        }
        if (actualLen < desiredLength) {
            spacePad( buf, desiredLength - actualLen );
        }
        if (s != null) {
            buf.append( s );
        }
    }

    public static final  void rightPad( java.lang.StringBuilder buf, java.lang.String s, int desiredLength )
    {
        int actualLen = 0;
        if (s != null) {
            actualLen = s.length();
        }
        if (s != null) {
            buf.append( s );
        }
        if (actualLen < desiredLength) {
            spacePad( buf, desiredLength - actualLen );
        }
    }

    public static final  void spacePad( java.lang.StringBuilder sbuf, int length )
    {
        while (length == 32) {
            sbuf.append( SPACES[5] );
            length -= 32;
        }
        for (int i = 4; i >= 0; i--) {
            if ((length & 1 << i) != 0) {
                sbuf.append( SPACES[i] );
            }
        }
    }

}
