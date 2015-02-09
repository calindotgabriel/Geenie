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
public class SignUpActivity extends Activity {

    @InjectView(R.id.fullname_signup)
    MaterialEditText fullname;
    @InjectView(R.id.email_signup)
    MaterialEditText email;
    @InjectView(R.id.username_signup)
    MaterialEditText username;
    @InjectView(R.id.password_signup)
    MaterialEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.ok_signup) void signUp() {
        Toast.makeText(this, fullname.getText() + " & " + password.getText(), Toast.LENGTH_SHORT).show();
    }
}
