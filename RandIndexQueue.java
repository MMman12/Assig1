import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandIndexQueue<T> implements MyQ<T>, Indexable<T>, Shufflable
{
    private int front, back, moves, size, cap;

    private T[] q;

    public RandIndexQueue(int c)
    {
        @SuppressWarnings("unchecked")
        T[] tempQ = (T[]) new Object [c + 1];
        q=tempQ;
        front=0;
        cap=c;
        back=c;
		size=0;
        moves=0;

    }

    public boolean offer(T item)
    {

        back=(back+1)%q.length;
        moves++;
		size++;
		
		if(isFull())
			resize();
		
		
		q[back]=item;

        return true;
    }

    public T poll()
    {
        T data= q[front];
        q[front]=null;
        front=(front+1)%q.length;
        size--;
        return data;
    }

    public T peek()
    {
        return q[front];
    }
	
    public boolean isFull()
    {
        if(size==cap)
		{
			return true;
		}
        else
            return false;

    }

    public void clear()
    {
		@SuppressWarnings("unchecked")
        T[] array = (T[]) new Object [q.length];
        q = array;
		front=0;
		back=cap;
		size=0;
    }

    public boolean isEmpty()
    {

        return size==0;
    }
	
    private void resize()
    {

		
	        T[]oldQ=q;
	        int oldSize=oldQ.length;
	        int newSize=2*oldSize;

	        @SuppressWarnings("unchecked")
	        T[]tempQ=(T[]) new Object[newSize];
	        q=tempQ;

	        for(int i=0; i<oldQ.length; i++)
	        {
	            q[i]=oldQ[front];
	            front=(front+1)%oldSize;
	        }

	        front=0;
	        back=oldSize-2;
			cap=newSize;
		
		

    }
	
    public int size()
    {
        return size;
    }
	
    public void setMoves(int m)
    {
        moves=m;
    }
	
    public int getMoves()
    {
        return moves;
    }
	
	public T get(int i)
	{
		if(size<i+1)
			throw new IndexOutOfBoundsException();
		
		return q[(i+front)%q.length];
		
		
	}
	
	public void set(int i, T item)
	{
		if(size<i+1)
			throw new IndexOutOfBoundsException();
		
		q[(i+front)%q.length]=item;
	
	}
	
	public void shuffle()
	{
		Random rand = new Random();
		
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[cap];
		for(int i=front; i<size+front; i=(i+1)%q.length)
		{
			int r = rand.nextInt(size);
			while(temp[r]!=null)
			{
				r=rand.nextInt(size);
			}
			temp[r]=q[i];
		}
	
		front=0;
		back=size-1;
		q=temp;
	}
	public String toString()
	{
		StringBuilder s = new StringBuilder("Contents:\n");
		
		for (int i = 0; i < size; i++)
		{

				s.append(q[(i+front)%q.length].toString() + " ");
		}
		return s.toString();
	}
}
