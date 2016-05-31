package ru.teaz.examplerssviewer.model.net;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sergey on 31.05.2016.
 */

@Root(name = "rss")
public class XmlRss {

    @Element(name = "channel")
    private XmlChannel mXmlChannel;

    public XmlChannel getChannel() {
        return mXmlChannel;
    }
}
