package com.cloudcraftgaming.novagameslib.api.kit;

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
     * Use {@link #KitResponse(Boolean, KitFailureReason)} if a specific failure reason exists!
     * @param _successful Whether or not the operation was successful.
     */
    public KitResponse(Boolean _successful) {
        successful = _successful;
        reason = KitFailureReason.NONE;
    }

    /**
     * Creates a new KitResponse Object for sending detailed info about a kit operation.
     * Use {@link #KitResponse(Boolean)} if there is no reason for failure or did not fail!
     * @param _successful Whether or not the operation was successful.
     * @param _reason If the operation failed, the reason why.
     */
    public KitResponse(Boolean _successful, KitFailureReason _reason) {
        successful = _successful;
        reason = _reason;
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