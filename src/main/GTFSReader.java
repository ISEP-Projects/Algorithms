package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.Route;
import model.Stop;
import model.Trip;

public class GTFSReader {

	HashMap<String, Stop> stopsList;
	HashMap<Integer, Trip> tripsList;
	HashMap<Integer, Route> routeList;
	
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
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
	}
	
	public void tripsReader(String filePath) {
		
		tripsList = new HashMap<>();
		routeList = new HashMap<>();
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
		        	if(!routeList.containsKey(route_id)) {
		        		Route r = new Route(route_id);
		        		r.addTrip_id(trip_id);
		        		routeList.put(route_id, r);
		        		//If route already added to the list then just add the new trip_id
		        	} else {
		        		routeList.get(route_id).addTrip_id(trip_id);
		        	}
		        	//routeList.get(route_id);
		        	
	        	}
	        }
	        System.out.println(filePath + " successfully parsed");
	        System.out.println(routeList.size() + " routes have been created");
	        //System.out.println(routeList.toString());
	        
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
	        
	        while ((line = br.readLine()) != null) {
	        	
	        	String[] splitLine = line.split(",");
	        	
	        	if(splitLine.length == 7) {
		        	int trip_id = Integer.parseInt(splitLine[0]);
		        	String stop_id = splitLine[3];
		        	int stop_sequence = Integer.parseInt(splitLine[4]);
		        	//System.out.println(splitLine[0] + "\t" + splitLine[3] + "\t" + splitLine[4]);
		        	
		        	
		        /*	Unecessary as each stop_sequence is unique to each trip. 
		         * Instead it should be assigned after is Stop is added to a particular trip	
        	
		        //Insert stop_sequence information in all stops in the list
		        	
		        	//Check if stop is saved in list and stop sequence isn't set
		        	if(stopsList.containsKey(stop_id)) {
		        		//Checks if stop_sequence is already set or not
		        		if(stopsList.get(stop_id).getStop_sequence() == -1)
		        			stopsList.get(stop_id).setStop_sequence(stop_sequence);
		        		
		        		//If stop doesn't exist
		        	} else {
		        		System.out.println("ERROR! Stop: " + stop_id + " doesn't exist in list!");
		        	}
		        	
		        */	
			        
		        //Create trip object containing all the stops visited
			        	
		        	//Check if trip is already added to list and if not creates a trip & add to tripsList	
		        	if(!tripsList.containsKey(trip_id)){
		        		Trip t = new Trip(trip_id);
		        		tripsList.put(trip_id, t);
		        	}
	        	
		        	//Add Stop to the respective trip
		        	if(stopsList.containsKey(stop_id)) {
	        			Stop s = stopsList.get(stop_id);
	        			s.setStop_sequence(stop_sequence);
	        			tripsList.get(trip_id).addStopList(s);
	        		}else {
	        			System.out.println("ERROR! Stop " + stop_id + " does not exist in stopslist");
	        		}
		        	
	        	}
	        }
	        //All trips with their respective stops and unique stop_sequences have been created
	       //Add trips to their respective routes by matching trip_id
        	
        	//Loop through all the Routes in the list and get their list of trips
        	for (Entry<Integer, Route> entry : routeList.entrySet()) {
        	    ArrayList<Integer> list = entry.getValue().getTrip_idList();
        	    
        	    //Loop through all trip ids in Route and add respective trip object 
        	    for (Integer id : list) {
        	    	entry.getValue().addTrip((tripsList.get(id)) );
                }
        	    System.out.println("Trips added to Route: " + entry.getValue().getRoute_id() + " " + entry.getValue().getTrip_idList());
        	    
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
			
	public static void main(String[] args) {
		String filepath = "src/GTFS-Data/stops.txt";
		
		GTFSReader gtfs = new GTFSReader();
		gtfs.stopsReader(filepath);

		filepath = "src/GTFS-Data/trips.txt";
		gtfs.tripsReader(filepath);
		
		filepath = "src/GTFS-Data/stop_times.txt";
		gtfs.stop_timesReader(filepath);
		
	}

}
