package com.example.advik_farm_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;

// All DB Methods added here, to resolve issue of no such table

public class AllDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "advikFarmDB";
    private static final int DB_VERSION = 1;

    SQLiteDatabase db = this.getWritableDatabase();

    public AllDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    String query = "CREATE TABLE milk_add (date TEXT PRIMARY KEY, morning_lit REAL, night_lit REAL);";
    db.execSQL(query);

    String query1 = "CREATE TABLE expense (exp_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, exp_type TEXT, amount INTEGER, descr TEXT);";
    db.execSQL(query1);

    String query2 = "CREATE TABLE cow_fertility (date TEXT, cow_name TEXT, descr TEXT);";
    db.execSQL(query2);

    String query3 = "CREATE TABLE calf_details (birth_date TEXT, cow_name TEXT, descr TEXT);";
    db.execSQL(query3);

    }

    public void addMilk(String date, float liters, String rg_choice)
    {
        try
        {
            // Check if the record already exists
            String query = "SELECT COUNT(*) FROM milk_add WHERE date = ?";
            SQLiteStatement statement = db.compileStatement(query);
            statement.bindString(1,date);
            long count = statement.simpleQueryForLong();
            statement.close();

            // Format liters to 2 decimal places
            DecimalFormat df = new DecimalFormat("0.00");
            liters = Float.parseFloat(df.format(liters));

            ContentValues values = new ContentValues();

            if(count > 0)
            {
                // Update existing record
                if("morning".equals(rg_choice))
                {
                    values.put("morning_lit", liters);
                }
                else{
                    values.put("night_lit", liters);
                }
                db.update("milk_add", values, "date = ?",new String[]{date});
            }
            else {
                values.put("date",date);
                if("morning".equals(rg_choice))
                {
                    values.put("morning_lit",liters);
                    values.put("night_lit",0);
                }else {
                    values.put("morning_lit",0);
                    values.put("night_lit",liters);
                }
                db.insert("milk_add",null,values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(db != null && db.isOpen())
                db.close();
        }
    }

    public void addExpense(String date, String exp_type, int amount, String description) {

        ContentValues values = new ContentValues();

        values.put("date", date);
        values.put("exp_type", exp_type);
        values.put("amount", amount);
        values.put("descr", description);

        db.insert("expense",null,values);
        db.close();
    }

    public void addCowFertility(String date, String cow_name, String description) {
        ContentValues values = new ContentValues();

        values.put("date", date);
        values.put("cow_name", cow_name);
        values.put("descr", description);

        db.insert("cow_fertility",null,values);
        db.close();
    }


    public void addCalfDetails(String birth_date, String cow_name, String description) {
        ContentValues values = new ContentValues();

        values.put("birth_date", birth_date);
        values.put("cow_name", cow_name);
        values.put("descr", description);

        db.insert("calf_details",null,values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS milk_add");
        db.execSQL("DROP TABLE IF EXISTS expense");
        db.execSQL("DROP TABLE IF EXISTS cow_fertility");
        db.execSQL("DROP TABLE IF EXISTS calf_details");
        onCreate(db);
    }
}
