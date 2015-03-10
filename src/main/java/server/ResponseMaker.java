package server;

/**
 * Created by kumeskyi on 05.03.2015.
 */

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import web_pages.HelloPage;
import web_pages.NotFoundPage;
import web_pages.StatusPage;

import static io.netty.handler.codec.http.HttpResponseStatus.FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/*
  Receives from ServerHandler a response construction task, analyses the request URI
  and asks for corresponding web page
 */
public class ResponseMaker {

       // number of seconds to wait before hello page loads
        private static final int SLEEP_TIME = 10;

        private final StatsMaker stats = StatsMaker.getStatsMaker();

        public synchronized FullHttpResponse buildHttpResponse(String requestURI) throws InterruptedException {

            if (requestURI.equals("/hello")) {

                return buildHelloPage();

            } else if (requestURI.length() > 14 && requestURI.substring(0, 14).equals("/redirect?url=")) {
                String redirectedURI = requestURI.substring(14, requestURI.length());
                stats.addRedirect(redirectedURI);
                return buildRedirectedPage(redirectedURI);

            } else if (requestURI.equals("/status")) {

                return buildStatusPage(stats);

            } else {

                return buildNonExistentPage(requestURI);

            }

        }

        private FullHttpResponse buildHelloPage() throws InterruptedException {

             Thread.sleep(SLEEP_TIME * 1000);
             return new DefaultFullHttpResponse(HTTP_1_1, OK, new HelloPage().getContent());

        }

        private FullHttpResponse buildNonExistentPage(String requestURI) throws InterruptedException {

              return new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND, new NotFoundPage().getContent(requestURI));

        }

         private FullHttpResponse buildRedirectedPage(String redirected) throws InterruptedException {

             FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FOUND);
             response.headers().set("location", redirected);
             return response;

        }

        private FullHttpResponse buildStatusPage(StatsMaker statsMaker) throws InterruptedException {

              return new DefaultFullHttpResponse(HTTP_1_1, OK, new StatusPage().getContent(statsMaker));

        }

    }

