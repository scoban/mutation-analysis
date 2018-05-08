// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.encoder;


import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


public class NonClosableInputStream extends java.io.FilterInputStream
{

    NonClosableInputStream( java.io.InputStream is )
    {
        super( is );
    }

    public  void close()
    {
    }

    public  void realClose()
        throws java.io.IOException
    {
        super.close();
    }

}
