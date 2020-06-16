package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Route {

	private int route_id;
	//private ArrayList<Integer> trip_idList = new ArrayList<Integer>();	//id of all trips so that complete trip object can be added to respective route
	//private LinkedList<Stop> stopsList = new LinkedList<Stop>(); 
	private HashMap<Integer, Trip> tripsList = new HashMap<Integer, Trip>();
	
	//Constructor
    public Route(int route_id){
    	this.route_id = route_id;
    }
	
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
	
	public void addTrip(Trip t) {
		tripsList.put(t.getTrip_id(), t);
	}

	public HashMap<Integer, Trip> getTripsList() {
		return tripsList;
	}

	public void setTripsList(HashMap<Integer, Trip> tripsList) {
		this.tripsList = tripsList;
	}
	
	public Trip getTrip(int n) {
		return tripsList.get(n);
	}
	
	public void updateTrip(Trip t) {
		tripsList.remove(t.getTrip_id());
		tripsList.put(t.getTrip_id(), t);
	}

	@Override
	public String toString() {
		return "Route [route_id=" + route_id + ", tripsList=" + tripsList + "]";
	}
	
	
	
}
