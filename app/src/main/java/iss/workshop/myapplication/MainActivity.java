package iss.workshop.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inputUsername;
    EditText inputPassword;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        Login = (Button)findViewById(R.id.loginBtn);
        if (Login != null) {
            Login.setOnClickListener(this);
        }

        SharedPreferences pref = getSharedPreferences("user_account",MODE_PRIVATE);
        if(pref.contains("username") && pref.contains("password")){
            boolean loginConfirm = checkAccount(pref.getString("username",""), pref.getString("password",""));
            if (loginConfirm){
                startHomeActivity();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.loginBtn) {
            String username = inputPassword.getText().toString();
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
            }
        }
    }

    private boolean checkAccount(String username, String password){
        if (username.equals("123") && password.equals("123")){
            return true;
        }
        return false;
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}