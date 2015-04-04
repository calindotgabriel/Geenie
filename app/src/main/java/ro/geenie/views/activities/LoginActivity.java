package ro.geenie.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ro.geenie.R;
import ro.geenie.contracts.LoginContract;

/**
 * Created by motan on 08.02.2015.
 */
public class LoginActivity extends Activity {

    @InjectView(R.id.username_login)
    MaterialEditText username;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = this.getSharedPreferences(LoginContract.KEY_APP_NAME, Context.MODE_PRIVATE);
        String userName = prefs.getString(LoginContract.KEY_USERNAME, LoginContract.DEFAULT_USERNAME);
        if (userName.equals(LoginContract.DEFAULT_USERNAME)) {
            setContentView(R.layout.activity_login);
            ButterKnife.inject(this);
        } else {
            goToDash();
        }
    }

    @OnClick(R.id.ok_login) void okPressed() {
        prefs.edit().putString(LoginContract.KEY_USERNAME, username.getText().toString()).apply();
        goToDash();
    }

    private void goToDash() {
        Intent intent = new Intent(LoginActivity.this, DashActivity.class);
        startActivity(intent);
    }

}
