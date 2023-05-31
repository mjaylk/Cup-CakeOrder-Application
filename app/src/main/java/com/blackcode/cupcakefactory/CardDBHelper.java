package com.blackcode.cupcakefactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CardDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "card.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "cards";

    private static final String ID = "id";
    private static final String HOLDER_NAME = "holder_name";
    private static final String CARD_NUMBER = "card_number";
    private static final String YEAR = "year";

    public CardDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HOLDER_NAME + " TEXT,"
                + CARD_NUMBER + " TEXT,"
                + YEAR + " TEXT"
                + ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertCard(String holderName, String cardNumber, String year) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HOLDER_NAME, holderName);
        values.put(CARD_NUMBER, cardNumber);
        values.put(YEAR, year);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result;
    }

    public Card getCard() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            String holderName = cursor.getString(cursor.getColumnIndex(HOLDER_NAME));
            String cardNumber = cursor.getString(cursor.getColumnIndex(CARD_NUMBER));
            String year = cursor.getString(cursor.getColumnIndex(YEAR));

            Card card = new Card(holderName, cardNumber, year);
            cursor.close();
            return card;
        }


        return null;
    }



}
