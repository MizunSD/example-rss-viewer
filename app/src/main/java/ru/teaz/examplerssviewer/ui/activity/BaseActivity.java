package ru.teaz.examplerssviewer.ui.activity;

import android.support.v7.app.AppCompatActivity;

import ru.teaz.examplerssviewer.App;
import ru.teaz.examplerssviewer.di.component.AppComponent;
import ru.teaz.examplerssviewer.di.module.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity {

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

}
