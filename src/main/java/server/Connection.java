package server;


import java.util.Date;

/**
 * Created by kumeskyi on 06.03.2015.
 */
public class Connection {

    private String src_ip;
    private String uri;
    private Date date;
    private long sent_bytes;
    private long received_bytes;
    private double speed;

    public Connection (String ip, String uri, long sent_bytes, int received_bytes, double speed) {
        this.src_ip = ip;
        this.uri = uri;
        this.date = new Date();
        this.sent_bytes = sent_bytes;
        this.received_bytes = received_bytes;
        this.speed = speed;
    }

    public String getSrc_ip() {
        return src_ip;
    }

    public String getUri() {
        return uri;
    }

    public Date getDate() {
        return date;
    }

    public long getSent_bytes() {
        return sent_bytes;
    }

    public long getReceived_bytes() {
        return received_bytes;
    }

    public double getSpeed() {
        return speed;
    }
}
