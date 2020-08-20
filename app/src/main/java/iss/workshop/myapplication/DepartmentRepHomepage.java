package iss.workshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DepartmentRepHomepage extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deprephome);

        Button btn1 = findViewById(R.id.raiserequest);
        Button btn2 = findViewById(R.id.acknowledge);
    }

    @Override
    public void onClick(View v)
    { int id = v.getId();

        if (id == R.id.raiserequest)
        {
            Intent intent = new Intent(this, SubmitRequest.class);
            startActivity(intent);
        }
        else if (id == R.id.acknowledge)
        {
            Intent intent = new Intent(this, AcknowledgeDisbursement.class);
            startActivity(intent);
        }
    };
}
