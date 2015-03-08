package ro.geenie.models;

/**
 * Created by loopiezlol on 04.03.2015.
 */
public class AssignmentItem {

    String title;
    int click;

    public AssignmentItem() {
    }


    public AssignmentItem(String title, int click) {
        this.title = title;
        this.click = click;

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
