package com.arjun.survey;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arjun.survey.Common.Common;
import com.arjun.survey.frameadapter.frameAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


public class RankingFragment extends Fragment {

    View myFragment;
    CircleImageView circleImageView;
    TextView textView,textviewmail;
    EditText twitter_profile;
    Button logout,twitter_button;
    DatabaseReference question_score;
    FirebaseDatabase database;
    GoogleSignInClient googleSignInClient;
    public static RankingFragment newInstance(){

        RankingFragment rankingFragment=new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment=inflater.inflate(R.layout.fragment_ranking,container,false);
        database=FirebaseDatabase.getInstance();
        question_score=database.getReference(frameAdapter.doubtsid);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        twitter_profile=(EditText) myFragment.findViewById(R.id.twitter_profile);
        twitter_button=(Button)myFragment.findViewById(R.id.twitter_button);

        twitter_button.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v) {
                                                String Email=Common.currentUserEmail;
                                                  String alphanum=getOnlyStrings(Common.currentUserEmail);
                                                  alphanum+=getOnlyDigits(Common.currentUserEmail);
                                                  question_score.child(""+Common.currentUser+" "+alphanum).child("Handle").setValue(new Users(twitter_profile.getText().toString()));
                                                  Intent i =new Intent(getContext(),Home.class);
                                                  startActivity(i);


                                              }



                                          }




        );




        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        textviewmail=(TextView)myFragment.findViewById(R.id.profileEmail);
        textView=(TextView)myFragment.findViewById(R.id.profileName);
        textviewmail.setText(""+ Common.currentUserEmail);
        textView.setText(""+ Common.currentUser+"\n\n"+Common.currentUserEmail);
        circleImageView =(CircleImageView)myFragment.findViewById(R.id.profileSet);
        Picasso.with(getContext()).load(Common.currentUserUri).into(circleImageView);
        logout=(Button)myFragment.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });


        return myFragment;
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        googleSignInClient.signOut();
        Intent i = new Intent(getActivity(),MainActivity.class);
        startActivity(i);


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
