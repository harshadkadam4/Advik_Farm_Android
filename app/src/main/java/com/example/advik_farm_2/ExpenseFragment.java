package com.example.advik_farm_2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class ExpenseFragment extends Fragment {

    private TextView date;
    private TextInputEditText amount,description;
    private MaterialButton add;
    private AutoCompleteTextView select_expense;

    private ExpenseDB expenseDB;

    String exp_type="";

    public ExpenseFragment(){
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //to keep the list full visible while switching between fragments
        String[] list_options = getResources().getStringArray(R.array.list_options);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_list, list_options);
        select_expense.setAdapter(arrayAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate)
    {
        View view = inflater.inflate(R.layout.expense,container,false);

        date = view.findViewById(R.id.date);
        amount = view.findViewById(R.id.amount);
        description = view.findViewById(R.id.description);
        add = view.findViewById(R.id.add);
        select_expense = view.findViewById(R.id.select_expense);

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

        select_expense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                exp_type = parent.getItemAtPosition(position).toString();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected_date = date.getText().toString();
                String added_desc = description.getText().toString();
                String entered_amount_str = amount.getText().toString();

                if(added_desc.length() == 0)
                {
                    added_desc="NA";
                }

                if(validateDate(selected_date) && validateExpType(exp_type) && validateAmount(entered_amount_str) )
                {
                    int entered_amount = Integer.parseInt(entered_amount_str);
                    expenseDB = new ExpenseDB(getContext());
                    expenseDB.addExpense(selected_date, exp_type, entered_amount, added_desc);
                    Toast.makeText(getContext(), "Expense Added", Toast.LENGTH_SHORT).show();
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


    public boolean validateAmount(String entered_amount_str)
    {
        if(entered_amount_str.length() == 0){
            amount.setError("Enter Amount");
            return false;
        }
        else {
            amount.setError(null);
            return true;
        }
    }

    public boolean validateExpType(String exp_type)
    {
        if(exp_type.length() == 0){
            select_expense.setError("Select Expense Type");
            return false;
        }
        else {
            select_expense.setError(null);
            return true;
        }
    }

}
