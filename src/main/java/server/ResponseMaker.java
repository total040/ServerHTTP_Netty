package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import web_pages.HelloPage;
import web_pages.NotFoundPage;
import web_pages.StatusPage;

//import static io.netty.handler.codec.http.HttpHeaderNames.LOCATION;
import static io.netty.handler.codec.http.HttpResponseStatus.FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by kumeskyi on 05.03.2015.
 */
public class ResponseMaker {

        private static final int TIME_SLEEP = 3;

        public FullHttpResponse buildHttpResponse(String requestURI) throws InterruptedException {

            if (requestURI.equals("/hello")) {

                return buildHelloPage();

            } else if (requestURI.length() > 14 && requestURI.substring(0, 14).equals("/redirect?url=")) {
                String redirectedURI = requestURI.substring(14, requestURI.length());
                return buildRedirectedPage(redirectedURI);

            } else if (requestURI.equals("/status")) {

                return buildStatusPage(ServerInitializer.stats);

            } else {

                return buildNonExistentPage(requestURI);

            }

        }

        private FullHttpResponse buildHelloPage() throws InterruptedException {

             Thread.sleep(TIME_SLEEP * 1000);
             return new DefaultFullHttpResponse(HTTP_1_1, OK, new HelloPage().getContent());

        }

        private FullHttpResponse buildNonExistentPage(String requestURI) throws InterruptedException {

              return new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND, new NotFoundPage().getContent(requestURI));

        }

         private FullHttpResponse buildRedirectedPage(String redirected) throws InterruptedException {

             // В контент нужно добавить заголовок
             FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FOUND); // FOUND = 302
             response.headers().set("location", redirected);
             return response;

        }

        private FullHttpResponse buildStatusPage(StatsMaker statsMaker) throws InterruptedException {

              return new DefaultFullHttpResponse(HTTP_1_1, OK, new StatusPage().getContent(statsMaker));

        }

    }
