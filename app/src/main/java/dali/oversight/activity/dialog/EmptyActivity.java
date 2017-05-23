package dali.oversight.activity.dialog;

import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dali.oversight.R;
import dali.oversight.data.friend;

public class EmptyActivity extends Activity implements OnMapReadyCallback {
    Dialog dialog;
    private GoogleMap mGoogleMap;
    private Location TrackLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_empty);
        friend f=getIntent().getParcelableExtra("friend");
          dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custompopuptraka);
        dialog.setTitle("Position "+f.getName());



        Button dialogButton = (Button) dialog.findViewById(R.id.id_custom_traka_bt);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        GoogleMap googleMap;


        MapView mMapView = (MapView)dialog.findViewById(R.id.mapView);
        MapsInitializer.initialize(this);

        mMapView = (MapView) dialog.findViewById(R.id.mapView);
        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();// needed to get the map to display immediately
        mMapView.getMapAsync(this);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;
        TrackLocation=new Location("");
        TrackLocation.setLatitude(getIntent().getDoubleExtra("Latitude",0));
        TrackLocation.setLongitude(getIntent().getDoubleExtra("Longitude",0));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomOut());
        if(TrackLocation!=null){
            LatLng ll=new LatLng(TrackLocation.getLatitude(),TrackLocation.getLongitude());
            CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll,10);

            mGoogleMap.moveCamera(update);;
            Marker newmarker = mGoogleMap.addMarker(new MarkerOptions().position(ll));
        }
    }
}
