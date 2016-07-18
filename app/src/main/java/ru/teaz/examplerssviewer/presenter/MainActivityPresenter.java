package ru.teaz.examplerssviewer.presenter;

import ru.teaz.examplerssviewer.data.Filter;
import ru.teaz.examplerssviewer.ui.callback.RssFeedView;

public interface MainActivityPresenter {

    void bindView(RssFeedView view);

    void loadNewsFeed(Filter filter);
    void loadNewsItem(int id);
    void updateFavoriteCount();
    void updateUnreadCount();
}
