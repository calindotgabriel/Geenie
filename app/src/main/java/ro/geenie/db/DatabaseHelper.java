package ro.geenie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ro.geenie.models.Assignment;
import ro.geenie.models.Event;
import ro.geenie.models.Member;
import ro.geenie.models.Post;

/**
 * Created by motan on 10.01.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "geenielocal.db";
    public static final int DATABASE_VERSION = 11;

    private RuntimeExceptionDao<Member, Integer> memberRuntimeDao = null;
    private RuntimeExceptionDao<Post, Integer> postRuntimeDao = null;
    private RuntimeExceptionDao<Assignment, Integer> assignmentRuntimeDao = null;
    private RuntimeExceptionDao<Event, Integer> eventRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Member.class);
            TableUtils.createTable(connectionSource, Post.class);
            TableUtils.createTable(connectionSource, Assignment.class);
            TableUtils.createTable(connectionSource, Event.class);
        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Member.class, true);
            TableUtils.dropTable(connectionSource, Post.class, true);
            TableUtils.dropTable(connectionSource, Assignment.class, true);
            TableUtils.dropTable(connectionSource, Event.class, true);
            onCreate(database, connectionSource);
        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }



    public RuntimeExceptionDao<Member, Integer> getMemberDao() {
        if (memberRuntimeDao == null) {
            memberRuntimeDao = getRuntimeExceptionDao(Member.class);
        }
        return memberRuntimeDao;
    }

    public RuntimeExceptionDao<Post, Integer> getPostDao() {
        if (postRuntimeDao == null) {
            postRuntimeDao = getRuntimeExceptionDao(Post.class);
        }
        return postRuntimeDao;
    }

    public RuntimeExceptionDao<Assignment, Integer> getAssignmentDao() {
        if (assignmentRuntimeDao == null) {
            assignmentRuntimeDao = getRuntimeExceptionDao(Assignment.class);
        }
        return assignmentRuntimeDao;
    }

    public RuntimeExceptionDao<Event, Integer> getEventDao() {
        if (eventRuntimeDao == null) {
            eventRuntimeDao = getRuntimeExceptionDao(Event.class);
        }
        return eventRuntimeDao;
    }

    @Override
    public void close() {
        super.close();
        memberRuntimeDao = null;
        postRuntimeDao = null;
        assignmentRuntimeDao = null;
        eventRuntimeDao = null;
    }

}
