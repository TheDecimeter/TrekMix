package com.cs4530cpjd.trekmix.utility.observers;

import android.location.Location;

public interface LocationObserver {
    void passLocation(Location location, String readableLoc);

}
