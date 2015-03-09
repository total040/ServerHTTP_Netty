package server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.time.LocalDateTime;


/**
 * Created by kumeskyi on 05.03.2015.
 */
public class ServerInitializer extends ChannelInitializer <SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        Connection connection = new Connection();
        connection.setIp(socketChannel.remoteAddress().getHostString());
        connection.setEstablished(LocalDateTime.now());
        StatsMaker.getStatsMaker().addConnection(connection);

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("counter", new ThroughputCounter(10, connection));
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("handler", new ServerHandler(connection));
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
    }
}
