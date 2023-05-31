package com.blackcode.cupcakefactory;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.TextView;

import android.view.MotionEvent;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;

public class DetailActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    TextView itemDesc, itemTitle, itemPrice, itemHead, buyNow, addCart, buyText, cartText,buyAll,addAll,total;
    ImageView itemlImage,cancelButton,backButton,cartButton, addItem, removeItem,favourite;
    String key = "";
    String imageUrl = "";
    String allIt = "";
    EditText numberTxt;
    GestureDetector gestureDetector;
    FrameLayout bottomSheetContent;
    private int[] itemCount;
    private double[] itemCountPrice;
    private CartDB dbHelper;
    ImageView cartDel;


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemDesc = findViewById(R.id.itemDesc);
        itemlImage = findViewById(R.id.itemImage);
        itemHead = findViewById(R.id.itemHead);
        itemPrice = findViewById(R.id.itemPrice);
        itemTitle = findViewById(R.id.itemTitle);
        buyNow = findViewById(R.id.buyNow);
        cancelButton = findViewById(R.id.cancelButton);
        bottomSheetContent = findViewById(R.id.bottomSheetContent);
        backButton = findViewById(R.id.backButton);
        cartButton = findViewById(R.id.cartButton);
        addCart = findViewById(R.id.addCart);
        buyText = findViewById(R.id.buyText);
        cartText = findViewById(R.id.cartHeadText);
        buyAll = findViewById(R.id.buyAll);
        addAll = findViewById(R.id.addAll);
        addItem = findViewById(R.id.addItem);
        removeItem = findViewById(R.id.removeItem);
        numberTxt = findViewById(R.id.numberTxt);
        total = findViewById(R.id.total);
        favourite = findViewById(R.id.favourite);
        addAll = findViewById(R.id.addAll);




        gestureDetector = new GestureDetector(this, this);
        bottomSheetContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        Bundle bundle = getIntent().getExtras();
        boolean notNul =false;
        if (bundle != null){


            itemDesc.setText(bundle.getString("Description"));
            itemHead.setText(bundle.getString("Title"));
            itemTitle.setText(bundle.getString("Title"));
            itemPrice.setText(bundle.getString("Price"));//single item price
            total.setText(bundle.getString("Price"));
            key = bundle.getString("Key");
            allIt = bundle.getString("cartItem");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(imageUrl).into(itemlImage);
             notNul =true;
        }

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetContent.setVisibility(View.VISIBLE);
                buyText.setVisibility(View.VISIBLE);
                buyAll.setVisibility(View.VISIBLE);
                cartText.setVisibility(View.GONE);
                addAll.setVisibility(View.GONE);
            }
        });

        if(notNul) {
            if(notNul) {


                final String finalPrice = bundle.getString("Price");
                final String[] itemCountPrice = {finalPrice};
                final int[] itemCount = {1};

                if(allIt == null && total != null) {
                    numberTxt.setText(String.valueOf("1"));
                    total.setText(bundle.getString("Price")); // all item price
                    String test1 = bundle.getString("Price");

                }else{
                    numberTxt.setText(String.valueOf(allIt));
                    total.setText(bundle.getString("total")); // all item price

                }

                addItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int count = Integer.parseInt(numberTxt.getText().toString());
                            if (count < 50000) {
                                count++;

                                numberTxt.setText(String.valueOf(count));

                                String finalPrice = itemPrice.getText().toString().replace("LKR", "").trim();
                                updateItemPrice(finalPrice, count);
                            } else {
                                Toast.makeText(DetailActivity.this, "You can only add 50000 items", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(DetailActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                buyAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(DetailActivity.this, CheckOut.class);

                        String sendItem = numberTxt.getText().toString();
                        String sendTotal = total.getText().toString();

                        intent.putExtra("itemCount", sendItem);
                        intent.putExtra("itemPrice", sendTotal);
                        intent.putExtra("price", sendItem);
                        intent.putExtra("desc", itemDesc.getText().toString());
                        intent.putExtra("title", itemTitle.getText().toString());
                        intent.putExtra("image", imageUrl);
                        String FROM = "DetailActivity";
                        intent.putExtra("FROM",FROM);
                        String RESULT = "OK";
                        intent.putExtra("RESULT",RESULT );

                        bottomSheetContent.setVisibility(View.GONE);
                        cartText.setVisibility(View.GONE);
                        addAll.setVisibility(View.GONE);
                        buyText.setVisibility(View.GONE);
                        buyAll.setVisibility(View.GONE);
                        startActivity(intent);
                    }
                });


                numberTxt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String finalPrice = itemPrice.getText().toString().replace("LKR", "").trim();
                        if (!TextUtils.isEmpty(s)) {
                            String inputStr = s.toString().trim();
                            if (inputStr.matches("\\d+")) {
                                try {
                                    int countValue = Integer.parseInt(inputStr);
                                    updateItemPrice(finalPrice, countValue);
                                } catch (NumberFormatException e) {
                                    Toast.makeText(DetailActivity.this, "Try to Contact Us", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                            }
                        } else {
                            updateItemPrice(finalPrice, 1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }


        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int count = Integer.parseInt(numberTxt.getText().toString());
                    count--;
                    if (count < 1) {

                    } else {
                        numberTxt.setText(String.valueOf(count));
                        String finalPrice = itemPrice.getText().toString().replace("LKR", "").trim();
                        updateItemPrice(finalPrice, count);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(DetailActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });


        dbHelper = new CartDB(this);


        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetContent.setVisibility(View.VISIBLE);
                cartText.setVisibility(View.VISIBLE);
                addAll.setVisibility(View.VISIBLE);
                buyText.setVisibility(View.GONE);
                buyAll.setVisibility(View.GONE);

            }
        });

        addAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String addItemsStr = numberTxt.getText().toString(); // all item counts
                int addItems = Integer.parseInt(addItemsStr);

                String addTotStr = total.getText().toString().replaceAll("[^\\d.]+","");
                if(addTotStr.isEmpty()) {
                    addTotStr = "1";
                }
                int addTot = (int) Double.parseDouble(addTotStr);

                int getSinglePrice = addTot / addItems;
                String singlePrice = String.valueOf(getSinglePrice);

                String addTotals = addTotStr + " LKR";
                String itemsName = itemTitle.getText().toString();
                String itemsDesc = itemDesc.getText().toString();

                Log.d(TAG, "addData: Adding " + singlePrice );
                imageUrl = bundle.getString("Image");
                CartDB cartDB = new CartDB(DetailActivity.this);
                long result = cartDB.addCarts(addItemsStr, addTotals, itemsName, itemsDesc, imageUrl, singlePrice);

                if (result > -1) {
                    Toast.makeText(DetailActivity.this, "Added to the Cart", Toast.LENGTH_SHORT).show();
                    bottomSheetContent.setVisibility(View.GONE);
                    buyText.setVisibility(View.GONE);
                    buyAll.setVisibility(View.GONE);
                    cartText.setVisibility(View.GONE);
                    addAll.setVisibility(View.GONE);
                } else {
                    Toast.makeText(DetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


        final boolean[] isFavourite = {false};


        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavourite[0]) {
                    favourite.setImageResource(R.drawable.ic_like);
                } else {
                    favourite.setImageResource(R.drawable.ic_like_after);
                }
                isFavourite[0] = !isFavourite[0];
            }
        });




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetContent.setVisibility(View.GONE);
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // call this to close the current activity
            }
        });


        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                intent.putExtra("fragment", "cart");
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getY() < e2.getY()) { // Swipe down
            bottomSheetContent.setVisibility(View.GONE);
        }
        return true;
    }

    private void updateItemPrice(String price, int count) {
        double itemPrice = Double.parseDouble(price);
        double totalPrice = itemPrice * count;
        String formattedPrice;
        if (count <= 50000) {
            formattedPrice = String.format(" %.2f"+" LKR", totalPrice);
        } else {
            formattedPrice = "LKR -";
            Toast.makeText(DetailActivity.this, "50000 is the Limit", Toast.LENGTH_SHORT).show();
        }
        total.setText(formattedPrice);
    }


}