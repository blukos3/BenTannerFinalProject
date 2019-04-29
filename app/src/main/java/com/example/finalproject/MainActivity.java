package com.example.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static RequestQueue rq;
    public String client_id = "client_id=WDKMZ3KFJ0VWPLAIFYL0NR2AH5AY5TFAQBFH3VSJROVUFZSB";
    public String client_secret = "client_secret=N3QIIRJ2VHXQW2LWP10WIZCSZVZF30CKV1E4TCAA5NCUYJQH";
    public String url = "https://api.foursquare.com/v2/venues/search";
    public String query = "near=";
    public JSONObject fullJson;
    public String version = "v=20190429";
    public String addressOfPlace1;
    public String addressOfPlace2;
    public String addressOfPlace3;
    public String nameOfPlace1;
    public String nameOfPlace2;
    public String nameOfPlace3;
    public String city;
    public EditText cityInput;
    public Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText userInput = findViewById(R.id.cityInput);
        Button submitButton = findViewById(R.id.button);
        submitButton.setOnClickListener(v -> {
            String input = userInput.getText().toString();
            apiCall(query + input);
        });
    }
    public void apiCall(final String query) {
        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url + "?" + query + "&" + client_id + "&" +client_secret + "&" + version,
                null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                fullJson = response;
                try {
                    JSONObject responseData = fullJson.getJSONObject("response");
                    JSONArray venueData = responseData.getJSONArray("venues");
                    JSONObject place1 = venueData.getJSONObject(0);
                    JSONObject place2 = venueData.getJSONObject(1);
                    JSONObject place3 = venueData.getJSONObject(2);
                    nameOfPlace1 = place1.getString("name");
                    nameOfPlace2 = place2.getString("name");
                    nameOfPlace3 = place3.getString("name");
                    JSONObject location1 = place1.getJSONObject("location");
                    JSONObject location2 = place1.getJSONObject("location");
                    JSONObject location3 = place1.getJSONObject("location");
                    addressOfPlace1 = location1.getString("address");
                    addressOfPlace2 = location2.getString("address");
                    addressOfPlace3 = location3.getString("address");
                    setView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        });
        rq.add(jsonObjRequest);
    }
    public void setView() {
        TextView name1 = findViewById(R.id.name1);
        TextView name2 = findViewById(R.id.name2);
        TextView name3 = findViewById(R.id.name3);
        name1.setText(nameOfPlace1);
        name2.setText(nameOfPlace2);
        name3.setText(nameOfPlace3);
        TextView address1 = findViewById(R.id.address1);
        TextView address2 = findViewById(R.id.address2);
        TextView address3 = findViewById(R.id.address3);
        address1.setText(addressOfPlace1);
        address2.setText(addressOfPlace2);
        address3.setText(addressOfPlace3);
    }
}
