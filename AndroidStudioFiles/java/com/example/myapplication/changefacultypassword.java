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

public class changefacultypassword extends AppCompatActivity {

    public androidx.appcompat.widget.AppCompatButton btnadminchangepasswordback;
    public androidx.appcompat.widget.AppCompatButton btnadminchangepasswordsubmit;
    private String URL = "http://10.0.2.2/pavaniapp/admanfacchpas.php";
    private EditText penpass;
    private EditText penconpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changefacultypassword);
        btnadminchangepasswordback=findViewById(R.id.uspbackbtn);

        //getting the input fields data
        penpass=findViewById(R.id.cpnpass);
        penconpass=findViewById(R.id.cpcnpass);

        btnadminchangepasswordback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotobackpage();
            }
        });
        btnadminchangepasswordsubmit=findViewById(R.id.uspsubmitbtn);
        btnadminchangepasswordsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepasswordprocess();
            }
        });

    }
    public void gotobackpage(){
        Intent intent=new Intent(this,managefaculty.class);
        startActivity(intent );
    }

    public void changepasswordprocess(){
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        // Get the data from EditText fields
        String tx1 = penpass.getText().toString();
        String tx2 = penconpass.getText().toString();

        // Check if password and confirm password match
        if (!tx1.equals(tx2)) {
            // Display a message if passwords do not match
            Toast.makeText(changefacultypassword.this, "Password and Confirm Password must be the same", Toast.LENGTH_SHORT).show();
            return; // Stop the process
        }

        // Create a JSON object with the data
        JSONObject jsonData = new JSONObject();
        try {
            Log.d("tag1",tx1);
            Log.d("tag2",tx2);
            Log.d("tag3",userIdp);
            jsonData.put("field1", tx1);
            jsonData.put("field2", tx2);
            jsonData.put("field3", userIdp);
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
                                    Toast.makeText(changefacultypassword.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(changefacultypassword.this, managefaculty.class);
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