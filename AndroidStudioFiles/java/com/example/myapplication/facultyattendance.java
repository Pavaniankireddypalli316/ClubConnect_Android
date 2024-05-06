package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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

public class facultyattendance extends AppCompatActivity {

    private TextView dataDisplayTextView;
    public androidx.appcompat.widget.AppCompatButton facregbackbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultyattendance);

        //Initialize the backgoing button
        facregbackbtn=findViewById(R.id.regbackbutton);
        facregbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotobackpage();
            }
        });
        // Initialize the dataDisplayTextView
        dataDisplayTextView = findViewById(R.id.headregpone);

        // Call the method to fetch and display user orders
        fetchUserOrders();
    }

    public void gotobackpage(){
        Intent intent=new Intent(this,facultyhomepage.class);
        Intent intent1 = getIntent();
        String piduser = intent1.getStringExtra("id");
        intent.putExtra("id", piduser);
        startActivity(intent);

    }

    // Helper method for styling
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

    // Method to fetch and display user orders
    private void fetchUserOrders() {
        // getting the userId from login page
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        //String userIdp = "t24f";
        String url = "http://10.0.2.2/pavaniapp/facreg.php"; // Replace with your PHP script's URL

        // Initialize the Volley request queue
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);

        // Create a StringRequest to make a POST request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // Process the JSON response and display orders' details
                    Log.d("JSON Response", response); // Log the cleaned JSON response
                    JSONObject jsonResponse = new JSONObject(response);
                    LinearLayout dataDisplayLinearLayout = findViewById(R.id.dataDisplayLinearLayout);

                    if (jsonResponse.getBoolean("status")) {
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject order = jsonArray.getJSONObject(i);
                                String clubid = order.getString("courseid");
                                String clubname=order.getString("coursename");
                                String a=clubid+"-"+clubname;

                                // Inflate the new layout for each record
                                View detailView = getLayoutInflater().inflate(R.layout.facreg_page_two, null);

                                // Find the TextViews, Spinner, and Button in the inflated layout
                                TextView updateButton = detailView.findViewById(R.id.buttonforacourse);

                                updateButton.setText(a);

                                // Set up the update button click listener
                                updateButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //when the user clicks on update button the page need to redirect regarding club code
                                        nextpage(userIdp,clubid);
                                    }
                                });

                                // Add the inflated layout to your main layout
                                // Assume dataDisplayLinearLayout is the layout container in your main XML
                                dataDisplayLinearLayout.addView(detailView);
                            }
                        } else {
                            // Handle case when no data is found
                            dataDisplayTextView.setText("No data found for this user.");
                        }
                    } else {
                        // Handle other status scenarios, if needed
                        dataDisplayTextView.setText("No Club Allocated");
                    }
                } catch (Exception e) {
                    dataDisplayTextView.setText("Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle any errors here
                dataDisplayTextView.setText("Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Set parameters for the update request
                Map<String, String> params = new HashMap<>();
                params.put("facid", userIdp);
                return params;
            }
        };
        // Customize the retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the Volley request queue
        requestQueue1.add(stringRequest);
    }
    public void nextpage(String Facultyid,String Clubcode)
    {
        Intent intent1=new Intent(this,facatdpagetwo.class);
        intent1.putExtra("id", Facultyid);
        intent1.putExtra("clubid",Clubcode);
        startActivity(intent1);
    }
}