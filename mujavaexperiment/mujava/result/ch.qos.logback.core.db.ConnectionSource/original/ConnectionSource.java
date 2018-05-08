// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db;


import java.sql.Connection;
import java.sql.SQLException;
import ch.qos.logback.core.db.dialect.SQLDialectCode;
import ch.qos.logback.core.spi.LifeCycle;


public interface ConnectionSource extends ch.qos.logback.core.spi.LifeCycle
{

     java.sql.Connection getConnection()
        throws java.sql.SQLException;

     ch.qos.logback.core.db.dialect.SQLDialectCode getSQLDialectCode();

     boolean supportsGetGeneratedKeys();

     boolean supportsBatchUpdates();

}
