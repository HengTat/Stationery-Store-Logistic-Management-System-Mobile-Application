package iss.workshop.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import iss.workshop.myapplication.Model.CustomEmployeeMobile;
import iss.workshop.myapplication.Model.InventoryItem;

public class MainActivity extends AppCompatActivity {
    //for api testing
    String SERVER_URL = "http://10.0.2.2:5000/api/AccountAPI/";
    //
    EditText inputUsername;
    EditText inputPassword;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login");

        inputUsername = (EditText) findViewById(R.id.usernametextbox);
        inputPassword = (EditText) findViewById(R.id.passwordtextbox);
        Login = (Button)findViewById(R.id.loginBtn);

        SharedPreferences pref = getSharedPreferences("loggedInUser",MODE_PRIVATE);
        if(pref.contains("id") && pref.contains("role")) {
            String role = pref.getString("role", "");
            if (!(role.equals("ActingHead")))
                startCorrectActivity(role);
            else {
                long delegateExpiration = pref.getLong("delegateExpiration", 0);
                if (System.currentTimeMillis() < delegateExpiration + 86399999)
                    startCorrectActivity(role);
            }
        }

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = inputUsername.getText().toString();
                    String password = inputPassword.getText().toString();

                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id", 0);
                        jsonObject.put("email", username);
                        jsonObject.put("password", password);
                        jsonObject.put("role", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SERVER_URL, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                int id = response.getInt("id");
                                String name = response.getString("name");
                                String role = response.getString("role");
                                long delegateExpiration = response.getLong("delegateExpiration");
                                storeUserInSharedPref(id, name, role, delegateExpiration);
                                startCorrectActivity(role);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // api call failed
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        public Map<String,String> getHeaders() throws AuthFailureError {
                            // build header
                            final Map<String, String> params = new HashMap<>();
                            params.put("Content-Type", "application/json");
                            return params;
                        }
                    };
                    Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);
                }
            });
        }

    private void startCorrectActivity(String role) {
        if (role.equals("Employee"))
            startEmployeeHome();
        if (role.equals("Store Clerk"))
            startClerkHome();
        if (role.equals("Department Head") || role.equals("ActingHead"))
            startDepartmentHeadHome();
        if (role.equals("Department Rep"))
            startDepartmentRepHome();
    }

    private void startEmployeeHome() {
        Intent intent = new Intent(this, EmployeeHomepage.class);
        finish();
        startActivity(intent);
    }
    private void startClerkHome() {
        Intent intent = new Intent(this, ClerkHomepage.class);
        finish();
        startActivity(intent);
    }
    private void startDepartmentHeadHome() {
        Intent intent = new Intent(this, DepartmentHeadHomepage.class);
        finish();
        startActivity(intent);
    }
    private void startDepartmentRepHome() {
        Intent intent = new Intent(this, DepartmentRepHomepage.class);
        finish();
        startActivity(intent);
    }
    private void storeUserInSharedPref(int id, String name, String role, long delegateExpiration){
        SharedPreferences pref = getSharedPreferences("loggedInUser",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("id", id);
        editor.putString("name", name);
        editor.putString("role", role);
        editor.putLong("delegateExpiration", delegateExpiration);
        editor.commit();
    }
}