package dali.oversight.activity.main;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public class MainInteractorImpl implements MainInteractor {
    @Override
    public void checkUserLogIn(FirebaseUser user,OnLoginListener listener) {
        if(user==null){
            listener.logOff();
        }else{
            listener.logOn();
        }
    }
}
