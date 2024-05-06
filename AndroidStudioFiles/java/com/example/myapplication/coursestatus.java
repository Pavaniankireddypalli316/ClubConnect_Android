package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
import org.json.JSONArray;
import org.json.JSONException;
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
public class coursestatus extends AppCompatActivity {

    public androidx.appcompat.widget.AppCompatButton backbutton;
    private TextView historyDetailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursestatus);

        backbutton = findViewById(R.id.historybackbtn);
        historyDetailsTextView = findViewById(R.id.historyDetailsTextView);

        historyDetailsTextView.setMovementMethod(new ScrollingMovementMethod());


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotobackpage();
            }
        });

        // Fetch orders for the specific user
        fetchUserOrders();
    }

    public void gotobackpage() {
        Intent intent = new Intent(this, menubar.class);
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        intent.putExtra("id", userIdp);
        startActivity(intent);
    }

    // Helper methods for styling
    private SpannableStringBuilder getBoldText(String text) {
        SpannableStringBuilder boldText = new SpannableStringBuilder(text);
        boldText.setSpan(new StyleSpan(Typeface.BOLD), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return boldText;
    }

    private SpannableStringBuilder getRedText(String text) {
        SpannableStringBuilder redText = new SpannableStringBuilder(text);
        redText.setSpan(new ForegroundColorSpan(Color.RED), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return redText;
    }

    private void fetchUserOrders() {
        //getting the userId from login page
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        String url = "http://10.0.2.2/pavaniapp/stdcoursestatus.php"; // Replace with your PHP script's URL

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    StringBuilder ordersDetails = new StringBuilder();
                    // Process the JSON response and display orders' details
                    Log.d("JSON Response", response); // Log the cleaned JSON response
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.getBoolean("status")) {
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject order = jsonArray.getJSONObject(i);
                                String name = order.getString("coursename");
                                String date = order.getString("courseid");
                                String mobile = order.getString("coursestatus");
                                String service = order.getString("grade");

                                // Create a SpannableStringBuilder for styling
                                SpannableStringBuilder styledDetails = new SpannableStringBuilder();

                                // Apply styling to headings
                                styledDetails.append(getBoldText("Club Name: ")).append(name).append("\n");
                                styledDetails.append(getBoldText("Club Code: ")).append(date).append("\n");
                                styledDetails.append(getBoldText("Club Status: ")).append(mobile).append("\n");
                                styledDetails.append(getBoldText("Grade: ")).append(service).append("\n\n");

                                // Add a light blue background and equal spacing
                                styledDetails.setSpan(new BackgroundColorSpan(Color.parseColor("#ADD8E6")), 0, styledDetails.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                styledDetails.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, styledDetails.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                // Append the styled details
                                ordersDetails.append(styledDetails);
                            }
                        }
                        else {
                            // Handle case when no data is found
                            historyDetailsTextView.setText("No data found for this user.");
                        }
                    }
                    else{
                        // Handle other status scenarios, if needed
                        historyDetailsTextView.setText("Data Extraction Failed");

                    }
                    // Set orders' details to the TextView
                    historyDetailsTextView.setText(ordersDetails.toString());
                } catch (Exception e) {
                    historyDetailsTextView.setText("Error: " + e.getMessage());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle any errors here
                historyDetailsTextView.setText("Error: " + error.getMessage());
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
        ;
    }
}