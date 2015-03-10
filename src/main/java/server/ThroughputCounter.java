package server;

/**
 * Created by kumeskyi on 08.03.2015.
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;

import java.time.LocalDateTime;

/*
 Helps to account I/O traffic within one connection
 */

public class ThroughputCounter extends ChannelTrafficShapingHandler {

    private final Connection connection;

    public ThroughputCounter(long checkInterval, Connection connection) {
        super(checkInterval);
        this.connection = connection;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        this.trafficCounter().start();
    }

    @Override
    public synchronized void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        connection.setClosed(LocalDateTime.now());
        connection.setBytesReceived(this.trafficCounter().cumulativeReadBytes());
        connection.setBytesSent(this.trafficCounter().cumulativeWrittenBytes());
        super.handlerRemoved(ctx);
        this.trafficCounter().stop();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }
}
