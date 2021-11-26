package sushi.amara.com.customarapp.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.SpaceBook;
import sushi.amara.com.customarapp.retrofit.RetrofitClient;
import sushi.amara.com.customarapp.retrofit.responce.MailResponce;

public class SpaceBooking extends NaviBase {

    private EditText editTextEmail, editTextPhone, editName,Group,Note;

    private int mYear, mMonth, mDay, mHour, mMinute;

    String name,fon,email,group,note,date,time;

    TextView DateShow,Time;

    Spinner PAckege;

    String store;

    private DatabaseReference rootRef;
    private DatabaseReference bookRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_booking);

        PAckege = findViewById(R.id.isp_pac2);

//        mtollbarText=(TextView) findViewById(R.id.toolbar_text_base);
//        mtollbarText.setText("Space Booking");
//        mtollbarText.setCompoundDrawablesWithIntrinsicBounds( R.drawable.store, 0, 0, 0);

        rootRef = FirebaseDatabase.getInstance().getReference();
        bookRef = rootRef.child("SpaceBooking");



        editTextEmail = findViewById(R.id.mail2);
        editTextPhone = findViewById(R.id.phone2);
        editName = findViewById(R.id.name2);
        Group = findViewById(R.id.group);
        Note = findViewById(R.id.pick_note);
        DateShow = findViewById(R.id.date);
        Time = findViewById(R.id.time4);

        DateShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(SpaceBooking.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                DateShow.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                                date = DateShow.getText().toString();

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }


        });

        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(SpaceBooking.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay>11) {
                                    if (hourOfDay==12){
                                        Time.setText(hourOfDay + ":" + minute+" PM");
                                        time = Time.getText().toString();

                                    }else {
                                        int cou = hourOfDay-12;
                                        Time.setText(cou + ":" + minute+" PM");
                                        time = Time.getText().toString();
                                    }


                                }else {
                                    Time.setText(hourOfDay + ":" + minute+" AM");
                                    time = Time.getText().toString();

                                }

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }


        });

        ArrayAdapter spinnerDegreeAdapter = ArrayAdapter.createFromResource(
                SpaceBooking.this, R.array.store_array, R.layout.spinner_item_select_model2);

        spinnerDegreeAdapter.setDropDownViewResource(R.layout.spinner_item_select_model2);

        PAckege.setAdapter(spinnerDegreeAdapter);

        PAckege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i>0){
                    store = PAckege.getItemAtPosition(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void booking(View view) {
        if (store==null){
            Toast.makeText(this, "Wählen Sie Store First", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date==null){
            Toast.makeText(this, "Datum verpflichtend", Toast.LENGTH_SHORT).show();
            return;
        }
        name = editName.getText().toString();
        fon = editTextPhone.getText().toString();
        email = editTextEmail.getText().toString();
        group = Group.getText().toString();
        note = Note.getText().toString();

        if (group==null){
            group = " ";
        }
        if (note==null){
            note = " ";
        }

        if (name.isEmpty()){
            editName.setError(getString(R.string.field_is_required));
            editName.requestFocus();
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

        else {

            ConfirmBook();


        }


    }

    private void ConfirmBook() {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Hochladen...");
        mDialog.show();

        String id = bookRef.push().getKey();

        final SpaceBook spaceBook = new SpaceBook(id, store,group,date, time,note,name,fon,email,"pending" );

        bookRef.child(id).child("details").setValue(spaceBook).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    sentMail();
                    mDialog.dismiss();


                } else {
                    mDialog.dismiss();
                    Toast.makeText(SpaceBooking.this, "Gescheitert...!", Toast.LENGTH_SHORT).show();

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mDialog.dismiss();
                Toast.makeText(SpaceBooking.this, "Gescheitert "+e, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void sentMail() {

        String subject = "Anfrage gesendet!";
        String message = "Danke, "+name +"!"
                +"\n\n"+"Wir haben Ihre Reservierungsanfrage erhalten und werden diese\n" +
                "schnellstmöglich bearbeiten. Sie erhalten eine Antwort per \n" +
                "E-Mail an "+email+".\n \n" +
                "Beste Grüße,\nSushi Amara"
                ;

        //Creating SendMail object


        Call<MailResponce> callDepartment = RetrofitClient
                .getInstance()
                .getApi()
                .sentMail(email,name,subject,message);

        callDepartment.enqueue(new Callback<MailResponce>() {
            @Override
            public void onResponse(Call<MailResponce> call, Response<MailResponce> response) {
                if (response.isSuccessful()){

                    MailResponce mailResponce = response.body();

                    Toast.makeText(SpaceBooking.this, " "+mailResponce.getMessage(), Toast.LENGTH_SHORT).show();


//                    loading.dismiss();

                    Intent intent = new Intent(SpaceBooking.this,MainActivity.class);
                    Toast.makeText(SpaceBooking.this, "Erfolgreich. Wir werden uns umgehend mit Ihnen in Verbindung setzen", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();


                }else {
//                    loading.dismiss();
                    Toast.makeText(SpaceBooking.this, "Error Code: "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MailResponce> call, Throwable t) {
//                loading.dismiss();

            }
        });





    }
    /*private void sentMail() {

        String subject = "Anfrage gesendet!";
        String message = "Danke, "+name +"!"
                +"\n\n"+"Wir haben Ihre Reservierungsanfrage erhalten und werden diese\n" +
                "schnellstmöglich bearbeiten. Sie erhalten eine Antwort per \n" +
                "E-Mail an "+email+".\n \n" +
                "Beste Grüße,\nSushi Amara"
                ;

        //Creating SendMail object

        SendMail sm = new SendMail(this, email, subject, message);
        //Executing sendmail to send email
        sm.execute();
    }
*/
}
