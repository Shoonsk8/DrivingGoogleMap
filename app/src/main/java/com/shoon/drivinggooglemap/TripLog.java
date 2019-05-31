package com.shoon.drivinggooglemap;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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
    private TextView tvTripLog;
    private View view;
    private PositionLog positionLog;
    public static TripLog newInstance() {
        return new TripLog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate( R.layout.trip_log_fragment, container, false );
        tvTripLog=view.findViewById( R.id.textTripLog );
        tvTripLog.setTextColor( Color.RED );

        TripLogViewModel tripLog=ViewModelProviders.of(  getActivity()).get(TripLogViewModel.class);
        tripLog.getCurrentPosition().observe( getActivity(), position->runOnUiThread( new Runnable() {
            @Override
            public void run() {
                assert position != null;
                positionLog=position;
                tvTripLog.setText(  Integer.toString( positionLog.getiSerialNumber())+" ");
                tvTripLog.append( positionLog.getdLatLng().toString());
            }
        } ));
        Bundle bundle=getArguments();
        if (bundle != null) {
            positionLog  = bundle.getParcelable("data_of_position");
            tvTripLog.setText(  Integer.toString( positionLog.getiSerialNumber())+" ");
            tvTripLog.append( positionLog.getdLatLng().toString());
        }

        return view;
    }

    private void runOnUiThread(Runnable runnable) {
        tvTripLog.setText(  Integer.toString( positionLog.getiSerialNumber())+" ");
        tvTripLog.append( positionLog.getdLatLng().toString());
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        mViewModel = ViewModelProviders.of( this ).get( TripLogViewModel.class );
        // TODO: Use the ViewModel


    }


}
