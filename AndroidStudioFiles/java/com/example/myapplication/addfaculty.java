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

public class addfaculty extends AppCompatActivity {

    public androidx.appcompat.widget.AppCompatButton btnaddroomback;
    public androidx.appcompat.widget.AppCompatButton btnaddroomsubmit;
    private EditText namefield;
    private EditText roomidfied;
    private EditText mobfield;
    private EditText dobfield;
    private EditText emailfield;

    private EditText addressfield;
    private String URL = "http://10.0.2.2/pavaniapp/admanfacadd.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfaculty);
        btnaddroomback=findViewById(R.id.historybackbtn);
        btnaddroomback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotobackpage();
            }
        });
        btnaddroomsubmit=findViewById(R.id.facsubtn);
        btnaddroomsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicksubmitbutton();
            }
        });
        namefield=findViewById(R.id.inpname);
        roomidfied=findViewById(R.id.inpstdid);
        mobfield=findViewById(R.id.inpcon);
        dobfield=findViewById(R.id.inpdob);
        emailfield=findViewById(R.id.inpemail);
        addressfield=findViewById(R.id.inpaddress);
    }
    public void gotobackpage(){
        Intent intent=new Intent(this,managefaculty.class);
        startActivity(intent);
    }
    public void onclicksubmitbutton()
    {
        // Get the data from EditText fields
        String tx0 = namefield.getText().toString();
        String tx1 = roomidfied.getText().toString();
        String tx2 = mobfield.getText().toString();
        String tx3 = dobfield.getText().toString();
        String tx4 = emailfield.getText().toString();
        String tx5 = addressfield.getText().toString();
        // Create a JSON object with the data
        JSONObject jsonData = new JSONObject();
        try {
            Log.d("tag0",tx0);
            Log.d("tag1",tx1);
            Log.d("tag2",tx2);
            Log.d("tag3",tx3);
            Log.d("tag4",tx4);
            Log.d("tag5",tx5);
            jsonData.put("field0", tx0);
            jsonData.put("field1", tx1);
            jsonData.put("field2", tx2);
            jsonData.put("field3", tx3);
            jsonData.put("field4", tx4);
            jsonData.put("field5", tx5);
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
                                    Toast.makeText(addfaculty.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(addfaculty.this, managefaculty.class);
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