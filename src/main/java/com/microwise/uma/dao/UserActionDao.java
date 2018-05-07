package com.microwise.uma.dao;

import com.microwise.uma.po.CircuitedActionPO;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.po.UserCensusPO;

import java.util.Date;
import java.util.List;

/**
 * 用户行为Dao接口
 *
 * @author houxiaocheng
 * @date 2013-4-24 下午2:40:34
 * @check 2013-05-07 xubaoji svn:2954
 */
public interface UserActionDao {

    /**
     * 用户匹配规则入库
     *
     * @param userActions 用户行为队列
     */
    public void saveUserAction(List<UserActionPO> userActions);

    /**
     * 用户匹配后的往返规则入库
     *
     * @param userActions 往返行为记录List
     */
    public void saveCircuitedAction(List<CircuitedActionPO> userActions);

    /**
     * 根据用户id查询该用户当前最新的行为
     *
     * @param userId 用户Id
     * @param times  分析记录时间
     * @return
     */
    public List<UserActionPO> findActionsAfterLastTime(Long userId, Long times);

    /**
     * 查询给定时间后用户最新的行为
     *
     * @param times
     * @return 用户最新行为列表
     */
    public List<UserActionPO> findUserLastAction(Long times);

    /**
     * 修改分析状态为已分析
     * 根据userAction的Id修改对应的checkFlag值
     *
     * @param userAction 用户行为
     */
    public void updateActionFlag(UserActionPO userAction);

    /**
     * 根据匹配标识获取用户的行为
     *
     * @param checkFlag 匹配标识
     * @return 用户的行为
     */
    public List<UserActionPO> findUserActionsByCheckFlag(int checkFlag);

    /**
     * 查询不包括单程的用户行为
     *
     * @return
     */
    public List<UserCensusPO> findUserCensusWithoutSingleRule(long startTime, long endTime, String zoneId);

    /**
     * 是否存在用户停留时间记录
     *
     * @param zoneId     区域编号
     * @param createTime 创建的时间
     * @return
     */
    public boolean isExistUserStayTimesRecord(String zoneId, Date createTime);

    /**
     * 保存用户停留时间信息
     *
     * @param zoneId     区域编号
     * @param stayTimes  停留的时间
     * @param createTime 创建的时间
     */
    public void saveUserStayTimes(String zoneId, long stayTimes, Date createTime);

    /**
     * 修改用户停留时间信息
     *
     * @param zoneId     区域编号
     * @param stayTimes  停留的时间
     * @param createTime 创建的时间
     */
    public void updateUserStayTimes(String zoneId, long stayTimes, Date createTime);

}
