package iss.workshop.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//AUTHOR: THUN SU NYI NYI
public class EmployeeHomepage extends AppCompatActivity implements View.OnClickListener
{
    Button Logout;
    TextView Name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeehome);
        getSupportActionBar().setTitle("Employee Home");
        Name = (TextView)findViewById(R.id.name);

        SharedPreferences pref = getSharedPreferences("loggedInUser",MODE_PRIVATE);
        if (pref.contains("name")){
            Name.setText(pref.getString("name", ""));
        }

        Button button1 = findViewById(R.id.raiserequest);
        if (button1 != null)
            button1.setOnClickListener(this);
        Logout = (Button)findViewById(R.id.logout);
        Logout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        int id=v.getId();
        if (id == R.id.raiserequest)
        {
            Intent intent = new Intent(this, SubmitRequest.class);
            startActivity(intent);
        }
        else if (id == R.id.logout)
        {
            SharedPreferences.Editor editor = getSharedPreferences("loggedInUser", MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
