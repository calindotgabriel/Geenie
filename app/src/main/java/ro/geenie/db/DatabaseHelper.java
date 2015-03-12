package ro.geenie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ro.geenie.models.Member;
import ro.geenie.models.Post;
import ro.geenie.models.exception.NoOwnerException;

/**
 * Created by motan on 10.01.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "geenielocal.db";
    public static final int DATABASE_VERSION = 5;

    private Dao<Member, Integer> memberDao = null;

    private RuntimeExceptionDao<Member, Integer> memberRuntimeDao = null;
    private RuntimeExceptionDao<Post, Integer> postRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Member.class);
            TableUtils.createTable(connectionSource, Post.class);

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
            onCreate(database, connectionSource);
        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Member, Integer> getMemberDao() throws SQLException {
        if (memberDao == null) {
            memberDao = getDao(Member.class);
        }
        return memberDao;
    }

    public RuntimeExceptionDao<Member, Integer> getMemberRuntimeDao() {
        if (memberRuntimeDao == null) {
            memberRuntimeDao = getRuntimeExceptionDao(Member.class);
        }
        return memberRuntimeDao;
    }

    public RuntimeExceptionDao<Post, Integer> getPostRuntimeDao() {
        if (postRuntimeDao == null) {
            postRuntimeDao = getRuntimeExceptionDao(Post.class);
        }
        return postRuntimeDao;
    }

    @Override
    public void close() {
        super.close();
        memberDao = null;
        memberRuntimeDao = null;
        postRuntimeDao = null;
    }

    /**
     * Gets the owner (logged in user)
     * @throws java.sql.SQLException
     */
    //TODO move to MemberProvider
    public Member getOwner() throws SQLException, NoOwnerException {
        CloseableIterator<Member> iterator =
                getMemberRuntimeDao().closeableIterator();
        try {
            while (iterator.hasNext()) {
                Member member = iterator.next();
                if (member.isOwner()) {
                    return member;
                }
            }
        } finally {
            iterator.close();
        }
        throw new NoOwnerException("Found no owner in db.");
    }
}
