package ro.geenie.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "posts")
public class Post {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String text;

    public Post() {
    }

    public Post(int id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMemberName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
