package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Route {

	private int route_id;
	private ArrayList<Integer> trip_idList = new ArrayList<Integer>();	//id of all trips so that complete trip object can be added to respective route
	private LinkedList<Stop> stopsList = new LinkedList<Stop>(); 
	private LinkedList<Trip> tripsList = new LinkedList<Trip>();
	

    public Route(int route_id){
    	this.route_id = route_id;
    }
	
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
	
	public void addTrip_id(int trip_id) {
		trip_idList.add(trip_id);
	}
	
	public ArrayList<Integer> getTrip_idList() {
		return trip_idList;
	}
	public void setTrip_idList(ArrayList<Integer> trip_idList) {
		this.trip_idList = trip_idList;
	}
	
	public void addTrip(Trip t) {
		tripsList.add(t);
	}
	
	public LinkedList<Stop> getStopsList() {
		return stopsList;
	}
	public void setStopsList(LinkedList<Stop> stopsList) {
		this.stopsList = stopsList;
	}

	@Override
	public String toString() {
		return "Route [route_id=" + route_id + ", trip_idList=" + trip_idList + ", stopsList=" + stopsList + "]";
	}
	
	

	
	
	
	
	
}
