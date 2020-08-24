package iss.workshop.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import iss.workshop.myapplication.Model.Request;

public class ViewPendingRequests extends AppCompatActivity
{
    String server_url = "http://10.0.2.2:5000/api/RequestAPI/PendingApproval";
    int Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending_requests);
        getSupportActionBar().setTitle("Approve Request");

        JsonArrayRequest request = new JsonArrayRequest(server_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Request> requests = new ArrayList<Request>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject req = (JSONObject) response.get(i);
                                Id = req.getInt("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Request req = new Request(Id);
                            requests.add(req);
                        }

                        ViewPendingRequestsAdapter reqAdapter = new ViewPendingRequestsAdapter(getApplicationContext(), requests);

                        ListView reqListView = (ListView) findViewById(R.id.pendingRequestsList);
                        if (reqListView != null) {
                            reqListView.setAdapter(reqAdapter);

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
    }
}