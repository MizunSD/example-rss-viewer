package ru.teaz.examplerssviewer.data.net;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "enclosure", strict = false)
public class XmlEnclosure {

    @Attribute(name = "url")
    private String mUrl;

    @Attribute(name = "length", required = false)
    private Integer mLength;

    @Attribute(name = "type", required = false)
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
