package ro.geenie.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "posts")
public class Post extends Item{

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TEXT = "text";

    @DatabaseField(id = true, columnName = KEY_ID)
    private int id;
    @DatabaseField(columnDefinition = KEY_NAME)
    private String name;
    @DatabaseField(columnDefinition = KEY_TEXT)
    private String text;

    public Post() {
    }

    public Post(int id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public Post(String name, String text) {
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

    @Override
    public String getTitle() {
        return this.name;
    }

    @Override
    public String getSubtitle() {
        return this.text;
    }
}
