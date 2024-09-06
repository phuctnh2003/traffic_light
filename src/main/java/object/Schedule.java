package object;
import java.sql.Date;
import java.sql.Time;

public class Schedule {
    private int schedule_id;
    private String name;
    private String date;
    private String start;
    private String end;

    // Constructors
    public Schedule() {}

    public Schedule(int schedule_id, String name, String date, String start, String end) {
        this.schedule_id = schedule_id;
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    // Getters and Setters
    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
