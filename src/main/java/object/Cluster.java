package object;

public class Cluster
{
    private String cluster_id;
    private String street_name1;
    private String street_name2;
    private String latitude;
    private String longitude;
    private String current_script;
    private String manual;
    private String cluster_status;
    public String getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(String cluster_id) {
        this.cluster_id = cluster_id;
    }

    public String getStreet_name1() {
        return street_name1;
    }

    public void setStreet_name1(String street_name1) {
        this.street_name1 = street_name1;
    }

    public String getStreet_name2() {
        return street_name2;
    }

    public void setStreet_name2(String street_name2) {
        this.street_name2 = street_name2;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }



    public String getCurrent_script() {
        return current_script;
    }

    public void setCurrent_script(String current_script) {
        this.current_script = current_script;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public String getCluster_status() {
        return cluster_status;
    }

    public void setCluster_status(String cluster_status) {
        this.cluster_status = cluster_status;
    }
}

