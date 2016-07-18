package ru.teaz.examplerssviewer.data.repo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.LocalDateTime;

import java.util.List;

import ru.teaz.examplerssviewer.data.Filter;
import ru.teaz.examplerssviewer.data.NewsSource;
import ru.teaz.examplerssviewer.data.db.model.News;
import rx.Observable;

public interface NewsRepository {

    @Nullable
    Observable<List<News>> getNewsList(@NonNull Filter filter);

    @Nullable
    Observable<News> getNews(int newsId);

    void createNews(@NonNull List<News> newsList);

    void changeNewsState(@NonNull News news);

    @Nullable
    Observable readAllNews();

    Observable readNews(@NonNull News news);

    boolean isFirstRun();

    @Nullable
    LocalDateTime getLastUpdateTime();

    void setLastUpdateTime(@NonNull LocalDateTime lastUpdateTime);

    @Nullable
    LocalDateTime getLastNewsTime(NewsSource source);
}
