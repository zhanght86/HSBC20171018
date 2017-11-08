package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.WorkFlowUI;
/**
 * <p>Title: </p>
 * <p>Description: 新契约变更通知书上载工作流处理接口实现类</p>
 * 业务类型号码为11
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author tuqiang
 * @version 1.0
 */

public class DocTBIssueWorkFlowService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBIssueWorkFlowService.class);

	public boolean deal(){
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		GlobalInput tGlobalInput = new GlobalInput();
		/** 全局变量 */
		// tGlobalInput.Operator = "001";
		// tGlobalInput.ComCode = "86";
		// tGlobalInput.ManageCom = "86";
		/** 得到具体信息* */

		// 通过前台得到最后操作数据
		tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
		tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
		tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();

		// 对输入数据进行验证
		LWMissionDB mLWMissionDB = new LWMissionDB();
		LWMissionSet mLWMissionSet = new LWMissionSet();
		/***********工作流升级修改为查询functionid**************/
		String MissionSQL="select * from lwmission where missionprop3='"+"?missionprop3?"+"' AND MISSIONPROP15='TB89' and activityid in(select activityid from lwactivity where functionid in('10010030'))";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(MissionSQL);
		sqlbv1.put("missionprop3", mES_DOC_MAINSchema.getDocCode());
		mLWMissionSet = mLWMissionDB.executeQuery(sqlbv1);
		if (mLWMissionSet == null || mLWMissionSet.size() != 1) {
			CError.buildErr(this, "无发放此扫描单!");
			return false;
		}

		// 获取合同信息
		String mContNo = new String();
		mContNo = mLWMissionSet.get(1).getMissionProp2();

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "合同[" + mContNo + "]信息查询失败!");
			return false;
		}

		LCContSchema mLCContSchema = new LCContSchema();
		mLCContSchema.setSchema(tLCContDB);

		// 准备传输数据 VData
		String tOperate = new String();

		tTransferData.setNameAndValue("CertifyNo", mES_DOC_MAINSchema
				.getDocCode());
		// tTransferData.setNameAndValue("CertifyCode",
		// PrintManagerBL.CERT_CODE_PE);
		tTransferData.setNameAndValue("CertifyCode", "9999"); // by niuzj
																// 20050809
		tTransferData.setNameAndValue("ContNo", mLWMissionSet.get(1)
				.getMissionProp2());
		tTransferData.setNameAndValue("MissionID", mLWMissionSet.get(1)
				.getMissionID());
		tTransferData.setNameAndValue("SubMissionID", mLWMissionSet.get(1)
				.getSubMissionID());
		// tTransferData.setNameAndValue("LZSysCertifySchema",
		// tLZSysCertifySchema);
		tTransferData.setNameAndValue("flag", "N");// add by tuqiang for
													// tranction!!
		tTransferData.setNameAndValue("PrtNo", mLWMissionSet.get(1)
				.getMissionProp1());// add by sunxy 20050619
		tTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo());
		tTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		tTransferData.setNameAndValue("TakeBackOperator", mES_DOC_MAINSchema
				.getScanOperator());// add by liukun
		tTransferData.setNameAndValue("TakeBackMakeDate", mES_DOC_MAINSchema
				.getMakeDate());// add by liukun
		tTransferData.setNameAndValue("ActivityID", mLWMissionSet.get(1).getActivityID());
		 tTransferData.setNameAndValue("BusiType", "1001");

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		try {
			WorkFlowUI tWorkFlowUI = new WorkFlowUI();
			if (!tWorkFlowUI.submitData(tVData, "execute")) {
				// @@错误处理
				CError.buildErr(this, "扫描录入工作流节点生成失败!", tWorkFlowUI.mErrors);
				return false;
			}
			mResult = tWorkFlowUI.getResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError.buildErr(this, ex.toString());
			return false;
		}
		logger.debug("----------------------DocTBIssueWorkFlowService End----------------------");
		return true;
	}
}
