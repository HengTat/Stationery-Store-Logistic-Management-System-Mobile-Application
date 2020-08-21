package iss.workshop.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.myapplication.Model.InventoryItem;

public class MainActivity extends AppCompatActivity {
    //for api testing
    String server_url = "http://10.0.2.2:5000/api/RequestAPI/";
    private List<InventoryItem> InventoryList = new ArrayList<>();
    //
    EditText inputUsername;
    EditText inputPassword;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        inputUsername = (EditText) findViewById(R.id.usernametextbox);
        inputPassword = (EditText) findViewById(R.id.passwordtextbox);
        Login = (Button)findViewById(R.id.loginBtn);

        /*SharedPreferences pref = getSharedPreferences("user_account",MODE_PRIVATE);
        if(pref.contains("username") && pref.contains("password")){
            boolean loginConfirm = checkAccount(pref.getString("username",""), pref.getString("password",""));
            if (loginConfirm){
                startHomeActivity();
            }
        }*/

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                boolean loginConfirm = checkAccount(username,password);
                if (loginConfirm) {
                    SharedPreferences pref = getSharedPreferences("user_account", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    startHomeActivity();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Username/Password are invalid", Toast.LENGTH_SHORT).show();
                }*/
                JsonArrayRequest request = new JsonArrayRequest
                        (server_url,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        Toast.makeText(getApplicationContext(),"Hello ",Toast.LENGTH_SHORT).show();

                                        for(int i =0; i <response.length(); i++){

                                            try{
                                                JSONObject jsonObject = response.getJSONObject(i);
                                                InventoryItem item = new InventoryItem();
                                                item.setId(jsonObject.getString("id"));
                                                item.setDescription(jsonObject.getString("description"));
                                                InventoryList.add(item);
                                            }catch(JSONException e){
                                                e.printStackTrace();
                                            }
                                        }

                                        for(InventoryItem item: InventoryList){
                                            System.out.println(item.getDescription());

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"something went wrong ",Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        });
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(request);
            }
        });
    }


    private boolean checkAccount(String username, String password){
        if (username.equals("John") && password.equals("password")){
            return true;
        }
        return false;
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}