package com.example.advik_farm_2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MilkAddFragment extends Fragment {

    TextView date;
    EditText liters;
    MaterialButton add;
    RadioGroup rg;
    String rg_choice = "morning";
    private MilkAddDB milkAddDB;

    public MilkAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate)
    {
        View view = inflater.inflate(R.layout.milk_add_frag,container,false);

        date = view.findViewById(R.id.date);
        liters = view.findViewById(R.id.liters);
        add = view.findViewById(R.id.add);
        rg = view.findViewById(R.id.radioGroup);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                            }
                        },year,month,day);

                datePickerDialog.show();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.morning)
                    rg_choice = "morning";
                else
                    rg_choice = "night";
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String literText = liters.getText().toString();
                String selected_date = date.getText().toString();

                if(validateLiters(literText) && validateDate(selected_date))
                {
                    float milk_liter  = Float.parseFloat(literText);

                    milkAddDB = new MilkAddDB(getContext());
                    milkAddDB.addMilk(selected_date,milk_liter,rg_choice);
                    Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    public boolean validateLiters(String literText)
    {
        if(literText.isEmpty())
        {
            liters.setError("Enter Milk");
            return false;
        }
        else {
            liters.setError(null);
            return true;
        }
    }

    public boolean validateDate(String edate)
    {
        if(edate.isEmpty())
        {
            date.setError("Enter Date");
            return false;
        }
        else {
            date.setError(null);
            return true;
        }
    }
}
