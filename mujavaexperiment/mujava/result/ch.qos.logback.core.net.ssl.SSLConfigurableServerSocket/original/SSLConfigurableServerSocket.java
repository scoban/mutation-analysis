// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import javax.net.ssl.SSLServerSocket;


public class SSLConfigurableServerSocket implements ch.qos.logback.core.net.ssl.SSLConfigurable
{

    private final javax.net.ssl.SSLServerSocket delegate;

    public SSLConfigurableServerSocket( javax.net.ssl.SSLServerSocket delegate )
    {
        this.delegate = delegate;
    }

    public  java.lang.String[] getDefaultProtocols()
    {
        return delegate.getEnabledProtocols();
    }

    public  java.lang.String[] getSupportedProtocols()
    {
        return delegate.getSupportedProtocols();
    }

    public  void setEnabledProtocols( java.lang.String[] protocols )
    {
        delegate.setEnabledProtocols( protocols );
    }

    public  java.lang.String[] getDefaultCipherSuites()
    {
        return delegate.getEnabledCipherSuites();
    }

    public  java.lang.String[] getSupportedCipherSuites()
    {
        return delegate.getSupportedCipherSuites();
    }

    public  void setEnabledCipherSuites( java.lang.String[] suites )
    {
        delegate.setEnabledCipherSuites( suites );
    }

    public  void setNeedClientAuth( boolean state )
    {
        delegate.setNeedClientAuth( state );
    }

    public  void setWantClientAuth( boolean state )
    {
        delegate.setWantClientAuth( state );
    }

}
