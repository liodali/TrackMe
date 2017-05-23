package dali.oversight.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.facebook.AccessToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dali.oversight.R;
import dali.oversight.activity.gpstrack.GPSActivity;
import dali.oversight.data.friend;

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
        final friend fr =bundle.getParcelable("friend");

        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        final DatabaseReference database= reference.child("gps");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post

                    if(postSnapshot.hasChild("traka") && postSnapshot.hasChild("tracker") && postSnapshot.hasChild("old")){
                        if (postSnapshot.child("tracker").getValue().toString().equals(AccessToken.getCurrentAccessToken().getUserId()) && postSnapshot.child("traka").getValue().toString().equals(fr.getId()) && postSnapshot.child("old").getValue().toString().equals("0")) {
                            if( !postSnapshot.child("alt").getValue().toString().equals("undefined") && !postSnapshot.child("lon").getValue().toString().equals("undefined")){
                                NotificationCompat.Builder mBuilder =
                                        new NotificationCompat.Builder(TrackService.this)
                                                .setSmallIcon(R.drawable.com_facebook_button_icon)
                                                .setContentTitle("My notification")
                                                .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
                                Intent resultIntent = new Intent(TrackService.this, GPSActivity.class);
                                resultIntent.putExtra("friend",fr);
// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(TrackService.this);
// Adds the back stack for the Intent (but not the Intent itself)
                                stackBuilder.addParentStack(GPSActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
                                stackBuilder.addNextIntent(resultIntent);
                                PendingIntent resultPendingIntent =
                                        stackBuilder.getPendingIntent(
                                                0,
                                                PendingIntent.FLAG_UPDATE_CURRENT
                                        );

                                mBuilder.setContentIntent(resultPendingIntent);
                                NotificationManager mNotificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                                mNotificationManager.notify(0, mBuilder.build());
                                stopSelf();
                            }
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return super.onStartCommand(intent, flags, startId);
    }


}
