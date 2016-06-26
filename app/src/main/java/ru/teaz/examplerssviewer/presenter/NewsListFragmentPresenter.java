package ru.teaz.examplerssviewer.presenter;

import android.support.annotation.NonNull;

import ru.teaz.examplerssviewer.model.Filter;
import ru.teaz.examplerssviewer.model.db.News;
import ru.teaz.examplerssviewer.ui.callback.NewsListFragmentView;

/**
 * Created by Teaz on 25.06.2016.
 */
public interface NewsListFragmentPresenter {

    void bindView(NewsListFragmentView view);

    void loadNewsList(@NonNull Filter filter);
    void downloadNews(@NonNull Filter filter);
    void searchNews(@NonNull String searchString);
    void readAllNews(@NonNull Filter filter);
    void newsClick(News news);
}
