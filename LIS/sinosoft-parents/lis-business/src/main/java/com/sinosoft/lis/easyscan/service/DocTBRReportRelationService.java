package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 生调单证上载工作流处理接口实现类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author tuqiang
 * @version 1.0
 */

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.easyscan.service.util.PolStateCheck;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.SQLwithBindVariables;

public class DocTBRReportRelationService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBRReportRelationService.class);

	public boolean deal() {
		MMap map = new MMap();
		LWMissionDB mLWMissionDB = new LWMissionDB();
		LWMissionSet mLWMissionSet = new LWMissionSet();
		/***********工作流升级修改为查询functionid**************/
		String MissionSQL="select * from lwmission where missionprop3='"+"?mES_DOC_MAINSchema.getDocCode()?"+"' and activityid in(select activityid from lwactivity where functionid in('10010031'))";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(MissionSQL);
		sqlbv1.put("missionprop3",mES_DOC_MAINSchema.getDocCode() );
		mLWMissionSet = mLWMissionDB.executeQuery(sqlbv1);

		if (mLWMissionSet == null || mLWMissionSet.size() != 1) {
			MissionSQL="select * from lwmission where missionprop3='"+"?missionprop3?"+"' and activityid in(select activityid from lwactivity where functionid in('10010035'))";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(MissionSQL);
			sqlbv2.put("missionprop3",mES_DOC_MAINSchema.getDocCode() );
			mLWMissionSet = mLWMissionDB.executeQuery(sqlbv2);
			if(mLWMissionSet.size()>0){
				CError.buildErr(this, "此生调尚未回复！");
				return false;
			}
			CError.buildErr(this, "无发放此生调通知书!");
			return false;
		}
		
		if(!PolStateCheck.isPolSameManage(mLWMissionSet.get(1).getMissionProp2(), mES_DOC_MAINSchema.getManageCom())){
			CError.buildErr(this, "扫描机构与投保单扫描机构不一致，请核实 ");
			return false;
		}
		
		ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(mES_DOC_MAINSchema.getDocCode());
		mES_DOC_RELATIONSchema.setDocCode(mES_DOC_MAINSchema.getDocCode());
		mES_DOC_RELATIONSchema.setBussNoType("15");// guomy modified
		// 20050809
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		if (mES_DOC_RELATIONSchema != null) {
			map.put(mES_DOC_RELATIONSchema, "INSERT");
		}
		mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema
				.setBussNo(mLWMissionSet.get(1).getMissionProp2());
		mES_DOC_RELATIONSchema.setDocCode(mLWMissionSet.get(1).getMissionProp2());
		mES_DOC_RELATIONSchema.setBussNoType("11");// hl 20050928
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		if (mES_DOC_RELATIONSchema != null) {
			map.put(mES_DOC_RELATIONSchema, "INSERT");
		}
		if (map != null) {
			// 返回数据map
			mResult.add(map);
		} else {
			CError.buildErr(this, "数据生成出错!");
			return false;
		}
		return true;

	}

}
