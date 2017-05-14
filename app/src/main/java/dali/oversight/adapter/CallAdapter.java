package dali.oversight.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import dali.oversight.R;
import dali.oversight.data.Call;

/**
 * Created by Mohamed ali on 07/05/2017.
 */

public class CallAdapter extends  RecyclerView.Adapter<CallAdapter.ViewHolder> {


    private ArrayList<Call> list=new ArrayList<>();


    public CallAdapter() {
    }

    public CallAdapter(ArrayList<Call> list) {
        this.list = list;
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
