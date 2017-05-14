package dali.oversight.activity.call;

import android.content.Intent;

import dali.oversight.data.Call;

/**
 * Created by Mohamed ali on 07/05/2017.
 */

public interface CallsInteractor {

    interface OnFinishedListener {


        void onError();

        void onSuccess(Call c,String Filename,String path);
    }
    void dataCall(Intent intent,OnFinishedListener listener);


}
