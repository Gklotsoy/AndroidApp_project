package com.example.pointsofinterest_androidlab;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.pointsofinterest_androidlab.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private double userLatitude;
    private double userLongitude;
    Location userLocation = new Location("");


    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Marker poiMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                if (location != null){
                    userLatitude = (double) location.getLatitude();
                    userLongitude = (double) location.getLongitude();

                    userLocation.setLatitude(userLatitude);
                    userLocation.setLongitude(userLongitude);
                    showAlerDialogOnLocationChange(location);

                    Log.d("LOKI", "Location changed: " + location.getLatitude() + ", " + location.getLongitude());

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Location", "Status changed: " + status);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Location", "Provider enabled: " + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Location", "Provider disabled: " + provider);
            }
        });
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);

        // Add a marker at the POI and move the camera
        LatLng poi = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(poi, 14));
        mMap.addMarker(new MarkerOptions().position(poi).title("Marker at " + getIntent().getStringExtra("name")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(poi));

        Location poiLocation = new Location("");
        poiLocation.setLatitude(latitude);
        poiLocation.setLongitude(longitude);

//        Location poiLocation = new Location("");
        poiLocation.setLatitude(latitude);
        poiLocation.setLongitude(longitude);

        float distance = userLocation.distanceTo(poiLocation);

        if (distance < 500) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            builder.setMessage(getIntent().getStringExtra("description"))
                    .setTitle(getIntent().getStringExtra("name"));
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
    public boolean isDialogShown = false;
    public void showAlerDialogOnLocationChange(Location location) {
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);

        Location poiLocation = new Location("");
        poiLocation.setLatitude(latitude);
        poiLocation.setLongitude(longitude);

        float distance = location.distanceTo(poiLocation);

        if (distance < 500 && !isDialogShown) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            builder.setMessage(getIntent().getStringExtra("description"))
                    .setTitle(getIntent().getStringExtra("name"));
            AlertDialog dialog = builder.create();
            dialog.show();
            isDialogShown = true;

        }



    }

}