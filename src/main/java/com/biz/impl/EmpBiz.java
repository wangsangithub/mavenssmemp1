package com.biz.impl;

import com.biz.IEmpBiz;
import com.po.*;
import com.service.DaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("EmpBiz")
@Transactional
public class EmpBiz implements IEmpBiz {
	@Resource(name="DaoService")
	   private DaoService daoservice;
	
	public DaoService getDaoservice() {
		return daoservice;
	}

	public void setDaoservice(DaoService daoservice) {
		this.daoservice = daoservice;
	}

	@Override
	public boolean save(Emp emp) {
		int code=daoservice.getEmpmapper().save(emp);
		//��Ϊ�����Ա��ʱ����Ҫ���н�ʺ͸�������Ҫ�õ�Ա��Id
		if(code>0){
			//��ȡԱ������ŵķ���
			int eid=daoservice.getEmpmapper().findMaxEid();
			/**����н��**/
			Salary sa=new Salary(eid,emp.getEmoney());
			daoservice.getSalarymapper().save(sa);
			/**����н��end**/
			/**��ȡԱ������id����**/
			String[] wids=emp.getWids();
			if(wids!=null&&wids.length>0){
				for (int i = 0; i < wids.length; i++) {
					EmpWelfare ewf=new EmpWelfare(eid,new Integer(wids[i]));
					daoservice.getEmpwelfaremapper().save(ewf);
				}
			}
			/**��ȡԱ������id����end**/
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Emp emp) {
		int code=daoservice.getEmpmapper().update(emp);
		if(code>0){
			/**����н��**/
			 //��ȡ��Ա����ԭ��н��
			Salary oldsalary=daoservice.getSalarymapper().findSalaryByEid(emp.getEid());
			if(oldsalary!=null&&oldsalary.getEmoney()!=null){
				oldsalary.setEmoney(emp.getEmoney());//н�ʸĶ�
				daoservice.getSalarymapper().updateByEid(oldsalary);
			}else{
				Salary sa=new Salary(emp.getEid(),emp.getEmoney());
				daoservice.getSalarymapper().save(sa);
			}
			/**����н��end**/
			/**���¸���**/
			 //��ȡԭ���ĸ���
			List<Welfare> lswf=daoservice.getEmpwelfaremapper().findByEid(emp.getEid());
			if(lswf!=null&&lswf.size()>0){
				//ɾ��ԭ�и���
				daoservice.getEmpwelfaremapper().delByEid(emp.getEid());
			}
			//����µĸ���
			String[] wids=emp.getWids();
			if(wids!=null&&wids.length>0){
			   for (int i = 0; i < wids.length; i++) {
				   EmpWelfare ewf=new EmpWelfare(emp.getEid(),new Integer(wids[i]));
				   daoservice.getEmpwelfaremapper().save(ewf);
				}	
			}
			
			/**���¸���end**/
			return true;
		}
		return false;
	}

	@Override
	public boolean delById(Integer eid) {
		//��ɾ���ӱ�����
		daoservice.getSalarymapper().delByEid(eid);
		daoservice.getEmpwelfaremapper().delByEid(eid);
		//ɾ��Ա������
		int code=daoservice.getEmpmapper().delById(eid);
		if(code>0){
			return true;
		}
		return false;
	}

	@Override
	public Emp findById(Integer eid) {
		//��ȡԱ������
		Emp emp=daoservice.getEmpmapper().findById(eid);
		/**��ȡн����ӵ�emp����**/
		Salary sa=daoservice.getSalarymapper().findSalaryByEid(eid);
		if(sa!=null&&sa.getEmoney()!=null){
			emp.setEmoney(sa.getEmoney());
		}
		/**��ȡн����ӵ�emp����end**/
		/**��ȡ������ӵ�emp����**/
		  //����Ա����Ż�ȡ��������
		List<Welfare> lswf=daoservice.getEmpwelfaremapper().findByEid(eid);
		if(lswf!=null&&lswf.size()>0){
			//��������id����
			String[] wids=new String[lswf.size()];
			for (int i = 0; i < wids.length; i++) {
				Welfare wf=lswf.get(i);
				wids[i]=wf.getWid().toString();
			}
			emp.setWids(wids);
		}
		emp.setLswf(lswf);
		/**��ȡ������ӵ�emp����end**/
		return emp;
	}

	@Override
	public List<Emp> findPageAll(PageBean pb) {
		if(pb==null){
			pb=new PageBean();
		}
		return daoservice.getEmpmapper().findPageAll(pb);
	}

	@Override
	public int findMaxRow() {
		// TODO Auto-generated method stub
		return daoservice.getEmpmapper().findmaxRows();
	}

}
