package iss.workshop.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//AUTHOR: CHONG HENG TAT, JAMES FOO

public class LoadingRequestSubmission extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_request_submission);
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
                Intent i = new Intent(LoadingRequestSubmission.this,SubmitRequest.class);
                Toast.makeText(getApplicationContext(), "Request has been submitted!", Toast.LENGTH_LONG).show();
                finish();
                startActivity(i);
            }
        }, 10000);


    }

}