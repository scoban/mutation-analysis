// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import ch.qos.logback.core.CoreConstants;


public class ObjectWriterFactory
{

    public  ch.qos.logback.core.net.AutoFlushingObjectWriter newAutoFlushingObjectWriter( java.io.OutputStream outputStream )
        throws java.io.IOException
    {
        return new ch.qos.logback.core.net.AutoFlushingObjectWriter( new java.io.ObjectOutputStream( outputStream ), CoreConstants.OOS_RESET_FREQUENCY );
    }

}
