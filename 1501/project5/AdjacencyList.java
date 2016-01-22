
import java.io.*;
import java.util.*;
import java.lang.*;

public class AdjacencyList 
{
    /*
        vCount - holds initial number of vertices
        curSize - reflect deletions and insertions on the adjacency list
    */
	private int eCount, vCount, curSize;
    /*
        delArr - holds deleted node
        marked - determines connectivity
    */ 
	private int[] delArr, marked; 
	 
	private String status; 
        private final int maxInt = 2147483647;
        private LinkedList[] list; //contains all of the linkedlists that make up the adjacency list
	private ArrayList<String> pathList; //global arraylist for holding calculated paths
	private final String errListOutOfBounds = "\nAttempted to insert vertex into an insufficiently sized Adjacency List!\n";

//////////////////// CONSTRUCTORS //////////////////////////////////////
/*
*   Constructs an AdjancencyList by taking in the maximum number of 
*   vertices the graph can hold.
    PARAM- int v
*/
	public AdjacencyList(int v) 
	{
		this.vCount = v;
		curSize = vCount;
		delArr = new int[vCount];
		this.list = new LinkedList[vCount];
		for (int i = 0; i<=list.length-1; i++)
		{
			delArr[i] = 1;
			this.list[i] = new LinkedList();
		}
	}

/*
*   Constructs an AdjancencyList by taking in a BufferedReader that is used 
*   to read in a file with graph specifications
*   PARAM- BufferedReader r
*/
	public AdjacencyList(BufferedReader r) 
	{
		ArrayList<String> specList = new ArrayList<String>(); 
		String input = null;
		String line;
		try
		{
			while ((input = r.readLine())!=null) 
			{
				line = new String(input); 
				specList.add(line);
			} 
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Error, Exiting...");
                        System.exit(-1);
		}
		this.vCount = Integer.parseInt(specList.get(0));
		this.eCount = Integer.parseInt(specList.get(1));
		curSize = vCount;
		delArr = new int[this.vCount]; 
		this.list = new LinkedList[this.vCount];
		for (int i = 0; i <= vCount-1; i++) 
		{
			delArr[i] = 1;
			this.list[i] = new LinkedList(i, specList); 
		}
		status="Connected";
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////// METHODS DOWN BELOW ///////////////////////////////////////////////////////////////////

///////////////////////////////CONTAINS/////////////////////////////////////////////////////////////////////
/*
*   CONTAINS:
*   returns true if the VERTEX is found in the adjacency list, false 
*   otherwise.
*/
	public boolean contains(int vertex)
	{
		for(int i = 0; i <= this.list.length-1; i++)
		{
			Node tempNode = list[i].head;
			while (tempNode!=null)
			{
				int v0 = tempNode.getData().someVertex();
				int v1 = tempNode.getData().adjacentVertex(v0);
				if ((v0==vertex) || (v1==vertex)) 
                                {
                                    return true;
                                }
				tempNode=tempNode.nextNode;
			}
		}
		return false;
	}

/*
*   CONTAINS:
*   returns true if the EDGE is found in the adjacency list, false 
*   otherwise.
*/
	public boolean contains(Edge e)
	{
		for (int i = 0; i <= this.list.length-1; i++)
		{
			if (this.list[i].find(e)==true) return true;
		}
		return false;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////INSERT///////////////////////////////////////////////////////////////////////////
/*
*   INSERT edges into the graph      
*   PARAM: Edge Class
*/

	public void insert(Edge newEdge)
	{
		int v0 = newEdge.someVertex();
		int v1 = newEdge.adjacentVertex(v0);
		if ((v0 > this.list.length-1)||(v1 > this.list.length-1)) throw new java.lang.ArrayIndexOutOfBoundsException(errListOutOfBounds);
		if ((this.contains(v0)!=true) || this.contains(v1)!=true) //as long as at least one of the included vertices is NOT already in the adjacency list, you're good to go.
		{
			this.list[newEdge.getVertexA()].insert(newEdge);
			this.list[newEdge.getVertexB()].insert(newEdge.reverse());
		}
		else if (this.contains(newEdge) == false) //then both vertices exist in the graph BUT an edge does NOT exist between them. so create it.
		{	
			this.list[newEdge.getVertexA()].insert(newEdge);
			this.list[newEdge.getVertexB()].insert(newEdge.reverse());
		}
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////R E P O R T////////////////////////////////////////////////////////////////////////
/*
*   REPORT: display the current active network (all active nodes and edges, including edge weights);
*   show the status of the network (connected or not); show the connected components of the network        
*/

	public void report() //display detailed information on each Node 
	{
		System.out.println();
		if (this.isConnected()==true) System.out.println("\nNetwork Status: CONNECTED.\n");
		else System.out.println("\nNetwork Status: DISCONNECTED\n");
		this.view();
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////i s C o n n e c t e d////////////////////////////////////////////////////////////////////
/*
*   isConnected: Through DFS, if not all of the nodes are visited then the graph is
*   is disconnected and will return false.
*/
	public boolean isConnected()
	{
		int curLocation = 0;
		System.out.println("Current number of vertices in graph: " + this.curSize);
		marked = new int[this.vCount];
		for (int j = 0; j <= this.vCount-1; j++)
		{
			marked[j] = 0; //preset is 0; when visited each node's respective index gets set to 1.
			if (delArr[j]==0) marked[j] = 1;
		}
		for (int i = 0; i <= this.list.length-1; i++) //for loop iterates over all vertices until it finds a list that is not empty. Our arbitrary starting location for the algorithm is the head of this list.
		{
			if (this.list[i].size > 0)
			{
				curLocation = this.list[i].head.getData().getVertexA();
				break;
			}
		}
		DFS(curLocation);
		boolean connected = true;
		for (int i = 0; i<=this.marked.length-1; i++) //A Connected would have an array of all ones                                                      
		{                                             
			if (marked[i] != 1)                   
			{
				connected = false;
				break;
			}
		}
		return connected;

	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////D F S/////////////////////////////////////////////////////////////////////////////
/*
*   DFS: Recursive depth first search algorith 
*   PARAM - int of location
*/

	private void DFS(int curLocation)
	{
		marked[curLocation] = 1;
		Node curNode = this.list[curLocation].head;
		while (curNode != null)
		{
			if (this.marked[curNode.getData().adjacentVertex(curLocation)] != 1) //if the adjacent node has yet to be visited
			{
				DFS(curNode.getData().adjacentVertex(curLocation)); //visit it!
			}
			curNode = curNode.nextNode;
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////m i n S T//////////////////////////////////////////////////////////////////////////////
/*
*   minST: uses Prim's Algorithm to calculate the Minimum Spanning Tree        
*/

	public void minST()
	{
		int MSTsize = 0;
		
		AdjacencyList MST = new AdjacencyList(vCount);
		int[] MSTVertices = new int[vCount];
		int[] GVertices = new int[vCount];

		for(int i = 0; i <= vCount-1; i++)
		{
			GVertices[i] = 1; 
			MSTVertices[i] = 0; 
		}
		int curLocation = 0;
		for (int i = 0; i <= this.list.length-1; i++) 
		{
			if (this.list[i].head != null)
			{
				curLocation = this.list[i].head.getData().getVertexA();
				break;
			}
		}
		GVertices[curLocation] = 0; 
		MSTVertices[curLocation] = 1; 
		MSTsize = 1;
		while(true)
		{
			Edge cheapestEdge = new Edge(this.minEdge(GVertices, MSTVertices)); 

			if (cheapestEdge.getWeight() == maxInt)
			{
				break;
			} 
			if (MSTVertices[cheapestEdge.getVertexA()]==0) 
                        {
                            MSTsize++;
                        } 
			if (MSTVertices[cheapestEdge.getVertexB()]==0) 
                        {
                            MSTsize++;
                        } 

			GVertices[cheapestEdge.getVertexA()] = 0;
			MSTVertices[cheapestEdge.getVertexA()] = 1;
			GVertices[cheapestEdge.getVertexB()] = 0;
			MSTVertices[cheapestEdge.getVertexB()] = 1;

			MST.insert(cheapestEdge);
			if (MSTsize==this.curSize) 
                        {
                            break;
                        } 
		}
		MST.view();
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////f i n d M i n i m u m E d g e////////////////////////////////////////////////////////////////////////////////////
/*
*    finds the lowest weighted edge that connects with a vertex 
*    not in the tree.
*    PARAM- takes an int array and of the vertices in the graph and in the tree
*/
	private Edge minEdge(int[] graphVertices, int[] treeVertices) 
	{
		Edge cheapestEdge = new Edge(1, 1, maxInt);
		Edge innerCheapestEdge = new Edge(1, 1, maxInt);	
		for (int i = 0; i <= this.list.length-1; i++)
		{
			if ((treeVertices[i] == 1) && (this.list[i].size > 0)) 
			{	
				Node tempNode = this.list[i].head; 	
				while (tempNode!=null)
				{
					if (graphVertices[tempNode.getData().getVertexB()] == 1) 
					{
						if (tempNode.getData().getWeight() < innerCheapestEdge.getWeight())
						{
							innerCheapestEdge = tempNode.getData();
						}
					}
					tempNode = tempNode.nextNode;
				}
				if (innerCheapestEdge.getWeight() < cheapestEdge.getWeight())
				{
					cheapestEdge = innerCheapestEdge;
				}
			}
		}
		return cheapestEdge;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////d j i k s t r a s//////////////////////////////////////////////////////////////////////////////////////////
/*
*   djikstras: uses Djikstra's algorithm to find lowest latency route between
*   two points.
*   PARAM- two points
*/
	public void djikstras(int start, int end) 
	{
		if ((this.delArr[start]==1) && (this.delArr[end]==1))
		{
			int curLocation = start;
			int[] foundArray = new int[vCount];
			String[] pathArray = new String[this.vCount];
			int[] distance = new int[vCount];
			for (int i = 0; i <=vCount-1; i++) 
			{
				pathArray[i] = new String(Integer.toString(start) + " ");
				foundArray[i] = 0;
				distance[i] = maxInt;
			}
			while (true) 
			{
				Node temp = this.list[curLocation].head;
				while (temp!=null)
				{
					if((temp.getData().getWeight() + distance[curLocation]) < distance[temp.getData().adjacentVertex(curLocation)]) //if (length of current edge being tested) + (length of current path) is CHEAPER than (current recorded path length)
					{
						distance[temp.getData().adjacentVertex(curLocation)] = temp.getData().getWeight() + distance[curLocation]; //update distance array
						pathArray[temp.getData().adjacentVertex(curLocation)] = new String(pathArray[curLocation] + Integer.toString(temp.getData().adjacentVertex(curLocation)) + " ");
					}
					temp = temp.nextNode;
				}
				if (curLocation==end) 
                                {
                                    break;
                                } 
				foundArray[curLocation]=1;
				curLocation = this.list[curLocation].getCheapestEdge(foundArray).adjacentVertex(curLocation); 
			}			
			int[] pathVertices = getInts(pathArray[end]);
			AdjacencyList shortestPath = new AdjacencyList(this.vCount);

			for (int j = 0; j <= pathVertices.length-2; j++)
			{
				int edgeEnd = j+1;
				
				shortestPath.insert(this.list[pathVertices[j]].getEdge(pathVertices[edgeEnd])); 
			}
			System.out.println("\nLowest Latency Path Between Node " + start + " and Node " + end + " Summary: \n");
			shortestPath.view();
		}
		else
		{
			System.err.println("\nUnable to calculate network path\n");
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////d e l e t e E d g e//////////////////////////////////////////////////////////////////////////////////
/*
*   disableNode: removes an edge from the graph
*   PARAM- Edge
*/

	public void deleteEdge(Edge e)
	{
		if (this.contains(e)==true)
		{
			this.list[e.getVertexA()].deleteNode(e.getVertexB()); 
			this.list[e.getVertexB()].deleteNode(e.getVertexA()); 
		}
		else
		{
			System.err.println("UNABLE TO DELETE EDGE!");
		}

	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      //////////////////////////////d i s t i n c t P a t h s////////////////////////////////////////////////////////////
        /*
        *   distinctPaths: caculates valid paths between start and end
        *   PARAM- int start, int end, int max
        */
	public void distinctPaths(int start, int end, int max)
	{
		if ((this.delArr[start]==1) && (this.delArr[end]==1))
		{
			System.out.println("Calculating all possible distinct paths between " + start + 
                                " and " + end + " with maximum length " + max + ". \n");
			pathList = new ArrayList<String>();
			int d = 0;
			String curPath = new String();

			searchPaths(start, end, d, curPath, max);
			
			if (pathList.get(0).charAt(0)==' ') 
			{
				String temp = new String(pathList.get(0));
				pathList.remove(0);
				pathList.add(temp.substring(1, temp.length()));
			}
		
			int[] paths;
			for (int i = 0; i <= pathList.size()-1; i++)
			{
				System.out.println("\nPath # " + (i+1) + ": ");
				paths = getInts(pathList.get(i));

				for (int j = 0; j <= paths.length-2; j++)
				{
					System.out.print("(" + paths[j] + ")---------->(" + paths[j+1] + ")");
				}
				System.out.println();
			}
		}
		else
		{
			System.err.println("\n Node cannot be found\n");
		}
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////s e a r c h P a t h s////////////////////////////////////////////////////
        /*
        *   searchPaths: recursive method that calculates all paths.
        *   PARAM - int curLocation, int endLocation, int curDistance, 
        *   String curPath, int maxLen
        */
	private void searchPaths(int curLocation, int endLocation, int curDistance, String curPath, int maxLen)
	{

			if (curDistance > maxLen) 
			{
				return;
			}

			if (curLocation == endLocation)
			{
				pathList.add(new String(curPath + curLocation + " "));
				return;
			}
			Node curNode = this.list[curLocation].head;
			while (curNode != null)
			{
				if (curPath.contains(Integer.toString((Integer)curNode.getData().adjacentVertex(curLocation))) != true)
				{
					searchPaths(curNode.getData().adjacentVertex(curLocation), endLocation, (curDistance + curNode.getData().getWeight()), new String(curPath + curLocation + " "), maxLen); 
				}
				curNode = curNode.nextNode;
			}
			return;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////d i s a b l e N o d e//////////////////////////////////////////////////////////////////////
        /*
        *   disableNode: disables a node
        *   PARAM - takes in the vertex to be disabled
        */

	public void disableNode(int node)
	{
		if (delArr[node]==1)
		{
			System.out.println("\nWARNING!\nNetwork Node " + node + 
                                " is no longer responding. Attempting automatic repair procedures. \n(This event has been logged)\n");
			this.curSize--;
			this.delArr[node] = 0; 
			int[] delArr = this.list[node].disable(); 
			for (int i = 0; i <= delArr.length-1; i++)
			{
				this.list[delArr[i]].deleteNode(node);
			}
			this.view();
		}
		else
		{
			System.err.println("\nCannot reach node\n");
		}	
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////r e s t o r e N o d e//////////////////////////////////////////////////////////////////
        /*
        *   enableNode: restores node that was disabled
        *   PARAM - takes in the vertex to be restored
        */
   
	public void enableNode(int node) //
	{
		System.out.println("\nInitiating automatic repair procedures on Node " + node + "...\n");
		if (delArr[node]==0)
		{
			this.curSize++;
			this.delArr[node] = 1;
			for (int i = 0; i <= this.list.length-1; i++) 
			{
				for (int j = 0; j <= this.list[i].edgeGraveYard.size()-1; j++)  
				{
					if (this.list[i].edgeGraveYard.get(j).connectsNode(node)) 
					{
						if ((delArr[this.list[i].edgeGraveYard.get(j).getVertexA()] == 1) && (delArr[this.list[i].edgeGraveYard.get(j).getVertexB()] == 1)) 
						{
							this.insert(this.list[i].edgeGraveYard.get(j)); 
						}
					}
				}
			}
			this.view();
			System.out.println("\nNode " + node + " has been restored!\n");
		}
		else
		{
			System.err.println("\nCannot enable Node\n");
		}

	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////m o d i f y W e i g h t///////////////////////////////////////////////////////////////////////
        /*
        *   modifyWeight: This void function iterates through the Adjacency List in search of the target edge,
        *   and modifies/deletes/creates the edge
        *   PARAM -  takes in ints for two vertices and an int for the weight
        */
	public void modifyWeight(int v0, int v1, int w) 
	{
		if ((this.delArr[v0] == 1) && (this.delArr[v1] == 1)) 
		{
			Edge tempEdge = new Edge(v0, v1, w);
			if (this.contains(tempEdge)==true)
			{
				System.out.println("Modifying edge (" + v0 + ", " + v1 + ", " + w + ") ");
				if (w <= 0) 
				{
					deleteEdge(tempEdge);
				}
				else if (w > 0) 
				{
					Node tempNode = this.list[v0].head;
					while (tempNode != null)
					{
						if ((tempNode.getData().getVertexA() == v0) && (tempNode.getData().getVertexB() == v1)) 
						{
							tempNode.getData().setWeight(w);
						}
						tempNode = tempNode.nextNode;
					}
					tempNode = this.list[v1].head;
					while (tempNode != null)
					{
						if ((tempNode.getData().getVertexA() == v1) && (tempNode.getData().getVertexB() == v0))
						{
							tempNode.getData().setWeight(w);
						}
						tempNode = tempNode.nextNode;
					}
				}
			}
			else if (w > 0)
			{
				System.out.println("Creating edge (" + v0 + ", " + v1 + ", " + w + ") ");
				this.insert(tempEdge);
			}
			else 
			{
				System.err.println("\nCannot insert edge.\n");
			}
		}
		else System.err.println("Cannot modify weight\n");
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////v i e w///////////////////////////////////////////////////////////////////
        /*
        *   view: displays the current state of the graph
        */

	public void view()
	{
		System.out.println("	Links connected to Node (node) . . . . . . . . . . . . . . : (node)---weight--->(node)");
		for (int i = 0; i <= this.list.length-1; i++)
		{
			System.out.print("	Links connected to Node " + i + " . . . . . . . . . . . . . . : ");
			Node tempNode = this.list[i].head;
			while (tempNode!=null)
			{			
				System.out.print(" (" + tempNode.getData().getVertexA() + ") ---" + tempNode.getData().getWeight() + 
                                        "---> (" + tempNode.getData().getVertexB() + ")");
				tempNode = tempNode.nextNode;
			}
			System.out.println();
			
		}
		System.out.println();
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////g e t I n t s//////////////////////////////////////////////////////////////////////
        /*
        *    getInts: Reads in a string of integers seperated by spaces, and transforms them into an int array. 
        *   PARAM - String s
        */
   
	static int[] getInts(String s)
	{
		String[] strArray = s.split(" ");
		int[] intArray = new int[strArray.length];
		for (int i = 0; i <= strArray.length-1; i++)
		{
			intArray[i] = Integer.parseInt(strArray[i]);
		}
		return intArray;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////LINKED LIST CLASS////////////////////////////////////////
        /*
        *   SINGLY LINKED LIST CLASS
        */

	private class LinkedList 
	{
		//LinkedList fields:
		public Node head;
		private int size = 0;
		private boolean status;
		private ArrayList<Edge> edgeGraveYard; 

		public LinkedList()
		{
			edgeGraveYard = new ArrayList<Edge>();
		}

		public LinkedList(int n, ArrayList<String> specList)
		{
			this.status=true; 
			edgeGraveYard = new ArrayList<Edge>();
			int numDigitsV0, numDigitsV1, numDigitsWeight;
			String temp;
			for(int i = 2; i <= specList.size()-1; i++) 
			{
				char c = specList.get(i).charAt(0); 
				int index = 0;
				while (c!=' ') 
				{
					index++; 
					c = specList.get(i).charAt(index);
				} 
				numDigitsV0=index;

				index++;
				c = specList.get(i).charAt(index); 
				while (c!=' ') 
				{
					index++; 
					c = specList.get(i).charAt(index);
				} 
				numDigitsV1=(index-(numDigitsV0+1));

				index++;
				c = specList.get(i).charAt(index); 
				while (index!=(specList.get(i).length()-1)) 
				{
					index++; 
					c = specList.get(i).charAt(index);
				} 
				numDigitsWeight=index-((numDigitsV0+1)+(numDigitsV1+1));

				temp = new String(specList.get(i).substring(0, numDigitsV0)); 
				int v0 = Integer.parseInt(temp);

				temp = new String(specList.get(i).substring(numDigitsV0+1, ((numDigitsV0) + (numDigitsV1))+1));
				int v1 = Integer.parseInt(temp);

				temp = new String(specList.get(i).substring(((numDigitsV0+1) + (numDigitsV1+1)), ((numDigitsV0+1) + ((numDigitsV1+1)) + (numDigitsWeight+1))));
				int w = Integer.parseInt(temp);

				if (v0==n)
				{
					this.insert(new Edge(v0, v1, w));
				} 
				else if (v1==n) 
				{
					this.insert(new Edge(v1, v0, w));
				}
			} 
		}

		public void deleteNode(int b) 
		{
			Node curNode = this.head;
			Node prevNode = curNode;
			while (curNode != null)
			{
				if (curNode.getData().getVertexB()==b) 
				{
					if ((curNode.getData().getVertexA()==this.head.getData().getVertexA()) && (curNode.getData().getVertexB()==this.head.getData().getVertexB())) 
					{
						edgeGraveYard.add(new Edge(curNode.getData()));
						this.head = curNode.nextNode; 

						curNode = null; 
					}
					else
					{
						edgeGraveYard.add(new Edge(curNode.getData())); 
						prevNode.nextNode = curNode.nextNode; 

						curNode = null;
					}
					this.size--;
					break;
				}
				prevNode = curNode;
				curNode = curNode.nextNode;
			}
		}

		public Edge getCheapestEdge()
		{
			Node tempNode = this.head;
			int cheapestEdge = 100;
			int count = 0;
			int cheapestIndex = 0;
			while (tempNode!=null) 
			{
				int curEdge = tempNode.getData().getWeight();
				if (curEdge < cheapestEdge)
				{
					cheapestEdge = curEdge;
					cheapestIndex = count;	
				}
				count++; 
				tempNode=tempNode.nextNode;
			}
			int j = 0;
			Node iterator = this.head;

			while (j != (cheapestIndex))
			{
				iterator=iterator.nextNode;
				j++;
			}
			return new Edge(iterator.getData());
		}


		public Edge getCheapestEdge(int[] intArray) 
		{
			Node tempNode = this.head;
			int cheapestEdge = maxInt;
			int count = 0;
			int cheapestIndex = 0;
			while (tempNode!=null) 
			{
				if ((intArray[tempNode.getData().getVertexA()]!=1) || (intArray[tempNode.getData().getVertexB()]!=1))
				{
					int curEdge = tempNode.getData().getWeight();
					if ((curEdge < cheapestEdge))
					{
						cheapestEdge = curEdge;
						cheapestIndex = count;	
					}	
				}
				count++; 
				tempNode=tempNode.nextNode;
			}
			int j = 0;
			Node iterator = this.head;
			while (j != (cheapestIndex))
			{
				iterator=iterator.nextNode;
				j++;
			}
			return new Edge(iterator.getData());
		}


		public Edge getEdge(int vertex_b) 
		{
				Node tempNode = this.head;
				while(tempNode!=null)
				{
					if (tempNode.getData().getVertexB()==vertex_b) 
					{	
						return tempNode.getData();
					}
					else tempNode = tempNode.nextNode;
				}
				System.err.println("Vertex not found\n");
				System.exit(-1);
				return new Edge(0, 0, 0); 
		}

		public boolean getStatus()
		{
			return this.status;
		}

		public void setStatus(boolean b)
		{
			this.status=b;
		}

		private void display()
		{
			Node temp = this.head;
			while (temp!=null)
			{
				temp.display();
				temp = temp.nextNode;
			}
		}

		private int[] disable()
		{
			int[] vertexArray = new int[this.size]; 
			Node tempNode = this.head;
			int i = 0;
			while(tempNode!=null)
			{
				edgeGraveYard.add(new Edge(tempNode.getData())); 
				vertexArray[i] = tempNode.getData().getVertexB();
				tempNode = tempNode.nextNode;
				i++;
			}
			this.head=null; 
			this.size=0;
			return vertexArray; 
		}

		private void insert(Edge e)
		{
			if (this.find(e)==false)
			{
				Node newNode = new Node(e);
				if (this.size==0) 
				{
					this.head=newNode;
				}
				else
				{
					newNode.nextNode = this.head;
					this.head = newNode;
				}
				this.size++;
			}
		}

		private boolean find(Edge e)
		{
			Node temp = this.head;
			while (temp!=null)
			{
				if ((e.getVertexA()==temp.getData().getVertexA()) && (e.getVertexB()==temp.getData().getVertexB())) return true;
				else temp=temp.nextNode;
			}
			return false;
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////PRIVATE CLASS NODE//////////////////////////////////////////////////////////////////
	private class Node 
	{
		//Node fields:
		private Edge data;
		private Node nextNode;
		
		//Node constructors:
		public Node(Edge e)
		{
			this.data=e;
		}
		
		//Node methods:
		public void display()
		{
			data.display();
		}
		public Edge getData()
		{
			return this.data;
		}
	} 
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////PRIVATE CLASS EDGE//////////////////////////////////////////////

	private class Edge 
	{
            /*
            * Fields for edge
            */
		private int weight;
		private int Node_0;
		private int Node_1;
		private boolean state;

		//Edge constructors:
		public Edge(int a, int b, int edgeWeight)
		{
			state = true;
			this.Node_0=a;
			this.Node_1=b;
			this.weight=edgeWeight;
		}
		public Edge(Edge e)
		{
			this(e.Node_0, e.Node_1, e.weight);
		}
/////////////////////////EDGE METHODS BELOW/////////////////////////////////////////
		public void setWeight(int w)
		{
			this.weight=w;
		}
		public int getVertexA()
		{
			return Node_0;
		}
		public int getVertexB()
		{
			return Node_1;
		}

		public Edge reverse() //returns a new Edge object whose vertices are reversed, and whose weight is the same as the parent object. This method is utilized when inserting new edges into the Adjacency List
		{
			return new Edge(Node_1, Node_0, weight);
		}
		public void display() //displays status
		{
			System.out.println (
									   "Link from Node " + Node_0 + " to Node " + Node_1 + ":\n" 
									 + "\n   Link Status  . . . . . . . . . . . . . : " + curState(state) + "\n"
									 + "   Node " + Node_1 + " . . . . . . . . . . . . . . . . : " + curState(list[Node_1].getStatus()) + "\n"
									 + "   Node " + Node_0 + " . . . . . . . . . . . . . . . . : " + curState(list[Node_0].getStatus()) + "\n"
									 + "   Connection Latency . . . . . . . . . . : " + weight + "\n"
							   );
		}
		public int someVertex() //returns a random vertex.
		{
			int num;
			Random rand = new Random();
			num = rand.nextInt();
			num = num % 2;
			if (num==0) return Node_0;
			else return Node_1;
		}
		public int adjacentVertex(int v) //returns the vertex opposite of parameter v
		{
			if (v==Node_0) return Node_1;
			else return Node_0;
		}
		public int getWeight()
		{
			return this.weight;
		}
		public boolean isActive()
		{
			return this.state;
		}
		public void setState(boolean b)
		{
			this.state=b;
		}
		public boolean connectsNode(int node)
		{
			if ((Node_0==node) || (Node_1==node))
			{
				return true;
			}
			else return false;
		}
		private String curState(boolean b)
		{
			if (b==true) return new String("Active");
			else return new String("Inactive");
		}
	} 
        //////////////////////////////////////////////////////////////////////////////
} 
