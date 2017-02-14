package com.example.user.projects_team;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //floating action button 선언
    FloatingActionButton fab_main, fab_chat, fab_write;

    //floating action button animation 선언
    Animation fabOpen, fabClose, fabClockwise, fabAntiClockwise;
    Button btn, btn2;

    //변수
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //버튼 객체
        btn = (Button) findViewById(R.id.button_school_lunch);
        btn2 = (Button) findViewById(R.id.button_notice);

        //Floating Action Button 객체
        fab_main = (FloatingActionButton) findViewById(R.id.fab_main);
        fab_chat = (FloatingActionButton) findViewById(R.id.fab_chat);
        fab_write = (FloatingActionButton) findViewById(R.id.fab_write);

        //Floating Action Button Animation 객체
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);


        View.OnClickListener foodlistener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        };

        View.OnClickListener noticelistener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SchoolNoticeActivity.class);
                startActivity(intent);
            }
        };

        btn.setOnClickListener(foodlistener);
        btn2.setOnClickListener(noticelistener);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen)
                {
                    fab_write.startAnimation(fabClose);
                    fab_chat.startAnimation(fabClose);
                    //fab_main.startAnimation(fabClockwise);
                    fab_chat.setClickable(false);
                    fab_write.setClickable(false);
                    isOpen = false;
                }
                else
                {
                    fab_write.startAnimation(fabOpen);
                    fab_chat.startAnimation(fabOpen);
                    //fab_main.startAnimation(fabAntiClockwise);
                    fab_chat.setClickable(true);
                    fab_write.setClickable(true);
                    isOpen = true;
                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            Intent intent = new Intent(this, aboutDslife.class);
            startActivity(intent);
        }

        else if (id == R.id.setting) {

        }

        else if (id == R.id.signin) {
            Intent intent = new Intent(this, fcmLogin.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_share) {

        }

        else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        drawer.clearFocus();
        return true;
    }
}
