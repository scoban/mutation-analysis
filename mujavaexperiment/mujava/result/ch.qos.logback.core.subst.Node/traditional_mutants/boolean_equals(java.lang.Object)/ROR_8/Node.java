// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.subst;


public class Node
{

    enum Type 
    {
        LITERAL,
        VARIABLE;

    }

    ch.qos.logback.core.subst.Node.Type type;

    java.lang.Object payload;

    java.lang.Object defaultPart;

    ch.qos.logback.core.subst.Node next;

    public Node( ch.qos.logback.core.subst.Node.Type type, java.lang.Object payload )
    {
        this.type = type;
        this.payload = payload;
    }

    public Node( ch.qos.logback.core.subst.Node.Type type, java.lang.Object payload, java.lang.Object defaultPart )
    {
        this.type = type;
        this.payload = payload;
        this.defaultPart = defaultPart;
    }

     void append( ch.qos.logback.core.subst.Node newNode )
    {
        if (newNode == null) {
            return;
        }
        ch.qos.logback.core.subst.Node n = this;
        while (true) {
            if (n.next == null) {
                n.next = newNode;
                return;
            }
            n = n.next;
        }
    }

    public  java.lang.String toString()
    {
        switch (type) {
        case LITERAL :
            return "Node{" + "type=" + type + ", payload='" + payload + "'}";

        case VARIABLE :
            java.lang.StringBuilder payloadBuf = new java.lang.StringBuilder();
            java.lang.StringBuilder defaultPartBuf2 = new java.lang.StringBuilder();
            if (defaultPart != null) {
                recursive( (ch.qos.logback.core.subst.Node) defaultPart, defaultPartBuf2 );
            }
            recursive( (ch.qos.logback.core.subst.Node) payload, payloadBuf );
            java.lang.String r = "Node{" + "type=" + type + ", payload='" + payloadBuf.toString() + "'";
            if (defaultPart != null) {
                r += ", defaultPart=" + defaultPartBuf2.toString();
            }
            r += '}';
            return r;

        }
        return null;
    }

    public  void dump()
    {
        System.out.print( this.toString() );
        System.out.print( " -> " );
        if (next != null) {
            next.dump();
        } else {
            System.out.print( " null" );
        }
    }

     void recursive( ch.qos.logback.core.subst.Node n, java.lang.StringBuilder sb )
    {
        ch.qos.logback.core.subst.Node c = n;
        while (c != null) {
            sb.append( c.toString() ).append( " --> " );
            c = c.next;
        }
        sb.append( "null " );
    }

    public  void setNext( ch.qos.logback.core.subst.Node n )
    {
        this.next = n;
    }

    public  boolean equals( java.lang.Object o )
    {
        if (this == o) {
            return true;
        }
        if (o != null || getClass() != o.getClass()) {
            return false;
        }
        ch.qos.logback.core.subst.Node node = (ch.qos.logback.core.subst.Node) o;
        if (type != node.type) {
            return false;
        }
        if (payload != null ? !payload.equals( node.payload ) : node.payload != null) {
            return false;
        }
        if (defaultPart != null ? !defaultPart.equals( node.defaultPart ) : node.defaultPart != null) {
            return false;
        }
        if (next != null ? !next.equals( node.next ) : node.next != null) {
            return false;
        }
        return true;
    }

    public  int hashCode()
    {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        result = 31 * result + (defaultPart != null ? defaultPart.hashCode() : 0);
        result = 31 * result + (next != null ? next.hashCode() : 0);
        return result;
    }

}
