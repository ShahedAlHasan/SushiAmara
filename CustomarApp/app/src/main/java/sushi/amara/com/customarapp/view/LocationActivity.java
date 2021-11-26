package sushi.amara.com.customarapp.view;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import sushi.amara.com.customarapp.Common.Common;
import sushi.amara.com.customarapp.R;

public class LocationActivity extends NaviBase implements OnMapReadyCallback {



/*    private DatabaseReference rootRef;
    private DatabaseReference timeRef;
    private DatabaseReference timeRefB;
    private DatabaseReference holidayRef;
    private DatabaseReference holidayRefB;

    private List<Holidaypojo> holidaypojos = new ArrayList<Holidaypojo>();

    String formattedDate;*/

    LocationManager locationManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    TextView mCurrent_location;

    LinearLayout linearLayout;

    TextView StoreTV;

    String store;

    Dialog mydialog;

    private boolean isGPS = false;

    String myCity = "";
    String road = "";
    String house = "";
    String pc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        store = getIntent().getStringExtra("store");

//        mtollbarText=(TextView) findViewById(R.id.toolbar_text_base);
//        mtollbarText.setText(R.string.place_heder);
//        mtollbarText.setCompoundDrawablesWithIntrinsicBounds( R.drawable.placeholder, 0, 0, 0);

        mCurrent_location = findViewById(R.id.mCurrent_location);
        StoreTV = findViewById(R.id.storeTv);

        StoreTV.setText(store);
//        if (store.equals("Langenfeld")){
//            StoreTV.setText(R.string.store_a);
//        }else {
//            StoreTV.setText(R.string.store_b);
//        }

        mydialog = new Dialog(this);


        //data form database for check holiday or not///////////////////////////////////////////////////////

        /*Date c = Calendar.getInstance().getTime();
//        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);



        rootRef = FirebaseDatabase.getInstance().getReference();
        holidayRef = rootRef.child("Langenfeld").child("Holiday");
        holidayRefB = rootRef.child("Leichlingen").child("Holiday");
        timeRef = rootRef.child("Langenfeld").child("Time");
        timeRefB = rootRef.child("Leichlingen").child("Time");


        timeRef.child("start").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //  Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                Common.dateStart = dataSnapshot.getValue().toString();
                //loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        timeRefB.child("start").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //  Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                Common.dateStartB = dataSnapshot.getValue().toString();
                //loadingDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        timeRef.child("end").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.getValue().toString() == null)){
                    //Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    Common.dateEnd = dataSnapshot.getValue().toString();
                    // loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        timeRefB.child("end").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.getValue().toString() == null)){
                    //Toast.makeText(FinalCart.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    Common.dateEndB = dataSnapshot.getValue().toString();
                    //loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                        //loadingDialog.dismiss();
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
                        //loadingDialog.dismiss();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/

        mCurrent_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDilog();
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////


        linearLayout = findViewById(R.id.liniae);

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.d("mylog", "Not granted");
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    } else
                        requestLocation();
                } else
                    requestLocation();
            }
        });





        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location mCurrentLocation = locationResult.getLastLocation();
                LatLng myCoordinates = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                String cityName = getCityName(myCoordinates);
                //Toast.makeText(LocationActivity.this, cityName, Toast.LENGTH_SHORT).show();

            }
        };

        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("mylog", "Getting Location Permission");
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("mylog", "Not granted");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else
                requestLocation();
        } else
            requestLocation();

    }

    private void popUpDilog() {

        mydialog.setContentView(R.layout.castom_appointment);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();
        Button confirm = mydialog.findViewById(R.id.confirmBooking);
        TextView txtclose =(TextView) mydialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        final EditText Ethose = mydialog.findViewById(R.id.house);
        final EditText Etroad = mydialog.findViewById(R.id.road);
        final EditText poc = mydialog.findViewById(R.id.poscpde);
        final EditText city = mydialog.findViewById(R.id.city);


        if (myCity != null){
            city.setText(myCity);
        }
        if (road != null){
            Etroad.setText(road);
        }
        if (house != null){
            Ethose.setText(house);
        }
        if (pc != null){
            poc.setText(pc);
        }


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Road = Etroad.getText().toString().trim();
                if (Road.isEmpty()){
                    Etroad.setError("Street is missing");
                    Etroad.requestFocus();
                    return;
                }

                String houseg = Ethose.getText().toString().trim();
                if (houseg.isEmpty()){
                    Ethose.setError("House Number is missing!");
                    Ethose.requestFocus();
                    return;
                }
                String Pos = poc.getText().toString().trim();
                if (Pos.isEmpty()){
                    poc.setError("Postal code is missing!");
                    poc.requestFocus();
                    return;
                }
                String City = city.getText().toString().trim();
                if (City.isEmpty()){
                    city.setError("City is missing!");
                    city.requestFocus();
                    return;
                }else {
                    final String fullAddress = "Rd no "+Road+",H-"+houseg+","+Pos+","+City;
                    Toast.makeText(LocationActivity.this, fullAddress, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LocationActivity.this, FoodActivity.class);
                    intent.putExtra("store", store);
                    Common.system = "Delivery";
                    Common.store = store;
                    intent.putExtra("get", "Delivery");
                    Common.location = fullAddress;
                    intent.putExtra("address", fullAddress);
                    startActivity(intent);
                    finish();
                    mydialog.dismiss();
                }
            }
        });






    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private void requestLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        Log.d("mylog", "In Requesting Location");
        if (location != null && (System.currentTimeMillis() - location.getTime()) <= 1000 * 2) {
            LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
            String cityName = getCityName(myCoordinates);
            //Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setNumUpdates(1);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            Log.d("mylog", "Last Location too old getting new Location!");
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.requestLocationUpdates(locationRequest,
                    mLocationCallback, Looper.myLooper());
        }
    }
    private String getCityName(LatLng myCoordinates) {

        Double lat = 0.0;
        Double lon = 0.0;
        String myLocation = "";
        Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(myCoordinates.latitude, myCoordinates.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            myCity = addresses.get(0).getLocality();
            road = addresses.get(0).getThoroughfare();
            house = addresses.get(0).getFeatureName();
            pc = addresses.get(0).getPostalCode();
            lat = addresses.get(0).getLatitude();
            lon = addresses.get(0).getLongitude();
            Log.d("mylog", "Complete Address: " + addresses.toString());
            Log.d("mylog", "Address: " + address);

            mCurrent_location.setText(road+","+"H-"+house+","+pc+","+myCity);

            Common.myLocation = lat+","+lon;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCity;
    }


    public void delivery(View view) {

        if (mCurrent_location.getText().toString().isEmpty()){
            popUpDilog();
        }
        else {
            Intent intent = new Intent(LocationActivity.this, FoodActivity.class);
            intent.putExtra("store", store);
            Common.system = "Delivery";
            intent.putExtra("get", Common.system);
            intent.putExtra("address", mCurrent_location.getText().toString());
            Common.location = mCurrent_location.getText().toString();
            startActivity(intent);
            finish();
        }
    }

    public void pickup(View view) {
        
        Intent intent = new Intent(LocationActivity.this, FoodActivity.class);
        intent.putExtra("store", store);
        Common.store = store;
        Common.system = "PickUp";
        intent.putExtra("get", Common.system);
        intent.putExtra("address", "unzutreffend");
        Common.location = "unzutreffend";
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }
}
