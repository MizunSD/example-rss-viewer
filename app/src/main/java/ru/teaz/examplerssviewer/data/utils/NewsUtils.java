package ru.teaz.examplerssviewer.data.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.teaz.examplerssviewer.data.NewsSource;
import ru.teaz.examplerssviewer.data.db.model.News;
import ru.teaz.examplerssviewer.data.net.XmlNews;
import ru.teaz.examplerssviewer.data.net.XmlRss;

public final class NewsUtils {

    private NewsUtils() {}

    @NonNull
    public static List<News> rssToNews(@Nullable XmlRss rss) {
        final List<News> newsList = new ArrayList<>();
        if (rss != null && rss.getChannel() != null) {
            final String sourceTitle = rss.getChannel().getTitle().toLowerCase();
            NewsSource source = null;
            if (sourceTitle.startsWith(NewsSource.GAZETA.getName())) {
                source = NewsSource.GAZETA;
            }
            else
                if (sourceTitle.startsWith(NewsSource.LENTA.getName())) {
                    source = NewsSource.LENTA;
                }

            if (source != null) {
                final List<XmlNews> xmlNewsList = rss.getChannel().getXmlNews();
                for (XmlNews xmlNews : xmlNewsList) {
                    newsList.add(new News(source, xmlNews));
                }
            }
        }
        return newsList;
    }

    @NonNull
    public static List<News> filterByDate(@NonNull List<News> newsList, @Nullable LocalDateTime filterDate) {
        if (filterDate != null) {
            final Iterator<News> iterator = newsList.iterator();
            News candidate;
            while (iterator.hasNext()) {
                candidate = iterator.next();
                if (!candidate.getPubDate().isAfter(filterDate)) {
                    iterator.remove();
                }
            }
        }
        return newsList;
    }

}
