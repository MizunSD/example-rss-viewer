package ru.teaz.examplerssviewer.model.net;

import android.support.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Sergey on 31.05.2016.
 */
@Root(name = "channel")
public class XmlChannel {

    @Element(name = "language")
    private String mLanguage;

    @Element(name = "title", required = true)
    @NonNull
    private String mTitle;

    @Element(name = "description", required = true)
    @NonNull
    private String mDescription;

    @Element(name = "link", required = true)
    @NonNull
    private String mLink;

    @ElementList(name = "item", required = true)
    @NonNull
    private List<XmlNews> mXmlNews;

    public String getLanguage() {
        return mLanguage;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    @NonNull
    public String getLink() {
        return mLink;
    }

    @NonNull
    public List<XmlNews> getXmlNews() {
        return mXmlNews;
    }
}
