package iss.workshop.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//AUTHOR: CHONG HENG TAT, NGUI KAI LIN

public class loadingforapproverequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingforapproverequest);
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
        }, 6000);
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent i = new Intent(loadingforapproverequest.this, ViewPendingRequests.class);
                Toast.makeText(getApplicationContext(), "Request has been approved!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(i);
            }
        }, 8200);
    }
}