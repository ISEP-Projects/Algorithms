package model;

import java.util.LinkedList;

public class Trip {

	private int trip_id;
	private LinkedList<Stop> stopsList;
	
	public Trip(int trip_id) {
		this.trip_id = trip_id;
		stopsList = new LinkedList<Stop>();
	}
	
	public int getTrip_id() {
		return trip_id;
	}
	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}
	public LinkedList<Stop> getStopsList() {
		return stopsList;
	}
	public void setStopsList(LinkedList<Stop> stopsList) {
		this.stopsList = stopsList;
	}
	public void addStopList(Stop s) {
		stopsList.add(s);
	}
	
}
