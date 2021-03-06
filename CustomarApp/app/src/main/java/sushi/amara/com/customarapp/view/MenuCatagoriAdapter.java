package sushi.amara.com.customarapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sushi.amara.com.customarapp.R;

public class MenuCatagoriAdapter extends RecyclerView.Adapter<MenuCatagoriAdapter.BookingListAdapterViewHolder> {

    private Context context;
    int row_index = 0;

    private List<String> bookingList;
    private CatagoriListAdapterListener listener;

    public MenuCatagoriAdapter(Context context, List<String> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
        listener = (CatagoriListAdapterListener) context;
    }

    @NonNull
    @Override
    public BookingListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingListAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_menu_catogory, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final BookingListAdapterViewHolder holder, final int position) {

        final String hb = bookingList.get(position);

        holder.NameTV.setText(hb);

        holder.bookingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
                listener.onCompleteBooking(hb);



            }
        });

        if (row_index==0){
            holder.bookingLayout.setBackgroundColor(Color.parseColor("#567845"));
            holder.NameTV.setTextColor(Color.parseColor("#ffffff"));
        }

        if(row_index == position){

            holder.bookingLayout.setBackgroundColor(Color.parseColor("#567845"));
            holder.NameTV.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.bookingLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.NameTV.setTextColor(Color.parseColor("#000000"));
        }


    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    class BookingListAdapterViewHolder extends RecyclerView.ViewHolder{


        CardView bookingLayout;
        TextView NameTV;

        public BookingListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            bookingLayout = itemView.findViewById(R.id.bookingList);
            NameTV = itemView.findViewById(R.id.name);


        }
    }

    public interface CatagoriListAdapterListener {

        void onCompleteBooking(String hb);
    }
}
