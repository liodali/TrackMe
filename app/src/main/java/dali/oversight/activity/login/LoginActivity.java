package dali.oversight.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import dali.oversight.R;
import dali.oversight.activity.track.HomeActivity;
import dali.oversight.service.FirebaseServiceTraka;

public class LoginActivity extends AppCompatActivity implements LoginView {


    private FirebaseAuth mAuth;
    private EditText email,pwd;
    private Button login;
    private LoginButton loginFbButton;
    private ProgressDialog mProgress;
    private  LoginPresenterImpl presenter;
    private CoordinatorLayout coordinatorLayout;
    private CallbackManager callbackManager;
    private LoginManager fbLoginManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mProgress=new ProgressDialog(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        callbackManager = CallbackManager.Factory.create();

        email=(EditText)findViewById(R.id.id_login_email_edt);
        pwd=(EditText)findViewById(R.id.id_login_pwd_edt);
        login=(Button)findViewById(R.id.id_login_lg_bt);
        loginFbButton=(LoginButton)findViewById(R.id.id_login_fb_button);

        presenter=new LoginPresenterImpl(this,mAuth,LoginActivity.this);


        loginFbButton.setReadPermissions("email", "public_profile","user_friends");

        loginFbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                presenter.validateCredentialsFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                //Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
               // Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
        fbLoginManager = com.facebook.login.LoginManager.getInstance();
        CallbackManager callbackManager = CallbackManager.Factory.create();
        fbLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // here write code When Login successfully
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                // here write code when get error
            }
        });
    }


    public void TrakaAccount(View view){
        fbLoginManager.logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
    }

    public void LoginApp(View v){
        String mEmail=email.getText().toString();
        String mPwd=pwd.getText().toString();
        presenter.validateCredentials(mEmail,mPwd);
    }
    public void SignUpApp(View v){
        String mEmail=email.getText().toString();
        String mPwd=pwd.getText().toString();
        presenter.SigupCredentials(mEmail,mPwd);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showProgress() {
        mProgress.setMessage("Wait please ...");
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    public void hideProgress() {
        mProgress.dismiss();
    }

    @Override
    public void setEmailError(int x) {
        if(x==0)
            email.setError(getString(R.string.email_error_1));
        else if(x==1)
            email.setError(getString(R.string.email_error_2));
    }

    @Override
    public void setPasswordError(int x) {
        if(x==0)
            pwd.setError(getString(R.string.pwd_error_1));
        else if(x==1)
            pwd.setError(getString(R.string.pwd_error_2));
    }

    @Override
    public void setLoginError(String msg) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    @Override
    public void setLoginSucces() {

        navigateToHome();
        finish();
    }

    @Override
    public void setLoginSuccesTraka() {

            // FirebaseUser user = mAuth.getCurrentUser();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference mdata = database.getReference().child("traka");
            final String id = AccessToken.getCurrentAccessToken().getUserId();
            mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(id)) {
                        DatabaseReference myRef = mdata.child(id);
                        // myRef.push();

                        myRef.child("gps").setValue(0);
                        myRef.child("tracker").setValue(0);


                    }else{
                        Intent intent=new Intent(LoginActivity.this, FirebaseServiceTraka.class);
                        startService(intent);

                    }
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




    }



    @Override
    public void navigateToHome() {
        Intent i=new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
    }
}
