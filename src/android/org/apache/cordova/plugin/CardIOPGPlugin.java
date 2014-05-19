package org.apache.cordova.plugin;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class CardIOPGPlugin extends Plugin {

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action        The action to execute.
     * @param args          JSONArry of arguments for the plugin.
     * @param callbackId    The callback id used when calling back into JavaScript.
     * @return              A PluginResult object with a status and message.
     */
    public PluginResult execute(String action, JSONArray args, String callbackId) {
        try {
            if (action.equals("scan")) {
                this.scan(args,callbackId);   
            } 
            else if (action.equals("canScan")) {
                this.canScan(args,callbackId);   
            }
            else if (action.equals("version")) {
                this.version(args,callbackId);
            }
            else {
                return new PluginResult(PluginResult.Status.INVALID_ACTION);
            }
        } catch (JSONException e) {
            return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
        }
    }

    private PluginResult scan(JSONArray args, String callbackId) {
        try {
                return new PluginResult(PluginResult.Status.OK, "demo");
            } 
            else {
                return new PluginResult(PluginResult.Status.INVALID_ACTION);
            }
        } catch (JSONException e) {
            return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
        }
    }
    
    private PluginResult version() {
        try {
                return new PluginResult(PluginResult.Status.OK, "demo");
            } 
            else {
                return new PluginResult(PluginResult.Status.INVALID_ACTION);
            }
        } catch (JSONException e) {
            return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
        }
    }

    private PluginResult canScan(String action, JSONArray args, String callbackId) {
        try {
                return new PluginResult(PluginResult.Status.OK, "demo");
            } 
            else {
                return new PluginResult(PluginResult.Status.INVALID_ACTION);
            }
        } catch (JSONException e) {
            return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
        }
    }
}