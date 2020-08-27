package iss.workshop.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class loadingforacknowledgement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingforacknowledgement);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final com.airbnb.lottie.LottieAnimationView animation2= findViewById(R.id.loadanimation);
                animation2.setVisibility(View.INVISIBLE);
                TextView loadingtext= findViewById(R.id.loadingtext);
                loadingtext.setVisibility(View.INVISIBLE);
                final com.airbnb.lottie.LottieAnimationView animation1= findViewById(R.id.successanimation);
                animation1.bringToFront();
                animation1.setVisibility(View.VISIBLE);
                animation1.playAnimation();
                animation1.loop(false);
            }
        }, 8100);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(loadingforacknowledgement.this,EmployeeDisbursementList.class);
                Toast.makeText(getApplicationContext(), "Disbursement has been confirmed !", Toast.LENGTH_LONG).show();
                finish();
                startActivity(i);
            }
        }, 10000);


    }
}