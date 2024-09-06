package object;

public class Location {
    private String lat;
    private String lon;
    private String display_name;


    public Location(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }



    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}