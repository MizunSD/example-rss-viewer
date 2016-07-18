package ru.teaz.examplerssviewer.ui.callback;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import ru.teaz.examplerssviewer.data.db.model.News;

public interface NewsItemFragmentView {

    void showContent(@NonNull News news);
    void showError(@StringRes int errRes);
}
