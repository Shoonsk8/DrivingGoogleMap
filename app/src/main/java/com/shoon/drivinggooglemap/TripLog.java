package com.shoon.drivinggooglemap;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import static java.util.Objects.*;

public class TripLog extends Fragment {

    private TripLogViewModel mViewModel;
    TextView tvTripLog;
    View view;
    public static TripLog newInstance() {
        return new TripLog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TripLogViewModel tripLog=ViewModelProviders.of(  getActivity() ) .get(TripLogViewModel.class);
        view = inflater.inflate( R.layout.trip_log_fragment, container, false );


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        mViewModel = ViewModelProviders.of( this ).get( TripLogViewModel.class );
        // TODO: Use the ViewModel


    }

    @Override
    public void onResume() {
        super.onResume();


        tvTripLog=view.findViewById( R.id.textTripLog );
        tvTripLog.setTextColor( Color.RED );
        tvTripLog.append(  "test" );
        tvTripLog.invalidate();
        view.invalidate();
    }
}
