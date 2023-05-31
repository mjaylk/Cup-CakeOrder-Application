package com.blackcode.cupcakefactory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class TodayAdapter extends RecyclerView.Adapter<RecentItemsViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public TodayAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecentItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_items, parent, false);
        return new RecentItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentItemsViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recentImage);
        holder.recentTitle.setText(dataList.get(position).getDataTitle());
        holder.recentDesc.setText(dataList.get(position).getDataDesc());
        holder.recentPrice.setText(dataList.get(position).getDataPrice());

        holder.recentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Price", dataList.get(holder.getAdapterPosition()).getDataPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



    public void searchDataList(ArrayList<DataClass> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class RecentItemsViewHolder extends RecyclerView.ViewHolder{

    ImageView recentImage;
    TextView recentTitle, recentDesc, recentPrice;
    CardView recentCard;

    public RecentItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        recentImage = itemView.findViewById(R.id.todayImage);
        recentCard = itemView.findViewById(R.id.todayCard);
        recentDesc = itemView.findViewById(R.id.todayDesc);
        recentTitle = itemView.findViewById(R.id.todayTitle);
        recentPrice = itemView.findViewById(R.id.todayPrice);
    }
}
