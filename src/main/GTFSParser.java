package main;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GTFSParser {
    /*
    
    1) Convert degrees to radians
	2) Calculate Cartesian co-ordinates First
		R≈6371km
		x = Rcos(lat) cos(long)
		y = Rcos(lat) sin(long)
		z = R sin(lat)	//Can actually be re
		
	3) Euclidean Distance Formula
 		
 		dist((x, y, z), (a, b, c)) = √(x - a)² + (y - b)² + (z - c)²	
 	
 		R(θ2−θ1)2+cos2θ(ϕ2−ϕ1)2√.
 		
     */
	
    public static double distance(double lat1, double lon1, double lat2,
                                  double lon2) {
    	
    	double R = 6371000; //Earth's radius in metres
    	double p1x, p1y, p1z, p2x, p2y, p2z;
    	
    	//Conversion to radians
    	lat1 = lat1 * Math.PI / 180;
    	lat2 = lat2 * Math.PI / 180;
    	lon1 = lon1 * Math.PI / 180;
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
    
    
    public static void main(String[] args) throws IOException {
        String[] Lines = {"Underground"};

        // original id -> new id
        HashMap<String, String> stopIdRel = new HashMap<>();
        // new id <-> stop name
        HashMap<Integer, Map> stopMap = new HashMap<>();
        HashMap<String, String> stopBiMap = new HashMap<>();

        // get stops
        Integer stopIdIndex = 0;
        HashSet<String> stopNames = new HashSet<>();
        for (String Line : Lines) {
            
            String fileName = "src/GTFS-Data/stops.txt";
            FileInputStream stream = new FileInputStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(streamReader);
            reader.readLine(); // ignore title row
            Integer beforeIdIndex = stopIdIndex;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineData = CSVUtils.parseLine(line).toArray(new String[0]);
                String stopName = StringUtils.stripAccents(lineData[2]).toUpperCase();
                String latitude = lineData[3];
                String longitude = lineData[4];
                if (!stopNames.contains(stopName)) {
                    stopNames.add(stopName);
                    HashMap<String, Object> stopDetail = new HashMap<>();
                    stopDetail.put("name", stopName);
                    stopDetail.put("latitude", latitude);
                    stopDetail.put("longitude", longitude);
                    stopDetail.put("degree", 0);
                    stopMap.put(stopIdIndex, stopDetail);
                    stopBiMap.put(stopName, stopIdIndex.toString());
                    // build relationship between new id and stop name
                    stopIdIndex++;
                }
                String stopId = lineData[0];
                stopIdRel.put(stopId, stopBiMap.get(stopName));
                // build relationship between old id and new id
                // different original id can connect to the same new id
            }
            reader.close();
            streamReader.close();
            stream.close();
            System.out.println("parsed: " + fileName + ", " + (stopIdIndex - beforeIdIndex) + " stops.");
        }
        System.out.println("all stops parsed, " + stopNames.size() + " stops in total.");

        FileWriter jsonWriter = new FileWriter("src/Output-Data/stop-detail.json");
        Gson gson = new Gson();
        String json = gson.toJson(stopMap);
        jsonWriter.write(json);
        jsonWriter.close();

        // get sequences
        Integer seqCount = 0;
        HashMap<String, ArrayList<ArrayList<String>>> validSequences = new HashMap<>();
        for (String Line : Lines) {
            validSequences.put(Line, new ArrayList<>());
            // trip id -> route id
            HashMap<String, String> tripRouteRel = new HashMap<>();
            //String fileName = "src/GTFS-Data/stops.txt";
            String fileName1 = "src/GTFS-Data/trips.txt";
            FileInputStream stream1 = new FileInputStream(fileName1);
            InputStreamReader streamReader1 = new InputStreamReader(stream1);
            BufferedReader reader1 = new BufferedReader(streamReader1);
            reader1.readLine(); // ignore title row
            String line1;
            while ((line1 = reader1.readLine()) != null) {
                String[] lineData = CSVUtils.parseLine(line1).toArray(new String[0]);
                String routeId = lineData[0];
                String tripId = lineData[2];
                tripRouteRel.put(tripId, routeId);
            }
            HashSet<String> visitedRoute = new HashSet<>();
            HashSet<ArrayList<String>> stopSeqSet = new HashSet<>();
            HashMap<String, ArrayList<String>> stopSeqRel = new HashMap<>();
            String fileName = "src/GTFS-Data/stop_times.txt";
            FileInputStream stream = new FileInputStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(streamReader);
            reader.readLine(); // ignore title row
            String line2;
            while ((line2 = reader.readLine()) != null) {
                String[] lineData = CSVUtils.parseLine(line2).toArray(new String[0]);
                String tripId = lineData[0];
                String stopId = lineData[3];
                String routeId = tripRouteRel.get(tripId);
                if (!stopSeqRel.containsKey(tripId) && !visitedRoute.contains(routeId)) {
                    stopSeqRel.put(tripId, new ArrayList<>());
                    visitedRoute.add(routeId);
                }
                ArrayList <String> stopSeq = stopSeqRel.get(tripId);
                if (stopSeq == null) {
                    continue;
                }
                stopSeq.add(stopId);
            }
            stopSeqRel.forEach((s, oldSeq) -> {
                ArrayList<String> newSeq = new ArrayList<>();
                for (String anOldSeq : oldSeq) {
                    newSeq.add(stopIdRel.get(anOldSeq));
                }
                if (!stopSeqSet.contains(newSeq)) {
                    stopSeqSet.add(newSeq);
                }
            });
            reader.close();
            streamReader.close();
            stream.close();
            seqCount += stopSeqSet.size();
            validSequences.get(Line).addAll(stopSeqSet);
            System.out.println("parsed: " + fileName + ", " + stopSeqSet.size() + " sequences.");
        }
        System.out.println("all sequences parsed, " + seqCount.toString() + " sequences in total.");

        // build link between nodes
        HashSet<String> linkedEdge = new HashSet<>();
        for (String Line : Lines) {
            ArrayList<ArrayList<String>> validSequence = validSequences.get(Line);
            for (ArrayList<String> validSeq : validSequence) {
                for (int i = 0; i < validSeq.size() - 1; i++) {
                    String strId1 = validSeq.get(i);
                    String strId2 = validSeq.get(i + 1);
                    String str1 = strId1 + " " + strId2;
                    String str2 = strId2 + " " + strId1;
                    if (!linkedEdge.contains(str1) && !linkedEdge.contains(str2)) {
                        Integer id1 = Integer.parseInt(strId1);
                        Integer id2 = Integer.parseInt(strId2);
                        Integer stopDegree1 = (Integer) stopMap.get(id1).get("degree");
                        stopMap.get(id1).put("degree", stopDegree1 + 1);
                        Integer stopDegree2 = (Integer) stopMap.get(id2).get("degree");
                        stopMap.get(id2).put("degree", stopDegree2 + 1);
                        linkedEdge.add(str1);
                    }
                }
            }
        }
        FileWriter writer1 = new FileWriter("src/Output-Data/stop-rel.txt");
        writer1.write("stopId,stopName,stopDegree,stopLatitude,stopLongitude\n");
        stopMap.forEach((s1, s2) -> {
            try {
                writer1.write(s1 + "," + s2.get("name") + "," + s2.get("degree") + "," + s2.get("latitude") + "," + s2.get("longitude") + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer1.close();

        FileWriter writer2 = new FileWriter("src/Output-Data/valid-sequence.txt");
        validSequences.forEach((s, arrayLists) -> {
            try {
                for (ArrayList<String> arrayList : arrayLists) {
                    writer2.write(s + ": " + arrayList + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer2.close();

        FileWriter writer3 = new FileWriter("src/Output-Data/edge.txt");
        writer3.write("Node1, Node2, Distance\n");
        linkedEdge.forEach(s -> {
            try {
                String[] vw = s.split(" ");
                Integer s1 = Integer.parseInt(vw[0]);
                Integer s2 = Integer.parseInt(vw[1]);
                Map stop1 = stopMap.get(s1);
                Map stop2 = stopMap.get(s2);
                double lat1 = Double.parseDouble((String) stop1.get("latitude"));
                double lon1 = Double.parseDouble((String) stop1.get("longitude"));
                double lat2 = Double.parseDouble((String) stop2.get("latitude"));
                double lon2 = Double.parseDouble((String) stop2.get("longitude"));
                double dis = distance(lat1, lon1, lat2, lon2);
                writer3.write(Integer.toString(s1) + "," + Integer.toString(s2) + "," + Double.toString(dis) +  "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer3.close();
    }
}
