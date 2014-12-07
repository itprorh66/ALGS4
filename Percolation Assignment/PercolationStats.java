/****************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats < input.txt
 *  Dependencies: StdIn.java StdOut.java StdStats.java
 *                Percolation.java, Math.java
 * Test Dependencies: StopWatch.java
 *
 ****************************************************************************/

/**  Alogrithims Part 1 - Percolation Assignment:    
  **  Use the Percolation Class measure the time required to execute 
  **   the Percolation algorithim
  *  @author itprorh66@gmail.com
  *  @version 2014-08-02-005
  */


public class PercolationStats {
    private double[] testResults;  //Array to hold the terst results
    private int tests = 0;
   
    
/**  Uses the Percolation Class to build an NxN system and run T 
 *  independent experiments. Records the following results:
 *  <ol>
 *  <li>  mean
 *  <li>  stddev
 *  <li>  low Confidence point
 *  <li>  High Confidence point
 *  @param N int Number of Rows and Columns in System
 *  @param T int Number of times to run percolation test
 *  @throws java.lang.IllegalArgumentException if N <= 0 or T <= 0.
 * 
 */         
    public PercolationStats(int N, int T)  {
        if (N <= 0) throw new IllegalArgumentException("N <= 0");
        if (T <= 0) throw new IllegalArgumentException("T <= 0");
        tests = T;  // number of tests to run
        testResults = new double[T];  // array to hold connection results
        for (int t = 0; t < T; t++) {
            Stopwatch stopwatch = new Stopwatch();
            Percolation pr = new Percolation(N);
            double cellCount = 0.0;  // count of open cells
            while (!pr.percolates() && cellCount < N*N) {
                int r = StdRandom.uniform(1, N + 1);
                int c = StdRandom.uniform(1, N + 1);
                if (!pr.isOpen(r, c)) {
                    cellCount = cellCount + 1.0;
                    pr.open(r, c);
                }
                
            }
            testResults[t] = cellCount/(N * N);        
        }
    }
   

  // sample mean of percolation threshold
    public double mean()  {
        return StdStats.mean(testResults);
    }
  
  // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(testResults);
    }
   
   // returns lower bound of the 95% confidence interval    
    public double confidenceLo()  {
        double cf = 1.96;   // Confidence Interval constant
        return  mean() - cf*(StdStats.stddev(testResults)/Math.sqrt(tests));
    }
  
   // returns upper bound of the 95% confidence interval    
    public double confidenceHi() {
        double cf = 1.96;   // Confidence Interval constant
        return mean() + cf*(StdStats.stddev(testResults)/Math.sqrt(tests));
    }
       
   // test client, described below
    public static void main(String[] args) {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats ps = new PercolationStats(N, T);
        StdOut.println("Experimental Results for "+ T + " tests of a " + N + " by " + N + " Array in average time of " + stopwatch.elapsedTime()/T + " secs"); 
        StdOut.println("  Mean =\t\t\t" + ps.mean());
        StdOut.println("  Std Deviation = \t\t" + ps.stddev());
        StdOut.println("  95% Confidence Interval =\t" + ps.confidenceLo() + ",   " + ps.confidenceHi());
        
            
        
    }
       
}