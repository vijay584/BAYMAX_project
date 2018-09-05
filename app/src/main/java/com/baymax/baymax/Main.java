package com.baymax.baymax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Main extends AppCompatActivity {
    public static final String USER_PREF = "UserPrefs";
    public static final String DISPLAY_NAME_KEY = "username";
    public static final String AGE_KEY="age";
    public static final String GENDER_KEY="gender";

    DatabaseReference mDatabaseReference;
    Button mSigin,mRegister;
    EditText mEmail,mPassword;
    //private FirebaseAuth mAuth;
    SharedPreferences sharedpreferences;
    String n;
    public User loged_in_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_main);
        mRegister=(Button)findViewById(R.id.Register1);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AttemptRegistration();


            }
        });

        mSigin=(Button)findViewById(R.id.sign_in);
        mSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttempSignIn();
            }
        });



    }
    void AttemptRegistration(){
        mEmail=(EditText)findViewById(R.id.Email);
        mPassword=(EditText)findViewById(R.id.Password);
        boolean cancel = false;
        View focusView = null;
        final String Email=mEmail.getText().toString();
        final String Password=mPassword.getText().toString();
        mEmail.setError(null);
        mPassword.setError(null);

        if(Password.isEmpty()||!isPasswordValid(Password)){
            mPassword.setError("Password Field is Empty or invalid for Registration");
            cancel=true;
            focusView=mPassword;
        }

        if(Email.isEmpty()){
            mEmail.setError("Field Required");
            cancel=true;
            focusView=mEmail;
        }
        else if(!isEmailValid(Email)){
            mEmail.setError("Email Id is invalid");
            cancel=true;
            focusView=mEmail;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            Toast.makeText(Main.this,"A OK ",Toast.LENGTH_SHORT).show();
            final FirebaseAuth mAuth= FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        mEmail.setError("User Already exists");
                        View focusView=mEmail;
                        focusView.requestFocus();
                    }
                    else{
                        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.d("abc","till here");
                                   Intent i=new Intent(Main.this,SignUP.class);
                                   startActivity(i);
                                }
                            }
                        });
                    }
                }
            });


        }

    }
    void AttempSignIn()
    {

        mEmail=(EditText)findViewById(R.id.Email);
        mPassword=(EditText)findViewById(R.id.Password);
        final String Email=mEmail.getText().toString();
        final String Password=mPassword.getText().toString();
        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        boolean cancel=false;
        boolean success=false;
        if(Email.isEmpty()){
            cancel=true;
            Toast.makeText(Main.this,"Email id field cannot be empty",Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(Password.isEmpty()){
                cancel=true;
                Toast.makeText(Main.this,"Password Field cannot be Empty",Toast.LENGTH_SHORT).show();
            }
        }
        if(!cancel){
            mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {


                       FirebaseUser user=mAuth.getCurrentUser();
                        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
                        Query query=mDatabaseReference.child("User").orderByChild("U_id").equalTo(user.getUid());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot snapshot :
                                            dataSnapshot.getChildren()) {
                                        //String ni=snapshot.child("First_Name").getValue().toString();
                                        String mUserName=snapshot.child("First_Name").getValue().toString()+" "+snapshot.child("Last_Name").getValue().toString();
                                        String mAge=snapshot.child("Age").getValue().toString();
                                        String mGender=snapshot.child("Gender").getValue().toString();
                                        sharedpreferences = getApplicationContext().getSharedPreferences(USER_PREF, 0);
                                        sharedpreferences.edit().putString(AGE_KEY,mAge).apply();
                                        sharedpreferences.edit().putString(GENDER_KEY,mGender).apply();
                                        sharedpreferences.edit().putString(DISPLAY_NAME_KEY,mUserName).apply();



                                        //loged_in_user = new User(snapshot.child("U_id").getValue().toString(),snapshot.child("First_Name").getValue().toString(), snapshot.child("Last_Name").getValue().toString());
                                        Toast.makeText(Main.this,"Successfully Signed in as "+mUserName,Toast.LENGTH_SHORT).show();

                                       // Intent i =new Intent(Main.this,home.class);
                                        //i.putExtra("logged_in_user",loged_in_user);
                                        //startActivity(i);

                                    }
                                }
                                else
                                    Toast.makeText(Main.this,"lol",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                     //   Toast.makeText(Main.this,"Sucessfully Signed in as "+loged_in_user.First_Name,Toast.LENGTH_SHORT).show();
                        SharedPreferences prefs = getSharedPreferences(USER_PREF,MODE_PRIVATE);
                        String mDisplayName =prefs.getString(DISPLAY_NAME_KEY,null);
                        //Toast.makeText(Main.this,"Sucessfully Signed in as "+mDisplayName,Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent i =new Intent(Main.this,home.class);
                                startActivity(i);
                            }
                        }, 5000);

                    }
                }
            });

        }
    }
    boolean isEmailValid(String email){
        if(email.contains("@")){
            return true;
        }
        return false;
    }

    boolean isPasswordValid(String password){
        if(password.length()>=6){
            return true;
        }
        else
            return false;
    }
    /*void display(String g)
    {
        n=g;
    }*/

    /*public void readData(final FirebaseCallback firebaseCallback){
        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        Query query=mDatabaseReference.child("User").orderByChild("U_id").equalTo(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {
                       // String ni=snapshot.child("First_Name").getValue().toString();
                        //SharedPreferences.Editor editor = sharedpreferences.edit();
                        //editor.putString(n, ni);
                        //editor.commit();
                        loged_in_user = new User(snapshot.child("U_id").getValue().toString(),snapshot.child("First_Name").getValue().toString(), snapshot.child("Last_Name").getValue().toString());
                        //Toast.makeText(Main.this,"Successfully Signed in as "+loged_in_user.First_Name,Toast.LENGTH_SHORT).show();

                                       /* Intent i =new Intent(Main.this,home.class);
                                        i.putExtra("logged_in_user",loged_in_user);
                                        startActivity(i);
                       // firebaseCallback.onCallback(loged_in_user);

                    }

                }
                else
                    Toast.makeText(Main.this,"lol",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }




