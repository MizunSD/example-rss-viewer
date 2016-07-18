package ru.teaz.examplerssviewer.presenter.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import ru.teaz.examplerssviewer.R;
import ru.teaz.examplerssviewer.data.NewsSource;
import ru.teaz.examplerssviewer.data.net.RestClient;
import ru.teaz.examplerssviewer.data.net.XmlRss;
import ru.teaz.examplerssviewer.data.repo.NewsRepository;
import ru.teaz.examplerssviewer.data.utils.NewsUtils;
import ru.teaz.examplerssviewer.di.CustomScope;
import ru.teaz.examplerssviewer.domain.bus.RxBus;
import ru.teaz.examplerssviewer.data.Filter;
import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.domain.bus.event.UnreadNewsCountUpdateEvent;
import ru.teaz.examplerssviewer.presenter.NewsListFragmentPresenter;
import ru.teaz.examplerssviewer.ui.callback.NewsListFragmentView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@CustomScope
public class NewsListFragmentPresenterImpl implements NewsListFragmentPresenter {

    @Inject
    RestClient restClient;
    @Inject
    RxBus rxBus;
    @Inject
    NewsRepository newsRepository;

    private NewsListFragmentView mView;
    private AtomicBoolean mIsLoading = new AtomicBoolean(false);

    @Inject
    public NewsListFragmentPresenterImpl() {}

    @Override
    public void bindView(NewsListFragmentView view) {
        mView = view;
    }

    @Override
    public void readAllNews(@NonNull final Filter filter) {
        Observable<List<News>> observable = newsRepository.readAllNews();
        if (observable != null) {
            observable
                    .flatMap(new Func1<Object, Observable<List<News>>>() {
                        @Override
                        public Observable<List<News>> call(Object o) {
                            return newsRepository.getNewsList(filter);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<News>>() {
                        @Override
                        public void call(List<News> newsList) {
                            rxBus.post(new UnreadNewsCountUpdateEvent(0));
                            if (mView != null) {
                                mView.markAllRead();
                                mView.showMessage(R.string.read_all_news);
                            }
                        }
                    });
        }
    }

    @Override
    public void loadNewsList(@NonNull final Filter filter) {
        Observable<List<News>> observable = newsRepository.getNewsList(filter);
        if (observable != null) {
            observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<News>>() {
                        @Override
                        public void call(List<News> newsList) {
                            if (newsRepository.isFirstRun()) {
                                downloadNews(filter);
                            } else {
                                mView.hideProgress();
                                mView.updateContent(newsList);
                            }
                        }
                    });
        }
    }

    @Override
    public void newsClick(News news) {
        if (!news.isRead()) {
            Observable observable = newsRepository.readNews(news);
            if (observable != null) {
                observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1() {
                            @Override
                            public void call(Object o) {
                                updateUnreadCount();
                            }
                        });
            }
        }

        mView.newsClick(news);
    }

    @Override
    public void downloadNews(@NonNull final Filter filter) {
        if (!mIsLoading.get()) {
            mIsLoading.set(true);
            mView.showProgress();
            Observable.from(NewsSource.values())
                    .flatMap(new Func1<NewsSource, Observable<XmlRss>>() {
                        @Override
                        public Observable<XmlRss> call(NewsSource source) {
                            return restClient.getRss(source);
                        }
                    })
                    .map(new Func1<XmlRss, List<News>>() {
                        @Override
                        public List<News> call(XmlRss xmlRss) {
                            List<News> newsList = NewsUtils.rssToNews(xmlRss);
                            return NewsUtils.filterByDate(newsList, newsRepository.getLastNewsTime(newsList.get(0).getSource()));
                        }
                    })
                    .doOnNext(new Action1<List<News>>() {
                        @Override
                        public void call(List<News> newsList) {
                            newsRepository.createNews(newsList);
                        }
                    })
                    .doAfterTerminate(new Action0() {
                        @Override
                        public void call() {
                            updateUnreadCount();
                            mIsLoading.set(false);
                            Observable dbObservable = newsRepository.getNewsList(filter);
                            if (dbObservable != null) {
                                dbObservable
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<List<News>>() {
                                            @Override
                                            public void call(List<News> newsList) {
                                                mView.updateContent(newsList);
                                            }
                                        });
                            }
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<News>>() {
                        @Override
                        public void onCompleted() {
                            if (mView != null) {
                                mView.hideProgress();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mView != null) {
                                mView.showError(e.getMessage());
                                mView.hideProgress();
                            }
                        }

                        @Override
                        public void onNext(List<News> newsList) {

                        }
            });
        }
    }

    private void updateUnreadCount() {
        Observable observable = newsRepository.getNewsList(Filter.UNREAD);
        if (observable != null) {
            observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<News>>() {
                        @Override
                        public void call(List<News> newsList) {
                            rxBus.post(new UnreadNewsCountUpdateEvent(newsList.size()));
                        }
                    });
        }
    }
}
