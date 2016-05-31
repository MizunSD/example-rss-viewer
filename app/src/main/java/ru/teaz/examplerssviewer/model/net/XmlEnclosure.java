package ru.teaz.examplerssviewer.model.net;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Sergey on 31.05.2016.
 */
@Root(name = "enclosure")
public class XmlEnclosure {

    @Attribute(name = "url")
    private String mUrl;

    @Attribute(name = "length")
    private Integer mLength;

    @Attribute(name = "type")
    private String mType;

    public String getUrl() {
        return mUrl;
    }

    public Integer getLength() {
        return mLength;
    }

    public String getType() {
        return mType;
    }
}
