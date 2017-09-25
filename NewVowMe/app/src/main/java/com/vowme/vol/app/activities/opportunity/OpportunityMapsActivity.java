package com.vowme.vol.app.activities.opportunity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vowme.vol.app.R;


public class OpportunityMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Double latitude;
    private Double longitude;
    private GoogleMap mMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity_maps);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        double[] coordinates = getIntent().getDoubleArrayExtra(getResources().getString(R.string.EXTRA_LAT_LON));
        this.latitude = Double.valueOf(coordinates[0]);
        this.longitude = Double.valueOf(coordinates[1]);
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        LatLng opp = new LatLng(this.latitude.doubleValue(), this.longitude.doubleValue());
        this.mMap.addMarker(new MarkerOptions().position(opp));
        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(opp));
        this.mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
    }

    public void goToGoogleMap(View view) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/?q=" + this.latitude + "," + this.longitude)));
    }

    public void goBackToOpp(View view) {
        finish();
    }
}
