package ru.teaz.examplerssviewer.presenter.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.teaz.examplerssviewer.data.Filter;
import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.data.repo.NewsRepository;
import ru.teaz.examplerssviewer.presenter.MainActivityPresenter;
import ru.teaz.examplerssviewer.ui.callback.RssFeedView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

@Singleton
public class MainActivityPresenterImpl implements MainActivityPresenter {

    @Inject
    NewsRepository newsRepository;

    RssFeedView mView;

    @Inject
    public MainActivityPresenterImpl() {}

    @Override
    public void bindView(RssFeedView view) {
        mView = view;
    }

    @Override
    public void loadNewsFeed(Filter filter) {
        mView.showFeed(filter);
    }

    @Override
    public void loadNewsItem(int id) {
        mView.showItem(id);
    }

    @Override
    public void updateFavoriteCount() {
        Observable<List<News>> observable = newsRepository.getNewsList(Filter.FAVORITES);
        if (observable != null) {
            observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<News>>() {
                        @Override
                        public void call(List<News> newsList) {
                            if (mView != null) {
                                mView.updateFavoriteCount(newsList.size());
                            }
                        }
                    });
        }
    }

    @Override
    public void updateUnreadCount() {
        Observable<List<News>> observable = newsRepository.getNewsList(Filter.UNREAD);
        if (observable != null) {
            observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<News>>() {
                        @Override
                        public void call(List<News> newsList) {
                            if (mView != null) {
                                mView.updateUnreadCount(newsList.size());
                            }
                        }
                    });
        }
    }
}
