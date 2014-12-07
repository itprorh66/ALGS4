/****************************************************************************
 *  Compilation:  javac Subset.java
 *  Execution:    java Subset < input.txt
 *  Dependencies: StdIn.java StdOut.java 
 *                
 ****************************************************************************/

/**  Alogrithims Part 1 - Randomized Queues and Deques Assignment:    
 **  Client takes a command-line integer k; reads in a sequence 
 **  of N strings from standard input using StdIn.readString(); and prints out 
 **  exactly k of them, uniformly at random. 
 **  Each item from the sequence is printed out at most once. 
 *   Assumes that k > 0 and <= the number of string N.
 *
  *  @author itprorh66@gmail.com
  *  @version 2014-14-02-003
  */

public class Subset {
    public static void main(String[] args)  {
        int K = StdIn.readInt();
        int n = 0;
        RandomizedQueue<String> A1 = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (n < K) {
            A1.enqueue(s);
            n++;
            }
            else {
                if (StdRandom.uniform(2) == 0) {
                    A1.enqueue(s);
                    s = A1.dequeue();
                }
                
            }
        }
        for (int i = 0; i < K; i++) {
            StdOut.println(A1.dequeue());        
        }  
    }
}