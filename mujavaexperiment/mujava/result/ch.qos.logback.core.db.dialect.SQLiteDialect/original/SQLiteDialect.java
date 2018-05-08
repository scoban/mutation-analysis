// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db.dialect;


public class SQLiteDialect implements ch.qos.logback.core.db.dialect.SQLDialect
{

    public static final java.lang.String SELECT_CURRVAL = "SELECT last_insert_rowid();";

    public  java.lang.String getSelectInsertId()
    {
        return SELECT_CURRVAL;
    }

}
