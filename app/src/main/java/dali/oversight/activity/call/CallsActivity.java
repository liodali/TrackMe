package dali.oversight.activity.call;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import dali.oversight.R;
import dali.oversight.activity.track.HomeActivity;
import dali.oversight.data.Call;
import dali.oversight.service.TService;

public class CallsActivity extends AppCompatActivity implements CallsView{

    private Toolbar toolbar;
    private TextView Receiver,Caller,date,duration;
    private ProgressDialog mProgress;

    private CallsPresenterImpl presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);
        mProgress=new ProgressDialog(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_save_call);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Storage Call");
        final String filename=getIntent().getStringExtra("filename");
        final String path=getIntent().getStringExtra("path");

        Receiver=(TextView)findViewById(R.id.id_save_call_Receiver);
        Caller=(TextView)findViewById(R.id.id_save_call_Caller);
        date=(TextView)findViewById(R.id.id_save_call_date);
        duration=(TextView)findViewById(R.id.id_save_call_duration);
        ShowInformationCall(getIntent());

        presenter=new CallsPresenterImpl(this);

    }


    public void SaveCall(View view){

        presenter.SaveCalls(getIntent());
    }
    public  void CancelCall(View v){

    }

    @Override
    public void showProgress() {
        mProgress.setMessage("Wait please ...");
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    public void hideProgress() {
        stopService(new Intent(CallsActivity.this, TService.class));
        mProgress.dismiss();
        finish();
    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void ErrorCalls() {

    }


    @Override
    public void ShowInformationCall(Intent i) {
        Call c=i.getParcelableExtra("call");
        Receiver.setText(c.getReceiver());
        Caller.setText(c.getCaller());
        date.setText(c.getDate());
        duration.setText(c.getDure());
    }
}
