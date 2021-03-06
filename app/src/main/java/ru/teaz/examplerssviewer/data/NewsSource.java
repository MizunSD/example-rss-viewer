package ru.teaz.examplerssviewer.data;

import android.support.annotation.NonNull;

public enum  NewsSource {

    LENTA("lenta.ru", "http://www.lenta.ru", "rss"),
    GAZETA("газета", "http://www.gazeta.ru", "export/rss/lenta.xml");

    private String mName;
    private String mUrl;
    private String mPath;

    NewsSource(@NonNull String name, @NonNull String url, @NonNull String path) {
        mName = name;
        mUrl = url;
        mPath = path;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }

    @NonNull
    public String getPath() {
        return mPath;
    }
}
