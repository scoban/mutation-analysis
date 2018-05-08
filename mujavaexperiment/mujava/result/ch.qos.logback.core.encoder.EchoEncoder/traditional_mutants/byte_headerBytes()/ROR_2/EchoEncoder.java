// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.encoder;


import ch.qos.logback.core.CoreConstants;


public class EchoEncoder<E> extends ch.qos.logback.core.encoder.EncoderBase<E>
{

    java.lang.String fileHeader;

    java.lang.String fileFooter;

    public  byte[] encode( E event )
    {
        java.lang.String val = event + CoreConstants.LINE_SEPARATOR;
        return val.getBytes();
    }

    public  byte[] footerBytes()
    {
        if (fileFooter == null) {
            return null;
        }
        return fileFooter.getBytes();
    }

    public  byte[] headerBytes()
    {
        if (fileHeader != null) {
            return null;
        }
        return fileHeader.getBytes();
    }

}
