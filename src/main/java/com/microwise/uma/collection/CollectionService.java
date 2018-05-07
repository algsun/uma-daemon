package com.microwise.uma.collection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microwise.uma.collection.handler.UmaTCPHandler;

/**
 * 端口监听服务
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:56:28
 * 
 * @check 2013-05-06 xubaoji  svn:2915
 * @check 2013-10-14 @gaohui #5932
 */
@Component
public class CollectionService {

	private static Logger logger = LoggerFactory.getLogger(CollectionService.class);
	
	@Autowired
	private UmaTCPHandler umaTCPHandler;
    private IoAcceptor acceptor;

    /**
	 * 运行端口监听服务
	 * 
	 * @param buffersize
	 *            buffer缓存区大小
	 * @param port
	 *            绑定的端口号
	 * @throws IOException
	 *             异常信息
	 */
	public void run(int buffersize, List<Integer> ports) throws IOException {
		
		logger.info("开启tcp服务端监听 ");

		//创建接收器：设置启动线程数为cpu个数+1
        acceptor = new NioSocketAcceptor(Runtime.getRuntime()
                .availableProcessors() + 1);
		// acceptor.getFilterChain().addLast("codec",
		// new ProtocolCodecFilter(new CollectionFilterFactory()));
		//设置缓冲区大小 单位是 Byte
		acceptor.getSessionConfig().setReadBufferSize(buffersize);
		//设置空闲时间 单位为秒 
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		//设置handler
		acceptor.setHandler(umaTCPHandler);

        // TODO 从 u_port_site_mapping 中获取端口并监听 @gaohui 2013-10-14
		//绑定端口
		for (Integer port : ports) {
			acceptor.bind(new InetSocketAddress(port));
			logger.info("tcp 服务器 监听 端口 port = " + port);
		}
	}

    /**
     * 关闭 mina
     */
    public void stop(){
        acceptor.dispose();
    }

}
