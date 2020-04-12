package com.arjun.survey;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arjun.survey.Common.Common;
import com.arjun.survey.frameadapter.frameAdapter;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;


public class MainActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN=123;
    FirebaseAuth auth;

    Button login;
    GoogleSignInClient googleSignInClient;

    LinearLayout l1,l2,containers;
    Animation uptodown,downtoup;
    AnimationDrawable anim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        containers = (LinearLayout) findViewById(R.id.containers);
        anim = (AnimationDrawable) containers.getBackground();
        anim.setEnterFadeDuration(2000);
        anim.setExitFadeDuration(7000);



        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);


        login=(Button)findViewById(R.id.login);






        /* authenticate user to sign in */
        String one=frameAdapter.conversion_to_some_variables_indexes(Common.one);
        frameAdapter.doubtsid=one;
        String two=frameAdapter.conversion_to_some_variables_indexes(Common.two);
        frameAdapter.frameid=two;
        String three=frameAdapter.conversion_to_some_variables_indexes(Common.three);
        frameAdapter.answersid=three;
//        String four=frameAdapter.conversion_to_some_variables_indexes(Common.finals);
//        frameAdapter.succ=four;



        SharedPreferences settings=getSharedPreferences("Preference0",MODE_PRIVATE);
        String firstStart=settings.getString("FirstTimeInstalled0","");
        if (firstStart.equals("Yes")) {



        }
        else{
            SharedPreferences.Editor editor=settings.edit();
            editor.putString("FirstTimeInstalled0","Yes");
            editor.apply();
            TapTargetView.showFor(this,                 // `this` is an Activity
                    TapTarget.forView(findViewById(R.id.login), "Login", "SignIn with your google account.").tintTarget(false).outerCircleColor(R.color.followers));

        }



        auth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInGoogle();
            }
        });

        //Firebase

    }

    private void SignInGoogle() {

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN);

    }




    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Log.d("TAG", "signInWithCredential:success");
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {


                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                        Toast.makeText(this,"Authentication failed. !",Toast.LENGTH_LONG).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());


            Common.currentUser=name;
            Common.currentUserEmail=email;
            Common.currentUserUri=photo;


            Intent i=new Intent(MainActivity.this,Home.class);
            startActivity(i);
            finish();


        } else {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (Exception e) {
                Log.w("TAG", "Google sign in failed", e);
                Toast.makeText(MainActivity.this,""+e,Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= auth.getCurrentUser();


        if(user!=null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());


            Common.currentUser=name;
            Common.currentUserEmail=email;
            Common.currentUserUri=photo;
            Intent i=new Intent(MainActivity.this,Home.class);
            startActivity(i);
            finish();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }
}