package ru.teaz.examplerssviewer.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ru.teaz.examplerssviewer.R;
import ru.teaz.examplerssviewer.di.component.NewsComponent;
import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.presenter.impl.NewsItemFragmentPresenterImpl;
import ru.teaz.examplerssviewer.ui.callback.NewsItemFragmentView;

public class NewsItemFragment extends BaseFragment implements NewsItemFragmentView {

    private static final String ARG_NEWS = "arg_news";
    private static final String STATE_NEWS = "state_news";

    @InjectView(R.id.fab_favorite)
    FloatingActionButton fabFavorite;
    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_date) TextView tvDate;
    @InjectView(R.id.tv_content) TextView tvContent;

    private int mNewsId;
    private News mNews;

    @Inject
    NewsItemFragmentPresenterImpl presenter;

    public static NewsItemFragment newInstance(int newsId) {
        final NewsItemFragment fragment = new NewsItemFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_NEWS, newsId);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsItemFragment() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.reset(this);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(NewsComponent.class).inject(this);
        presenter.bindView(this);
        if (savedInstanceState == null) {
            final Bundle args = getArguments();
            if (args != null) {
                mNewsId = args.getInt(ARG_NEWS);
            }
        } else {
            mNewsId = savedInstanceState.getInt(STATE_NEWS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_NEWS, mNewsId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_item, container, false);
        ButterKnife.inject(this, view);
        presenter.loadNews(mNewsId);
        return view;
    }

    @OnClick(R.id.fab_favorite)
    public void addInFavorites() {
        presenter.handleFavoriteClick(mNews);
        final boolean currentState = mNews.isFavorite();
        mNews.setFavorite(!currentState);
        int favoriteRes = mNews.isFavorite() ? R.drawable.ic_heart_white_24dp:
                R.drawable.ic_heart_outline_white_24dp;

        Drawable favoriteDrawable = ContextCompat.getDrawable(getActivity(), favoriteRes);
        fabFavorite.setImageDrawable(favoriteDrawable);
    }

    @Override
    public void showContent(@NonNull News news) {
        mNews = news;
        tvTitle.setText(mNews.getTitle());
        final DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
        tvDate.setText(dtf.print(mNews.getPubDate()));
        tvContent.setText(mNews.getDescription());

        int favoriteRes = mNews.isFavorite() ? R.drawable.ic_heart_white_24dp:
                R.drawable.ic_heart_outline_white_24dp;

        Drawable favoriteDrawable = ContextCompat.getDrawable(getActivity(), favoriteRes);
        fabFavorite.setImageDrawable(favoriteDrawable);
    }

    @Override
    public void showError(@StringRes int errRes) {
        Snackbar.make(coordinatorLayout, getString(errRes), Snackbar.LENGTH_LONG)
                .show();
    }
}
