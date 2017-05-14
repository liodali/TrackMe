package dali.oversight.activity.CallArchieve;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dali.oversight.adapter.CallAdapter;
import dali.oversight.data.Call;

/**
 * Created by Mohamed ali on 07/05/2017.
 */

public class HistoriqueInteractorImpl implements HistoriqueInteractor {
    @Override
    public void findItems(final OnFinishedListener listener) {

        DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference().child("Calls");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Call> list=new ArrayList<>();

                for (DataSnapshot snapost: dataSnapshot.getChildren()) {

                    if(snapost.child("user").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        Call c=new Call();
                        //Get user map
                        c.setId(snapost.getKey());
//                    Log.d("title",snapost.child("title").getValue().toString());
                        c.setCaller(snapost.child("caller").getValue().toString());

                        c.setReceiver(snapost.child("receiver").getValue().toString());
                        c.setDate(snapost.child("Date").getValue().toString());


                        list.add(c);
                    }

                }
                listener.onFinished(list);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.i("tag3.9","test");
    }
}
