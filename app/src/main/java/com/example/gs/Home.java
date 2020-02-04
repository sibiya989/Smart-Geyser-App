package com.example.gs;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null)
        {


            Intent intent = new Intent(Home.this,Control.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            //Toast.makeText(Home.this,"Successful logout",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;

        }
        else
        {
            Intent intent = new Intent(Home.this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            //Toast.makeText(Home.this,"Successful logout",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;

        }
    }
}
