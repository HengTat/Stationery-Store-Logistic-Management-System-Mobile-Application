package iss.workshop.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import iss.workshop.myapplication.Model.DisbursementModel;

public class DisbursementList extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    String SERVER_URL_GET = "http://10.0.2.2:5000/api/disbursementclerkAPI/GetAllDisbursements/";
    ArrayList<DisbursementModel> disbursementsList = new ArrayList<>();
    TextView dataSelected;
    String dateToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_list);

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //Date Selection
        findViewById(R.id.dateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        dataSelected = (TextView) findViewById(R.id.datePicked);
        //get data
        JsonArrayRequest request = new JsonArrayRequest(
                SERVER_URL_GET,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            ArrayList<DisbursementModel> objects = mapper.readValue(String.valueOf(response), new TypeReference<ArrayList<DisbursementModel>>() {
                            });
                            disbursementsList = objects;
/*                            for (Disbursement dis : objects){
                                String date = sdf.format(dis.DisbursedDate);
                                System.out.println(date);
                                if(date.equals(dateToShow)){
                                    disbursementsList.add(dis);
                                }
                            }*/

                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                        //set adapter
                        DisbursementAdapter disAdapter = new DisbursementAdapter(getApplicationContext(), disbursementsList);
                        ListView disList = (ListView) findViewById(R.id.main_list);

                        if (disList != null) {
                            disList.setAdapter(disAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(DisbursementList.this);
        requestQueue.add(request);

    }

    //Date Picker
    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    //Date display
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
/*        Date shownDate = new Date(year, month, dayOfMonth);
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateToShow = sfd.format(shownDate);*/
        /*        selectedDate = year + "-" + (month+1) + "-" + dayOfMonth;*/
        /*        dataSelected.setText(selectedDate);*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        dateToShow = format.format(calendar.getTime());
        dataSelected.setText(dateToShow);
    }

}
