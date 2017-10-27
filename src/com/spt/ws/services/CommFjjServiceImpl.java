package com.spt.ws.services;


import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.spt.ws.dao.IDaoService;


public class CommFjjServiceImpl implements ICommFjjService{
	private static Logger log = LogManager.getLogger("com.spt.ws.ws");
	@Autowired
	private IDaoService daoService;
	
	@Override
	public String getFJXX(String parm) {
		log.info("receive msg is :" + parm);
		StringBuffer sb = new StringBuffer("#MSG::");
		String lines = this.getParm(parm);
		if(lines==null || "".equals(lines)){
			sb.append("-1::no rows::#END");
			return sb.toString();
		}
		try {
			daoService.getFJXX(lines);
			sb.append("0::success::#END");
		} catch (Exception e) {
			sb.append("-1::").append(e.getMessage()).append("::#END");
			e.printStackTrace();
			log.error(e.getMessage());
		}
		log.info("return msg is :" + sb.toString());
		return sb.toString();
	}
	
	
	private String getParm(String s){
		if("#HEAD::#END".equalsIgnoreCase(s)) return null;
		if (s.startsWith("#HEAD") && s.endsWith("#END")){
			String a = s.replace("#HEAD", "");
			a = a.replace("#END", "");
			return a;
		}else{
			return null;
		}
		
	}


	
	
}