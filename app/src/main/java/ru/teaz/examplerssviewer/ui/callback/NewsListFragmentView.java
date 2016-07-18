package ru.teaz.examplerssviewer.ui.callback;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.List;

import ru.teaz.examplerssviewer.data.db.model.News;

public interface NewsListFragmentView {

    void showProgress();
    void hideProgress();
    void showMessage(@StringRes int msgId);
    void showError(String errMessage);

    void updateContent(@NonNull List<News> newsList);
    void markAllRead();
    void newsClick(@NonNull News news);
}
