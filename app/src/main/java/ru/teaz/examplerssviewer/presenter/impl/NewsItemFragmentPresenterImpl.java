package ru.teaz.examplerssviewer.presenter.impl;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ru.teaz.examplerssviewer.R;
import ru.teaz.examplerssviewer.data.Filter;
import ru.teaz.examplerssviewer.data.net.RestClient;
import ru.teaz.examplerssviewer.data.repo.NewsRepository;
import ru.teaz.examplerssviewer.di.CustomScope;
import ru.teaz.examplerssviewer.domain.bus.RxBus;
import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.domain.bus.event.FavoriteNewsCountUpdateEvent;
import ru.teaz.examplerssviewer.presenter.NewsItemFragmentPresenter;
import ru.teaz.examplerssviewer.ui.callback.NewsItemFragmentView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

@CustomScope
public class NewsItemFragmentPresenterImpl implements NewsItemFragmentPresenter {

    @Inject
    RxBus rxBus;
    @Inject
    RestClient restClient;
    @Inject
    NewsRepository newsRepository;

    private NewsItemFragmentView mView;

    @Inject
    public NewsItemFragmentPresenterImpl() {}

    @Override
    public void bindView(NewsItemFragmentView view) {
        mView = view;
    }

    @Override
    public void loadNews(int newsId) {
        final Observable<News> observable = newsRepository.getNews(newsId);
        if (observable != null) {
            observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<News>() {
                        @Override
                        public void call(News news) {
                            if (mView != null) {
                                mView.showContent(news);
                            }
                        }
                    });
        } else {
            mView.showError(R.string.error_news_not_found);
        }
    }

    @Override
    public void handleFavoriteClick(@NonNull News news) {
        newsRepository.changeNewsState(news);
        Observable observable = newsRepository.getNewsList(Filter.FAVORITES);
        if (observable != null) {
            observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<News>>() {
                        @Override
                        public void call(List<News> newsList) {
                            rxBus.post(new FavoriteNewsCountUpdateEvent(newsList.size()));
                        }
                    });
        }
    }
}
