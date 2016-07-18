package ru.teaz.examplerssviewer;

import android.app.Application;

import ru.teaz.examplerssviewer.di.component.AppComponent;
import ru.teaz.examplerssviewer.di.component.DaggerAppComponent;
import ru.teaz.examplerssviewer.di.module.AppModule;

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    private void initInjector() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
