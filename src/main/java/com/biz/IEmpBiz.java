package com.biz;

import com.po.Emp;
import com.po.PageBean;

import java.util.List;

public interface IEmpBiz {
 public boolean save(Emp emp);
 public boolean update(Emp emp);
 public boolean delById(Integer eid);
 public Emp findById(Integer eid);
 public List<Emp> findPageAll(PageBean pb);
 public int findMaxRow();
}
