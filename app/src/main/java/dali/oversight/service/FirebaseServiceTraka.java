package dali.oversight.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.facebook.AccessToken;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dali.oversight.data.Gps;

/**
 * Created by mouna maazoun on 11/05/2017.
 */

public class FirebaseServiceTraka extends Service {


    private DatabaseReference mDatabase;
// ...


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        final DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
        mDatabase = ref.child("traka");
       final String id_fb=AccessToken.getCurrentAccessToken().getUserId();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    if(postSnapshot.getValue().toString().equals(id_fb)){
                        if(Integer.parseInt(postSnapshot.child("gps").getValue().toString())==1){ // get your position to tracker if not exist
                            DatabaseReference mData=FirebaseDatabase.getInstance().getReference().child("gps");
                            mData.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot Snapshot) {
                                    for(DataSnapshot post_Snapshot: Snapshot.getChildren()){
                                            if(Snapshot.child("traka").getValue().toString().equals(id_fb)){
                                                Map<String, Object> childUpdates = new HashMap<>();
                                                Gps g=getLocation();
                                                childUpdates.put("/gps/"+post_Snapshot.getKey()+"/alt" , g.getLatitude());
                                                childUpdates.put("/gps/"+post_Snapshot.getKey()+"/lon" , g.getLongitude());

                                                ref.updateChildren(childUpdates);
                                            }
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                         /*   mData.push();
                            Gps g=getLocation();
                            mData.child("alt").setValue(g.getLatitude());
                            mData.child("lon").setValue(g.getLongitude());
                            mData.child("traka").setValue(AccessToken.getCurrentAccessToken().getUserId());
                            mData.child("tracker").setValue(postSnapshot.child("tracker").getValue());
                            mData.child("old").setValue(0);*/
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }


    public Gps getLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Double lat,lon;
        try {
            lat = location.getLatitude ();
            lon = location.getLongitude ();
            return new Gps(String.valueOf(lat), String.valueOf(lon));
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

}
