package dali.oversight.activity.trackers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import dali.oversight.R;
import dali.oversight.activity.gpstrack.GPSActivity;
import dali.oversight.adapter.FriendAdapter;
import dali.oversight.data.friend;

public class ListTrackersActivity extends AppCompatActivity implements ListTrackerView {


    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RelativeLayout rl;
    private LinearLayout ll;
    private Button next;
    private FriendAdapter mAdapter;
    private ListTrackerPresenterImpl presenter;
    private friend f;
    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_trackers);
        toolbar = (Toolbar) findViewById(R.id.toolbar_list_traka_friend);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Choice friend");
        next = (Button) findViewById(R.id.id_next_bt);
        ll = (LinearLayout) findViewById(R.id.id_ll_chargement_list_traka);
        rl = (RelativeLayout) findViewById(R.id.id_rl_rc_bt_data);
        recyclerView = (RecyclerView) findViewById(R.id.id_list_friend);
        presenter=new ListTrackerPresenterImpl(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                navigateToHome();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void BeginTraka(View v){
        presenter.BeginTraka(f);
        Intent intent=new Intent(ListTrackersActivity.this, GPSActivity.class);
        intent.putExtra("friend",f);
        startActivity(intent);
        navigateToHome();
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showProgress() {
        rl.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        ll.setVisibility(View.GONE);
        rl.setVisibility(View.VISIBLE);

    }

    @Override
    public void navigateToHome() {
        finish();
    }

    @Override
    public void setItems(ArrayList<?> list) {
        mAdapter = new FriendAdapter((ArrayList<friend>) list, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void disableNext() {
        next.setEnabled(false);
    }

    @Override
    public void EnableNext() {
        next.setEnabled(true);
        next.setClickable(true);
    }

    @Override
    public void SetFriend(friend f) {
            this.f=f;
    }

    @Override
    public void Alertsnackbar() {

    }
}
