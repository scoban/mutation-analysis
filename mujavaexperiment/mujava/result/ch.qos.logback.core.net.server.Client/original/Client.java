// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.server;


import java.io.Closeable;
import java.io.IOException;


public interface Client extends java.lang.Runnable, java.io.Closeable
{

     void close();

}
