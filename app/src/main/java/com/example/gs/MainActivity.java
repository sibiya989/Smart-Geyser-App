package com.example.gs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btnonsignup,btnsignin;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnonsignup = (Button) findViewById(R.id.btnSignUp);
        btnsignin = (Button) findViewById(R.id.btnSignIn);

        btnonsignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(new Intent(MainActivity.this,Registration.class));

            }
        });

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,Control.class));

            }
        });

    }
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public void onResume(){
        super.onResume();

        if(firebaseUser != null)
        {


            Intent intent = new Intent(MainActivity.this,Control.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            //Toast.makeText(Home.this,"Successful logout",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;

        }
        else
        {
            Intent intent = new Intent(MainActivity.this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            //Toast.makeText(Home.this,"Successful logout",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;

        }


    }
}
