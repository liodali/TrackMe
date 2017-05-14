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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

import dali.oversight.R;
import dali.oversight.activity.track.HomeActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {


    private FirebaseAuth mAuth;
    private EditText email,pwd;
    private Button login;
    private LoginButton loginFbButton;
    private ProgressDialog mProgress;
    private  LoginPresenterImpl presenter;
    private CoordinatorLayout coordinatorLayout;
    private CallbackManager callbackManager;
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


        loginFbButton.setReadPermissions("email", "public_profile");

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
    }


    @Override
    public void navigateToHome() {
        Intent i=new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
    }
}
