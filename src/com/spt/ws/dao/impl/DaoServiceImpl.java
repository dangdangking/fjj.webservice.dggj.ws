package com.spt.ws.dao.impl;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;












import com.spt.fjj.service.models.common.AnnotationCommonService;
import com.spt.util.DateHelper;
import com.spt.ws.dao.IDaoService;

@Service
public class DaoServiceImpl extends AnnotationCommonService implements IDaoService {
	private static Logger log = LogManager.getLogger("com.spt.ws.ws");
	private String[] getLines(String parm){
		return parm.split("\\|\\|");
	}
	private String[] getCells(String line){
		String tmp = line.trim();
		if(tmp.startsWith("::")) tmp = tmp.substring(2);
		String t[] = tmp.split("::");
		for (int i = 0;i < t.length; i++){
			t[i] = t[i].trim();
		}
		return t;
	}
	private String[] getCells(String line,int len){
		String tmp = line.trim();
		if(tmp.startsWith("::")) tmp = tmp.substring(2);
		String t[] = tmp.split("::",len);
		for (int i = 0;i < t.length; i++){
			t[i] = t[i].trim();
		}
		return t;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void getFJXX(String parm)  throws Exception{
		String ins = "insert into F_RESULT (bjtm,KSSJ,GKHM,ZL,ywlx,synctm) values(?,?,?,?,?,'"
		        + DateHelper.getDateTime()
		        + "')";
		int[] len = {30,30,30,20,20};
		try {
			String[] lines = this.getLines(parm);
			parm = null;
			//dao.beginTransaction();
			for (String line : lines) {
				String[] cells = this.getCells(line);
				if(cells.length != 5 ){
				    throw new RuntimeException("字段数不对");
				}
				for(int i = 0; i < cells.length; i++ ){
    				if(cells[0] == null || cells[0].length() == 0 || cells[0].length() > len[i]){
    				    throw new RuntimeException("字段长度不对");
    				}
				}
				//Float f = Float.parseFloat(cells[3]);
				
                this._insert(ins, cells);
                cells =null;
			}
			lines = null;
		} catch(NumberFormatException nfe){
		    log.error(nfe.getMessage());
            throw new RuntimeException("重量数据非有效数字。 ");
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("数据库错误。 ");
		}
	}
	
	
}
