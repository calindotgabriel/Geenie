package ro.geenie.models;

import com.alamkanak.weekview.WeekViewEvent;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;

/**
 * Created by loopiezlol on 14.03.2015.
 */
@DatabaseTable(tableName = "events")
public class Event {

    @DatabaseField(id = true)
    private long libraryId;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Calendar startTime;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Calendar endTime;
    @DatabaseField
    private String name;
    @DatabaseField
    private int color;
    @DatabaseField
    private Boolean repeat;

    public Event() {
    }

    public Event(WeekViewEvent event, Boolean repeat) {
        this.libraryId = event.getId();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.name = event.getName();
        this.color = event.getColor();
        this.repeat = repeat;
    }

    public WeekViewEvent getEvent() {
        WeekViewEvent event = new WeekViewEvent(this.libraryId, this.name,
                                                this.startTime, this.endTime);
        event.setColor(this.color);
        return event;
    }

    public Boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }

    public long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(long libraryId) {
        this.libraryId = libraryId;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
