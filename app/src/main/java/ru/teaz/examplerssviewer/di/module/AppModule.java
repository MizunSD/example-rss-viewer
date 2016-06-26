package ru.teaz.examplerssviewer.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.teaz.examplerssviewer.App;

/**
 * Created by Teaz on 19.06.2016.
 */
@Module
public class AppModule {

    private final App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return mApp;
    }
}
