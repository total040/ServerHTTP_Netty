package server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * Created by kumeskyi on 05.03.2015.
 */
public class ServerInitializer extends ChannelInitializer <SocketChannel>{

    public static StatsMaker stats = StatsMaker.getStatsMaker();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        String ip = socketChannel.remoteAddress().getHostString();

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("handler", new ServerHandler(ip));
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));

    }
}
