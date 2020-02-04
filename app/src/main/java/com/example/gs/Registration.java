package com.example.gs;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private Button buttonSignup,buttonSignin;
    private EditText Username,Email,Password,ConfirmPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupViews();

        firebaseAuth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar)findViewById(R.id.RegprogressBar);
        progressBar.setVisibility(View.GONE);


        //createan account
        buttonSignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //progressbar

                if(Validate())
                {
                    final String email = Email.getText().toString().trim();
                    final String password = Password.getText().toString().trim();
                    final String name = Username.getText().toString().trim();
                    Query usernameQuerry = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Name").equalTo(name);
                    usernameQuerry.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0)
                            {
                                Toast.makeText(Registration.this, "User name taken choose a different one", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            String email = Email.getText().toString().trim();
                                            String name =Username.getText().toString();
                                            String userID = firebaseAuth.getCurrentUser().getUid();
                                            DatabaseReference currentUserDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                                            Map newPost = new HashMap();
                                            newPost.put("Name",name);
                                            newPost.put("Email",email);
                                            newPost.get("df");

                                            currentUserDB.setValue(newPost);

                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(Registration.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Registration.this,Login.class));
                                        }
                                        else
                                        {
                                            Toast.makeText(Registration.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });
        //login
        buttonSignin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Registration.this,Login.class));
            }
        });
    }

    private boolean Validate()
    {
        boolean result =false;
        String name =Username.getText().toString();
        String email =Email.getText().toString();
        String password =Password.getText().toString();
        String confirmpassword = ConfirmPassword.getText().toString();



        if(TextUtils.isEmpty(name))
        {
            Username.setError("Name cannot be empty");
            result = false;

        }
        else if(TextUtils.isEmpty(email))
        {
            Email.setError("Email cannot be empty");
            result = false;


        }
        else if(TextUtils.isEmpty(confirmpassword))
        {
            Email.setError("Confirm Password cannot be empty");
            result = false;


        }
        else if(TextUtils.isEmpty(password))
        {
            Email.setError("Password cannot be empty");
            result = false;


        }
        else if(TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmpassword))
        {
            Password.setError("Password and confirm Password do not match");
            result = false;


        }

        else{

            result=true;
        }


        return result;



    }

    private  void setupViews()
    {
        Username = (EditText) findViewById(R.id.txtRegUserName);
        Email = (EditText) findViewById(R.id.txtRegEmail);
        Password = (EditText) findViewById(R.id.txtRegPassword);
        ConfirmPassword = (EditText) findViewById(R.id.txtRegPasswordconfirm);

        buttonSignin = (Button)findViewById(R.id.btnRegSingIn);
        buttonSignup = (Button)findViewById(R.id.btnRegSignUp);

    }



}
