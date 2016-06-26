package ru.teaz.examplerssviewer.ui.callback;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import ru.teaz.examplerssviewer.model.db.News;

/**
 * Created by Teaz on 25.06.2016.
 */
public interface NewsItemFragmentView {

    void showContent(@NonNull News news);
    void showError(@StringRes int errRes);
}
