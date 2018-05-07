package com.microwise.uma.util;

import java.util.Comparator;

import com.microwise.uma.po.UserActionPO;

/**
 * 规则排序比较器
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:22:31
 */
public class UserActionComparator implements Comparator<UserActionPO> {

	@Override
	public int compare(UserActionPO o1, UserActionPO o2) {
		//优先比较结束时间
		int t = (int) (o1.getEndTime() - o2.getEndTime());
		//结束时间相同 再比较开始时间
		if(t == 0){
			t = (int) (o1.getStartTime() - o2.getStartTime());

            //开始时间和结束时间都相同 则比较类型
            if(t == 0){
                t = o1.getType() - o2.getType();
            }
		}
        return t;
	}
	
}
