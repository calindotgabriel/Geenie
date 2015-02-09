package ro.geenie.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ro.geenie.R;

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
    }

    @OnClick(R.id.ok_login) void logIn() {
        Toast.makeText(this, username.getText() + " / " + password.getText(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.signup_login) void goSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }




}
