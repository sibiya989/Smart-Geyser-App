package com.example.gs;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private Button buttonSignup,buttonSignin;
    private EditText Username,Email,Password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupViews();
        firebaseAuth = FirebaseAuth.getInstance();
        //createan account
        buttonSignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Validate())
                {
                    String email = Email.getText().toString().trim();
                    String password = Password.getText().toString().trim();
                    String name = Username.getText().toString().trim();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Name");
                    myRef.setValue(name);

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Registration.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this,Login.class));
                            }
                            else
                            {

                                Toast.makeText(Registration.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


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
        if(name.isEmpty()||email.isEmpty()||password.isEmpty())
        {
            Toast.makeText(this,"Please make sure all fieds are not empty!!!",Toast.LENGTH_SHORT).show();

        }
        else
        {
            result = true;
        }

        return result;
    }

    private  void setupViews()
    {
        Username = (EditText) findViewById(R.id.txtRegUserName);
        Email = (EditText) findViewById(R.id.txtRegEmail);
        Password = (EditText) findViewById(R.id.txtRegPassword);

        buttonSignin = (Button)findViewById(R.id.btnRegSingIn);
        buttonSignup = (Button)findViewById(R.id.btnRegSignUp);

    }



}
