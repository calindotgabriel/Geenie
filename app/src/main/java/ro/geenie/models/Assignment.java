package ro.geenie.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;

/**
 * Created by loopiezlol on 04.03.2015.
 */
@DatabaseTable(tableName = "assignments")
public class Assignment {

    @DatabaseField
    String title;
    @DatabaseField
    int click;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    Calendar calendar;
    @DatabaseField
    String tag;
    @DatabaseField(id = true)
    int id;

    public Assignment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Assignment(String title, int click, Calendar calendar) {
        this.title = title;
        this.click = click;
        this.calendar = calendar;

    }

    public Assignment(String title, int click, Calendar calendar, String tag) {
        this.title = title;
        this.click = click;
        this.calendar = calendar;
        this.tag = tag;


    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
