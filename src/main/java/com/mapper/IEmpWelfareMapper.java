package com.mapper;

import com.po.EmpWelfare;
import com.po.Welfare;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("EmpWelfareDAO")
public interface IEmpWelfareMapper {
   //����Ա����������
	public int save(EmpWelfare ewf);
   //����Ա����ţ�ɾ����������
	public int delByEid(Integer eid);
   //����Ա����Ų���Ա����Ӧ�ĸ�������
	public List<Welfare> findByEid(Integer eid);
}
