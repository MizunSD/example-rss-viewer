package ru.teaz.examplerssviewer.data.net;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class XmlRss {

    @Element(name = "channel")
    private XmlChannel mXmlChannel;

    public XmlChannel getChannel() {
        return mXmlChannel;
    }
}
