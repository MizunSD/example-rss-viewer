package ru.teaz.examplerssviewer.presenter.impl;

import javax.inject.Inject;

import ru.teaz.examplerssviewer.di.CustomScope;
import ru.teaz.examplerssviewer.domain.bus.RxBus;
import ru.teaz.examplerssviewer.model.Filter;
import ru.teaz.examplerssviewer.presenter.MainActivityPresenter;
import ru.teaz.examplerssviewer.ui.callback.RssFeedView;

/**
 * Created by Teaz on 19.06.2016.
 */
@CustomScope
public class MainActivityPresenterImpl implements MainActivityPresenter {

    @Inject
    RxBus rxBus;

    @Override
    public void bindView(RssFeedView view) {

    }

    @Override
    public void loadNewsFeed(Filter filter) {

    }

    @Override
    public void loadNewsItem(int id) {

    }

    @Override
    public void updateFavoriteCount() {

    }

    @Override
    public void updateUnreadCount() {

    }
}
