package com.arjun.survey;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arjun.survey.Common.Common;
import com.arjun.survey.frameadapter.frameAdapter;
import com.bernaferrari.emojislider.EmojiSlider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ritik.emojireactionlibrary.ClickInterface;
import com.ritik.emojireactionlibrary.EmojiReactionView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Playing extends AppCompatActivity {


    EmojiReactionView myImage;

    EmojiSlider sliderv1,sliderv2,sliderv3,sliderv4;

    int clickedEmoji = 0;
    LinearLayout layoutSingle,layoutMultiple,layoutText,layoutEmoji,layoutEmoji1,layout5050;
    EditText editText;
    int index=0,score=0,thisQuestion=0,totalQuestion;
    String results="";
    RadioButton btnA,btnB,btnC,btnD,btnA5050,btnB5050;
    CircleImageView circleImageView;
    CheckBox checkA,checkB,checkC,checkD;
    TextView profile_name;
    RadioGroup radioGroup,radioGroup5050;
    View views;


    //Firebase

//    FirebaseDatabase database;
//    DatabaseReference questions;

    ProgressBar progressBar;
    Button nextBtn;
    TextView question_text;

    public Playing() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);




        //Views

        views=(View)findViewById(R.id.views);
        sliderv1=(EmojiSlider)findViewById(R.id.sliderv1);
        sliderv1.setSliderParticleSystem(views);


        sliderv2=(EmojiSlider)findViewById(R.id.sliderv2);
        sliderv2.setSliderParticleSystem(views);



        sliderv3=(EmojiSlider)findViewById(R.id.sliderv3);
        sliderv3.setSliderParticleSystem(views);


        sliderv4=(EmojiSlider)findViewById(R.id.sliderv4);
        sliderv4.setSliderParticleSystem(views);

        myImage = (EmojiReactionView)findViewById(R.id.image);
        if (savedInstanceState != null) {
            clickedEmoji = savedInstanceState.getInt("emojiNumber");
            myImage.setClickedEmojiNumber(clickedEmoji);
        }


        myImage.setOnEmojiClickListener(new ClickInterface() {
            @Override
            public void onEmojiClicked(int emojiIndex, int x, int y) {
                String message;
                if (x != -1) {
                    switch (emojiIndex) {
                        case 0:
                            message = " Loved... ";
                            Common.emojiResponse="Loved";
                            break;
                        case 1:
                            message = " Shocked!! ";
                            Common.emojiResponse="Shocked";
                            break;
                        case 2:
                            message = " Sad... ";
                            Common.emojiResponse="Sad";
                            break;
                        case 3:
                            message = " Great!! ";
                            Common.emojiResponse="Great";
                            break;
                        case 4:
                            message = "  ";

                            break;
                        case 5:
                            message = "  ";
                            break;
                        default:
                            message = " ** ";
                            Common.emojiResponse="";
                    }
                    //Toast.makeText(Playing.this,message, Toast.LENGTH_SHORT).show();
                }
                clickedEmoji = emojiIndex;

            }

            @Override
            public void onEmojiUnclicked(int emojiIndex, int x, int y) {

                Common.emojiResponse="";
//                if (x != -1)
//                    Toast.makeText(getActivity(), "Emoji " + emojiIndex +" removed", Toast.LENGTH_SHORT).show();
            }
        });




        circleImageView=(CircleImageView)findViewById(R.id.profile_image);
        profile_name=(TextView)findViewById(R.id.profile_name);
        profile_name.setText(Common.currentUser);
        layout5050=(LinearLayout)findViewById(R.id.layout5050);
        layoutEmoji1=(LinearLayout)findViewById(R.id.layoutEmoji1);
        layoutSingle=(LinearLayout)findViewById(R.id.layoutSingle);
        layoutMultiple=(LinearLayout)findViewById(R.id.layoutMultiple);
        layoutEmoji=(LinearLayout)findViewById(R.id.layoutEmoji);
        layoutText=(LinearLayout)findViewById(R.id.layoutText);
        btnA=(RadioButton)findViewById(R.id.btnAnswerA);
        btnB=(RadioButton)findViewById(R.id.btnAnswerB);
        btnA5050=(RadioButton)findViewById(R.id.btnAnswerA5050);
        btnB5050=(RadioButton)findViewById(R.id.btnAnswerB5050);
        btnC=(RadioButton)findViewById(R.id.btnAnswerC);
        btnD=(RadioButton)findViewById(R.id.btnAnswerD);
        checkA=(CheckBox)findViewById(R.id.checkA);
        checkB=(CheckBox)findViewById(R.id.checkB);
        checkC=(CheckBox)findViewById(R.id.checkC);
        checkD=(CheckBox)findViewById(R.id.checkD);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        radioGroup5050=(RadioGroup)findViewById(R.id.radiogroup5050);
        editText=(EditText)findViewById(R.id.editText);
        String one= frameAdapter.conversion_to_some_variables_indexes(Common.one);
        frameAdapter.doubtsid=one;





        question_text=(TextView)findViewById(R.id.question_text);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

        nextBtn=(Button)findViewById(R.id.nextBtn);

        Picasso.with(getApplicationContext()).load(Common.currentUserUri).into(circleImageView);



       nextBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {



//               Toast.makeText(Playing.this, ""+sliderv1.getProgress(), Toast.LENGTH_SHORT).show();
//               sliderv1.setProgress(0.0f);
//               sliderv2.setProgress(0.0f);
//               sliderv3.setProgress(0.0f);
//               sliderv4.setProgress(0.0f);
               if (index < totalQuestion) {

                   if (Common.questionList.get(index).getType().equals("scq")) {
                       String temptext = "";
                       if (btnA.isChecked()) {
                           temptext = btnA.getText().toString();

                       } else if (btnB.isChecked()) {
                           temptext = btnB.getText().toString();

                       } else if (btnC.isChecked()) {
                           temptext = btnC.getText().toString();

                       } else if (btnD.isChecked()) {
                           temptext = btnD.getText().toString();

                       }
                       score += 10;



                       if(temptext!="") {
                           progressBar.setProgress(index+1);
                           results += temptext+"@@@";
                           radioGroup.clearCheck();
                           showQuestion(++index); // next Question

                       }
                       else{
                           Toast.makeText(Playing.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
                       }


                   }
                   else if(Common.questionList.get(index).getType().equals("mcq")){
                       String temptext = "";

                       if (checkA.isChecked()) {
                           temptext += checkA.getText().toString()+"$$$";
                           checkA.setChecked(false);
                       } if (checkB.isChecked()) {
                           temptext += checkB.getText().toString()+"$$$";
                           checkB.setChecked(false);
                       } if (checkC.isChecked()) {
                           temptext += checkC.getText().toString()+"$$$";
                           checkC.setChecked(false);
                       } if (checkD.isChecked()) {
                           temptext += checkD.getText().toString()+"$$$";
                           checkD.setChecked(false);
                       }

                       if(temptext!="") {
                           progressBar.setProgress(index+1);
                           results += temptext+"@@@";
                           showQuestion(++index); // next Question
                       }
                       else{
                           Toast.makeText(Playing.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
                       }


                   }

                   else if(Common.questionList.get(index).getType().equals("text")){
                       String temptext=editText.getText().toString();
                       //Toast.makeText(Playing.this, "-"+temptext+"-"+temptext.length(), Toast.LENGTH_SHORT).show();
                       if(temptext.length()>0) {
                           if(temptext.length()<200) {
                               temptext=temptext.replaceAll("[^a-zA-Z0-9!@#$%^&*(),._{}+=?<>|:;~` ]+","");
                               progressBar.setProgress(index + 1);
                               results += temptext + "@@@";
                               editText.setText("");
                               showQuestion(++index); // next Question
                           }
                           else{
                               Toast.makeText(Playing.this, "Please write your response under 200 characters", Toast.LENGTH_LONG).show();
                           }
                       }
                       else{
                           Toast.makeText(Playing.this, "Please write your response", Toast.LENGTH_SHORT).show();
                       }
                   }


                   else if(Common.questionList.get(index).getType().equals("emoji")){
                       //Toast.makeText(Playing.this, "-"+temptext+"-"+temptext.length(), Toast.LENGTH_SHORT).show();
                       if(Common.emojiResponse!="") {

//                               temptext=temptext.replaceAll("[^a-zA-Z0-9!@#$%^&*(),._{}+=?<>|:;~` ]+","");
                               progressBar.setProgress(index + 1);
                               results += Common.emojiResponse + "@@@";
                               myImage.setClickedEmojiNumber(-1);
                               //editText.setText("");
                               showQuestion(++index); // next Question

                           }


                       else{
                           Toast.makeText(Playing.this, "Please select the emoji", Toast.LENGTH_SHORT).show();
                       }
                   }




                   else if(Common.questionList.get(index).getType().equals("love") || Common.questionList.get(index).getType().equals("shock") || Common.questionList.get(index).getType().equals("sad") || Common.questionList.get(index).getType().equals("great")){
                       //Toast.makeText(Playing.this, "-"+temptext+"-"+temptext.length(), Toast.LENGTH_SHORT).show();
                       if(Common.emojisResponses=="v1") {

//                               temptext=temptext.replaceAll("[^a-zA-Z0-9!@#$%^&*(),._{}+=?<>|:;~` ]+","");

                           int checks=Math.round(sliderv1.getProgress()*100);
                           sliderv1.setProgress(0.0f);
                           progressBar.setProgress(index + 1);
                           results += checks + "@@@";

                           //editText.setText("");
                           showQuestion(++index); // next Question

                       }


                       else if(Common.emojisResponses=="v2") {

//                               temptext=temptext.replaceAll("[^a-zA-Z0-9!@#$%^&*(),._{}+=?<>|:;~` ]+","");

                           int checks=Math.round(sliderv2.getProgress()*100);
                           sliderv2.setProgress(0.0f);
                           progressBar.setProgress(index + 1);
                           results += checks + "@@@";

                           //editText.setText("");
                           showQuestion(++index); // next Question

                       }
                       else if(Common.emojisResponses=="v3") {

//                               temptext=temptext.replaceAll("[^a-zA-Z0-9!@#$%^&*(),._{}+=?<>|:;~` ]+","");

                           int checks=Math.round(sliderv3.getProgress()*100);
                           sliderv3.setProgress(0.0f);
                           progressBar.setProgress(index + 1);
                           results += checks + "@@@";

                           //editText.setText("");
                           showQuestion(++index); // next Question

                       }
                       else if(Common.emojisResponses=="v4") {

//                               temptext=temptext.replaceAll("[^a-zA-Z0-9!@#$%^&*(),._{}+=?<>|:;~` ]+","");

                           int checks=Math.round(sliderv4.getProgress()*100);
                           sliderv4.setProgress(0.0f);
                           progressBar.setProgress(index + 1);
                           results += checks + "@@@";

                           //editText.setText("");
                           showQuestion(++index); // next Question

                       }
                   }

                   else if (Common.questionList.get(index).getType().equals("5050")) {
                       String temptext = "";
                       if (btnA5050.isChecked()) {
                           temptext = btnA5050.getText().toString();

                       } else if (btnB5050.isChecked()) {
                           temptext = btnB5050.getText().toString();

                       }
                       score += 10;



                       if(temptext!="") {
                           progressBar.setProgress(index+1);
                           results += temptext+"@@@";
                           radioGroup5050.clearCheck();
                           showQuestion(++index); // next Question

                       }
                       else{
                           Toast.makeText(Playing.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
                       }


                   }

                if(index==totalQuestion-1){

                    nextBtn.setText("Submit");


                }



               }


           }



       });

    }



    private void showQuestion(int index) {

        if (index < totalQuestion) {
            thisQuestion++;




            if (Common.questionList.get(index).getType().equals("scq")) {

                //If is image;
                layoutSingle.setVisibility(View.VISIBLE);
                layout5050.setVisibility(View.GONE);
                layoutEmoji.setVisibility(View.GONE);
                layoutMultiple.setVisibility(View.GONE);
                layoutText.setVisibility(View.GONE);
                layoutEmoji1.setVisibility(View.GONE);
                int var=index+1;
                question_text.setText("Q"+var+". "+Common.questionList.get(index).getQuestion());




                btnA.setText(Common.questionList.get(index).getAnswerA());
                btnB.setText(Common.questionList.get(index).getAnswerB());
                btnC.setText(Common.questionList.get(index).getAnswerC());
                btnD.setText(Common.questionList.get(index).getAnswerD());





            }

            else if(Common.questionList.get(index).getType().equals("mcq")){

                layoutMultiple.setVisibility(View.VISIBLE);
                layout5050.setVisibility(View.GONE);
                layoutEmoji1.setVisibility(View.GONE);
                layoutSingle.setVisibility(View.GONE);
                layoutEmoji.setVisibility(View.GONE);
                layoutText.setVisibility(View.GONE);
                int var=index+1;
                checkA.setText(Common.questionList.get(index).getAnswerA());
                checkB.setText(Common.questionList.get(index).getAnswerB());
                checkC.setText(Common.questionList.get(index).getAnswerC());
                checkD.setText(Common.questionList.get(index).getAnswerD());

                question_text.setText("Q"+var+". "+Common.questionList.get(index).getQuestion());





            }




            else if(Common.questionList.get(index).getType().equals("text")) {
                int var=index+1;
                layoutText.setVisibility(View.VISIBLE);
                layout5050.setVisibility(View.GONE);
                layoutEmoji1.setVisibility(View.GONE);
                layoutEmoji.setVisibility(View.GONE);
                layoutMultiple.setVisibility(View.GONE);
                layoutSingle.setVisibility(View.GONE);
                question_text.setText("Q"+var+". "+Common.questionList.get(index).getQuestion());




            }



            else if(Common.questionList.get(index).getType().equals("emoji")) {
                int var=index+1;
                layoutEmoji.setVisibility(View.VISIBLE);
                layout5050.setVisibility(View.GONE);
                layoutText.setVisibility(View.GONE);
                layoutMultiple.setVisibility(View.GONE);
                layoutEmoji1.setVisibility(View.GONE);
                layoutSingle.setVisibility(View.GONE);
                Common.emojiResponse="";
                question_text.setText("Q"+var+". "+Common.questionList.get(index).getQuestion());




            }




            else if(Common.questionList.get(index).getType().equals("love") || Common.questionList.get(index).getType().equals("shock") || Common.questionList.get(index).getType().equals("sad") || Common.questionList.get(index).getType().equals("great")) {

                String s1=Common.questionList.get(index).getType();
                if(s1.equals("love")) {
                    Common.emojisResponses = "v1";
                    sliderv1.setVisibility(View.VISIBLE);
                    sliderv2.setVisibility(View.GONE);
                    sliderv3.setVisibility(View.GONE);
                    sliderv4.setVisibility(View.GONE);

                }
                else if(s1.equals("shock")){
                    Common.emojisResponses="v2";
                    sliderv1.setVisibility(View.GONE);
                    sliderv2.setVisibility(View.VISIBLE);
                    sliderv3.setVisibility(View.GONE);
                    sliderv4.setVisibility(View.GONE);

                }

                else if(s1.equals("sad")){
                    Common.emojisResponses="v3";
                    sliderv1.setVisibility(View.GONE);
                    sliderv2.setVisibility(View.GONE);
                    sliderv3.setVisibility(View.VISIBLE);
                    sliderv4.setVisibility(View.GONE);

                }
                else if(s1.equals("great")){
                    Common.emojisResponses="v4";
                    sliderv1.setVisibility(View.GONE);
                    sliderv2.setVisibility(View.GONE);
                    sliderv3.setVisibility(View.GONE);
                    sliderv4.setVisibility(View.VISIBLE);
                }
                int var=index+1;
                layoutEmoji1.setVisibility(View.VISIBLE);
                layoutEmoji.setVisibility(View.GONE);
                layout5050.setVisibility(View.GONE);
                layoutText.setVisibility(View.GONE);
                layoutMultiple.setVisibility(View.GONE);
                layoutSingle.setVisibility(View.GONE);
                question_text.setText("Q"+var+". "+Common.questionList.get(index).getQuestion());




            }


            else if (Common.questionList.get(index).getType().equals("5050")) {

                //If is image;
                layoutSingle.setVisibility(View.GONE);
                layout5050.setVisibility(View.VISIBLE);
                layoutEmoji.setVisibility(View.GONE);
                layoutMultiple.setVisibility(View.GONE);
                layoutText.setVisibility(View.GONE);
                layoutEmoji1.setVisibility(View.GONE);
                int var=index+1;
                question_text.setText("Q"+var+". "+Common.questionList.get(index).getQuestion());




                btnA5050.setText(Common.questionList.get(index).getAnswerA());
                btnB5050.setText(Common.questionList.get(index).getAnswerB());
//                btnC.setText(Common.questionList.get(index).getAnswerC());
//                btnD.setText(Common.questionList.get(index).getAnswerD());
//




            }





        }



    else{

            final MediaPlayer mp = MediaPlayer.create(this, R.raw.soho);
            mp.start();

            Intent i = new Intent(Playing.this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putString("results", results);
            i.putExtras(dataSend);
            startActivity(i);
            finish();


        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion=Common.questionList.size();
        progressBar.setMax(totalQuestion);
        showQuestion(index);







    }
}
