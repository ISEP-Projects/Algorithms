package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Route {

	private int route_id;
	
	private int trip_id;	//Taking only 1 trip data
	private Trip trip;
	
	//private ArrayList<Integer> trip_idList = new ArrayList<Integer>();	//id of all trips so that complete trip object can be added to respective route
	//private LinkedList<Stop> stopsList = new LinkedList<Stop>(); 
	//private LinkedList<Trip> tripsList = new LinkedList<Trip>();
	
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

	public int getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	@Override
	public String toString() {
		return "Route [route_id=" + route_id + ", trip_id=" + trip_id + ", trip=" + trip + "]";
	}

	
	
}
