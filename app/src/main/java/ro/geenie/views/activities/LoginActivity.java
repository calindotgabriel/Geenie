package ro.geenie.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ro.geenie.R;
import ro.geenie.models.LoginTaskParams;
import ro.geenie.network.DbLoginAsyncTask;

/**
 * Created by motan on 08.02.2015.
 */
public class LoginActivity extends Activity {
    @InjectView(R.id.username_login)
    MaterialEditText username;
    @InjectView(R.id.password_login)
    MaterialEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);


//        new ServletPostAsyncTask().execute(new Pair<Context, String>(this, "Eusebio"));
//        LoginTaskParams loginTaskParams = new LoginTaskParams(this, "Eusebiu", "123456");
//        new LoginAsyncTask().execute(loginTaskParams);

    }

    @OnClick(R.id.ok_login)
    void goLogin() {
        LoginTaskParams loginParams = new LoginTaskParams(this,
                username.getText().toString(),
                password.getText().toString()
        );
        new DbLoginAsyncTask().execute(loginParams);
    }

    @OnClick(R.id.signup_login)
    void goSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


}
