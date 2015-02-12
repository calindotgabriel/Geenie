package ro.geenie.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ro.geenie.R;
import ro.geenie.network.GsonClient;

/**
 * Created by motan on 09.02.2015.
 */
public class NetworkActivity extends Activity {

    @InjectView(R.id.response_text)
    TextView responseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.contact_network_btn) void contactNetwork() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.google.com";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseView.setText(error.toString());
                    }
                });

        queue.add(stringRequest);
    }
}
