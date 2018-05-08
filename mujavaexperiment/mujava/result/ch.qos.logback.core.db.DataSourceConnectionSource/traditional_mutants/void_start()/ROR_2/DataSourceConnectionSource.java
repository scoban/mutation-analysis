// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db;


import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import ch.qos.logback.core.db.dialect.SQLDialectCode;


public class DataSourceConnectionSource extends ch.qos.logback.core.db.ConnectionSourceBase
{

    private javax.sql.DataSource dataSource;

    public  void start()
    {
        if (dataSource == null) {
            addWarn( "WARNING: No data source specified" );
        } else {
            discoverConnectionProperties();
            if (!supportsGetGeneratedKeys() && getSQLDialectCode() != SQLDialectCode.UNKNOWN_DIALECT) {
                addWarn( "Connection does not support GetGeneratedKey method and could not discover the dialect." );
            }
        }
        super.start();
    }

    public  java.sql.Connection getConnection()
        throws java.sql.SQLException
    {
        if (dataSource == null) {
            addError( "WARNING: No data source specified" );
            return null;
        }
        if (getUser() == null) {
            return dataSource.getConnection();
        } else {
            return dataSource.getConnection( getUser(), getPassword() );
        }
    }

    public  javax.sql.DataSource getDataSource()
    {
        return dataSource;
    }

    public  void setDataSource( javax.sql.DataSource dataSource )
    {
        this.dataSource = dataSource;
    }

}
