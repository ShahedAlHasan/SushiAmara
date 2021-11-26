package sushi.amara.com.customarapp.view;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.ConfirmOrderMain;
import sushi.amara.com.customarapp.pojo.User;

public class ProfileActivity extends NaviBase implements ActiveOrderAdapter.OnGetListener{

    private FirebaseAuth auth;

    TextView name,fon,mail;

    LinearLayout linearLayout;

    private List<ConfirmOrderMain> orderCharts = new ArrayList<ConfirmOrderMain>();

    CardView logoutBt;

    private DatabaseReference rootRef;
    private DatabaseReference orderRef;

    RecyclerView Category,FoodRecycler, test;

    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        mtollbarText=(TextView) findViewById(R.id.toolbar_text_base);
//        mtollbarText.setText("Profile");
//        mtollbarText.setCompoundDrawablesWithIntrinsicBounds( R.drawable.user, 0, 0, 0);
        //Init Firebase
        auth = FirebaseAuth.getInstance();

        rootRef = FirebaseDatabase.getInstance().getReference();
        orderRef = rootRef.child("CustomersorderDetails").child(auth.getUid());
        FoodRecycler = findViewById(R.id.foodRecy2);

        linearLayout = findViewById(R.id.linearlay);
        name = findViewById(R.id.userName);
        fon = findViewById(R.id.userfon);
        mail = findViewById(R.id.userMail);
        logoutBt = findViewById(R.id.logout);

        logoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseDatabase.getInstance().getReference("Customers").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                if (user.getStatus().equals("Active")) {
                    linearLayout.setVisibility(View.GONE);
                    name.setText(user.getName());
                    fon.setText(user.getPhone());
                    mail.setText(user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                orderCharts.clear();

                for (DataSnapshot donnerSnapshot : dataSnapshot.getChildren()) {
                    ConfirmOrderMain confirmOrder = donnerSnapshot.child("details").getValue(ConfirmOrderMain.class);
                    orderCharts.add(confirmOrder);
                }


                ActiveOrderAdapter recylaerViewAdapter = new ActiveOrderAdapter(ProfileActivity.this,orderCharts);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProfileActivity.this);
                FoodRecycler.setLayoutManager(mLayoutManager);
                FoodRecycler.setItemAnimator(new DefaultItemAnimator());
                FoodRecycler.setAdapter(recylaerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main2,menu);
        return true;
    }

    public void main2(MenuItem item) {

        auth.signOut();
        if(auth.getCurrentUser() == null)
        {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        }

    }

    @Override
    public void onGet(ConfirmOrderMain confirmOrder, int position) {

    }
}
