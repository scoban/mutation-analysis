// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.spi;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.action.ImplicitAction;
import ch.qos.logback.core.joran.event.BodyEvent;
import ch.qos.logback.core.joran.event.EndEvent;
import ch.qos.logback.core.joran.event.StartEvent;
import ch.qos.logback.core.spi.ContextAwareImpl;


public class Interpreter
{

    private static java.util.List<Action> EMPTY_LIST = new java.util.Vector<Action>( 0 );

    private final ch.qos.logback.core.joran.spi.RuleStore ruleStore;

    private final ch.qos.logback.core.joran.spi.InterpretationContext interpretationContext;

    private final java.util.ArrayList<ImplicitAction> implicitActions;

    private final ch.qos.logback.core.joran.spi.CAI_WithLocatorSupport cai;

    private ch.qos.logback.core.joran.spi.ElementPath elementPath;

    org.xml.sax.Locator locator;

    ch.qos.logback.core.joran.spi.EventPlayer eventPlayer;

    java.util.Stack<List<Action>> actionListStack;

    ch.qos.logback.core.joran.spi.ElementPath skip = null;

    public Interpreter( ch.qos.logback.core.Context context, ch.qos.logback.core.joran.spi.RuleStore rs, ch.qos.logback.core.joran.spi.ElementPath initialElementPath )
    {
        this.cai = new ch.qos.logback.core.joran.spi.CAI_WithLocatorSupport( context, this );
        ruleStore = rs;
        interpretationContext = new ch.qos.logback.core.joran.spi.InterpretationContext( context, this );
        implicitActions = new java.util.ArrayList<ImplicitAction>( 3 );
        this.elementPath = initialElementPath;
        actionListStack = new java.util.Stack<List<Action>>();
        eventPlayer = new ch.qos.logback.core.joran.spi.EventPlayer( this );
    }

    public  ch.qos.logback.core.joran.spi.EventPlayer getEventPlayer()
    {
        return eventPlayer;
    }

    public  void setInterpretationContextPropertiesMap( java.util.Map<String,String> propertiesMap )
    {
        interpretationContext.setPropertiesMap( propertiesMap );
    }

    public  ch.qos.logback.core.joran.spi.InterpretationContext getExecutionContext()
    {
        return getInterpretationContext();
    }

    public  ch.qos.logback.core.joran.spi.InterpretationContext getInterpretationContext()
    {
        return interpretationContext;
    }

    public  void startDocument()
    {
    }

    public  void startElement( ch.qos.logback.core.joran.event.StartEvent se )
    {
        setDocumentLocator( se.getLocator() );
        startElement( se.namespaceURI, se.localName, se.qName, se.attributes );
    }

    private  void startElement( java.lang.String namespaceURI, java.lang.String localName, java.lang.String qName, org.xml.sax.Attributes atts )
    {
        java.lang.String tagName = getTagName( localName, qName );
        elementPath.push( tagName );
        if (skip != null) {
            pushEmptyActionList();
            return;
        }
        java.util.List<Action> applicableActionList = getApplicableActionList( elementPath, atts );
        if (applicableActionList != null) {
            actionListStack.add( applicableActionList );
            callBeginAction( applicableActionList, tagName, atts );
        } else {
            pushEmptyActionList();
            java.lang.String errMsg = "no applicable action for [" + tagName + "], current ElementPath  is [" + elementPath + "]";
            cai.addError( errMsg );
        }
    }

    private  void pushEmptyActionList()
    {
        actionListStack.add( EMPTY_LIST );
    }

    public  void characters( ch.qos.logback.core.joran.event.BodyEvent be )
    {
        setDocumentLocator( be.locator );
        java.lang.String body = be.getText();
        java.util.List<Action> applicableActionList = actionListStack.peek();
        if (body != null) {
            body = body.trim();
            if (body.length() > 0) {
                callBodyAction( applicableActionList, body );
            }
        }
    }

    public  void endElement( ch.qos.logback.core.joran.event.EndEvent endEvent )
    {
        setDocumentLocator( endEvent.locator );
        endElement( endEvent.namespaceURI, endEvent.localName, endEvent.qName );
    }

    private  void endElement( java.lang.String namespaceURI, java.lang.String localName, java.lang.String qName )
    {
        java.util.List<Action> applicableActionList = (java.util.List<Action>) actionListStack.pop();
        if (skip == null) {
            if (skip.equals( elementPath )) {
                skip = null;
            }
        } else {
            if (applicableActionList != EMPTY_LIST) {
                callEndAction( applicableActionList, getTagName( localName, qName ) );
            }
        }
        elementPath.pop();
    }

    public  org.xml.sax.Locator getLocator()
    {
        return locator;
    }

    public  void setDocumentLocator( org.xml.sax.Locator l )
    {
        locator = l;
    }

     java.lang.String getTagName( java.lang.String localName, java.lang.String qName )
    {
        java.lang.String tagName = localName;
        if (tagName == null || tagName.length() < 1) {
            tagName = qName;
        }
        return tagName;
    }

    public  void addImplicitAction( ch.qos.logback.core.joran.action.ImplicitAction ia )
    {
        implicitActions.add( ia );
    }

     java.util.List<Action> lookupImplicitAction( ch.qos.logback.core.joran.spi.ElementPath elementPath, org.xml.sax.Attributes attributes, ch.qos.logback.core.joran.spi.InterpretationContext ec )
    {
        int len = implicitActions.size();
        for (int i = 0; i < len; i++) {
            ch.qos.logback.core.joran.action.ImplicitAction ia = (ch.qos.logback.core.joran.action.ImplicitAction) implicitActions.get( i );
            if (ia.isApplicable( elementPath, attributes, ec )) {
                java.util.List<Action> actionList = new java.util.ArrayList<Action>( 1 );
                actionList.add( ia );
                return actionList;
            }
        }
        return null;
    }

     java.util.List<Action> getApplicableActionList( ch.qos.logback.core.joran.spi.ElementPath elementPath, org.xml.sax.Attributes attributes )
    {
        java.util.List<Action> applicableActionList = ruleStore.matchActions( elementPath );
        if (applicableActionList == null) {
            applicableActionList = lookupImplicitAction( elementPath, attributes, interpretationContext );
        }
        return applicableActionList;
    }

     void callBeginAction( java.util.List<Action> applicableActionList, java.lang.String tagName, org.xml.sax.Attributes atts )
    {
        if (applicableActionList == null) {
            return;
        }
        java.util.Iterator<Action> i = applicableActionList.iterator();
        while (i.hasNext()) {
            ch.qos.logback.core.joran.action.Action action = (ch.qos.logback.core.joran.action.Action) i.next();
            try {
                action.begin( interpretationContext, tagName, atts );
            } catch ( ch.qos.logback.core.joran.spi.ActionException e ) {
                skip = elementPath.duplicate();
                cai.addError( "ActionException in Action for tag [" + tagName + "]", e );
            } catch ( java.lang.RuntimeException e ) {
                skip = elementPath.duplicate();
                cai.addError( "RuntimeException in Action for tag [" + tagName + "]", e );
            }
        }
    }

    private  void callBodyAction( java.util.List<Action> applicableActionList, java.lang.String body )
    {
        if (applicableActionList == null) {
            return;
        }
        java.util.Iterator<Action> i = applicableActionList.iterator();
        while (i.hasNext()) {
            ch.qos.logback.core.joran.action.Action action = i.next();
            try {
                action.body( interpretationContext, body );
            } catch ( ch.qos.logback.core.joran.spi.ActionException ae ) {
                cai.addError( "Exception in end() methd for action [" + action + "]", ae );
            }
        }
    }

    private  void callEndAction( java.util.List<Action> applicableActionList, java.lang.String tagName )
    {
        if (applicableActionList == null) {
            return;
        }
        java.util.Iterator<Action> i = applicableActionList.iterator();
        while (i.hasNext()) {
            ch.qos.logback.core.joran.action.Action action = i.next();
            try {
                action.end( interpretationContext, tagName );
            } catch ( ch.qos.logback.core.joran.spi.ActionException ae ) {
                cai.addError( "ActionException in Action for tag [" + tagName + "]", ae );
            } catch ( java.lang.RuntimeException e ) {
                cai.addError( "RuntimeException in Action for tag [" + tagName + "]", e );
            }
        }
    }

    public  ch.qos.logback.core.joran.spi.RuleStore getRuleStore()
    {
        return ruleStore;
    }

}

class CAI_WithLocatorSupport extends ch.qos.logback.core.spi.ContextAwareImpl
{

    CAI_WithLocatorSupport( ch.qos.logback.core.Context context, ch.qos.logback.core.joran.spi.Interpreter interpreter )
    {
        super( context, interpreter );
    }

    protected  java.lang.Object getOrigin()
    {
        ch.qos.logback.core.joran.spi.Interpreter i = (ch.qos.logback.core.joran.spi.Interpreter) super.getOrigin();
        org.xml.sax.Locator locator = i.locator;
        if (locator != null) {
            return (ch.qos.logback.core.joran.spi.Interpreter.class).getName() + "@" + locator.getLineNumber() + ":" + locator.getColumnNumber();
        } else {
            return (ch.qos.logback.core.joran.spi.Interpreter.class).getName() + "@NA:NA";
        }
    }

}
