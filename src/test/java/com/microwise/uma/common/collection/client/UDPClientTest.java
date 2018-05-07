package com.microwise.uma.common.collection.client;

import com.google.common.base.Charsets;
import com.microwise.uma.collection.client.UDPClient;
import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author gaohui
 * @date 14-1-4 10:02
 */
public class UDPClientTest {
    @Test
    public void test() throws IOException {
        System.out.println(Charset.defaultCharset());
        UDPClient client = new UDPClient();
        client.send(((new Date()).toString() + "li.jianfei" + " 我是中文 ").getBytes());
        client.close();
    }


    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8082);

        while (true) {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
            System.out.println("receiving ...");
            datagramSocket.receive(datagramPacket);
            System.out.println(datagramPacket.getOffset());
            System.out.println(datagramPacket.getLength());
            String s = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), Charsets.UTF_8);
            System.out.println(s);
        }
    }
}
