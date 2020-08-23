package com.mapper;

import com.po.Emp;
import com.po.PageBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("EmpDAO")
public interface IEmpMapper {
  public int save(Emp emp);
  public int update(Emp emp);
  public int delById(Integer eid);
  public Emp findById(Integer eid);
  public List<Emp> findPageAll(PageBean pb);
  public int findmaxRows();
  //��ȡԱ�����id
  public int findMaxEid();
}
