package server;

/**
 * Created by kumeskyi on 06.03.2015.
 */

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class Connection {

    private LocalDateTime established;
    private LocalDateTime closed;
    private String ip;
    private Set<String> uris;

    private long bytesSent;
    private long bytesReceived;

    public Connection() {
        uris = new HashSet<>();
    }


    public synchronized String getUrisAsString() {
        StringBuilder sb = new StringBuilder();
        uris.forEach((uri) -> sb.append(uri).append(", "));
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }

    public synchronized double getSpeed() {
        double connectionDuration = ChronoUnit.MILLIS.between(established, closed);
        connectionDuration /= 1000;
        return Math.round((((double)bytesSent + (double)bytesReceived)*100) / 100*connectionDuration);
    }

    public synchronized long getBytesReceived() {
        return bytesReceived;
    }

    public synchronized void setBytesReceived(long bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public synchronized LocalDateTime getEstablished() {
        return established;
    }

    public synchronized void setEstablished(LocalDateTime established) {
        this.established = established;
    }

    public synchronized LocalDateTime getClosed() {
        return closed;
    }

    public synchronized void setClosed(LocalDateTime closed) {
        this.closed = closed;
    }

    public synchronized String getIp() {
        return ip;
    }

    public synchronized void setIp(String ip) {
        this.ip = ip;
    }

    public synchronized void addUri(String uri) {
        if (uri != null) {
            uris.add(uri);
        }
    }

    public synchronized long getBytesSent() {
        return bytesSent;
    }

    public synchronized void setBytesSent(long bytesSent) {
        this.bytesSent = bytesSent;
    }


}
