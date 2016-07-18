package ru.teaz.examplerssviewer.data.repo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.joda.time.LocalDateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import ru.teaz.examplerssviewer.data.Filter;
import ru.teaz.examplerssviewer.data.NewsSource;
import ru.teaz.examplerssviewer.data.db.DbHelper;
import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.data.utils.PreferencesUtils;
import ru.teaz.examplerssviewer.data.utils.RxUtils;
import rx.Observable;
import rx.functions.Action1;

public class NewsRepositoryImpl implements NewsRepository {

    private static final String TAG = NewsRepositoryImpl.class.getCanonicalName();

    private List<News> mCachedAllNews = new ArrayList<>();
    private List<News> mCachedUnreadNews = new ArrayList<>();
    private List<News> mCachedFavoriteNews = new ArrayList<>();

    DbHelper mDbHelper;
    PreferencesUtils mPreferencesUtils;

    @Inject
    public NewsRepositoryImpl(@NonNull Context context) {
        mDbHelper = new DbHelper(context);
        mPreferencesUtils = new PreferencesUtils(context);
    }

    @Nullable
    @Override
    public Observable<News> getNews(int newsId) {
        News news = getNewsFromCache(newsId);
        if (news == null) {
            try {
                return mDbHelper.getNewsDao().getNewsById(newsId);
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Observable readAllNews() {
        clearCache();
        try {
            return mDbHelper.getNewsDao().setAllRead();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public boolean isFirstRun() {
         return getLastUpdateTime() == null;
    }

    @Override
    public void setLastUpdateTime(@NonNull LocalDateTime lastUpdateTime) {
        mPreferencesUtils.setLastUpdateTime(lastUpdateTime);
    }

    @Nullable
    @Override
    public Observable<List<News>> getNewsList(@NonNull Filter filter) {
        switch (filter) {
            case ALL_NEWS:
                return getAllNews();
            case FAVORITES:
                return getFavoriteNews();
            case UNREAD:
                return getUnreadNews();
            default:
                return null;
        }
    }

    @Override
    public Observable readNews(@NonNull News news) {
        clearCache();
        try {
            return mDbHelper.getNewsDao().setRead(news);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public void changeNewsState(@NonNull News news) {
        mCachedFavoriteNews = Collections.EMPTY_LIST;
        try {
            mDbHelper.getNewsDao().changeState(news);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Nullable
    @Override
    public LocalDateTime getLastUpdateTime() {
        return mPreferencesUtils.getLastUpdateTime();
    }

    @Override
    public void createNews(@NonNull List<News> newsList) {
        clearCache();
        try {
            mDbHelper.getNewsDao().saveNews(newsList);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Nullable
    @Override
    public LocalDateTime getLastNewsTime(NewsSource source) {
        try {
            return mDbHelper.getNewsDao().getLastNewsTime(source);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    private Observable<List<News>> getAllNews() {
        if (mCachedAllNews.isEmpty()) {
            try {
                return mDbHelper.getNewsDao().selectAllNews()
                        .doOnNext(new Action1<List<News>>() {
                            @Override
                            public void call(List<News> newsList) {
                                mCachedAllNews = new ArrayList<>(newsList);
                            }
                        });
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return RxUtils.makeObservable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                return new ArrayList<>(mCachedAllNews);
            }
        });
    }

    private Observable<List<News>> getFavoriteNews() {
        if (mCachedFavoriteNews.isEmpty()) {
            try {
                return mDbHelper.getNewsDao().selectFavoriteNews()
                        .doOnNext(new Action1<List<News>>() {
                            @Override
                            public void call(List<News> newsList) {
                                mCachedFavoriteNews = new ArrayList<>(newsList);
                            }
                        });
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return RxUtils.makeObservable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                return new ArrayList<>(mCachedFavoriteNews);
            }
        });
    }

    private Observable<List<News>> getUnreadNews() {
        if (mCachedUnreadNews.isEmpty()) {
            try {
                return mDbHelper.getNewsDao().selectUnreadNews()
                        .doOnNext(new Action1<List<News>>() {
                            @Override
                            public void call(List<News> newsList) {
                                mCachedUnreadNews = new ArrayList<>(newsList);
                            }
                        });
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return RxUtils.makeObservable(new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                return new ArrayList<>(mCachedUnreadNews);
            }
        });
    }

    @Nullable
    private News getNewsFromCache(int newsId) {
        for(News news: mCachedUnreadNews) {
            if (news.getId() == newsId) {
                return news;
            }
        }
        return null;
    }

    private void clearCache() {
        mCachedAllNews.clear();
        mCachedFavoriteNews.clear();
        mCachedUnreadNews.clear();
    }
}
