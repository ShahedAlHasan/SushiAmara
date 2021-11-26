package sushi.amara.com.customarapp.view;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sushi.amara.com.customarapp.Common.Common;
import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.Holidaypojo;

public class MainActivity extends NaviBase {


    private DatabaseReference rootRef;
    private DatabaseReference disRefGet;
    private DatabaseReference timeRef;
    private DatabaseReference timeRefB;
    private DatabaseReference holidayRef;
    private DatabaseReference holidayRefB;

    private List<Holidaypojo> holidaypojos = new ArrayList<Holidaypojo>();

    String formattedDate;

    private LoadingDialog dialog;

    TextView storeA,storeB,storeAmass,storeBmass;

    private static final int MY_PERMISSIONS_REQUEST = 1;


    private int mHour, mMinute;
    private int mHour2, mMinute2;



    private Date timenow;
    private Date dateCompareOne;
    private Date dateCompareOneB;
    private Date dateCompareTwo;
    private Date dateCompareTwoB;

    public static final String inputFormat = "HH:mm";
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

    String Astart,Bstart,Aend,Bend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new LoadingDialog(MainActivity.this,"Herzlich willkommen...");
        dialog.show();


        if (!checkPermissionGranted()) {
            askForPermission();

        }
        if (!checkInternetConnection()) {

            Toast.makeText(MainActivity.this,"please check internet connection",Toast.LENGTH_SHORT).show();
        }

/*
//        mtollbarText=(TextView) findViewById(R.id.toolbar_text_base);
//        mtollbarText.setText(R.string.home_heder);
        ///mtollbarText.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_home_black_24dp, 0, 0, 0);

        //data form database for check holiday or not///////////////////////////////////////////////////////*/

        Date c = Calendar.getInstance().getTime();
//        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);



        rootRef = FirebaseDatabase.getInstance().getReference();
        holidayRef = rootRef.child("Langenfeld").child("Holiday");
        holidayRefB = rootRef.child("Leichlingen").child("Holiday");
        timeRef = rootRef.child("Langenfeld").child("Time");
        timeRefB = rootRef.child("Leichlingen").child("Time");

        showDiscount();

        timeRef.child("start").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //  Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                Astart = dataSnapshot.getValue().toString();
                Common.dateStart = Astart;
//                dialog.dismiss();
                dateCompareOne = parseDate(Astart);

                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                timeRefB.child("start").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //  Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                        Bstart = dataSnapshot.getValue().toString();
                        Log.d("Time", "Start Time:" + Bstart);
                        Common.dateStartB = Bstart;
//                dialog.dismiss();
                        dateCompareOneB = parseDate(Bstart);

                        timeRef.child("end").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!(dataSnapshot.getValue().toString() == null)){
                                    //Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                    Aend = dataSnapshot.getValue().toString();
                                    Common.dateEnd = Aend;
//                    dialog.dismiss();
                                    dateCompareTwo = parseDate(Aend);

                                    timeRefB.child("end").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (!(dataSnapshot.getValue().toString() == null)){
                                                //Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                                Bend = dataSnapshot.getValue().toString();
                                                Common.dateEndB = Bend;
                                                dialog.dismiss();
                                                dateCompareTwoB = parseDate(Bend);

                                                MassegeGet();


                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
/*        timeRefB.child("start").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //  Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                Bstart = dataSnapshot.getValue().toString();
                Common.dateStartB = Bstart;
//                dialog.dismiss();
                dateCompareOneB = parseDate(Bstart);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/



/*        timeRef.child("end").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.getValue().toString() == null)){
                    //Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    Aend = dataSnapshot.getValue().toString();
                    Common.dateEnd = Aend;
//                    dialog.dismiss();
                    dateCompareTwo = parseDate(Aend);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

/*        timeRefB.child("end").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.getValue().toString() == null)){
                    //Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    Bend = dataSnapshot.getValue().toString();
                    Common.dateEndB = Bend;
                    dialog.dismiss();
                    dateCompareTwoB = parseDate(Bend);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        holidayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holidaypojos.clear();
                for (DataSnapshot donnerSnapshot : dataSnapshot.getChildren()) {
                    Holidaypojo confirmOrder = donnerSnapshot.child("details").getValue(Holidaypojo.class);
                    holidaypojos.add(confirmOrder);
                }

                for (int i = 0;i<holidaypojos.size();i++) {
                    if (holidaypojos.get(i).getId().equals(formattedDate)){
                        Common.holiday = holidaypojos.get(i).getHoli();
//                        dialog.dismiss();
                    }
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holidayRefB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holidaypojos.clear();
                for (DataSnapshot donnerSnapshot : dataSnapshot.getChildren()) {
                    Holidaypojo confirmOrder = donnerSnapshot.child("details").getValue(Holidaypojo.class);
                    holidaypojos.add(confirmOrder);
                }

                for (int i = 0;i<holidaypojos.size();i++) {
                    if (holidaypojos.get(i).getId().equals(formattedDate)){
                        Common.holidayB = holidaypojos.get(i).getHoli();
//                        dialog.dismiss();
                    }
                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /////////////////////////////////////////////////////////////////////////////////////////////

        storeA = findViewById(R.id.storeA);
        storeAmass = findViewById(R.id.store1Massege);
        storeB = findViewById(R.id.storeB);
        storeBmass = findViewById(R.id.store2Massege);

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        String datecmp = hour+":"+minute;
        timenow = parseDate(datecmp);



//        dateCompareOne = parseDate(Astart);
//        dateCompareOneB = parseDate(Bstart);
//        dateCompareTwo = parseDate(Aend);
//        dateCompareTwoB = parseDate(Bend);

        //Log.d("Shafi","akhonkar time: "+timenow+"......"+hour+" : "+minute);
        Log.d("ShafiS","Dokan kulbe: "+dateCompareOne+"......"+dateCompareOne);
        Log.d("ShafiE","Dokan bondho hobe: "+dateCompareTwo+"......"+dateCompareOneB);

        /*if (Common.holiday.equals("yes")){
            storeAmass.setText("Heute sind Ferien");
        }
        else {
            if (dateCompareOne.after(timenow)) {
                //Opening Soon
                storeAmass.setText("Geöffnet um" + Common.dateStart);
            } else if (dateCompareTwo.before(timenow)) {
                //Store Close
                storeAmass.setText("Store schließen");
            }
        }

        if (Common.holidayB.equals("yes")){
            storeBmass.setText("Heute sind Ferien");
        }
        else {
            if (dateCompareOneB.after(timenow)) {
                //Opening Soon
                storeBmass.setText("Geöffnet um" + Common.dateStartB);
            } else if (dateCompareTwoB.before(timenow)) {
                //Store Close
                storeBmass.setText("Store schließen");
            }
        }*/

        storeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInternetConnection()) {

                    Toast.makeText(MainActivity.this,"please check internet connection",Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(MainActivity.this,LocationActivity.class);
                    Common.store = "Langenfeld";
                    intent.putExtra("store","Langenfeld");
                    startActivity(intent);
                }

            }
        });
        storeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInternetConnection()) {

                    Toast.makeText(MainActivity.this,"please check internet connection",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                    Common.store = "Leichlingen";
                    intent.putExtra("store", "Leichlingen");
                    startActivity(intent);
                }
            }
        });

    }

    private void showDiscount() {

        disRefGet = rootRef.child("Discount");
        disRefGet.child("value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.getValue().toString() == null)){

                    Common.discount = Integer.parseInt(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void MassegeGet() {

        if (Common.holiday.equals("yes")){
            storeAmass.setText("Heute sind Ferien");
        }
        else {
            if (dateCompareOne.after(timenow)) {
                //Opening Soon
                storeAmass.setText("Geöffnet um " + Common.dateStart);
            } else if (dateCompareTwo.before(timenow)) {
                //Store Close
                storeAmass.setText("Store schließen");
            }
        }

        if (Common.holidayB.equals("yes")){
            storeBmass.setText("Heute sind Ferien");
        }
        else {
            if (dateCompareOneB.after(timenow)) {
                //Opening Soon
                storeBmass.setText("Geöffnet um" + Common.dateStartB);
            } else if (dateCompareTwoB.before(timenow)) {
                //Store Close
                storeBmass.setText("Store schließen");
            }
        }

    }


    private Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }


    //**************** Shafi -> Runtime Permission Check ****************//
    @Override
    protected void onResume() {
        super.onResume();
        if (!checkPermissionGranted()) {
            askForPermission();
        }
        if (!checkInternetConnection()) {

            Toast.makeText(this,"please check internet connection",Toast.LENGTH_SHORT).show();
        } else {


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED
                        /*&& grantResults[5] == PackageManager.PERMISSION_GRANTED
                        && grantResults[6] == PackageManager.PERMISSION_GRANTED
                        && grantResults[7] == PackageManager.PERMISSION_GRANTED*/
                ) {

                    return;

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private Boolean checkInternetConnection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        return connected;
    }

    private boolean checkPermissionGranted() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

       /* if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }*/

/*        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }*/
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void askForPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.CALL_PHONE,
                        /*Manifest.permission.CAMERA,
                      Manifest.permission.WRITE_EXTERNAL_STORAGE,
                      Manifest.permission.READ_EXTERNAL_STORAGE,*/
                      Manifest.permission.ACCESS_COARSE_LOCATION,
                      Manifest.permission.ACCESS_FINE_LOCATION,
                },
                MY_PERMISSIONS_REQUEST);
    }

    //************************Shafi -> Runtime Permission Check ******************//
}
