package ro.geenie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by motan on 08.02.2015.
 */
public class LoginActivity extends Activity {

    @InjectView(R.id.username)
    MaterialEditText username;

    @InjectView(R.id.password)
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




}
