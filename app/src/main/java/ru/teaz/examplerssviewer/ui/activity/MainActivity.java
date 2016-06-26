package ru.teaz.examplerssviewer.ui.activity;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.teaz.examplerssviewer.R;
import ru.teaz.examplerssviewer.di.HasComponent;
import ru.teaz.examplerssviewer.di.component.DaggerNewsComponent;
import ru.teaz.examplerssviewer.di.component.NewsComponent;
import ru.teaz.examplerssviewer.domain.bus.RxBus;
import ru.teaz.examplerssviewer.domain.bus.event.FavoriteNewsCountUpdateEvent;
import ru.teaz.examplerssviewer.domain.bus.event.UnreadNewsCountUpdateEvent;
import ru.teaz.examplerssviewer.model.Filter;
import ru.teaz.examplerssviewer.presenter.impl.MainActivityPresenterImpl;
import ru.teaz.examplerssviewer.ui.callback.RssFeedView;
import ru.teaz.examplerssviewer.ui.fragment.NewsListFragment;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements HasComponent<NewsComponent>,
        RssFeedView,
        NewsListFragment.Callbacks{

    private static final String STATE_FILTER_TYPE = "state_filter_type";

    private NewsComponent mNewsComponent;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.lv_root)
    LinearLayout lvRoot;

    @Inject
    RxBus rxBus;
    @Inject
    MainActivityPresenterImpl presenter;

    private Subscription mUpdateUnreadCount;
    private Subscription mUpdateFavoriteCount;


    private Filter mFilter = Filter.ALL_NEWS;
    private Drawer mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initInjector();
        registerRxBusEvents();
        presenter.bindView(this);
        if (savedInstanceState == null) {
            presenter.loadNewsFeed(mFilter);
        } else {
            mFilter = (Filter) savedInstanceState.get(STATE_FILTER_TYPE);
        }
        initToolbar();
        initDrawer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_FILTER_TYPE, mFilter);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBusEvents();
        ButterKnife.reset(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rss_feed_menu, menu);
        return true;
    }

    private void initInjector() {
        mNewsComponent = DaggerNewsComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private void registerRxBusEvents() {

        mUpdateUnreadCount = rxBus.register(UnreadNewsCountUpdateEvent.class, new Action1<UnreadNewsCountUpdateEvent>() {
            @Override
            public void call(UnreadNewsCountUpdateEvent event) {
                updateUnreadCount(event.getCount());
            }
        });

        mUpdateFavoriteCount = rxBus.register(FavoriteNewsCountUpdateEvent.class, new Action1<FavoriteNewsCountUpdateEvent>() {
            @Override
            public void call(FavoriteNewsCountUpdateEvent event) {
                updateFavoriteCount(event.getCount());
            }
        });
    }

    private void unregisterBusEvents() {
        if (mUpdateFavoriteCount != null) {
            mUpdateUnreadCount.unsubscribe();
        }
        if (mUpdateFavoriteCount != null) {
            mUpdateFavoriteCount.unsubscribe();
        }
    }

    @Override
    public NewsComponent getComponent() {
        return mNewsComponent;
    }

    @Override
    public void showFeed(Filter filter) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.news_list_container, NewsListFragment.newInstance(filter))
                .commit();

    }

    @Override
    public void showItem(int id) {

    }

    @Override
    public void showError(@StringRes int resId) {

    }

    @Override
    public void updateUnreadCount(int count) {

    }

    @Override
    public void updateFavoriteCount(int count) {

    }

    @Override
    public void onNewsClick(int newsId) {

    }

    private void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void initDrawer() {
        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.nav_header)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(R.drawable.ic_newspaper_grey600_24dp).withName(R.string.all_news).withIdentifier(R.string.all_news).withTag(Filter.ALL_NEWS),
                        new PrimaryDrawerItem().withIcon(R.drawable.ic_email_outline_grey600_24dp).withName(R.string.unread).withIdentifier(R.string.unread).withTag(Filter.UNREAD),
                        new PrimaryDrawerItem().withIcon(R.drawable.ic_heart_outline_grey600_24dp).withName(R.string.favorites).withIdentifier(R.string.favorites).withTag(Filter.FAVORITES)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        final Filter filter = (Filter) drawerItem.getTag();
                        if (filter != mFilter) {
                            mFilter = filter;
                            presenter.loadNewsFeed(filter);
                        }
                        return false;

                    }
                })
                .build();

        switch (mFilter) {
            case ALL_NEWS:
                mDrawer.setSelection(0);
                break;
            case UNREAD:
                mDrawer.setSelection(1);
                break;
            case FAVORITES:
                mDrawer.setSelection(2);
                break;
        }
        presenter.updateFavoriteCount();
        presenter.updateUnreadCount();
    }
}
