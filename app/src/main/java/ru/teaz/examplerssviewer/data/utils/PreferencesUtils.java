package ru.teaz.examplerssviewer.data.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import org.joda.time.LocalDateTime;

public class PreferencesUtils {

    private static final String PREFS = "prefs";
    private static final String LAST_UPDATE_TIME = "last_update_time";

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public PreferencesUtils(Context context) {
        mPrefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        mEditor.putString(LAST_UPDATE_TIME, lastUpdateTime.toString());
        mEditor.commit();
    }

    @Nullable
    public LocalDateTime getLastUpdateTime() {
        final String lastUpdateTime = mPrefs.getString(LAST_UPDATE_TIME, null);
        if (lastUpdateTime == null) {
            return null;
        } else {
            return new LocalDateTime(lastUpdateTime);
        }
    }
}
