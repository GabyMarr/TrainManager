package main;

import interfaces.List;

import interfaces.Map;
import interfaces.Stack;
import interfaces.HashFunction;
import interfaces.Queue;
import interfaces.Set;
import interfaces.Entry;
import interfaces.List;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
//import java.util.function.Function;

import data_structures.ArrayList;
import data_structures.ArrayListStack;
import data_structures.HashSet;
import data_structures.HashTableSC;
import data_structures.SimpleHashFunction;
public class TrainStationManager {
	private String stationfileLine; 
	private String[] lineArray = new String[3];
	private Map<String, Station> shortestDistanceMap = new HashTableSC<>(13, new SimpleHashFunction());
	private Map<String, List<Station>> neighborStationsMap = new HashTableSC<>(13,new SimpleHashFunction());
	private Set<Station> visitedStationsSet = new HashSet<>();
	private Stack<Station> toVisitStack = new ArrayListStack<>();
	
	private ArrayList<String> stationList = new ArrayList<>(13);
	
	
	
	/**
	 * Constructor for TrainStationManager 
	 * @param takes in stations.csv and sorts the data
	 * 
	 * */
	public TrainStationManager(String station_file) {
		
		
		
		try {
			BufferedReader stationReader = new BufferedReader(new FileReader("inputFiles/" + station_file));
			stationfileLine = stationReader.readLine();
			// makes a list of all the Stations
			while ((stationfileLine = stationReader.readLine()) != null) {

				lineArray = stationfileLine.split(",");

				if(!stationList.contains(lineArray[0])){stationList.add(lineArray[0]);}
				else if(!stationList.contains(lineArray[1])) {stationList.add(lineArray[1]);}
		
			}
			// makes neighborStationMap
			for(int i =0; i<stationList.size(); i++){
				ArrayList<Station> temp = new ArrayList<>();

				stationReader = new BufferedReader(new FileReader("inputFiles/" + station_file));

				
				while ((stationfileLine = stationReader.readLine()) != null) {

					lineArray = stationfileLine.split(",");
					if(stationList.get(i).equals(lineArray[0])){
						Station tempObj = new Station(lineArray[1], Integer.parseInt(lineArray[2]));
						temp.add(tempObj);
					}
					else if(stationList.get(i).equals(lineArray[1])){
			
						Station tempObj = new Station(lineArray[0], Integer.parseInt(lineArray[2]));
						temp.add(tempObj);
					}
				}
				neighborStationsMap.put(stationList.get(i), temp); 
			}
		} 
		catch (IOException e) {	/* TODO Auto-generated catch block */ e.printStackTrace();}
		
		findShortestDistance();
		
		
		
		
		
	}
	
	
	
	
	/**
	 * Dijkestra's Algorithm to find shortest distance
	 * */
	private void findShortestDistance() {
		// Map of how the current shortest routes to each station
		for(int i = 0; i<stationList.size(); i++) {
			Station tempObj = new Station("Westside", Integer.MAX_VALUE);
			shortestDistanceMap.put(stationList.get(i), tempObj);
		}
		// Choosing WestSide as starting Position
		Station startingStationObj = new Station("Westside", 0);
		shortestDistanceMap.put("Westside", startingStationObj);
		toVisitStack.push(startingStationObj);
		// Dijkstra's Algorithm
		
		while(!toVisitStack.isEmpty()) {
			Station currentStation = toVisitStack.pop(); // part a
			visitedStationsSet.add(currentStation); // part b
			List<Station> neighbors = neighborStationsMap.get(currentStation.getCityName()); 
			for(int i = 0; i<neighbors.size(); i++) { // part c
		
				int B = neighbors.get(i).getDistance(); // distance from Bugapest to Westside
				int C = shortestDistanceMap.get(currentStation.getCityName()).getDistance();  // shortest distance to Westside
				int shortestDistance = B + C;
				int A = shortestDistanceMap.get(neighbors.get(i).getCityName()).getDistance(); // current shortest distance

				Station neighborStationObj = new Station(currentStation.getCityName(), shortestDistance);
				if(shortestDistance < A) {

					shortestDistanceMap.put(neighbors.get(i).getCityName(), neighborStationObj);


				}
				Station idkName = new Station(neighbors.get(i).getCityName(), shortestDistance); // bugapest, 55
				if(!visitedStationsSet.isMember(idkName)) {
					
					sortStack(idkName, toVisitStack);
				
				}
				
			}
			
		}
		
	}
	
	
	
	
	/**
	 * Adds and sorts a stack based on closest distance
	 * @param Station - Stack you want added, stackToSort - Stack you are adding to and sorting
	 * */
	public void sortStack(Station station, Stack<Station> stackToSort) {
		ArrayListStack<Station> copyStack = new ArrayListStack<>();
		int dis = station.getDistance();
		if(stackToSort.isEmpty()) {stackToSort.push(station);}
		
		else if(station.getDistance()<stackToSort.top().getDistance()) {
			stackToSort.push(station);
				
		}
		else if(dis>stackToSort.top().getDistance()) {
//			stackToSort.push(station);
			while(!stackToSort.isEmpty()){
				if(dis>stackToSort.top().getDistance()) {
					copyStack.push(stackToSort.pop());
				}
				else {
					
					break;
				}
			}
			stackToSort.push(station);
			while(!copyStack.isEmpty()) {
				stackToSort.push(copyStack.pop());
			}		
	
		}
	}
	
	
	
	
	/**
	 * Returns the travel times each trip takes
	 * 
	 * @return (Map<String, Double>) (String) - closest neighbor , (Double) - travel time
	 * 
	 * 
	 * */
	public Map<String, Double> getTravelTimes() {
		// 5 minutes per kilometer
		// 15 min per station
		Map<String, Double> travelTimeMap = new HashTableSC<>(13, new SimpleHashFunction());
		for(String key: getShortestRoutes().getKeys()) {
			if(getShortestRoutes().get(key).getCityName().equals("Westside")) {
				travelTimeMap.put(key, getShortestRoutes().get(key).getDistance()*2.5);
			}
			else {
				int stops = 0;
				int timeTravelled = getShortestRoutes().get(key).getDistance();
				String name = getShortestRoutes().get(key).getCityName();
				while(!name.equals("Westside")){
					
					
					name = getShortestRoutes().get(name).getCityName();
					stops++;
				}
				Double fullJourney = (timeTravelled*2.5) + (stops*15);
				travelTimeMap.put(key, fullJourney);
			}
				
		}
		return travelTimeMap;
		
	}

	/**
	 * Return a Map with all the neighboring stations to each station
	 * @return Map<String, List<Station>> (String) - current station, (List<Station>) - List of all neighboring stations
	 * */
	public Map<String, List<Station>> getStations() {
		return neighborStationsMap;
	}

	/**
	 * Set map you input to neighboring stations map
	 * @param cities - Map to set to neighbor stations map
	 * */
	public void setStations(Map<String, List<Station>> cities) {
		this.neighborStationsMap = cities;
	}

	/**
	 * Gets the map with the shortest routes
	 * @return  Map<String, Station> (String) - station name , (Station) - station Object
	 * */
	public Map<String, Station> getShortestRoutes() {
		return shortestDistanceMap;
		
	}

	/**
	 * Sets map to shortest routes map
	 * @param shortestRoutes - Map to set to shortes routes map
	 * */
	public void setShortestRoutes(Map<String, Station> shortestRoutes) {
		shortestRoutes = getShortestRoutes();
		
	}
	/**
	 * Returns a list with all the names of the 13 stations
	 * @return ArrayList<String> (String) - station names
	 * */
	public ArrayList<String> getStationList() {
		return stationList;
		
	}
	
	/**
	 * BONUS EXERCISE THIS IS OPTIONAL
	 * Returns the path to the station given. 
	 * The format is as follows: Westside->stationA->.....stationZ->stationName
	 * Each station is connected by an arrow and the trace ends at the station given.
	 * 
	 * @param stationName - Name of the station whose route we want to trace
	 * @return (String) String representation of the path taken to reach stationName.
	 */
	public String traceRoute(String stationName) {
		// Remove if you implement the method, otherwise LEAVE ALONE
//		throw new UnsupportedOperationException();
		ArrayListStack<String> traceStack = new ArrayListStack<>();
		String resultString = "";
		if(stationName.equals("Westside")) {return stationName;}
		else if(getShortestRoutes().get(stationName).getCityName().equals("Westside")) {
			traceStack.push(stationName);
			traceStack.push("Westside");
		}
		else {
			traceStack.push(stationName);
			String name = stationName;
			while(!name.equals("Westside")){
				
				
				name = getShortestRoutes().get(name).getCityName();
				traceStack.push(name);
			}

			
		}
		
		while(traceStack.size()>1) {
			
			
			resultString+=traceStack.pop()+"->";
		
		}
		return resultString+traceStack.pop();
	}

}