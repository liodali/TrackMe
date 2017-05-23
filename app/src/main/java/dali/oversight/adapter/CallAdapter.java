package dali.oversight.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import dali.oversight.R;
import dali.oversight.data.Call;

/**
 * Created by Mohamed ali on 07/05/2017.
 */

public class CallAdapter extends  RecyclerView.Adapter<CallAdapter.ViewHolder> {


    private ArrayList<Call> list=new ArrayList<>();
    private Context context;


    public CallAdapter() {
    }

    public CallAdapter(ArrayList<Call> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public CallAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_call, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CallAdapter.ViewHolder holder, int position) {
        Call c = list.get(position);
        if(c.getCaller().length()>2)
             holder.phone.setText(c.getCaller());
        else{
            holder.phone.setText(c.getReceiver());
        }
        holder.date.setText(c.getDate());
        holder.hear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                final DatabaseReference database= reference.child("Calls");
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            // TODO: handle the post
                            if(postSnapshot.child("user").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                // Create a storage reference from our app
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference();
                                storageRef.child("call/"+postSnapshot.child("file_name").getValue().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                                        intent.setDataAndType(uri, "audio/*");
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        context.startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });
                            }


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView phone,date;
        ImageButton hear;
        public ViewHolder(View itemView) {
            super(itemView);
            phone = (TextView) itemView.findViewById(R.id.id_txt_number);
            date = (TextView) itemView.findViewById(R.id.id_txt_date);
            hear = (ImageButton) itemView.findViewById(R.id.id_bt_hear_call);
        }
    }

}
