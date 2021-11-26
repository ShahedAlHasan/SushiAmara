package sushi.amara.com.customarapp.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sushi.amara.com.customarapp.Common.Common;
import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.AddFood;
import sushi.amara.com.customarapp.pojo.AddFood2;
import sushi.amara.com.customarapp.pojo.ConfirmOrderMain;
import sushi.amara.com.customarapp.pojo.OrderChart;
import sushi.amara.com.customarapp.test.TestAdapter;

public class FoodActivity extends NaviBase implements MenuCatagoriAdapter.CatagoriListAdapterListener ,FoodRecylaerViewAdapter.FoodListiner,FoodRecylaerViewAdapter2.FoodListiner2 {

    RecyclerView Category,FoodRecycler, test;

    private List<AddFood> addFoods = new ArrayList<AddFood>();
    private List<AddFood> addFoods2 = new ArrayList<AddFood>();
    private List<OrderChart> orderCharts = new ArrayList<OrderChart>();

    String SeleFoodPrice;

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

    String location,store,System,treffen,treffenFinal;

    ImageView Item_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

/*        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }*/


//        Utils.getDatabase();

        mydialog = new Dialog(this);



        Item = findViewById(R.id.item);
        Category = findViewById(R.id.menuName);
        FoodRecycler = findViewById(R.id.foodRecy);

        Item_image = findViewById(R.id.item_image);

        frameLayout = findViewById(R.id.fram);

        location = getIntent().getStringExtra("address");
        store = getIntent().getStringExtra("store");
        System = getIntent().getStringExtra("get");

//        Log.d("Shafi",Common.myLocation);

//        mtollbarText=(TextView) findViewById(R.id.toolbar_text_base);
//        mtollbarText.setText(store);
//        mtollbarText.setCompoundDrawablesWithIntrinsicBounds( R.drawable.store, 0, 0, 0);

        orderCharts.clear();
        totalPrice = 0;

        final String catag [] = {"Popular dishes","NEU","Mittagskarte bis 16:00 Uhr","Vorspeisen und Snacks","Spezial Salate","Gemischte Sushi-Boxen","SUSHI AMARA - Spezial Suppen","Nigiri Sushi","Maki Sushi","SUSHI AMARA Inside Out Maki","Spezial Sashimi",
                "Spezial Futo Maki","Temaki Handrolle","Spezial Gunkan","Warme Gerichte","Japanische Nudeln","Spezial Gebratener Reis","Einzelbestellungen",
                "Alkoholfreie Getränke","Biere","Sake","Vegetarische/Vegane Speisen","Dessert"};

        final List<String> CatagoryList = new ArrayList<String>(Arrays.asList(catag));

        MenuCatagoriAdapter recylaerViewAdapter = new MenuCatagoriAdapter(FoodActivity.this, CatagoryList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        Category.setLayoutManager(mLayoutManager);
        Category.setItemAnimator(new DefaultItemAnimator());
        Category.setAdapter(recylaerViewAdapter);

        rootRef = FirebaseDatabase.getInstance().getReference();

        foodRef = rootRef.child("Food List").child("Category").child("Popular dishes").child("Food");

        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addFoods.clear();
                //historyTV.setText(String.valueOf(dataSnapshot.getValue()));
                for (DataSnapshot hd: dataSnapshot.getChildren()){
                    AddFood doc = hd.child("details").getValue(AddFood.class);
                    addFoods.add(doc);
                }



                FoodRecylaerViewAdapter  recylaerViewAdapter = new FoodRecylaerViewAdapter(FoodActivity.this,addFoods);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                FoodRecycler.setLayoutManager(mLayoutManager);
                FoodRecycler.setItemAnimator(new DefaultItemAnimator());
                FoodRecycler.setAdapter(recylaerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("Shafi",""+databaseError);
            }
        });

/*        foodRef = rootRef.child("All Food");

        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addFoods2.clear();
                //historyTV.setText(String.valueOf(dataSnapshot.getValue()));
                for (DataSnapshot hd: dataSnapshot.getChildren()){
                    AddFood2 doc = hd.child("details").getValue(AddFood2.class);
                    addFoods2.add(doc);
                }

                FoodRecylaerViewAdapter2  recylaerViewAdapter = new FoodRecylaerViewAdapter2(FoodActivity.this,addFoods2);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                FoodRecycler.setLayoutManager(mLayoutManager);
                FoodRecycler.setItemAnimator(new DefaultItemAnimator());
                FoodRecycler.setAdapter(recylaerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        if (Common.orderCharts.size()>0){

            it=Common.orderCharts.size();
            Item.setText(String.valueOf(it));

            frameLayout.setVisibility(View.VISIBLE);
            orderCharts = Common.orderCharts;
        }


        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onDatabase();
            }
        });


    }


    @Override
    public void onCompleteBooking(String hb) {

        Item_imageShow(hb);

        if (hb.equals("Vegetarische/Vegane Speisen")){
            catagory = "Vegetarische";
        }
        else {

            catagory = hb;
        }

        foodRef = rootRef.child("Food List").child("Category").child(catagory).child("Food");

        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addFoods.clear();
                //historyTV.setText(String.valueOf(dataSnapshot.getValue()));
                for (DataSnapshot hd: dataSnapshot.getChildren()){
                    AddFood doc = hd.child("details").getValue(AddFood.class);
                    addFoods.add(doc);
                }

                Collections.sort(addFoods, new Comparator<AddFood>() {
                    @Override
                    public int compare(AddFood addFood, AddFood t1) {
                        Integer value1 = Integer.valueOf(addFood.getFoodNumber());
                        Integer value2 = Integer.valueOf(t1.getFoodNumber());
                        return value1.compareTo(value2);
                    }
                });

                FoodRecylaerViewAdapter  recylaerViewAdapter = new FoodRecylaerViewAdapter(FoodActivity.this,addFoods);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                FoodRecycler.setLayoutManager(mLayoutManager);
                FoodRecycler.setItemAnimator(new DefaultItemAnimator());
                FoodRecycler.setAdapter(recylaerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Shafi",""+databaseError);
            }
        });
    }

    private void Item_imageShow(String hb) {
        if (hb.equals("Vorspeisen und Snacks")){
            Item_image.setImageResource(R.drawable.vorspeisen_und_snacks);
            //Picasso.get().load(R.drawable.vorspeisen_und_snacks).into(Item_image);
        }else if (hb.equals("Gemischte Sushi-Boxen")){
            Item_image.setImageResource(R.drawable.gemischte_sushiboxen);
            //Picasso.get().load(R.drawable.gemischte_sushiboxen).into(Item_image);
        } else if (hb.equals("SUSHI AMARA - Spezial Suppen")){
            Item_image.setImageResource(R.drawable.sushi_amara_spezial_suppen);
            //Picasso.get().load(R.drawable.sushi_amara_spezial_suppen).into(Item_image);
        }else if (hb.equals("Spezial Salate")){
            Item_image.setImageResource(R.drawable.spezial_salate);
            //Picasso.get().load(R.drawable.spezial_salate).into(Item_image);
        }else if (hb.equals("Nigiri Sushi")){
            Item_image.setImageResource(R.drawable.nigiri_sushi);
            //Picasso.get().load(R.drawable.nigiri_sushi).into(Item_image);
        }else if (hb.equals("Maki Sushi")){
            Item_image.setImageResource(R.drawable.maki_sushi);
            //Picasso.get().load(R.drawable.maki_sushi).into(Item_image);
        }else if (hb.equals("Spezial Sashimi")){
            Item_image.setImageResource(R.drawable.spezial_sashimi);
            //Picasso.get().load(R.drawable.spezial_sashimi).into(Item_image);
        }else if (hb.equals("SUSHI AMARA Inside Out Maki")){
            Item_image.setImageResource(R.drawable.sushi_amara_inside_out_maki);
            //Picasso.get().load(R.drawable.sushi_amara_inside_out_maki).into(Item_image);
        }else if (hb.equals("Spezial Futo Maki")){
            Item_image.setImageResource(R.drawable.spezial_futo_maki);
            //Picasso.get().load(R.drawable.spezial_futo_maki).into(Item_image);
        }else if (hb.equals("Temaki Handrolle")){
            Item_image.setImageResource(R.drawable.temaki_handrolle);
            //Picasso.get().load(R.drawable.temaki_handrolle).into(Item_image);
        }else if (hb.equals("Warme Gerichte")){
            Item_image.setImageResource(R.drawable.warme_gerichte);
            //Picasso.get().load(R.drawable.warme_gerichte).into(Item_image);
        }else if (hb.equals("Spezial Gunkan")){
            Item_image.setImageResource(R.drawable.spezial_gunkan);
            //Picasso.get().load(R.drawable.spezial_gunkan).into(Item_image);
        }else if (hb.equals("Japanische Nudeln")){
            Item_image.setImageResource(R.drawable.japanische_nudeln);
            //Picasso.get().load(R.drawable.japanische_nudeln).into(Item_image);
        }else if (hb.equals("Spezial Gebratener Reis")){
            Item_image.setImageResource(R.drawable.spezial_gebratener_reis);
            //Picasso.get().load(R.drawable.spezial_gebratener_reis).into(Item_image);
        }else if (hb.equals("Einzelbestellungen")){
            Item_image.setImageResource(R.drawable.einzelbestellungen);
            //Picasso.get().load(R.drawable.einzelbestellungen).into(Item_image);
        }else {
            Item_image.setImageResource(R.drawable.default_image);
            //Picasso.get().load(R.drawable.default_image).into(Item_image);
        }
    }

    @Override
    public void onFooodCurt(AddFood addFood) {
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

    private void addToChart2(final AddFood addFood) {
        //Dialog open
        mydialog.setContentView(R.layout.custom_addtocurt_flaver);
        CardView txtcncNote=(CardView) mydialog.findViewById(R.id.txtclose2f);
        TextView txtclose =(TextView) mydialog.findViewById(R.id.txtclosef);
        TextView txtname =(TextView) mydialog.findViewById(R.id.FoodETNAmef);
        TextView txtsub =(TextView) mydialog.findViewById(R.id.FoodETSubf);
        TextView txtAllergnes =(TextView) mydialog.findViewById(R.id.FoodAllergensf);
        TextView heading =(TextView) mydialog.findViewById(R.id.heading);
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
            heading.setText("Lachs oder Thunfisch");
            Lachs.setText("Lachs (0,00€)");
            Hühnchen.setText("Thunfisch (0,00€)");
            nFpri = Fpri;
        }else if (addFood.getFoodNumber().equals("553")){
            heading.setText("Lachs oder Thunfisch");
            Lachs.setText("Lachs (0,00€)");
            Hühnchen.setText("Thunfisch (0,00€)");
            nFpri = Fpri;
        }else if (addFood.getFoodNumber().equals("254")){
            heading.setText("");
            Lachs.setText("chicken");
            Hühnchen.setText("fisch");
            nFpri = Fpri;
        }else if (addFood.getFoodNumber().equals("650")){
            heading.setText("Ei oder ohne Ei");
            Lachs.setText("Ei (1,50€)");
            Hühnchen.setText("ohne Ei (0,00€)");
            nFpri = Fpri+1.50;
        }else if (addFood.getFoodNumber().equals("700")){
            heading.setText("Ei oder ohne Ei");
            Lachs.setText("Ei (1,50€)");
            Hühnchen.setText("ohne Ei (0,00€)");
            nFpri = Fpri+1.50;
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
                if (addFood.getFoodNumber().equals("49")||addFood.getFoodNumber().equals("50")){
                    treffen = "Nudeln";
                }else if (addFood.getFoodNumber().equals("113")||addFood.getFoodNumber().equals("553")){
                    treffen = "Lachs";
                }else if (addFood.getFoodNumber().equals("650")||addFood.getFoodNumber().equals("700")){
                    treffen = "Ei";
                }else if (addFood.getFoodNumber().equals("254")){
                    treffen = "chicken";
                }
                else {
                    treffen = "Lachs";
                }
                SeleFoodPrice = String.format("%.2f", nFpri);
                txtprice.setText(SeleFoodPrice);

            }
        });
        Hühnchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addFood.getFoodNumber().equals("49")||addFood.getFoodNumber().equals("50")){
                    treffen = "Reis";
                }else if (addFood.getFoodNumber().equals("113")||addFood.getFoodNumber().equals("553")){
                    treffen = "Lachs";
                }else if (addFood.getFoodNumber().equals("650")||addFood.getFoodNumber().equals("700")){
                    treffen = "ohne Ei";
                }else if (addFood.getFoodNumber().equals("254")){
                    treffen = "fisch";
                }
                else {
                    treffen = "Hühnchen";
                }
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
            txtAllergnes.setText("Allergens: Noch nicht hinzugefügt..!");
        }


        CardView confirm = mydialog.findViewById(R.id.cartf);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (treffen==null){
                    Toast.makeText(FoodActivity.this, "!Sie müssen noch mindestens 1 Auswahlmöglichkeiten treffen!", Toast.LENGTH_SHORT).show();
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

    private void openDialog2(AddFood addFood) {

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

    private void addToChart(final AddFood addFood) {

        //Dialog open
        mydialog.setContentView(R.layout.custom_addtocurt);
        CardView txtcncNote=(CardView) mydialog.findViewById(R.id.txtclose2);
        TextView txtclose =(TextView) mydialog.findViewById(R.id.txtclose);
        TextView txtname =(TextView) mydialog.findViewById(R.id.FoodETNAme);
        TextView txtsub =(TextView) mydialog.findViewById(R.id.FoodETSub);
        TextView txtAllergnes =(TextView) mydialog.findViewById(R.id.FoodAllergens);
        TextView txtprice =(TextView) mydialog.findViewById(R.id.foodPrice);
        ImageView imageView =(ImageView) mydialog.findViewById(R.id.food_curt_image);
        txtnote =(EditText) mydialog.findViewById(R.id.etNote);
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

        SeleFoodPrice = addFood.getFoodPrice();
        txtname.setText(addFood.getFoodName());
        txtsub.setText(addFood.getFoodDescription());
        txtprice.setText(SeleFoodPrice);

        if (addFood.getAllergens() != null
                && !TextUtils.isEmpty(addFood.getAllergens())) {
            txtAllergnes.setText("Allergnes: "+addFood.getAllergens());
        }
        else {
            txtAllergnes.setText("Allergens: Noch nicht hinzugefügt..!");
        }

        if (addFood.getImageUrl() != null
                && !TextUtils.isEmpty(addFood.getImageUrl())) {

            Picasso.get().load(addFood.getImageUrl()).into(imageView);
        }

        CardView confirm = mydialog.findViewById(R.id.cart);
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

    private void openDialog(AddFood addFood) {

        Note = txtnote.getText().toString();


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
                double price = Double.parseDouble(SeleFoodPrice);
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
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void gotoNext() {

        String id = "PUSH ID";
        totalPrice = 0;
        for (OrderChart c: orderCharts){
            double price = Double.parseDouble(c.getProductPrice());
            totalPrice += price;
        }
        String includeVatPrice = String.valueOf((totalPrice+(totalPrice*15.0)/100.0));
        ConfirmOrderMain cd = new ConfirmOrderMain(id, orderCharts, location,System, String.valueOf(totalPrice),includeVatPrice,store,"null","local","null","null","null","null","null","0.0","0.0");
        Intent intent = new Intent(new Intent(FoodActivity.this,FinalCart.class));
        Common.orderCharts = orderCharts;
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
                    Toast.makeText(FoodActivity.this, "Bitte wählen Sie Speichern", Toast.LENGTH_SHORT).show();
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

    }


    public void pickup2(View view) {

        if (store==null){
            Toast.makeText(FoodActivity.this, "Bitte wählen Sie Speichern", Toast.LENGTH_SHORT).show();
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
            ConfirmOrderMain cd = new ConfirmOrderMain(id, orderCharts, location,System, String.valueOf(totalPrice),includeVatPrice,store,"null","local","null","null","null","null","null","0.0","0.0");
            Intent intent = new Intent(new Intent(FoodActivity.this,FinalCart.class));
            intent.putExtra("curt",cd);
            intent.putExtra("store", store);
            intent.putExtra("get", System);
            intent.putExtra("address", location);
            startActivity(intent);
            finish();
        }
    }
}
