package com.microwise.uma.quartz;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 *  数据持久化统计,方便开发调试
 * 
 * @author xu.baoji
 * @date 2013-9-27
 */
@Component
@Aspect
public class PersistenceState {
	
	private static final Logger logger = LoggerFactory.getLogger(PersistenceState.class);

    public static final PersistenceState  instance = new PersistenceState();

    // 总记写操作了多少数据包
    private AtomicInteger packetWrites = new AtomicInteger(0);

    // 数据包临时写入次数
    private AtomicInteger packetWritesForSpeed = new AtomicInteger(0);

    // 每秒入库次数
    private AtomicReference<Float> packWritesPerSecond = new AtomicReference<Float>(new Float(0));

    /**
     * 返回实例
     *
     * @return
     */
    public static PersistenceState getInst(){
        return instance;
    }

    /**
     * 返回入库包数
     *
     * @return
     */
    public int packetWrites(){
        return packetWrites.get();
    }

    /**
     * 入库包数加1, 并返回总入库次数
     *
     * @return
     */
    public int incrPacketWrites(int delta){
        packetWritesForSpeed.addAndGet(delta);
        return packetWrites.addAndGet(delta);
    }
    
    /**重置入库 包临时统计*/
    public void resetPacketWritesForSpeed(){
    	packetWritesForSpeed.set(0);
    }

    /**
     * 重置入库包数
     *
     */
    public void resetPacketWrites(){
        packetWrites.set(0);
    }

    public AtomicInteger getPacketWritesForSpeed(){
        return packetWritesForSpeed;
    }

    @SuppressWarnings("rawtypes")
	public AtomicReference getPacketWritesPerSecond(){
        return packWritesPerSecond;
    }
    
    

	@SuppressWarnings("rawtypes")
	@Around("execution (* com.microwise.uma.service.LocationService.saveLocationsBatch(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        List deviceBeans = (List) point.getArgs()[0];
        Object o = point.proceed();
        PersistenceState.getInst().incrPacketWrites(deviceBeans.size());
        return o;
    }

    public void print(){
        logger.debug("历史位置包入库次数: {}", packetWrites());
    }
    
	
}
