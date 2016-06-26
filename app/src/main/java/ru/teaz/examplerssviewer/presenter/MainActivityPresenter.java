package ru.teaz.examplerssviewer.presenter;

import ru.teaz.examplerssviewer.model.Filter;
import ru.teaz.examplerssviewer.ui.callback.RssFeedView;

/**
 * Created by Teaz on 19.06.2016.
 */
public interface MainActivityPresenter {

    void bindView(RssFeedView view);

    void loadNewsFeed(Filter filter);
    void loadNewsItem(int id);
    void updateFavoriteCount();
    void updateUnreadCount();
}
