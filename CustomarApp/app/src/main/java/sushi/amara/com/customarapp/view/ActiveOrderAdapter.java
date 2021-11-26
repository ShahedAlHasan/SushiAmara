package sushi.amara.com.customarapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.ConfirmOrderMain;


public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.MyViewHolder>{


    private Context context;
    private List<ConfirmOrderMain> confirmOrders;

    private OnGetListener onGet;

    public ActiveOrderAdapter(Context context, List<ConfirmOrderMain> confirmOrders) {
        this.context = context;
        this.confirmOrders = confirmOrders;
        onGet = (OnGetListener) context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView location, type,Price,SMS;

        LinearLayout getDetailsLayout,TypeBack;


        public MyViewHolder(View view) {
            super(view);

            getDetailsLayout = itemView.findViewById(R.id.card);
            TypeBack = itemView.findViewById(R.id.type_bac);

            location = (TextView) view.findViewById(R.id.location_show2);
            type = (TextView) view.findViewById(R.id.typeText);
            Price = (TextView) view.findViewById(R.id.typePrice);
            SMS = (TextView) view.findViewById(R.id.sms_show);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final ConfirmOrderMain confirmOrder = confirmOrders.get(position);

        holder.location.setText(confirmOrder.getLocation());
        holder.type.setText(confirmOrder.getStore());
        holder.SMS.setText(confirmOrder.getSystem());
        holder.Price.setText((new DecimalFormat("##.##").format(Double.parseDouble(confirmOrder.getIncludeVatTotalPrice())))+" € ");


//        if (confirmOrder.getSystem().equals("Delivery")){
//            holder.TypeBack.setBackgroundColor(Color.parseColor("#00ACC1"));
//        }
//        else if(confirmOrder.getSystem().equals("InHouse")){
//            holder.TypeBack.setBackgroundColor(Color.parseColor("#FF0000"));
//        }
//        else {
//            holder.TypeBack.setBackgroundColor(Color.parseColor("#43A047"));
//        }

        holder.getDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onGet.onGet(confirmOrder,position+1);

            }
        });


    }

    @Override
    public int getItemCount() {
        return confirmOrders.size();
    }



    public interface OnGetListener{

        void onGet(ConfirmOrderMain confirmOrder, int position);
    }


}
