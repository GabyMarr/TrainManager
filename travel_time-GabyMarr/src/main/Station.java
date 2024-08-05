package main;

public class Station {
    private String stationName;
    private Integer stationDistance;
    
    /**
     * Creates a Station object
     * @param name - Name of the station, dist - distance to Westside (Central Station)
     * */
    public Station(String name, int dist) {
        this.stationName = name;
        this.stationDistance = dist; 
    }

    /**
     * Gets city name of Station Object
     * @return (String) - string of city name
     * */
    public String getCityName() {
        return this.stationName;

    }
    /**
     * Sets city name of Station Object
     * @param cityName - city name you want to set
     * */
    public void setCityName(String cityName) {
        this.stationName = cityName;

    }
    /**
     * Gets distance of Station Object
     * @return (int) - distance of a station
     * */
    public int getDistance() {
        return this.stationDistance;
    }
    /**
     * Sets distance of Station Object
     * @param distance - the distance you want to set
     * 
     * */
    public void setDistance(int distance) {
        this.stationDistance = distance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Station other = (Station) obj;
        return this.getCityName().equals(other.getCityName()) && this.getDistance() == other.getDistance();
    }
    @Override
    public String toString() {
        return "(" + this.getCityName() + ", " + this.getDistance() + ")";
    }

}