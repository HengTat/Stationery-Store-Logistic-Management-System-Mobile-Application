package iss.workshop.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        getSupportActionBar().setTitle("Clerk Retrieval Form");

        Button btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        Intent intent=getIntent();
        final int retId=intent.getIntExtra("RetID",0);

        server_url = "http://10.0.2.2:5000/api/RetrievalAPI/"+Integer.toString(retId);

        JsonArrayRequest request=new JsonArrayRequest(server_url,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(JSONArray response) {

                        TextView rtId=findViewById(R.id.retrievalId);
                        rtId.setText(Integer.toString(retId));

                        TableLayout tblRetDetails = (TableLayout) findViewById(R.id.tblRetDetails);

/*                        TableRow header_row = new TableRow(getApplicationContext());
                        TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        header_row.setLayoutParams(lp1);

                        TextView head_itemCode=new TextView(getApplicationContext());
                        head_itemCode.setText("Item Code");
                        head_itemCode.setTextSize(18);
                        head_itemCode.setTypeface(null, Typeface.BOLD);
                        head_itemCode.setBackgroundResource(R.drawable.border);
                        head_itemCode.setGravity(Gravity.CENTER);
                        header_row.addView(head_itemCode);

                        TextView head_desc=new TextView(getApplicationContext());

                        head_desc.setTextSize(18);
                        head_desc.setTypeface(null, Typeface.BOLD);
                        head_desc.setGravity(Gravity.CENTER);
                        header_row.addView(head_desc);

                        TextView head_bin=new TextView(getApplicationContext());
                        head_bin.setTextSize(18);
                        head_bin.setTypeface(null, Typeface.BOLD);
                        head_bin.setGravity(Gravity.CENTER);
                        header_row.addView(head_bin);

                        TextView head_qtyNeeded=new TextView(getApplicationContext());
                        head_qtyNeeded.setTextSize(18);
                        head_qtyNeeded.setTypeface(null, Typeface.BOLD);
                        head_qtyNeeded.setGravity(Gravity.CENTER);
                        header_row.addView(head_qtyNeeded);

                        TextView head_actual=new TextView(getApplicationContext());
                        head_actual.setTextSize(18);
                        head_actual.setTypeface(null, Typeface.BOLD);
                        head_actual.setGravity(Gravity.CENTER);
                        header_row.addView(head_actual);

                        tblRetDetails.addView(header_row,0);*/


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
                            tvItemCode.setTextSize(14);
                            tvItemCode.setWidth(180);
                            tvItemCode.setHeight(150);
                            tvItemCode.setGravity(Gravity.CENTER);
                            row.addView(tvItemCode);

                            TextView tvDesc = new TextView(getApplicationContext());
                            tvDesc.setText(desc);
                            tvDesc.setTextSize(14);
                            tvDesc.setWidth(330);
                            tvDesc.setHeight(150);
                            tvDesc.setGravity(Gravity.CENTER);
                            row.addView(tvDesc);

                            TextView tvBin = new TextView(getApplicationContext());
                            tvBin.setText(bin);
                            tvBin.setTextSize(14);
                            tvBin.setWidth(150);
                            tvBin.setGravity(Gravity.CENTER);
                            row.addView(tvBin);

                            TextView tvQtyRequested = new TextView(getApplicationContext());
                            tvQtyRequested.setText(String.valueOf(qtyNeeded));
                            tvQtyRequested.setTextSize(14);
                            tvQtyRequested.setWidth(200);
                            tvQtyRequested.setGravity(Gravity.CENTER);
                            row.addView(tvQtyRequested);

                            EditText etActualRetrieved = new EditText(getApplicationContext());
                            etActualRetrieved.setInputType(InputType.TYPE_CLASS_NUMBER);
                            etActualRetrieved.setText(Integer.toString(qtyNeeded));
                            etActualRetrieved.setTextSize(14);
                            etActualRetrieved.setWidth(200);
                            etActualRetrieved.setGravity(Gravity.CENTER);
                            etActualRetrieved.setId(retId);
                            row.addView(etActualRetrieved);
                            row.setTag(currRetDetId);
                            tblRetDetails.addView(row,i);

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

                TextView txtRet=findViewById(R.id.retrievalId);
                EditText actualCol=(EditText)tableRow.getChildAt(4);
                int retrieveId=Integer.parseInt(txtRet.getText().toString());
                int retDetailsId= (int) tableRow.getTag();
                String stringActualQty=actualCol.getText().toString();
                int actualRetrieved=Integer.parseInt(stringActualQty);


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
/*                    Toast.makeText(getApplicationContext(), "Saved Successfully!",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(RetrievalDetails.this,ClerkRetrieval.class);
                    finish();
                    startActivity(intent);*/
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(RetrievalDetails.this, loadingRetrievedQtyUpdate.class);
                            startActivity(i);
                            ((Activity)RetrievalDetails.this).finish();
                        }
                    },1800);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Something is wrong. Check the input format and try again!",Toast.LENGTH_LONG).show();
/*                    Intent intent=new Intent(RetrievalDetails.this,ClerkRetrieval.class);
                    startActivity(intent);*/
                }
            });
            Volley.newRequestQueue(getApplicationContext()).add(requestArray);

        }
    }
}
