package server;

import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by kumeskyi on 06.03.2015.
 */
public class StatsMaker {

    private int numberOfQueries;
    private Map<String, Integer> ipRequestsByCount = new HashMap<>();
    private Map<String, LocalDateTime> ipRequestsByDate = new HashMap<>();
    private Map<String, Integer> redirects = new HashMap<String, Integer>();
    private List<Connection> connections = Collections.synchronizedList(new ArrayList<>());

    private static StatsMaker statsMaker;

    private StatsMaker() {}

    public static synchronized StatsMaker getStatsMaker(){
        if (statsMaker == null) {
            statsMaker = new StatsMaker();
        }
        return statsMaker;
    }

    private DefaultChannelGroup activeConnections  = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public synchronized void addActive(Channel c) {
        activeConnections.add(c);
    }

    public synchronized int getNumberOfActiveConnections(){
        return  activeConnections.size();
    }

    public synchronized int getTotalNumberOfQueries() {
        return numberOfQueries;
    }

    public synchronized int getNumberOfUniqueQueries(){
          return ipRequestsByCount.size();
    }

    public synchronized void addRequestFromIp(String ip, LocalDateTime localDateTime) {
        if (ipRequestsByCount.containsKey(ip)) {
            ipRequestsByCount.put(ip, ipRequestsByCount.get(ip) + 1);
        } else {
            ipRequestsByCount.put(ip, 1);
        }

        ipRequestsByDate.put(ip, localDateTime);
        numberOfQueries++;
    }

    public synchronized Map<String, Integer> getIpRequestsByCount() {
        return ipRequestsByCount;
    }

    public synchronized Map<String, LocalDateTime> getIpRequestsByDate() {
        return ipRequestsByDate;
    }

    public synchronized void addRedirect(String destinationUrl) {
        if (redirects.containsKey(destinationUrl)) {
            redirects.put(destinationUrl, redirects.get(destinationUrl) + 1);
        } else {
            redirects.put(destinationUrl, 1);
        }
    }

    public synchronized Map<String, Integer> getRedirects() {
        return redirects;
    }

    public synchronized void addConnection(Connection connection) {
        if (connections.size() == 16) {
            connections.remove(0);
        }
        connections.add(connection);
    }

    public synchronized List<Connection> getConnections() {
        return connections;
    }

    public String getFormattedDateTime(LocalDateTime ldt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YY-MM-dd hh:mm:ss");
        return ldt.format(formatter);
    }

}
