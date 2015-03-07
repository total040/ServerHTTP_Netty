package server;

import java.util.*;

/**
 * Created by kumeskyi on 06.03.2015.
 */
public class StatsMaker {

    private int numberOfQueries;

    private int numberOfUniqueQueries;

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private List<Connection> connections = new ArrayList<Connection>();

    private Set<Connection> uniqueConnections = new HashSet<Connection>();

    private static StatsMaker statsMaker;

    private StatsMaker() {}

    public static synchronized StatsMaker getStatsMaker(){
        if (statsMaker == null) {
            statsMaker = new StatsMaker();
        }
        return statsMaker;
    }


    // Collection for last 16 connections.
    private List<Connection> connectionsInfo = new ArrayList<Connection>();

    // Set for unique queries.
    private Set<String> uniqueQuery = new HashSet<String>();

    // Collect redirected URL, and their count.
    private Map<String, Integer> urlCollection = new LinkedHashMap<String, Integer>();

    private int countActiveConnections = 0;

    private String lastConnectionIP;

    public synchronized void addConnection(Connection conn) {
        if (connectionsInfo.size() == 16) {
            connectionsInfo.remove(0);
        }
        connections.add(conn);
    }

    public synchronized void addConnectionIfUnique(String uri) {
        if (!uri.equals("/favicon.ico")) {
            uniqueQuery.add(uri);
        }
    }

    public synchronized void putUrlOrIncrementCountIfExist(String url) {
        if (urlCollection.containsKey(url)) {
            urlCollection.put(url, urlCollection.get(url) + 1);
        } else {
            urlCollection.put(url, 1);
        }
    }

    public synchronized void setLastConnectionIP(String lastConnectionIP) {
        this.lastConnectionIP = lastConnectionIP;
    }

    public synchronized String getLastConnectionIP() {
        return lastConnectionIP;
    }

    public synchronized void addNumberOfQueries() {
        numberOfQueries++;
    }

    public synchronized void incrementActiveConnectionCount() {
        countActiveConnections++;
    }

    public synchronized void decrementActiveConnectionCount() {
        countActiveConnections--;
    }

    public synchronized int getCountQuery() {
        return numberOfQueries;
    }

    public synchronized List<Connection> getConnections() {
        return connections;
    }

    public synchronized Map<String, Integer> getUrlCollection(){
        return urlCollection;}

    public synchronized int getCountUniqueConnections(){
        return uniqueQuery.size();
    }

    public synchronized int getCountActiveConnections() {
        return countActiveConnections;
    }

    public synchronized String getTimeOfLastConnection(){
        return connections.size() > 0 ? getLastConnections().getDate() + "" : new Date() + "";
    }


    private Connection getLastConnections(){
        return connectionsInfo.get(connectionsInfo.size() - 1);
    }


}
