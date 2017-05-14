package dali.oversight.activity.login;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public class LoginPresenterImpl implements LoginPresenter,LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;
    private FirebaseAuth mAuth;
    private  Context context;
    public LoginPresenterImpl(LoginView loginView,FirebaseAuth mAuth,Context context) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.mAuth=mAuth;
        this.context=context;
    }



    @Override
    public void validateCredentials(String email, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        loginInteractor.login(email, password, this);
    }

    @Override
    public void validateCredentialsFacebook(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginView.hideProgress();
                            loginView.setLoginSucces();
                        } else {
                            loginView.hideProgress();
                            String msg="Authentication failed.";
                            loginView.setLoginError(msg);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void SigupCredentials(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loginView.hideProgress();
                            loginView.setLoginSucces();
                        } else {
                            // If sign in fails, display a message to the user.
                            loginView.hideProgress();
                            String msg="Sign Up failed.";
                            loginView.setLoginError(msg);
                        }


                    }
                });
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onEmailError(int x) {
        if (loginView != null) {
            loginView.setEmailError(x);
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError(int x) {
        if (loginView != null) {
            loginView.setPasswordError(x);
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess(String email,String password) {
        if (loginView != null) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithEmail:success");
                               // FirebaseUser user = mAuth.getCurrentUser();
                                loginView.hideProgress();
                                loginView.setLoginSucces();
                            } else {
                                // If sign in fails, display a message to the user.
                                loginView.hideProgress();
                                String msg="User does't exist";
                                loginView.setLoginError(msg);
                            }


                        }
                    });

        }
    }
}
