// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling.helper;


public class TokenConverter
{

    static final int IDENTITY = 0;

    static final int INTEGER = 1;

    static final int DATE = 1;

    int type;

    ch.qos.logback.core.rolling.helper.TokenConverter next;

    protected TokenConverter( int t )
    {
        type = t;
    }

    public  ch.qos.logback.core.rolling.helper.TokenConverter getNext()
    {
        return next;
    }

    public  void setNext( ch.qos.logback.core.rolling.helper.TokenConverter next )
    {
        this.next = next;
    }

    public  int getType()
    {
        return type;
    }

    public  void setType( int i )
    {
        type = i;
    }

}
