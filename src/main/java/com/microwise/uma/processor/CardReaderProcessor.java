package com.microwise.uma.processor;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.microwise.uma.bean.Request;
import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.pack.CardReaderPack;
import com.microwise.uma.po.DevicePO;

/**
 * 读卡器线程：循环 下发 获得读卡器信息指令，解析读卡器包
 *
 * @author xu.baoji
 * @date 2013-09-26
 */
@Component
@Scope("prototype")
public class CardReaderProcessor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CardReaderProcessor.class);

    /**
     * 读卡器 ip:prot
     */
    private String ipAndPort;

    /**
     * 用来判断线程是否运行
     */
    private AtomicBoolean isStop = new AtomicBoolean(false);

    private String message = "正在运行";

    /**
     * 系统初始化信息
     */
    @Autowired
    private SystemConfigure systemConfigure;

    @Override
    public void run() {
        logger.info("读卡器线程启动ip:port=" + ipAndPort);
        while (!isStop.get()) {
            try {
                Request request = systemConfigure.getReaderRequest(ipAndPort);
                // 获得读卡器session
                IoSession session = request.getSession();
                // 获得 读卡器 实体对象 一定不为null
                DevicePO readerDevice = request.getReaderDevice();

                // TODO 可考虑在此线程 解析 读卡器

                // 如果Session 为空
                if (session == null) break;
                // 如果 连接关闭 结束线程  或者 session 关闭
                if (session.isClosing() || readerDevice == null) {
                    logger.info("读卡器IP:PORT=" + ipAndPort + "发送指令线程关闭");
                    message = "读卡器session关闭，导致线程停止";
                    break;
                }
                // 发送 指令
                sendRequest(session, readerDevice);
                // 休眠
                Thread.sleep(5 * 1000);
            } catch (Exception e) {
                logger.error("读卡器线程出错ip:port=" + ipAndPort, e);
            }
        }
        if (isStop.get()) {
            message = "已解析到读卡器数据，线程关闭";
            logger.info("读卡器IP:PORT=" + ipAndPort + "发送指令线程关闭");
        }
    }

    /***
     * 下发 指令
     *
     * @author xu.baoji
     * @date 2013-9-26
     *
     * @param session
     * @param readerDevice
     */
    private void sendRequest(IoSession session, DevicePO readerDevice) {
        // 获得读卡器id 返回数据包可同时解析出读卡器sn
        if (readerDevice.getDeviceId() == null) {
            session.write(IoBuffer.wrap(CardReaderPack.getIDPack()));
        }
        // 获得读卡器 version
        if (readerDevice.getVersion() == null) {
            session.write(IoBuffer.wrap(CardReaderPack.getVersionPack()));
        }
    }

    public String getIpAndPort() {
        return ipAndPort;
    }

    public void setIpAndPort(String ipAndPort) {
        this.ipAndPort = ipAndPort;
    }

    public void stop() {
        isStop.set(true);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
