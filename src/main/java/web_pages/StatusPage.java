package web_pages;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import server.Connection;
import server.StatsMaker;

import java.util.Map;

/**
 * Created by kumeskyi on 06.03.2015.
 */
public class StatusPage {

    public ByteBuf getContent(StatsMaker stat) {


        String content = "" +
                "<html>" +
                "<title>Status page</title>" +
                "<body bgcolor = eeff90>" +
                "<h1>WEB-server status:</h1>" +

                // First table.
                "<table border = 1>" +
                "<tr>" +
                "<th>Total requests</th>" +
                "<th>Unique connections</th>" +
                "<th>Active connections</th>" +
                "<th>Last IP</th>" +
                "<th>Last connection</th>" +
                "</tr>" +
                "<tr>" +
                "<td align=\"center\">" + stat.getCountQuery() + "</td>" +
                "<td align=\"center\">" + stat.getCountUniqueConnections() + "</td>" +
                "<td align=\"center\">" + stat.getCountActiveConnections() + "</td>" +
                "<td align=\"center\">" + stat.getLastConnectionIP() + "</td>" +
                "<td align=\"center\">" + stat.getTimeOfLastConnection() + "</td>" +
                "</tr>" +
                "</table>" +

                // Second table.
                "<table border = 1>" +
                "<tr>" +
                "<th>Redirected URL</th>" +
                "<th>Count</th>";
        for (Map.Entry<String, Integer> coll : stat.getUrlCollection().entrySet()) {
            content += "<tr>" +
                    "<td align=\"center\">" + coll.getKey() + "</td>" +
                    "<td align=\"center\">" + coll.getValue() + "</td>" +
                    "</tr>";
        }
        content += "" +
                "</table>" +

                // Third table.
                "<table border = 1>" +
                "<tr>" +
                "<th>IP</th>" +
                "<th>URI</th>" +
                "<th>Time stamp</th>" +
                "<th>Send bytes</th>" +
                "<th>Received bytes</th>" +
                "<th>Speed(byte/sec)</th>" +
                "</tr>";
        for (Connection connInfo : stat.getConnections()) {
            content += "" +
                    "<tr>" +
                    "<td>" + connInfo.getSrc_ip() + "</td>" +
                    "<td>" + connInfo.getUri() + "</td>" +
                    "<td>" + connInfo.getDate() + "</td>" +
                    "<td align=\"center\">" + connInfo.getSent_bytes() + "</td>" +
                    "<td align=\"center\">" + connInfo.getReceived_bytes() + "</td>" +
                    "<td align=\"center\">" + connInfo.getSpeed() + "</td>" +
                    "</tr>";
        }
        content += "" +
                "</table>" +
                "</body>" +
                "</html>";


        return Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
    }
}
