package iss.workshop.myapplication;

import android.os.Bundle;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import iss.workshop.myapplication.Model.DisbursementDetailAPImodel;

public class ClerkDisbursementDetail extends AppCompatActivity {
    ListView listView;
    List<DisbursementDetailAPImodel> listofDisbursementDetails= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_disbursement_detail);

        final String department= getIntent().getStringExtra("Department");
        final String collectionPoint= String.valueOf(getIntent().getIntExtra("CollectionPoint",0));
        final String disbursementId = String.valueOf(getIntent().getIntExtra("DisbursementId", 0));

        //(GET) Retrieve all disbursementdetails using disbursementid
        String server_url="http://10.0.2.2:5000/api/disbursementclerkAPI/Getdisbursementdetails/"+disbursementId;

        JsonArrayRequest request = new JsonArrayRequest
                (server_url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                ObjectMapper mapper = new ObjectMapper();
                                try {
                                    List<DisbursementDetailAPImodel> objects = mapper.readValue(String.valueOf(response), new TypeReference<List<DisbursementDetailAPImodel>>(){});
                                    listofDisbursementDetails=objects;
                                    DisbursementDetailAdapter disbursementdetailAdapter= new DisbursementDetailAdapter(getApplicationContext(),listofDisbursementDetails);
                                    listView=(ListView) findViewById(R.id.disbursementdetail_List);

                                    ViewGroup header=(ViewGroup)getLayoutInflater().inflate(R.layout.disbursementdetailheader,listView,false);
                                    TextView IDdisplay= (TextView) header.findViewById(R.id.disbursementidbox) ;
                                    if(IDdisplay!=null){
                                        IDdisplay.setText(disbursementId);
                                    }

                                    TextView Departmentdisplay= (TextView) header.findViewById(R.id.departmentbox) ;
                                    if(IDdisplay!=null){
                                       Departmentdisplay.setText(department);
                                    }
                                    TextView CollectionPointdisplay= (TextView) header.findViewById(R.id.collectionpointbox) ;
                                    if(IDdisplay!=null) {
                                        CollectionPointdisplay.setText(collectionPoint);
                                    }
                                    listView.addHeaderView(header);
                                    if(listofDisbursementDetails!=null){
                                        listView.setAdapter(disbursementdetailAdapter);
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

