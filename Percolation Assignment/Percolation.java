/****************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation < input.txt
 *  Dependencies: StdIn.java StdOut.java 
 *                WeightedQuickUnion.java StdRandom.java
 * Test Dependencies: StopWatch.java
 *
 ****************************************************************************/

/**  Alogrithims Part 1 - Percolation Assignment:    
  **  Create an N x N system of randomly blocked cells for use in testing percolation
  **  Where the Rows and Columns are each by an int between 1 and N
  * 
  *  @author itprorh66@gmail.com
  *  @version 2014-08-02-004
  */

public class Percolation  {
   private int width;                   // defines the System Matrix width
   private WeightedQuickUnionUF wuf;    // Pointer to WeightedQuickUnion object
   private boolean[][] pm;              // percolation matrix
   
   
/**  Constructs an Array NxN long of blocked cells for use in testing percolation 
  *  also creates a Union Array that is (N * N + 1) in length to keep track of path connections
  *  The Union Array is initialized by conecting cell(0) to cells(1 thru Cells(N+1). This allows for
  *  qucikly testing for isFiull status by checking connectivity to position 0.  
  *  
  *  @param N int Number of Rows and Columns in System
  *  @throws java.lang.IndexOutOfBoundsException if N <= 0
  * 
  */ 
   public Percolation(int N)  {
       if (N <= 0) throw new IndexOutOfBoundsException("N is less <= 0");
       width = N;                                 // width of matrix
       pm = new boolean[width][width];            // percolation matrix, keeps track of block/unblock stats
       wuf = new WeightedQuickUnionUF(width*width+1);   // connection array
       for (int k = 1; k <= width; k++)  {
           wuf.union(0, k);                                           // Connect first row to cell(0)
       
       }
       }
 
   
/**  Unblocks a System cell defined by a row (0 <= width-1 and a column (0 <= width-1)
 *  If cell is blocked, sets cell value to true and connects cell to its neighbors.
 *  If cell is already unblocked, no action is taken.
 * 
 *  @param i int Row pointer
 *  @param j int Column pointer
 *  @throws java.lang.IndexOutOfBoundsException if i <= 0 or i > N
 *  @throws java.lang.IndexOutOfBoundsException if j <= 0 or j > N
 */  
   public void open(int i, int j) {
       if (i <= 0 || i > width) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j <= 0 || j > width) throw new IndexOutOfBoundsException("column index j out of bounds");
       int r = i - 1;  
       int c = j - 1;
       if (!pm[r][c]) {
           pm[r][c] = true;
           int[]  rwpts  = {r-1, r+1};
           int[]  clpts  = {c-1, c+1};
           for (int rp : rwpts) {
               if (rp >= 0 && rp < width && pm[rp][c])  {
                   wuf.union((r * width + c + 1), (rp * width + c + 1));
               }
           }
           for (int cp : clpts) {
               if (cp >= 0 && cp < width && pm[r][cp])  {
                   wuf.union((r * width + c + 1), (r*width + cp + 1)); 
               }
           }
       }
   }                 
                                
  
   
   
/**  Tests Cell[row i,column j] to determine if it is open or closed
 *  Throws exception if either i or j are > width, or < 0
 * 
 *  @param i int Row pointer
 *  @param j int Column pointer
 *  @throws java.lang.IndexOutOfBoundsException if i < 0 or i > N
 *  @throws java.lang.IndexOutOfBoundsException if j < 0 or j > N
 */  
   public boolean isOpen(int i, int j)  {
       if (i <= 0 || i > width) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j <= 0 || j > width) throw new IndexOutOfBoundsException("column index j out of bounds");
       int r = i - 1;
       int c = j - 1;
       return pm[r][c];
   }

   
/**  Tests Cell[row i,column j] to determine if it is full
 *  Throws exception if either i or j are out of bounds
 * 
 *  @param i int Row pointer
 *  @param j int Column pointer
 *  @throws java.lang.IndexOutOfBoundsException if i < 0 or i > N
 *  @throws java.lang.IndexOutOfBoundsException if j < 0 or j > N
 */    
   public boolean isFull(int i, int j) {
       if (i <= 0 || i > width) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j <= 0 || j > width) throw new IndexOutOfBoundsException("column index j out of bounds");
       int r = i - 1;
       int c = j - 1;
       return  pm[r][c] && wuf.connected(0, r*width + c +1);
   }

   
/** Tests for percolation by determining if any cell in the last row 
 *  is connected to cell 0 .  If connected the system percolates 
 */
   public boolean percolates()  {
       boolean result = false;
       for (int k = 1; k <= width; k++) {
           if (isFull(width, k)) {
               result = true;
               break;                  
           }
       }      
       return result;     
   }

//  Internal test case   
   public static void main(String[] args) {
       Stopwatch stopwatch = new Stopwatch();
       double threshold = 0.0;
       int N = StdIn.readInt();
       double cellCount = 0.0;                 // Count of Unblocked cells
       Percolation pr = new Percolation(N);
       while (!pr.percolates() && cellCount < N*N) {
           int r = StdRandom.uniform(1, N + 1);
           int c = StdRandom.uniform(1, N + 1);
           if (!pr.isOpen(r, c)) {
                    cellCount = cellCount + 1.0;
                    pr.open(r, c);
           }
      }
      double time = stopwatch.elapsedTime();
      threshold = cellCount/(N * N);
      StdOut.println("Percolation Threshold = " + threshold + " open cells in " + time + " seconds");
   }
   
  }