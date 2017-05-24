package dali.oversight.activity.main;

import android.content.Context;
import android.content.Intent;
import android.app.*;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dali.oversight.activity.AccountTraka.TrakaActivity;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public class MainInteractorImpl implements MainInteractor {
    @Override
    public void checkUserLogIn(final Context context, FirebaseUser user, final OnLoginListener listener) {
        if(user==null){
            listener.logOff();
        }else{
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference mdata = database.getReference().child("traka");
            final String id = AccessToken.getCurrentAccessToken().getUserId();
            mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(id)) {
                        listener.logOn();


                    }else{

                        Intent intent=new Intent(context, TrakaActivity.class);
                        context.startActivity(intent);
                        ( (Activity) context).finish();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
}
