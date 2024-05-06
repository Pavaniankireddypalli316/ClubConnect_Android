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
public class facprofileupdate extends AppCompatActivity {

    public androidx.appcompat.widget.AppCompatButton backtp;
    public Button savedetails;
    private EditText editemail;
    private EditText editpasswrod;
    private String URL = "http://10.0.2.2/pavaniapp/facprofileupdate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facprofileupdate);

        //initialize the edittext
        editemail=findViewById(R.id.stdprofilemailup);
        editpasswrod=findViewById(R.id.stdprofilepasup);

        backtp=findViewById(R.id.profilesavetoeditbackbtn);
        backtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backtoprofilepage();
            }
        });
        savedetails=findViewById(R.id.profilesavebtn);
        savedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJsonData();
            }
        });
    }
    public void backtoprofilepage(){
        Intent intent=new Intent(this,facprofilepage.class);
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        intent.putExtra("id", userIdp);
        startActivity(intent);
    }
    private void sendJsonData() {
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        // Get the data from EditText fields
        String tx1 = editemail.getText().toString();
        String tx3 = editpasswrod.getText().toString();

        // Create a JSON object with the data
        JSONObject jsonData = new JSONObject();
        try {
            Log.d("tag0",userIdp);
            Log.d("tag1",tx1);
            Log.d("tag1",tx3);
            jsonData.put("field0", userIdp);
            jsonData.put("field1", tx1);
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
                                    Toast.makeText(facprofileupdate.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(facprofileupdate.this, facprofilepage.class);
                                    intent.putExtra("id",userIdp);
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