package com.baymax.baymax;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class home extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment mhomeFragment;
    private Test mTestFragment;
    private Test2Fragmet mTest2Fragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);



        mhomeFragment = new HomeFragment();
        mTestFragment = new Test();
        mTest2Fragment = new Test2Fragmet();

        if(savedInstanceState == null)
        {
            mMainNav.setItemBackgroundResource(R.color.colorPrimary);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, mhomeFragment);
            fragmentTransaction.commit();
        }



        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(mhomeFragment);
                        return true;


                    case R.id.nav_test:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(mTestFragment);
                        return true;


                    case R.id.nav_test2:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(mTest2Fragment);
                        return true;

                    default:
                        return false;

                }
            }

            private void setFragment(Fragment fragment) {
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.commit();
            }
        });

    }



    }


