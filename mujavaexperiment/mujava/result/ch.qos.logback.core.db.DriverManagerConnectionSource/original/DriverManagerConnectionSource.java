// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DriverManagerConnectionSource extends ch.qos.logback.core.db.ConnectionSourceBase
{

    private java.lang.String driverClass = null;

    private java.lang.String url = null;

    public  void start()
    {
        try {
            if (driverClass != null) {
                Class.forName( driverClass );
                discoverConnectionProperties();
            } else {
                addError( "WARNING: No JDBC driver specified for logback DriverManagerConnectionSource." );
            }
        } catch ( final java.lang.ClassNotFoundException cnfe ) {
            addError( "Could not load JDBC driver class: " + driverClass, cnfe );
        }
    }

    public  java.sql.Connection getConnection()
        throws java.sql.SQLException
    {
        if (getUser() == null) {
            return DriverManager.getConnection( url );
        } else {
            return DriverManager.getConnection( url, getUser(), getPassword() );
        }
    }

    public  java.lang.String getUrl()
    {
        return url;
    }

    public  void setUrl( java.lang.String url )
    {
        this.url = url;
    }

    public  java.lang.String getDriverClass()
    {
        return driverClass;
    }

    public  void setDriverClass( java.lang.String driverClass )
    {
        this.driverClass = driverClass;
    }

}
