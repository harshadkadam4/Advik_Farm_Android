package com.example.advik_farm_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "advikFarmDB";
    private static final int DB_VERSION = 1;


    public ExpenseDB( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE expense (exp_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, exp_type TEXT, amount INTEGER, descr TEXT);";
        db.execSQL(query);
    }

    public void addExpense(String date, String exp_type, int amount, String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("date", date);
        values.put("exp_type", exp_type);
        values.put("amount", amount);
        values.put("descr", description);

        db.insert("expense",null,values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS expense");
        onCreate(db);
    }
}
