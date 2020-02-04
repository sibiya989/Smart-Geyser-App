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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private EditText email,password;
    private Button buttonLogin,buttonSugnUp,buttonForgotPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private int counter =5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar)findViewById(R.id.LogprogressBar);
        progressBar.setVisibility(View.GONE);


        email = (EditText)findViewById(R.id.txtLogEmail);
        password = (EditText)findViewById(R.id.txtLogPassword);

        buttonLogin = (Button)findViewById(R.id.btnlogLogin);
        buttonSugnUp = (Button)findViewById(R.id.btnLogSingUp);
        buttonForgotPassword =(Button) findViewById(R.id.btnLogForgotpasseord);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (Validate2())
                {
                    progressBar.setVisibility(View.VISIBLE);

                    validate(email.getText().toString(),password.getText().toString());

                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }

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

        buttonForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });




    }

    private boolean Validate2()
    {
        boolean result =false;
        String Email =email.getText().toString();
        String Password =password.getText().toString();

        if(TextUtils.isEmpty(Email))
        {
            email.setError("Name cannot be empty");
            result = false;

        }
        else if(TextUtils.isEmpty(Password))
        {
            password.setError("Password cannot be empty");
            result = false;


        }

        else{

            result=true;
        }


        return result;
    }

    private void validate(String nam,String pass)
    {

        firebaseAuth.signInWithEmailAndPassword(nam,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this,"Login Success",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login.this,Control.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //startActivity(new Intent(Login.this,Control.class));
                }
                else
                {

                    Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
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
