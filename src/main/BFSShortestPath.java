package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;


public class BFSShortestPath {

    private int sourceNode;
    private boolean[] marked;
    private Edge[] edgeTo;
    private int[] distance;

    public BFSShortestPath() {
        sourceNode = -1;
        marked = null;
        edgeTo = null;
        distance = null;
    }

    public BFSShortestPath(BFSShortestPath aBFSShortestPath) {
        sourceNode = aBFSShortestPath.sourceNode;
        marked = aBFSShortestPath.marked;
        edgeTo = aBFSShortestPath.edgeTo;
        distance = aBFSShortestPath.distance;
    }

    public ArrayList<ArrayList<Integer>> bfsAll(EdgeWeightedGraph G) {
        int v = G.getNumNodes() + 100;
        marked = new boolean[v];
        edgeTo = new Edge[v];
        distance = new int[v];
        for (int i = 0; i < v; i++) {
            edgeTo[i] = null; // UNDEFINED
            distance[i] = Integer.MAX_VALUE; // +INFINITY
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int node:G.getNodeset()) {
            if (!marked[node]) {
                result.add(parse(G, node));
            }
        }
        return result;
    }

    private ArrayList<Integer> parse(EdgeWeightedGraph G, int s) {
        sourceNode = s;
        // Use an array list to record visit orders.
        ArrayList<Integer> visitOrder = new ArrayList<>();
        // Perform search algorithm without recursive calls.
        // BFS uses Queue data structure.
        Queue<Integer> queue = new LinkedList<>();
        Queue<Integer> distanceQueue = new LinkedList<>();
        // Put root node into queue
        // Put distance into queue (correspond to node)
        queue.add(s);
        marked[s] = true;
        edgeTo[s] = null;
        distanceQueue.add(0);
        distance[s] = 0;
        while (!queue.isEmpty()) {
            int node = queue.remove();
            int nodeDistance = distanceQueue.remove();
            visitOrder.add(node);
            // In case of choice, the vertex with the smallest identifier will be chosen.
            //System.out.println(G.getNode(node));
            for (Edge childEdge : G.getNode(node)) {
                int thisNode = childEdge.other(node);
                if (!marked[thisNode]) {
                    queue.add(thisNode);
                    // Mark child node
                    marked[thisNode] = true;
                    edgeTo[thisNode] = childEdge;
                    // Update distance
                    distanceQueue.add(nodeDistance + 1);
                    distance[thisNode] = nodeDistance + 1;
                }
            }
        }
        return visitOrder;
    }

    // The function to do BFS traversal.
    public ArrayList<Integer> bfs(EdgeWeightedGraph G, int s) {
        int v = G.getNumNodes() + 100;
        marked = new boolean[v];
        edgeTo = new Edge[v];
        distance = new int[v];
        for (int i = 0; i < v; i++) {
            edgeTo[i] = null; // UNDEFINED
            distance[i] = Integer.MAX_VALUE; // +INFINITY
        }
        return parse(G, s);
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public int distTo(int v) {
        return distance[v];
    }

    public Edge edgeTo(int v) { return edgeTo[v]; }

    public ArrayList<Integer> pathTo(int w) {
        ArrayList<Integer> shortestPath = new ArrayList<>();
        int thisNode = w;
        while (thisNode > -1 && thisNode != sourceNode) {
            shortestPath.add(thisNode);
            thisNode = edgeTo[thisNode].other(thisNode);
            if (thisNode == sourceNode) {
                shortestPath.add(sourceNode);
                break;
            }
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    public static boolean isConnectedGraph(EdgeWeightedGraph G) {
        Integer total = new BFSShortestPath().bfs(G, 0).size();
        return total == G.getNumNodes();
    }

    public static void main(String[] args) throws IOException {

        EdgeWeightedGraph graph = new EdgeWeightedGraph("src/Output-Data/edges.txt");

        BFSShortestPath bfsSP = new BFSShortestPath();
        bfsSP.bfs(graph, 3711);
        ArrayList<Integer> suggestedPath = bfsSP.pathTo(3952);
        System.out.println("Shortest path from 3711 to 3952 is: ");
        System.out.println(suggestedPath);
        System.out.println("Number of nodes:" + suggestedPath.size());

    }

}