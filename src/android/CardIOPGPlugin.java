package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import android.content.Intent;

/*
 * This Plugin handles the Card.io library to scan credit cards using the phone's camera.
 *
 * @class CardIOPGPlugin
 * @extend CordovaPlugin
 * @author Leonardo Peralta <leo.peralta@gmail.com>
 *         Tomas Campodonico <tomas.campodonico@gmail.com>
 *
 */
public class CardIOPGPlugin extends CordovaPlugin {

    private JSONObject result = new JSONObject();
    private CallbackContext callbackContext = null;

    /*
     * Executes the request and returns a boolean.
     *
     * @param action        The action to execute.
     * @param args          JSONArry of arguments for the plugin.
     * @param callbackContext    The callback used when calling back into JavaScript.
     * @return              A boolean.
     *
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("scan")) {
            this.scan(args, callbackContext);
        } 
        else if (action.equals("canScan")) {
            return this.canScan();   
        }
        else if (action.equals("version")) {
            callbackContext.success(this.version());
        }
        else {
            return false;
        }
        return true;
    }

    /*
    * Execute the callback once the scanning has finished.
    *
    * @param requestCode       An integer to identify the sender activity.
    * @param resultCode        An integer representing the result of the activity execution.
    * @param data              The data sent by the sender activity.
    *
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                try {
                    this.result.put("cardNumber", scanResult.cardNumber);
                    this.result.put("redacted_card_number", scanResult.getRedactedCardNumber());

                    if (scanResult.isExpiryValid()) {
                        this.result.put("expiry_month", scanResult.expiryMonth);
                        this.result.put("expiry_year", scanResult.expiryYear);
                    }

                    if (scanResult.cvv != null) {
                        this.result.put("cvv", scanResult.cvv);
                    }

                    if (scanResult.postalCode != null) {
                        this.result.put("zip", scanResult.postalCode);
                    }

                    this.callbackContext.success(this.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }    
            }
            
        }
    }
    

    /* PRIVATE METHODS */


    /*
    * Performs the card scanning calling the CardIOActivity.
    *
    * @private
    * @param args               JSONArry of arguments for the plugin.
    * @param callbackContext    The callback used when calling back into JavaScript.
    * @return                   A boolean indicating whether it succeed or not.
    *
    */
    private boolean scan(JSONArray args, CallbackContext callbackContext) {
        try {
            Intent scanIntent = new Intent(this.cordova.getActivity().getApplicationContext(), CardIOActivity.class);

            // required for authentication with card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_APP_TOKEN, (String) args.get(0));

            JSONObject options = (JSONObject) args.get(1);
            System.out.println(options);

            // customize these values to suit your needs.
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, options.getBoolean("collect_expiry"));
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, options.getBoolean("collect_cvv"));
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, options.getBoolean("collect_zip"));
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, options.getBoolean("disable_manual_entry_buttons"));

            this.cordova.setActivityResultCallback(this);
            this.cordova.getActivity().startActivityForResult(scanIntent, 0);
            this.callbackContext = callbackContext;
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /*
    * Returns the Card.io SDK version in use.
    *
    * @private
    * @return  A String with the Card.io SDK version.
    *
    */
    private String version() {
        return CardIOActivity.sdkVersion();
    }

    /*
    * Returns if the phone is able to scan using the camera.
    *
    * @private
    * @return  A boolean indicating if it can scan.
    *
    */
    private boolean canScan() {
        return CardIOActivity.canReadCardWithCamera();
    }
}