package ru.teaz.examplerssviewer.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Teaz on 19.06.2016.
 */
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity activity() {
        return mActivity;
    }
}
