package dali.oversight.activity.gpstrack;

import android.content.Context;

import dali.oversight.data.friend;

/**
 * Created by Mohamed ali on 11/05/2017.
 */

public interface GPSPresenter {

    void ReTrack(friend f);
    void CheckGoogleService(Context context);
    void GetPosTraka(friend f);

}
