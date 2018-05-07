package com.microwise.uma.collection.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPClientHandler extends IoHandlerAdapter {
	private final static Logger logger = LoggerFactory
			.getLogger(TCPClientHandler.class);

	@Override
	public void sessionOpened(IoSession session) {
		logger.error("TCPClientHandler =%%%%%%%%%%%%%%%%%%%%%%%%%%%sessionOpened ");
		// send summation requests
		byte[] bys = new byte[41];
		session.write(bys);
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		logger.error("TCPClientHandler =%%%%%%%%%%%%%%%%%%%%%%%%%%%messageReceived ");
		// server only sends ResultMessage. otherwise, we will have to identify
		// its type using instanceof operator.
		logger.info("Message received : sessionId : " + session.getId()
				+ " ; message :" + message);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		logger.error("TCPClientHandler =%%%%%%%%%%%%%%%%%%%%%%%%%%%exceptionCaught ");
		session.close(true);
	}

}
