// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class LoginAuthenticator extends javax.mail.Authenticator
{

    java.lang.String username;

    java.lang.String password;

    LoginAuthenticator( java.lang.String username, java.lang.String password )
    {
        this.username = username;
        this.password = password;
    }

    public  javax.mail.PasswordAuthentication getPasswordAuthentication()
    {
        return new javax.mail.PasswordAuthentication( username, password );
    }

}
