package com.microwise.uma.collection.client;

import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.util.ConfigureProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

/**
 * UDP 客户端，向外推送当前指定区域的规则触发状况
 *
 * @author li.jianfei
 * @date 2013-08-21
 */
public class UDPClient {
    public static final Logger log = LoggerFactory.getLogger(UDPClient.class);

    private DatagramSocket dataSocket;

    private String udpServerIp;
    private int udpServerPort;


    /**
     * 初始化 UDP 客户端
     *
     * @throws SocketException
     */
    public UDPClient() throws SocketException {
        udpServerIp = SystemConfigure.getConfigure(ConfigureProperties.UDP_SERVER_IP, "");
        udpServerPort = SystemConfigure.getConfigure(ConfigureProperties.UDP_SERVER_PORT, 0);

        this.dataSocket = new DatagramSocket();
    }

    /**
     * 向指定服务器发送数据信息
     *
     * @param bytes 要发送的数据
     * @return DatagramPacket
     * @throws IOException
     */
    public DatagramPacket send(byte[] bytes) throws IOException {
        DatagramPacket dataPacket = null;
        // TODO 可使用 guava Splitter @gaohui 2014-1-4
        String ips[] = udpServerIp.split("\\|");
        for (String ip : ips) {
            dataPacket = new DatagramPacket(bytes,
                                            bytes.length,
                                            InetAddress.getByName(ip),
                                            udpServerPort);
            this.dataSocket.send(dataPacket);
        }
        return dataPacket;
    }

    /**
     * 关闭 UDP 连接
     */
    public void close() {
        try {
            dataSocket.close();
        } catch (Exception ex) {
            log.error("send UDP error", ex);
        }
    }
}
