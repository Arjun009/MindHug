package com.arjun.survey;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arjun.survey.Common.Common;
import com.arjun.survey.Interface.ItemClickListener;
import com.arjun.survey.ViewHolder.CategoryViewHolder;
import com.arjun.survey.frameadapter.frameAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;





public class CategoryFragment extends Fragment {

    View myFragment;


    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categories;
    FirebaseUser user;
    FirebaseAuth auth;
    ProgressBar pb;
    TextView mark;

    DatabaseReference questions;




    public static CategoryFragment newInstance(){

        CategoryFragment categoryFragment=new CategoryFragment();
        return categoryFragment;







    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        database=FirebaseDatabase.getInstance();
        categories=database.getReference(frameAdapter.frameid);
        questions=database.getReference(frameAdapter.answersid);




        String one=frameAdapter.conversion_to_some_variables_indexes(Common.one);
        frameAdapter.doubtsid=one;
        String two=frameAdapter.conversion_to_some_variables_indexes(Common.two);
        frameAdapter.frameid=two;
        String three=frameAdapter.conversion_to_some_variables_indexes(Common.three);
        frameAdapter.answersid=three;
//        String four=frameAdapter.conversion_to_some_variables_indexes(Common.finals);
//        frameAdapter.succ=four;


        loadQuestion(Common.categoryId);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment=inflater.inflate(R.layout.fragment_category,container,false);
        listCategory=(RecyclerView)myFragment.findViewById(R.id.listCategory);
        pb=(ProgressBar)myFragment.findViewById(R.id.pb);
        pb.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        pb.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(),"Loading...",Toast.LENGTH_LONG);
        listCategory.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);

        loadCategories();

        auth = FirebaseAuth.getInstance();


        user= auth.getCurrentUser();
        if(user!=null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());


            Common.currentUser=name;
            Common.currentUserEmail=email;
            Common.currentUserUri=photo;

        }



        return myFragment;
    }

    private void loadCategories() {


        adapter=new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(

                Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories
        ) {


            @Override
            protected void populateViewHolder(CategoryViewHolder holder, final Category model, int position) {
                holder.category_name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(holder.category_image);
                holder.mark.setText(""+adapter.getRef(position).getKey());
                pb.setVisibility(View.GONE);

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Common.categoryId=adapter.getRef(position).getKey();
                        Common.Questions=model.getQues();
                        loadQuestion(Common.categoryId);
                        Common.categoryName=model.getName();

                        Intent ii = new Intent(getActivity(), Start.class);

                        startActivity(ii);



//
//                        new Handler().postDelayed(new Runnable() {
//
//                                                      @Override
//                                                      public void run() {
//
//                                                          Intent ii=new Intent(getActivity(),Start.class);
//                                                          startActivity(ii);
//
//                                                      }
//                                                  },1000);








                        // Toast.makeText(getActivity(),String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()),Toast.LENGTH_SHORT).show();
                    }
                });




            }


        };

        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }
    private void loadQuestion(String categoryId) {

        if(Common.questionList.size()>0)
            Common.questionList.clear();


        questions.orderByChild("CategoryId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()){

                    Question ques=postSnapShot.getValue(Question.class);
                    Common.questionList.add(ques);


                }
                //.makeText(getActivity(),""+Common.questionList.size(),Toast.LENGTH_LONG).show();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}