package com.example.advik_farm_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;

public class MilkAddDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "advikFarmDB";
    private static final int DB_VERSION = 1;

    public MilkAddDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String query = "CREATE TABLE milk_add (date TEXT PRIMARY KEY, morning_lit REAL, night_lit REAL);";
    db.execSQL(query);
    }

    public void addMilk(String date, float liters, String rg_choice)
    {
        SQLiteDatabase db=null;

        try
        {
            db = this.getWritableDatabase();

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS milk_add");
        onCreate(db);
    }
}
