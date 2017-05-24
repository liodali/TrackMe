package dali.oversight.activity.AccountTraka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import dali.oversight.R;
import dali.oversight.service.FirebaseServiceTraka;

public class TrakaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traka);
    }
    public void LogoutTraka(View v){
        stopService(new Intent(TrakaActivity.this, FirebaseServiceTraka.class));
        FirebaseAuth.getInstance().signOut();
    }
}
