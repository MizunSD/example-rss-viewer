package ru.teaz.examplerssviewer.data.net;

import android.support.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class XmlNews {

    @Element(name = "title")
    @NonNull
    private String mTitle;

    @Element(name = "link")
    private String mLink;

    @Element(name = "description")
    private String mDescription;

    @Element(name = "pubDate")
    private String mPubDate;

    @Element(name = "category", required = false)
    private String mCategory;

    @Element(name = "author", required = false)
    private String mAuthor;

    @Element(name = "enclosure", required = false)
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
