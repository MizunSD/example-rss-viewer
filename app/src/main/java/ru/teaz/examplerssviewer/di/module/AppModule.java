package ru.teaz.examplerssviewer.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.teaz.examplerssviewer.App;
import ru.teaz.examplerssviewer.data.net.RestClient;
import ru.teaz.examplerssviewer.data.repo.NewsRepository;
import ru.teaz.examplerssviewer.data.repo.NewsRepositoryImpl;
import ru.teaz.examplerssviewer.domain.bus.RxBus;

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

    @Provides
    @Singleton
    RxBus provideRxBus() {
        return new RxBus();
    }

    @Provides
    @Singleton
    NewsRepository provideNewsRepository() {
        return new NewsRepositoryImpl(mApp);
    }

    @Provides
    @Singleton
    RestClient provideRestClient() {
        return new RestClient();
    }
}
