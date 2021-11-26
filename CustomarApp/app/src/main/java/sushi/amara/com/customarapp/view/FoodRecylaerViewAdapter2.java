package sushi.amara.com.customarapp.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.AddFood2;

public class FoodRecylaerViewAdapter2 extends RecyclerView.Adapter<FoodRecylaerViewAdapter2.MyViewHolder> {


    private Context context;
    private List<AddFood2> addFoods;

    private FoodListiner2 listener;

    public FoodRecylaerViewAdapter2(Context context, List<AddFood2> addFoods) {
        this.context = context;
        this.addFoods = addFoods;
        listener = (FoodListiner2) context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView FoodName, FoodDis,FoodPrice,FoodNumber;
        public ImageView imageView;

        CardView getDetailsLayout;


        public MyViewHolder(View view) {
            super(view);

            getDetailsLayout = itemView.findViewById(R.id.curt_master2);

            FoodName = (TextView) view.findViewById(R.id.food_name2);
            FoodDis = (TextView) view.findViewById(R.id.food_dis2);
            FoodPrice = (TextView) view.findViewById(R.id.food_price2);
            imageView = view.findViewById(R.id.food_curt_imageShow);
            FoodNumber = (TextView) view.findViewById(R.id.food_number);

        }
    }
    @NonNull
    @Override
    public FoodRecylaerViewAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item2, parent, false);

        return new FoodRecylaerViewAdapter2.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRecylaerViewAdapter2.MyViewHolder holder, int position) {

        final AddFood2 addFood = addFoods.get(position);


        holder.FoodName.setText(addFood.getFoodName());
        holder.FoodDis.setText(addFood.getFoodDescription());
        holder.FoodPrice.setText(addFood.getFoodPrice());
        holder.FoodNumber.setText(addFood.getFoodNumber());


        if (addFood.getType() != null
                && !TextUtils.isEmpty(addFood.getType())) {
            if(addFood.getType().equals("chilli")){
                holder.FoodName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.chili, 0);
            }
            else if (addFood.getType().equals("vegetable")){
                holder.FoodName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.lettuce, 0);
            }
            else {
                holder.FoodName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
            }

        }
        else {
            holder.FoodName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
        }


        if (addFood.getImageUrl() != null
                && !TextUtils.isEmpty(addFood.getImageUrl())) {
            Picasso.get().load(addFood.getImageUrl()).into(holder.imageView);
        }else {
            Picasso.get().load(R.drawable.food2).into(holder.imageView);
        }

        holder.getDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFooodCurt2(addFood);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addFoods.size();
    }



    public interface FoodListiner2{
        void onFooodCurt2(AddFood2 addFood);
    }
}
