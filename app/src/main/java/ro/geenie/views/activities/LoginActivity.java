package ro.geenie.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ro.geenie.R;
import ro.geenie.controller.LocalOwnerController;
import ro.geenie.controller.MemberController;
import ro.geenie.models.exception.MemberProviderException;
import ro.geenie.util.Utils;

/**
 * Created by motan on 08.02.2015.
 */
public class LoginActivity extends Activity {

    @InjectView(R.id.username_login)
    MaterialEditText username;
    @InjectView(R.id.password_login)
    MaterialEditText password;

    private LocalOwnerController ownerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ownerController = new LocalOwnerController(this);
        if (ownerController.isOwnerRegistered()) {
            Intent intent = new Intent(LoginActivity.this, DashActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        debugOnly();

    }

    private void debugOnly() {
        username.setText("ion");
        password.setText("123456");
//        goLogin();
    }

    @OnClick(R.id.ok_login)
    void goLogin() {
//        LoginTaskParams loginParams = new LoginTaskParams(this,
//                username.getText().toString(),
//                password.getText().toString()
//        );
//        new DbLoginAsyncTask().execute(loginParams);
        String enteredName = username.getText().toString();
        MemberController memberController = new MemberController(this);
        try {
            if (memberController.isMemberValid(enteredName)) {
                ownerController.registerLocalOwner(enteredName);
            }
        } catch (MemberProviderException e) {
            Utils.toastLog(this, e.getMessage());
            e.printStackTrace();
        }
    }

    @OnClick(R.id.signup_login)
    void goSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


}
