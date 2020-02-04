package com.example.gs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.formatter.IFillFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private Button researt;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email=(EditText) findViewById(R.id.txtForgotEmail);
        researt=(Button) findViewById(R.id.btnForgotPassword);


        firebaseAuth = FirebaseAuth.getInstance();


            researt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Validate())
                    {

                        firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Password successfully sent to your email", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
            });

    }

    private boolean Validate()
    {
        boolean result =false;
        String Email =email.getText().toString();



        if(TextUtils.isEmpty(Email))
        {
            email.setError("Email cannot be empty");
            result = false;

        }


        else{

            result=true;
        }


        return result;



    }

}

