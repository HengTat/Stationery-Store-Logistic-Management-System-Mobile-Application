package iss.workshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;

public class DisbursementListSelectDate extends AppCompatActivity {
    DatePicker picker;
    Button confirmDatebtn;
    Button cancelDatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_list_select_date);
        confirmDatebtn=(Button) findViewById(R.id.confirmdatebtn);
        cancelDatebtn=(Button) findViewById(R.id.canceldatebtn);
        picker=(DatePicker) findViewById(R.id.datePicker1);
        confirmDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String selectedDate1=String.valueOf(LocalDate.of(picker.getYear(),picker.getMonth()+1,picker.getDayOfMonth()));

                Intent i = new Intent(DisbursementListSelectDate.this,ClerkDisbursementList.class);
                i.putExtra("selecteddate",selectedDate1);
                startActivity(i);
            }
        });
        cancelDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}