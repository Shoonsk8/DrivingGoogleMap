package com.shoon.drivinggooglemap;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.model.LatLng;

public class PositionLog implements Parcelable {
    private int iTripID;
    private int iSerialNumber;
    private double dLatitude;

    public PositionLog(int iTripID, int iSerialNumber, double dLatitude, double dLongitude, float fBearing, float fTilt, double dTimeStamp) {
        this.iTripID = iTripID;
        this.iSerialNumber = iSerialNumber;
        this.dLatitude = dLatitude;
        this.dLongitude = dLongitude;
        this.fBearing = fBearing;
        this.fTilt = fTilt;
        this.dTimeStamp = dTimeStamp;
    }

    private double dLongitude;

    private float fBearing;
    private float fTilt;
    private double dTimeStamp;

    public PositionLog(int iTripID, int iSerialNumber,StreetViewPanorama panorama){
        this.iTripID = iTripID;
        this.iSerialNumber = iSerialNumber;
        this.dLatitude = panorama.getLocation().position.latitude;;
        this.dLongitude = panorama.getLocation().position.longitude;
        this.fBearing = panorama.getPanoramaCamera().bearing;
        this.fTilt = panorama.getPanoramaCamera().tilt;
        this.dTimeStamp = dTimeStamp;
    }
    public PositionLog(int iTripID, int iSerialNumber,LatLng latLng){
        this.iTripID = iTripID;
        this.iSerialNumber = iSerialNumber;
        this.dLatitude = latLng.latitude;;
        this.dLongitude = latLng.longitude;
        this.fBearing = 0;
        this.fTilt = 0;
        this.dTimeStamp = dTimeStamp;
    }


    public PositionLog(int iTripID, int iSerialNumber, LatLng ltln, float fBearing, float fTilt, double dTimeStamp) {
        this.iTripID = iTripID;
        this.iSerialNumber = iSerialNumber;
        this.dLatitude = ltln.latitude;;
        this.dLongitude = ltln.longitude;
        this.fBearing = fBearing;
        this.fTilt = fTilt;
        this.dTimeStamp = dTimeStamp;
    }
    public PositionLog(int iTripID, int iSerialNumber, LatLng ltln, float fBearing, float fTilt) {
        this.iTripID = iTripID;
        this.iSerialNumber = iSerialNumber;
        this.dLatitude = ltln.latitude;;
        this.dLongitude = ltln.longitude;
        this.fBearing = fBearing;
        this.fTilt = fTilt;
        this.dTimeStamp = System.currentTimeMillis();
    }

    public LatLng getdLatLng() {
        LatLng latLng=new LatLng( dLatitude, dLongitude);
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.dLatitude = latLng.latitude;
        this.dLongitude = latLng.longitude;
    }

    public double getdLatitude() {
        return dLatitude;
    }

    public void setdLatitude(double dLatitude) {
        this.dLatitude = dLatitude;
    }

    public double getdLongitude() {
        return dLongitude;
    }

    public void setdLongitude(double dLongitude) {
        this.dLongitude = dLongitude;
    }

    public PositionLog(int iTripID, int iSerialNumber, LatLng ltln, float fBearing) {
        this.iTripID = iTripID;
        this.iSerialNumber = iSerialNumber;
        this.dLatitude = ltln.latitude;;
        this.dLongitude = ltln.longitude;
        this.fBearing = fBearing;
        this.fTilt =0;
        this.dTimeStamp = System.currentTimeMillis();
    }


    protected PositionLog(Parcel in) {
        iTripID = in.readInt();
        iSerialNumber = in.readInt();
        dLatitude = in.readDouble();
        dLongitude = in.readDouble();
        fBearing = in.readFloat();
        fTilt = in.readFloat();
        dTimeStamp = in.readDouble();
    }

    public static final Creator<PositionLog> CREATOR = new Creator<PositionLog>() {
        @Override
        public PositionLog createFromParcel(Parcel in) {
            return new PositionLog( in );
        }

        @Override
        public PositionLog[] newArray(int size) {
            return new PositionLog[size];
        }
    };

    public int getiTripID() {
        return iTripID;
    }

    public void setiTripID(int iTripID) {
        this.iTripID = iTripID;
    }

    public int getiSerialNumber() {
        return iSerialNumber;
    }

    public void setiSerialNumber(int iSerialNumber) {
        this.iSerialNumber = iSerialNumber;
    }


    public float getfBearing() {
        return fBearing;
    }

    public void setfBearing(float fBearing) {
        this.fBearing = fBearing;
    }

    public float getfTilt() {
        return fTilt;
    }

    public void setfTilt(float fTilt) {
        this.fTilt = fTilt;
    }

    public double getdTimeStamp() {
        return dTimeStamp;
    }

    public void setdTimeStamp(double dTimeStamp) {
        this.dTimeStamp = dTimeStamp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( iTripID );
        dest.writeInt( iSerialNumber );
        dest.writeDouble( dLatitude );
        dest.writeDouble( dLongitude );
        dest.writeFloat( fBearing );
        dest.writeFloat( fTilt );
        dest.writeDouble( dTimeStamp );
    }
}
