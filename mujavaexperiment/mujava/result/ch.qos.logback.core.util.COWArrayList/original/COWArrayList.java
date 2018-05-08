// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class COWArrayList<E> implements java.util.List<E>
{

    java.util.concurrent.atomic.AtomicBoolean fresh = new java.util.concurrent.atomic.AtomicBoolean( false );

    java.util.concurrent.CopyOnWriteArrayList<E> underlyingList = new java.util.concurrent.CopyOnWriteArrayList<E>();

    E[] ourCopy;

    final E[] modelArray;

    public COWArrayList( E[] modelArray )
    {
        this.modelArray = modelArray;
    }

    public  int size()
    {
        return underlyingList.size();
    }

    public  boolean isEmpty()
    {
        return underlyingList.isEmpty();
    }

    public  boolean contains( java.lang.Object o )
    {
        return underlyingList.contains( o );
    }

    public  java.util.Iterator<E> iterator()
    {
        return underlyingList.iterator();
    }

    private  void refreshCopyIfNecessary()
    {
        if (!isFresh()) {
            refreshCopy();
        }
    }

    private  boolean isFresh()
    {
        return fresh.get();
    }

    private  void refreshCopy()
    {
        ourCopy = underlyingList.toArray( modelArray );
        fresh.set( true );
    }

    public  java.lang.Object[] toArray()
    {
        refreshCopyIfNecessary();
        return ourCopy;
    }

    public <T> T[] toArray( T[] a )
    {
        refreshCopyIfNecessary();
        return (T[]) ourCopy;
    }

    public  E[] asTypedArray()
    {
        refreshCopyIfNecessary();
        return ourCopy;
    }

    private  void markAsStale()
    {
        fresh.set( false );
    }

    public  void addIfAbsent( E e )
    {
        underlyingList.addIfAbsent( e );
        markAsStale();
    }

    public  boolean add( E e )
    {
        boolean result = underlyingList.add( e );
        markAsStale();
        return result;
    }

    public  boolean remove( java.lang.Object o )
    {
        boolean result = underlyingList.remove( o );
        markAsStale();
        return result;
    }

    public  boolean containsAll( java.util.Collection<?> c )
    {
        return underlyingList.containsAll( c );
    }

    public  boolean addAll( java.util.Collection<? extends E> c )
    {
        boolean result = underlyingList.addAll( c );
        markAsStale();
        return result;
    }

    public  boolean addAll( int index, java.util.Collection<? extends E> col )
    {
        boolean result = underlyingList.addAll( index, col );
        markAsStale();
        return result;
    }

    public  boolean removeAll( java.util.Collection<?> col )
    {
        boolean result = underlyingList.removeAll( col );
        markAsStale();
        return result;
    }

    public  boolean retainAll( java.util.Collection<?> col )
    {
        boolean result = underlyingList.retainAll( col );
        markAsStale();
        return result;
    }

    public  void clear()
    {
        underlyingList.clear();
        markAsStale();
    }

    public  E get( int index )
    {
        refreshCopyIfNecessary();
        return (E) ourCopy[index];
    }

    public  E set( int index, E element )
    {
        E e = underlyingList.set( index, element );
        markAsStale();
        return e;
    }

    public  void add( int index, E element )
    {
        underlyingList.add( index, element );
        markAsStale();
    }

    public  E remove( int index )
    {
        E e = (E) underlyingList.remove( index );
        markAsStale();
        return e;
    }

    public  int indexOf( java.lang.Object o )
    {
        return underlyingList.indexOf( o );
    }

    public  int lastIndexOf( java.lang.Object o )
    {
        return underlyingList.lastIndexOf( o );
    }

    public  java.util.ListIterator<E> listIterator()
    {
        return underlyingList.listIterator();
    }

    public  java.util.ListIterator<E> listIterator( int index )
    {
        return underlyingList.listIterator( index );
    }

    public  java.util.List<E> subList( int fromIndex, int toIndex )
    {
        return underlyingList.subList( fromIndex, toIndex );
    }

}
