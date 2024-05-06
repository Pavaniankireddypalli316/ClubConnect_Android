package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class stdenrollment extends AppCompatActivity {

    private TextView dataDisplayTextView;
    private androidx.appcompat.widget.AppCompatButton backgohomepagebtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdenrollment);

        //Initialize the backgoing button
        backgohomepagebtn=findViewById(R.id.historybackbtn);
        backgohomepagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funtobackpage();
            }
        });
        // Initialize the dataDisplayTextView
        dataDisplayTextView = findViewById(R.id.datadetailstextview);

        // Call the method to fetch and display user orders
        fetchUserOrders();
    }

    public void funtobackpage()
    {
        Intent intent = new Intent(this, menubar.class);
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        intent.putExtra("id", userIdp);
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
        String url = "http://10.0.2.2/pavaniapp/stdenrollment.php"; // Replace with your PHP script's URL

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
                                String room = order.getString("courseid");
                                String name = order.getString("coursename");
                                String service = order.getString("strength");


                                // Inflate the new layout for each record
                                View detailView = getLayoutInflater().inflate(R.layout.std_enroll_page, null);

                                // Find the TextViews, Spinner, and Button in the inflated layout
                                TextView detailNameTextView = detailView.findViewById(R.id.r);
                                TextView detailScoreTextView = detailView.findViewById(R.id.n);
                                TextView detailStatusTextView = detailView.findViewById(R.id.s);
                                Button updateButton = detailView.findViewById(R.id.updateButton);

                                // Set the data to the TextViews
                                detailNameTextView.setText("Name: " + room);
                                detailScoreTextView.setText("Score: " + name);
                                detailStatusTextView.setText("Service: " + service);

                                //to check weather the student has been registered for that course or not
                                String updateUrl = "http://10.0.2.2/pavaniapp/stdenrollcheck.php"; // Replace with your PHP script's update URL
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


                                // Create a StringRequest to make a POST request for updating data
                                StringRequest updateRequest = new StringRequest(Request.Method.POST, updateUrl,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Handle the update response
                                                Log.d("Response", response);
                                                //checking the data
                                                try {
                                                    Log.d("JSON Response", response); // Log the cleaned JSON response
                                                    JSONObject jsonResponse = new JSONObject(response);
                                                    boolean status = jsonResponse.getBoolean("status");
                                                    if (status) {
                                                        // Data extracted successfully
                                                        JSONArray dataArray = jsonResponse.getJSONArray("data");
                                                        //first data set of the array
                                                        JSONObject firstObject = dataArray.getJSONObject(0);
                                                        // Extract the value of "course status"
                                                        String courseStatus = firstObject.getString("studentstatus");
                                                        //need to disable the functionallity of update button
                                                        updateButton.setOnClickListener(null);

                                                        updateButton.setText(courseStatus);
                                                        updateButton.setBackgroundColor(Color.YELLOW);

                                                    } else {
                                                        // The student is not registered
                                                        updateButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                updateData(room, userIdp,name);
                                                            }
                                                        });

                                                    }
                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Handle errors during the update request
                                        Log.e("Update Error", error.getMessage());
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        // Set parameters for the update request
                                        Map<String, String> params = new HashMap<>();
                                        params.put("clubid", room);
                                        params.put("studentid", userIdp);
                                        return params;
                                    }
                                };

                                // Add the update request to the Volley request queue
                                requestQueue.add(updateRequest);

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
                        dataDisplayTextView.setText("Data Extraction Failed");
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
        });

        // Customize the retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the Volley request queue
        requestQueue1.add(stringRequest);
    }

    // Method to handle the update logic
    private void updateData(String room, String selectedStatus,String couname) {
        // Implement your logic to update the data in the database with the selected status
        // You may use Volley or another method to make an update request
        // Display a message or perform any additional actions based on the update result
        String updateUrl = "http://10.0.2.2/pavaniapp/stdenrollregister.php"; // Replace with your PHP script's update URL
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a StringRequest to make a POST request for updating data
        StringRequest updateRequest1 = new StringRequest(Request.Method.POST, updateUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the update response
                        Log.d("Update Response", response);
                        finish();
                        startActivity(getIntent());
                        // You can display a message or perform other actions based on the update result
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors during the update request
                Log.e("Update Error", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Set parameters for the update request
                Map<String, String> params = new HashMap<>();
                params.put("courseid", room);
                params.put("studentid", selectedStatus);
                params.put("coursename",couname);
                return params;
            }
        };

        // Add the update request to the Volley request queue
        requestQueue.add(updateRequest1);
    }
//    public void checkstudentidcourseid(String clubid,String stdid)
//    {
//        // Implement your logic to update the data in the database with the selected status
//        // You may use Volley or another method to make an update request
//        // Display a message or perform any additional actions based on the update result
//        String updateUrl = "http://10.0.2.2/pavaniapp/stdenrollcheck.php"; // Replace with your PHP script's update URL
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        // Create a StringRequest to make a POST request for updating data
//        StringRequest updateRequest = new StringRequest(Request.Method.POST, updateUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Handle the update response
//                        Log.d("Update Response", response);
//                        finish();
//                        startActivity(getIntent());
//                        // You can display a message or perform other actions based on the update result
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // Handle errors during the update request
//                Log.e("Update Error", error.getMessage());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // Set parameters for the update request
//                Map<String, String> params = new HashMap<>();
//                params.put("clubid", clubid);
//                params.put("studentid", stdid);
//                return params;
//            }
//        };
//
//        // Add the update request to the Volley request queue
//        requestQueue.add(updateRequest);
//    }
}
