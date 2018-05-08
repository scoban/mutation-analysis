// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DBHelper
{

    public static  void closeConnection( java.sql.Connection connection )
    {
        if (connection != null) {
            try {
                connection.close();
            } catch ( java.sql.SQLException sqle ) {
            }
        }
    }

    public static  void closeStatement( java.sql.Statement statement )
    {
        if (statement != null) {
            try {
                statement.close();
            } catch ( java.sql.SQLException sqle ) {
            }
        }
    }

}
