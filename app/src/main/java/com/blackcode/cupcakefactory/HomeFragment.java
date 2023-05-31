package com.blackcode.cupcakefactory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView,todayRv,allRv;
    List<DataClass> dataList,todayList,allList;
    MyAdapter adapter;
    TodayAdapter adapter2;
    AllAdapter adapter3;
    SearchView searchView;
    DatabaseReference db,db2;
    ImageView profileImageHome;
    TextView txtHello;
    private String ImageUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.popularRv);
        todayRv = view.findViewById(R.id.todayRv);
        allRv = view.findViewById(R.id.allRv);
        profileImageHome = view.findViewById(R.id.profileImageHome);
        txtHello = view.findViewById(R.id.txtHello);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();
            db = FirebaseDatabase.getInstance().getReference("users").child(userId);

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (isAdded() && dataSnapshot.exists()) {
                        User userHome = dataSnapshot.getValue(User.class);
                        ImageUrl = userHome.getProfileImageUrl();

                        String textWithHello = "Hi "+ userHome.getName();
                        txtHello.setText(textWithHello);

                        if (ImageUrl != null) {
                            Glide.with(getContext()).load(ImageUrl)
                                    .placeholder(R.drawable.proi)
                                    .error(R.drawable.proi)
                                    .into(profileImageHome);
                        }
                    } else {
                        Log.d("TAG", "User not found");
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("TAG", "Database error: " + error.getMessage());
                }
            });
        } else {
            Log.d("TAG", "User not logged in");
        }









        todayRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        allRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));

        searchView = view.findViewById(R.id.search);
        searchView.clearFocus();

        searchView = view.findViewById(R.id.search);
        int searchHintColor = getResources().getColor(R.color.gray);
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setHintTextColor(searchHintColor);

        int searchTextColor = getResources().getColor(R.color.black);
        searchEditText.setTextColor(searchTextColor);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();
        todayList = new ArrayList<>();
        allList = new ArrayList<>();

        adapter2 =new TodayAdapter(getContext(), todayList);
        adapter = new MyAdapter(getContext(), dataList);
        adapter3 = new AllAdapter(getContext(), allList);

        recyclerView.setAdapter(adapter);
        todayRv.setAdapter(adapter2);
        allRv.setAdapter(adapter3);



        databaseReference = FirebaseDatabase.getInstance().getReference("Popular Cakes");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);

                    dataClass.setKey(itemSnapshot.getKey());

                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });


        DatabaseReference todayCake = FirebaseDatabase.getInstance().getReference("Today Cakes");

        todayCake.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                todayList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);

                    dataClass.setKey(itemSnapshot.getKey());

                    todayList.add(dataClass);

                }
                adapter2.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });


        DatabaseReference allCake = FirebaseDatabase.getInstance().getReference("All Cakes");

        allCake.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    allList.add(dataClass);
                }
                adapter3.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        return view;
    }

    public void searchList(String text){
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass: dataList){
            if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }

        adapter.searchDataList(searchList);

        for (DataClass dataClass: todayList){
            if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter2.searchDataList(searchList);

        for (DataClass dataClass: allList){
            if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter3.searchDataList(searchList);
    }

    private void displayImageUrl(String imageUrl) {
        Toast.makeText(getContext(),"ABC" + imageUrl,Toast.LENGTH_SHORT).show();


    }


}