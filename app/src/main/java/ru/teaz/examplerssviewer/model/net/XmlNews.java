package ru.teaz.examplerssviewer.model.net;

import android.support.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sergey on 31.05.2016.
 */
@Root(name = "item")
public class XmlNews {

    @Element(name = "title", required = true)
    @NonNull
    private String mTitle;

    @Element(name = "link")
    private String mLink;

    @Element(name = "description")
    private String mDescription;

    @Element(name = "pubDate")
    private String mPubDate;

    @Element(name = "category")
    private String mCategory;

    @Element(name = "author")
    private String mAuthor;

    @Element(name = "enclosure")
    private XmlEnclosure mXmlEnclosure;

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public XmlEnclosure getXmlEnclosure() {
        return mXmlEnclosure;
    }
}
