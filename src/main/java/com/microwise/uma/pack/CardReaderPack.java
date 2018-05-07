package com.microwise.uma.pack;

import java.util.ArrayList;
import java.util.List;

/**
 * 读卡器信息包
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:34:26
 * 
 * @check 2013-05-06 xubaoji svn:3207
 * 
 */
public class CardReaderPack extends BasePack {

	/**获取读卡器sn 指令标识*/
	public static final short COMMAND_GETSN = 0x0001;
	
	/**获取读卡器id指令标识*/
	public static final short COMMAND_GETID = 0x0002;
	
	/**获取读卡器版本号 指令标识*/
	public static final short COMMAND_GETVERSION = 0x0003;

	/**获取读卡器id 响应包 中 sn 索引在包中开始位置*/
	public static final int COMMAND_GETID_SNINDEX = 7;
	
	/**获取读卡器id 响应包 中 id 索引在包中开始位置*/
	public static final int COMMAND_GETID_IDINDEX = 11;

	/**获得读卡器版本号数据响应包中 读卡器version索引位置  */
	public static final int COMMAND_GETVERSION_INDEX = 19;

	/**
	 * 获取id包
	 */
	private static List<Byte> buffer_GETID = new ArrayList<Byte>(9);
	
	static {
		buffer_GETID.add((byte) 0x05);
		buffer_GETID.add((byte) 0x00);
		buffer_GETID.add((byte) 0x06);
		buffer_GETID.add((byte) 0x80);
		buffer_GETID.add((byte) 0x01);
		buffer_GETID.add((byte) 0x00);
		buffer_GETID.add((byte) 0x02);
		buffer_GETID.add((byte) 0x6F);
		buffer_GETID.add((byte) 0x38);
	}
	
	/**
	 * 获取version包
	 */
	private static List<Byte> buffer_GETVERSION = new ArrayList<Byte>(9);
	
	static {
		buffer_GETVERSION.add((byte) 0x05);
		buffer_GETVERSION.add((byte) 0x00);
		buffer_GETVERSION.add((byte) 0x06);
		buffer_GETVERSION.add((byte) 0x80);
		buffer_GETVERSION.add((byte) 0x01);
		buffer_GETVERSION.add((byte) 0x00);
		buffer_GETVERSION.add((byte) 0x03);
		buffer_GETVERSION.add((byte) 0x7E);
		buffer_GETVERSION.add((byte) 0xB1);
	}

	/**读卡器 sn*/
	private String sn;
	
	/**读卡器设备id*/
	private String deviceId;
	
	/**读卡器版本号*/
	private String version;

	/**读卡器数据包 中指令类型*/
	private short command;
	
	public CardReaderPack(String sn, String deviceId, String version,
			short command) {
		this.sn = sn;
		this.deviceId = deviceId;
		this.version = version;
		this.command = command;
	}

	public CardReaderPack(short command) {
		this.command = command;
	}

	/**
	 * 组装获取读卡器sn 指令数据包
	 * @param ID 读卡器id
	 *
	 * @author 许保吉
	 * @date   2013-5-6 
	 * 
	 * @return List<Byte> 字节列表指令包
	 */
	public List<Byte> getSNPacK(byte[] ID) {
		List<Byte> buffer_GETSN = new ArrayList<Byte>(9);
		buffer_GETSN.add((byte) 0x05);
		buffer_GETSN.add((byte) 0x00);
		buffer_GETSN.add((byte) 0x06);
		buffer_GETSN.add((byte) 0x88);
		buffer_GETSN.add((byte) 0x00);
		buffer_GETSN.add((byte) 0x00);
		buffer_GETSN.add((byte) 0x01);
		buffer_GETSN.add((byte) 0x00);
		buffer_GETSN.add((byte) ID[0]);
		buffer_GETSN.add((byte) ID[1]);
		return buffer_GETSN;
	}

	/**
	 * 组装获取读卡器版本号包
	 * 
	 * @param ID  读卡器id
	 * @param SN  读卡器sn
	 *
	 * @author 许保吉
	 * @date   2013-5-6 
	 * 
	 * @return  List<Byte> 字节列表 指令包
	 */
	public List<Byte> getVersionPack(byte[] ID, byte[] SN) {

		List<Byte> buffer_GETVersion = new ArrayList<Byte>(17);
		buffer_GETVersion.add((byte) 0x05);
		buffer_GETVersion.add((byte) 0x00);
		buffer_GETVersion.add((byte) 0x06);
		buffer_GETVersion.add((byte) 0x88);
		buffer_GETVersion.add((byte) 0x00);
		buffer_GETVersion.add((byte) 0x00);
		buffer_GETVersion.add((byte) 0x02);
		buffer_GETVersion.add((byte) 0x00);
		buffer_GETVersion.add((byte) 0x00);
		// sn
		buffer_GETVersion.add(ID[0]);
		buffer_GETVersion.add(ID[1]);
		buffer_GETVersion.add(ID[2]);
		buffer_GETVersion.add(ID[3]);
		// id
		buffer_GETVersion.add(SN[0]);
		buffer_GETVersion.add(SN[1]);
		// 特殊字段
		buffer_GETVersion.add((byte) 0x00);
		buffer_GETVersion.add((byte) 0x00);
		return buffer_GETVersion;
	}

	/**
	 * 将字节集合中的字节放入字节数组
	 * 
	 * @param datagram
	 *            请求byte队列
	 * @return
	 */
	private static byte[] listToByteArray(List<Byte> datagram) {
		if (null != datagram && !datagram.isEmpty()) {
			int length = datagram.size();
			byte[] byteArray = new byte[length];
			for (int i = 0; i < length; i++) { // 将字节集合中的字节放入字节数组
				byteArray[i] = datagram.get(i);
			}
			return byteArray;
		} else {
			return new byte[0];
		}
	}

	public short getCommand() {
		return command;
	}

	public void setCommand(short command) {
		this.command = command;
	}
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public static byte[] getIDPack() {
		return listToByteArray(buffer_GETID);
	}

	public static byte[] getVersionPack() {
		return  listToByteArray(buffer_GETVERSION);
	}

	
}
