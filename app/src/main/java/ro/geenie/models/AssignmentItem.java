package ro.geenie.models;

import java.util.Calendar;

/**
 * Created by loopiezlol on 04.03.2015.
 */
public class AssignmentItem {

    String title;
    int click;
    Calendar calendar;
    String tag;

    public AssignmentItem() {
    }


    public AssignmentItem(String title, int click, Calendar calendar) {
        this.title = title;
        this.click = click;
        this.calendar = calendar;

    }

    public AssignmentItem(String title, int click, Calendar calendar, String tag) {
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
