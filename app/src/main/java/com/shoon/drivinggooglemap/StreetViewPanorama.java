package com.shoon.drivinggooglemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shoon.drivinggooglemap.ui.streetviewpanorama.StreetViewPanoramaFragment;

public class StreetViewPanorama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.street_view_panorama_activity );
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, StreetViewPanoramaFragment.newInstance() )
                    .commitNow();
        }
    }
}
