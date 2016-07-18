package ru.teaz.examplerssviewer.data.db.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

import org.joda.time.LocalDateTime;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import ru.teaz.examplerssviewer.data.NewsSource;
import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.data.utils.RxUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NewsDAO extends BaseDaoImpl<News, Integer> {

    public NewsDAO(@NonNull final ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, News.class);
    }

    public void saveNews(@NonNull final List<News> newsList) throws SQLException {
        final Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                callBatchTasks(new Callable<News>() {
                    @Override
                    public News call() throws SQLException {
                        for (News news : newsList) {
                            try {
                                create(news);
                            } catch (SQLException e) {
                                Log.e("RSS", "Skip news: " + news.getTitle());
                            }
                        }
                        return null;
                    }
                });
                return null;
            }
        };
        RxUtils.makeObservable(callable)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {

                    }
                });
    }

    public Observable<List<News>> selectAllNews() {
        final Callable<List<News>> callable = new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                return queryBuilder().orderBy(News.FIELD_DATE, false).query();
            }
        };
        return RxUtils.makeObservable(callable)
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<List<News>> selectFavoriteNews() {
        final Callable<List<News>> callable = new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                return queryBuilder().orderBy(News.FIELD_DATE, false)
                        .where()
                        .eq(News.FIELD_FAVORITE, true)
                        .query();
            }
        };
        return RxUtils.makeObservable(callable)
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<List<News>> selectUnreadNews() {
        final Callable<List<News>> callable = new Callable<List<News>>() {
            @Override
            public List<News> call() throws Exception {
                return queryBuilder().orderBy(News.FIELD_DATE, false)
                        .where()
                        .eq(News.FIELD_READ, false)
                        .query();
            }
        };
        return RxUtils.makeObservable(callable)
                .subscribeOn(Schedulers.newThread());
    }

    public void changeState(final News news) throws SQLException {
        final Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                UpdateBuilder<News, Integer> updateBuilder = updateBuilder();
                updateBuilder.where().idEq(news.getId());
                updateBuilder.updateColumnValue(News.FIELD_FAVORITE, !news.isFavorite());
                updateBuilder.update();
                return null;
            }
        };
        RxUtils.makeObservable(callable)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {

                    }
                });
    }

    public Observable setRead(final News news) throws SQLException {
        final Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                UpdateBuilder<News, Integer> updateBuilder = updateBuilder();
                updateBuilder.where().idEq(news.getId());
                updateBuilder.updateColumnValue(News.FIELD_READ, true);
                updateBuilder.update();
                return null;
            }
        };
        return RxUtils.makeObservable(callable)
                .subscribeOn(Schedulers.newThread());
    }

    public Observable setAllRead() throws SQLException {
        final Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                UpdateBuilder<News, Integer> updateBuilder = updateBuilder();
                updateBuilder.updateColumnValue(News.FIELD_READ, true);
                updateBuilder.update();
                return null;
            }
        };
        return RxUtils.makeObservable(callable)
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<News> getNewsById(final int newsId) throws SQLException {
        final Callable<News> callable = new Callable<News>() {
            @Override
            public News call() throws Exception {
                return queryForId(newsId);
            }
        };
        return RxUtils.makeObservable(callable)
                .subscribeOn(Schedulers.newThread());
    }

    public LocalDateTime getLastNewsTime(final NewsSource source) throws SQLException {
        List<LocalDateTime> times =
                        queryRaw("SELECT " + News.FIELD_DATE + " FROM " + News.TABLE_NAME
                                + " WHERE "
                                + News.FIELD_SOURCE + " = \"" + source + "\""
                                + " ORDER BY " + News.FIELD_DATE + " DESC"
                                + " LIMIT 1", new RawRowMapper<LocalDateTime>() {
                            @Override
                            public LocalDateTime mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
                                return LocalDateTime.parse(resultColumns[0].replace(" ", "T"));
                            }
                        }).getResults();
        return times.isEmpty() ? null : times.get(0);
    }
}
