package dali.oversight.activity.gpstrack;

import android.content.Context;

import dali.oversight.data.Gps;
import dali.oversight.data.friend;

/**
 * Created by Mohamed ali on 11/05/2017.
 */

public interface GPSInteractor {
    interface OnFinishedListener {
        void onError();



        void onSuccess();
    }
    interface OnTrakaFinishedListener {
        void onTrakaError();



        void onTrakaSuccess(Gps g);
    }
    void googleServiceAvaible(Context context, OnFinishedListener listener);
    void TrakaPosition(friend f,OnTrakaFinishedListener listener);
    void ReTrakaPosition(friend f,OnTrakaFinishedListener listener);
}
