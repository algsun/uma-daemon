package com.microwise.uma.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microwise.uma.bean.GoBackRegular;
import com.microwise.uma.bean.OneWayRegular;
import com.microwise.uma.bean.ParentRegular;
import com.microwise.uma.bean.SubRegular;
import com.microwise.uma.dao.RegularDao;
import com.microwise.uma.dao.RegularExciterDao;
import com.microwise.uma.po.AnalyseStatePO;
import com.microwise.uma.po.Regular;
import com.microwise.uma.po.RegularExciter;
import com.microwise.uma.service.RegularService;

/**
 * 规则service接口的实现
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:08:39
 * 
 * @check 2013-05-07 xubaoji svn:2931
 */
@Service
@Transactional
@Scope("prototype")
public class RegularServiceImpl implements RegularService {

	/**
	 * 规则dao接口
	 */
	@Autowired
	private RegularDao regularDao;
	/**
	 * 规则顺序dao接口
	 */
	@Autowired
	private RegularExciterDao regExtdao;

	@Override
	public List<Regular> findRegulars() {
		return regularDao.findRegularByType();
	}

	@Override
	public List<Regular> findOneDirectionRegulars() {
		List<Regular> res = new ArrayList<Regular>();
		res.addAll(regularDao.findRegularByType(1));// 纯单程
		res.addAll(regularDao.findRegularByType(3));// 往
		res.addAll(regularDao.findRegularByType(4));// 返
		return res;
	}

	@Override
	public List<Regular> findOneDirectionwholeRegInfo() {

		List<Regular> res = new ArrayList<Regular>();
		res.addAll(regularDao.findRegularByType(1));// 纯单程
		res.addAll(regularDao.findRegularByType(3));// 往
		res.addAll(regularDao.findRegularByType(4));// 返

		for (Regular reg : res) {
			List<RegularExciter> regExciters = regExtdao
					.findRegularExcitersByTempId(reg.getId());
			reg.setRes(regExciters);
		}

		return res;
	}

	@Override
	public List<AnalyseStatePO> findAnalySeState() {
		return null;
	}

	@Override
	public List<ParentRegular> findAllRegulars() {
		List<ParentRegular> pRegulars = new ArrayList<ParentRegular>();
		List<Regular> pres = new ArrayList<Regular>();
		// 单程规则
		pres = regularDao.findRegularByParentId(0, 1);
		if (pres != null && pres.size() > 0) {
			for (Regular pR : pres) {
				//单程规则查询子规则
				OneWayRegular oneWayRegular = new OneWayRegular(pR.getId());
				List<Regular> subList = regularDao.findRegularByParentId(
						pR.getId(), 1);
				for (Regular subreg : subList) {
					List<RegularExciter> regExciters = regExtdao
							.findRegularExcitersByTempId(subreg.getId());
					subreg.setRes(regExciters);
				}
				oneWayRegular.setSubRegulars(new SubRegular(1, subList));
				pRegulars.add(oneWayRegular);
			}
		}

		// 往返规则
		pres = new ArrayList<Regular>();
		pres = regularDao.findRegularByParentId(0, 2);
		if (pres != null && pres.size() > 0) {
			for (Regular pR : pres) {
				GoBackRegular gobackRegular = new GoBackRegular(pR.getId());
				
				//往规则
				List<Regular> goList = regularDao.findRegularByParentId(
						pR.getId(), 3);
				for (Regular subreg : goList) {
					List<RegularExciter> regExciters = regExtdao
							.findRegularExcitersByTempId(subreg.getId());
					subreg.setRes(regExciters);
				}
				gobackRegular.setGoRegulars(new SubRegular(3 , goList));
				
				//返规则
				List<Regular> backList = regularDao.findRegularByParentId(
						pR.getId(), 4);
				for (Regular subreg : backList) {
					List<RegularExciter> regExciters = regExtdao
							.findRegularExcitersByTempId(subreg.getId());
					subreg.setRes(regExciters);
				}
				gobackRegular.setBackRegulars(new SubRegular(4 , backList));
				
				pRegulars.add(gobackRegular);
			}
		}

		return pRegulars;
	}

	@Override
	public int getMaxRegularLength() {
		return regularDao.getMaxRegularLen();
	}

}
