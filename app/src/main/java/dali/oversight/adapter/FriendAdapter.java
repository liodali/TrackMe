package dali.oversight.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import dali.oversight.R;
import dali.oversight.activity.trackers.ListTrackerView;
import dali.oversight.data.friend;

/**
 * Created by Mohamed ali on 10/05/2017.
 */

public class FriendAdapter extends  RecyclerView.Adapter<FriendAdapter.ViewHolder> {


    private ArrayList<friend> list=new ArrayList<>();
    private ListTrackerView trackerView;
    RadioButton button;
    public FriendAdapter() {
    }

    public FriendAdapter(ArrayList<friend> list,ListTrackerView trackerView) {
        this.trackerView=trackerView;
        this.list = list;
    }

    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemfriend, parent, false);

        return new FriendAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FriendAdapter.ViewHolder holder, int position) {
        final friend f = list.get(position);
        holder.name.setText(f.getName());
        holder.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button!=null){
                    button.setChecked(false);
                }
                holder.rb.setChecked(true);
                button=holder.rb;
                trackerView.EnableNext();
                trackerView.SetFriend(f);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        RadioButton rb;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.id_txt_name_friend);
            rb = (RadioButton) itemView.findViewById(R.id.id_rb_checked_traka);
            img = (ImageView) itemView.findViewById(R.id.id_friend_profile);
        }
    }

}
