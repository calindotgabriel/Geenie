package ro.geenie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by loopiezlol on 08.02.2015.
 */
public class NewClassActivity extends Activity {

    @InjectView(R.id.class_name)
    MaterialEditText className;

    @InjectView(R.id.class_key)
    MaterialEditText classKey;

    @InjectView(R.id.class_description)
    MaterialEditText classDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_define);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.ok_new_class)
    void createClass() {
        Toast.makeText(this, className.getText() + " / " + classKey.getText() + " / " + classDesc.getText(), Toast.LENGTH_SHORT).show();
    }
}
