package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.certify.CertSendOutBL;
import com.sinosoft.lis.certify.PubCertifyTakeBack;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPEdorPrint2DB;
import com.sinosoft.lis.db.LPEdorPrintDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LPEdorPrintSet;
import com.sinosoft.lis.vschema.LZCardPrintSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 打印管理表更新类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author liurx
 * @creatdate:2005-09-20
 * @version 1.0
 */
public class PrtUpdateBL {
private static Logger logger = Logger.getLogger(PrtUpdateBL.class);

	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mOperate;

	private String mOtherNo;
	private String mCode;
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	private static final String VALID_AGENT_STATE = " IN ('01', '02') "; // 可用的代理人的状态
	private boolean SendOutFlag = true;

	public PrtUpdateBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}
		if (cOperate == null || cOperate.trim().equals("")) {
			cOperate = "NOTICE";
		}
		if (cOperate.trim().equals("NOTICE")) {
			// 通知书、收据
			if (!submitNotice()) {
				return false;
			}
		} else if (cOperate.trim().equals("EDORNO")) {
			// 批单,传入批单号
			if (!submitEdor()) {
				return false;
			}
		} else if (cOperate.trim().equals("EDORACCEPTNO")) {
			// 批单，传入保全受理号
			if (!submitAccept()) {
				return false;
			}
		} else if (cOperate.trim().equals("BQCHECK")) {
			// 批单，传入保全受理号
			if (!submitBQCheck()) {
				return false;
			}
		} else if (cOperate.trim().equals("GRPEDORNO")) {
			// 团体人名清单,传入批单号
			if (!submitGrpNameList()) {
				return false;
			}
		} else {
			mErrors.addOneError(new CError("不支持的操作字符串！"));
			return false;
		}
		return true;
	}

	private boolean submitNotice() {
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		VData tVData = new VData();
		String tPrtseq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		MMap aMap = new MMap();

		if (mCode != null && mCode.equals("BF00")) {
			LOPRTManagerSchema aLOPRTManagerSchema = new LOPRTManagerSchema();

			aLOPRTManagerSchema.setPrtSeq(tPrtseq);
			aLOPRTManagerSchema.setOtherNo(mOtherNo);
			aLOPRTManagerSchema.setOtherNoType("02");
			aLOPRTManagerSchema.setCode("BF00");
			aLOPRTManagerSchema.setReqCom("00000000");
			aLOPRTManagerSchema.setReqOperator("00000000");
			aLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom);
			aLOPRTManagerSchema.setPrtType("0");
			aLOPRTManagerSchema.setStateFlag("1");

			aLOPRTManagerSchema.setDoneDate(mCurrentDate);
			aLOPRTManagerSchema.setDoneTime(mCurrentTime);
			aMap.put(aLOPRTManagerSchema, "INSERT");
			tVData.add(aMap);

		} else {
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(mOtherNo);
			if (!tLOPRTManagerDB.getInfo()) {
				mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
				return false;
			}
			LOPRTManagerSchema tLOPRTManagerSchema = tLOPRTManagerDB
					.getSchema();
			tLOPRTManagerSchema.setStateFlag("1");
			tLOPRTManagerSchema.setDoneDate(mCurrentDate);
			tLOPRTManagerSchema.setDoneTime(mCurrentTime);

			MMap tMap = new MMap();

			tMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
			tVData.add(tMap);
		}

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			mErrors.addOneError(new CError("在更新数据时发生错误"));
			return false;
		}
		return true;
	}

	private boolean submitEdor() {
		LPEdorPrintDB tLPEdorPrintDB = new LPEdorPrintDB();
		tLPEdorPrintDB.setEdorNo(mOtherNo);
		if (!tLPEdorPrintDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorPrintDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		String strsql = "update lpedorprint set prttimes = prttimes+1,prtflag = 'Y',modifydate='"
				+ "?mCurrentDate?"
				+ "',modifytime='"
				+ "?mCurrentTime?"
				+ "',operator='"
				+ "?mGlobalInput?"
				+ "' where edorno = '"
				+ "?mOtherNo?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strsql);
		sqlbv1.put("mCurrentDate", mCurrentDate);
		sqlbv1.put("mCurrentTime", mCurrentTime);
		sqlbv1.put("mGlobalInput", mGlobalInput.Operator);
		sqlbv1.put("mOtherNo", mOtherNo);
		PubSubmit tPubSubmit = new PubSubmit();
		MMap tMap = new MMap();
		VData tVData = new VData();
		tMap.put(sqlbv1, "UPDATE");
		tVData.add(tMap);
		if (!tPubSubmit.submitData(tVData, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			mErrors.addOneError(new CError("在更新数据时发生错误"));
			return false;
		}
		return true;
	}

	private boolean submitGrpNameList() {
		LPEdorPrint2DB tLPEdorPrint2DB = new LPEdorPrint2DB();
		tLPEdorPrint2DB.setEdorNo(mOtherNo);
		if (!tLPEdorPrint2DB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorPrint2DB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		String strsql = "update lpedorprint2 set prttimes = prttimes+1,prtflag = 'Y',modifydate='"
				+ "?mCurrentDate?"
				+ "',modifytime='"
				+ "?mCurrentTime?"
				+ "',operator='"
				+ "?mGlobalInput?"
				+ "' where edorno = '"
				+ "?mOtherNo?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strsql);
		sqlbv2.put("mCurrentDate", mCurrentDate);
		sqlbv2.put("mCurrentTime", mCurrentTime);
		sqlbv2.put("mGlobalInput", mGlobalInput.Operator);
		sqlbv2.put("mOtherNo", mOtherNo);
		PubSubmit tPubSubmit = new PubSubmit();
		MMap tMap = new MMap();
		VData tVData = new VData();
		tMap.put(sqlbv2, "UPDATE");
		tVData.add(tMap);
		if (!tPubSubmit.submitData(tVData, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			mErrors.addOneError(new CError("在更新数据时发生错误"));
			return false;
		}
		return true;
	}

	private boolean submitAccept() {
		String strsql = "select * from lpedorprint where edorno in (select edorno from lpedormain where edoracceptno = '"
				+ "?mOtherNo?" + "')";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strsql);
		sqlbv3.put("mOtherNo", mOtherNo);
		LPEdorPrintDB tLPEdorPrintDB = new LPEdorPrintDB();
		LPEdorPrintSet tLPEdorPrintSet = new LPEdorPrintSet();
		tLPEdorPrintSet = tLPEdorPrintDB.executeQuery(sqlbv3);
		if (tLPEdorPrintDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorPrintDB.mErrors);
			mErrors.addOneError(new CError("查询批单打印表数据失败"));
			return false;
		}
		if (tLPEdorPrintSet == null || tLPEdorPrintSet.size() <= 0) {
			// XinYQ added on 2007-05-31 : 如果个单没有, 可能为团体批单
			strsql = "select * from LPEdorPrint where EdorNo in (select EdorNo from LPGrpEdorMain where EdorAcceptNo = '"
					+ "?mOtherNo?" + "')";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(strsql);
			sqlbv4.put("mOtherNo", mOtherNo);
			tLPEdorPrintSet = tLPEdorPrintDB.executeQuery(sqlbv4);
			if (tLPEdorPrintSet == null || tLPEdorPrintSet.size() <= 0) {
				CError.buildErr(this, "无批单打印表数据！");
				return false;
			}
		}
		strsql = "update lpedorprint set prttimes = prttimes+1,prtflag = 'Y',modifydate='"
				+ "?mCurrentDate?"
				+ "',modifytime='"
				+ "?mCurrentTime?"
				+ "',operator='"
				+ "?mGlobalInput?"
				+ "' where edorno in (select edorno from lpedormain where edoracceptno = '"
				+ "?mOtherNo?" + "')";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(strsql);
		sqlbv5.put("mCurrentDate", mCurrentDate);
		sqlbv5.put("mCurrentTime", mCurrentTime);
		sqlbv5.put("mGlobalInput", mGlobalInput.Operator);
		sqlbv5.put("mOtherNo", mOtherNo);
		PubSubmit tPubSubmit = new PubSubmit();
		MMap tMap = new MMap();
		VData tVData = new VData();
		tMap.put(sqlbv5, "UPDATE");
		tVData.add(tMap);
		if (!tPubSubmit.submitData(tVData, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			mErrors.addOneError(new CError("在更新数据时发生错误"));
			return false;
		}
		return true;
	}

	/**
	 * 保全发票打印处理系统
	 * 
	 * @todo One 发票的发放
	 * @todo Twe 发票的回收
	 * @todo Three 更新打印管理表的打印状态
	 * @CreateDate 2005-11-29
	 * @author Lizhuo
	 * @return boolean
	 */
	private boolean submitBQCheck() {
		// 发票的发放
		if (!SendOutBQCheck()) {
			return false;
		}
		// 发票的回收
		if (SendOutFlag == true) {
			if (!ReceiveBQCheck()) {
				return false;
			}
		} else {
			if (!submitNotice()) { // 通知书、收据
				return false;
			}
		}
		return true;
	}

	/**
	 * 保全发票打印处理系统
	 * 
	 * @todo 发票的发放
	 * @return boolean
	 */
	private boolean SendOutBQCheck() {
		logger.debug("开始进行保全发票的发放:::::::::::::");
		LZCardSet tLZCardSet = new LZCardSet();
		LZCardSchema tLZCardSchema = new LZCardSchema();
		LZCardPrintSet tLZCardPrintSet = new LZCardPrintSet();
		try {
			String cSendOutCom = (String) mTransferData
					.getValueByName("SendOutCom");
			String cReceiveCom = (String) mTransferData
					.getValueByName("ReceiveCom");
			String cCertifyCode = (String) mTransferData
					.getValueByName("CertifyCode");
			String cStartNo = (String) mTransferData.getValueByName("StartNo");
			String cEndNo = (String) mTransferData.getValueByName("EndNo");
			String cHandler = (String) mTransferData.getValueByName("Handler");
			if (cSendOutCom == null || cReceiveCom == null
					|| cCertifyCode == null || cStartNo == null
					|| cEndNo == null || cHandler == null) {
				CError.buildErr(this, "传入数据不完整，不能进行发票发放与回收!");
				return false;
			}
			if (!checkSendOutCom(cReceiveCom)) {
				SendOutFlag = false;
				logger.debug("============= 保单代理人无效，代理人号："
						+ cReceiveCom.substring(1));
				return true;
			}
			tLZCardSchema.setSendOutCom(cSendOutCom);
			tLZCardSchema.setReceiveCom(cReceiveCom);
			tLZCardSchema.setCertifyCode(cCertifyCode);
			tLZCardSchema.setStartNo(cStartNo);
			tLZCardSchema.setEndNo(cEndNo);
			tLZCardSchema.setHandler(cHandler);
			tLZCardSchema.setHandleDate(PubFun.getCurrentDate());
			tLZCardSchema.setInvaliDate(PubFun.getCurrentDate());
			tLZCardSchema.setOperator(mGlobalInput.Operator);
			tLZCardSet.add(tLZCardSchema);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接受发放数据异常!");
			return false;
		}
		VData cVData = new VData();
		cVData.add(mGlobalInput);
		cVData.add(tLZCardSet);
		cVData.add(tLZCardPrintSet);
		CertSendOutBL tCertSendOutBL = new CertSendOutBL();
		if (!tCertSendOutBL.submitData(cVData, "INSERT")) {
			logger.debug("保全发票的发放错误:::::::::::::");
			mErrors.copyAllErrors(tCertSendOutBL.mErrors);
			return false;
		}
		logger.debug("保全发票的发放成功:::::::::::::");
		return true;
	}

	/**
	 * 保全发票打印处理系统
	 * 
	 * @todo 发票的回收与打印管理表的更新
	 * @return boolean
	 */
	private boolean ReceiveBQCheck() {
		logger.debug("开始进行保全发票的回收:::::::::::::");
		PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
		String cCertifyCode = (String) mTransferData
				.getValueByName("CertifyCode");
		String cStartNo = (String) mTransferData.getValueByName("StartNo");
		String cEndNo = (String) mTransferData.getValueByName("EndNo");
		if (!tPubCertifyTakeBack.CertifyTakeBack_A(cStartNo, cEndNo,
				cCertifyCode, mGlobalInput)) {
			logger.debug("保全发票的回收错误:::::::::::::");
			mErrors.copyAllErrors(tPubCertifyTakeBack.mErrors);
			return false;
		}
		MMap tMap = new MMap();
		tMap = (MMap) tPubCertifyTakeBack.getResult().getObjectByObjectName(
				"MMap", 0);

		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mOtherNo);
		if (!tLOPRTManagerDB.getInfo()) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		LOPRTManagerSchema tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		tLOPRTManagerSchema.setStateFlag("1");
		tLOPRTManagerSchema.setDoneDate(mCurrentDate);
		tLOPRTManagerSchema.setDoneTime(mCurrentTime);

		PubSubmit tPubSubmit = new PubSubmit();
		VData tVData = new VData();
		tMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
		tVData.add(tMap);
		if (!tPubSubmit.submitData(tVData, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			mErrors.addOneError(new CError("在更新数据时发生错误"));
			return false;
		}

		logger.debug("保全发票的回收成功:::::::::::::");
		return true;
	}

	private boolean checkSendOutCom(String aAgentCode) {
		String szSql = "SELECT * FROM LAAgent" + " WHERE AgentCode = '"
				+ "?AgentCode?" + "'" + " AND AgentState "
				+ VALID_AGENT_STATE;
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(szSql);
		sqlbv6.put("AgentCode", aAgentCode.substring(1));
		if (new LAAgentDB().executeQuery(sqlbv6).size() != 1) {
			return false;
		}
		szSql = "SELECT * FROM LAAgent" + " WHERE AgentCode = '"
				+ "?AgentCode?" + "'"
				+ " and (salequaf='N' OR salequaf is null)";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(szSql);
		sqlbv7.put("AgentCode", aAgentCode.substring(1));
		if (new LAAgentDB().executeQuery(sqlbv7).size() >= 1) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mOtherNo = (String) mTransferData.getValueByName("OtherNo");
		mCode = (String) mTransferData.getValueByName("Code");
		logger.debug("mGlobalInput=" + mGlobalInput);
		logger.debug("mOtherNo=" + mOtherNo);
		if (mGlobalInput == null || mOtherNo == null
				|| mOtherNo.trim().equals("")) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		return true;
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate(String cOperate) {
		return this.mOperate;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		logger.debug("test start:");
		TransferData tTransferData = new TransferData();
		GlobalInput tGI = new GlobalInput();

		tGI.Operator = "001";
		tGI.ComCode = "86";
		tGI.ManageCom = "86";

		tTransferData.setNameAndValue("OtherNo", "80000000217388");
		tTransferData.setNameAndValue("Code", "BF00");
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGI);
		PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
		if (!tPrtUpdateBL.submitData(tVData, "NOTICE")) {
			logger.debug("test failed:"
					+ tPrtUpdateBL.mErrors.getFirstError().toString());
		}
		// SSRS tssrs = new SSRS();
		// ExeSQL texesql = new ExeSQL();
		// String strsql =
		// "update lpedorprint set prttimes = prttimes+1 where edorno =
		// '6020050906000091'";
		// PubSubmit tPubSubmit = new PubSubmit();
		// MMap tMap = new MMap();
		// //VData tVData = new VData();
		// tMap.put(strsql, "UPDATE");
		// tVData.add(tMap);
		// if (!tPubSubmit.submitData(tVData, "")) {
		// logger.debug("test failed");
		// }
		logger.debug("test end");
	}
}
