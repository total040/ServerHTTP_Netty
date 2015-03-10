package web_pages;

/**
 * Created by kumeskyi on 05.03.2015.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class HelloPage {

    public ByteBuf getContent() {
        return Unpooled.copiedBuffer(
                "<html>" +
                        "<head>" +
                        "<title>hello</title>" +
                        "</head>" +
                        "<body bgcolor = green>" +
                        "<h1 align = 'center'>Hello World</h1>" +
                        "</body>" +
                        "</html>", CharsetUtil.UTF_8);
    }

}
