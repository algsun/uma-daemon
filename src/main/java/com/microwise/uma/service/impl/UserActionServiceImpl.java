package com.microwise.uma.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microwise.uma.dao.AnalyseStateDao;
import com.microwise.uma.dao.UserActionDao;
import com.microwise.uma.po.AnalyseStatePO;
import com.microwise.uma.po.CircuitedActionPO;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.service.UserActionService;

/**
 * 用户行为service接口实现
 * 
 * @author houxiaocheng
 * @date 2013-4-25 下午2:47:46
 * 
 * @check 2013-05-07 xubaoji svn:3040
 */
@Service
@Transactional
@Scope("prototype")
public class UserActionServiceImpl implements UserActionService {

	/**
	 * 用户行为dao
	 */
	@Autowired
	private UserActionDao userActionDao;
	/**
	 * 分析记录dao
	 */
	@Autowired
	private AnalyseStateDao analyseStateDao;

	@Override
	public void updateAnalyseStateOfLoaction(Long userId, Long times) {
		//查找用户对应的分析记录对象
		AnalyseStatePO po = analyseStateDao.findStateByUseId(userId);
		//如果分析记录为空，则需要新增一条记录
		if (po == null) {
			po = new AnalyseStatePO();
			po.setUserId(userId);
			po.setLocationTime(times);
			analyseStateDao.saveAnalyseState(po);
		} else {
			//如果已有该用户的分析记录 则需要对该记录进行更新
			analyseStateDao.updateLocationTime(userId, times);
		}
	}

	@Override
	public void saveUserActions(List<UserActionPO> actions) {
		if (actions != null && actions.size() > 0) {
			userActionDao.saveUserAction(actions);
		}
	}

	@Override
	public void updateAnalyseStateOfAction(Long userId, Long times) {
		//查找用户对应的分析记录对象
		AnalyseStatePO po = analyseStateDao.findStateByUseId(userId);
		//如果分析记录为空，则需要新增一条记录
		if (po == null) {
			po = new AnalyseStatePO();
			po.setUserId(userId);
			po.setActionTime(times);
			analyseStateDao.saveAnalyseState(po);
		} else {
			//如果已有该用户的分析记录 则需要对该记录进行更新
			analyseStateDao.updateActionTime(userId, times);
		}
	}

	@Override
	public List<UserActionPO> getUserLastAction(Date date) {
		List<UserActionPO> res = new ArrayList<UserActionPO>();
        // TODO date在sql那儿都判断了，这儿根本不需要判断
		res = userActionDao.findUserLastAction(date == null ? null : date
				.getTime());
		return res;
	}

	@Override
	public List<UserActionPO> getUserActions(Long userId) {
		Long checkTime = null;
		// 获取行为分析时间刻度
		AnalyseStatePO po = analyseStateDao.findStateByUseId(userId);
		if (po != null) {
			checkTime = po.getActionTime();
		}
		List<UserActionPO> res = new ArrayList<UserActionPO>();
		// 如果没有行为分析记录，则查找该用户的所有行为
		res = userActionDao.findActionsAfterLastTime(userId, checkTime);
		return res;
	}

	@Override
	public void updateUserActionFlags(List<UserActionPO> userActions) {
		if (userActions != null && userActions.size() > 0) {
			for (UserActionPO action : userActions) {
				try {
					userActionDao.updateActionFlag(action);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void saveCircuitedActions(List<CircuitedActionPO> actions) {
		if (actions != null && actions.size() > 0) {
			userActionDao.saveCircuitedAction(actions);
		}
	}

}
