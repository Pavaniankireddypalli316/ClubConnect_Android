package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class addevent extends AppCompatActivity {

    public androidx.appcompat.widget.AppCompatButton btnaddroomback;
    public androidx.appcompat.widget.AppCompatButton btnaddroomsubmit;
    private EditText clubcodefield;
    private EditText clubnamefied;
    private EditText facultyidfield;
    private EditText strengthfield;

    private String URL = "http://10.0.2.2/pavaniapp/admanclubadd.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);
        btnaddroomback=findViewById(R.id.historybackbtn);
        btnaddroomback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotobackpage();
            }
        });
        btnaddroomsubmit=findViewById(R.id.adcsubbtn);
        btnaddroomsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicksubmitbutton();
            }
        });
        clubcodefield=findViewById(R.id.inpclubcode);
        clubnamefied=findViewById(R.id.inpclubname);
        facultyidfield=findViewById(R.id.inpfacultyid);
        strengthfield=findViewById(R.id.inpstrength);

    }
    public void gotobackpage(){
        Intent intent=new Intent(this,manageclubevents.class);
        startActivity(intent);
    }
    public void onclicksubmitbutton()
    {
        // Get the data from EditText fields
        String tx0 = clubcodefield.getText().toString();
        String tx1 = clubnamefied.getText().toString();
        String tx2 = facultyidfield.getText().toString();
        String tx3 = strengthfield.getText().toString();
        // Create a JSON object with the data
        JSONObject jsonData = new JSONObject();
        try {
            Log.d("tag0",tx0);
            Log.d("tag1",tx1);
            Log.d("tag2",tx2);
            Log.d("tag3",tx3);
            jsonData.put("field0", tx0);
            jsonData.put("field1", tx1);
            jsonData.put("field2", tx2);
            jsonData.put("field3", tx3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send the JSON data to the PHP script using Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Check if the JSON response contains a "status" key
                        if (response.has("status")) {
                            String status = null;
                            try {
                                status = response.getString("status");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            if (status.equals("success")) {
                                // Data was updated successfully
                                try {
                                    String message = response.getString("message");
                                    Toast.makeText(addevent.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(addevent.this, manageclubevents.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                // Data update was not successful
                                try {
                                    String message = response.getString("message");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors (if needed)
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}