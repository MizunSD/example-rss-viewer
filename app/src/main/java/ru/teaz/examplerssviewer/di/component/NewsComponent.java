package ru.teaz.examplerssviewer.di.component;

import dagger.Component;
import ru.teaz.examplerssviewer.di.CustomScope;
import ru.teaz.examplerssviewer.di.module.ActivityModule;
import ru.teaz.examplerssviewer.di.module.NewsModule;
import ru.teaz.examplerssviewer.ui.fragment.NewsItemFragment;
import ru.teaz.examplerssviewer.ui.fragment.NewsListFragment;

@CustomScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, NewsModule.class})
public interface NewsComponent {

    void inject(NewsListFragment listFragment);
    void inject(NewsItemFragment itemFragment);
}
