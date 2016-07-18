package ru.teaz.examplerssviewer.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ru.teaz.examplerssviewer.di.CustomScope;

@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @CustomScope
    Activity activity() {
        return mActivity;
    }
}
