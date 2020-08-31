package iss.workshop.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import iss.workshop.myapplication.Model.RequestDetails;
//AUTHOR: NGUI KAI LIN

public class ViewRequestDetails extends AppCompatActivity implements View.OnClickListener {

    String server_url;
    int RequestId;
    String Description;
    int QtyRequested;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request_details);
        getSupportActionBar().setTitle("Approve Request");

        Intent intent = getIntent();
        int requestId = intent.getIntExtra("RequestID", 0);
        TextView reqID = findViewById(R.id.requestId);
        reqID.setText(Integer.toString(requestId));

        server_url = "http://10.0.2.2:5000/api/RequestAPI/PendingApproval/"+Integer.toString(requestId);

        JsonArrayRequest request = new JsonArrayRequest(server_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<RequestDetails> requestDetails = new ArrayList<RequestDetails>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject req = (JSONObject) response.get(i);

                                RequestId = req.getInt("requestId");
                                Description = req.getJSONObject("inventoryItem").getString("description");
                                QtyRequested = req.getInt("qtyRequested");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            RequestDetails reqdet = new RequestDetails(RequestId, Description, QtyRequested);
                            requestDetails.add(reqdet);
                        }
                        Log.d("response", requestDetails.toString());
                        ViewRequestDetailsAdapter reqDetAdapter = new ViewRequestDetailsAdapter(getApplicationContext(), requestDetails);

                        ListView reqDetListView = (ListView) findViewById(R.id.pendingRequestsList);
                        if (reqDetListView != null) {
                            reqDetListView.setAdapter(reqDetAdapter);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


        Button approve = findViewById(R.id.appprovebtn);
        if (approve != null)
            approve.setOnClickListener(this);
        Button reject = findViewById(R.id.rejectbtn);
        if (reject != null)
            reject.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.appprovebtn)
        {
            try
            {
                Intent intent = getIntent();
                int requestId = intent.getIntExtra("RequestID", 0);
                String cmd = "Approve";
                server_url = "http://10.0.2.2:5000/api/RequestAPI/PendingApproval/"+Integer.toString(requestId)+"/"+cmd;

                JSONObject jsonObject = new JSONObject();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, server_url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(ViewRequestDetails.this, loadingforapproverequest.class);
                        startActivity(i);
                        ((Activity)ViewRequestDetails.this).finish();
                    }
                },1800);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (id == R.id.rejectbtn)
        {
            try
            {
                Intent intent = getIntent();
                int requestId = intent.getIntExtra("RequestID", 0);
                String cmd = "Reject";
                server_url = "http://10.0.2.2:5000/api/RequestAPI/PendingApproval/"+Integer.toString(requestId)+"/"+cmd;

                JSONObject jsonObject = new JSONObject();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, server_url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(ViewRequestDetails.this, loadingforrejectrequest.class);
                        startActivity(i);
                        ((Activity)ViewRequestDetails.this).finish();
                    }
                },1800);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
