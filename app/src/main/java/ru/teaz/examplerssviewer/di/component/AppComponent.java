package ru.teaz.examplerssviewer.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.teaz.examplerssviewer.di.module.AppModule;
import ru.teaz.examplerssviewer.ui.activity.MainActivity;

/**
 * Created by Teaz on 19.06.2016.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
