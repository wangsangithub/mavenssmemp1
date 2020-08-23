package com.mapper;

import com.po.Welfare;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("WelfareDAO")
public interface IWelfareMapper {
 public List<Welfare> findAll();
}
