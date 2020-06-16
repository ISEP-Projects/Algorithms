package model;

public class Stop {
	
	private int id;
	private String stop_id;
	private String stop_name;
	private Double stop_lat;
	private Double stop_lon;
	private int stop_sequence;
	
	
	public Stop(int id, String stop_id, String stop_name, Double stop_lat, Double stop_lon) {
		super();
		this.id = id;
		this.stop_id = stop_id;
		this.stop_name = stop_name;
		this.stop_lat = stop_lat;
		this.stop_lon = stop_lon;
		this.stop_sequence = -1;	//Unset value
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStop_id() {
		return stop_id;
	}
	public void setStop_id(String stop_id) {
		this.stop_id = stop_id;
	}
	public String getStop_name() {
		return stop_name;
	}
	public void setStop_name(String stop_name) {
		this.stop_name = stop_name;
	}
	public Double getStop_lat() {
		return stop_lat;
	}
	public void setStop_lat(Double stop_lat) {
		this.stop_lat = stop_lat;
	}
	public Double getStop_lon() {
		return stop_lon;
	}
	public void setStop_lon(Double stop_lon) {
		this.stop_lon = stop_lon;
	}

	public int getStop_sequence() {
		return stop_sequence;
	}

	public void setStop_sequence(int stop_sequence) {
		this.stop_sequence = stop_sequence;
	}

	@Override
	public String toString() {
		return "Stop [id=" + id + ", stop_id=" + stop_id + ", stop_name=" + stop_name + ", stop_lat=" + stop_lat
				+ ", stop_lon=" + stop_lon + "]";
	}
	
	
}
