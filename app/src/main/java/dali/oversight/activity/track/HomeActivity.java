package dali.oversight.activity.track;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import dali.oversight.R;
import dali.oversight.activity.CallArchieve.HistoriqueActivity;
import dali.oversight.activity.login.LoginActivity;
import dali.oversight.activity.trackers.ListTrackersActivity;
import dali.oversight.service.TService;

public class HomeActivity extends AppCompatActivity implements HomeView{

    private CoordinatorLayout layout;

    private HomePresenterImpl presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        layout=(CoordinatorLayout)findViewById(R.id.id_home_coordinator);

        presenter=new HomePresenterImpl(this);
    }



    public void saveCall(View v){

        stopService(new Intent(HomeActivity.this, TService.class));
        Intent myIntent = new Intent(HomeActivity.this, TService.class);
        // myIntent.putExtra("user",u.getId());
        startService(myIntent);
        makeCallDialog();
    }
    public void gpsTrack(View v){
        Intent intent=new Intent(HomeActivity.this, ListTrackersActivity.class);
        startActivity(intent);
    }
    public void SmsTrack(View v){

    }
    public void historique(View v){
        Intent intent=new Intent(HomeActivity.this, HistoriqueActivity.class);
        startActivity(intent);
    }
    public void setting(View v){

    }
    public void BecomePremium(View v){

    }
    public void Logout(View v){
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void makeCallDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Get Number");

// Set up the input
        final EditText input = new EditText(HomeActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phone = input.getText().toString();
                presenter.Calls(phone);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void MakeCall(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(HomeActivity.this,"You don't have permission",Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(intent);
    }

    @Override
    public void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar
                .make(layout, msg, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    @Override
    public void navigateToGpsTrack() {

    }

    @Override
    public void navigateToSmsGpsTrack() {

    }
}
