package ru.teaz.examplerssviewer.presenter;

import android.support.annotation.NonNull;

import ru.teaz.examplerssviewer.model.db.News;
import ru.teaz.examplerssviewer.ui.callback.NewsItemFragmentView;

/**
 * Created by Teaz on 25.06.2016.
 */
public interface NewsItemFragmentPresenter {

    void bindView(NewsItemFragmentView view);

    void loadNews(int newsId);
    void handleFavoriteClick(@NonNull News news);
}
