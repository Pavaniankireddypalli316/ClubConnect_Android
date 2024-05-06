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

public class facatdpagetwo extends AppCompatActivity {

    private TextView dataDisplayTextView;
    private androidx.appcompat.widget.AppCompatButton button;
    private androidx.appcompat.widget.AppCompatButton backgohomepagebtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facatdpagetwo);

        button = findViewById(R.id.btn);
        backgohomepagebtn = findViewById(R.id.regbackbutton);
        dataDisplayTextView = findViewById(R.id.headingfacregptwo);

        backgohomepagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funtobackpage();
            }
        });

        fetchUserOrders();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent12 = getIntent();
                String clubid = intent12.getStringExtra("clubid");
                updateRecords(clubid);
            }
        });
    }

    public void funtobackpage() {
        Intent intent = new Intent(this, facultyhomepage.class);
        Intent intent1 = getIntent();
        String piduser = intent1.getStringExtra("id");
        intent.putExtra("id", piduser);
        startActivity(intent);
    }

    private void fetchUserOrders() {
        Intent intent1 = getIntent();
        String userIdp = intent1.getStringExtra("id");
        String clubid = intent1.getStringExtra("clubid");
        String url = "http://10.0.2.2/pavaniapp/facattendancetwo.php";

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("JSON Response", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    LinearLayout dataDisplayLinearLayout = findViewById(R.id.dataDisplayLinearLayout);

                    if (jsonResponse.getBoolean("status")) {
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject order = jsonArray.getJSONObject(i);
                                String stdid = order.getString("studentid");
                                String name = order.getString("name");
                                String status = order.getString("studentstatus");

                                View detailView = getLayoutInflater().inflate(R.layout.fac_attendance_block, null);

                                TextView detailNameTextView = detailView.findViewById(R.id.r);
                                TextView detailScoreTextView = detailView.findViewById(R.id.n);
                                TextView cnt = detailView.findViewById(R.id.actionstext);
                                Spinner statusSpinner = detailView.findViewById(R.id.statusSpinner);

                                detailNameTextView.setText("Student id: " + stdid);
                                detailScoreTextView.setText("Student Name: " + name);
                                cnt.setText("Attendance : ");

                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                                        R.array.fac_attendance_array, android.R.layout.simple_spinner_item);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                statusSpinner.setAdapter(adapter);

                                dataDisplayLinearLayout.addView(detailView);
                            }
                        } else {
                            dataDisplayTextView.setText("No data found for this user.");
                        }
                    } else {
                        dataDisplayTextView.setText("No One Registered");
                    }
                } catch (Exception e) {
                    dataDisplayTextView.setText("Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dataDisplayTextView.setText("Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("facid", userIdp);
                params.put("clubid", clubid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue1.add(stringRequest);
    }

    private void updateRecords(String clid) {
        LinearLayout dataDisplayLinearLayout = findViewById(R.id.dataDisplayLinearLayout);
        int childCount = dataDisplayLinearLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View detailView = dataDisplayLinearLayout.getChildAt(i);
            TextView detailNameTextView = detailView.findViewById(R.id.r);
            Spinner statusSpinner = detailView.findViewById(R.id.statusSpinner);

            String stdid = detailNameTextView.getText().toString().replace("Student id: ", "");
            String selectedStatus = statusSpinner.getSelectedItem().toString();

            updateData(stdid, selectedStatus, clid);
        }
    }

    private void updateData(String studentid, String selectedStatus, String clubidid) {
        String updateUrl = "http://10.0.2.2/pavaniapp/facattendanceupdate.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest updateRequest = new StringRequest(Request.Method.POST, updateUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Update Response", response);
                        finish();
                        startActivity(getIntent());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Update Error", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("stdid", studentid);
                params.put("status", selectedStatus);
                params.put("clubid",clubidid);
                return params;
            }
        };

        requestQueue.add(updateRequest);
    }
}
