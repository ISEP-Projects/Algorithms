package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EdgeWeightedGraph {


    private int capacity = 0;   // Max-capacity of graph
    private int numNodes = 0; // number of Nodes
    private int numEdges = 0; // number of edge

    // Array of lists for Adjacency List Representation
    private LinkedList<Edge>[] adj;

    // Constructor
    public EdgeWeightedGraph(int v) {
        capacity = v;
        adj = new LinkedList[capacity];
    }

    // Constructor: build graph from file
    public EdgeWeightedGraph(String filePath) throws IOException {
        
    	//Use HashSet instead of an ArrayList because it won't allow duplicate values to be added
    	//Since nodes will be repeated when reading the edges from the file, in order to ensure a node isn't added multiple times the HashSet is used.
    	HashSet<Integer> set = new HashSet<>();
 
        
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	        String line;
	        if((line = br.readLine()) != null) {
	        	String[] splitLine = line.split(",");
	        	for (int i = 0; i < splitLine.length; i++) {
	              //System.out.print(splitLine[i] + "\t");
	        	}
	        	//System.out.println("\n=====================================================================");
	        }
	        while ((line = br.readLine()) != null) {
	        	String[] splitLine = line.split(",");
	        	set.add(Integer.parseInt(splitLine[0]));
	        	set.add(Integer.parseInt(splitLine[1]));
	        	//System.out.println(splitLine[0] + "\t" + splitLine[1] + "\t" + splitLine[2]);

	        }
        }   
        
        /*
        for (Integer temp : set) { 
        	System.out.println(temp );
        }
        */
        
        //Missing Nodes 151, 201, 320, 353
        
        //Find Largest number in the set
        ArrayList<Integer> nodeIds = new ArrayList<Integer>(set);
        int size = Collections.max(nodeIds) + 1;
        
        //System.out.println("Number of nodes: " + size);
        capacity = size;
        //size = 2591;
        adj = new LinkedList[capacity];
        for (int i = 0; i < capacity; ++i)
            addVertex(i);
	        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {     
	        String line;
	        
	        //To skip first line where there is text
	        if((line = br.readLine()) != null) {
	        	String[] splitLine = line.split(",");
	        }
	        
	        while ((line = br.readLine()) != null) {
	        	String[] splitLine = line.split(",");
	        	
	        	//If given text file contains only 2 parameters(The 2 nodes) then that will be entered into the unweighted graph. 
	        	//1 will be automatically assigned as the weight to all edges
	        	if(splitLine.length == 2) {
	        		addEdge( new Edge(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1])) );
	        	//If there are 3 parameters( 2 nodes & weight of the edge) then a weighted graph is created
	        	}else if(splitLine.length == 3) {
	        		//System.out.println("Creating edge: " +splitLine[0] + "-" + splitLine[1] + " with weight: " + splitLine[2]);
	        		addEdge( new Edge(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]) ) );
	        	}
	        }
        }
     
    }

    public void addVertex(int v) {
        adj[v] = new LinkedList<>();
        numNodes = numNodes + 1;
    }

    // Function to add an edge into the graph
    public void addEdge(Edge e) {
    	//Include Nodes in each others' list
        adj[e.from()].add(e);  
        adj[e.to()].add(e); 
        //System.out.println("Adding edge between " + e.from() + " - " + e.to());
        numEdges++;
    }

    public LinkedList<Edge> getNode(int v) {
        return adj[v];
    }

    public Edge getEdge(int v, int w) {
        LinkedList<Edge> source = getNode(v);
        for (Edge edge : source) {
            //Checks if edge is connected to both nodes
            if (edge.other(v) == w) {
                return edge;
            }
        }
        return null;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public int getCapacity() {
        return capacity;
    }

    public HashSet<Edge> edges() {
        HashSet<Edge> edges = new HashSet<>();
        for (LinkedList<Edge> adjItem : adj) {
            edges.addAll(adjItem);
        }
        return edges;
    }

    public void removeEdge(Edge e) {
        for (int i = 0; i < capacity; i++) {
            if (adj[i] != null) {
                for (Edge n : adj[i]) {
                    if (n == e) {
                        adj[i].remove(e);
                        break;
                    }
                }
            }
        }
    }

    // Print the whole graph
    public void print() {
        for (int i = 0; i < capacity; i++) {
            System.out.print(i + ": ");
            if (adj[i] != null) {
                for (Edge n : adj[i]) {
                    System.out.print(n + ", ");
                }
            }
            System.out.println();
        }
    }

}
