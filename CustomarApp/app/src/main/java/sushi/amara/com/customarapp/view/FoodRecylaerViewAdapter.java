package sushi.amara.com.customarapp.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.AddFood;

public class FoodRecylaerViewAdapter extends RecyclerView.Adapter<FoodRecylaerViewAdapter.MyViewHolder> {


    private Context context;
    private List<AddFood> addFoods;
    private List<AddFood> items;

    private FoodListiner listener;

    public FoodRecylaerViewAdapter(Context context, List<AddFood> addFoods) {
        this.context = context;
        this.addFoods = addFoods;
        listener = (FoodListiner) context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView FoodName, FoodDis,FoodPrice,FoodNumber;


        CardView getDetailsLayout;


        public MyViewHolder(View view) {
            super(view);

            getDetailsLayout = itemView.findViewById(R.id.curt_master3);

            FoodName = (TextView) view.findViewById(R.id.food_name3);
            FoodDis = (TextView) view.findViewById(R.id.food_dis3);
            FoodPrice = (TextView) view.findViewById(R.id.food_price3);
            FoodNumber = (TextView) view.findViewById(R.id.food_number3);


        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item3, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final AddFood addFood = addFoods.get(position);

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

        }else {
            holder.FoodName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
        }


        holder.getDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFooodCurt(addFood);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addFoods.size();
    }

    public interface FoodListiner{
        void onFooodCurt(AddFood addFood);
    }

    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                addFoods = (List<AddFood>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<AddFood> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = items;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<AddFood> getFilteredResults(String constraint) {
        List<AddFood> results = new ArrayList<>();

        for (AddFood item : items) {
            if (item.getFoodName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }





}
