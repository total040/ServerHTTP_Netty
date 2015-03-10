package web_pages;

/**
 * Created by kumeskyi on 06.03.2015.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import server.Connection;
import server.StatsMaker;

import java.util.Map;

public class StatusPage {

    public synchronized ByteBuf getContent(StatsMaker stat) {

        StringBuilder contents = new StringBuilder();

        contents.append(
                "<html>" +
                "<title>Status page</title>" +
                "<h3>WEB-server statistics</h3>" +
                // First table.
                "<h4>General statistics</h4>" +
                "<table border = 1>" +
                "<tr>" +
                "<th>Total requests</th>" +
                "<th>Unique connections</th>" +
                "<th>Active connections</th>" +
                "</tr>" +
                "<tr>" +
                "<td align=\"center\">" + stat.getTotalNumberOfQueries() + "</td>" +
                "<td align=\"center\">" + stat.getNumberOfUniqueQueries() + "</td>" +
                "<td align=\"center\">" + stat.getNumberOfActiveConnections() + "</td>" +
                "</tr>" +
                "</table>" +

                // Second table
                "<h4>Request statistics</h4>" +
                "<table border = 1>" +
                "<tr>" +
                "<th>Request from (IP)</th>" +
                "<th>Number of requests from IP</th>" +
                "<th>Time of last request</th>");

        for (Map.Entry<String, Integer> ipRequest: stat.getIpRequestsByCount().entrySet()) {
            contents.append(
                    "<tr>" +
                    "<td align=\"center\">" + ipRequest.getKey() + "</td>" +
                    "<td align=\"center\">" + ipRequest.getValue() + "</td>" +
                    "<td align=\"center\">" + stat.getFormattedDateTime(stat.getIpRequestsByDate().get(ipRequest.getKey()))
                            + "</td>" +
                    "</tr>");
        }
        contents.append(
                    "</table>" +

                    // Third table.
                    "<h4>Redirect statistics</h4>" +
                    "<table border = 1>" +
                    "<tr>" +
                    "<th>Redirected URL</th>" +
                    "<th>Count</th>");
            for (Map.Entry<String, Integer> coll : stat.getRedirects().entrySet()) {
                contents.append(
                        "<tr>" +
                        "<td align=\"center\">" + coll.getKey() + "</td>" +
                        "<td align=\"center\">" + coll.getValue() + "</td>" +
                        "</tr>");
            }
            contents.append(
                    "</table>" +

                    // Fourth table.
                    "<h4>Connection statistics (last 16)</h4>" +
                    "<table border = 1>" +
                    "<tr>" +
                    "<th>src_ip</th>" +
                    "<th>URI</th>" +
                    "<th>Time stamp</th>" +
                    "<th>sent_bytes</th>" +
                    "<th>received_bytes</th>" +
                    "<th>Speed(byte/sec)</th>" +
                    "</tr>");
            for (Connection conn : stat.getConnections()) {
                contents.append(
                        "<tr>" +
                        "<td>" + conn.getIp() + "</td>" +
                        "<td>" + conn.getUrisAsString() + "</td>");
                if (conn.getClosed() != null) {
                    contents.append(
                            "<td>" + stat.getFormattedDateTime(conn.getEstablished()) + "</td>" +
                                    "<td align=\"center\">" + conn.getBytesSent() + "</td>" +
                                    "<td align=\"center\">" + conn.getBytesReceived() + "</td>" +
                                    "<td align=\"center\">" + conn.getSpeed() + "</td>" +
                                    "</tr>");
                } else {
                    contents.append(
                            "<td>" + "---" + "</td>" +
                            "<td align=\"center\">" + "---" + "</td>" +
                            "<td align=\"center\">" + "---" + "</td>" +
                            "<td align=\"center\">" + "---" + "</td>" +
                            "</tr>");
                }
            }
            contents.append(
                    "</table>" +
                    "</body>" +
                    "</html>");

            return Unpooled.copiedBuffer(contents, CharsetUtil.UTF_8);
        }
}
