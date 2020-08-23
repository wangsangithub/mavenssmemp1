package com.biz.impl;

import com.biz.IWelfareBiz;
import com.po.Welfare;
import com.service.DaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("WelfareBiz")
@Transactional
public class WelfareBiz implements IWelfareBiz {
	@Resource(name="DaoService")
	   private DaoService daoservice;
	
	public DaoService getDaoservice() {
		return daoservice;
	}

	public void setDaoservice(DaoService daoservice) {
		this.daoservice = daoservice;
	}

	@Override
	public List<Welfare> findAll() {
		// TODO Auto-generated method stub
		return daoservice.getWelfaremapper().findAll();
	}

}
