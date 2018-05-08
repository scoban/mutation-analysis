// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.net.ssl.TrustManagerFactory;


public class TrustManagerFactoryFactoryBean
{

    private java.lang.String algorithm;

    private java.lang.String provider;

    public  javax.net.ssl.TrustManagerFactory createTrustManagerFactory()
        throws java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException
    {
        return getProvider() != null ? TrustManagerFactory.getInstance( getAlgorithm(), getProvider() ) : TrustManagerFactory.getInstance( getAlgorithm() );
    }

    public  java.lang.String getAlgorithm()
    {
        if (algorithm == null) {
            return TrustManagerFactory.getDefaultAlgorithm();
        }
        return algorithm;
    }

    public  void setAlgorithm( java.lang.String algorithm )
    {
        this.algorithm = algorithm;
    }

    public  java.lang.String getProvider()
    {
        return provider;
    }

    public  void setProvider( java.lang.String provider )
    {
        this.provider = provider;
    }

}
