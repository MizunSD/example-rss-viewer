package ru.teaz.examplerssviewer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ru.teaz.examplerssviewer.R;
import ru.teaz.examplerssviewer.di.component.NewsComponent;
import ru.teaz.examplerssviewer.model.Filter;
import ru.teaz.examplerssviewer.model.db.News;
import ru.teaz.examplerssviewer.presenter.impl.NewsListFragmentPresenterImpl;
import ru.teaz.examplerssviewer.ui.adapter.NewsListRecyclerViewAdapter;
import ru.teaz.examplerssviewer.ui.callback.NewsListFragmentView;
import ru.teaz.examplerssviewer.ui.view.NestedScrollableSwipeRefreshLayout;

/**
 * Created by Teaz on 19.06.2016.
 */
public class NewsListFragment extends BaseFragment implements NewsListFragmentView, NewsListRecyclerViewAdapter.OnNewsClickListener {

    private static final String ARG_FILTER_TYPE = "arg_filter_type";
    private static final String STATE_FILTER_TYPE = "state_filter_type";

    private Callbacks mCallback;
    private Filter mFilter;
    private NewsListRecyclerViewAdapter mAdapter;

    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @InjectView(R.id.contentView)
    NestedScrollableSwipeRefreshLayout refreshLayout;
    @InjectView(R.id.news_list)
    RecyclerView rvNewsList;
    @InjectView(R.id.pb_wheel)
    ProgressWheel pbNewsLoading;
    @InjectView(R.id.fab_read_all)
    FloatingActionButton fabReadAll;

    @Inject
    NewsListFragmentPresenterImpl presenter;

    public static NewsListFragment newInstance(@NonNull Filter filter) {
        final NewsListFragment fragment = new NewsListFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ARG_FILTER_TYPE, filter);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsListFragment() {
        // Required empty public constructor
    }

    public interface Callbacks {

        void onNewsClick(int newsId);

    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof Callbacks) {
            mCallback = (Callbacks) context;
        } else {
            throw new ClassCastException(
                    context.getClass().getSimpleName() + " must implement NewsListFragment.Callback"
            );
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // prevent memory leaks
        mCallback = null;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(NewsComponent.class).inject(this);
        presenter.bindDisplay(this);
        if (savedInstanceState == null) {
            final Bundle args = getArguments();
            if (args != null) {
                mFilter = (Filter) args.get(ARG_FILTER_TYPE);
            }
        } else {
            mFilter = (Filter) savedInstanceState.get(STATE_FILTER_TYPE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_FILTER_TYPE, mFilter);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.inject(this, view);

        // Init refreshLayout
        refreshLayout.setCanChildScrollUpCallback(new NestedScrollableSwipeRefreshLayout.CanChildScrollUpCallback() {
            @Override
            public boolean canSwipeRefreshChildScrollUp() {
                return rvNewsList.computeVerticalScrollOffset() > 0;
            }
        });
        refreshLayout.setOnRefreshListener(new NestedScrollableSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.downloadNews(mFilter);
            }
        });

        presenter.loadNewsList(mFilter);

        // Init RecyclerView
        mAdapter = new NewsListRecyclerViewAdapter(getActivity());
        mAdapter.setOnNewsClickListener(this);
        rvNewsList.setHasFixedSize(true);
        rvNewsList.setAdapter(mAdapter);

        fabReadAll.setVisibility(mFilter == Filter.FAVORITES ? View.GONE : View.VISIBLE);
        return view;
    }

    @Override
    public void newsClick(@NonNull News news) {
        mCallback.onNewsClick(news.getId());
    }

    @Override
    public void showMessage(@StringRes int mesText) {
        Snackbar.make(coordinatorLayout, mesText, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        refreshLayout.setRefreshing(false);
        pbNewsLoading.setVisibility(View.GONE);
        rvNewsList.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateContent(@NonNull List<News> newsList) {
        mAdapter.addData(newsList);
    }

    @Override
    public void markAllRead() {
        mAdapter.markAllRead();
    }

    @Override
    public void onNewsClick(News news) {
        presenter.newsClick(news);
    }

    @OnClick(R.id.fab_read_all)
    public void readAll() {
        presenter.readAllNews(mFilter);
    }

}
