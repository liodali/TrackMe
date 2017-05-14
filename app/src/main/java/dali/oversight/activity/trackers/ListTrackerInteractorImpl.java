package dali.oversight.activity.trackers;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import dali.oversight.data.Constantes;
import dali.oversight.data.friend;

/**
 * Created by Mohamed ali on 08/05/2017.
 */

public class ListTrackerInteractorImpl implements ListTrackerInteractor {


    @Override
    public void BeginFriendTraka(final friend f) {


        final DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("traka");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(f.getId())) {
                    // run some code
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/"+f.getId()+"/gps", 1);
                    database.updateChildren(childUpdates);
                }else{
                    DatabaseReference ref=database.child(f.getId());
                    ref.child("gps").setValue(1);
                    ref.child("tracker").setValue(AccessToken.getCurrentAccessToken().getUserId());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference mData=FirebaseDatabase.getInstance().getReference().child("gps");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean has=false;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(snapshot.hasChild("traka") && snapshot.hasChild("tracker")){
                        if( snapshot.child("tracker").getValue().equals(AccessToken.getCurrentAccessToken().getUserId()) && snapshot.child("traka").getValue().equals(f.getId())){
                            has=true;
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/"+snapshot.getKey()+"/alt/", Constantes.undefined);
                            childUpdates.put("/"+snapshot.getKey()+"/lon/", Constantes.undefined);
                            childUpdates.put("/"+snapshot.getKey()+"/old/", Constantes.notsee);
                            mData.updateChildren(childUpdates);
                            Log.i("exist","yes");
                        }
                    }

                }
                if(!has){
                    DatabaseReference mD=mData.push();
                    mD.child("alt").setValue(Constantes.undefined);
                    mD.child("lon").setValue(Constantes.undefined);
                    mD.child("traka").setValue(f.getId());
                    mD.child("tracker").setValue(AccessToken.getCurrentAccessToken().getUserId());
                    mD.child("old").setValue(0);
                    has=false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       /* DatabaseReference traka=FirebaseDatabase.getInstance().getReference().child("").push();
        traka.child("traka_u").setValue(f.getId());
        */
    }

    @Override
    public void findItems(OnFinishedListener listener) {


                    myNewGraphReq(listener);


    }
    private void myNewGraphReq(final OnFinishedListener listener) {

        final ArrayList<friend> friendlist=new ArrayList<>();
        GraphRequestAsyncTask request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends?fields=picture,first_name,id",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {


                    @Override
                    public void onCompleted(GraphResponse response) {


                        int nombre =0;
                        try {
                            Log.i("data friends",response.toString());
                            JSONArray rawName = response.getJSONObject().getJSONArray("data");
                            Log.i("data friends",rawName.toString());
                            if(rawName.length()==0){
                             //   ll.startAnimation(fadeOut);
                            }else{
                                while (response!=null ){
                                    rawName = response.getJSONObject().getJSONArray("data");

                                    nombre=nombre+rawName.length();
                                    int l=rawName.length();
                                    for(int i=0;i<l;i++){


                                       final friend user=new friend();
                                        user.setId(rawName.getJSONObject(i).getString("id"));
                                        user.setName(rawName.getJSONObject(i).getString("first_name"));
                                        user.setUrl(rawName.getJSONObject(i).getJSONObject("picture").getJSONObject("data").getString("url"));
                                        friendlist.add(user);



                                    }
                                    GraphRequest request1= response.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
                                    if(request1 !=null){
                                        GraphRequestAsyncTask request_2=request1.executeAsync();
                                        response=request_2.get().get(0);
                                    }else{
                                        response=null;
                                    }



                                }

                                Log.i("data friends length",""+nombre);
                                listener.onFinished(friendlist);
                               // adapter=new ListInviteFriendAdapter(userlist,ListFriendToInviteActivity.this);
                                //ll.startAnimation(fadeOut);
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }



                    }
                }
        ).executeAsync();


    }


}
