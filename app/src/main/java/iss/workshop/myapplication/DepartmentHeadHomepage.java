package iss.workshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DepartmentHeadHomepage extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departmentheadhome);

        Button button1 = findViewById(R.id.approve);
        if (button1 != null)
            button1.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        int id=v.getId();
        if (id == R.id.approve)
        {
            Intent intent = new Intent(this, ViewRequestDetail.class);
            startActivity(intent);
        }
    }
}
