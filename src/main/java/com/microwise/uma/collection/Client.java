package com.microwise.uma.collection;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microwise.uma.collection.client.TCPClientHandler;

public class Client {

	private static Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		SocketConnector connector = new NioSocketConnector();

		connector.getFilterChain().addLast("logging", new LoggingFilter());
		connector.setConnectTimeoutMillis(3600000);
		connector.setHandler(new TCPClientHandler());
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));
		SocketAddress address = new InetSocketAddress("192.168.40.161", 60001);

		connector.getSessionConfig().setUseReadOperation(true);

		ConnectFuture future = connector.connect(address);

		future.awaitUninterruptibly();

		if (!future.isConnected()) {

			logger.error("服务器连接失败" + address);

		}

		IoSession session = future.getSession();

		if(session.isConnected()){
			logger.info("session is conntected! send hello to server!");
			future.getSession().write("hello !");
		}
		
	}
}
