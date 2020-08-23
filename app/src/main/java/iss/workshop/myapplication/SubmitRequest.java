package iss.workshop.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import iss.workshop.myapplication.Model.InventoryItem;
import iss.workshop.myapplication.Model.ItemCategory;

public class SubmitRequest extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner1;
    Spinner spinner2;
    Button submit;
    EditText requestQty;
    String SERVER_URL_GET = "http://10.0.2.2:5000/api/RequestAPI/";
    String SERVER_URL_POST = "http://10.0.2.2:5000/api/RequestAPI/";

    private List<InventoryItem> inventoryList = new ArrayList<>();
    private List<ItemCategory> categoryList = new ArrayList<>();
    private List<String> categoryGroupbyName = new ArrayList<>();
    private List<String> matchCategoryItems = new ArrayList<>();
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> childAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitrequest);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        requestQty = (EditText) findViewById(R.id.requestqty);
        requestQty.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "99")});

        submit = (Button) findViewById(R.id.submit);
        if(submit != null){
            submit.setOnClickListener(this);
        }
        categoryGroupbyName.add(0,  "Select Category");
        matchCategoryItems.add(0, "Select Item");
        JsonArrayRequest request = new JsonArrayRequest
                (SERVER_URL_GET,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        ItemCategory category = new ItemCategory();
                                        category.setId(jsonObject.getJSONObject("itemCategory").getInt("id"));
                                        category.setName(jsonObject.getJSONObject("itemCategory").getString("name"));

                                        InventoryItem item = new InventoryItem();
                                        item.setId(jsonObject.getString("id"));
                                        item.setDescription(jsonObject.getString("description"));
                                        item.setItemCateggoryId(jsonObject.getInt("itemCategoryId"));
                                        item.setItemCategory(category);



                                        inventoryList.add(item);
                                        categoryList.add(category);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                Map<String, List<ItemCategory>> categorylistGrouped =
                                        categoryList.stream().collect(Collectors.groupingBy(w -> w.getName()));
                                for(String name: categorylistGrouped.keySet()) {
                                    categoryGroupbyName.add(name);
                                }
                                /*filteredListrest = categoryList.stream().filter(distinctByKey(p->p.getId())).collect(Collectors.toList());
                                filteredListall =  Stream.concat(filteredListzero.stream(), filteredListrest.stream())
                                        .collect(Collectors.toList());*/

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "something went wrong ", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner, categoryGroupbyName);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if(adapterView.getItemAtPosition(position).equals("Select Category")){
                    //do nothing
                }
                else{
                    String name = adapterView.getItemAtPosition(position).toString();
                    matchCategoryItems.clear();
                    //matchCategoryItems
                    for(InventoryItem item : inventoryList){
                        if(item.getItemCategory().getName().equals(name)){
                            matchCategoryItems.add(item.getDescription());
                            System.out.println(item.getDescription());
                        }
                    }
                    childAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_spinner, matchCategoryItems);
                    childAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                    spinner2.setAdapter(childAdapter);

                    Toast.makeText(adapterView.getContext(), "Selected: " + name, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        requestQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.submit){
            try{
            SharedPreferences sharedPref = getSharedPreferences("loggedInUser",MODE_PRIVATE);
            String category = spinner1.getSelectedItem().toString();
            String description = spinner2.getSelectedItem().toString();
            String qty =  requestQty.getText().toString();
            int retId = sharedPref.getInt("id",0);
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("employeeId", retId);
                jsonObject.put("category", category);
                jsonObject.put("description", description);
                jsonObject.put("requestQty", qty);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SERVER_URL_POST, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Your request has been submitted successfully!", Toast.LENGTH_SHORT).show();
                    startEmployeeHome();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // api call failed
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Please enter request quantity!", Toast.LENGTH_SHORT).show();
                }
            }){
            };
            Volley.newRequestQueue(this).add(jsonObjectRequest);
        }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Please select an item!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

        public static <T> Predicate<T> distinctByKey(
        Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
        }

    private void startEmployeeHome() {
        Intent intent = new Intent(this, EmployeeHomepage.class);
        finish();
        startActivity(intent);
    }
}
