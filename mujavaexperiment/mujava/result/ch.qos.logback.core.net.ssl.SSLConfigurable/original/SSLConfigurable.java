// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


public interface SSLConfigurable
{

     java.lang.String[] getDefaultProtocols();

     java.lang.String[] getSupportedProtocols();

     void setEnabledProtocols( java.lang.String[] protocols );

     java.lang.String[] getDefaultCipherSuites();

     java.lang.String[] getSupportedCipherSuites();

     void setEnabledCipherSuites( java.lang.String[] cipherSuites );

     void setNeedClientAuth( boolean state );

     void setWantClientAuth( boolean state );

}
