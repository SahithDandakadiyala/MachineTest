package com.example.machinetest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;

        mapFragment.getMapAsync(this);


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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", String.valueOf(location));
                mMap.clear();
                LatLng gatewayofIndia = new LatLng(18.9219841, 72.8346543);
                LatLng mylocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(gatewayofIndia).title("Gateway"));
                mMap.addMarker(new MarkerOptions().position(mylocation).title("Gateway"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gatewayofIndia, 5));
                SharedPreferences sharedPreferences = MapsActivity.this.getSharedPreferences("com.example.machinetest", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("latitude", String.valueOf(location.getLatitude())).apply();
                sharedPreferences.edit().putString("longitude", String.valueOf(location.getLongitude())).apply();
                sharedPreferences.edit().putString("location", location.getProvider()).apply();

                final String latitude = sharedPreferences.getString("latitude", null);
                final String longitude = sharedPreferences.getString("longitude", null);
                if (latitude != null && longitude != null) {
                    String provider = sharedPreferences.getString("location", null);
                    location = new Location(provider);
                    location.setLatitude(Double.parseDouble(latitude));
                    location.setLongitude(Double.parseDouble(longitude));
                    Log.i("TAG2", String.valueOf(location));
                    new CountDownTimer(4000, 2000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            Intent openURL = new Intent(Intent.ACTION_VIEW);
                            openURL.setData(Uri.parse("https://www.google.com/maps/dir/" + latitude + "," + longitude + "/Gateway+Of+India+Mumbai,+Apollo+Bandar,+Colaba,+Mumbai,+Maharashtra+400001/@17.7645022,74.5018783,7z/data=!3m1!4b1!4m9!4m8!1m1!4e1!1m5!1m1!1s0x3be7d1c73a0d5cad:0xc70a25a7209c733c!2m2!1d72.8346543!2d18.9219841"));
                            startActivity(openURL);
                        }

                    }.start();


                }
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        }


    }

}