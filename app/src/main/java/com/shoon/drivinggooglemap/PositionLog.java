package com.shoon.drivinggooglemap;

import com.google.android.gms.maps.model.LatLng;

public class PositionLog {
    int iTripID;
    int iSerialNumber;
    LatLng ltln;
    float fBearing;
    float fTilt;
    double dTimeStamp;

    public PositionLog(int iTripID, int iSerialNumber, LatLng ltln, float fBearing, float fTilt, double dTimeStamp) {
        this.iTripID = iTripID;
        this.iSerialNumber = iSerialNumber;
        this.ltln = ltln;
        this.fBearing = fBearing;
        this.fTilt = fTilt;
        this.dTimeStamp = dTimeStamp;
    }
    public PositionLog(int iTripID, int iSerialNumber, LatLng ltln, float fBearing, float fTilt) {
        this.iTripID = iTripID;
        this.iSerialNumber = iSerialNumber;
        this.ltln = ltln;
        this.fBearing = fBearing;
        this.fTilt = fTilt;
        this.dTimeStamp = System.currentTimeMillis();
    }

    public PositionLog(int iTripID, int iSerialNumber, LatLng ltln, float fBearing) {
        this.iTripID = iTripID;
        this.iSerialNumber = iSerialNumber;
        this.ltln = ltln;
        this.fBearing = fBearing;
        this.fTilt =0;
        this.dTimeStamp = System.currentTimeMillis();
    }

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

    public LatLng getLtln() {
        return ltln;
    }

    public void setLtln(LatLng ltln) {
        this.ltln = ltln;
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
}
