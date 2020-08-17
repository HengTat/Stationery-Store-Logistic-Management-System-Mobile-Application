package iss.workshop.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity implements View.OnClickListener {

    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Logout = (Button)findViewById(R.id.logoutBtn);
        if(Logout!=null){
            Logout.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.logoutBtn){
            finish();
        }
    }
}