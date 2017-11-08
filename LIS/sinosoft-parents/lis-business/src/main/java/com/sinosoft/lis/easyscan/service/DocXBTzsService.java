package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowUI;

public class DocXBTzsService extends BaseService {
private static Logger logger = Logger.getLogger(DocXBTzsService.class);

	protected boolean deal() {
		logger.debug("====DocXBTzsService start====");
		MMap mMap = new MMap();

		// 扫描回销
		String tPrtSeq = mES_DOC_MAINSchema.getDocCode();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(tPrtSeq);
		if (!tLOPRTManagerDB.getInfo()) {
			throw new IllegalArgumentException("查不到通知书！");
		}

		if (!"1".equals(tLOPRTManagerDB.getStateFlag())) {
			throw new IllegalArgumentException("通知书状态错误:"
					+ tLOPRTManagerDB.getStateFlag());
		}

		if (!checkWTJNO(tLOPRTManagerDB.getCode())) {
			throw new IllegalArgumentException("通知书类型错误:"
					+ tLOPRTManagerDB.getCode());
		}
		
		String tActivityid = "";
		String tCertifyCode = "";
		String tCode = tLOPRTManagerDB.getCode();
		if(tCode.equals("43"))
		{
			tActivityid = "0000007009";
			tCertifyCode = "7009";
		}
		else if(tCode.equals("44"))
		{
			tActivityid = "0000007012";
			tCertifyCode = "7012";
		}
		else if(tCode.equals("45"))
		{
			tActivityid = "0000007006";
			tCertifyCode = "7006";
		}
			
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		GlobalInput tGlobalInput = new GlobalInput();
	
		// 通过前台得到最后操作数据
		tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
		tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
		tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();

		// 对输入数据进行验证
		LWMissionDB mLWMissionDB = new LWMissionDB();
		LWMissionSet mLWMissionSet = new LWMissionSet();
		mLWMissionDB.setActivityID(tActivityid);
		mLWMissionDB.setMissionProp3(tPrtSeq); // 流水号
		mLWMissionSet = mLWMissionDB.query();
		if (mLWMissionSet == null || mLWMissionSet.size() != 1) {
			CError.buildErr(this, "无发放此单证!");
			return false;
		}

		String tContNo = tLOPRTManagerDB.getOtherNo();
		// 准备传输数据 VData
		tTransferData.setNameAndValue("CertifyNo",mES_DOC_MAINSchema.getDocCode());
		tTransferData.setNameAndValue("CertifyCode",tCertifyCode) ;
		tTransferData.setNameAndValue("ContNo",tContNo) ;
		tTransferData.setNameAndValue("GrpContNo",tContNo) ;
		tTransferData.setNameAndValue("MissionID",mLWMissionSet.get(1).getMissionID());
		tTransferData.setNameAndValue("SubMissionID",mLWMissionSet.get(1).getSubMissionID());
		tTransferData.setNameAndValue("TakeBackOperator",mES_DOC_MAINSchema.getScanOperator());
		tTransferData.setNameAndValue("TakeBackMakeDate",mES_DOC_MAINSchema.getMakeDate());
		tTransferData.setNameAndValue("flag", "N");
		
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
			
		ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(tPrtSeq);
		mES_DOC_RELATIONSchema.setDocCode(tPrtSeq);
		mES_DOC_RELATIONSchema.setBussNoType("35");
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		mMap.put(mES_DOC_RELATIONSchema.getSchema(), "DELETE&INSERT");

		mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(tContNo);
		mES_DOC_RELATIONSchema.setDocCode(tContNo);
		mES_DOC_RELATIONSchema.setBussNoType("13");
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		mMap.put(mES_DOC_RELATIONSchema.getSchema(), "DELETE&INSERT");
		

		try {
			TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
			if (!tTbWorkFlowUI.submitData(tVData, tActivityid)) {
				// @@错误处理
				CError.buildErr(this, "回收通知书失败!", tTbWorkFlowUI.mErrors);
				return false;
			}
			mResult = tTbWorkFlowUI.getResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError.buildErr(this, ex.toString());
			return false;
		}
		MMap m=(MMap)mResult.getObjectByObjectName("MMap", 0);
		m.add(mMap);
		return true;
	}

	private boolean checkWTJNO(String code) {
		String[] validnos = new String[] { "43", "44", "45"};
		for (int i = 0; i < validnos.length; i++) {
			if (validnos[i].equals(code))
				return true;
		}
		return false;
	}

}
