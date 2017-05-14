package dali.oversight.activity.gpstrack;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dali.oversight.R;
import dali.oversight.data.Gps;
import dali.oversight.data.friend;

public class GPSActivity extends AppCompatActivity implements
       GPSView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener, OnMapReadyCallback {

    private Toolbar toolbar;
    private TextView name,distance;
    private Button retrack,cancel;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation,TrackLocation;
    private String Latitude,Longitude;
    private Location mCurrentLocation;

    private GoogleMap mGoogleMap;
    private ProgressBar bar;
    private Gps g;
    private friend f;
    private TextView traka_name;
    private GPSPresenterImpl presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        toolbar = (Toolbar) findViewById(R.id.toolbar_traka_gps);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Traka gps");
        bar =(ProgressBar)findViewById(R.id.id_pb_wait_gps);
        traka_name=(TextView)findViewById(R.id.id_traka_name_txt);

        this.f=getIntent().getParcelableExtra("friend");
        traka_name.setText(f.getName());
        presenter=new GPSPresenterImpl(this,this);
        presenter.CheckGoogleService(this);
        presenter.GetPosTraka(f);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                navigateToHome();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;



    }

    @Override
    public void initMap() {
        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.Mapfragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void alertSnack() {

    }

    @Override
    public void SetPos(Gps gps) {
        this.g=gps;
        TrackLocation=new Location("");
        TrackLocation.setLatitude(Double.valueOf(g.getLatitude()));
        TrackLocation.setLongitude(Double.valueOf(g.getLatitude()));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomOut());
        if(TrackLocation!=null){
            LatLng ll=new LatLng(TrackLocation.getLatitude(),TrackLocation.getLongitude());
            CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll,10);

            mGoogleMap.moveCamera(update);;
            Marker newmarker = mGoogleMap.addMarker(new MarkerOptions().position(ll)
                    .title("postion of "+f.getName()));
        }
    }

    @Override
    public void showProgress() {
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        bar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome() {
        finish();
    }
}
