package ro.geenie.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by motan on 27.02.2015.
 */
@DatabaseTable(tableName = "members")
public class Member {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String user;
    @DatabaseField
    private boolean isAdmin;
    @DatabaseField
    private boolean isOwner;

    public Member() {
    }

    public Member(String user, boolean isAdmin, boolean isOwner) {
        this.user = user;
        this.isAdmin = isAdmin;
        this.isOwner = isOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }
}
