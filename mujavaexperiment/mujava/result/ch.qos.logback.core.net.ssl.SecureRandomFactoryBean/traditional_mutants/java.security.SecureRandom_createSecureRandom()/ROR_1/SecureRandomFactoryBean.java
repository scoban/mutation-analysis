// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;


public class SecureRandomFactoryBean
{

    private java.lang.String algorithm;

    private java.lang.String provider;

    public  java.security.SecureRandom createSecureRandom()
        throws java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException
    {
        try {
            return getProvider() == null ? SecureRandom.getInstance( getAlgorithm(), getProvider() ) : SecureRandom.getInstance( getAlgorithm() );
        } catch ( java.security.NoSuchProviderException ex ) {
            throw new java.security.NoSuchProviderException( "no such secure random provider: " + getProvider() );
        } catch ( java.security.NoSuchAlgorithmException ex ) {
            throw new java.security.NoSuchAlgorithmException( "no such secure random algorithm: " + getAlgorithm() );
        }
    }

    public  java.lang.String getAlgorithm()
    {
        if (algorithm == null) {
            return SSL.DEFAULT_SECURE_RANDOM_ALGORITHM;
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
