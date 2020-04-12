package com.arjun.survey;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;


import com.arjun.survey.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Animatable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import mehdi.sakout.fancybuttons.FancyButton;

public class Start extends AppCompatActivity implements View.OnClickListener{

//    Button btnplay;
//    FirebaseDatabase database;
//    DatabaseReference questions;
    FancyButton facebookLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


//        database=FirebaseDatabase.getInstance();

        facebookLoginBtn=(FancyButton)findViewById(R.id.btnPlay);
        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iz =new Intent(Start.this,Playing.class);
                startActivity(iz);
                finish();
            }
        });


        TextView textab=(TextView)findViewById(R.id.textab);
        TextView desc=(TextView)findViewById(R.id.description);
        desc.setText(Common.categoryName);
        long date=System.currentTimeMillis();

        SimpleDateFormat aa=new SimpleDateFormat("dd MMM yyyy, h:mm a");
        String datestring=aa.format(date);
        textab.setText(""+datestring);
        ExpandableTextView expTv1 = (ExpandableTextView)findViewById(R.id.expand_text_view);
        expTv1.setText("This is a survey \ud83d\udcd4 for "+Common.categoryName+".\nIt has "+Common.Questions+" questions.\nMake sure you have a working internet connection before giving this survey.\nWe are thankful for your valuable time and opinion.");

//        MagicButton btnYoutube=(MagicButton) findViewById(R.id.magic_button_youtube);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout cool=(CollapsingToolbarLayout)findViewById(R.id.ab12);
        setSupportActionBar(toolbar);
        cool.setTitle("Survey");
//        btnYoutube.setMagicButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent ii=new Intent(scrollview.this,value.class);
//                startActivity(ii);
//            }
//        }

//        bu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ii=new Intent(scrollview.this,value.class);
//                startActivity(ii);
//            }
//        });
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cool.setCollapsedTitleTextAppearance(R.style.Text123);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Start.this,Home.class);
                startActivity(in);
                finish();

            }
        });

    }

//    private void loadQuestion(String categoryId) {
//
//        if(Common.questionList.size()>0)
//            Common.questionList.clear();
//
//
//        questions.orderByChild("CategoryId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()){
//
//                    Question ques=postSnapShot.getValue(Question.class);
//                    Common.questionList.add(ques);
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }

    @Override
    public void onClick(View v) {

    }
}
