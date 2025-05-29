package com.example.advik_farm_2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CalfFragment extends Fragment {

    private TextView date;
    private TextInputEditText cow_name,description;
    private MaterialButton add;

    private AllDB calfDB;

    public CalfFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate)
    {
        View view = inflater.inflate(R.layout.calf_details,container,false);

        date = view.findViewById(R.id.date);
        cow_name = view.findViewById(R.id.cow_name);
        description = view.findViewById(R.id.description);
        add = view.findViewById(R.id.add);

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



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected_date = date.getText().toString();
                String added_desc = description.getText().toString();
                String entered_cow_name = cow_name.getText().toString();

                if(added_desc.length() == 0)
                {
                    added_desc="NA";
                }

                if(validateDate(selected_date) && validateCowName(entered_cow_name))
                {
                    calfDB = new AllDB(getContext());
                    calfDB.addCalfDetails(selected_date, entered_cow_name, added_desc);
                    Toast.makeText(getContext(), "Cow Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public boolean validateDate(String selected_date)
    {
        if(selected_date.length() == 0){
            date.setError("Select Date");
            return false;
        }
        else {
            date.setError(null);
            return true;
        }
    }


    public boolean validateCowName(String entered_cow_name)
    {
        if(entered_cow_name.length() == 0){
            cow_name.setError("Enter Cow Name");
            return false;
        }
        else {
            cow_name.setError(null);
            return true;
        }
    }

}
