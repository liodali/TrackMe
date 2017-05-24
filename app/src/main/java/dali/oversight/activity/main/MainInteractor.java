package dali.oversight.activity.main;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public interface MainInteractor {

    interface OnLoginListener {
        void logOn();

        void logOff();


    }

    void checkUserLogIn(Context context, FirebaseUser user, OnLoginListener listener);

}
