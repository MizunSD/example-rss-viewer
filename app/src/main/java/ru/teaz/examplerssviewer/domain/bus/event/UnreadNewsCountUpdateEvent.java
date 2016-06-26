package ru.teaz.examplerssviewer.domain.bus.event;

/**
 * Created by Teaz on 25.06.2016.
 */
public class UnreadNewsCountUpdateEvent {
    private final int mUnreadNewsCount;

    public UnreadNewsCountUpdateEvent(int count) {
        mUnreadNewsCount = count;
    }

    public int getCount() {
        return mUnreadNewsCount;
    }
}
