package ru.teaz.examplerssviewer.presenter;

import android.support.annotation.NonNull;

import ru.teaz.examplerssviewer.data.Filter;
import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.ui.callback.NewsListFragmentView;

public interface NewsListFragmentPresenter {

    void bindView(NewsListFragmentView view);

    void loadNewsList(@NonNull Filter filter);
    void downloadNews(@NonNull Filter filter);
    void readAllNews(@NonNull Filter filter);
    void newsClick(News news);
}
