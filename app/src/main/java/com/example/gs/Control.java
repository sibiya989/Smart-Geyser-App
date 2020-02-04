package com.example.gs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Control extends AppCompatActivity {
    private ToggleButton on;
    private ToggleButton emergency;
    private ToggleButton alarm;
    private TextView feedback,feedback2;

    private EditText temp;

    private FirebaseAuth firebaseAuth;

    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        firebaseAuth = FirebaseAuth.getInstance();
        on = (ToggleButton) findViewById(R.id.on);
        emergency = (ToggleButton) findViewById(R.id.emergency);
        alarm = (ToggleButton) findViewById(R.id.alarm);

        feedback =(TextView) findViewById(R.id.feedback);
        feedback2 =(TextView) findViewById(R.id.feedback2);

        temp = (EditText) findViewById(R.id.setTemp);
        temp.setText("50");
        timePicker = (TimePicker) findViewById(R.id.timepicker);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ON");
        DatabaseReference myRef2 = database.getReference("AUTO_ON");








        alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)

                {
                    String Temperature =temp.getText().toString().trim();
                    int i = Integer.parseInt(Temperature);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myTemp = database.getReference("USERTEMP");
                    myTemp.setValue(i);

                    Calendar calendar = Calendar.getInstance();
                    if(Build.VERSION.SDK_INT >=23)
                    {
                        calendar.set( calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),

                                timePicker.getHour(),
                                timePicker.getMinute()       ,
                                0);



                    }else
                    {
                        calendar.set( calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute(),
                                0);


                    }

                    setAlarm(calendar.getTimeInMillis());


                } else {
                    // The toggle is disabled

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("AUTO_ON");
                    myRef.setValue(0);
                    DatabaseReference myRef1 = database.getReference("ON");
                    myRef1.setValue(0);
                    DatabaseReference myTemp = database.getReference("USERTEMP");
                    myTemp.setValue(0);
                }
            }
        });



        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getRef().getParent().getKey();


                int value = dataSnapshot.getValue(int.class);
                if(value == 1)
                {
                    feedback2.setText("Geyser AUTO_ON");
                    alarm.setChecked(true);

                }
                else
                {
                    feedback2.setText("Geyser AUTO_OFF");
                    alarm.setChecked(false);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value


            }
        });



        on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)

                {
                    // Write a message to the database

                    String Temperature =temp.getText().toString().trim();
                    int i = Integer.parseInt(Temperature);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("ON");
                    myRef.setValue(1);
                    DatabaseReference myTemp = database.getReference("USERTEMP");
                    myTemp.setValue(i);

                } else {
                    // The toggle is disabled

                    // Write a message to the database

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("ON");
                    myRef.setValue(0);
                    DatabaseReference myTemp = database.getReference("USERTEMP");
                    myTemp.setValue(0);

                }
            }
        });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getRef().getParent().getKey();


                int value = dataSnapshot.getValue(int.class);
                if(value == 1)
                {
                    feedback.setText("Geyser ON");
                    on.setChecked(true);
                }
                else
                {
                    feedback.setText("Geyser OFF");
                    on.setChecked(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value


            }
        });




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getRef().getParent().getKey();


                int value = dataSnapshot.getValue(int.class);
                if(value == 1)
                {
                    feedback.setText("Geyser ON");
                    on.setChecked(true);
                }
                else
                {
                    feedback.setText("Geyser OFF");
                    on.setChecked(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value


            }
        });


        emergency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)

                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Emergency");

                    myRef.setValue(1);

                } else {
                    // The toggle is disabled

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Emergency");

                    myRef.setValue(0);
                }
            }
        });


    }

    private void setAlarm(long timeInMillis)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Control.this,MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC,timeInMillis,alarmManager.INTERVAL_DAY,pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logoutMenu:
            {
                firebaseAuth.signOut();

                Intent intent = new Intent(Control.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(this,"Successful logout",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;


            }
            case R.id.profileMenu:
            {
               startActivity(new Intent(Control.this, Profile.class));
                Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();
                break;

            }

            case R.id.statistics:
            {

                startActivity(new Intent(Control.this, Chart.class));
                break;


            }



        }
        return super.onOptionsItemSelected(item);
    }
}
