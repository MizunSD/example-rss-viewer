package ru.teaz.examplerssviewer.presenter;

import android.support.annotation.NonNull;

import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.ui.callback.NewsItemFragmentView;

public interface NewsItemFragmentPresenter {

    void bindView(NewsItemFragmentView view);

    void loadNews(int newsId);
    void handleFavoriteClick(@NonNull News news);
}
