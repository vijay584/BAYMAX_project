package com.baymax.baymax;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
public class Test extends Fragment {




    public Test() {
        // Required empty public constructor
    }
    SearchView mSearchView;
    ListView mMedList;
    ArrayList<DataSnapshot> mDataSnapShotArray;
    String mMed[]={"Calpole","crossin","detol","sumo","radol","a","s","d","h","j","t","o"};
    String mDes[]={"fever","fever","wound","fever","vomiting","z","x","c","v","b","n","m"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView=inflater.inflate(R.layout.test, container, false);
        mSearchView=(SearchView)rootView.findViewById(R.id.symptom);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                final String symptom=mSearchView.getQuery().toString();
                DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance().getReference();
                Query query=mDatabaseReference.child("Medicine");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mDataSnapShotArray=new ArrayList<>();
                        for (DataSnapshot snapshot :
                                dataSnapshot.getChildren()){
                            if(snapshot.child("Use1").getValue().toString().equals(symptom) || snapshot.child("Use2").getValue().toString().equals(symptom) || snapshot.child("Use3").getValue().toString().equals(symptom)){
                                Log.d( "onDataChange: ",snapshot.child("Med_name").getValue().toString());
                                mDataSnapShotArray.add(snapshot);
                            }

                        }
                        mMedList=(ListView)rootView.findViewById(R.id.medicine_list);
                        BaymaxAdaptor bmxAaptor=new BaymaxAdaptor(getActivity().getApplicationContext(),mDataSnapShotArray);
                        mMedList.setAdapter((ListAdapter) bmxAaptor);

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
        // Inflate the layout for this fragment
        //mSearch_Med=(Button)rootView.findViewById(R.id.searchMed);
        //mEditText=(EditText)rootView.findViewById(R.id.editText);
        //mSearch_Med.setOnClickListener(new View.OnClickListener() {
          /*  @Override
            public void onClick(View view) {
                String search=mEditText.getText().toString();
                Toast.makeText(getContext(),search,Toast.LENGTH_SHORT).show();
                mMedList=(ListView)rootView.findViewById(R.id.medicine_list);
                BaymaxAdaptor bmxAaptor=new BaymaxAdaptor(getActivity().getApplicationContext(),mMed,mDes);
                mMedList.setAdapter((ListAdapter) bmxAaptor);
            }
        });*/
        return rootView;
    }

}
