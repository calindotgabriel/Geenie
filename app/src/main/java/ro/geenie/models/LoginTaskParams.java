package ro.geenie.models;

import android.content.Context;

/**
 * Created by motan on 21.02.2015.
 */

public class LoginTaskParams {
    public Context context;
    public String name;
    public String password;

    public LoginTaskParams(Context context, String name, String password) {
        this.context = context;
        this.name = name;
        this.password = password;
    }
}
