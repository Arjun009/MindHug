package com.arjun.survey;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.arjun.survey.Common.Common;
import com.arjun.survey.frameadapter.frameAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Done extends AppCompatActivity {



    TextView txtResultScore;
    FirebaseDatabase database;
    DatabaseReference question_score,success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        database=FirebaseDatabase.getInstance();

        question_score=database.getReference(frameAdapter.doubtsid);
        //success=database.getReference(frameAdapter.succ);


        txtResultScore=(TextView)findViewById(R.id.thanks);


        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            int Score=extra.getInt("SCORE");
            int totalQuestion=extra.getInt("TOTAL");
            String results=extra.getString("results");


            txtResultScore.setText("\u2b50   Thank You\n\n"+Common.currentUser+"\n"+Common.currentUserEmail);



            String alphanum=getOnlyStrings(Common.currentUserEmail);
            alphanum+=getOnlyDigits(Common.currentUserEmail);
            Boolean y;


            ConnectivityManager cm =
                    (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();

            if(isConnected){




            }

            else{



                Toast.makeText(this,"You are offline! Don't remove this app from recent use. It will send your response once you are online.",Toast.LENGTH_LONG).show();


            }
            //success.child(String.format("CategoryId-%s",Common.categoryId)).setValue(new Result("true"));
            question_score.child(""+Common.currentUser+" "+alphanum).child("About").setValue(new User(Common.currentUser,Common.currentUserEmail));
            question_score.child(""+Common.currentUser+" "+alphanum).child(String.format("CategoryId-%s",Common.categoryId)).setValue(new Result(results));
//
//            question_score.child(String.format("CategoryId-%s",Common.categoryId)).child(""+Common.currentUser+" "+alphanum)
//                    .setValue(new Result(results,Common.currentUserEmail,Common.currentUser));





        }



    }


    public static String getOnlyDigits(String s) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }
    public static String getOnlyStrings(String s) {
        Pattern pattern = Pattern.compile("[^a-z A-Z]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }



}
