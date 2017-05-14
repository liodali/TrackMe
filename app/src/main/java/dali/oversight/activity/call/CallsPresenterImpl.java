package dali.oversight.activity.call;

import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import dali.oversight.data.Call;

/**
 * Created by Mohamed ali on 07/05/2017.
 */

public class CallsPresenterImpl implements CallsPresenter,CallsInteractor.OnFinishedListener  {

    private CallsView callsView;
    private CallsInteractor interactor;

    public CallsPresenterImpl(CallsView callsView){
        this.callsView=callsView;
        interactor=new CallsInteractorImpl();
    }


    @Override
    public void SaveCalls(Intent intent) {
        interactor.dataCall(intent,this);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess(final Call c, final String Filename, String path) {
        callsView.showProgress();
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mRefstorage = storage.getReference();
        Uri f=Uri.fromFile(new File(path));
        final StorageReference filepath=mRefstorage.child("call").child(f.getLastPathSegment());
        filepath.putFile(f).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("Calls");
                DatabaseReference calls=database.push();
                calls.child("user").setValue(user.getUid());
                calls.child("caller").setValue(c.getCaller());
                calls.child("receiver").setValue(c.getReceiver());
                calls.child("Date").setValue(c.getDate());
                calls.child("dure").setValue(c.getDure());
                calls.child("file_name").setValue(Filename);
            }
        });



        callsView.hideProgress();
    }
}
