package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class adminloginpage extends AppCompatActivity {

    public androidx.appcompat.widget.AppCompatButton btnlogin;

    private EditText inputuserid;
    private EditText inputuserpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminloginpage);

        inputuserid=findViewById(R.id.usernameinput);
        inputuserpassword=findViewById(R.id.passwordinput);


        btnlogin=findViewById(R.id.adminloginbutton);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminloginprocess();
            }
        });
    }

    //functions
    public void gotoadminhomepage()
    {
        Intent intent=new Intent(this, adminhomepage.class);
        String usern = inputuserid.getText().toString().trim();
        intent.putExtra("id", usern);
        startActivity(intent);
    }
    //student login process code
    public void adminloginprocess() {
        String username = inputuserid.getText().toString().trim();
        String password = inputuserpassword.getText().toString().trim();

        String url = "http://10.0.2.2/pavaniapp/adminlogin.php"; // Replace with your PHP script URL

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("res",response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                // Login successful, redirect to another activity or perform actions
                                gotoadminhomepage(); // Redirect to the homepage or any other activity
                            } else {
                                // Login failed, show error message
                                Toast.makeText(adminloginpage.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(adminloginpage.this, "JSON Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(adminloginpage.this, "Volley Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Log.d("tag1",username);
                Log.d("tag1",password);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}