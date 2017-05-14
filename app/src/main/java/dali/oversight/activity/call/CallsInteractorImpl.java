package dali.oversight.activity.call;

import android.content.Intent;

import dali.oversight.data.Call;

/**
 * Created by Mohamed ali on 07/05/2017.
 */

public class CallsInteractorImpl implements CallsInteractor {
    @Override
    public void dataCall(Intent intent,OnFinishedListener listener) {
        Call c=intent.getParcelableExtra("call");
        String Filename=intent.getStringExtra("filename");
        String path=intent.getStringExtra("path");
        if(c==null){
            listener.onError();
        }else{
            listener.onSuccess(c,Filename,path);
        }
    }
}
