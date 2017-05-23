package dali.oversight.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Mohamed ali on 17/05/2017.
 */

public class TrackService extends Service {

    Bundle bundle;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bundle=intent.getExtras();
        String id =bundle.getString("id");

        return super.onStartCommand(intent, flags, startId);
    }
}
