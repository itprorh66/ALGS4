import java.util.Iterator;
import java.util.NoSuchElementException;
/****************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque < input.txt
 *  Dependencies: StdIn.java StdOut.java , java.util.Iterator, 
 *                java.util.NoSuchElementException
 *                java.util.UnsupportedOperationException
 *                
 * Test Dependencies: StopWatch.java, StdRandom.java
 *
 ****************************************************************************/

/**  Alogrithims Part 1 - Randomized Queues and Deques Assignment:    
 **   Creates a generalized Queue data structure that operates in constant time 
 **   for all worst case for all operations.
  *  @author itprorh66@gmail.com
  *  @version 2014-14-02-003
  */

public class Deque<Item> implements Iterable<Item> {
    private int N;                // size of Queue
    private Node firstInQue;      // front of Queue
    private Node lastInQue;       // back of Queue
  
    
    // helper linked list class
    private class Node {
        private Item item;
        private Node  next;
        private Node prior;
    }    
    
    
   /** Constructs an empty deque structure */
    public Deque()  {
        firstInQue = null;
        lastInQue = null;
        N = 0;
    }
    
    
    /** returns true if deque is empty */
    public boolean isEmpty()  {
        return N == 0;    
    }
  
    
    /**  returns the number of items on the deque  */
    public int size()    {
        return N;          
    }
   
    
    /** inserts item at the front of the deque
      * @throws  NullPointerException if item = null  
      */
      
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("null entry");
        Node oldPointer = firstInQue;
        firstInQue =  new Node();
        firstInQue.item = item;
        firstInQue.next = oldPointer;
        if (oldPointer != null) oldPointer.prior = firstInQue;
        ++N;
        if (lastInQue == null) lastInQue = firstInQue;       
    }
  
    
    /** inserts item at the end of the deque
      * @throws  NullPointerException if item = null  
      */  
    public void addLast(Item item)  {
        if (item == null) throw new NullPointerException("null entry");
        Node oldPointer = lastInQue;
        lastInQue = new Node();
        lastInQue.item = item;
        lastInQue.prior = oldPointer;
        if (oldPointer != null) oldPointer.next = lastInQue;
        ++N;
        if (firstInQue == null) firstInQue = lastInQue;              
    }
  
    
    /** deletes and return the item at the front of the deque
      * @throws NoSuchElementException if deque is empty
      */
    public Item removeFirst()  {
        if (firstInQue == null) throw new NoSuchElementException("Queue is empty");
        Node oldPointer = firstInQue;
        firstInQue = oldPointer.next; 
        if (firstInQue != null) firstInQue.prior = null;
        oldPointer.next = null;
        oldPointer.prior = null;       
        N--;
        if (N == 0) {
            firstInQue = null;
            lastInQue = null;               
        }
        return oldPointer.item;                 
    }
    
    
    /**  delete and return the item at the end 
      *  @throws NoSuchElementException if deque is empty
      */
    public Item removeLast()  {
        if (lastInQue == null) throw new NoSuchElementException("Queue is empty");
        Node oldPointer = lastInQue;
        lastInQue = oldPointer.prior;
        if (lastInQue != null) lastInQue.next = null;
        oldPointer.next = null;
        oldPointer.prior = null;
        N--;
        if (N == 0) {
            firstInQue = null;
            lastInQue = null;               
        }
        return oldPointer.item;                 
    }
 
    
    /** return an iterator over items in order from front to end
     ** @throws UnsupportedOperationException for remove method
     */
    public Iterator<Item> iterator()   {
         return new ListIterator();                
    }

    
    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = firstInQue;

        public boolean hasNext()  { 
            return current != null;
        }

        
        public void remove()      { 
            throw new UnsupportedOperationException(); 
        }

        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }    

    
    /**   unit testing
     */
    public static void main(String[] args) {
        
        //Test Creation of Multiple Deque Instances
        Deque<String> Q1 = new Deque<String>();
        Deque<Integer> Q2 = new Deque<Integer>();
        Deque<Boolean> Q3 = new Deque<Boolean>();
        StdOut.println("Created multiple Deque Instances");
        StdOut.println("Q1= " + Q1);
        StdOut.println("Q2= " + Q2);
        StdOut.println("Q3= " + Q3);
        
        //Test to Verify String Size, Add, Remove & Iterate
        StdOut.println("\n*** Test to verify String Size, Add, Remove, and Iterate\n");
        String[] ltrs = {"C", "D", "B", "E", "A", "F"};
        for (int k = 0; k < ltrs.length; k++) {
                if (k % 2  == 0) {
                    Q1.addFirst(ltrs[k]);
                }
                else {
                    Q1.addLast(ltrs[k]);        
                }
        }
        
        // Test Iterable implementation
        StdOut.print("Listing: ");
        for (String i  : Q1) StdOut.print(i + ", ");
        StdOut.print("\n");
                    
        // Clear the Quue
        StdOut.print("Removing from Back: ");
        while (Q1.size() > 0) 
            StdOut.print(Q1.removeLast() + ", ");
        StdOut.print("\n");
        
        //Test to Verify Integer Size, Add, Remove & Iterate
        StdOut.println("\n*** Test to verify Integer Size, Add, Remove, and Iterate\n");
        Integer[] nmbrs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int k = 0; k < nmbrs.length; k++) {
                if (k % 2  == 0) {
                    Q2.addFirst(nmbrs[k]);
                }
                else {
                    Q2.addLast(nmbrs[k]);        
                }
        }
        
        // Test Iterable implementation
        StdOut.print("Listing: ");
        for (int i  : Q2) StdOut.print(i + ", ");
        StdOut.print("\n");
                    
        // Clear the Quue
        StdOut.print("Removing from Back: ");
        while (Q2.size() > 0) 
            StdOut.print(Q2.removeLast() + ", ");
        StdOut.print("\n");
        
          //Test to Verify Boolean Size, Add, Remove & Iterate
        StdOut.println("\n*** Test to verify Integer Size, Add, Remove, and Iterate\n");
        boolean[] flgs = {false, true, false, false, true, true, false};
        for (int k = 0; k < flgs.length; k++) {
                if (k % 2  == 0) {
                    Q3.addFirst(flgs[k]);
                }
                else {
                    Q3.addLast(flgs[k]);        
                }
        }
        
        // Test Iterable implementation
        StdOut.print("Listing: ");
        for (boolean i  : Q3) StdOut.print(i + ", ");
        StdOut.print("\n");
                    
        // Clear the Quue
        StdOut.print("Removing from Back: ");
        while (Q3.size() > 0) 
            StdOut.print(Q3.removeLast() + ", ");
        StdOut.print("\n");  
        
        
      // Test of AddFirst, Addlast, RemoveFirst, RemoveLast  
        String[] inst = {"AA", "CC", "BB", "DD"};
        String[] out = new String[4];
        Deque<String> B1 = new Deque<String>();
        B1.addFirst(inst[0]);
        B1.addLast(inst[1]);
        out[0] = B1.removeFirst();
        out[2] = B1.removeLast();
        B1.addFirst("X");
        B1.addLast("Y");
        B1.addFirst(inst[2]);
        B1.addLast(inst[3]);
        out[3] = B1.removeLast();
        out[1] = B1.removeFirst();
        for (String mm : out) {
            StdOut.println(mm);
        }
    }
    }
