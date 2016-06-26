package ru.teaz.examplerssviewer.domain.bus.event;

/**
 * Created by Teaz on 25.06.2016.
 */
public class FavoriteNewsCountUpdateEvent {
    private final int mFavoriteNewsCount;

    public FavoriteNewsCountUpdateEvent(int count) {
        mFavoriteNewsCount = count;
    }

    public int getCount() {
        return mFavoriteNewsCount;
    }
}
