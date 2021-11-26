package sushi.amara.com.customarapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sushi.amara.com.customarapp.Common.Common;
import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.AddFood2;
import sushi.amara.com.customarapp.pojo.ConfirmOrderMain;
import sushi.amara.com.customarapp.pojo.OrderChart;
import sushi.amara.com.customarapp.test.TestAdapter;

public class AllFoodSearch extends NaviBase implements FoodRecylaerViewAdapter2.FoodListiner2 {

    RecyclerView Category,FoodRecycler, test;

    private List<AddFood2> addFoods = new ArrayList<AddFood2>();
    private List<AddFood2> addFoods2 = new ArrayList<AddFood2>();

    private Toolbar searchBar;

    private List<OrderChart> orderCharts = new ArrayList<OrderChart>();

    private DatabaseReference rootRef;
    private DatabaseReference foodRef;

    Dialog mydialog;



    private boolean checkNothing = false;

    TextView Item;
    String catagory;

    FrameLayout frameLayout;

    private double totalPrice;

    int it = 0;

    EditText txtnote;

    private TestAdapter adapterT;

    String Note;

    String SeleFoodPrice;

    String location,store,System,treffen,treffenFinal;

    String SSstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_food_search);

//        Utils.getDatabase();

        searchBar = findViewById(R.id.search_bar_base);
        setSupportActionBar(searchBar);
        getSupportActionBar().setTitle(null);

        FoodRecycler = findViewById(R.id.foodRecyallfood);
        frameLayout = findViewById(R.id.fram2);

        location = getIntent().getStringExtra("address");
        store = getIntent().getStringExtra("store");
        System = getIntent().getStringExtra("get");

        mydialog = new Dialog(this);

        Item = findViewById(R.id.item2);

//        mtollbarText=(TextView) findViewById(R.id.toolbar_text_base);
//        mtollbarText.setText(store);


        rootRef = FirebaseDatabase.getInstance().getReference();

        foodRef = rootRef.child("All Food");

        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addFoods.clear();
                //historyTV.setText(String.valueOf(dataSnapshot.getValue()));
                for (DataSnapshot hd: dataSnapshot.getChildren()){
                    AddFood2 doc = hd.child("details").getValue(AddFood2.class);
                    addFoods.add(doc);
                }

                FoodRecylaerViewAdapter2  recylaerViewAdapter = new FoodRecylaerViewAdapter2(AllFoodSearch.this,addFoods);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                FoodRecycler.setLayoutManager(mLayoutManager);
                FoodRecycler.setItemAnimator(new DefaultItemAnimator());
                FoodRecycler.setAdapter(recylaerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDatabase();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);



        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();

        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
            searchView.setPadding(0,1,0,1);
            searchView.setQueryHint("Suche nach Name oder Nummer...");

        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                addFoods2.clear();
                for (int i=0;i<addFoods.size();i++){

                    if (addFoods.get(i).getFoodName().toLowerCase().indexOf(s)>=0 || addFoods.get(i).getFoodNumber().toLowerCase().indexOf(s)>=0){
                        addFoods2.add(addFoods.get(i));
                    }
                }
                FoodRecylaerViewAdapter2  recylaerViewAdapter = new FoodRecylaerViewAdapter2(AllFoodSearch.this,addFoods2);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                FoodRecycler.setLayoutManager(mLayoutManager);
                FoodRecycler.setItemAnimator(new DefaultItemAnimator());
                FoodRecycler.setAdapter(recylaerViewAdapter);

                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setSubmitButtonEnabled(true);

        return true;
    }



    private void addToChart(final AddFood2 addFood) {
        //Dialog open
        mydialog.setContentView(R.layout.custom_addtocurt2);
        CardView txtcncNote=(CardView) mydialog.findViewById(R.id.txtclose22);
        TextView txtclose =(TextView) mydialog.findViewById(R.id.txtclose2);
        TextView txtname =(TextView) mydialog.findViewById(R.id.FoodETNAme2);
        TextView txtsub =(TextView) mydialog.findViewById(R.id.FoodETSub2);
        TextView txtAllergnes =(TextView) mydialog.findViewById(R.id.FoodAllergens2);
        TextView txtprice =(TextView) mydialog.findViewById(R.id.foodPrice2);
        ImageView imageView =(ImageView) mydialog.findViewById(R.id.food_curt_image2);
        txtnote =(EditText) mydialog.findViewById(R.id.etNote2);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        txtcncNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        txtname.setText(addFood.getFoodName());
        txtsub.setText(addFood.getFoodDescription());
        txtprice.setText(addFood.getFoodPrice());

        if (addFood.getAllergens() != null
                && !TextUtils.isEmpty(addFood.getAllergens())) {
            txtAllergnes.setText("Allergnes: "+addFood.getAllergens());
        }
        else {
            txtAllergnes.setText("Allergens: Not added yet..!");
        }
        if (addFood.getImageUrl() != null
                && !TextUtils.isEmpty(addFood.getImageUrl())) {

            Picasso.get().load(addFood.getImageUrl()).into(imageView);
        }

        CardView confirm = mydialog.findViewById(R.id.cart2);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(addFood);
                frameLayout.setVisibility(View.VISIBLE);
                mydialog.dismiss();
            }
        });

        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();


    }

    private void openDialog(AddFood2 addFood) {
        String Note = txtnote.getText().toString();

        if (Note.isEmpty()){
            Note = "  ";
        }


        checkNothing = false;
        it=it+1;
        Item.setText(String.valueOf(it));
        for (OrderChart c: orderCharts){
            if (c.getProductName().equals(addFood.getFoodName())){
                int i = Integer.parseInt(c.getProductQuantity());
                i++;
                orderCharts.remove(c);
                c.setProductQuantity(String.valueOf(i));
                double price = Double.parseDouble(addFood.getFoodPrice());
                c.setProductPrice(String.valueOf(price*i));
                c.setNote(Note);
                orderCharts.add(c);
                checkNothing =  true;
                break;
            }else {
                checkNothing = false;
            }
        }
        if (!checkNothing){

            OrderChart chart = new OrderChart(addFood.getFoodNumber(),addFood.getFoodName(),addFood.getFoodDescription(), String.valueOf(1), addFood.getFoodPrice(), Note);
            orderCharts.add(chart);
        }
    }

    public void onDatabase(){

        if (location==null){
            popup();
        }else {

            gotoNext();

        }


    }

    private void gotoNext() {

        String id = "PUSH ID";
        totalPrice = 0;
        for (OrderChart c: orderCharts){
            double price = Double.parseDouble(c.getProductPrice());
            totalPrice += price;
        }

        String includeVatPrice = String.valueOf((totalPrice+(totalPrice*15.0)/100.0));
        ConfirmOrderMain cd = new ConfirmOrderMain(id, orderCharts, location,System, String.valueOf(totalPrice),includeVatPrice,store,"null","local","null","null","null","null","null","0.0 km","0.0 min");
        Intent intent = new Intent(new Intent(AllFoodSearch.this,FinalCart.class));
        intent.putExtra("curt",cd);
        intent.putExtra("store", store);
        intent.putExtra("get", System);
        intent.putExtra("address", location);
        startActivity(intent);
        finish();
    }

    private void popup() {

        mydialog.setContentView(R.layout.castom_appointment2);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();
        Button confirm = mydialog.findViewById(R.id.confirmBooking5);
        TextView txtclose =(TextView) mydialog.findViewById(R.id.txtclose5);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        final EditText hose = mydialog.findViewById(R.id.house5);
        final EditText road = mydialog.findViewById(R.id.road5);
        final EditText poc = mydialog.findViewById(R.id.poscpde5);
        final EditText city = mydialog.findViewById(R.id.city5);
        final RadioGroup storeGroup = mydialog.findViewById(R.id.storeGroup5);
        final LinearLayout first = mydialog.findViewById(R.id.first);
        final LinearLayout second = mydialog.findViewById(R.id.second);
        final LinearLayout third = mydialog.findViewById(R.id.third);
        final LinearLayout fourth = mydialog.findViewById(R.id.fourth);
        final LinearLayout fith = mydialog.findViewById(R.id.fith);
        final LinearLayout six = mydialog.findViewById(R.id.six);
        final LinearLayout deliorpic = mydialog.findViewById(R.id.deliorpic);
        final Button Deleviry = mydialog.findViewById(R.id.deleviry);

        Deleviry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System = "Delivery";
                deliorpic.setVisibility(View.GONE);
                first.setVisibility(View.VISIBLE);
                second.setVisibility(View.VISIBLE);
                third.setVisibility(View.VISIBLE);
                fourth.setVisibility(View.VISIBLE);
                fith.setVisibility(View.VISIBLE);
                six.setVisibility(View.VISIBLE);
            }
        });

        storeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)mydialog.findViewById(checkedId);
                store=rb.getText().toString();

            }

        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (store==null){
                    Toast.makeText(AllFoodSearch.this, "Bitte wählen Sie Speichern", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Road = road.getText().toString().trim();
                if (Road.isEmpty()){
                    road.setError("Straße fehlt!");
                    road.requestFocus();
                    return;
                }

                String house = hose.getText().toString().trim();
                if (house.isEmpty()){
                    hose.setError("Hausnummer fehlt!");
                    hose.requestFocus();
                    return;
                }
                String Pos = poc.getText().toString().trim();
                if (Pos.isEmpty()){
                    poc.setError("Postleitzahl fehlt!");
                    poc.requestFocus();
                    return;
                }
                String City = city.getText().toString().trim();
                if (City.isEmpty()){
                    city.setError("Stadt fehlt!");
                    city.requestFocus();
                    return;
                }else {
                    final String fullAddress = "Rd no "+Road+",H-"+house+","+Pos+","+City;
                    Common.location = fullAddress;
                    location = fullAddress;
                    gotoNext();
                    mydialog.dismiss();
                }
            }
        });
    }


    @Override
    public void onFooodCurt2(AddFood2 addFood) {
        final String catagFn [] = {"45","46","49","50","113","254","553","650","653","655","700","704"};
        List<String> Fnumber = new ArrayList<String>(Arrays.asList(catagFn));
        for (int n=0 ; n<Fnumber.size();n++){
            if (addFood.getFoodNumber().equals(Fnumber.get(n))){
                addToChart2(addFood);
                break;
            }else {
                addToChart(addFood);
            }

        }
    }

    private void addToChart2(final AddFood2 addFood) {
        //Dialog open
        mydialog.setContentView(R.layout.custom_addtocurt_flaver);
        CardView txtcncNote=(CardView) mydialog.findViewById(R.id.txtclose2f);
        TextView txtclose =(TextView) mydialog.findViewById(R.id.txtclosef);
        TextView txtname =(TextView) mydialog.findViewById(R.id.FoodETNAmef);
        TextView txtsub =(TextView) mydialog.findViewById(R.id.FoodETSubf);
        TextView txtAllergnes =(TextView) mydialog.findViewById(R.id.FoodAllergensf);
        final TextView txtprice =(TextView) mydialog.findViewById(R.id.foodPricef);
        RadioButton Lachs = mydialog.findViewById(R.id.radio001);
        RadioButton Hühnchen = mydialog.findViewById(R.id.radio002);
        txtnote =(EditText) mydialog.findViewById(R.id.etNotef);

        final Double Fpri = Double.valueOf(addFood.getFoodPrice());
        final Double nFpri;
        if (addFood.getFoodNumber().equals("49")||addFood.getFoodNumber().equals("50")){
            Lachs.setText("Nudeln(+1.50€)");
            Hühnchen.setText("Reis");
            nFpri = Fpri+1.50;

        }else if (addFood.getFoodNumber().equals("113")){
            Lachs.setText("Thunfisch");
            Hühnchen.setText("Lachs");
            nFpri = Fpri;
        }else if (addFood.getFoodNumber().equals("254")){
            Lachs.setText("chicken");
            Hühnchen.setText("fisch");
            nFpri = Fpri;
        }
        else {
            nFpri = Fpri+3.00;
        }

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        Lachs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                treffen = "Lachs";
                SeleFoodPrice = String.valueOf(nFpri);
                txtprice.setText(SeleFoodPrice);

            }
        });
        Hühnchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                treffen = "Hühnchen";
                SeleFoodPrice = addFood.getFoodPrice();
                txtprice.setText(SeleFoodPrice);
            }
        });

        txtcncNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        txtname.setText(addFood.getFoodName());
        txtsub.setText(addFood.getFoodDescription());
        txtprice.setText(SeleFoodPrice);

        if (addFood.getAllergens() != null
                && !TextUtils.isEmpty(addFood.getAllergens())) {
            txtAllergnes.setText("Allergnes: "+addFood.getAllergens());
        }
        else {
            txtAllergnes.setText("Allergens: Not added yet..!");
        }


        CardView confirm = mydialog.findViewById(R.id.cartf);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (treffen==null){
                    Toast.makeText(AllFoodSearch.this, "!Sie müssen noch mindestens 1 Auswahlmöglichkeiten treffen!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    treffenFinal =treffen;
                    treffen= null;
                    openDialog2(addFood);
                    frameLayout.setVisibility(View.VISIBLE);
                    mydialog.dismiss();
                }
            }
        });

        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();


    }

    private void openDialog2(AddFood2 addFood) {

        Note = txtnote.getText().toString();

        if (Note.isEmpty()){
            Note = " ";
        }


        checkNothing = false;
        it=it+1;
        Item.setText(String.valueOf(it));
        for (OrderChart c: orderCharts){
            if (c.getProductName().equals(addFood.getFoodName())){
                int i = Integer.parseInt(c.getProductQuantity());
                i++;
                orderCharts.remove(c);
                c.setProductQuantity(String.valueOf(i));
                double price = Double.parseDouble(SeleFoodPrice);
                c.setProductPrice(String.valueOf(price*i));
                c.setNote(Note+" : "+treffenFinal);
                orderCharts.add(c);
                checkNothing =  true;
                break;
            }else {
                checkNothing = false;
            }
        }
        if (!checkNothing){

            OrderChart chart = new OrderChart(addFood.getFoodNumber(),addFood.getFoodName(),addFood.getFoodDescription(), String.valueOf(1), SeleFoodPrice, Note+" : "+treffenFinal);
            orderCharts.add(chart);
        }

    }


    public void pickup2(View view) {
        if (store==null){
            Toast.makeText(AllFoodSearch.this, "Bitte wählen Sie Speichern", Toast.LENGTH_SHORT).show();
            return;
        }else {
            String id = "PUSH ID";
            totalPrice = 0;
            for (OrderChart c: orderCharts){
                double price = Double.parseDouble(c.getProductPrice());
                totalPrice += price;
            }

            location = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            System = "PickUp";
            String includeVatPrice = String.valueOf((totalPrice+(totalPrice*15.0)/100.0));
            ConfirmOrderMain cd = new ConfirmOrderMain(id, orderCharts, location,System, String.valueOf(totalPrice),includeVatPrice,store,"null","local","null","null","null","null","null","0.0 min","0.0 min");
            Intent intent = new Intent(new Intent(AllFoodSearch.this,FinalCart.class));
            intent.putExtra("curt",cd);
            intent.putExtra("store", store);
            intent.putExtra("get", System);
            intent.putExtra("address", location);
            startActivity(intent);
            finish();
        }

    }
}
