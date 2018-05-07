package com.microwise.uma.pack;

import java.nio.ByteBuffer;

/**
 * 基本包 ： 拆包线程将数据接受队列中数据封装为基本数据包
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:34:09
 * 
 * @check 2013-05-06 xubaoji svn:3156
 * @check 2013-10-14 @gaohui #5658
 * 
 */
public class BasePack {

	/** 包格式 STX MSG LEN FLAGS COMMAND ID S/N PIN ENC CHK {DATA} CRC */

	/** 错误判断字节长度 （最后两位） */
	public static final int ERROR_LEN = 2;

	/** 包头标识 */
	public static final int STX = 0x06;

	/** 包长度位所占字节长度 */
	public static final int MSGLEN = 2;
	
	/**数据包指令类型起始位置 两位*/
	public static final int PACK_COMMAND_INDEX = 5;

	/** 标识位 */
	public static final int[] FLAGS = new int[] { 0, 0 };

	/** 错包标识 */
	public static final int[][] ERRORS = new int[][] { { 0x00, 0x00 }, { 0xff, 0xff } };

	/** 包数据 */
	protected ByteBuffer datas;

	/** 装包时间 */
	protected Long dateTimes;

	/** 对应上传数据的读卡器的IP:port 格式数据 （用来唯一标识 一个读卡器设备） */
	// TODO 建议改为 ipAndPort @gaohui 2013-10-14
	protected String ipAndPort;

	/** 数据 包站点 */
	protected String siteId;

	public Long getDate() {
		return dateTimes;
	}

	public void setDate(Long date) {
		this.dateTimes = date;
	}

	public ByteBuffer getDatas() {
		return datas;
	}

	public void setDatas(ByteBuffer datas) {
		this.datas = datas;
	}

	public String getIpAndPort() {
		return ipAndPort;
	}

	public void setIpAndPort(String ipAndPort) {
		this.ipAndPort = ipAndPort;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
