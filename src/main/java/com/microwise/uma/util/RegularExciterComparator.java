package com.microwise.uma.util;

import java.util.Comparator;

import com.microwise.uma.po.RegularExciter;

/**
 * 规则排序比较器
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:22:31
 */
public class RegularExciterComparator implements Comparator<RegularExciter> {

	@Override
	public int compare(RegularExciter o1, RegularExciter o2) {
		return (int) (o1.getSequence() - o2.getSequence());
	}

}
