package com.microwise.uma.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import com.microwise.uma.util.RegularExciterComparator;

/**
 * 规则对象
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:31:37
 * 
 * @check 2013-05-06 xubaoji svn:3194
 */
public class Regular implements Serializable {
	private static final long serialVersionUID = 1L;

	/** id编号 */
	private Long id;

	/** 父规则编号 */
	private Long parentId;

	/** 规则名称 */
	private String name;

	/** 规则类型 */
	private int type;

	/** 是否可用 1 可用 0 不可用 */
	private int enable;

    /** 区域ID */
    private String zoneId;

	/**
	 * 正则匹配对象
	 */
	private Pattern pattern;

	public Regular() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public List<RegularExciter> getRes() {
		return res;
	}

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String  zoneId) {
        this.zoneId = zoneId;
    }

    /**
	 * 如果激发器顺序不为空，则生成对应正则匹配对象
	 * 
	 * @param res 激发其顺序
	 */
	public void setRes(List<RegularExciter> res) {
		this.res = res;
		if (res != null && res.size() >= 2) {
			// 先排序
			Collections.sort(res, new RegularExciterComparator());
			StringBuffer regStr = new StringBuffer("");
			for (int i = 0; i < res.size(); i++) {
				RegularExciter regExciter = res.get(i);
				if (i == 0) {
					regStr.append("'"+regExciter.getDeviceId()+"'");
					regStr.append("\\{(\\d+)\\}");
					continue;
				} else if (i > 0 && i < res.size() - 1) {
					regStr.append("'"+regExciter.getDeviceId()+"'");
					regStr.append("\\{\\d+\\}");
				} else if (i == res.size() - 1) {
					regStr.append("'"+regExciter.getDeviceId()+"'");
					regStr.append("\\{(\\d+)\\}");
				}

			}
			this.pattern = Pattern.compile(regStr.toString());
		}
	}

	private List<RegularExciter> res = new ArrayList<RegularExciter>();

	/**
	 * 转换为可用的正则匹配表达式
	 */
	public Pattern toPattern() {
		return this.pattern;
	}

}
