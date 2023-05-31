package com.blackcode.cupcakefactory;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcode.cupcakefactory.Cart;
import com.blackcode.cupcakefactory.CartAdapter;
import com.blackcode.cupcakefactory.CartDB;
import com.blackcode.cupcakefactory.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Cart> cartList = new ArrayList<>();
    private CartDB cartDB;
    private TextView cartRemoveAll,cartBuyAll;
    private TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartDB = new CartDB(getActivity());
        cartList = cartDB.getCarts();

        adapter = new CartAdapter(cartList, getContext());

        recyclerView = view.findViewById(R.id.cartRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        cartRemoveAll = view.findViewById(R.id.cartRemoveAll);
        emptyView = view.findViewById(R.id.empty_text);
        cartBuyAll =view.findViewById(R.id.cartBuyAll);

        emptyView.setVisibility(View.VISIBLE);

        if(cartList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(getContext(),"Cart Is Empty",Toast.LENGTH_SHORT).show();
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        cartRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartDB.deleteAllItems();
                cartList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "All items removed from cart", Toast.LENGTH_SHORT).show();
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        });
        cartBuyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList.isEmpty()) {
                    Toast.makeText(getContext(),"Cart Is Empty",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), CheckOut.class);
                    Bundle bundle = new Bundle();
                    for (int i = 0; i < cartList.size(); i++) {
                        Cart cart = cartList.get(i);
                        intent.putExtra("itemCount" + i, cart.getItemCount());
                        intent.putExtra("itemPrice" + i, cart.getItemTotal());
                        intent.putExtra("singlePrice" + i, cart.getSinglePrice());
                        intent.putExtra("title" + i, cart.getItemName());
                        intent.putExtra("Images" + i, cart.getItemImage());
                    }

                    intent.putExtra("itemCountSize", cartList.size());
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}


