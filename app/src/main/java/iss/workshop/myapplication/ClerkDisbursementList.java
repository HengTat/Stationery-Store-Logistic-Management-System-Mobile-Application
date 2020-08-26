package iss.workshop.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import iss.workshop.myapplication.Model.DisbursementAPImodel;

public class ClerkDisbursementList extends AppCompatActivity {

    ListView listView;
    List<DisbursementAPImodel> listofDisbursements= new ArrayList<>();
    List<Integer> listofdisbursementid= new  ArrayList<Integer>();
    LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_disbursement_list);

        //(GET) Retrieve all disbursements
        String server_url="http://10.0.2.2:5000/api/disbursementclerkAPI/GetAllDisbursements";

        JsonArrayRequest request = new JsonArrayRequest
                (server_url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                ObjectMapper mapper = new ObjectMapper();
                                try {
                                    listofDisbursements = mapper.readValue(String.valueOf(response), new TypeReference<List<DisbursementAPImodel>>(){});
                                    for(DisbursementAPImodel i : listofDisbursements){
                                        listofdisbursementid.add(i.Id);
                                    }
                                    Intent intent= getIntent();
                                    String date=intent.getStringExtra("selecteddate");

                                    if (date!=null){
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        selectedDate=LocalDate.parse(date,formatter);
                                    }

                                    List<DisbursementAPImodel> Listofdis=new ArrayList<>();
                                    if (selectedDate !=null){
                                        for (int i=0;i<listofDisbursements.size();i++){
                                            LocalDate dateofdisbursement=listofDisbursements.get(i).getDisbursedDate().toInstant().atZone(ZoneId.systemDefault()).minusHours(8).toLocalDate();
                                            if (dateofdisbursement.equals(selectedDate)){
                                                Listofdis.add(listofDisbursements.get(i));
                                            }
                                        }
                                        listofDisbursements=Listofdis;
                                    }

                                    DisbursementListAdapter disbursementListAdapter= new DisbursementListAdapter(getApplicationContext(),listofDisbursements);
                                    listView=(ListView) findViewById(R.id.disbursement_List);
                                    ViewGroup header=(ViewGroup)getLayoutInflater().inflate(R.layout.disbursementlistheaderwithdatepicker,listView,false);
                                    listView.addHeaderView(header);
                                    TextView datedisplay= (TextView) header.findViewById(R.id.selecteddate);
                                    if (date==null){
                                        datedisplay.setText("All Dates");
                                    }
                                    else{
                                        datedisplay.setText(date);
                                    }
                                    Button pickdatebtn= (Button) header.findViewById(R.id.pickdatebtn);
                                    pickdatebtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(ClerkDisbursementList.this,DisbursementListSelectDate.class);
                                            startActivity(i);
                                            ((Activity)ClerkDisbursementList.this).finish();
                                        }
                                    });

                                    if(listofDisbursements!=null){
                                        listView.setAdapter(disbursementListAdapter);
                                    }

                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}