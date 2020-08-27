package iss.workshop.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class loadingRetrievedQtyUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_retrieved_qty_update);

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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(loadingRetrievedQtyUpdate.this,ClerkRetrieval.class);
                Toast.makeText(getApplicationContext(), "Actual retrieved quantity is updated successfully!", Toast.LENGTH_LONG).show();
                finish();
                startActivity(i);
            }
        }, 10000);
    }
}