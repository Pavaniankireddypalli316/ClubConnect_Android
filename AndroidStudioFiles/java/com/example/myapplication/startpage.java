package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class startpage extends AppCompatActivity {


    public LinearLayout btnstudent;
    public LinearLayout btnfaculty;
    public LinearLayout btnadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);

        btnstudent=findViewById(R.id.studentbutton);
        btnfaculty=findViewById(R.id.facultybutton);
        btnadmin=findViewById(R.id.adminbutton);
        btnstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotostudentloginpage();
            }
        });
        btnfaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_facultypage();
            }
        });
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoadimnpage();
            }
        });
    }
    public void gotostudentloginpage()
    {
        Intent intent=new Intent(this, loginpage.class);
        startActivity(intent);
    }
    public void goto_facultypage()
    {
        Intent intent=new Intent(this, facultyloginpage.class);
        startActivity(intent);
    }
    public void gotoadimnpage()
    {
        Intent intent=new Intent(this, adminloginpage.class);
        startActivity(intent);
    }
}