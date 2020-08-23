package com.action;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.po.Dep;
import com.po.Emp;
import com.po.PageBean;
import com.po.Welfare;
import com.service.BieService;
import com.util.AjAxUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmpAction implements IAction {
    @Resource(name="BieService")
	private BieService bizService;
    
	public BieService getBizService() {
		return bizService;
	}

	public void setBizService(BieService bizService) {
		this.bizService = bizService;
	}

	@Override
	@RequestMapping(value="save_Emp.do")
	public String save(HttpServletRequest request, HttpServletResponse response, Emp emp) {
		System.out.println(emp.toString());
		String realpath = request.getRealPath("/");
		/***************** �ϴ��ļ� ************************************/
		// ��ȡ�ϴ���Ƭ�Ķ���
		MultipartFile multipartFile = emp.getPic();
		if (multipartFile != null && !multipartFile.isEmpty()) {
			// ��ȡ�ϴ����ļ�����
			String fname = multipartFile.getOriginalFilename();
			// ����
			if (fname.lastIndexOf(".") != -1) {// ���ں�׺
				// ��ȡ��׺��
				String ext = fname.substring(fname.lastIndexOf("."));

				// �жϺ�׺�Ƿ�Ϊjpg��ʽ
				if (ext.equalsIgnoreCase(".jpg")) {
					// ����
					String newfname = new Date().getTime() + ext;
					// �����ļ�����ָ���ϴ��ļ���·��
					File destFile = new File(realpath + "/uppic/" + newfname);
					// �ϴ�
					try {
						FileUtils.copyInputStreamToFile(
								multipartFile.getInputStream(), destFile);
						emp.setPhoto(newfname);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		/****************************************************************************/
		boolean flag=bizService.getEmpBiz().save(emp);
		if(flag){
			AjAxUtils.printString(response, 1+"");
		}else{
			AjAxUtils.printString(response, 0+"");
		}
		return null;
	}

	@Override
	@RequestMapping(value="update_Emp.do")
	public String update(HttpServletRequest request, HttpServletResponse response, Emp emp) {
		String realpath = request.getRealPath("/");
		//��ȡԭ����Ա����Ƭ
		String oldphoto=bizService.getEmpBiz().findById(emp.getEid()).getPhoto();
		
		/***************** �ϴ��ļ� ************************************/
		// ��ȡ�ϴ���Ƭ�Ķ���
		MultipartFile multipartFile = emp.getPic();
		if (multipartFile != null && !multipartFile.isEmpty()) {
			// ��ȡ�ϴ����ļ�����
			String fname = multipartFile.getOriginalFilename();
			// ����
			if (fname.lastIndexOf(".") != -1) {// ���ں�׺
				// ��ȡ��׺��
				String ext = fname.substring(fname.lastIndexOf("."));

				// �жϺ�׺�Ƿ�Ϊjpg��ʽ
				if (ext.equalsIgnoreCase(".jpg")) {
					// ����
					String newfname = new Date().getTime() + ext;
					// �����ļ�����ָ���ϴ��ļ���·��
					File destFile = new File(realpath + "/uppic/" + newfname);
					// �ϴ�
					try {
						FileUtils.copyInputStreamToFile(
								multipartFile.getInputStream(), destFile);
						emp.setPhoto(newfname);
						
						//ɾ��ԭ������Ƭ
						File oldfile=new File(realpath+"/uppic/"+oldphoto);
						if(oldfile.exists()&&!oldphoto.equalsIgnoreCase("default.jpg")){
							oldfile.delete();//ɾ��
							
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else{
			emp.setPhoto(oldphoto);//��û�и���Ա����Ƭʱ������ԭ��Ա������ƬΪ����Ƭ
		}
		/****************************************************************************/
		boolean flag=bizService.getEmpBiz().update(emp);
		if(flag){
			AjAxUtils.printString(response, 1+"");
		}else{
			AjAxUtils.printString(response, 0+"");
		}
		return null;
	}

	@Override
	@RequestMapping(value="delById_Emp.do")
	public String delById(HttpServletRequest request, HttpServletResponse response, Integer eid) {
		boolean flag=bizService.getEmpBiz().delById(eid);
		if(flag){
			AjAxUtils.printString(response, 1+"");
		}else{
			AjAxUtils.printString(response, 0+"");
		}
		return null;
	}

	@Override
	@RequestMapping(value="findById_Emp.do")
	public String findById(HttpServletRequest request, HttpServletResponse response, Integer eid) {
		Emp oldemp=bizService.getEmpBiz().findById(eid);
		PropertyFilter propertyFilter= AjAxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(oldemp,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AjAxUtils.printString(response, jsonstr);
		return null;
	}

	@Override
	@RequestMapping(value="findDetail_Emp.do")
	public String findDetail(HttpServletRequest request, HttpServletResponse response, Integer eid) {
		Emp oldemp=bizService.getEmpBiz().findById(eid);
		PropertyFilter propertyFilter= AjAxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(oldemp,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AjAxUtils.printString(response, jsonstr);
		return null;
	}

	@Override
	@RequestMapping(value="findPageAll_Emp.do")
	public String findPageAll(HttpServletRequest request, HttpServletResponse response, Integer page, Integer rows) {
		System.out.println("111111111111111111111111111");
		Map<String,Object> map=new HashMap<String,Object>();
		PageBean pb=new PageBean();
		page=page==null||page<1?pb.getPage():page;
		rows=rows==null||rows<1?pb.getRows():rows;
		if(rows>10)rows=10;
		pb.setPage(page);
		pb.setRows(rows);
		//��ȡ��¼����
		List<Emp> lsemp=bizService.getEmpBiz().findPageAll(pb);
		//��ȡ�ܼ�¼��
		int maxrows=bizService.getEmpBiz().findMaxRow();
		map.put("page", page);
		map.put("rows", lsemp);
		map.put("total", maxrows);
		PropertyFilter propertyFilter= AjAxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(map, propertyFilter, SerializerFeature.DisableCircularReferenceDetect);
		AjAxUtils.printString(response, jsonstr);
		return null;
	}

	@Override
	@RequestMapping(value="doinit_Emp.do")
	public String doinit(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map=new HashMap<String,Object>();
		List<Dep> lsdep=bizService.getDepBiz().findAll();
		List<Welfare> lswf=bizService.getWelfareBiz().findAll();
		map.put("lsdep", lsdep);
		map.put("lswf", lswf);
		PropertyFilter propertyFilter= AjAxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(map, propertyFilter, SerializerFeature.DisableCircularReferenceDetect);
		AjAxUtils.printString(response, jsonstr);
		return null;
	}

}
