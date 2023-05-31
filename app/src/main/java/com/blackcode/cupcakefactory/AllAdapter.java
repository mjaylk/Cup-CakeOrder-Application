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

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllItemViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public AllAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public AllItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_cakes, parent, false);
        return new AllItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllItemViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.allImg);
        holder.allTitle.setText(dataList.get(position).getDataTitle());
        holder.allPrice.setText(dataList.get(position).getDataPrice());

        holder.allCard.setOnClickListener(new View.OnClickListener() {
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

    public void searchRecentDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }

    public void searchDataList(ArrayList<DataClass> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }

    static class AllItemViewHolder extends RecyclerView.ViewHolder{

        ImageView allImg;
        TextView allTitle, allDesc, allPrice;
        CardView allCard;

        public AllItemViewHolder(@NonNull View itemView) {
            super(itemView);

            allImg = itemView.findViewById(R.id.allImg);
            allCard = itemView.findViewById(R.id.allCard);
            allTitle = itemView.findViewById(R.id.allTitle);
            allPrice = itemView.findViewById(R.id.allPrice);
        }
    }
}
