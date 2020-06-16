package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import model.Route;
import model.Stop;
import model.Trip;

public class GTFSReader {

	HashMap<String, Stop> stopsList;
	HashMap<Integer, Trip> tripsList;
	HashMap<Integer, Route> routesList;
	
	
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
		        			        	
		        	//Create Route and add to list if not added
		        	if(!routesList.containsKey(route_id)) {
		        		Route r = new Route(route_id);
		        		//r.setTrip_id(trip_id);
		        		Trip t = new Trip(trip_id);
		        		t.setRoute_id(route_id);
		        		//r.setTrip(t);
		        		r.addTrip(t);
		        		tripsList.put(trip_id, t);
		        		routesList.put(route_id, r);
		        	} else {
		        		Trip t = new Trip(trip_id);
		        		t.setRoute_id(route_id);
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
	
	public void createEdges() {

		for (Entry<Integer, Route> rList : routesList.entrySet()) {
        	for (Entry<Integer, Trip> tList : rList.getValue().getTripsList().entrySet()) {
        		TreeMap<Integer, Stop> newOrderedRouteList = new TreeMap<Integer, Stop>();
        		for (Entry<String, Stop> sList : tList.getValue().getStopsList().entrySet()) {
        			newOrderedRouteList.put(sList.getValue().getStop_sequence(), sList.getValue());
        		}
        		//System.out.println(newOrderedRouteList.toString());
        	}     	
        	//System.out.println("Id: " +list.getValue().getStop_id() +" Sequence: " + list.getValue().getStop_sequence());	
    	}
		
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
		
		//gtfs.checkresults();
	}

}
