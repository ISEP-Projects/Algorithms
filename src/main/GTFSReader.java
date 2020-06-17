package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import model.Route;
import model.Stop;
import model.Trip;
import model.Edge;

public class GTFSReader {

	HashMap<String, Stop> stopsList;
	HashMap<Integer, Trip> tripsList;
	HashMap<Integer, Route> routesList;
	
	HashMap<String,Edge> edgesList;
	
	public void stopsReader(String filePath) {
		
		stopsList = new HashMap<>();
		
		File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	        String line;
	        if((line = br.readLine()) != null) {
	        	String[] splitLine = line.split(",");
	        	for (int i = 0; i < splitLine.length; i++) {
	              //System.out.print(splitLine[i] + "\t");
	        	}
	        }
	        int id = 1; //Number id that can be used instead of the text stop_id. Maybe more useful?
	        while ((line = br.readLine()) != null) {
	        	
	        	String[] splitLine = line.split(",");
	        	
	        	if(splitLine.length == 9) {
		        	String stop_id = splitLine[0];
		        	String stop_name = splitLine[2];
		        	Double stop_lat = Double.parseDouble(splitLine[3]);
		        	Double stop_lon= Double.parseDouble(splitLine[4]);
		        	
		        	Stop s = new Stop(id,stop_id,stop_name,stop_lat,stop_lon);
		        	stopsList.put(stop_id, s);	//Add newly created stop to Hashmap with stop_id as the key
		        	//System.out.println(stopsList.get(stop_id).toString());
		        	id++;
	        	}

	        }
	        System.out.println(filePath + " successfully parsed");
	        System.out.println(stopsList.size() + " stops have been created");
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
	}
	
	//trips.txt
	public void tripsReader(String filePath) {
		
		tripsList = new HashMap<>();
		routesList = new HashMap<>();
		//Extract required data from trips.txt
		File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	        String line;
	        if((line = br.readLine()) != null) {
	        	String[] splitLine = line.split(",");
	        	for (int i = 0; i < splitLine.length; i++) {
	        		
	        	}
	        }
	        
	        while ((line = br.readLine()) != null) {
	        	
	        	String[] splitLine = line.split(",");
	        	
	        	if(splitLine.length == 3) {
		        	int route_id = Integer.parseInt(splitLine[0]);
		        	int trip_id = Integer.parseInt(splitLine[2]);
		        	
		        	//Create new Trip
		        	Trip t = new Trip(trip_id,route_id);
	        				        	
		        	//Create Route and add to list if not added
		        	if(!routesList.containsKey(route_id)) {
		        		Route r = new Route(route_id);
		        		r.addTrip(t);
		        		tripsList.put(trip_id, t);
		        		routesList.put(route_id, r);
	        		//If route already created just add the trip
		        	} else {		        		
		        		tripsList.put(trip_id, t);
		        		routesList.get(route_id).addTrip(t);
		        	}
		        	//routeList.get(route_id);
		        	
	        	}
	        }
	        System.out.println(filePath + " successfully parsed");
	        System.out.println(routesList.size() + " routes have been created");
	        
	        /*
        	for (Entry<Integer, Route> r : routesList.entrySet()) {
        		System.out.println("\nRoute:" + r.getValue().getRoute_id());
            	for (Entry<Integer, Trip> temp : r.getValue().getTripsList().entrySet()) {
        		    System.out.print("Trip:" + temp.getValue().getTrip_id() + "\t");
        		}

        	}
        	*/
	        
	        
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	//Parsing stop_times.txt 
	public void stop_timesReader(String filePath) {
		
		File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	
        	String line;
	        if((line = br.readLine()) != null) {
	        	String[] splitLine = line.split(",");
	        	for (int i = 0; i < splitLine.length; i++) {
	        	}
	        }
	        
	        
	        while ((line = br.readLine()) != null ) {
	        	
	        	String[] splitLine = line.split(",");
	        	
	        	if(splitLine.length == 7) {
		
		        	int trip_id = Integer.parseInt(splitLine[0]);
		        	String stop_id = splitLine[3];
		        	int stop_sequence = Integer.parseInt(splitLine[4]);
		        	
		        	//System.out.println("Read Data " +trip_id + "\t" + stop_id + "\t" + stop_sequence);
					
		        	Stop temp = stopsList.get(stop_id);
		        	
		        	int id = temp.getId();
		        	String stop_name = temp.getStop_name();
		        	Double stop_lat = temp.getStop_lat();
		        	Double stop_lon = temp.getStop_lon();
		        	int route_id = tripsList.get(trip_id).getRoute_id();
		        	
	        		//Create new stop with all the data
		        	//Stop(int id, String stop_id, String stop_name, Double stop_lat, Double stop_lon, int stop_sequence, int route_id)
	        		Stop s = new Stop(id,stop_id,stop_name,stop_lat,stop_lon,stop_sequence,route_id);	        		
    				tripsList.get(trip_id).addStopList(s);
    				//System.out.println("Trip: " + tripsList.get(trip_id).getTrip_id() + "\tStop:" + stop_id  + " Route_id = " + tripsList.get(trip_id).getStop(stop_id).getRoute_id());	        	
	        	}
	        	       	
	        }
	        
	        /*
	        for(int i = 1; i< 10; i++) {
	        	System.out.println("\nTrip id:" + tripsList.get(i).getTrip_id());
	    		for (Entry<String, Stop> l : tripsList.get(i).getStopsList().entrySet()) {
			    	System.out.println("Route_id: " +l.getValue().getRoute_id() +"  Stop id:" + l.getValue().getStop_id() + "  Sequence:" +l.getValue().getStop_sequence()); 
			    }	
	        }
	        */
	        
	        //All trips with their respective stops and unique stop_sequences have been created
	       //Add trips to their respective routes by matching trip_id
        	
	        	//Loop through all the Routes in the list and add list of trips
        	for (Entry<Integer, Route> r : routesList.entrySet()) {
        		//System.out.println("Route:" +r.getValue().getRoute_id());        		
        		for (Entry<Integer, Trip> trip : r.getValue().getTripsList().entrySet()) {
        			//Trip t = tripsList.get(trip.getValue().getTrip_id());
        			//r.getValue().updateTrip(t);
        			//System.out.println("Trip: " + trip.getValue().getTrip_id());
        			for (Entry<String, Stop> stop : trip.getValue().getStopsList().entrySet()) {
        				int stop_sequence = tripsList.get(trip.getValue().getTrip_id()).getStop(stop.getValue().getStop_id()).getStop_sequence();
        				stop.getValue().setStop_sequence(stop_sequence);
        				//System.out.println("Id: " +stop.getValue().getStop_id() +" Sequence: " + stop.getValue().getStop_sequence());
        			}
    			}
      		
        	    //System.out.print(r.getValue().getRoute_id() + ":");
        	}
        	
        		
        	for (Entry<Integer, Route> r : routesList.entrySet()) {
	        	//System.out.print("Route: " +r.getValue().getRoute_id() + "\tTrip: " + r.getValue().getTrip().getTrip_id() +"\nStops:");
	        	//for (Entry<String, Stop> list : r.getValue().getTrip().getStopsList().entrySet()) {
	        		//System.out.println("Id: " +list.getValue().getStop_id() +" Sequence: " + list.getValue().getStop_sequence());
	        	//}
	        	
        	}
        	
        	
	        System.out.println(filePath + " successfully parsed");
	        System.out.println(tripsList.size() + " trips have been created");
	        //System.out.println(routeList.toString());
	        
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/*
	public static HashMap<String,Stop> sortByValue(HashMap<String,Stop> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String,Stop> > list =  new LinkedList<Map.Entry<String,Stop> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String,Stop> >() { 
            public int compare(Map.Entry<String,Stop> e1,Map.Entry<String,Stop> e2){ 
                
            	int v1 = e1.getValue().getStop_sequence(); 
            	int v2 = e2.getValue().getStop_sequence(); 
            	//res=(num1>num2) ? (num1+num2):(num1-num2)
            	int min;
            	min = (v1<v2) ? v1 : v2;
            	return min;
            	//return (o1.getValue()).compareTo(o2.getValue().ge); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String,Stop> temp = new LinkedHashMap<String,Stop>(); 
        for (Map.Entry<String,Stop> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
	*/
	
	
	//Calculate distance between two points
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		
		double R = 6371000; //Earth's radius in metres
		double p1x, p1y, p1z, p2x, p2y, p2z;
		
		//Conversion to radians
		lat1 = lat1 * Math.PI / 180;
		lon1 = lon1 * Math.PI / 180;
		lat2 = lat2 * Math.PI / 180;
		lon2 = lon2 * Math.PI / 180;
		
		//Cartesian Co-ordinates
		p1x = R*Math.cos(lat1)*Math.cos(lon1);
		p1y = R*Math.cos(lat1)*Math.sin(lon1);
		p1z = R*Math.sin(lat1);
		
		p2x = R*Math.cos(lat2)*Math.cos(lon2);
		p2y = R*Math.cos(lat2)*Math.sin(lon2);
		p2z = R*Math.sin(lat2);
		
		double x = (p2x - p1x) * (p2x - p1x);
		double y = (p2y - p1y) * (p2y - p1y);
		double z = (p2z - p1z) * (p2z - p1z);
		
		//double test = R * Math.sqrt( (lat2-lat1)*(lat2-lat1) + Math.cos(lat1+lat2)*Math.cos(lat1+lat2)*(lon2-lon1)*(lon2-lon1) );
		//return test;
		//double distance = R* Math.acos( p1x*p2x + p1y*p2y + p1z*p2z );
		
		double distance = Math.sqrt((x + y + z));
		
		return distance;
		}
	
	
	public void createEdges() {

		edgesList = new HashMap<String,Edge>();
		
		System.out.println("Number of routes:" +routesList.size());
		for (Entry<Integer, Route> rList : routesList.entrySet()) {
			//System.out.println("Route: " + rList.getValue().getRoute_id());
			int max = 0, tripMax = 0;
			
			//Find Trip with the most Stops = The trip that visits all the stations			
			HashMap<String,Stop> maxStopsList = null;
			for (Entry<Integer, Trip> tList : rList.getValue().getTripsList().entrySet()) {
				if(tList.getValue().getStopsList().size() > max) {
					maxStopsList = tList.getValue().getStopsList();
					max = maxStopsList.size();
					tripMax = tList.getValue().getTrip_id();
				}
			}
			
			//Sort List in order of sequence			
			List<Stop> newOrderedStopsList = new ArrayList<>(maxStopsList.values());
			Collections.sort(newOrderedStopsList);
			
			if(newOrderedStopsList.size()>1) {
				Iterator<Stop> iterator = newOrderedStopsList.iterator();
				//Object[] array = newOrderedStopsList.toArray();
				
				for(int i = 0; i< newOrderedStopsList.size()-1; i++) {
					String id = newOrderedStopsList.get(i).getStop_id() + newOrderedStopsList.get(i+1).getStop_id();
		        	int node1 = newOrderedStopsList.get(i).getId(); 
		        	int node2 = newOrderedStopsList.get(i+1).getId();
		        	
		        	double lat1 = newOrderedStopsList.get(i).getStop_lat();
		        	double lon1 = newOrderedStopsList.get(i).getStop_lon();
		        	double lat2 = newOrderedStopsList.get(i+1).getStop_lat() ;
		        	double lon2 = newOrderedStopsList.get(i+1).getStop_lon();		        	
		        	double distance = distance(lat1, lon1, lat2, lon2);
		        	
		        	Edge e = new Edge(id, node1, node2, distance);
		        	
		        	//Check if the edge exists but with the nodes swapped before adding a new one
		        	String id2 = newOrderedStopsList.get(i+1).getStop_id() + newOrderedStopsList.get(i).getStop_id();

		        	//Add to list only if it doesn't exist already
		        	if(!edgesList.containsKey(id) && !edgesList.containsKey(id2)) {
		        		edgesList.put(id, e);
		        		//System.out.println("Created: " + e.toString());
		        	}
					
				}

			}
			/* 
			for(Edge temp : edgesList) {
				System.out.println(temp.toString());
			}
			*/			
		}
		System.out.println("\tEdges created: " + edgesList.size());

	}
	
	public void outputStopsFile() throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter("src/Output-Data/stops.txt");
			writer.write("id, stop_id, stop_name, latitude, longitude\n");
			for (Entry<String, Stop> stop : stopsList.entrySet()) {				
				writer.write(Integer.toString(stop.getValue().getId() ) + "," + stop.getValue().getStop_id() + "," 
			+ stop.getValue().getStop_name() + "," + Double.toString(stop.getValue().getStop_lat() ) 
			+ "," + Double.toString(stop.getValue().getStop_lon() ) +  "\n");	
			}
		}catch (IOException e) {
            e.printStackTrace();
        }
		writer.close();
	}
	
	public void outputEdgesFile() throws IOException {
		
		//HashSet<Edge> edgesList = new HashSet<Edge>();
		
		FileWriter writer = null;
		try {
			writer = new FileWriter("src/Output-Data/edges.txt");
			writer.write("Node1, Node2, Distance\n");
			for (Entry<String, Edge> edge : edgesList.entrySet()) {			
				//System.out.println(Integer.toString(edge.getNode1()) + "," + Integer.toString(edge.getNode2()) + "," + Double.toString(edge.getDistance()) +  "\n");
				writer.write(Integer.toString(edge.getValue().getNode1()) + "," + Integer.toString(edge.getValue().getNode2()) 
				+ "," + Double.toString(edge.getValue().getDistance()) +  "\n");	
			}
		}catch (IOException e) {
            e.printStackTrace();
        }
		writer.close();
	}

	
	public void checkresults() {
		int check = 0;
		//Loop through all routes
		for (Entry<Integer, Route> r : routesList.entrySet()) {
			System.out.println("Route: " +r.getValue().getRoute_id());
			for (Entry<Integer, Trip> temp : r.getValue().getTripsList().entrySet()) {
				System.out.println("Trip: " + temp.getKey());
				for (Entry<String, Stop> entrys : temp.getValue().getStopsList().entrySet()) {
				    Stop s = entrys.getValue();
				  System.out.println("Stop: " + s.getStop_id() + "\tStop_Sequence = " +s.getStop_sequence()+ "\t Route_id = " +s.getRoute_id());
				}
			}
			check++;
			if(check == 500)
				break;
    	}/**/
	}
	
	public static void main(String[] args) {
		String filepath = "src/GTFS-Data/stops.txt";
		
		GTFSReader gtfs = new GTFSReader();
		gtfs.stopsReader(filepath);

		filepath = "src/GTFS-Data/trips.txt";
		gtfs.tripsReader(filepath);
		
		filepath = "src/GTFS-Data/stop_times.txt";
		gtfs.stop_timesReader(filepath);
		
		gtfs.createEdges();
		
		try {
			gtfs.outputEdgesFile();
			gtfs.outputStopsFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//gtfs.checkresults();
	}

}
