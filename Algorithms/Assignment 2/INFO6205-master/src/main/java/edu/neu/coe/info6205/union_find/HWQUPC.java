
package edu.neu.coe.info6205.union_find;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import sun.net.ProgressSource;

public class HWQUPC {
	private int[] parent; // parent[i] = parent of i
	private int[] height; // height[i] = height of subtree rooted at i
	private static int count; // number of components
	private boolean pathCompression = false;

	/**
	 * Initializes an empty union–find data structure with {@code n} sites {@code 0}
	 * through {@code n-1}. Each site is initially in its own component.
	 *
	 * @param n
	 *            the number of sites
	 * @throws IllegalArgumentException
	 *             if {@code n < 0}
	 */
	public HWQUPC(int n) {
		count = n;
		parent = new int[n];
		height = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			height[i] = 0;
		}
	}

	public void show() {
		for (int i = 0; i < parent.length; i++) {
			System.out.printf("%d: %d, %d\n", i, parent[i], height[i]);
		}
	}

	/**
	 * Returns the number of components.
	 *
	 * @return the number of components (between {@code 1} and {@code n})
	 */
	 static int count(int n) {
		 HWQUPC path = new HWQUPC(n);
		 Random r = new Random();
			int min = 0;
			int max = n - 1;

		 int iteration =0;
		while (count != 1) {
			int i = r.nextInt(n);
		
			int j = r.nextInt(n);

			if (!path.connected(i, j)) {
				//System.out.println(" ( " + i + " , " + j + " ) ");
				path.union(i, j);
			
			//	path.show();

//				connection++;
//				
//				System.out.println(" connections " + connection);
			}
			iteration ++;
			//System.out.println(" count " +  iteration);
			
		}
return iteration;
	}

	/**
	 * Returns the component identifier for the component containing site {@code p}.
	 *
	 * @param p
	 *            the integer representing one site
	 * @return the component identifier for the component containing site {@code p}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= p < n}
	 */
	public int find(int p) {
		validate(p);
		int root = p;
		while (root != parent[root])
			root = parent[root];
		if (!pathCompression)
			return root;
		HWQUPC.doPathCompression(p, root, parent);
		return root;
		// ... end of TODO
	}

	// validate that p is a valid index
	private void validate(int p) {
		int n = parent.length;
		if (p < 0 || p >= n) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
		}
	}

	/**
	 * Returns true if the the two sites are in the same component.
	 *
	 * @param p
	 *            the integer representing one site
	 * @param q
	 *            the integer representing the other site
	 * @return {@code true} if the two sites {@code p} and {@code q} are in the same
	 *         component; {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless both {@code 0 <= p < n} and {@code 0 <= q < n}
	 */
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	/**
	 * Merges the component containing site {@code p} with the the component
	 * containing site {@code q}.
	 *
	 * @param p
	 *            the integer representing one site
	 * @param q
	 *            the integer representing the other site
	 * @throws IllegalArgumentException
	 *             unless both {@code 0 <= p < n} and {@code 0 <= q < n}
	 */
	public void union(int p, int q) {
		int i = find(p);
		int j = find(q);
		if (i == j)
			return;

		HWQUPC.mergeComponents(i, j, height, parent);
		count--;
	}

	/**
	 * Used only by testing code
	 * 
	 * @param pathCompression
	 */
	public void setPathCompression(boolean pathCompression) {
		this.pathCompression = pathCompression;
	}

	/**
	 * Used only by testing code
	 * 
	 * @param i
	 *            the component
	 * @return the parent of the component
	 */
	public int getParent(int i) {
		return parent[i];
	}

	static void mergeComponents(int i, int j, int[] height, int[] parent) {
		// TODO make shorter root point to taller one
		if (height[i] < height[j])
			parent[i] = j;
		else if (height[i] > height[j])
			parent[j] = i;
		else {
			parent[j] = i;
			height[i]++;
		}

		// throw new RuntimeException("not implemented");
	}

	static void doPathCompression(int p, int root, int[] parent) {
		// TODO update parent if appropriate
		// throw new RuntimeException("not implemented");
		parent[p] = root;

	}

	public static void main(String[] args) {
		// read n from the user and pass tht as an argument to generate sites
		Scanner input = new Scanner(System.in);
		//System.out.println("Enter the value of n : ");

		// total number of sites is n
	//	int n = input.nextInt();
//		HWQUPC path = new HWQUPC(n);

		// generating random pairs of integers between 0 to n-1
//		Random r = new Random();
//		int min = 0;
//		int max = n - 1;
//
//		
//		int connection = 0;

		// calling connected() to see if the pairs are connected
		// if they are not connected then connect them using union()
		// boolean check = connected();
		String csvFile = "Analysis_HWQUPC.csv";
        FileWriter writer;
		try {
			writer = new FileWriter(csvFile);
		   for(int n =100; n<10000;) {
			
			   System.out.println ("N : " +n);

		double avg = 0;
		double expected = 0;
		int c= 0;
	for(int i=0; i < 1000;i++) {
		 c = c+ HWQUPC.count(n);
		
	}
	
	 writeLine(writer, Arrays.asList(String.valueOf(n), String.valueOf(c/1000),String.valueOf((n / 2) * Math.log(n))));
	 expected = (n / 2) * Math.log(n);

		System.out.println(" the total number of iterations are " + (c/1000)+ " against expected " + expected);

		 n = n+100;
		   }			

		   
	        writer.flush();
	        writer.close();
	    	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		   
		  

		
		
	}
	
    
    
private static final char DEFAULT_SEPARATOR = ',';
    
    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

   
    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }
}
