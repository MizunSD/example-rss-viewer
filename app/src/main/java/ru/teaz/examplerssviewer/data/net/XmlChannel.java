package ru.teaz.examplerssviewer.data.net;

import android.support.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class XmlChannel {

    @Element(name = "title", required = true)
    @NonNull
    private String mTitle;

    @ElementList(name = "item", required = true, inline = true)
    @NonNull
    private List<XmlNews> mXmlNews;

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public List<XmlNews> getXmlNews() {
        return mXmlNews;
    }
}
