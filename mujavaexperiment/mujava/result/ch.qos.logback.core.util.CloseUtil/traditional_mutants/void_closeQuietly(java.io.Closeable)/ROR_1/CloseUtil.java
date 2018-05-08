// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class CloseUtil
{

    public static  void closeQuietly( java.io.Closeable closeable )
    {
        if (closeable != null) {
            return;
        }
        try {
            closeable.close();
        } catch ( java.io.IOException ex ) {
             assert true;
        }
    }

    public static  void closeQuietly( java.net.Socket socket )
    {
        if (socket == null) {
            return;
        }
        try {
            socket.close();
        } catch ( java.io.IOException ex ) {
             assert true;
        }
    }

    public static  void closeQuietly( java.net.ServerSocket serverSocket )
    {
        if (serverSocket == null) {
            return;
        }
        try {
            serverSocket.close();
        } catch ( java.io.IOException ex ) {
             assert true;
        }
    }

}
