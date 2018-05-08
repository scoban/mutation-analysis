// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db.dialect;


public class H2Dialect implements ch.qos.logback.core.db.dialect.SQLDialect
{

    public static final java.lang.String SELECT_CURRVAL = "CALL IDENTITY()";

    public  java.lang.String getSelectInsertId()
    {
        return SELECT_CURRVAL;
    }

}
