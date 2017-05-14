package dali.oversight.activity.login;

import android.os.Handler;
import android.text.TextUtils;



/**
 * Created by Mohamed ali on 06/05/2017.
 */

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(final String email, final String password, final OnLoginFinishedListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(email)){
                    listener.onEmailError(0);
                    error = true;
                    return;
                }

               /* if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    listener.onEmailError(1);
                    error = true;
                    return;
                }*/
                if (TextUtils.isEmpty(password)){
                    listener.onPasswordError(0);
                    error = true;
                    return;
                }
                if (password.length()<6){
                    listener.onPasswordError(1);
                    error = true;
                    return;
                }

                if (!error){
                    listener.onSuccess(email,password);
                }
            }
        }, 2000);
    }
}
