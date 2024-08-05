package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import data_structures.ArrayList;
import data_structures.HashTableSC;
import data_structures.SimpleHashFunction;
import interfaces.List;
import interfaces.Map;

import java.awt.*;
import java.awt.event.*;

public class StationGUI implements ActionListener {
    
    private JFrame frame = new JFrame();
    private JTextField searchField;
    private JTable table;
    private TrainStationManager trainManager = new TrainStationManager("stations.csv");
    private Map<String, String> departureTimes= new HashTableSC<>(13,new SimpleHashFunction());
    private Map<String, String> arrivalTimes= new HashTableSC<>(13,new SimpleHashFunction());

    /**
     * Constructor for StationGUi that renders everything
     * */
    public StationGUI() {
    	setDepartureTimes(departureTimes);
    	setArrivalTimes(arrivalTimes);
    	String[] columnNames = { "Station Name", "Departure Time", "Arrival Time" };
    	DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    	

	    	for(String stationName: trainManager.getShortestRoutes().getKeys()) {


	    		if(!stationName.equals("Westside")){
	    			tableModel.addRow(new String[]{stationName, getDepartureTimes().get(stationName), getArrivalTimes().get(stationName)});
	    		}


    	}
        
       

       
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        

        JButton button = new JButton("Welcome");
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(150, 50));
        
        JLabel label = new JLabel("Enter station name to find the route:");
        searchField = new JTextField(20);
        searchField.addActionListener(e -> popUp(searchField.getText()));


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.SOUTH);
        panel.add(searchField, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(30, 30, 10, 30));

        frame = new JFrame("WELCOME TO WESTSIDE STATION");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Creates a map that sets up all the departure times according to the station you are arriving to
     * @param Map<String, String> departureTimes String - station name , String - time of departure from Westside
     * */
    public void setDepartureTimes(Map<String, String> departureTimes) {
    	departureTimes.put("Bugapest", "9:35am");
    	departureTimes.put("Dubay", "10:30am");
    	departureTimes.put("Berlint", "8:25pm");
    	departureTimes.put("Mosbull", "6:00pm");
    	departureTimes.put("Cayro", "6:40am");
    	departureTimes.put("Bostin", "10:25am");
    	departureTimes.put("Los Angelos", "12:30pm");
    	departureTimes.put("Dome", "1:30pm");
    	departureTimes.put("Takyo", "3:35pm");
    	departureTimes.put("Unstabul", "4:45pm");
    	departureTimes.put("Chicargo", "7:25am");
    	departureTimes.put("Loondun", "2:00pm");
    }
    
    /**
     * Returns a map with all the stations and their departure times
     * @return Map<String, String> String - station name , String - time of departure from Westside to station of choosing
     * */
    public Map<String, String> getDepartureTimes(){
    	return departureTimes;
    }
    
    /**
     * Creates a map that sets up all the arrival times according to the station you are arriving to
     * @param Map<String, String> arrivalTimes String - station name , String - time of arrival to station
     * */
    public void setArrivalTimes(Map<String, String> arrivalTimes) {
    	arrivalTimes.put("Bugapest", "12:35pm");
    	arrivalTimes.put("Dubay", "12:37pm");
        arrivalTimes.put("Berlint", "12:20am");
        arrivalTimes.put("Mosbull", "9:30pm");
        arrivalTimes.put("Cayro", "1:00pm");
        arrivalTimes.put("Bostin", "12:50pm");
        arrivalTimes.put("Los Angelos", "4:30pm");
        arrivalTimes.put("Dome", "4:30pm");
        arrivalTimes.put("Takyo", "7:15pm");
        arrivalTimes.put("Unstabul", "8:05pm");
        arrivalTimes.put("Chicargo", "12:55pm");
        arrivalTimes.put("Loondun", "4:00pm");
    }
    
    /**
     * Returns a map that with all the stations and their the arrival time from Westside
     * @return Map<String, String> String - station name , String - time of arrival to station from Westside
     * */
    public Map<String, String> getArrivalTimes(){
    	return arrivalTimes;
    }
    
    
    /**
     * Handles the pop up message that displays the route taken
     * @param stationName - name of station we want to find route to
     * */
    private void popUp(String stationName) {
    	if(trainManager.getStationList().contains(stationName)) {
    		JOptionPane.showMessageDialog(null, trainManager.traceRoute(stationName));
    	}
    	else {
    		JOptionPane.showMessageDialog(null, "That Station Does Not Exist");
    	}
    }

    /**
     * Handles the search field 
     * @param e - object representing the action performed
     * */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchField) {
            popUp(searchField.getText());
        } else {
            
        }
    }
   
   
    public static void main(String[] args) {
        new StationGUI();
    }

	
}

