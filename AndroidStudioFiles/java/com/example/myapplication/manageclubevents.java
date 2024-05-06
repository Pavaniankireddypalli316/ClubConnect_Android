package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class manageclubevents extends AppCompatActivity {

    public androidx.appcompat.widget.AppCompatButton gobackbtn;
    public androidx.appcompat.widget.AppCompatButton btnaddroom;

    private TextView dataDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageclubevents);
        gobackbtn=findViewById(R.id.managestdbackbtn);
        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoback();
            }
        });
        btnaddroom=findViewById(R.id.addbuttonfac);
        btnaddroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoaddfacpage();
            }
        });

        dataDisplayTextView = findViewById(R.id.heading);
        fetchUserOrders();
    }
    public void gotoback(){
        Intent intent=new Intent(manageclubevents.this,adminhomepage.class);
        startActivity(intent);
    }
    public void gotoaddfacpage(){
        Intent intent2=new Intent(manageclubevents.this,addevent.class);
        startActivity(intent2);
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


    //the data can be displayed by using this function
    private void fetchUserOrders() {
        String url = "http://10.0.2.2/pavaniapp/admanclub.php"; // Replace with your PHP script's URL

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    StringBuilder ordersDetails = new StringBuilder();
                    // Process the JSON response and display orders' details
                    Log.d("JSON Response", response); // Log the cleaned JSON response
                    JSONObject jsonResponse = new JSONObject(response);
                    LinearLayout dataDisplayLinearLayout = findViewById(R.id.dataDisplayLinearLayout);

                    if (jsonResponse.getBoolean("status")) {
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject order = jsonArray.getJSONObject(i);
                                String stdid = order.getString("courseid");
                                String name = order.getString("coursename");
                                String facid = order.getString("facultyid");

                                // Inflate the new layout for each record
                                View detailView = getLayoutInflater().inflate(R.layout.club_manage_block, null);

                                // Find the TextViews, Spinner, and Button in the inflated layout
                                TextView userid = detailView.findViewById(R.id.r);
                                TextView username = detailView.findViewById(R.id.n);
                                TextView actio = detailView.findViewById(R.id.act);

                                // Set the data to the TextViews
                                userid.setText("Club Code : " + stdid);
                                username.setText("Club Name : " + name);
                                actio.setText("Faculty Id: "+facid);


                                // Add the inflated layout to your main layout
                                // Assume dataDisplayLinearLayout is the layout container in your main XML
                                dataDisplayLinearLayout.addView(detailView);
                            }
                        }
                        else {
                            // Handle case when no data is found
                            dataDisplayTextView.setText("No data found for this user.");
                        }
                    }
                    else{
                        // Handle other status scenarios, if needed
                        dataDisplayTextView.setText("Data Extraction Failed");

                    }
                    // Set orders' details to the TextView
                    dataDisplayTextView.setText(ordersDetails.toString());
                } catch (Exception e) {
                    dataDisplayTextView.setText("Error: Hii" + e.getMessage());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle any errors here
                dataDisplayTextView.setText("Error: Hello" + error.getMessage());
            }
        });
        // Customize the retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue1.add(stringRequest);
    }
}