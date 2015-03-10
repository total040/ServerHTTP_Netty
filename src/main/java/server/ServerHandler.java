package server;

/**
 * Created by kumeskyi on 05.03.2015.
 */

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.time.LocalDateTime;


/*
  Handles Http requests from client and delegates response construction to ResponseMaker class
 */
public class ServerHandler extends SimpleChannelInboundHandler {

    private final StatsMaker stats = StatsMaker.getStatsMaker();
    private final Connection connection;
    private HttpRequest request;

    public ServerHandler(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        stats.addActive(ctx.channel());
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        stats.addRequestFromIp(connection.getIp(), LocalDateTime.now());
        if (request != null) {
            connection.addUri(request.getUri());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            this.request = (HttpRequest) msg;

            FullHttpResponse response = new ResponseMaker().buildHttpResponse(request.getUri());

            if (response != null) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);

            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}