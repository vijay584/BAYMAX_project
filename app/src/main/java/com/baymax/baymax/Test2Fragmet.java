package com.baymax.baymax;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Test2Fragmet extends Fragment {


    public Test2Fragmet() {
        // Required empty public constructor
    }

    SearchView mSearchView;
    TextView mMedName,mMedDesc,mMedDescBody,mMedUse,mMedUseBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_test2_fragmet, container, false);
        mSearchView=(SearchView)rootView.findViewById(R.id.med_search_view);
        mMedName=(TextView)rootView.findViewById(R.id.med_name);
        mMedDesc=(TextView)rootView.findViewById(R.id.med_desc);
        mMedDescBody=(TextView)rootView.findViewById(R.id.med_desc_body);
        mMedUse=(TextView)rootView.findViewById(R.id.med_use);
        mMedUseBody=(TextView)rootView.findViewById(R.id.med_use_body);

        mMedName.setAlpha(0.0f);
        mMedDesc.setAlpha(0.0f);
        mMedDescBody.setAlpha(0.0f);
        mMedUse.setAlpha(0.0f);
        mMedUseBody.setAlpha(0.0f);

       // mSearchView.setQueryHint("Type your text here");
      //  mSearchView.setBackgroundColor(Color.WHITE);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String des=mSearchView.getQuery().toString();
                DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance().getReference();
                Query query=mDatabaseReference.child("Medicine").orderByChild("Med_name").equalTo(des);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            for (DataSnapshot snapshot :
                                    dataSnapshot.getChildren()){


                                String Name="NAME :  "+snapshot.child("Med_name").getValue().toString();
                                String Description=snapshot.child("Med_Description").getValue().toString();
                                String Use="\n1."+snapshot.child("Use1").getValue().toString()+"\n2."+snapshot.child("Use2").getValue().toString()+"\n3."+snapshot.child("Use3").getValue().toString();
                                mMedName.setAlpha(1.0f);
                                mMedDesc.setAlpha(1.0f);
                                mMedDescBody.setAlpha(1.0f);
                                mMedUse.setAlpha(1.0f);
                                mMedUseBody.setAlpha(1.0f);

                                mMedName.setText(Name);
                                mMedDescBody.setText(Description);
                                mMedUseBody.setText(Use);
                            }
                        }
                        else
                        {
                            mMedName.setAlpha(0.0f);
                            mMedDesc.setAlpha(0.0f);
                            mMedDescBody.setAlpha(0.0f);
                            mMedUse.setAlpha(0.0f);
                            mMedUseBody.setAlpha(0.0f);
                            Toast.makeText(getActivity(),"No Such Medicine Exists",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        String des=mSearchView.getQuery().toString();

        // Inflate the layout for this fragment
        return rootView;
    }

}
