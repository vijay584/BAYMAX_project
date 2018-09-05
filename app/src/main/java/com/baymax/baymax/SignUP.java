package com.baymax.baymax;

import android.content.Intent;
import android.media.SoundPool;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class SignUP extends AppCompatActivity {

    Button Register;
    private FirebaseAuth mAuth ;
    EditText mFirstName;
    EditText mLastName;
    EditText mAge;
    TextView tv;
    RadioGroup mRadioGroup;
    RadioButton mRadioButton;
    public DatabaseReference mDataBaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        Register =(Button)findViewById(R.id.register2);

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        Toast.makeText(SignUP.this,"User id is "+user.getUid(),Toast.LENGTH_LONG).show();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register_User_details();
            }
        });

    }
    public void Register_User_details(){

        mFirstName=(EditText)findViewById(R.id.Fname);
        mLastName=(EditText)findViewById(R.id.Lname);
        mAge=(EditText)findViewById(R.id.age);
        mRadioGroup=(RadioGroup)findViewById(R.id.genderGroup);
        tv=(TextView) findViewById(R.id.gender);
        final String Gender;

        int selectedId=mRadioGroup.getCheckedRadioButtonId();
        if(selectedId==R.id.male){

            mRadioButton=(RadioButton)findViewById(R.id.male);
            Gender=mRadioButton.getText().toString();
        }
        else if(selectedId==R.id.female){

            mRadioButton=(RadioButton)findViewById(R.id.female);
            Gender=mRadioButton.getText().toString();


        }
        else if(selectedId==R.id.other){

            mRadioButton=(RadioButton)findViewById(R.id.other);
            Gender=mRadioButton.getText().toString();
        }
        else{

            Gender=null;

        }


        boolean cancel = false;
        View focusView = null;
        final String FirstName=mFirstName.getText().toString();
        final String LastName=mLastName.getText().toString();
        final String Age=mAge.getText().toString();

        if(FirstName.isEmpty()){
            mFirstName.setError("Cannot be Empty");
            cancel=true;
            focusView=mFirstName;
        }
        if(LastName.isEmpty()){
            mLastName.setError("Cannot be Empty");
            cancel=true;
            focusView=mLastName;
        }
        if(Age.isEmpty())
        {
            mAge.setError("Cannot Be Empty");
            cancel=true;
            focusView=mAge;
        }
        if(Gender==null){

            tv.setError("Pick an Option");
            cancel=true;
            focusView=tv;

        }
        if(cancel)
            focusView.requestFocus();

        else {


            FirebaseUser user = mAuth.getInstance().getCurrentUser();
            User registering_user = new User(user.getUid(),FirstName, LastName,Age,Gender);
            mDataBaseReference = FirebaseDatabase.getInstance().getReference();
            mDataBaseReference.child("User").push().setValue(registering_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUP.this, "New User Details added congrats!!!", Toast.LENGTH_SHORT).show();
                        Logout();


                    } else
                        Toast.makeText(SignUP.this, "Something goofed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    void Logout()
    {
        try {
            mAuth.getInstance().signOut();
            Intent i=new Intent(SignUP.this,Main.class);
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void Registration() {
       Umail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        Uname = (EditText) findViewById(R.id.name);
        Somehing = (EditText) findViewById(R.id.anything);
        mAuth=FirebaseAuth.getInstance();
        mDataBaseReference= FirebaseDatabase.getInstance().getReference();

        final String mMail = Umail.getText().toString();
        final String mPass = pass.getText().toString();
        final String mName=Uname.getText().toString();
        final String mSomething=Somehing.getText().toString();
        mAuth.createUserWithEmailAndPassword(mMail, mPass).addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplication(), "failed", Toast.LENGTH_SHORT).show();
                } else {
                            mAuth.signInWithEmailAndPassword(mMail,mPass).addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful())
                                    {

                                    }
                                    else
                                    {
                                        FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                        User mUser=new User(mName,mSomething);
                                        mDataBaseReference.child("User").child(user.getUid()).setValue(mUser);
                                        Toast.makeText(getApplication(), "user " +user.getUid()+ " created", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });




                        }



                    };


                });
            }*/


     }






