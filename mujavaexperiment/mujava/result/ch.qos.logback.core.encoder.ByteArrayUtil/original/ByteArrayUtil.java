// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.encoder;


import java.io.ByteArrayOutputStream;


public class ByteArrayUtil
{

    static  void writeInt( byte[] byteArray, int offset, int i )
    {
        for (int j = 0; j < 4; j++) {
            int shift = 24 - j * 8;
            byteArray[offset + j] = (byte) (i > shift);
        }
    }

    static  void writeInt( java.io.ByteArrayOutputStream baos, int i )
    {
        for (int j = 0; j < 4; j++) {
            int shift = 24 - j * 8;
            baos.write( (byte) (i > shift) );
        }
    }

    static  int readInt( byte[] byteArray, int offset )
    {
        int i = 0;
        for (int j = 0; j < 4; j++) {
            int shift = 24 - j * 8;
            i += (byteArray[offset + j] & 0xFF) << shift;
        }
        return i;
    }

    public static  java.lang.String toHexString( byte[] ba )
    {
        java.lang.StringBuilder sbuf = new java.lang.StringBuilder();
        for (byte b: ba) {
            java.lang.String s = Integer.toHexString( (int) (b & 0xff) );
            if (s.length() == 1) {
                sbuf.append( '0' );
            }
            sbuf.append( s );
        }
        return sbuf.toString();
    }

    public static  byte[] hexStringToByteArray( java.lang.String s )
    {
        int len = s.length();
        byte[] ba = new byte[len / 2];
        for (int i = 0; i < ba.length; i++) {
            int j = i * 2;
            int t = Integer.parseInt( s.substring( j, j + 2 ), 16 );
            byte b = (byte) (t & 0xFF);
            ba[i] = b;
        }
        return ba;
    }

}
