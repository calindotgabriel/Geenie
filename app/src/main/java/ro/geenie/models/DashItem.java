package ro.geenie.models;

/**
 * Created by loopiezlol on 26.02.2015.
 */
public class DashItem {

    private String name;
    private String text;

    public DashItem() {
    }


    public DashItem(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
