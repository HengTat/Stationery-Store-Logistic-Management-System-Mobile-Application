package iss.workshop.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

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

import iss.workshop.myapplication.Model.DisbursementDetailModel;

public class DisbursementDetails extends AppCompatActivity {

    ArrayList<DisbursementDetailModel> disbursementDetailList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_details);

        Intent intent = getIntent();
        int disId = intent.getIntExtra("DisbursementID", 0);
        String deptName = intent.getStringExtra("DepartmentName");
        int collectionPoint = intent.getIntExtra("CollectionPoint",0);
        TextView department = findViewById(R.id.deptName);
        department.setText("Department: " + deptName);
        TextView collectPoint = findViewById(R.id.collectPoint);
        collectPoint.setText("Collection Point: "+Integer.toString(collectionPoint));
        TextView disburseId = findViewById(R.id.detailsId);
        disburseId.setText("ID: "+Integer.toString(disId));

        String SERVER_URL_GET = "http://10.0.2.2:5000/api/disbursementclerkAPI/GetDisbursementDetails/"+Integer.toString(disId);
        System.out.println(SERVER_URL_GET);

        //get data
        JsonArrayRequest request = new JsonArrayRequest(
                SERVER_URL_GET,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            ArrayList<DisbursementDetailModel> objects = mapper.readValue(String.valueOf(response), new TypeReference<ArrayList<DisbursementDetailModel>>() {
                            });
                            disbursementDetailList = objects;
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                        DisbursementDetailAdapter detailAdapter = new DisbursementDetailAdapter(getApplicationContext(), disbursementDetailList);
                        ListView disList = (ListView) findViewById(R.id.detailsList);

                        if (disList != null) {
                            disList.setAdapter(detailAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(DisbursementDetails.this);
        requestQueue.add(request);
    }
}