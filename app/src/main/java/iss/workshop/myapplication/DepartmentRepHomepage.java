package iss.workshop.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//AUTHOR:  THUN SU NYI NYI
public class DepartmentRepHomepage extends AppCompatActivity implements View.OnClickListener
{
    Button Logout;
    TextView Name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deprephome);
        getSupportActionBar().setTitle("Department Rep Home");
        Button btn1 = findViewById(R.id.depRaiseRequest);
        Button btn2 = findViewById(R.id.acknowledge);
        Name = (TextView)findViewById(R.id.name);
        Logout = (Button)findViewById(R.id.logout);
        Logout.setOnClickListener(this);
        btn2.setOnClickListener(this);
        if (btn1 != null)
            btn1.setOnClickListener(this);
        SharedPreferences pref = getSharedPreferences("loggedInUser",MODE_PRIVATE);
        if (pref.contains("name")){
            Name.setText(pref.getString("name", ""));
        }
    }

    @Override
    public void onClick(View v)
    { int id = v.getId();

        if (id == R.id.depRaiseRequest)
        {
            Intent intent = new Intent(this, SubmitRequest.class);
            startActivity(intent);
        }
        else if (id == R.id.acknowledge)
        {
            Intent intent = new Intent(this, EmployeeDisbursementList.class);
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
    };
}
