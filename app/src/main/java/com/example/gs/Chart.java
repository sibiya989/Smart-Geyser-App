package com.example.gs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Chart extends AppCompatActivity {

    public TextView power,price,indicator,oldPrice;
    public EditText edittext;
    private Button stats,Compareusage;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;




    PieChart pieChart;

    private  static  String TAG ="Chart";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);




        power = (TextView) findViewById(R.id.power);
        price = (TextView) findViewById(R.id.price);
        oldPrice = (TextView) findViewById(R.id.price2);
        indicator = (TextView) findViewById(R.id.indicator);
        edittext= (EditText) findViewById(R.id.Birthday);
        Compareusage= (Button) findViewById(R.id.CompareUsage);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("PRICE");
        DatabaseReference myRef2 = database.getReference("POWER");
        DatabaseReference myRef3 = database.getReference("Temp");



        Date date = new Date();
        Date newDate = new Date(date.getTime());
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        final String stringdate = dt.format(newDate);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("STATISTICS");
        databaseReference.child(stringdate).child("POWER").setValue(1);
        databaseReference.child(stringdate).child("PRICE").setValue(1);
        databaseReference.child(stringdate).child("DATE").setValue(stringdate);


        final DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("STATISTICS");
        final DatabaseReference zone1Ref = zonesRef.child(stringdate);
        DatabaseReference zone1NameRef = zone1Ref.child("POWER");
        final DatabaseReference zone1NameRef2 = zone1Ref.child("PRICE");
        final DatabaseReference zone1NameRef3 = zone1Ref.child("DATE");

        //final DatabaseReference mostafa = zonesRef.child("STATISTICS").child("2019-12-11");



        final Calendar myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                edittext.setText(sdf.format(myCalendar.getTime()));



            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(Chart.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Compareusage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newStats =edittext.getText().toString().trim();


                Query zonesQuery = zonesRef.orderByChild("DATE").equalTo(newStats);
                zonesQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                            //Log.i(TAG, zoneSnapshot.child("ZNAME").getValue(String.class));
                            long value = zoneSnapshot.child("PRICE").getValue(long.class);
                            oldPrice.setText("Previous PRICE R " +Long.toString(value));
                            //String str = Long.toString(l);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "onCancelled", databaseError.toException());
                    }
                });

            }
        });


        zone1NameRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        float value = dataSnapshot.getValue(float.class);
                        price.setText("Price R "+String.valueOf(value));

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value


                    }
                });

        zone1NameRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //String value = dataSnapshot.getRef().getParent().getKey();


                        float value = dataSnapshot.getValue(float.class);
                        power.setText("Power usage "+ String.valueOf(value)+" kW/h");

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value


                    }
                });


        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getRef().getParent().getKey();


                float value = dataSnapshot.getValue(float.class);
                indicator.setText(String.valueOf(value)+" Degrees");

                if(value <=30)
                {
                    indicator.setBackgroundColor(Color.GREEN);
                }
                else if(value >=30 && value <=60)
                {
                    indicator.setBackgroundColor(Color.YELLOW);
                }
                else
                {
                    indicator.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value


            }
        });




    }




}



