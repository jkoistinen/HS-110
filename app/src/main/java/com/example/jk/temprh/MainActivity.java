package com.example.jk.temprh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textViewCurrent;
    private TextView textViewVoltage;
    private TextView textViewPower;
    private TextView textViewTotal;
    private TextView textViewErrorCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Volley RequestQueue
        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        //Start building the request
        //JSON request
        String url = "http://telldusv2.lanlocal:3001/api/getconsumption";
        JSONObject jsonobjectrequest = null;

        //Success handler
        Response.Listener<JSONObject> successlistener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject responsejson = response.getJSONObject("get_realtime");

                    String current = responsejson.getString("current");
                    String voltage = responsejson.getString("voltage");
                    String power = responsejson.getString("power");
                    String total = responsejson.getString("total");
                    String error_code = responsejson.getString("err_code");

                    textViewCurrent = (TextView) findViewById(R.id.current);
                    textViewCurrent.setText("Current:"+current);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        //Error handler
        Response.ErrorListener errorlistener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Error
            }
        };

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, jsonobjectrequest, successlistener, errorlistener);

        //Add the request to queue
        VolleySingleton.getInstance(this).addToRequestQueue(req);

    }

}
