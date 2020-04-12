package com.arjun.survey;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.arjun.survey.Common.Common;
import com.arjun.survey.frameadapter.frameAdapter;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;


public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_home);
        String one=frameAdapter.conversion_to_some_variables_indexes(Common.one);
        frameAdapter.doubtsid=one;
        String two=frameAdapter.conversion_to_some_variables_indexes(Common.two);
        frameAdapter.frameid=two;
        String three=frameAdapter.conversion_to_some_variables_indexes(Common.three);
        frameAdapter.answersid=three;




        bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notificationChannel=new NotificationChannel("MyNotification","MyNotification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }




        SharedPreferences settings=getSharedPreferences("Preference",MODE_PRIVATE);
        String firstStart=settings.getString("FirstTimeInstalled","");
        if (firstStart.equals("Yes")) {



        }
        else{
            SharedPreferences.Editor editor=settings.edit();
            editor.putString("FirstTimeInstalled","Yes");
            editor.apply();
            TapTargetView.showFor(this,                 // `this` is an Activity
                    TapTarget.forView(findViewById(R.id.navigation), "Select Survey", "Make sure you have a working internet before giving the survey.").tintTarget(false).outerCircleColor(R.color.greens));

        }



            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment=null;
                switch (menuItem.getItemId()){
                    case R.id.action_category:
                        selectedFragment=CategoryFragment.newInstance();
                        break;

                    case R.id.action_ranking:
                        selectedFragment=RankingFragment.newInstance();
                        break;



                }

                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,selectedFragment);
                fragmentTransaction.commit();
                return true;
            }


        });

        setDefaultFragment();



    }


    private void setDefaultFragment() {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,CategoryFragment.newInstance());
        fragmentTransaction.commit();

    }


    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user= auth.getCurrentUser();




        if(user==null){
            Intent i=new Intent(Home.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    }



}
