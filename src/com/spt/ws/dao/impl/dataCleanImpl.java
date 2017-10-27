package com.spt.ws.dao.impl;


import java.util.List;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spt.fjj.service.models.common.AnnotationCommonService;
import com.spt.ws.dao.IDataclean;


/**
 * dataclean  数据清理后台定时任务
 *
 */
@Service
public class dataCleanImpl extends AnnotationCommonService implements IDataclean {

	@Autowired

	
	private static final Logger log = LogManager.getLogger("com.spt.ws.dao.impl.dataCleanImpl");

	@Transactional
	@Override
	public void dataclean() throws Exception  {
		//数据清理  删除 24 小时之前的数据
//		表包括 BGFJJ_FFYJXXB BGFJJ_SRGKZTXXB BGFJJ_TRACE BGFJJ_XZSBXXB,
//		1、将 当前表中的数据 复制到 历史表中
//		2、删除当前表 scbz字段  = 1 时间为12小时之前的数据

		
		log.info("##### 111111111 dataclean start");
		
		try {	

			// 获取 当前时间24小时前的日期
    		String closeDate="";
    		String startDate="";
    		String endDate="";
    		String ffstartDate="";
    		String ffendDate="";
    		String srstartDate="";
    		String srendDate="";
    		String xzstartDate="";
    		String xzendDate="";
    		
				
				
				
			closeDate = this._querySingle("select to_char(sysdate - 1,'yyyymmddhh24') from dual");
				
			List<Map<String, Object>> rowlst = this._queryMultex("  select min(T.GJSJ) as startDate,to_char(to_date(min(T.GJSJ),'yyyy-mm-dd hh24:mi:ss')+ 5/(24*60),'yyyy-mm-dd hh24:mi:ss') as endDate from BGFJJ_TRACE t where t.gjsj< ? ",closeDate);
			Map<String, Object> rowMap = rowlst.get(0);
			startDate =  (String)rowMap.get("startDate");
			endDate = (String)rowMap.get("endDate");
			log.info(" 111111111 startDate = "+startDate);
			log.info(" 111111111 endDate = "+endDate);
			
			rowlst = this._queryMultex("  select min(T.lgsj) as ffstartDate,to_char(to_date(min(T.lgsj),'yyyy-mm-dd hh24:mi:ss')+ 5/(24*60),'yyyy-mm-dd hh24:mi:ss') as ffendDate  from bgfjj_ffyjxxb t where t.lgsj< ? ",closeDate);
			rowMap = rowlst.get(0);
			ffstartDate = (String)rowMap.get("ffstartDate");
			ffendDate =  (String)rowMap.get("ffendDate");

			log.info(" 111111111 ffstartDate = "+ffstartDate);
			log.info(" 111111111 ffendDate = "+ffendDate);

				
			rowlst = this._queryMultex("  select min(T.srgkjrsj) as srstartDate,to_char(to_date(min(T.srgkjrsj),'yyyy-mm-dd hh24:mi:ss')+ 5/(24*60),'yyyy-mm-dd hh24:mi:ss') as srendDate  from bgfjj_srgkztxxb t where t.srgkjrsj< ? ",closeDate);
			rowMap = rowlst.get(0);
			srstartDate = (String)rowMap.get("srstartDate");
			srendDate =  (String)rowMap.get("srendDate");

			log.info(" 111111111 srstartDate = "+srstartDate);
			log.info(" 111111111 srendDate = "+srendDate);
			
			
			rowlst = this._queryMultex("   select to_char(to_date(min(replace(sbsj,'/','-')),'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') as  xzstartDate,to_char(to_date(min(replace(sbsj,'/','-')),'yyyy-mm-dd hh24:mi:ss')+ 5/(24*60),'yyyy-mm-dd hh24:mi:ss') as xzendDate   from bgfjj_xzsbxxb t where t.sbsj< ? ",closeDate);
			rowMap = rowlst.get(0);
			xzstartDate = (String)rowMap.get("xzstartDate");
			xzendDate =  (String)rowMap.get("xzendDate");

			log.info(" 111111111 xzstartDate = "+xzstartDate);
			log.info(" 111111111 xzendDate = "+xzendDate);	 
			

    		log.info(" 111111111 @@@@ insert into bgfjj_ffyjxxb_his(BZ, CJDM, GBCZY, GJTH, HEIGHT, ID, JSBZ, LENGTH, LGSJ, LXGK, SBID, SCBZ, SJZL, WIDTH, WLGKHM, XCBH, YJTM, YJZL, YLDM,XCBH1) select BZ, CJDM, GBCZY, GJTH, HEIGHT, ID, JSBZ, LENGTH, LGSJ, LXGK, SBID, SCBZ, SJZL, WIDTH, WLGKHM, XCBH, YJTM, YJZL, YLDM,XCBH1 from bgfjj_ffyjxxb where  lgsj >= "+ffstartDate+" and lgsj <= "+ffendDate);
    		this._update("insert into bgfjj_ffyjxxb_his(BZ, CJDM, GBCZY, GJTH, HEIGHT, ID, JSBZ, LENGTH, LGSJ, LXGK, SBID, SCBZ, SJZL, WIDTH, WLGKHM, XCBH, YJTM, YJZL, YLDM,XCBH1) select BZ, CJDM, GBCZY, GJTH, HEIGHT, ID, JSBZ, LENGTH, LGSJ, LXGK, SBID, SCBZ, SJZL, WIDTH, WLGKHM, XCBH, YJTM, YJZL, YLDM,XCBH1 from bgfjj_ffyjxxb where  lgsj >= ? and lgsj <= ?", ffstartDate,ffendDate);
    		
     		
    		log.info(" 111111111 @@@@ insert into bgfjj_srgkztxxb_his(CJDM, SCBZ, ID, SBID, SRGKJRSJ, SRYY, WLGKH, YJTM) select CJDM, CSBZ, ID, SBID, SRGKJRSJ, SRYY, WLGKH, YJTM from bgfjj_srgkztxxb where  srgkjrsj >= "+srstartDate+" and srgkjrsj <= "+srendDate);
    		this._update("insert into bgfjj_srgkztxxb_his(CJDM, SCBZ, ID, SBID, SRGKJRSJ, SRYY, WLGKH, YJTM) select CJDM, CSBZ, ID, SBID, SRGKJRSJ, SRYY, WLGKH, YJTM from bgfjj_srgkztxxb where  srgkjrsj >= ? and srgkjrsj <= ?", srstartDate,srendDate);
    		

    		log.info(" 111111111 @@@@ insert into bgfjj_trace_his(BZ, CAR, CSBZ, GJFS, GJSJ, GJTHM, HEIGHT, ID, JSBZ, LENGTH, LXGK, SMCZY, SMQH, SMSJ, WEIGHT, WIDTH, WLGKH1, WLGKH2, WLGKH3, WLGKH4, YJTM, YJZL, YLDM, ZT,CAR1) select BZ, CAR, CSBZ, GJFS, GJSJ, GJTHM, HEIGHT, ID, JSBZ, LENGTH, LXGK, SMCZY, SMQH, SMSJ, WEIGHT, WIDTH, WLGKH1, WLGKH2, WLGKH3, WLGKH4, YJTM, YJZL, YLDM, ZT,CAR1 from   bgfjj_trace where  gjsj >= "+startDate+" and gjsj <= "+endDate);
    		this._update("insert into bgfjj_trace_his(BZ, CAR, CSBZ, GJFS, GJSJ, GJTHM, HEIGHT, ID, JSBZ, LENGTH, LXGK, SMCZY, SMQH, SMSJ, WEIGHT, WIDTH, WLGKH1, WLGKH2, WLGKH3, WLGKH4, YJTM, YJZL, YLDM, ZT,CAR1) select BZ, CAR, CSBZ, GJFS, GJSJ, GJTHM, HEIGHT, ID, JSBZ, LENGTH, LXGK, SMCZY, SMQH, SMSJ, WEIGHT, WIDTH, WLGKH1, WLGKH2, WLGKH3, WLGKH4, YJTM, YJZL, YLDM, ZT,CAR1 from   bgfjj_trace where  gjsj >= ? and gjsj <= ?",startDate,endDate);


    		log.info(" 111111111 @@@@ insert into bgfjj_xzsbxxb_his(CARNO, ID, SBSJ, SBYY, WLGKH, YJTM) select CARNO, ID, SBSJ, SBYY, WLGKH, YJTM from bgfjj_xzsbxxb where  to_date(sbsj,'yyyy-mm-dd hh24:mi:ss') >=  to_date("+xzstartDate+",'yyyy-mm-dd hh24:mi:ss') and to_date(sbsj,'yyyy-mm-dd hh24:mi:ss') <= to_date("+xzendDate+",'yyyy-mm-dd hh24:mi:ss')");
    		this._update("insert into bgfjj_xzsbxxb_his(CARNO, ID, SBSJ, SBYY, WLGKH, YJTM) select CARNO, ID, SBSJ, SBYY, WLGKH, YJTM from bgfjj_xzsbxxb where  to_date(sbsj,'yyyy-mm-dd hh24:mi:ss') >= to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(sbsj,'yyyy-mm-dd hh24:mi:ss') <= to_date(?,'yyyy-mm-dd hh24:mi:ss')",xzstartDate,xzendDate);


    		
    		
//			2、删除当前表中数据
    		log.debug(" 111111111 @@@@ delete from bgfjj_ffyjxxb where lgsj >= "+ffstartDate+" and lgsj <= "+ffendDate);
    		this._update("delete from bgfjj_ffyjxxb where  lgsj >= ? and lgsj <= ?",ffstartDate,ffendDate);
    		
    		log.debug(" 111111111 @@@@ delete from bgfjj_srgkztxxb where srgkjrsj >= "+srstartDate+" and srgkjrsj <= "+srendDate);
    		this._update("delete from bgfjj_srgkztxxb where  srgkjrsj >= ? and srgkjrsj <= ?",srstartDate,srendDate);
    		
    		log.debug(" 111111111 @@@@ delete from bgfjj_trace where gjsj >= "+startDate+" and gjsj <= "+endDate);
    		this._update("delete from   bgfjj_trace where  gjsj >= ? and gjsj <= ?",startDate,endDate);
    		
    		log.debug(" 111111111 @@@@ delete from bgfjj_xzsbxxb where  to_date(sbsj,'yyyy-mm-dd hh24:mi:ss') >= "+xzstartDate+" and to_date(sbsj,'yyyy-mm-dd hh24:mi:ss') <= "+xzendDate);
    		this._update("delete from bgfjj_xzsbxxb where  to_date(sbsj,'yyyy-mm-dd hh24:mi:ss') >= to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(sbsj,'yyyy-mm-dd hh24:mi:ss') <= to_date(?,'yyyy-mm-dd hh24:mi:ss')",xzstartDate,xzendDate);

    		
    		
    		
    		log.info("##### 111111111 dataclean end");
	       		

		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			log.info("删除当前表出错 "+e);
		}	
    		
	}

	@Transactional
	@Override
	public void datadelete() throws Exception {
		//运单数据，日志数据删除

//		删除七天前的 每次删除500条  bgfjj_tajfj bgfjj_tyjgk
		
		
		log.info("##### 2222222  datadelete start");
	
		try {	
   		
//			1、删除一定时间前、一定量的历史数据
//			时间和条数从系统参数表中获取
  
			// 查询一次删历史表条数
    		String delenum = "";
    		delenum = this._querySingle(" select T.PARAM_VALUE as delenum from ST_SYS_PARAM t where T.CLASS_ID='DATACLEAN' and T.SUB_CLASS_ID='DELENUM'");
			if (delenum == null ){
				
				
	    		log.info("数据删除-------出错：未设置删历史表条数！");
	    		
	    		return;
			}
			
			//查询 运单数据表保留天数
    		String waybillkeepday = "";
    		waybillkeepday  = this._querySingle(" select T.PARAM_VALUE from ST_SYS_PARAM t where T.CLASS_ID='WAYBILLDATADELE' and T.SUB_CLASS_ID='KEEPDAY'");
			if (delenum == null){
	    		log.info("数据删除-------出错：未设置删运单表条数！");
			}
			
			log.info(" 2222222  @@@@ waybillkeepday = "+waybillkeepday);
			
			String closeDate = this._querySingle("   select to_char(sysdate - ?,'yyyy/mm/dd') as  from dual",waybillkeepday);
			
			String kccloseDate = this._querySingle(" select to_char(sysdate - ?,'yyyymmdd') as kccloseDate from dual",waybillkeepday);


			log.info(" 2222222  @@@@@@@  delete bgfjj_tajfj sql = delete from bgfjj_tajfj where  to_char(to_date(ajsj,'yyyy/fmmm/fmdd hh24:mi:ss'),'yyyy/mm/dd') <    '"+closeDate+"' and rownum<=  "+delenum);
			this._update("delete from bgfjj_tajfj where to_char(to_date(ajsj,'yyyy/fmmm/fmdd hh24:mi:ss'),'yyyy/mm/dd') < ?  and rownum<= ? ",closeDate,delenum);

			log.info(" 2222222  @@@@@@@  delete bgfjj_tyjgk sql = delete from bgfjj_tyjgk where kcsj <    "+kccloseDate+"and rownum<=  "+delenum);
			this._update("delete from bgfjj_tyjgk where kcsj < ?  and rownum<= ? ",kccloseDate,delenum);

			
			log.info("##### 2222222  datadelete end");
			
    		
    		
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info(e);

		}	
    		

	}
	
	@Transactional
	@Override
	public void hisDatadelete() throws Exception {
		//分拣数据删除
//		删除 七天前分拣信息的 历史数据 每次删除 500条
		
		log.info("@@@@@@@ 3333333  datadeletehis start");

		try {	
    		
//			1、删除一定时间前、一定量的历史数据
//			时间和条数从系统参数表中获取
    		// 查询历史表数据保留天数
    		String keepday = "";
    		keepday  = this._querySingle(" select T.PARAM_VALUE as keepday from ST_SYS_PARAM t where T.CLASS_ID='SORTERDATADELE' and T.SUB_CLASS_ID='KEEPDAY'");
			if (keepday == null){
	    		log.debug("分拣数据删除-------出错：未设置历史表数据保留天数！");
	    		return;
			}
	    	// 查询一次删历史表条数
    		String delenum = "";
    		delenum  = this._querySingle(" select T.PARAM_VALUE as delenum from ST_SYS_PARAM t where T.CLASS_ID='DATACLEAN' and T.SUB_CLASS_ID='DELENUM'");
			if (delenum == null){
				
	    		log.info("分拣数据删除-------出错：未设置删历史表条数！");
		    	return;
			}
    		
			
			String closeDate="";
		
			closeDate = this._querySingle("  select to_char(sysdate - ?,'yyyymmdd') as closeDate from dual",keepday);
			
			
			String ffcloseDate = this._querySingle("  select to_char(sysdate - ?,'yyyy/mm/dd') as closeDate from dual",keepday);

			
			String srcloseDate = this._querySingle("  select to_char(sysdate - ?,'yyyy-mm-dd') as closeDate from dual",keepday);
			
	
			
			
			
//			根据条件删除历史数据
			
			log.info("@@@@@@@ 3333333    delete bgfjj_ffyjxxb_his sql = delete from bgfjj_ffyjxxb_his where  lgsj < "+srcloseDate+" and rownum<= "+delenum);
			
			this._update("delete from bgfjj_ffyjxxb_his where  lgsj < ? and rownum<= ?",srcloseDate,delenum);

			log.info("@@@@@@@ 3333333    delete bgfjj_srgkztxxb_his sql = delete from bgfjj_srgkztxxb_his where  srgkjrsj < "+srcloseDate+"and rownum<="+delenum);
			
			this._update("delete from bgfjj_srgkztxxb_his where   srgkjrsj < ? and rownum<= ?",srcloseDate,delenum);

			log.info("@@@@@@@ 3333333    delete bgfjj_trace_his sql = delete from bgfjj_trace_his where   gjsj <  "+srcloseDate+"and rownum<="+delenum);

			this._update("delete from bgfjj_trace_his where   gjsj < ? and rownum<= ?",srcloseDate,delenum);

			log.info("@@@@@@@  3333333   delete bgfjj_xzsbxxb_his sql = delete from bgfjj_xzsbxxb_his where   sbsj < "+srcloseDate+"and rownum<="+delenum);

			this._update("delete from bgfjj_xzsbxxb_his where  sbsj < ? and rownum<= ?",srcloseDate,delenum);

		

			log.info("@@@@@@@ 3333333  datadeletehis end");
			
    		
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("分拣数据删除-------出错："+e);
		}	
    		

	}
	
	
}
