package com.shoon.drivinggooglemap;

import android.Manifest;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLink;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.shoon.drivinggooglemap.SQL.TripLogSQLDAO;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMarkerDragListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        SettingsFragment.OnFragmentInteractionListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        OnStreetViewPanoramaChangeListener{
    private static Settings settings;
    private Context context;
    private static final String MARKER_POSITION_KEY = "MarkerPosition";

    // George St, Sydney
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng INDY = new LatLng(39.85183, -86.106064);
    private static LatLng initialLocation=INDY;
    private StreetViewPanorama mStreetViewPanorama;

    private Marker mMarker;
    private GoogleMap mMap;
    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;
    private boolean mLocationPermissionDenied = false;
    MediaPlayer player;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
  //  private LatLng currentLocation;
    private TextView tvDebug;
    private LatLng markerPosition;
    int i=0;
    private PositionLog positionLogCurrent;
    private Bundle bundle;
    private TripLog tripLog;
    private boolean bStop=true;
    private DialView dvSteering;
    private SettingsViewModel viewModelSettings;
    private TripLogViewModel viewModelTripLog;

    private SettingRepository settingRepository;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        context=getApplicationContext();
        TripLogRepository tripLogRepository=new TripLogRepository(  context );
        settingRepository=new SettingRepository( context );

        dvSteering=findViewById(R.id.dial_volume);
        settings=new Settings();

        final Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        toolbar.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getSupportActionBar().hide();
                return false;
            }
        });


        final FloatingActionButton fab = findViewById( R.id.fab );

        fab.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View view) {
            if(bStop){
                bStop=false;
                fab.setImageResource(  R.drawable.ic_palm );

                Snackbar.make( view, "Tap the steering wheel. Let's go!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
                getSupportActionBar().hide();
            }else{
                bStop=true;
                getSupportActionBar().show();
                fab.setImageResource(  R.drawable.ic_running );
                Snackbar.make( view, "Holding!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
            }
        } );
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );



        if (savedInstanceState == null) {
            markerPosition = initialLocation;
        } else {
            markerPosition = savedInstanceState.getParcelable(MARKER_POSITION_KEY);
        }
        SupportMapFragment mapFragment = new SupportMapFragment();
        FragmentTransaction transaction3= getSupportFragmentManager().beginTransaction().add( R.id.upper, mapFragment);
        transaction3.addToBackStack( null );
        transaction3.commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap map) {
                new Thread( new Runnable() {
                    @Override
                    public void run() {
                        if (mStreetViewPanorama != null) {
                            mStreetViewPanorama.setOnStreetViewPanoramaChangeListener( new OnStreetViewPanoramaChangeListener() {
                                @Override
                                public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
                                    mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( positionLogCurrent.getdLatLng(), 15 ) );
                                }
                            } );
                        }
                    } } );



                mMap = map;
                mMap.setOnMapClickListener(MainActivity.this);
                mMap.setOnMapLongClickListener(MainActivity.this);
                mMap.setOnCameraIdleListener(MainActivity.this);
                // Add a marker in Sydney and move the camera
                mMap.addMarker( new MarkerOptions().position( positionLogCurrent.getdLatLng() ).title( "Here I am" ) );
                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( positionLogCurrent.getdLatLng(),15) );

                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.setOnMyLocationButtonClickListener(MainActivity.this);
                mMap.setOnMyLocationClickListener(MainActivity.this);
                enableMyLocation();
                UiSettings mUiSettings = mMap.getUiSettings();
                //  mUiSettings.setMapToolbarEnabled(true);
                mUiSettings.setZoomControlsEnabled(true);
                mUiSettings.setScrollGesturesEnabled(true);
                mUiSettings.setZoomGesturesEnabled(true);
                mUiSettings.setTiltGesturesEnabled(true);
                mUiSettings.setRotateGesturesEnabled(true);

                // Creates a draggable marker. Long press to drag.
              /*  TripLogViewModel tripLog=ViewModelProviders.of(MainActivity.this).get(TripLogViewModel.class);
                tripLog.getCurrentPosition().observe( MainActivity.this, position->runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        assert position != null;
                        markerPosition=position.getdLatLng();
                        mMarker = map.addMarker(new MarkerOptions()
                                .position(markerPosition)
                                .icon(BitmapDescriptorFactory.fromResource( R.drawable.pegman))
                                .draggable(true));
                        player.start();
                    }
                } ));*/

                mMarker.remove();
                mMarker = mMap.addMarker(new MarkerOptions()
                        .position(positionLogCurrent.getdLatLng())
                        .icon(BitmapDescriptorFactory.fromResource( R.drawable.pegman))
                        .draggable(true));

                player.start();

            }
        });
        positionLogCurrent=new PositionLog(0,0, initialLocation,0 );
        tripLog=new TripLog();
        bundle = new Bundle();
        bundle.putParcelable("data_of_position", positionLogCurrent);
        tripLog.setArguments(bundle);

        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction().add(  R.id.upper, tripLog );
        transaction.addToBackStack( null );
        transaction.commit();

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =new SupportStreetViewPanoramaFragment();
        FragmentTransaction transaction2= getSupportFragmentManager().beginTransaction().replace(  R.id.lower, streetViewPanoramaFragment );
        transaction2.addToBackStack( null );
        transaction2.commit();
        TextView tv=findViewById(R.id.textPosition );
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
            new OnStreetViewPanoramaReadyCallback() {

                @Override
                public void onStreetViewPanoramaReady(final StreetViewPanorama panorama) {
                    int rotation = getWindowManager().getDefaultDisplay().getRotation();


                    mStreetViewPanorama = panorama;
                    mMap.setOnMyLocationChangeListener(  new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location location) {
                            StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera( panorama.getPanoramaCamera().zoom, panorama.getPanoramaCamera().tilt, dvSteering.getBearig() );
                            panorama.animateTo( camera, settings.getlDulationAnimateTo() );
                            // tvDebug.setText( Float.toString(  dvSteering.getBearig()));
                            if (dvSteering.isGoingForward()) {
                                goForward( panorama );
                                mMap.moveCamera( CameraUpdateFactory.newLatLng( positionLogCurrent.getdLatLng() ) );
                                tv.setText( positionLogCurrent.getiSerialNumber() + " " + positionLogCurrent.getdLatLng().toString() );

                            }
                        }
                    } );

                    mMap.setOnMarkerDragListener( new OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {

                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {

                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {
                            positionLogCurrent.setLatLng( mMarker.getPosition() );
                            panorama.setPosition( positionLogCurrent.getdLatLng() );
                            panorama.setPosition( positionLogCurrent.getdLatLng() );
                            panorama.animateTo( panorama.getPanoramaCamera(), 20 );
                        }
                    } );


                    mStreetViewPanorama.setOnStreetViewPanoramaChangeListener( MainActivity.this );


                    if(savedInstanceState ==null)

                {
                    mStreetViewPanorama.setPosition( positionLogCurrent.getdLatLng() );
                }


                    dvSteering.setOnTouchListener(new View.OnTouchListener()

                {
                    @Override
                    public boolean onTouch (View v, MotionEvent event){
                    StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera( panorama.getPanoramaCamera().zoom, panorama.getPanoramaCamera().tilt, dvSteering.getBearig() );
                    panorama.animateTo( camera, settings.getlDulationAnimateTo() );
                    // tvDebug.setText( Float.toString(  dvSteering.getBearig()));
                    if (dvSteering.isGoingForward()) {
                        goForward( panorama );
                    }
                    return false;
                }
                } );
            }
            });
        player=MediaPlayer.create( this,R.raw.po );
    }

    public void goForward(StreetViewPanorama panorama){
        if(bStop)return;

        MainActivity.this.positionLogCurrent=new PositionLog(0,i++,panorama );
        MainActivity.this.setIntent( new Intent( String.valueOf( context ) ) );
        viewModelTripLog= ViewModelProviders.of(MainActivity.this).get(TripLogViewModel.class);
        MutableLiveData<PositionLog> positionLogMutableLiveData = new MutableLiveData<>();
        positionLogMutableLiveData.setValue( positionLogCurrent );
        viewModelTripLog.setPostion( positionLogMutableLiveData );

        StreetViewPanoramaLocation location = panorama.getLocation();
        StreetViewPanoramaCamera camera = mStreetViewPanorama.getPanoramaCamera();
        if (location != null && location.links != null) {
            StreetViewPanoramaLink link = findClosestLinkToBearing(location.links, camera.bearing);
            mStreetViewPanorama.setPosition(link.panoId);

        }
    }
    public StreetViewPanoramaLink findClosestLinkToBearing(StreetViewPanoramaLink[] links,
                                                                  float bearing) {
        float minBearingDiff = 360;
        StreetViewPanoramaLink closestLink = links[0];
        for (StreetViewPanoramaLink link : links) {
            if (minBearingDiff > findNormalizedDifference(bearing, link.bearing)) {
                minBearingDiff = findNormalizedDifference(bearing, link.bearing);
                closestLink = link;
            }
        }
        dvSteering.setfBearingLink(closestLink.bearing);
        return closestLink;
    }
    // Find the difference between angle a and b as a value between 0 and 180
    public static float findNormalizedDifference(float a, float b) {
        float diff = a - b;
        float normalizedDiff = diff - (float) (360 * Math.floor(diff / 360.0f));
        return (normalizedDiff < 180.0f) ? normalizedDiff : 360.0f - normalizedDiff;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if(!getSupportActionBar().isShowing()){
            getSupportActionBar().show();
            return;
        }

        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }

    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MARKER_POSITION_KEY, mMarker.getPosition());
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation location) {
        if (location != null) {
            mMarker.setPosition(location.position);
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        mStreetViewPanorama.setPosition(marker.getPosition(), 150);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && requestCode == 1) {

            if (data != null) {
                try {
                    String str=data.getData().toString();
                    player.setDataSource(getApplicationContext(), Uri.parse(str ));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });


            }


        }

    }

    @SuppressWarnings("Options")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            SupportMapFragment mapFragment = new SupportMapFragment();
            FragmentTransaction transaction= getSupportFragmentManager().beginTransaction().add( R.id.upper, mapFragment);
            transaction.addToBackStack( null );
            transaction.commit();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {
            SettingsFragment settingsFragment=new SettingsFragment();

         //   bundle.putSerializable( "data_of_settings", settings);
    //        settingsFragment.setArguments(bundle);

            FragmentTransaction transaction= getSupportFragmentManager().beginTransaction().replace( R.id.upper, settingsFragment );
            transaction.addToBackStack( null );
            transaction.commit();


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lite_list_menu, menu);
        return true;
    }

    boolean bPin=true;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.loncationIndy:
                initialLocation=INDY;
                positionLogCurrent.setLatLng( INDY);
                positionLogCurrent.setLatLng( new LatLng( initialLocation.latitude,initialLocation.longitude));
                mMarker.remove();
                mMarker = mMap.addMarker(new MarkerOptions()
                        .position(positionLogCurrent.getdLatLng())
                        .icon(BitmapDescriptorFactory.fromResource( R.drawable.pegman))
                        .draggable(true));

                player.start();

                mStreetViewPanorama.setPosition( positionLogCurrent.getdLatLng() );
                break;
            case R.id.locationCurrent:
                initialLocation=new LatLng( mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
                CameraPosition position=mMap.getCameraPosition();
                positionLogCurrent.setLatLng( new LatLng( initialLocation.latitude,initialLocation.longitude));
                mMarker.remove();
                mMarker = mMap.addMarker(new MarkerOptions()
                        .position(positionLogCurrent.getdLatLng())
                        .icon(BitmapDescriptorFactory.fromResource( R.drawable.pegman))
                        .draggable(true));

                player.start();

                mStreetViewPanorama.setPosition( positionLogCurrent.getdLatLng() );
                break;
            case R.id.locationSydney:

                initialLocation=SYDNEY;
                positionLogCurrent.setLatLng( SYDNEY);
                positionLogCurrent.setLatLng( new LatLng( initialLocation.latitude,initialLocation.longitude));
                mMarker.remove();
                mMarker = mMap.addMarker(new MarkerOptions()
                        .position(positionLogCurrent.getdLatLng())
                        .icon(BitmapDescriptorFactory.fromResource( R.drawable.pegman))
                        .draggable(true));

                player.start();

                mStreetViewPanorama.setPosition( positionLogCurrent.getdLatLng() );
                break;
        }
        return true;
    }



    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    int iCounter=0;
    @Override
    public void onMapLongClick(LatLng latLng) {
        mMarker.remove();
        mMarker = mMap.addMarker(new MarkerOptions()
                .position(positionLogCurrent.getdLatLng())
                .icon(BitmapDescriptorFactory.fromResource( R.drawable.pegman))
                .draggable(true));

        player.start();

        mStreetViewPanorama.setPosition( positionLogCurrent.getdLatLng() );
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        positionLogCurrent.setLatLng( new LatLng( location.getLatitude(),location.getLongitude()));

        mMap.addMarker( new MarkerOptions()
                .position(positionLogCurrent.getdLatLng()).title(  "My location" )
                .snippet(positionLogCurrent.getdLatLng().toString()) );
        player.start();

        //    Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onMyLocationButtonClick() {
        CameraPosition position=mMap.getCameraPosition();
        positionLogCurrent.setLatLng( new LatLng( position.target.latitude,position.target.longitude));
        return false;
    }


    @Override
    public void onFragmentInteraction(Settings settings) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
