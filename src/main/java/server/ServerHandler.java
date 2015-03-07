package server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;

/**
 * Created by kumeskyi on 05.03.2015.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private String uri;

    private String ip;

    public ServerHandler (String ip) {
        this.ip = ip;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
         ServerInitializer.stats.addNumberOfQueries();
         ServerInitializer.stats.setIp(ip);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (!(msg instanceof HttpRequest)) {
            return;
        }

        uri = ((HttpRequest) msg).getUri();
        FullHttpResponse res = new ResponseMaker().buildHttpResponse(uri);

        if (res != null) {
         //   this.bytesSent = res.content().writerIndex();
            ctx.write(res).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        System.out.println("Connection from: " + ip);
        ctx.flush();
        System.out.print("Flushed: " + ctx.name());
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
