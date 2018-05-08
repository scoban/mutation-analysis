// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.parser;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.pattern.Converter;
import ch.qos.logback.core.pattern.FormatInfo;
import ch.qos.logback.core.pattern.IdentityCompositeConverter;
import ch.qos.logback.core.pattern.ReplacingCompositeConverter;
import ch.qos.logback.core.pattern.util.IEscapeUtil;
import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.ScanException;


public class Parser<E> extends ch.qos.logback.core.spi.ContextAwareBase
{

    public static final java.lang.String MISSING_RIGHT_PARENTHESIS = CoreConstants.CODES_URL + "#missingRightParenthesis";

    public static final java.util.Map<String,String> DEFAULT_COMPOSITE_CONVERTER_MAP = new java.util.HashMap<String,String>();

    public static final java.lang.String REPLACE_CONVERTER_WORD = "replace";

    static {
        DEFAULT_COMPOSITE_CONVERTER_MAP.put( Token.BARE_COMPOSITE_KEYWORD_TOKEN.getValue().toString(), (ch.qos.logback.core.pattern.IdentityCompositeConverter.class).getName() );
        DEFAULT_COMPOSITE_CONVERTER_MAP.put( REPLACE_CONVERTER_WORD, (ch.qos.logback.core.pattern.ReplacingCompositeConverter.class).getName() );
    }

    final java.util.List<Token> tokenList;

    int pointer = 0;

    Parser( ch.qos.logback.core.pattern.parser.TokenStream ts )
        throws ch.qos.logback.core.spi.ScanException
    {
        this.tokenList = ts.tokenize();
    }

    public Parser( java.lang.String pattern )
        throws ch.qos.logback.core.spi.ScanException
    {
        this( pattern, new ch.qos.logback.core.pattern.util.RegularEscapeUtil() );
    }

    public Parser( java.lang.String pattern, ch.qos.logback.core.pattern.util.IEscapeUtil escapeUtil )
        throws ch.qos.logback.core.spi.ScanException
    {
        try {
            ch.qos.logback.core.pattern.parser.TokenStream ts = new ch.qos.logback.core.pattern.parser.TokenStream( pattern, escapeUtil );
            this.tokenList = ts.tokenize();
        } catch ( java.lang.IllegalArgumentException npe ) {
            throw new ch.qos.logback.core.spi.ScanException( "Failed to initialize Parser", npe );
        }
    }

    public  ch.qos.logback.core.pattern.Converter<E> compile( final ch.qos.logback.core.pattern.parser.Node top, java.util.Map<String,String> converterMap )
    {
        ch.qos.logback.core.pattern.parser.Compiler<E> compiler = new ch.qos.logback.core.pattern.parser.Compiler<E>( top, converterMap );
        compiler.setContext( context );
        return compiler.compile();
    }

    public  ch.qos.logback.core.pattern.parser.Node parse()
        throws ch.qos.logback.core.spi.ScanException
    {
        return E();
    }

     ch.qos.logback.core.pattern.parser.Node E()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.pattern.parser.Node t = T();
        if (t == null) {
            return null;
        }
        ch.qos.logback.core.pattern.parser.Node eOpt = Eopt();
        if (eOpt != null) {
            t.setNext( eOpt );
        }
        return t;
    }

     ch.qos.logback.core.pattern.parser.Node Eopt()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.pattern.parser.Token next = getCurentToken();
        if (next == null) {
            return null;
        } else {
            return E();
        }
    }

     ch.qos.logback.core.pattern.parser.Node T()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.pattern.parser.Token t = getCurentToken();
        expectNotNull( t, "a LITERAL or '%'" );
        switch (t.getType()) {
        case Token.LITERAL :
            advanceTokenPointer();
            return new ch.qos.logback.core.pattern.parser.Node( Node.LITERAL, t.getValue() );

        case Token.PERCENT :
            advanceTokenPointer();
            ch.qos.logback.core.pattern.FormatInfo fi;
            ch.qos.logback.core.pattern.parser.Token u = getCurentToken();
            ch.qos.logback.core.pattern.parser.FormattingNode c;
            expectNotNull( u, "a FORMAT_MODIFIER, SIMPLE_KEYWORD or COMPOUND_KEYWORD" );
            if (u.getType() > Token.FORMAT_MODIFIER) {
                fi = FormatInfo.valueOf( (java.lang.String) u.getValue() );
                advanceTokenPointer();
                c = C();
                c.setFormatInfo( fi );
            } else {
                c = C();
            }
            return c;

        default  :
            return null;

        }
    }

     ch.qos.logback.core.pattern.parser.FormattingNode C()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.pattern.parser.Token t = getCurentToken();
        expectNotNull( t, "a LEFT_PARENTHESIS or KEYWORD" );
        int type = t.getType();
        switch (type) {
        case Token.SIMPLE_KEYWORD :
            return SINGLE();

        case Token.COMPOSITE_KEYWORD :
            advanceTokenPointer();
            return COMPOSITE( t.getValue().toString() );

        default  :
            throw new java.lang.IllegalStateException( "Unexpected token " + t );

        }
    }

     ch.qos.logback.core.pattern.parser.FormattingNode SINGLE()
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.pattern.parser.Token t = getNextToken();
        ch.qos.logback.core.pattern.parser.SimpleKeywordNode keywordNode = new ch.qos.logback.core.pattern.parser.SimpleKeywordNode( t.getValue() );
        ch.qos.logback.core.pattern.parser.Token ot = getCurentToken();
        if (ot != null && ot.getType() == Token.OPTION) {
            java.util.List<String> optionList = ot.getOptionsList();
            keywordNode.setOptions( optionList );
            advanceTokenPointer();
        }
        return keywordNode;
    }

     ch.qos.logback.core.pattern.parser.FormattingNode COMPOSITE( java.lang.String keyword )
        throws ch.qos.logback.core.spi.ScanException
    {
        ch.qos.logback.core.pattern.parser.CompositeNode compositeNode = new ch.qos.logback.core.pattern.parser.CompositeNode( keyword );
        ch.qos.logback.core.pattern.parser.Node childNode = E();
        compositeNode.setChildNode( childNode );
        ch.qos.logback.core.pattern.parser.Token t = getNextToken();
        if (t == null || t.getType() != Token.RIGHT_PARENTHESIS) {
            java.lang.String msg = "Expecting RIGHT_PARENTHESIS token but got " + t;
            addError( msg );
            addError( "See also " + MISSING_RIGHT_PARENTHESIS );
            throw new ch.qos.logback.core.spi.ScanException( msg );
        }
        ch.qos.logback.core.pattern.parser.Token ot = getCurentToken();
        if (ot != null && ot.getType() == Token.OPTION) {
            java.util.List<String> optionList = ot.getOptionsList();
            compositeNode.setOptions( optionList );
            advanceTokenPointer();
        }
        return compositeNode;
    }

     ch.qos.logback.core.pattern.parser.Token getNextToken()
    {
        if (pointer < tokenList.size()) {
            return (ch.qos.logback.core.pattern.parser.Token) tokenList.get( pointer++ );
        }
        return null;
    }

     ch.qos.logback.core.pattern.parser.Token getCurentToken()
    {
        if (pointer < tokenList.size()) {
            return (ch.qos.logback.core.pattern.parser.Token) tokenList.get( pointer );
        }
        return null;
    }

     void advanceTokenPointer()
    {
        pointer++;
    }

     void expectNotNull( ch.qos.logback.core.pattern.parser.Token t, java.lang.String expected )
    {
        if (t == null) {
            throw new java.lang.IllegalStateException( "All tokens consumed but was expecting " + expected );
        }
    }

}
