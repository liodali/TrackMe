package dali.oversight.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;

import dali.oversight.R;
import dali.oversight.activity.login.LoginActivity;
import dali.oversight.activity.track.HomeActivity;

public class MainActivity extends AppCompatActivity implements MainView {
    private FirebaseAuth mAuth;
    private MainPresenterImpl presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        presenter=new MainPresenterImpl(this,mAuth);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) .


        presenter.ValideUser(this);
    }



    @Override
    public void navigateToHome() {

        Intent i=new Intent(MainActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void navigateToLogin() {
        Intent i=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
