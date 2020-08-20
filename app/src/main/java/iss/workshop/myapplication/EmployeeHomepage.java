package iss.workshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeHomepage extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeehome);

        Button button1 = findViewById(R.id.raiserequest);
        if (button1 != null)
            button1.setOnClickListener(this);
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
    }
}
