// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.subst;


import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.spi.ScanException;
import java.util.List;


public class Parser
{

    final java.util.List<Token> tokenList;

    int pointer = 0;

    public Parser( java.util.List<Token> tokenList )
    {
        this.tokenList = tokenList;
    }

    public  ch.qos.logback.core.subst.Node parse()
        throws ch.qos.logback.core.spi.ScanException
    {
        if (tokenList == null || tokenList.isEmpty()) {
            return null;
        }
        return E();
    }

    private  ch.qos.logback.core.subst.Node E()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.subst.Node t = T();
        if (t == null) {
            return null;
        }
        ch.qos.logback.core.subst.Node eOpt = Eopt();
        if (eOpt != null) {
            t.append( eOpt );
        }
        return t;
    }

    private  ch.qos.logback.core.subst.Node Eopt()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.subst.Token next = peekAtCurentToken();
        if (next == null) {
            return null;
        } else {
            return E();
        }
    }

    private  ch.qos.logback.core.subst.Node T()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.subst.Token t = peekAtCurentToken();
        switch (t.type) {
        case LITERAL :
            advanceTokenPointer();
            return makeNewLiteralNode( t.payload );

        case CURLY_LEFT :
            advanceTokenPointer();
            ch.qos.logback.core.subst.Node innerNode = C();
            ch.qos.logback.core.subst.Token right = peekAtCurentToken();
            expectCurlyRight( right );
            advanceTokenPointer();
            ch.qos.logback.core.subst.Node curlyLeft = makeNewLiteralNode( CoreConstants.LEFT_ACCOLADE );
            curlyLeft.append( innerNode );
            curlyLeft.append( makeNewLiteralNode( CoreConstants.RIGHT_ACCOLADE ) );
            return curlyLeft;

        case START :
            advanceTokenPointer();
            ch.qos.logback.core.subst.Node v = V();
            ch.qos.logback.core.subst.Token w = peekAtCurentToken();
            expectCurlyRight( w );
            advanceTokenPointer();
            return v;

        default  :
            return null;

        }
    }

    private  ch.qos.logback.core.subst.Node makeNewLiteralNode( java.lang.String s )
    {
        return new ch.qos.logback.core.subst.Node( Node.Type.LITERAL, s );
    }

    private  ch.qos.logback.core.subst.Node V()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.subst.Node e = E();
        ch.qos.logback.core.subst.Node variable = new ch.qos.logback.core.subst.Node( Node.Type.VARIABLE, e );
        ch.qos.logback.core.subst.Token t = peekAtCurentToken();
        if (isDefaultToken( t )) {
            advanceTokenPointer();
            ch.qos.logback.core.subst.Node def = E();
            variable.defaultPart = def;
        }
        return variable;
    }

    private  ch.qos.logback.core.subst.Node C()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.subst.Node e0 = E();
        ch.qos.logback.core.subst.Token t = peekAtCurentToken();
        if (isDefaultToken( t )) {
            advanceTokenPointer();
            ch.qos.logback.core.subst.Node literal = makeNewLiteralNode( CoreConstants.DEFAULT_VALUE_SEPARATOR );
            e0.append( literal );
            ch.qos.logback.core.subst.Node e1 = E();
            e0.append( e1 );
        }
        return e0;
    }

    private  boolean isDefaultToken( ch.qos.logback.core.subst.Token t )
    {
        return t != null && t.type == Token.Type.DEFAULT;
    }

     void advanceTokenPointer()
    {
        pointer++;
    }

     void expectNotNull( ch.qos.logback.core.subst.Token t, java.lang.String expected )
    {
        if (t == null) {
            throw new java.lang.IllegalArgumentException( "All tokens consumed but was expecting \"" + expected + "\"" );
        }
    }

     void expectCurlyRight( ch.qos.logback.core.subst.Token t )
        throws ch.qos.logback.core.spi.ScanException
    {
        expectNotNull( t, "}" );
        if (t.type != Token.Type.CURLY_RIGHT) {
            throw new ch.qos.logback.core.spi.ScanException( "Expecting }" );
        }
    }

     ch.qos.logback.core.subst.Token peekAtCurentToken()
    {
        if (false) {
            return tokenList.get( pointer );
        }
        return null;
    }

}
