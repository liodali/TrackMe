package dali.oversight.activity.gpstrack;

import android.content.Context;
import android.util.Log;

import com.facebook.AccessToken;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dali.oversight.data.Constantes;
import dali.oversight.data.Gps;
import dali.oversight.data.friend;

/**
 * Created by Mohamed ali on 11/05/2017.
 */

public class GPSInteractorImpl implements GPSInteractor {
    @Override
    public void googleServiceAvaible(Context context, OnFinishedListener listener) {
        GoogleApiAvailability apiAvailability= GoogleApiAvailability.getInstance();
        int is=apiAvailability.isGooglePlayServicesAvailable(context);
        if (is== ConnectionResult.SUCCESS){
            listener.onSuccess();
        }else{

           listener.onError();
        }
    }

    @Override
    public void TrakaPosition(final friend f, final OnTrakaFinishedListener listener) {

        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        final DatabaseReference database= reference.child("gps");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post

                    if(postSnapshot.hasChild("traka") && postSnapshot.hasChild("tracker") && postSnapshot.hasChild("old")){
                        if (postSnapshot.child("tracker").getValue().toString().equals(AccessToken.getCurrentAccessToken().getUserId()) && postSnapshot.child("traka").getValue().toString().equals( f.getId()) && postSnapshot.child("old").getValue().toString().equals("0")) {
                            if( !postSnapshot.child("alt").getValue().toString().equals("undefined") && !postSnapshot.child("lon").getValue().toString().equals("undefined")){
                                Gps g=new Gps();
                                g.setLatitude(postSnapshot.child("alt").getValue().toString());
                                g.setLongitude(postSnapshot.child("lon").getValue().toString());
                                listener.onTrakaSuccess(g);

                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put("/gps/"+postSnapshot.getKey()+"/old" , 1);
                                childUpdates.put("/traka/"+f.getId()+"/gps" , 0);
                                reference.updateChildren(childUpdates);
                                Log.i("data","change");
                            }
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void ReTrakaPosition(final friend f, final OnTrakaFinishedListener listener) {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("traka");
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+f.getId()+"/gps", 1);
        database.updateChildren(childUpdates);
        final DatabaseReference mData=FirebaseDatabase.getInstance().getReference().child("gps");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(snapshot.hasChild("traka") && snapshot.hasChild("tracker")){
                        if( snapshot.child("tracker").getValue().equals(AccessToken.getCurrentAccessToken().getUserId()) && snapshot.child("traka").getValue().equals(f.getId())){

                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/"+snapshot.getKey()+"/alt/", Constantes.undefined);
                            childUpdates.put("/"+snapshot.getKey()+"/lon/", Constantes.undefined);
                            childUpdates.put("/"+snapshot.getKey()+"/old/", Constantes.notsee);
                            mData.updateChildren(childUpdates);
                            TrakaPosition(f,listener);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
