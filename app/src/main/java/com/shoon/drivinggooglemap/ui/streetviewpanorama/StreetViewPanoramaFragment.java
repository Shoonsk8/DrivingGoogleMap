package com.shoon.drivinggooglemap.ui.streetviewpanorama;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoon.drivinggooglemap.R;

public class StreetViewPanoramaFragment extends Fragment {

    private StreetViewPanoramaViewModel mViewModel;

    public static StreetViewPanoramaFragment newInstance() {
        return new StreetViewPanoramaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.street_view_panorama_fragment, container, false );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        mViewModel = ViewModelProviders.of( this ).get( StreetViewPanoramaViewModel.class );
        // TODO: Use the ViewModel
    }

}
