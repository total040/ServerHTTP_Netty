package web_pages;

/**
 * Created by kumeskyi on 06.03.2015.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NotFoundPage {

    public ByteBuf getContent(String page) {
        return Unpooled.copiedBuffer(
                "<html>" +
                        "<head>" +
                        "<title>Page Not Found</title>" +
                        "</head>" +
                        "<body bgcolor = white>" +
                        "<h3 align = 'center'>Sorry, page " + page + " not found " + "</h3>" +
                        "</body>" +
                        "</html>", CharsetUtil.UTF_8);
    }
}
