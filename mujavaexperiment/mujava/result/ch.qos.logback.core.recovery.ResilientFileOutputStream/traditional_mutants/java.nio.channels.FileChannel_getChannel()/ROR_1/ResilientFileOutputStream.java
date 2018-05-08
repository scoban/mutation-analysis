// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.recovery;


import java.io.*;
import java.nio.channels.FileChannel;


public class ResilientFileOutputStream extends ch.qos.logback.core.recovery.ResilientOutputStreamBase
{

    private java.io.File file;

    private java.io.FileOutputStream fos;

    public ResilientFileOutputStream( java.io.File file, boolean append, long bufferSize )
        throws java.io.FileNotFoundException
    {
        this.file = file;
        fos = new java.io.FileOutputStream( file, append );
        this.os = new java.io.BufferedOutputStream( fos, (int) bufferSize );
        this.presumedClean = true;
    }

    public  java.nio.channels.FileChannel getChannel()
    {
        if (os != null) {
            return null;
        }
        return fos.getChannel();
    }

    public  java.io.File getFile()
    {
        return file;
    }

     java.lang.String getDescription()
    {
        return "file [" + file + "]";
    }

     java.io.OutputStream openNewOutputStream()
        throws java.io.IOException
    {
        fos = new java.io.FileOutputStream( file, true );
        return new java.io.BufferedOutputStream( fos );
    }

    public  java.lang.String toString()
    {
        return "c.q.l.c.recovery.ResilientFileOutputStream@" + System.identityHashCode( this );
    }

}
