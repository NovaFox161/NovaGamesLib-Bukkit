package com.cloudcraftgaming.novagameslib.kit;

import com.sun.istack.internal.Nullable;

/**
 * Created by Nova Fox on 1/12/17.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
public class KitResponse {
    private final Boolean successful;
    private final KitFailureReason reason;

    /**
     * Creates a new KitResponse Object for sending detailed info about a kit operation.
     * @param _successful Whether or not the operation was successful.
     * @param _reason If the operation failed, the reason why, else <code>null</code>.
     */
    public KitResponse(Boolean _successful, @Nullable KitFailureReason _reason) {
        successful = _successful;
        if (_reason != null) {
            reason = _reason;
        } else {
            reason = KitFailureReason.NONE;
        }
    }

    /**
     * Gets whether or not the operation was successful.
     * @return <code>true</code> if successful, else <code>false</code>.
     */
    public Boolean wasSuccessful() {
        return successful;
    }

    /**
     * Gets the reason the operation failed, if it did fail.
     * @return The reason the operation failed.
     */
    public KitFailureReason getReason() {
        return reason;
    }
}