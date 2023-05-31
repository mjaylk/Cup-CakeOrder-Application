package com.blackcode.cupcakefactory;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.popImage);
        holder.popTitle.setText(dataList.get(position).getDataTitle());
        holder.popDesc.setText(dataList.get(position).getDataDesc());
        holder.popPrice.setText(dataList.get(position).getDataPrice());

        holder.popCard.setOnClickListener(new View.OnClickListener() {
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

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView popImage;
    TextView popTitle, popDesc, popPrice;
    CardView popCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        popImage = itemView.findViewById(R.id.popImage);
        popCard = itemView.findViewById(R.id.popCard);
        popDesc = itemView.findViewById(R.id.popDesc);
        popTitle = itemView.findViewById(R.id.popTitle);
        popPrice = itemView.findViewById(R.id.popPrice);
    }
}