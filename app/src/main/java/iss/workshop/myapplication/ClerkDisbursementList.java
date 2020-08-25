package iss.workshop.myapplication;

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
    List<DisbursementAPImodel> Listofdisbursements= new ArrayList<>();
    List<Integer> listofdisbursementid= new  ArrayList<Integer>();
    LocalDate selecteddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_disbursement_list);

        //httpget request to listofjavaobject(disbursement)
        //(GET) Retrieve all disbursements
        String server_url2="http://10.0.2.2:5000/api/disbursementclerkAPI/GetAllDisbursements";

        JsonArrayRequest request = new JsonArrayRequest
                (server_url2,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                ObjectMapper mapper = new ObjectMapper();
                                try {
                                    List<DisbursementAPImodel> objects = mapper.readValue(String.valueOf(response), new TypeReference<List<DisbursementAPImodel>>(){});
                                    Listofdisbursements=objects;
                                    for(DisbursementAPImodel i : Listofdisbursements){
                                        listofdisbursementid.add(i.Id);
                                    }
                                    Intent intent= getIntent();
                                    String date=intent.getStringExtra("selecteddate");

                                    if (date!=null){
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        selecteddate=LocalDate.parse(date,formatter);
                                    }

                                    List<DisbursementAPImodel> Listofdis=new ArrayList<>();
                                    if (selecteddate !=null){
                                        for (int i=0;i<Listofdisbursements.size();i++){
                                            LocalDate dateofdisbursement=Listofdisbursements.get(i).getDisbursedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                            if (dateofdisbursement.equals(selecteddate)){
                                                Listofdis.add(Listofdisbursements.get(i));
                                            }
                                        }
                                        Listofdisbursements=Listofdis;
                                    }

                                    DisbursementListAdapter disbursementListAdapter= new DisbursementListAdapter(getApplicationContext(),Listofdisbursements);
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
                                        }
                                    });

                                    if(Listofdisbursements!=null){
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