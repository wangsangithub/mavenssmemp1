package com.service;

import com.biz.IDepBiz;
import com.biz.IEmpBiz;
import com.biz.IWelfareBiz;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("BieService")
public class BieService {
   @Resource(name="DepBiz")
   private IDepBiz depBiz;
   @Resource(name="EmpBiz")
   private IEmpBiz empBiz;
   @Resource(name="WelfareBiz")
   private IWelfareBiz welfareBiz;
public IDepBiz getDepBiz() {
	return depBiz;
}
public void setDepBiz(IDepBiz depBiz) {
	this.depBiz = depBiz;
}
public IEmpBiz getEmpBiz() {
	return empBiz;
}
public void setEmpBiz(IEmpBiz empBiz) {
	this.empBiz = empBiz;
}
public IWelfareBiz getWelfareBiz() {
	return welfareBiz;
}
public void setWelfareBiz(IWelfareBiz welfareBiz) {
	this.welfareBiz = welfareBiz;
}
   
}
