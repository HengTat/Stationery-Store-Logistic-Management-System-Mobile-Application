package iss.workshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;


public class RetrievalDetails extends AppCompatActivity implements View.OnClickListener {

    String server_url;
    int currRetDetId;
    String itemId;
    String desc;
    String bin;
    int qtyNeeded;
    int qtyRetrieved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval_details);

        Button btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        Intent intent=getIntent();
        final int retId=intent.getIntExtra("RetID",0);

        server_url = "http://10.0.2.2:5000/api/RetrievalAPI/"+Integer.toString(retId);

        JsonArrayRequest request=new JsonArrayRequest(server_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        TextView rtId=findViewById(R.id.txtRetId);
                        rtId.setText(Integer.toString(retId));

                        TableLayout tblRetDetails = (TableLayout) findViewById(R.id.tblRetDetails);

                        TableRow header_row = new TableRow(getApplicationContext());
                        TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        header_row.setLayoutParams(lp1);

                        TextView head_itemCode=new TextView(getApplicationContext());
                        head_itemCode.setText("Item Code");
                        header_row.addView(head_itemCode);

                        TextView head_desc=new TextView(getApplicationContext());
                        head_desc.setText("Description");
                        header_row.addView(head_desc);

                        TextView head_bin=new TextView(getApplicationContext());
                        head_bin.setText("Bin");
                        header_row.addView(head_bin);

                        TextView head_qtyNeeded=new TextView(getApplicationContext());
                        head_qtyNeeded.setText("Qty Needed");
                        header_row.addView(head_qtyNeeded);

                        TextView head_actual=new TextView(getApplicationContext());
                        head_actual.setText("Actual Retrieved");
                        header_row.addView(head_actual);

                        tblRetDetails.addView(header_row,0);


                        for (int i = 0; i < response.length(); i++) {
                            TableRow row = new TableRow(getApplicationContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            row.setLayoutParams(lp);

                            try {
                                JSONObject curr = (JSONObject) response.get(i);
                                currRetDetId = curr.getInt("id");
                                itemId = curr.getJSONObject("inventoryItem").getString("id");
                                desc = curr.getJSONObject("inventoryItem").getString("description");
                                bin = curr.getJSONObject("inventoryItem").getString("bin");
                                qtyNeeded = curr.getInt("qtyNeeded");
                                qtyRetrieved=curr.getInt("qtyRetrieved");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            TextView tvItemCode = new TextView(getApplicationContext());
                            tvItemCode.setText(itemId);
                            row.addView(tvItemCode);

                            TextView tvDesc = new TextView(getApplicationContext());
                            tvDesc.setText(desc);
                            row.addView(tvDesc);

                            TextView tvBin = new TextView(getApplicationContext());
                            tvBin.setText(bin);
                            row.addView(tvBin);

                            TextView tvQtyRequested = new TextView(getApplicationContext());
                            tvQtyRequested.setText(String.valueOf(qtyNeeded));
                            row.addView(tvQtyRequested);

                            EditText etActualRetrieved = new EditText(getApplicationContext());
                            etActualRetrieved.setText(Integer.toString(qtyRetrieved));
                            etActualRetrieved.setId(retId);
                            row.addView(etActualRetrieved);
                            row.setTag(currRetDetId);
                            tblRetDetails.addView(row,i+1);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "No retrieval details.", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        String base_url = "http://10.0.2.2:5000/api/RetrievalAPI/";
        ArrayList<RetrievalDetails> retDetailsList=new ArrayList<RetrievalDetails>();
        JSONArray retDetailsArray=new JSONArray();
        if(id== R.id.btnSubmit){
            TableLayout table=findViewById(R.id.tblRetDetails);
            int rowCount=table.getChildCount();
            for(int i=1;i<table.getChildCount();i++){
                TableRow tableRow=(TableRow)table.getChildAt(i);

                TextView txtRet=findViewById(R.id.txtRetId);
                EditText actualCol=(EditText)tableRow.getChildAt(4);
                int retrieveId=Integer.parseInt(txtRet.getText().toString());
                int retDetailsId= (int) tableRow.getTag();
                int actualRetrieved=Integer.parseInt(actualCol.getText().toString());
                int toTest=actualRetrieved;

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("RetrievalId",retrieveId);
                    jsonObject.put("RetrievalDetailsId",retDetailsId);
                    jsonObject.put("ActualRetrievedQty",actualRetrieved);
                    retDetailsArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            final JsonArrayRequest requestArray=new JsonArrayRequest(Request.Method.POST, base_url, retDetailsArray, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            Volley.newRequestQueue(getApplicationContext()).add(requestArray);
            Intent intent=new Intent(getApplicationContext(), ClerkRetrieval.class);
            finish();
            startActivity(intent);
        }
    }
}
