package ro.geenie.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by motan on 27.02.2015.
 */
@DatabaseTable(tableName = "members")
public class Member extends Item{


    public static final String KEY_USER = "user";
    public static final String KEY_OWNER = "owner";
    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = KEY_USER)
    private String user;
    @DatabaseField (columnName = KEY_OWNER)
    private boolean isOwner;

    public Member() {
    }

    public Member(int id, String user , boolean isOwner) {
        this.id = id;
        this.user = user;
        this.isOwner = isOwner;
    }

    public Member(int id, String user, boolean isAdmin, boolean isOwner) {
        this.id = id;
        this.user = user;
        this.isOwner = isOwner;
    }

    public Member(int id, String user) {
        this.id = id;
        this.user = user;
        this.isOwner = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    @Override
    public String getTitle() {
        return this.getName();
    }

    @Override
    public String getSubtitle() {
        return this.isOwner() ? "is owner" : "not owner";
    }
}
