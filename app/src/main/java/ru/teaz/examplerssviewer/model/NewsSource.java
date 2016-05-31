package ru.teaz.examplerssviewer.model;

import android.support.annotation.NonNull;

/**
 * Created by Sergey on 31.05.2016.
 */
public enum  NewsSource {

    LENTA("lenta.ru", "http://www.lenta.ru", "rss"),
    GAZETA("gazeta.ru", "http://www.gazeta.ru", "export/rss/lenta.xml");

    private String mName;
    private String mUrl;
    private String mPath;

    NewsSource(@NonNull String name, @NonNull String url, @NonNull String path) {
        mName = name;
        mUrl = url;
        mPath = path;
    }

    @NonNull
    public String getmName() {
        return mName;
    }

    @NonNull
    public String getmUrl() {
        return mUrl;
    }

    @NonNull
    public String getmPath() {
        return mPath;
    }
}
