package sushi.amara.com.customarapp.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sushi.amara.com.customarapp.Common.Common;
import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.Remote.IGoogleAPI;
import sushi.amara.com.customarapp.pojo.ConfirmOrderMain;
import sushi.amara.com.customarapp.pojo.OrderChart;
import sushi.amara.com.customarapp.test.TestAdapter;

public class FinalCart extends NaviBase implements TestAdapter.OrderListener{

    RecyclerView test;
    private TestAdapter adapterT;

    private int mHour, mMinute;
    private int mHour2, mMinute2;

    private Date timenow;
    private Date dateCompareOne;
    private Date dateCompareTwo;

    public static final String inputFormat = "HH:mm";
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);




    private int mYear, mMonth, mDay;
    private String holidate,EndTime,StratTime;


    private  int m2,m3;

    TextView discountTxt, Location,price,finalPrice,discountValue,sys;



    private ConfirmOrderMain confirmOrder;

    private List<OrderChart> orderCharts = new ArrayList<OrderChart>();

    double totalPrice,disprice;

    Dialog mydialog;

    String includeVatPrice,TotalPrice,OrderTime,DelivaryTime,storeDistance,distancetime;


    private String holidayCheck = "no";

    String dateStrt,dateEnd,holiday;

    CardView cardView;

    TextView CartText,DistancePrice;

    String location,loca,store,System;

    IGoogleAPI mService;

    LinearLayout distanLineR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_cart);

        confirmOrder =(ConfirmOrderMain) getIntent().getSerializableExtra("curt");
        orderCharts = Common.orderCharts;
        loca = getIntent().getStringExtra("address");
        store = getIntent().getStringExtra("store");
        System = getIntent().getStringExtra("get");

        test = findViewById(R.id.foodRecyTest);
//        Time = findViewById(R.id.time);

        Location = findViewById(R.id.location_show);
        price = findViewById(R.id.showPrice);
        finalPrice = findViewById(R.id.showPriceF);
        sys = findViewById(R.id.syst);
        DistancePrice = findViewById(R.id.distancePrice);
        discountTxt = findViewById(R.id.disText);
        discountValue = findViewById(R.id.DiscountPriceF);


        discountTxt.setText("Rabatt("+Common.discount+"%)");

        cardView = findViewById(R.id.curt_master2F);
        CartText = findViewById(R.id.cart_text);

        distanLineR = findViewById(R.id.distanLiner);

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        String datecmp = hour+":"+minute;
        timenow = parseDate(datecmp);

        totalPrice = 0;
        for (OrderChart c: orderCharts){
            double price = Double.parseDouble(c.getProductPrice());
            totalPrice += price;
        }

        mService = Common.getGoogleService();

            if (store.equals("Langenfeld")) {
                dateStrt = Common.dateStart;
                dateEnd = Common.dateEnd;
                holiday = Common.holiday;
                dateCompareOne = parseDate(dateStrt);
                dateCompareTwo = parseDate(dateEnd);
                if (System.equals("PickUp")){
                    picup();
                }else {
                    getPrice(Common.myLocation, Common.LangenfeldLocation);
                }
            }
            if (store.equals("Leichlingen")) {

                dateStrt = Common.dateStartB;
                dateEnd = Common.dateEndB;
                holiday = Common.holidayB;
                dateCompareOne = parseDate(dateStrt);
                dateCompareTwo = parseDate(dateEnd);
                if (System.equals("PickUp")){
                    picup();
                }else {
                    getPrice(Common.myLocation, Common.LeichlingenLocation);
                }


            }


        mydialog = new Dialog(this);




        Log.d("Shafi","akhonkar time: "+timenow+"......"+hour+" : "+minute);
        Log.d("ShafiS","Dokan kulbe: "+dateCompareOne+"......"+dateStrt);
        Log.d("ShafiE","Dokan bondho hobe: "+dateCompareTwo+"......"+dateEnd);


        if (store.equals("Langenfeld")){
            Location.setText(R.string.store_a_address);
        }else {
            Location.setText(R.string.store_b_address);
        }



        adapterT = new TestAdapter(FinalCart.this, orderCharts);
        LinearLayoutManager llm = new LinearLayoutManager(FinalCart.this);
        llm.setOrientation(RecyclerView.VERTICAL);
        test.setLayoutManager(llm);
        test.setAdapter(adapterT);


    }

    private void picup() {
        disprice = 0.0;
        sys.setText(R.string.pickup);
        distanLineR.setVisibility(View.GONE);


        price.setText("€ "+String.format("%.2f", totalPrice));
        DistancePrice.setText("€ "+ String.valueOf(disprice));

        double discount = (totalPrice*Common.discount)/100;
        discountValue.setText("€ "+String.format("%.2f", discount));

        double vatpri = (totalPrice-discount)+disprice;
        includeVatPrice = String.valueOf((totalPrice-discount)+disprice);

        finalPrice.setText("€ "+String.format("%.2f", vatpri));
        if (holiday.equals("yes")){
            CartText.setText("Heute sind Ferien");
            return;
        }
        else{
            if (dateCompareOne.after(timenow)) {
                //Opening Soon
                CartText.setText("Öffnet bald");
            } else if (dateCompareTwo.before(timenow)) {
                //Store Close
                CartText.setText("Store schließen");
            } else if (dateCompareOne.before(timenow) && dateCompareTwo.after(timenow)) {
                //CHECKOUT
                CartText.setText("Zur Kasse");
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(totalPrice>0) {

                            ConfirmPArchas();
                        }else{

                            //add food first
                            Toast.makeText(FinalCart.this, "Zuerst das Essen hinzufügen...!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }


    private void getPrice(String myLocation, String storeLocation) {

        price.setText(new DecimalFormat("##.##").format(totalPrice));

        String requestUrl = null;
        try {

            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&" +
                    "transit_routing_preference=less_driving&" +
                    "origin=" + storeLocation + "&" +
                    "destination=" + myLocation + "&" +
                    "key=" + getResources().getString(R.string.google_destination_api);

            Log.d("Shafi ", requestUrl); //print i log,,see the url
            mService.getPath(requestUrl)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                JSONArray routes = jsonObject.getJSONArray("routes");

                                JSONObject object = routes.getJSONObject(0);
                                JSONArray legs = object.getJSONArray("legs");

                                JSONObject legObject = legs.getJSONObject(0);

                                //GEt distance

                                JSONObject distance = legObject.getJSONObject("distance");
                                String distance_text = distance.getString("text");

                                storeDistance = distance_text;


                                //remove all the text...not digite
                                Double distance_value = Double.parseDouble(distance_text.replaceAll("[^0-9\\\\.]+",""));

                                    sys.setText(R.string.delivery);
                                    if (distance_value <= 4.0) {
                                        disprice = 0.0;
                                    } else if (distance_value > 4.0 && distance_value <= 6.0) {
                                        disprice = 2.0;
                                    } else if (distance_value > 6.0 && distance_value <= 8.0) {
                                        disprice = 3.0;
                                    } else if (distance_value > 8.0 && distance_value <= 15.0) {
                                        disprice = 5.0;
                                    } else {
                                        disprice = 100.0;
                                    }

                                JSONObject time = legObject.getJSONObject("duration");
                                String time_text = time.getString("text");
                                distancetime = time_text;
//                                Integer time_value = Integer.parseInt(time_text.replaceAll("\\D+",""));

                                String final_caculate = String.format("%s ",distance_value);

                                Log.d("Shafi",""+final_caculate);
                                Log.d("Shafi",""+time_text);
                                Log.d("Shafi",""+distance_text);

                                //Toast.makeText(FinalCart.this, "Distance : "+final_caculate+" Km", Toast.LENGTH_SHORT).show();
                                distanLineR.setVisibility(View.VISIBLE);

                                price.setText("€ "+String.format("%.2f", totalPrice));
                                DistancePrice.setText("€ "+ String.valueOf(disprice));

                                double discount = (totalPrice*Common.discount)/100;
                                discountValue.setText("€ "+String.format("%.2f", discount));

                                double vatpri = (totalPrice-discount)+disprice;
                                includeVatPrice = String.valueOf((totalPrice-discount)+disprice);

                                finalPrice.setText("€ "+String.format("%.2f", vatpri));





                                if (holiday.equals("yes")){
                                    CartText.setText("Heute sind Ferien");
                                    return;
                                }
                                if (disprice == 100.0){
                                    CartText.setText("Zu viel weg");
                                    return;
                                }
                                else{
                                    if (dateCompareOne.after(timenow)) {
                                        //Opening Soon
                                        CartText.setText("Öffnet bald");
                                    } else if (dateCompareTwo.before(timenow)) {
                                        //Store Close
                                        CartText.setText("Store schließen");
                                    } else if (dateCompareOne.before(timenow) && dateCompareTwo.after(timenow)) {
                                        //CHECKOUT
                                        CartText.setText("Zur Kasse");
                                        cardView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                if(totalPrice>0) {

                                                    ConfirmPArchas();
                                                }else{

                                                    //add food first
                                                    Toast.makeText(FinalCart.this, "Zuerst das Essen hinzufügen...!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }


                            }catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("ERROR",t.getMessage());
                        }
                    });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }



    }

    private void ConfirmPArchas() {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


        if (DelivaryTime==null){

            m2 = mMinute+30 ;

            if(m2>=60){
                m3 = m2-60;
                ++mHour;
            }else{
                m3 = m2;
            }

            if (mHour>11) {
                if (mHour==12){

                    DelivaryTime = mHour + ":" + m3+" PM";

                }else {
                    int cou = mHour-12;
                    DelivaryTime = cou + ":" + m3+" PM";
                }


            }else {
                DelivaryTime = mHour + ":" + m3+" AM";
            }
        }


        if (storeDistance == null){
            storeDistance = "0.0 km";
        }
        if (distancetime == null){
            distancetime = "0.0 min";
        }

        ConfirmOrderMain con = new ConfirmOrderMain("null", orderCharts ,loca,System,String.valueOf(totalPrice),includeVatPrice,store,DelivaryTime,"null","null","null","null","null","null",storeDistance,distancetime);
        //Set value to database

                    Intent intent = new Intent(FinalCart.this,PickUpInfrormation.class);
                    intent.putExtra("curt",con);
                    intent.putExtra("store", store);
                    intent.putExtra("get", System);
                    intent.putExtra("address", loca);
                    startActivity(intent);
                    finish();


    }

    @Override
    public void onOrderDetails(OrderChart orderChart) {

        orderCharts.remove(orderChart);
        totalPrice = 0;
        for (OrderChart c: orderCharts){
            double price = Double.parseDouble(c.getProductPrice());
            totalPrice += price;
        }
        price.setText("€ "+String.format("%.2f", totalPrice));
        DistancePrice.setText("€ "+ String.valueOf(disprice));

        double discount = (totalPrice*Common.discount)/100;
        discountValue.setText("€ "+String.format("%.2f", discount));

        double vatpri = (totalPrice-discount)+disprice;
        includeVatPrice = String.valueOf((totalPrice-discount)+disprice);

        finalPrice.setText("€ "+String.format("%.2f", vatpri));
    }

    @Override
    public void onPlus() {
        totalPrice = 0;
        for (OrderChart c: orderCharts){
            double price = Double.parseDouble(c.getProductPrice());
            totalPrice += price;
        }
        price.setText("€ "+String.format("%.2f", totalPrice));
        DistancePrice.setText("€ "+ String.valueOf(disprice));

        double discount = (totalPrice*Common.discount)/100;
        discountValue.setText("€ "+String.format("%.2f", discount));

        double vatpri = (totalPrice-discount)+disprice;
        includeVatPrice = String.valueOf((totalPrice-discount)+disprice);

        finalPrice.setText("€ "+String.format("%.2f", vatpri));
    }

    @Override
    public void onMinus() {
        totalPrice = 0;
        for (OrderChart c: orderCharts){
            double price = Double.parseDouble(c.getProductPrice());
            totalPrice += price;
        }
        price.setText("€ "+String.format("%.2f", totalPrice));
        DistancePrice.setText("€ "+ String.valueOf(disprice));

        double discount = (totalPrice*Common.discount)/100;
        discountValue.setText("€ "+String.format("%.2f", discount));

        double vatpri = (totalPrice-discount)+disprice;
        includeVatPrice = String.valueOf((totalPrice-discount)+disprice);

        finalPrice.setText("€ "+String.format("%.2f", vatpri));
    }

    @Override
    public void Note(final OrderChart orderChart) {

    }

    @Override
    public void onBackPressed() {
        if(totalPrice>0) {
/*        new AlertDialog.Builder(this)
                    .setMessage("Wenn Du den Store Verlässt geht Dein Warenkorb verloren. Willst Du wirklich zurück zur Storeauswahl?")
                    .setCancelable(false)
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FinalCart.this.finish();
                        }
                    })
                    .setNegativeButton("Nein", null)
                    .show();*/
//            super.onBackPressed();

            Common.orderCharts = orderCharts ;
            Intent intent = new Intent(FinalCart.this,FoodActivity.class);
            intent.putExtra("store", store);
            intent.putExtra("get",System);
            intent.putExtra("address", loca);
            startActivity(intent);
            finish();

        }else {
            Intent intent = new Intent(FinalCart.this,FoodActivity.class);
            Common.orderCharts.clear();
            intent.putExtra("store", store);
            intent.putExtra("get",System);
            intent.putExtra("address", loca);
            startActivity(intent);
            finish();
        }

    }

    private Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}


/*        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(FinalCart.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay>11) {
                                    if (hourOfDay==12){
                                        Time.setText(hourOfDay + ":" + minute+" PM");
                                        DelivaryTime = Time.getText().toString();
                                    }else {
                                        int cou = hourOfDay-12;
                                        Time.setText(cou + ":" + minute+" PM");
                                        DelivaryTime = Time.getText().toString();
                                    }


                                }else {
                                    Time.setText(hourOfDay + ":" + minute+" AM");
                                    DelivaryTime = Time.getText().toString();
                                }

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });*/
/*        //Dialog open
        mydialog.setContentView(R.layout.custom_addtocurt);
        TextView txtclose =(TextView) mydialog.findViewById(R.id.txtclose2);
        TextView txtname =(TextView) mydialog.findViewById(R.id.FoodETNAme);
        TextView txtnote2 =(TextView) mydialog.findViewById(R.id.add_note);
        TextView txtprice =(TextView) mydialog.findViewById(R.id.foodPrice);
        ImageView image =(ImageView) mydialog.findViewById(R.id.add_note0);
        txtnote =(EditText) mydialog.findViewById(R.id.etNote);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        txtnote2.setText("ADD NOTE");
        txtname.setText(orderCha
        rt.getProductName());


        image.setVisibility(View.INVISIBLE);
        txtprice.setVisibility(View.INVISIBLE);

        CardView confirm = mydialog.findViewById(R.id.cart);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderChart.setNote(txtnote.getText().toString());

                mydialog.dismiss();
            }
        });

        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();*/