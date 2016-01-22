
import java.util.*;
import java.net.*;
import java.io.*;
import java.math.*;

public class Assig5
{
	private static AdjacencyList AdjLis;
	
	public static void main(String[] args)
	{
		if (args.length!=1) 
                {
                    System.err.println("Please Enter the following into the Command Line: java Assig5 <filename.txt>");
                    System.exit(-1);
                }
		if (args.length==1) //Constructs adjacency lists
		{
			String fileName;
			fileName = args[0];
			File graphFile = new File(fileName);
			try
			{
				AdjLis = new AdjacencyList(new BufferedReader(new FileReader(graphFile))); //calls the adjacency list constructor taking a bufferedreader object
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				System.err.println("No such file found");
			}
		}
		System.out.println("\n\n\nAssig5              \n"); 
		System.out.println("Network Simulation Tool             \n");
		prompt();
	}

	static void prompt()
	{
		System.out.println("Enter a command,type 'H' for the list of commands");
		Scanner s = new Scanner(System.in);
		String input = s.nextLine();
		if (input.equals("R")==true) 
                {
                    System.out.println("\nNetwork Summary:              \n");
                    AdjLis.report();
                }
		if (input.equals("M")==true)
                {
                    System.out.println("\nNetwork Minimum Spanning Tree Summary:              \n");
                    AdjLis.minST();
                }
		if (input.equals("V")==true) 
                {
                    AdjLis.view();
                }
		if (input.charAt(0)=='S') 
                {
                    int[] arr = getInts(input);
                    AdjLis.djikstras(arr[0], arr[1]);
                }
		if (input.charAt(0)=='P') 
                {
                    int[] arr = getInts(input);
                    AdjLis.distinctPaths(arr[0], arr[1], arr[2]);
                }
		if (input.charAt(0)=='H') 
                {
                    help();
                }
		if (input.charAt(0)=='D') 
                {
                    int[] arr = getInts(input);
                    AdjLis.disableNode(arr[0]);
                }
		if (input.charAt(0)=='U') 
                {
                    int[] arr = getInts(input);
                    AdjLis.enableNode(arr[0]);
                }
		if (input.charAt(0)=='C') 
                {
                    int[] arr = getInts(input);
                    AdjLis.modifyWeight(arr[0], arr[1], arr[2]);
                    AdjLis.view();
                }
		if (input.equals("Q")==true) 
                {
                    System.exit(0);
                }
		prompt();
	}
	

	static int[] getInts(String s) //reads in a string of integers seperated by spaces, and transforms them into an int array. Used for arguments when calling adjacencylist functions.
	{
		s = s.substring(2, s.length());
		String[] strArray = s.split(" ");
		int[] intArray = new int[strArray.length];
		for (int i = 0; i <= strArray.length-1; i++)
		{
			intArray[i] = Integer.parseInt(strArray[i]);
		}
		return intArray;
	}

	
	final static void help()
	{
		System.out.println (
								  "\nVALID INTERACTIVE COMMANDS:"   
					            + "\n -R . . . . . . . . . . : display the current active network (all active nodes and edges, including edge weights); show the status of the network (connected or not); show the connected components of the network\n" 
								+ "\n -M . . . : show the vertices and edges (with weights) in the current minimum spanning tree of the network" 
								+ "\n -H . . . . . . . . . . . : display a list of all valid arguments."
								+ "\n -S <i> <j> . . . . . . . . . . : display the shortest path (by latency) from vertex i to vertex j in the graph" 
								+ "\n -P <i> <j> <x> . . . . . . . . : display each of the distinct paths (no common edges) from vertex i to vertex j with total weight less than or equal to x. All of the vertices and edges in each path should be shown and the total number of distinct paths should be shown at the end.\n" 
								+ "\n -D <i> . . . . . . . . . . . . : node i in the graph will go down. All edges incident upon i will be removed from the graph as well." 
								+ "\n -U <i> . . . . . . . . . . . . : node i in the graph will come back up. All edges previously incident upon i (before it went down) will now come back online with their previous weight values.\n" 
								+ "\n -C <i> <j> <x> . . . . . . . . : change the weight of edge (i, j) (and of edge (j, i)) in the graph to value x. If x is <= 0 the edge(s) should be removed from the graph. If x >= 0 and edge (i, j) previously did not exist, create it.\n" 
								+ "\n -Q . . . . . . . . . . . : Exit the program.\n"
							);
		prompt();
	}
}

