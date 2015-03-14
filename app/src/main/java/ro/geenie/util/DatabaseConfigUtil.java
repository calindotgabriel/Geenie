package ro.geenie.util;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;

import ro.geenie.models.Member;
import ro.geenie.models.Post;

/**
 * Created by motan on 09.01.2015.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
            Member.class,
            Post.class
    };

    public static void main(String[] args) throws Exception {
        writeConfigFile(new File("app/src/main/res/raw/ormlite_config.txt"), classes);
    }
}