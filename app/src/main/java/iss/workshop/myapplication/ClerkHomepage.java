package iss.workshop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ClerkHomepage extends AppCompatActivity implements View.OnClickListener{
     @Override
    protected void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_clerkhome);

         Button btn1 = findViewById(R.id.retrieval);
         Button btn2 = findViewById(R.id.disbursement);
     }

     @Override
     public void onClick(View v)
     { int id = v.getId();

         if (id == R.id.retrieval) {
             Intent intent = new Intent(this, ClerkRetrieval.class);
             startActivity(intent);
         }
         else if (id == R.id.disbursement)
         {
             Intent intent = new Intent(this, Disbursement.class);
             startActivity(intent);
         }
     };
}