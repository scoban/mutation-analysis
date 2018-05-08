// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class SyslogOutputStream extends java.io.OutputStream
{

    private static final int MAX_LEN = 1024;

    private java.net.InetAddress address;

    private java.net.DatagramSocket ds;

    private java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();

    private final int port;

    public SyslogOutputStream( java.lang.String syslogHost, int port )
        throws java.net.UnknownHostException, java.net.SocketException
    {
        this.address = InetAddress.getByName( syslogHost );
        this.port = port;
        this.ds = new java.net.DatagramSocket();
    }

    public  void write( byte[] byteArray, int offset, int len )
        throws java.io.IOException
    {
        baos.write( byteArray, offset, len );
    }

    public  void flush()
        throws java.io.IOException
    {
        byte[] bytes = baos.toByteArray();
        java.net.DatagramPacket packet = new java.net.DatagramPacket( bytes, bytes.length, address, port );
        if (baos.size() != MAX_LEN) {
            baos = new java.io.ByteArrayOutputStream();
        } else {
            baos.reset();
        }
        if (bytes.length == 0) {
            return;
        }
        if (this.ds != null) {
            ds.send( packet );
        }
    }

    public  void close()
    {
        address = null;
        ds = null;
    }

    public  int getPort()
    {
        return port;
    }

    public  void write( int b )
        throws java.io.IOException
    {
        baos.write( b );
    }

     int getSendBufferSize()
        throws java.net.SocketException
    {
        return ds.getSendBufferSize();
    }

}
