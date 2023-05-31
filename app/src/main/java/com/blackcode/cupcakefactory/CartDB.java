package com.blackcode.cupcakefactory;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CartDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "carts";
    private static final String ID = "id";
    private static final String ITEM_NAMES = "itemsName";
    private static final String ITEMS_PRICE = "addTotals";
    private static final String ITEM_COUNTS = "addItems";
    private static final String ITEM_DESC = "itemsDesc";
    private static final String ITEM_IMAGE = "itemImage";
    private static final String SINGLE_PRICE ="singlePrice";


    public CartDB(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ITEM_NAMES + " TEXT,"
                + ITEMS_PRICE + " TEXT,"
                + ITEM_DESC + " TEXT,"
                + ITEM_COUNTS + " TEXT,"
                + ITEM_IMAGE + " TEXT,"
                + SINGLE_PRICE + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addCarts(String addItems, String addTotals, String itemsName, String itemsDesc, String imageUrl, String singlePrice) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_NAMES, itemsName);
        values.put(ITEM_COUNTS, addItems);
        values.put(ITEMS_PRICE, addTotals);
        values.put(ITEM_DESC, itemsDesc);
        values.put(ITEM_IMAGE, imageUrl);
        values.put(SINGLE_PRICE, singlePrice);

        Log.d(TAG, "addData: Adding " + itemsName + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result;
    }

    /**
     * Returns all the data from database
     * @return
     */

    public List<Cart> getCarts() {
        List<Cart> cartList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndex(ITEM_NAMES));
                String itemCount = cursor.getString(cursor.getColumnIndex(ITEM_COUNTS));
                String itemDesc = cursor.getString(cursor.getColumnIndex(ITEM_DESC));
                String itemTotal = cursor.getString(cursor.getColumnIndex(ITEMS_PRICE));
                String itemImage = cursor.getString(cursor.getColumnIndex(ITEM_IMAGE));
                String singlePrice = cursor.getString(cursor.getColumnIndex(SINGLE_PRICE));
                Cart cart = new Cart(itemName, itemCount, itemTotal, itemDesc, itemImage,singlePrice);
                cartList.add(cart);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cartList;
    }

    public void deleteItem(String itemName) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ITEM_NAMES + "=?", new String[] { itemName });
        db.close();
    }

    public void deleteAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
