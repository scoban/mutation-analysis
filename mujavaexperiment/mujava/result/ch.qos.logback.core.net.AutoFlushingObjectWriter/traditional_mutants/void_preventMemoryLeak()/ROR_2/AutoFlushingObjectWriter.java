// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import java.io.IOException;
import java.io.ObjectOutputStream;


public class AutoFlushingObjectWriter implements ch.qos.logback.core.net.ObjectWriter
{

    private final java.io.ObjectOutputStream objectOutputStream;

    private final int resetFrequency;

    private int writeCounter = 0;

    public AutoFlushingObjectWriter( java.io.ObjectOutputStream objectOutputStream, int resetFrequency )
    {
        this.objectOutputStream = objectOutputStream;
        this.resetFrequency = resetFrequency;
    }

    public  void write( java.lang.Object object )
        throws java.io.IOException
    {
        objectOutputStream.writeObject( object );
        objectOutputStream.flush();
        preventMemoryLeak();
    }

    private  void preventMemoryLeak()
        throws java.io.IOException
    {
        if (++writeCounter < resetFrequency) {
            objectOutputStream.reset();
            writeCounter = 0;
        }
    }

}
