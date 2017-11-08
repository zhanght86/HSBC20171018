package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowUI;

public class DocBQHBWTJServer extends BaseService {
private static Logger logger = Logger.getLogger(DocBQHBWTJServer.class);

	protected boolean deal() {
		logger.debug("====DocBQBankService start====");
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
		/*
		if (tLOPRTManagerDB.getStandbyFlag1() == null
				|| tLOPRTManagerDB.getStandbyFlag1().equals("")) {
			throw new IllegalArgumentException("缺少保全受理号");
		}*/

		//tongmeng 2009-04-02 modify
		//"27"; // 保全补资料通知书
		//24 保全生调通知书
		//23 保全体检通知书
		//25 保全核保通知书
		/*
		 23	2000
		 24	2010
	     25	2011
	     27	2012
		 */
		
		String tActivityid = "";
		String tCertifyCode = "";
		String tCode = tLOPRTManagerDB.getCode();
		if(tCode.equals("23"))
		{
			tActivityid = "0000000303";
			tCertifyCode = "2000";
		}
		else if(tCode.equals("24"))
		{
			tActivityid = "0000000314";
			tCertifyCode = "2010";
		}
		else if(tCode.equals("25"))
		{
			tActivityid = "0000000322";
			tCertifyCode = "2011";
		}
		else if(tCode.equals("27"))
		{
			tActivityid = "0000000353";
			tCertifyCode = "2012";
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
			if(tCode.equals("24")){
				mLWMissionDB.setActivityID("0000000316");
				if(mLWMissionDB.getCount()>0){
					CError.buildErr(this, "此生调尚未回复！");
					return false;
				}
			}
			CError.buildErr(this, "无发放此单证!");
			return false;
		}

		// 获取保全批单号
		String mEdorNo = "";
		String tContNo = tLOPRTManagerDB.getOtherNo();
		String tSQL = "select edoracceptno from lpedorapp where otherno='"+"?tContNo?"+"'  and edorstate<>'0' and othernotype='3'";
		SQLwithBindVariables sqlbv1  = new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("tContNo",tContNo );
		ExeSQL tExeSQL = new ExeSQL();
		mEdorNo  = tExeSQL.getOneValue(sqlbv1);
		if(mEdorNo==null||mEdorNo.equals(""))
		{
			CError.buildErr(this, "获取保全受理号失败!");
			return false;
		}

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
		mES_DOC_RELATIONSchema.setBussNo(mEdorNo);
		mES_DOC_RELATIONSchema.setDocCode(mEdorNo);
		mES_DOC_RELATIONSchema.setBussNoType("31");
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
		String[] validnos = new String[] { "23", "24", "25", "27" };
		for (int i = 0; i < validnos.length; i++) {
			if (validnos[i].equals(code))
				return true;
		}
		return false;
	}

}
