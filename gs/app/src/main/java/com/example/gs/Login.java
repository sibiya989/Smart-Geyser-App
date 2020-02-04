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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private EditText email,password;
    private Button buttonLogin,buttonSugnUp;
    private FirebaseAuth firebaseAuth;
    private int counter =5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.txtLogEmail);
        password = (EditText)findViewById(R.id.txtLogPassword);

        buttonLogin = (Button)findViewById(R.id.btnlogLogin);
        buttonSugnUp = (Button)findViewById(R.id.btnLogSingUp);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
       // if(user!=null)
        //{
          //  finish();

            //startActivity(new Intent(Login.this,Control.class));

        //}


        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                validate(email.getText().toString(),password.getText().toString());
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("User Nam");
                myRef.setValue(email.getText().toString());
            }
        });


        buttonSugnUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });
    }

    private void validate(String nam,String pass)
    {

        firebaseAuth.signInWithEmailAndPassword(nam,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this,"Login Success",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(Login.this,Control.class));
                }
                else
                {

                    Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                    counter--;
                    if(counter == 0)
                    {
                        buttonLogin.setEnabled(false);

                    }

                }

            }
        });
    }
}
