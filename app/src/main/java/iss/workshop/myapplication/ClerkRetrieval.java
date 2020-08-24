package iss.workshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import iss.workshop.myapplication.Model.Retrieval;

public class ClerkRetrieval extends AppCompatActivity
{
    String server_url = "http://10.0.2.2:5000/api/RetrievalAPI/";
    int id;
    String dateRetrieved;
    int employeeId;
    Date retrievedDate;
    ArrayList<Retrieval> retrievals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_retrieval);

        getSupportActionBar().setTitle("Retrieval List");

        JsonArrayRequest request=new JsonArrayRequest(server_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Retrieval> retrievals=new ArrayList<Retrieval>();

                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject curr= (JSONObject) response.get(i);
                                id=curr.getInt("id");
/*                                dateRetrieved=curr.getString("dateretrieved");
                                employeeId=curr.getInt("employeeid");*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Retrieval r=new Retrieval(id,retrievedDate,employeeId);

                            retrievals.add(r);
                        }

                        RetrievalListAdapter retAdapter=new RetrievalListAdapter(getApplicationContext(),retrievals);

                        ListView retListView=(ListView)findViewById(R.id.retrieval_List);
                        ViewGroup header=(ViewGroup)getLayoutInflater().inflate(R.layout.retrievallsit_header,retListView,false);
                        retListView.addHeaderView(header);
                        if(retListView!=null){
                            retListView.setAdapter(retAdapter);
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "No retrieval list.", Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}
