package ru.teaz.examplerssviewer.ui.callback;

import android.support.annotation.StringRes;

import ru.teaz.examplerssviewer.data.Filter;

public interface RssFeedView {

    void updateUnreadCount(int count);
    void updateFavoriteCount(int count);
    void showFeed(Filter filter);
    void showItem(int id);
    void showError(@StringRes int resId);

}
