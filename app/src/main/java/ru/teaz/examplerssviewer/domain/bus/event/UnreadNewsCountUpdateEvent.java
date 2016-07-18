package ru.teaz.examplerssviewer.domain.bus.event;

public class UnreadNewsCountUpdateEvent {
    private final int mUnreadNewsCount;

    public UnreadNewsCountUpdateEvent(int count) {
        mUnreadNewsCount = count;
    }

    public int getCount() {
        return mUnreadNewsCount;
    }
}
