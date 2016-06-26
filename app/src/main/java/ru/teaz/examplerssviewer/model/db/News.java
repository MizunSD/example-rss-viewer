package ru.teaz.examplerssviewer.model.db;

import android.support.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.LocalDateTime;

import ru.teaz.examplerssviewer.model.NewsSource;
import ru.teaz.examplerssviewer.model.db.persister.LocalDateTimePersister;
import ru.teaz.examplerssviewer.model.net.XmlEnclosure;
import ru.teaz.examplerssviewer.model.net.XmlNews;
import ru.teaz.examplerssviewer.model.utils.DateUtils;

/**
 * Created by Sergey on 31.05.2016.
 */

@DatabaseTable(tableName = "news")
public class News {

    public static final String FIELD_DATE = "date";
    public static final String FIELD_READ = "read";
    public static final String FIELD_FAVORITE = "favorite";
    public static final String FIELD_TITLE = "title";

    @DatabaseField(columnName = "_id", id = true)
    private int mId;

    @DatabaseField(columnName = "source")
    private NewsSource mSource;

    @DatabaseField(columnName = FIELD_TITLE)
    private String mTitle;

    @DatabaseField(columnName = "link")
    private String mLink;

    @DatabaseField(columnName = "description")
    private String mDescription;

    @DatabaseField(columnName = FIELD_DATE, persisterClass = LocalDateTimePersister.class)
    private LocalDateTime mPubDate;

    @DatabaseField(columnName = "category")
    private String mCategory;

    @DatabaseField(columnName = "author")
    private String mAuthor;

    @DatabaseField(columnName = "url_image")
    private String mUrlImage;

    @DatabaseField(columnName = FIELD_READ)
    private boolean mRead = false;

    @DatabaseField(columnName = FIELD_FAVORITE)
    private boolean mFavorite = false;

    public News() {

    }

    public News(@NonNull NewsSource source, @NonNull XmlNews news) {
        mId = news.getLink().hashCode();
        mSource = source;
        mTitle = news.getTitle();
        mLink = news.getLink();
        mDescription = news.getDescription();
        mPubDate = DateUtils.parseStringToDateTime(news.getPubDate());
        mCategory = news.getCategory();
        mAuthor = news.getAuthor();
        XmlEnclosure enclosure = news.getXmlEnclosure();
        if (enclosure != null) {
            mUrlImage = enclosure.getUrl();
        }
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public NewsSource getSource() {
        return mSource;
    }

    public void setSource(NewsSource source) {
        this.mSource = source;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public LocalDateTime getPubDate() {
        return mPubDate;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.mPubDate = pubDate;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(String urlImage) {
        this.mUrlImage = urlImage;
    }

    public boolean isRead() {
        return mRead;
    }

    public void setRead(boolean read) {
        mRead = read;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        mFavorite = favorite;
    }
}
