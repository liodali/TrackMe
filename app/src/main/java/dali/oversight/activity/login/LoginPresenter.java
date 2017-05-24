package dali.oversight.activity.login;

import com.facebook.AccessToken;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public interface LoginPresenter {
    void validateCredentials(String email, String password);
    void validateCredentialsFacebook(AccessToken token);
    void validateCredentialsFacebookTraka(AccessToken token);
    void SigupCredentials(String email, String password);
    void onDestroy();
}
