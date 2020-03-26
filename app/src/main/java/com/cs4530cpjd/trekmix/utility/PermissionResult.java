package com.cs4530cpjd.trekmix.utility;

/**
 * Runnable like class which gets called when a permission message is received
 * Runnable is not used because a possible exception needs to be passed upwards
 */
public interface PermissionResult {
    /**
     * Call when a the result for a permission request is in.
     * @param accepted true if permission was granted
     */
    void result(boolean accepted) throws SecurityException;
}
