package com.baymax.baymax;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;
import static com.baymax.baymax.Main.AGE_KEY;
import static com.baymax.baymax.Main.DISPLAY_NAME_KEY;
import static com.baymax.baymax.Main.GENDER_KEY;
import static com.baymax.baymax.Main.USER_PREF;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    TextView name,age,gender;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_home, container, false);
        age=(TextView)rootView.findViewById(R.id.textView7);
        gender=(TextView)rootView.findViewById(R.id.textView9);
        name=(TextView)rootView.findViewById(R.id.textView);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(USER_PREF,MODE_PRIVATE);
        String mDisplayName =prefs.getString(DISPLAY_NAME_KEY,null);
        String mAge=prefs.getString(AGE_KEY,null);
        String mGender=prefs.getString(GENDER_KEY,null);
        name.append(mDisplayName);
        age.append(mAge);
        gender.append(mGender);
        return rootView;
    }

}
