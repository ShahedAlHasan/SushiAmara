package sushi.amara.com.customarapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.PaymentPojo;

public class PaymentDetails extends AppCompatActivity {

    TextView txtId, txtAmount, txtStatus;

    PaymentPojo paymentPojo;

    private DatabaseReference rootRef;
    private DatabaseReference paymentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        rootRef = FirebaseDatabase.getInstance().getReference();

        paymentPojo = (PaymentPojo) getIntent().getSerializableExtra("pay");

        txtId = (TextView)findViewById(R.id.txtId);
        txtAmount = (TextView)findViewById(R.id.txtAmount);
        txtStatus = (TextView)findViewById(R.id.txtStatus);

        Intent intent = getIntent();
        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        }
        catch (JSONException e)
        {

        }
    }


    private void showDetails(JSONObject response, String paymentAmount)
    {
        try{
            final ProgressDialog mDialog = new ProgressDialog(PaymentDetails.this);
            mDialog.setMessage("loading...");
            mDialog.show();

            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText(response.getString(String.format("$%s", paymentAmount)));

            paymentRef = rootRef.child("Payment");

            final PaymentPojo pojo = new PaymentPojo(paymentPojo.getId(), "online",response.getString(String.format(paymentAmount)),response.getString("id"),response.getString("state"));

            paymentRef.child(paymentPojo.getId()).child("details").setValue(pojo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(PaymentDetails.this, "Order Totally Complete", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
