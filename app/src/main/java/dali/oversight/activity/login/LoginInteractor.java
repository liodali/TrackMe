package dali.oversight.activity.login;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onEmailError(int x);

        void onPasswordError(int x);

        void onSuccess(String email,String password);
    }

    void login(String email, String password, OnLoginFinishedListener listener);

}
