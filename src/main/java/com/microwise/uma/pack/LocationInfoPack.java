package com.microwise.uma.pack;

/**
 * 位置信息包
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:36:57
 * 
 * @check 2013-05-06 xubaoji svn:2915
 */
public class LocationInfoPack extends BasePack {

	/** 指令类型 2 Byte 60 40 */
	public static final short COMMAND = 0x6040;

	/** 获得激发器索引 （41包长的索引，33位的数据包在该索引上减8）取两位 */
	public static final int EXCITER_INDEX = 39;

	/** 电子卡 sn 号 （41包长的索引，33位的数据包在该索引上减8）取四位 */
	public static final int SN_INDEX = 27;

	/** 获得电子卡电量 （41包长的索引，33位的数据包在该索引上减8）取1位 */
	public static final int VOLTAGE_INDEX = 34;
	
	// TODO 此处的 电子卡id 和 激发器 id 实际为 sn 编号    @xu.baoji 2013.09.17

	/** 电子卡ID */
	private String cardId;

	/** 激发器ID */
	private String exciterId;

	/** 电子卡电量 */
	private Float voltage;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getExciterId() {
		return exciterId;
	}

	public void setExciterId(String exciterId) {
		this.exciterId = exciterId;
	}

	public Float getVoltage() {
		return voltage;
	}

	public void setVoltage(Float voltage) {
		this.voltage = voltage;
	}

}
