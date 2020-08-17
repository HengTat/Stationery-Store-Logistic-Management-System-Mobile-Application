package iss.workshop.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    Button Logout;
    TextView user;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Logout = (Button)findViewById(R.id.logoutBtn);
        user = (TextView)findViewById(R.id.username);

        pref = (SharedPreferences)getSharedPreferences("user_account",MODE_PRIVATE);
        String username = pref.getString("username", "");
        user.setText("Welcome " + username);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                finish();
            }
        });
    }

}