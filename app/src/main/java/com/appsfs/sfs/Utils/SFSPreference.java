package com.appsfs.sfs.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by longdv on 4/9/16.
 */
public class SFSPreference {
    private static SFSPreference instance;
    private SharedPreferences preferences;

    private SFSPreference(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SFSPreference getInstance(Context context){
        if (instance == null) {
            instance = new SFSPreference(context);
        }

        return instance;
    }

    public boolean contain(String key){
        return preferences.contains(key);
    }

    public SFSPreference putInt(String key, int value) {
        preferences.edit().putInt(key, value).commit();
        return instance;
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public SFSPreference putLong(String key, long value) {
        preferences.edit().putLong(key, value).commit();
        return instance;
    }

    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public SFSPreference putString(String key, String value) {
        preferences.edit().putString(key, value).commit();
        return instance;
    }

    public String getString(String key, String defaultValue){
        return preferences.getString(key, defaultValue);
    }

    public SFSPreference putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).commit();
        return instance;
    }

    public boolean getBoolean(String key, boolean defaultValue){
        return preferences.getBoolean(key, defaultValue);
    }

    public SFSPreference putFloat(String key, float value) {
        preferences.edit().putFloat(key, value).commit();
        return instance;
    }

    public float getFloat(String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

}
