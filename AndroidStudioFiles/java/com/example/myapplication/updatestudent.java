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

public class updatestudent extends AppCompatActivity {

    public androidx.appcompat.widget.AppCompatButton btnupdateroomback;
    public Button btnupdateroomsubmit;

    private String URL = "http://10.0.2.2/pavaniapp/admanstdupdate.php";

    private EditText enname;
    private EditText enmob;
    private EditText endob;
    private EditText enemail;

    private EditText enaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatestudent);
        btnupdateroomback=findViewById(R.id.backbtn);
        btnupdateroomback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotobackpage();
            }
        });
        btnupdateroomsubmit=findViewById(R.id.updateButton);
        btnupdateroomsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateroomprocess();
            }
        });

        //getting the input field's data
        enname=findViewById(R.id.inpname);
        enmob=findViewById(R.id.inpcon);
        endob=findViewById(R.id.inpdob);
        enemail=findViewById(R.id.inpema);
        enaddress=findViewById(R.id.inpadd);
    }
    public void gotobackpage(){
        Intent intent=new Intent(this,managestudents.class);
        startActivity(intent);
    }

    public void updateroomprocess()
    {
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        String tx0 = enname.getText().toString();
        String tx1 = enmob.getText().toString();
        String tx2 = endob.getText().toString();
        String tx3 = enemail.getText().toString();
        String tx4 = enaddress.getText().toString();

        // Create a JSON object with the data
        JSONObject jsonData = new JSONObject();
        try {
            Log.d("tag0",tx0);
            Log.d("tag1",tx1);
            Log.d("tag2",tx2);
            Log.d("tag3",tx3);
            Log.d("tag4",tx4);
            Log.d("tag5",userIdp);
            jsonData.put("field0", tx0);
            jsonData.put("field1", tx1);
            jsonData.put("field2", tx2);
            jsonData.put("field3", tx3);
            jsonData.put("field4", tx4);
            jsonData.put("field5", userIdp);
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
                                    Toast.makeText(updatestudent.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(updatestudent.this, managestudents.class);
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