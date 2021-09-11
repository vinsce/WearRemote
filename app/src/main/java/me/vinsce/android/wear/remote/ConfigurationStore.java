package me.vinsce.android.wear.remote;

import android.content.Context;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConfigurationStore {
    private final Context context;

    public String getServerAddress() {
        return context.getSharedPreferences(Keys._CONFIGURATION, Context.MODE_PRIVATE)
                .getString(Keys.SERVER_ADDRESS, context.getString(R.string.default_server_address));
    }

    public void setServerAddress(String serverAddress) {
        context.getSharedPreferences(Keys._CONFIGURATION, Context.MODE_PRIVATE)
                .edit()
                .putString(Keys.SERVER_ADDRESS, serverAddress)
                .apply();
    }

    public int getServerPort() {
        return context.getSharedPreferences(Keys._CONFIGURATION, Context.MODE_PRIVATE)
                .getInt(Keys.SERVER_PORT, Integer.parseInt(context.getString(R.string.default_server_port)));
    }

    public void setServerPort(int port) {
        context.getSharedPreferences(Keys._CONFIGURATION, Context.MODE_PRIVATE)
                .edit()
                .putInt(Keys.SERVER_PORT, port)
                .apply();
    }

    public float getScaleFactor() {
        return context.getSharedPreferences(Keys._CONFIGURATION, Context.MODE_PRIVATE)
                .getFloat(Keys.TOUCHPAD_SCALE_FACTOR, Float.parseFloat(context.getString(R.string.default_scale_factor)));
    }

    public void setScaleFactor(float scaleFactor) {
        context.getSharedPreferences(Keys._CONFIGURATION, Context.MODE_PRIVATE)
                .edit()
                .putFloat(Keys.TOUCHPAD_SCALE_FACTOR, scaleFactor)
                .apply();
    }

    private interface Keys {
        String _CONFIGURATION = "configuration";

        String SERVER_ADDRESS = "server.address";
        String SERVER_PORT = "server.port";

        String TOUCHPAD_SCALE_FACTOR = "touchpad.scale_factor";
    }

}
