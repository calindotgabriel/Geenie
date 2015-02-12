package ro.geenie;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by motan on 09.02.2015.
 */
public class VolleyTest extends ApplicationTestCase<Application> {

    public VolleyTest() {
        super(Application.class);
    }

    public void testConnection() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://www.google.com";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Volley", response.substring(0, 300));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                });

        queue.add(stringRequest);
    }


}
