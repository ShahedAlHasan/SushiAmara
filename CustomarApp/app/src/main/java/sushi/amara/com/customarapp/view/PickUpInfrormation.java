package sushi.amara.com.customarapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sushi.amara.com.customarapp.Common.Common;
import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.molie.ApiInterface;
import sushi.amara.com.customarapp.molie.ApiUtils;
import sushi.amara.com.customarapp.molie.Payment;
import sushi.amara.com.customarapp.pojo.ConfirmOrderMain;
import sushi.amara.com.customarapp.pojo.OrderChart;
import sushi.amara.com.customarapp.pojo.PaymentPojo;
import sushi.amara.com.customarapp.pojo.User;
import sushi.amara.com.customarapp.retrofit.RetrofitClient;
import sushi.amara.com.customarapp.retrofit.responce.MailResponce;

public class PickUpInfrormation extends NaviBase {

    private EditText editTextEmail, editTextPhone, editTextFirstName, editTextLastName,Firme,Anmer;

    private TextView Dokan,Address,Vat,Time;

    String name,fon,email,group,node,Note,Timenode,storeDistance,distancetime;

    String includeVatPrice,totalPrice,OrderTime,DelivaryTime;

    Spinner PAckege;

    private int mHour, mMinute;

    String location,store,System;

    String token;


    private ConfirmOrderMain confirmOrder;

    private List<OrderChart> orderCharts = new ArrayList<OrderChart>();
    private User user;

    Button Complete;

    FirebaseAuth auth;

    private DatabaseReference rootRef,notificationAuth;
    private DatabaseReference paymentRef;
    private DatabaseReference orderRef;

    String payType;

    Dialog mydialog;

    private LoadingDialog dialog;

    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_infrormation);


        confirmOrder =(ConfirmOrderMain) getIntent().getSerializableExtra("curt");
        location = getIntent().getStringExtra("address");
        store = getIntent().getStringExtra("store");
        System = getIntent().getStringExtra("get");

//        mtollbarText=(TextView) findViewById(R.id.toolbar_text_base);
//        mtollbarText.setText("Complete Order");
//        mtollbarText.setCompoundDrawablesWithIntrinsicBounds( R.drawable.store, 0, 0, 0);

        mydialog = new Dialog(this);
        dialog = new LoadingDialog(PickUpInfrormation.this,"Order Processing....");


        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        orderCharts = confirmOrder.getChartList();
        DelivaryTime = confirmOrder.getTime();
        totalPrice = confirmOrder.getTottalPrice();
        includeVatPrice = confirmOrder.getIncludeVatTotalPrice();
        storeDistance = confirmOrder.getDistance();
        distancetime = confirmOrder.getDistancetime();

        editTextEmail = findViewById(R.id.mail);
        editTextPhone = findViewById(R.id.phone);
        editTextFirstName = findViewById(R.id.first_name);
        editTextLastName = findViewById(R.id.last_name);
        Firme = findViewById(R.id.group);
        Anmer = findViewById(R.id.pick_note_if);
        Dokan = findViewById(R.id.dokan_name);
        Address = findViewById(R.id.address);
        Vat = findViewById(R.id.vat);
        Complete = findViewById(R.id.complete_order_final);
        Time = findViewById(R.id.time2);

        Dokan.setText("Sushi Amara "+confirmOrder.getStore());
        if (confirmOrder.getStore().equals("Langenfeld")){
            Address.setText("Hauptstraße 67, 40764 Langenfeld (Rheinland)");
        }else {
            Address.setText("Brückenstraße 28, 42799 Leichlingen");
        }
        Vat.setText(new DecimalFormat("##.##").format(Double.parseDouble(confirmOrder.getIncludeVatTotalPrice()))+" €");

        PAckege = findViewById(R.id.isp_pac);

        ArrayAdapter spinnerDegreeAdapter = ArrayAdapter.createFromResource(PickUpInfrormation.this, R.array.time_array, R.layout.spinner_item_select_model2);

        spinnerDegreeAdapter.setDropDownViewResource(R.layout.spinner_item_select_model2);

        PAckege.setAdapter(spinnerDegreeAdapter);

        PAckege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Timenode = PAckege.getItemAtPosition(i).toString().trim();
                if (Timenode.equals("So schnell es geht")){
                    Note = "ASAP";
                }else if (Timenode.equals("Zeit einstellen")){

                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);


                    Time.setVisibility(View.VISIBLE);
                    PAckege.setVisibility(View.GONE);
                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(PickUpInfrormation.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    if (hourOfDay>11) {
                                        if (hourOfDay==12){
                                            Time.setText(hourOfDay + ":" + minute+" PM");
                                            Note = Time.getText().toString();
                                        }else {
                                            int cou = hourOfDay-12;
                                            Time.setText(cou + ":" + minute+" PM");
                                            Note = Time.getText().toString();
                                        }


                                    }else {
                                        Time.setText(hourOfDay + ":" + minute+" AM");
                                        Note = Time.getText().toString();
                                    }

                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Note = "ASAP";
            }
        });


        //initial direbase
        auth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        notificationAuth = rootRef.child("Token").child(store);

        notificationAuth.child("token").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.getValue().toString() == null)){

                    token = dataSnapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Check already session , if ok-> DashBoard
        if(auth.getCurrentUser() != null){
            String id = auth.getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference("Customers").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    user = dataSnapshot.getValue(User.class);
                    
                    editTextFirstName.setText(user.getName().toString());
                    editTextLastName.setText(" ");
                    editTextPhone.setText(user.getPhone().toString());
                    editTextEmail.setText(user.getEmail().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = editTextFirstName.getText().toString();
                String lname = editTextLastName.getText().toString();
                fon = editTextPhone.getText().toString();
                email = editTextEmail.getText().toString();
                group = Firme.getText().toString();
                node = Anmer.getText().toString();


                if (fname.isEmpty()){
                    editTextFirstName.setError(getString(R.string.field_is_required));
                    editTextFirstName.requestFocus();
                    return;
                }
                if (lname.isEmpty()){
                    editTextLastName.setError(getString(R.string.field_is_required));
                    editTextLastName.requestFocus();
                    return;
                }
                if (fon.isEmpty()){
                    editTextPhone.setError(getString(R.string.field_is_required));
                    editTextPhone.requestFocus();
                    return;
                }
                if (email.isEmpty()){
                    editTextEmail.setError(getString(R.string.field_is_required));
                    editTextEmail.requestFocus();
                    return;
                }
                if (payType==null){
                    Toast.makeText(PickUpInfrormation.this, "Muss PayType auswählen....!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if(group==null){
                        group = " ";
                    }
                    if(node==null){
                        node = " ";
                    }
                    name = fname + " "+lname;
                    confirm();

                }


            }
        });


        RadioGroup rg = (RadioGroup) findViewById(R.id.paymentype);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio1:
                        // do operations specific to this selection
                        payType="papal";
                        break;
                    case R.id.radio2:
                        // do operations specific to this selection
                        payType="cashOn";
                        break;
                }
            }
        });



    }

    private void confirm() {

        dialog.show();

        orderRef = rootRef.child("OrderList").child("Active");
        paymentRef = rootRef.child("Payment");


        final String id = orderRef.push().getKey();


        final PaymentPojo pojo = new PaymentPojo(id, payType,includeVatPrice,"null","null");

        final ConfirmOrderMain con = new ConfirmOrderMain(id, orderCharts ,location,System,totalPrice,includeVatPrice,store,DelivaryTime,Note,name,email,fon,group,node,storeDistance,distancetime,payType);

        sentMail(con,pojo,id);




/*        //Set value to database
        orderRef.child(id).child("details").setValue(con).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    paymentRef.child(id).child("details").setValue(pojo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (payType.equals("cashOn")){
                                Toast.makeText(PickUpInfrormation.this, "Vielen Dank für Ihren Einkauf...!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PickUpInfrormation.this,FoodActivity.class);
                                intent.putExtra("store", store);
                                intent.putExtra("get", System);
                                intent.putExtra("address", location);
                                startActivity(intent);
                                finish();
                            }else if (payType.equals("papal")){
                                Intent intent = new Intent(PickUpInfrormation.this,PaypalPayment.class);
                                Common.store = store;
                                Common.system = System;
                                Common.location = location;
                                intent.putExtra("pay",pojo);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {

                    Toast.makeText(PickUpInfrormation.this, "Gescheitert", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PickUpInfrormation.this, "Gescheitert : "+e, Toast.LENGTH_SHORT).show();
            }
        });*/

    }


    private void getPayment(String amount,String desc){
        ApiInterface apiInterface= ApiUtils.getAPIService();
        String currency="EUR";
        String redirecturl="https://sushiamara.de";
        String webhookurl="https://webshop.example.org/payments/webhook";
        String metadata="";
        Map<String,String> map=new HashMap<String, String>();
        map.put("amount[currency]",currency);
        map.put("amount[value]",amount);
        Call<Payment> call=apiInterface.getPayments(map,desc,redirecturl,webhookurl,metadata);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                Log.d("responsedata",response.raw().toString());
                Log.d("responsedata",response.body().toString());
                Log.d("responsedata",response.body().getLinks().getCheckout().getHref());
                Toast.makeText(PickUpInfrormation.this, "Redirecting to the payment page", Toast.LENGTH_SHORT).show();



                /*Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(response.body().getLinks().getCheckout().getHref()));
                startActivity(viewIntent);*/
                startActivity(new Intent(PickUpInfrormation.this,MollieActivity.class)
                        .putExtra("url", response.body().getLinks().getCheckout().getHref()));
                finish();
/*                startActivity(new Intent(PickUpInfrormation.this,FoodActivity.class)
                        .putExtra("store", Common.store)
                        .putExtra("get", Common.system)
                        .putExtra("address", Common.location));
                finish();*/
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {

            }
        });
    }

    private void sentMail(final ConfirmOrderMain con, final PaymentPojo pojo, final String id) {




        //Getting content for email
        String email = con.getEmail();
        String subject = getString(R.string.Order_Details);
        String message = "Hallo "+name+",\n\n"+"Vielen Dank für Ihren Einkauf."
                +"\n\n"+"Grüße \n" +
                "Sushi Amara\n\n"+
                "Auftragsnummer.: "+"#"+con.getOrderID()+
                "\nBestelldatum.: "+currentDate+
                "\nBevorzugter Lieferschlitz: "+con.getTimeNote()+
                "\nLieferadresse :"+con.getLocation()+
                "\n\n\n....................Bestelldetails......................\n\n";

        String message2 = "";
        for (OrderChart c : orderCharts){
           message2+=c.getProductName()+ "----" +"("+
                    c.getProductQuantity() +")"+
                    "--------" + c.getProductPrice() +
                    "\n";
        }
        
        String massege3 = "\n\nGesamtsumme:  "+new DecimalFormat("##.##").format(Double.parseDouble(totalPrice))+" €"/*+
                "\n\nGesamt (inkl. zustellgebühr):  "+ new DecimalFormat("##.##").format(Double.parseDouble(includeVatPrice))+" €"*/
                ;

        //Creating SendMail object


        Call<MailResponce> callDepartment = RetrofitClient
                .getInstance()
                .getApi()
                .sentMail(email,name,subject,message+message2+massege3);

        callDepartment.enqueue(new Callback<MailResponce>() {
            @Override
            public void onResponse(Call<MailResponce> call, Response<MailResponce> response) {
                if (response.isSuccessful()){


                }else {
                    dialog.dismiss();
                    Toast.makeText(PickUpInfrormation.this, "Error Code: "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MailResponce> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(PickUpInfrormation.this, "Error Code: 400000004", Toast.LENGTH_SHORT).show();

            }
        });

        if(auth.getCurrentUser() != null){
            String cusId = auth.getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference("CustomersorderDetails").child(cusId).child(id).child("details").setValue(con).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    //Set value to database
                    orderRef.child(id).child("details").setValue(con).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                paymentRef.child(id).child("details").setValue(pojo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {



                                        if (token !=null)
                                            sendFireBaseNotification(token);

                                        dialog.dismiss();

                                        if (payType.equals("cashOn")){
                                            Toast.makeText(PickUpInfrormation.this, "Vielen Dank für Ihren Einkauf...!", Toast.LENGTH_SHORT).show();
                                            Common.orderCharts.clear();
                                            Intent intent = new Intent(PickUpInfrormation.this,FoodActivity.class);
                                            intent.putExtra("store", store);
                                            intent.putExtra("get", System);
                                            intent.putExtra("address", location);
                                            startActivity(intent);
                                            finish();
                                        }else if (payType.equals("papal")){
//                                            Intent intent = new Intent(PickUpInfrormation.this,PaypalPayment.class);
//                                            Common.store = store;
//                                            Common.system = System;
//                                            Common.location = location;
//                                            intent.putExtra("pay",pojo);
//                                            startActivity(intent);
//                                            finish();

                                            final double amount=Double.parseDouble(includeVatPrice);
                                            final DecimalFormat df = new DecimalFormat("#.00");
                                            //getPayment(df.format(amount),id);
                                            getPayment(df.format(amount),"Ich bezahle mein Essen");


                                        }
                                    }
                                });

                            } else {
                                dialog.dismiss();
                                Toast.makeText(PickUpInfrormation.this, "Gescheitert", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(PickUpInfrormation.this, "Gescheitert : "+e, Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });

        }
        else {

            //Set value to database
            orderRef.child(id).child("details").setValue(con).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        paymentRef.child(id).child("details").setValue(pojo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (token !=null)
                                    sendFireBaseNotification(token);

                                dialog.dismiss();

                                if (payType.equals("cashOn")){
                                    Toast.makeText(PickUpInfrormation.this, "Vielen Dank für Ihren Einkauf...!", Toast.LENGTH_SHORT).show();
                                    Common.orderCharts.clear();
                                    Intent intent = new Intent(PickUpInfrormation.this,FoodActivity.class);
                                    intent.putExtra("store", store);
                                    intent.putExtra("get", System);
                                    intent.putExtra("address", location);
                                    //
                                    startActivity(intent);
                                    finish();
                                }else if (payType.equals("papal")){
                                    /*Intent intent = new Intent(PickUpInfrormation.this,PaypalPayment.class);
                                    Common.store = store;
                                    Common.system = System;
                                    Common.location = location;
                                    intent.putExtra("pay",pojo);
                                    //dialog.dismiss();
                                    startActivity(intent);
                                    finish();*/
                                    final double amount=Double.parseDouble(includeVatPrice);
                                    final DecimalFormat df = new DecimalFormat("#.00");
                                    //getPayment(df.format(amount),id);
                                    getPayment(df.format(amount),"Ich bezahle mein Essen");
                                }
                            }
                        });

                    } else {
                        dialog.dismiss();
                        Toast.makeText(PickUpInfrormation.this, "Gescheitert", Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(PickUpInfrormation.this, "Gescheitert : "+e, Toast.LENGTH_SHORT).show();
                }
            });


        }


    }


    private void sendFireBaseNotification(String token) {

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
//
        client.addHeader("authorization", "key=AAAASZkiJUA:APA91bEi-PUU0igeqGRV8E2Ui-dR2G44N2VylamNgLqm4yALQt1LDNrIxwYrMgvAUsT_I0K__jBiVdhK9KcgYjj7UVSH8vJNxlz6_CIKqeKLVEi0FDW3m9SC53PfG-pEgPyyTxVqYgN5");

        StringEntity entity = null;

        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("to", token);

            JSONObject data = new JSONObject();
            data.put("title", "New order.");
            data.put("message", "You have a new Order.Go to App And Check it.");
            jsonParams.put("data", data);
            jsonParams.put("time_to_live", 30);
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        client.post(getApplicationContext(), "https://fcm.googleapis.com/fcm/send", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("FCM::", "Notification Successfully send::" + response);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("FCM::", "Object Failed::" + errorResponse + statusCode);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.i("FCM::", "Send Notification::" + responseString + statusCode);

            }

        });


    }


    @Override
    public void onBackPressed() {
            new AlertDialog.Builder(this)
                    .setMessage("Wenn Du den Store Verlässt geht Dein Warenkorb verloren. Willst Du wirklich zurück zur Storeauswahl?")
                    .setCancelable(false)
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            PickUpInfrormation.this.finish();
                        }
                    })
                    .setNegativeButton("Nein", null)
                    .show();
//            super.onBackPressed();

        }


}
