package ru.teaz.examplerssviewer.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ru.teaz.examplerssviewer.data.net.RestClient;
import ru.teaz.examplerssviewer.data.repo.NewsRepository;
import ru.teaz.examplerssviewer.di.module.AppModule;
import ru.teaz.examplerssviewer.domain.bus.RxBus;
import ru.teaz.examplerssviewer.ui.activity.MainActivity;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MainActivity mainActivity);

    Context context();
    RxBus rxBus();
    NewsRepository newsRepository();
    RestClient restClient();
}
