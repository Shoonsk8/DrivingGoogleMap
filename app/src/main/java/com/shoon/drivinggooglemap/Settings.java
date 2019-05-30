package com.shoon.drivinggooglemap;

import java.io.Serializable;

public class Settings implements Serializable {
    long lDulationAnimateTo=1;
    public Settings() {
        this.lDulationAnimateTo = 1;
    }

    public long getlDulationAnimateTo() {
        return lDulationAnimateTo;
    }

    public void setlDulationAnimateTo(long lDulationAnimateTo) {
        this.lDulationAnimateTo = lDulationAnimateTo;
    }


}
