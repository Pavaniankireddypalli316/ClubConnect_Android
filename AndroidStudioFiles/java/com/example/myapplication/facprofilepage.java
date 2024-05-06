package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class facprofilepage extends AppCompatActivity {

    public androidx.appcompat.widget.AppCompatButton donationsreqbackbutton;
    public Button btnprofileedit;

    private TextView viewname;
    private TextView viewmobileno;
    private TextView viewdob;
    private TextView viewemail;
    private TextView viewaddresss;
    private TextView viewfacid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facprofilepage);
        //getting all profile view id
        viewname=findViewById(R.id.profilefieldname);
        viewmobileno=findViewById(R.id.mobilenoprofilefield);
        viewdob=findViewById(R.id.profilefielddob);
        viewemail=findViewById(R.id.profileemailfield);
        viewaddresss=findViewById(R.id.profilefieldaddress);
        viewfacid=findViewById(R.id.profilefieldid);
        //back button intialization
        donationsreqbackbutton=findViewById(R.id.reqbackbtn);
        donationsreqbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoback();
            }
        });
        fetchstudentdetails();
        //button to go for profile edit page
        btnprofileedit=findViewById(R.id.profileeditbtn);
        btnprofileedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoprofileupdatepage();
            }
        });
    }

    public void gotoback(){
        Intent intent=new Intent(this,facultyhomepage.class);
        Intent intent1 = getIntent();
        String piduser = intent1.getStringExtra("id");
        intent.putExtra("id", piduser);
        startActivity(intent);

    }
    //going to profile update page
    public void gotoprofileupdatepage(){
        //getting the userId from login page
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        Intent intent=new Intent(this,facprofileupdate.class);
        intent.putExtra("id", userIdp);
        startActivity(intent);
    }
    public void fetchstudentdetails(){

        //getting the userId from login page
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        String url = "http://10.0.2.2/pavaniapp/facprofile.php"; // Replace with your PHP script's URL

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    int indexOfJsonEnd = response.indexOf("}");
                    if (indexOfJsonEnd != -1) {
                        String jsonResponse = response.substring(0, indexOfJsonEnd + 1);

                        Log.d("JSON Response", jsonResponse); // Log the cleaned JSON response

                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                        String status = jsonObject.get("name").getAsString();
                        viewname.setText(status);
                        status = jsonObject.get("contact").getAsString();
                        viewmobileno.setText(status);
                        status = jsonObject.get("dob").getAsString();
                        viewdob.setText(status);
                        status = jsonObject.get("email").getAsString();
                        viewemail.setText(status);
                        status = jsonObject.get("facultyid").getAsString();
                        viewfacid.setText(status);
                        status = jsonObject.get("address").getAsString();
                        viewaddresss.setText(status);
                    }
                    else{
                        viewname.setText("Error: JSON format issue");
                    }


                } catch (Exception e) {
                    viewmobileno.setText("Error: " + e.getMessage());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle any errors here
                viewdob.setText("Error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Send the username and password as POST parameters
                Map<String, String> data = new HashMap<>();
                data.put("userId",userIdp);

                return data;
            }
        };

        // Customize the retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Initialize the Volley request queue and add the request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}