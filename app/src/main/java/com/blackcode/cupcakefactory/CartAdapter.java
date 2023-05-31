package com.blackcode.cupcakefactory;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> cartList;
    private Context context;

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_items, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.itemName.setText(cart.getItemName());
        holder.itemCount.setText(cart.getItemCount());
        holder.itemTotal.setText(cart.getItemTotal());

        // Load the image into the ImageView using Glide
        Glide.with(holder.itemImage.getContext())
                .load(cart.getItemImage())
                .placeholder(R.drawable.cakeimg1)
                .error(R.drawable.debit_card)
                .into(holder.itemImage);


        holder.cartDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the position of the item that needs to be deleted
                int pos = holder.getAdapterPosition();
                Cart cart = cartList.get(pos);
                cartList.remove(pos);
                CartDB cartDB = new CartDB(context);
                cartDB.deleteItem(cart.getItemName());
                notifyItemRemoved(pos);
            }

        });

        holder.cartCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", cartList.get(holder.getAdapterPosition()).getItemImage());
                intent.putExtra("Description", cartList.get(holder.getAdapterPosition()).getItemDesc());
                intent.putExtra("Title", cartList.get(holder.getAdapterPosition()).getItemName());
                intent.putExtra("cartItem",cartList.get(holder.getAdapterPosition()).getItemCount());//how many items
                intent.putExtra("total",cartList.get(holder.getAdapterPosition()).getItemTotal());//total price for the items
                String total = cart.getItemTotal();
                String totalItems = cartList.get(holder.getAdapterPosition()).getItemCount();
                String getSinglePrice = cartList.get(holder.getAdapterPosition()).getSinglePrice()+" LKR";
                intent.putExtra("Price",getSinglePrice); //single item price
                context.startActivity(intent);

            }
        });

        holder.buyOneCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CheckOut.class);
                intent.putExtra("itemCount", cartList.get(holder.getAdapterPosition()).getItemCount());
                intent.putExtra("itemPrice", cartList.get(holder.getAdapterPosition()).getItemTotal());
                intent.putExtra("singlePrice", cartList.get(holder.getAdapterPosition()).getItemTotal());
                intent.putExtra("title",cartList.get(holder.getAdapterPosition()).getItemName());
                intent.putExtra("image", cartList.get(holder.getAdapterPosition()).getItemImage());
                String RESULT = "OK";
                intent.putExtra("RESULT",RESULT );
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemCount, itemTotal,buyOneCart;
        public ImageView itemImage,cartDel;
        public CardView cartCard;
        public  LinearLayout singleLayout;

        public CartViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.cartItemTitle);
            itemCount = view.findViewById(R.id.cartItemCount);
            itemTotal = view.findViewById(R.id.cartPrice);
            itemImage = view.findViewById(R.id.cartImage);
            cartCard = view.findViewById(R.id.cartCard);
            cartDel = view.findViewById(R.id.cartDel);
            buyOneCart = view.findViewById(R.id.buyOneCart);
        }
    }
}
