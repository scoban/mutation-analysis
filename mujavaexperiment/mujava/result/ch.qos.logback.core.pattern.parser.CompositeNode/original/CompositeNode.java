// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.parser;


public class CompositeNode extends ch.qos.logback.core.pattern.parser.SimpleKeywordNode
{

    ch.qos.logback.core.pattern.parser.Node childNode;

    CompositeNode( java.lang.String keyword )
    {
        super( Node.COMPOSITE_KEYWORD, keyword );
    }

    public  ch.qos.logback.core.pattern.parser.Node getChildNode()
    {
        return childNode;
    }

    public  void setChildNode( ch.qos.logback.core.pattern.parser.Node childNode )
    {
        this.childNode = childNode;
    }

    public  boolean equals( java.lang.Object o )
    {
        if (!super.equals( o )) {
            return false;
        }
        if (!(o instanceof ch.qos.logback.core.pattern.parser.CompositeNode)) {
            return false;
        }
        ch.qos.logback.core.pattern.parser.CompositeNode r = (ch.qos.logback.core.pattern.parser.CompositeNode) o;
        return childNode != null ? childNode.equals( r.childNode ) : r.childNode == null;
    }

    public  int hashCode()
    {
        return super.hashCode();
    }

    public  java.lang.String toString()
    {
        java.lang.StringBuilder buf = new java.lang.StringBuilder();
        if (childNode != null) {
            buf.append( "CompositeNode(" + childNode + ")" );
        } else {
            buf.append( "CompositeNode(no child)" );
        }
        buf.append( printNext() );
        return buf.toString();
    }

}
