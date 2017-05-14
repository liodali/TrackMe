package dali.oversight.activity.login;

import dali.oversight.BaseView;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public interface LoginView extends BaseView{


    void setEmailError(int x);

    void setPasswordError(int x);

    void setLoginError(String msg);

    void setLoginSucces();


}
