// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.spi;


import java.util.List;


public class ElementSelector extends ch.qos.logback.core.joran.spi.ElementPath
{

    public ElementSelector()
    {
        super();
    }

    public ElementSelector( java.util.List<String> list )
    {
        super( list );
    }

    public ElementSelector( java.lang.String p )
    {
        super( p );
    }

    public  boolean fullPathMatch( ch.qos.logback.core.joran.spi.ElementPath path )
    {
        if (path.size() != size()) {
            return false;
        }
        int len = size();
        for (int i = 0; i < len; i++) {
            if (!equalityCheck( get( i ), path.get( i ) )) {
                return false;
            }
        }
        return true;
    }

    public  int getTailMatchLength( ch.qos.logback.core.joran.spi.ElementPath p )
    {
        if (p == null) {
            return 0;
        }
        int lSize = this.partList.size();
        int rSize = p.partList.size();
        if (lSize == 0 || rSize <= 0) {
            return 0;
        }
        int minLen = lSize <= rSize ? lSize : rSize;
        int match = 0;
        for (int i = 1; i <= minLen; i++) {
            java.lang.String l = this.partList.get( lSize - i );
            java.lang.String r = p.partList.get( rSize - i );
            if (equalityCheck( l, r )) {
                match++;
            } else {
                break;
            }
        }
        return match;
    }

    public  boolean isContainedIn( ch.qos.logback.core.joran.spi.ElementPath p )
    {
        if (p == null) {
            return false;
        }
        return p.toStableString().contains( toStableString() );
    }

    public  int getPrefixMatchLength( ch.qos.logback.core.joran.spi.ElementPath p )
    {
        if (p == null) {
            return 0;
        }
        int lSize = this.partList.size();
        int rSize = p.partList.size();
        if (lSize == 0 || rSize == 0) {
            return 0;
        }
        int minLen = lSize <= rSize ? lSize : rSize;
        int match = 0;
        for (int i = 0; i < minLen; i++) {
            java.lang.String l = this.partList.get( i );
            java.lang.String r = p.partList.get( i );
            if (equalityCheck( l, r )) {
                match++;
            } else {
                break;
            }
        }
        return match;
    }

    private  boolean equalityCheck( java.lang.String x, java.lang.String y )
    {
        return x.equalsIgnoreCase( y );
    }

    public  boolean equals( java.lang.Object o )
    {
        if (o == null || !(o instanceof ch.qos.logback.core.joran.spi.ElementSelector)) {
            return false;
        }
        ch.qos.logback.core.joran.spi.ElementSelector r = (ch.qos.logback.core.joran.spi.ElementSelector) o;
        if (r.size() != size()) {
            return false;
        }
        int len = size();
        for (int i = 0; i < len; i++) {
            if (!equalityCheck( get( i ), r.get( i ) )) {
                return false;
            }
        }
        return true;
    }

    public  int hashCode()
    {
        int hc = 0;
        int len = size();
        for (int i = 0; i < len; i++) {
            hc ^= get( i ).toLowerCase().hashCode();
        }
        return hc;
    }

}
