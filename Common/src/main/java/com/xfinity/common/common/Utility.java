package com.xfinity.common.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

import com.xfinity.common.configs.ComcastController;

public class Utility {

    /**
     * General utility function to print popup messages
     * @param message Response to be printed on the screen
     */
    public static void showMessage(String message) {
        Toast t = Toast.makeText(ComcastController.getApp(), message, Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }

    /**
     * Verify if internet connection is available
     */
    public static boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) ComcastController.getApp()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    /**
     * Utility method that gets the title & description of a topic
     * @param text Input parameter
     */
    public static String[] getTitleAndDescription(String text) {
        String[] out = {"No title", ""};

        if (!notNullNorEmpty(text)) return out;
        if (!text.contains("-")) {
            // Title not available & set the Description only
            out[1] = text;
        } else {
            try {
                // Capture Title & Description
                out[0] = text.substring(0, text.indexOf("-")).trim();
                out[1] = text.substring(text.indexOf("-") + 1).trim();
            } catch (Exception error) {
                System.out.println("Error :: " + error);
            }
        }

        return out;
    }

    /**
     * Utility method that verifies of a String is Null or Empty
     * @param string Input parameter
     */
    public static boolean notNullNorEmpty(String string) {
        return string != null && !string.equals("");
    }

}
