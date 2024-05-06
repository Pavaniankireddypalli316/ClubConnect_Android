package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class facultyhomepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultyhomepage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout =findViewById(R.id.drawerlayout);
        NavigationView navigationView= findViewById(R.id.faculty_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id= item.getItemId();

        if(id == R.id.fac_nav_home)
        {
            Intent intent=new Intent(this, homepage.class);
            Intent intent1 = getIntent();
            String piduser = intent1.getStringExtra("id");
            intent.putExtra("id", piduser);
            startActivity(intent);
        }
        else if (id == R.id.fac_nav_profile)
        {
            Intent intent=new Intent(this, facprofilepage.class);
            Intent intent1 = getIntent();
            String piduser = intent1.getStringExtra("id");
            intent.putExtra("id", piduser);
            startActivity(intent);
        }
        else if(id == R.id.fac_nav_attendance)
        {
            Intent intent=new Intent(this, facultyattendance.class);
            Intent intent1 = getIntent();
            String piduser = intent1.getStringExtra("id");
            intent.putExtra("id", piduser);
            startActivity(intent);
        }
        else if (id == R.id.fac_nav_registration)
        {
            Intent intent=new Intent(this, facultyregistration.class);
            Intent intent1 = getIntent();
            String piduser = intent1.getStringExtra("id");
            intent.putExtra("id", piduser);
            startActivity(intent);
        }
        else if(id == R.id.fac_nav_launch_course)
        {
            Intent intent=new Intent(this, facultylaunchcourse.class);
            Intent intent1 = getIntent();
            String piduser = intent1.getStringExtra("id");
            intent.putExtra("id", piduser);
            startActivity(intent);
        }
        else if(id == R.id.fac_nav_logout)
        {
            Intent intent=new Intent(this, startpage.class);
            Intent intent1 = getIntent();
            String piduser = intent1.getStringExtra("id");
            intent.putExtra("id", piduser);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}