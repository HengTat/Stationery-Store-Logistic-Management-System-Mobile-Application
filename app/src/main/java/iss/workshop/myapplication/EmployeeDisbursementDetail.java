package iss.workshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import iss.workshop.myapplication.Model.DisbursementDetailAPImodel;


public class EmployeeDisbursementDetail extends AppCompatActivity {
    ListView listView;
    List<DisbursementDetailAPImodel> Listofdisbursementdetails= new ArrayList<>();
    List<String> Listofreceivedqty= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_disbursement_detail);

        final String department= getIntent().getStringExtra("Department");
        int cp= getIntent().getIntExtra("CollectionPoint",0);
        final String CollectionPoint= String.valueOf(cp);
        int CurrDisbursementId = getIntent().getIntExtra("DisbursementId", 0);
        final String tester = String.valueOf(CurrDisbursementId);

        //GET REQUEST
        //httpget request to listofjavaobject(disbursementdetails)
        //(GET) Retrieve all disbursementdetails using disbursementid (REMEMBER TO PUT THE LAST NUMBER AS DISBURSEMENT ID)
        String server_url3="http://10.0.2.2:5000/api/disbursementemployeeAPI/Getdisbursementdetails/"+tester;

        JsonArrayRequest request = new JsonArrayRequest
                (server_url3,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                ObjectMapper mapper = new ObjectMapper();
                                try {
                                    List<DisbursementDetailAPImodel> objects = mapper.readValue(String.valueOf(response), new TypeReference<List<DisbursementDetailAPImodel>>(){});
                                    Listofdisbursementdetails=objects;

                                    DisbursementDetailEmployeeAdapter disbursementDetailEmployeeAdapter= new DisbursementDetailEmployeeAdapter(getApplicationContext(),Listofdisbursementdetails);
                                    listView=(ListView) findViewById(R.id.disbursementdetail_List);

                                    ViewGroup header=(ViewGroup)getLayoutInflater().inflate(R.layout.disbursementdetailheader,listView,false);
                                    TextView IDdisplay= (TextView) header.findViewById(R.id.disbursementidbox) ;
                                    if(IDdisplay!=null){
                                        IDdisplay.setText(tester);
                                    }

                                    TextView Departmentdisplay= (TextView) header.findViewById(R.id.departmentbox) ;
                                    if(IDdisplay!=null){
                                        Departmentdisplay.setText(department);
                                    }
                                    TextView CollectionPointdisplay= (TextView) header.findViewById(R.id.collectionpointbox) ;
                                    if(IDdisplay!=null) {
                                        CollectionPointdisplay.setText(CollectionPoint);
                                    }
                                    listView.addHeaderView(header);

                                    ViewGroup footer=(ViewGroup)getLayoutInflater().inflate(R.layout.disbursementdetailfooter,listView,false);
                                    listView.addFooterView(footer);

                                    if(Listofdisbursementdetails!=null){
                                        listView.setAdapter(disbursementDetailEmployeeAdapter);
                                    }

                                    Button acknowledgedisbursementbtn=(Button) footer.findViewById(R.id.acknowledgedisbursementbtn);
                                    acknowledgedisbursementbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            //retrieve list of receivedqty
                                            List<Integer> Listofreceivedqty= new ArrayList<>();
                                            for (int a = 1; a < listView.getCount()-1; a++) {
                                                EditText et = (EditText) listView.getChildAt(a).findViewById(R.id.receivedqtybox2);
                                                Listofreceivedqty.add(Integer.parseInt(et.getText().toString()));
                                            }

                                            for(int p=0; p <Listofdisbursementdetails.size();p++){
                                                DisbursementDetailAPImodel dd = Listofdisbursementdetails.get(p);
                                                dd.setQtyReceived(Listofreceivedqty.get(p));
                                                Listofdisbursementdetails.set(p,dd);
                                            }

                                             //update Listofdibursementdetailsreceivedqty

                                            //send api to confirm list
                                            //httpput request to update db using jsonbody
                                            //(PUT) Update all disbursementdetails , Insert json body with disbursementdetails that need to be updated
                                            String server_url6="http://10.0.2.2:5000/api/disbursementemployeeAPI/updatedisbursementdetails/";
                                            //List of disbursement testobjects
                                            List<DisbursementDetailAPImodel> Listoftestobjects= Listofdisbursementdetails;
                                            // put list of javaobject in to json array
                                            JSONArray jsonarray= new JSONArray();
                                            for (DisbursementDetailAPImodel dd : Listoftestobjects){
                                                JSONObject obj= new JSONObject();
                                                try {
                                                    obj.put("id",dd.getId());
                                                    obj.put("qtyNeeded",dd.getQtyNeeded());
                                                    obj.put("qtyReceived",dd.getQtyReceived());
                                                    obj.put("disbursementId",dd.getDisbursementId());
                                                    obj.put("inventoryItemId",dd.getInventoryItemId());
                                                    jsonarray.put(obj);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            final String jsonbody= jsonarray.toString();
                                            // volley put request
                                            StringRequest postRequest = new StringRequest(Request.Method.PUT, server_url6,
                                                    new Response.Listener<String>()
                                                    {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            // response
                                                            Log.d("Response", response);
                                                        }
                                                    },
                                                    new Response.ErrorListener()
                                                    {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            // error
                                                            error.printStackTrace();
                                                        }
                                                    }
                                            ) {
                                                @Override
                                                public String getBodyContentType() {
                                                    return "application/json; charset=utf-8";
                                                }

                                                @Override
                                                public byte[] getBody() throws AuthFailureError {
                                                    try {
                                                        return jsonbody == null ? null : jsonbody.getBytes("utf-8");
                                                    } catch (UnsupportedEncodingException uee) {
                                                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonbody, "utf-8");
                                                        return null;
                                                    }
                                                }

                                                @Override
                                                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                                    String responseString = "";
                                                    if (response != null) {
                                                        responseString = String.valueOf(response.statusCode);
                                                    }
                                                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                                                }
                                            };

                                            RequestQueue requestQueue = Volley.newRequestQueue(EmployeeDisbursementDetail.this);
                                            requestQueue.add(postRequest);


                                            Toast.makeText(getApplicationContext(), "Disbursement has been confirmed ", Toast.LENGTH_SHORT).show();
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    Intent i = new Intent(EmployeeDisbursementDetail.this,EmployeeDisbursementList.class);
                                                    startActivity(i);
                                                }
                                            }, 1200);

                                        }
                                    });

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