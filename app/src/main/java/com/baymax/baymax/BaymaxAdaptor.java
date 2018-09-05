package com.baymax.baymax;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class BaymaxAdaptor extends BaseAdapter {

    Context mContext;
    //String mMed[];
    //String mDec[];
    ArrayList<DataSnapshot> mSnapShot;
    LayoutInflater inflater;

    public BaymaxAdaptor(Context context, ArrayList<DataSnapshot> snapShot){
        mContext=context;
        mSnapShot=snapShot;
        //mMed=med;
       // mDec=dec;
        inflater =(LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return mSnapShot.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.list_view,null);
        TextView medicine=(TextView)view.findViewById(R.id.textView2);
        TextView description=(TextView)view.findViewById(R.id.textView3);
        String med_name=mSnapShot.get(i).child("Med_name").getValue().toString();
        String Description=mSnapShot.get(i).child("Med_Description").getValue().toString();
        medicine.append(med_name);
        description.append(Description);

        return view;
    }
}
