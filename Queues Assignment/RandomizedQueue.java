import java.util.Iterator;
import java.util.NoSuchElementException;
/****************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue < input.txt
 *  Dependencies: StdIn.java StdOut.java , java.util.Iterator, 
 *                java.util.NoSuchElementException
 *                java.util.NullPointerException
 *                
 * Test Dependencies: StopWatch.java, StdRandom.java
 *
 ****************************************************************************/

/**  Alogrithims Part 1 - Randomized Queues and Deques Assignment:    
 **  Creates a generalized Queue data structure allows for uniformly random 
 **  item extraction.  Support each randomized queue operation 
 **  (besides creating an iterator) in constant amortized time and uses
 **   space proportional to the number of items currently in the queue. 
  *   @author itprorh66@gmail.com
  *   @version 2014-15-02-004
  */

public class RandomizedQueue<Item> implements Iterable<Item> {
        private int N;         // Number of items in Array
        private Item[] WA = (Item[]) new Object[1];   

        
    // Helper Function to resize Array
       private void resize(int cap) {
            Item[] copy = (Item[]) new Object[cap];
            scramble(WA, copy);
            WA = copy;
        }
  
    // Helper Function to Scramble Array
       private void scramble(Item[] from, Item[] to)  {
            int end = N;
            for (int i = 0; i < N; i++)  { 
                int get = StdRandom.uniform(end);
                to[i] = from[get];
                from[get] = from[end-1];
                from[end-1] = null;
                end--;
            } 
       } 
    
    //  No constructor requires toImplement the empty Bag Structure 
    //    public void RandomizedQueue() {
    //    }
    
   /** Returns true if Queue is empty */ 
    public boolean isEmpty()   {
        return N == 0;      
    }
   
   /** Returns the number of items on the queue */
    public int size()  {
        return N;      
    }
    
    
    /** Adds item to queue
    **  @throws NullPointerException if item == null
    */
    public void enqueue(Item item)  {
        if (item == null) throw new NullPointerException("null entry");
        if (N == WA.length) resize(WA.length * 2);
        WA[N] = item;
        N++;       
    }           
    
    

    /** Deletes and returns uniformly random item from queue
    **  @throws NoSuchElementException if N == 0
    */    
    public Item dequeue()   {
        if (N == 0) throw new NoSuchElementException("Queue is empty");
        int p = StdRandom.uniform(N);    // select item at random 
        Item value = WA[p];
        WA[p] = WA[N-1];  //swap selected item with last item
        WA[N-1] = null;   // null out last item 
        N--;              // reduce number stored
        if (N > 0 && N == WA.length/4) resize(WA.length/2);
        return value;               
    }
   
     
    /** Returns a uniformly random item from queue
    **  @throws NoSuchElementException if N == 0
    */        
    public Item sample()       {
        if (N == 0) throw new NoSuchElementException("Queue is empty");
        int p = StdRandom.uniform(N);   // select item at random 
        return WA[p];    
    }
   
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator()    {
        resize(WA.length);
        return new ListIterator();                  
    }
   
   // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private int point = 0;
        

        public boolean hasNext()  { 
            return point < N;
        }

        
        public void remove()      { 
            throw new UnsupportedOperationException(); 
        }

        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = WA[point];
            point++;
            return item;
        }
    }        
    
    // unit testing
    public static void main(String[] args)  {
        
        // Test Creation of Multiple Arrays of different types
        RandomizedQueue<String> A1 = new RandomizedQueue<String>();
        RandomizedQueue<Integer> A2 = new RandomizedQueue<Integer>();
        RandomizedQueue<Double> A3 = new RandomizedQueue<Double>();
        StdOut.println("Objects: A1= " + A1 + ", A2= " + A2 + ", A3= " + A3);
        StdOut.println("Size: A1= " + A1.size() + ", A2= " + A2.size() + ", A3= " + A3.size());
        
        // Test enqueue, iteration, sample, and dequeue of Arrays
        String[] ltrs = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q"};
        for (int i = 0; i < ltrs.length; i++) {
            A1.enqueue(ltrs[i]);
            A2.enqueue(i);
            A3.enqueue(i*2.5);
        }
        
        StdOut.println("A1= " + A1.size() + ", A2= " + A2.size() + ", A3= " + A3.size());
        StdOut.println("Enqueue & Iteration Tests");
        StdOut.print("A1 array= ");
        for (String mm : A1) StdOut.print(mm + ", ");
        StdOut.println();
        for (String mm : A1) StdOut.print(mm + ", ");
        StdOut.println();
        for (String mm : A1) StdOut.print(mm + ", ");
        StdOut.println();
        StdOut.print("A2 array= ");
        for (int mm : A2) StdOut.print(mm + ", ");
        StdOut.println();
        StdOut.print("A31 array= ");
        for (double mm : A3) StdOut.print(mm + ", ");
        StdOut.println(); 
        StdOut.println("Size: A1= " + A1.size() + ", A2= " + A2.size() + ", A3= " + A3.size());
         
        StdOut.println("Sample Tests");
        for (int i = 0; i < 5; i++) {
            StdOut.println("Samples  A1= " + A1.sample() + ", A2= " + A2.sample() + ", A3= " + A3.sample());
        }
        StdOut.println("Size: A1= " + A1.size() + ", A2= " + A2.size() + ", A3= " + A3.size());
        
        StdOut.println("Dequeue Tests");
        for (int i = 0; i < 5; i++) {
            StdOut.println("Values  A1= " + A1.dequeue() + ", A2= " + A2.dequeue() + ", A3= " + A3.dequeue());
        }
        StdOut.println("Size: A1= " + A1.size() + ", A2= " + A2.size() + ", A3= " + A3.size());
        
        
    }
}