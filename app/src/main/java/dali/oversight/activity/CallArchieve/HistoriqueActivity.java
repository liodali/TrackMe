package dali.oversight.activity.CallArchieve;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import dali.oversight.R;
import dali.oversight.adapter.CallAdapter;
import dali.oversight.data.Call;

public class HistoriqueActivity extends AppCompatActivity implements HistoriqueView{

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayout ll;
    CallAdapter mAdapter;
    private HistoriquePrensenterImpl prensenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        toolbar = (Toolbar) findViewById(R.id.toolbar_hist_call);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Historique Call");

        ll=(LinearLayout)findViewById(R.id.id_ll_chargement);
        recyclerView=(RecyclerView)findViewById(R.id.id_rc_call);

        prensenter=new HistoriquePrensenterImpl(this);
        prensenter.onResume();

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

               finish();
                return true;

        }
        return  super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"test",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgress() {
       // recyclerView.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {

        recyclerView.setVisibility(View.VISIBLE);
        ll.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void setItems(ArrayList<?> list) {
        Log.i("tag4",list.size()+"");

        mAdapter = new CallAdapter((ArrayList<Call>) list,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
