package com.microwise.uma.po;

import java.io.Serializable;
import java.util.Date;

import com.microwise.uma.controller.SystemConfigure;

/**
 * 设备PO
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:01:07
 * 
 * @check 2013-05-06 xubaoji svn:2978
 */
public class DevicePO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id编号 */
	private Long id;

	/** 设备类型 */
	private Integer type;

	/** 设备id */
	private String deviceId;

	/** 设备名称 （默认使用设备sn 编号） */
	private String name;

	/** 设备sn */
	private String sn;

	/** 读卡器ip 地址 */
	private String ip;

	/** 读卡器 端口号 */
	private Integer port;

	/** 读卡器版本 */
	private String version;

	/** 电压 */
	private Float voltage;

	/** 读卡器状态 1：正常 0：异常 */
	private Integer state = 1;

	/** 设备是否启用 1：启用 0：禁用 */
	private Integer enable = 0;

	/** 创建时间 */
	private Date createTime;

	/** 最后工作时间 */
	private Date updateTime;

	/** 站点编号 */
	private String siteId;

	/**
	 * 最后工作时间标记时间
	 */
	private Date lastUpdateTime;

	public DevicePO() {

	}

	/**
	 * 封装激发器
	 * 
	 * @param sn
	 *            设备sn 编号
	 * @param type
	 *            设备类型应该为2
	 * @param createTime
	 *            创建时间
	 */
	public DevicePO(String sn, Integer type, Date createTime) {
		this.sn = sn;
		this.type = type;
		this.createTime = createTime;
		this.name = sn;
	}

	/**
	 * 封装电子卡
	 * 
	 * @param sn
	 *            设备sn 编号
	 * @param type
	 *            设备类型 应该为3
	 * @param createTime
	 *            创建时间
	 * @param voltage
	 *            电量
	 */
	public DevicePO(String sn, Integer type, Date createTime, Float voltage) {
		this.sn = sn;
		this.type = type;
		this.createTime = createTime;
		this.voltage = voltage;
		this.name = sn;
	}

	/**
	 * 封装读卡器
	 * 
	 * @param type
	 *            设备类型 （读卡器应该为 1）
	 * @param deviceId
	 *            设备id 编号
	 * @param sn
	 *            sn 编号
	 * @param createTime
	 *            创建时间
	 */
	public DevicePO(Integer type, String deviceId, String sn, Date createTime) {
		this.sn = sn;
		this.type = type;
		this.createTime = createTime;
		this.name = sn;
		this.deviceId = deviceId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Float getVoltage() {
		return voltage;
	}

	public void setVoltage(Float voltage) {
		this.voltage = voltage;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 是否需要更新设备信息 判断时间间隔是否大于配置文件中设置的时间间隔，如果需要将设备的更新时间设置为 packDate
	 * 
	 * @return boolean
	 */
	public boolean isNeedToUpd(long packDate) {
		lastUpdateTime = lastUpdateTime == null ? createTime : lastUpdateTime;
		if (packDate - lastUpdateTime.getTime() > SystemConfigure.UPDATE_FLAG * 60 * 1000) {
			updateTime = new Date(packDate);
			return true;
		}
		return false;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		this.lastUpdateTime = this.updateTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
