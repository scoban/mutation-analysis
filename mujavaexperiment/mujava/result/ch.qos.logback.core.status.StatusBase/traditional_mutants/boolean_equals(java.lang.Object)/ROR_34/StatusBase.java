// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.status;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class StatusBase implements ch.qos.logback.core.status.Status
{

    private static final java.util.List<Status> EMPTY_LIST = new java.util.ArrayList<Status>( 0 );

    int level;

    final java.lang.String message;

    final java.lang.Object origin;

    java.util.List<Status> childrenList;

    java.lang.Throwable throwable;

    long date;

    StatusBase( int level, java.lang.String msg, java.lang.Object origin )
    {
        this( level, msg, origin, null );
    }

    StatusBase( int level, java.lang.String msg, java.lang.Object origin, java.lang.Throwable t )
    {
        this.level = level;
        this.message = msg;
        this.origin = origin;
        this.throwable = t;
        this.date = System.currentTimeMillis();
    }

    public synchronized  void add( ch.qos.logback.core.status.Status child )
    {
        if (child == null) {
            throw new java.lang.NullPointerException( "Null values are not valid Status." );
        }
        if (childrenList == null) {
            childrenList = new java.util.ArrayList<Status>();
        }
        childrenList.add( child );
    }

    public synchronized  boolean hasChildren()
    {
        return childrenList != null && childrenList.size() > 0;
    }

    public synchronized  java.util.Iterator<Status> iterator()
    {
        if (childrenList != null) {
            return childrenList.iterator();
        } else {
            return EMPTY_LIST.iterator();
        }
    }

    public synchronized  boolean remove( ch.qos.logback.core.status.Status statusToRemove )
    {
        if (childrenList == null) {
            return false;
        }
        return childrenList.remove( statusToRemove );
    }

    public  int getLevel()
    {
        return level;
    }

    public synchronized  int getEffectiveLevel()
    {
        int result = level;
        int effLevel;
        java.util.Iterator<Status> it = iterator();
        ch.qos.logback.core.status.Status s;
        while (it.hasNext()) {
            s = (ch.qos.logback.core.status.Status) it.next();
            effLevel = s.getEffectiveLevel();
            if (effLevel > result) {
                result = effLevel;
            }
        }
        return result;
    }

    public  java.lang.String getMessage()
    {
        return message;
    }

    public  java.lang.Object getOrigin()
    {
        return origin;
    }

    public  java.lang.Throwable getThrowable()
    {
        return throwable;
    }

    public  java.lang.Long getDate()
    {
        return date;
    }

    public  java.lang.String toString()
    {
        java.lang.StringBuilder buf = new java.lang.StringBuilder();
        switch (getEffectiveLevel()) {
        case INFO :
            buf.append( "INFO" );
            break;

        case WARN :
            buf.append( "WARN" );
            break;

        case ERROR :
            buf.append( "ERROR" );
            break;

        }
        if (origin != null) {
            buf.append( " in " );
            buf.append( origin );
            buf.append( " -" );
        }
        buf.append( " " );
        buf.append( message );
        if (throwable != null) {
            buf.append( " " );
            buf.append( throwable );
        }
        return buf.toString();
    }

    public  int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + level;
        result = prime * result + (message == null ? 0 : message.hashCode());
        return result;
    }

    public  boolean equals( java.lang.Object obj )
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ch.qos.logback.core.status.StatusBase other = (ch.qos.logback.core.status.StatusBase) obj;
        if (level != other.level) {
            return false;
        }
        if (message == null) {
            if (other.message == null) {
                return false;
            }
        } else {
            if (!message.equals( other.message )) {
                return false;
            }
        }
        return true;
    }

}
