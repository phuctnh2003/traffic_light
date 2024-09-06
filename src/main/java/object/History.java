package object;

import java.sql.Timestamp;
//<-------------Thong tin lịch sử-------------->
public class History {
    int history_id;
    String searchword;
    String keyword;
    String latitude;
    String longitude;
    Timestamp time;

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

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int id) {
        this.history_id = id;
    }

    public String getSearchword() {
        return searchword;
    }

    public void setSearchword(String searchword) {
        this.searchword = searchword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}

