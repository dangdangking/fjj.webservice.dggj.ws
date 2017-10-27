package com.spt.ws.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spt.fjj.service.models.common.AnnotationCommonService;
import com.spt.ws.dao.IImanageDao;
@Service
public class IManageDaoImpl extends AnnotationCommonService implements IImanageDao{

	@Override
	public List<Map<String, Object>> queryStatistic() throws Exception {
		String sql = "select t.tablename,t.transmark,t.valid,t.tabledesc,nvl(t.dealfunction,'no') dealfunction,t.delaytime,t.lastactdt from T_MANAGE_LIST t where valid = '1'";
		List<Map<String,Object>> li = this._queryMultex(sql);
		String sql1 = "select count(*) total,sum(decode(?,'0',1,0)) as todo,sum(decode(?,'1',1,0)) as done, sum(decode(?,'0',0,'1',0,1)) as err from  ";
		String sql2 = "select count(*) HISNUM from ";
		for(Map<String,Object> map : li){
			if("1".equals(map.get("TRANS_FLAG"))){
				String tmpsql = sql1.replace("?", (String)map.get("TRANSMARK"));
				List<Map<String,Object>> tmpli = this._queryMultex(tmpsql + map.get("TABLENAME"));
				map.put("DONE", tmpli.get(0).get("DONE"));
				map.put("TODO", tmpli.get(0).get("TODO"));
				map.put("ERR", tmpli.get(0).get("ERR"));
				map.put("TOTAL", tmpli.get(0).get("TOTAL"));
			}
			if("1".equals(map.get("CLEAN_FLAG"))){
				List<Map<String,Object>> tmpli = this._queryMultex(sql2 +  map.get("TABLENAME") + "_HIS");
				map.put("HISNUM", tmpli.get(0).get("HISNUM"));
			}
		}
		return li;
	}

}
