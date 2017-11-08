package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 投保单上传后对于关联表的的操作</p>
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

public class DocTBYWYRelationService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBYWYRelationService.class);

	public boolean deal(){
		try {
			MMap map = new MMap();

			LWMissionDB mLWMissionDB = new LWMissionDB();
			LWMissionSet mLWMissionSet = new LWMissionSet();
			/***********工作流升级修改为查询functionid**************/
			String MissionSQL="select * from lwmission where missionprop3='"+"?missionprop3?"+"' and activityid in(select activityid from lwactivity where functionid in('10010009'))";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(MissionSQL);
			sqlbv1.put("missionprop3", mES_DOC_MAINSchema.getDocCode());
			mLWMissionSet = mLWMissionDB.executeQuery(sqlbv1);

			if (mLWMissionSet == null || mLWMissionSet.size() != 1) {
				CError.buildErr(this, "无发放此新契约变更通知书!");
				return false;
			}
			
			if(!PolStateCheck.isPolSameManage(mLWMissionSet.get(1).getMissionProp2(), mES_DOC_MAINSchema.getManageCom())){
				CError.buildErr(this, "扫描机构与投保单扫描机构不一致，请核实 ");
				return false;
			}
			
			ES_DOC_RELATIONSchema tES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
			tES_DOC_RELATIONSchema.setBussNo(mES_DOC_MAINSchema
					.getDocCode());
			tES_DOC_RELATIONSchema.setDocCode(mES_DOC_MAINSchema
					.getDocCode());
			tES_DOC_RELATIONSchema.setBussNoType("15");
			tES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
			tES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema
					.getBussType());
			tES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema
					.getSubType());
			tES_DOC_RELATIONSchema.setRelaFlag("0");
			if (tES_DOC_RELATIONSchema != null) {
				map.put(tES_DOC_RELATIONSchema, "INSERT");
			}
			tES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
			tES_DOC_RELATIONSchema.setBussNo(mLWMissionSet.get(1)
					.getMissionProp2());
			tES_DOC_RELATIONSchema.setDocCode(mLWMissionSet.get(1)
					.getMissionProp2());
			tES_DOC_RELATIONSchema.setBussNoType("11"); // by niuzj 20050809
			tES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
			tES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema
					.getBussType());
			tES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema
					.getSubType());
			tES_DOC_RELATIONSchema.setRelaFlag("0");
			if (tES_DOC_RELATIONSchema != null) {
				map.put(tES_DOC_RELATIONSchema, "INSERT");
			}
			if (map != null) {
				// 返回数据map
				mResult.add(map);
			} else {
				CError.buildErr(this, "数据生成出错!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

}
