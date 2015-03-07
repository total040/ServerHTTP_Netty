package web_pages;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * Created by kumeskyi on 05.03.2015.
 */
public class HelloPage {

    public ByteBuf getContent() {
        return Unpooled.copiedBuffer(
                "<html>" +
                        "<head>" +
                        "<title>hello</title>" +
                        "</head>" +
                        "<body bgcolor = green>" +
                        "<h1 align = 'center'>Hello World</h1>" +
                        "<h3 align = 'center'>Congrats!!! You've connected to \"Netty WEB server\"</h3>" +
                        "<h6 align = 'right'>(c)Created by pager</h6>" +
                        "</body>" +
                        "</html>", CharsetUtil.UTF_8);
    }

}
