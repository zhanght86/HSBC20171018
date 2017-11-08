package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证打印：批单[团体]-理赔给付批注 -- PCT002,PdLpjfpzTtC00020.vts
 * </p>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author zhaocx 20061206
 * @version 1.0
 */
public class LLPRTPostilGrpBL {
private static Logger logger = Logger.getLogger(LLPRTPostilGrpBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();

	private String mGrpClmNo = ""; // 团体赔案号

	public LLPRTPostilGrpBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------批单[团体]-理赔给付批注-----LLPRTPostilGrpBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		logger.debug("----------批单[团体]-理赔给付批注-----LLPRTPostilGrpBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mGrpClmNo = (String) mTransferData.getValueByName("GrpClmNo");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// ---------------- 查询 保险合同号 -----------------
		String tGrpContNo = "";
		String mGrpContNoSQL = "select RGTOBJNO from llgrpregister where rgtno='"
				+ mGrpClmNo + "'";
		ExeSQL ContNOExeSQL = new ExeSQL();
		tGrpContNo = ContNOExeSQL.getOneValue(mGrpContNoSQL);
		// ---------------- 查询 投保单位名称 -----------------
		String mComNameSQL = " select grpname from ldgrp where customerno in "
				+ " (select customerno from lcgrpappnt where grpcontno='"
				+ tGrpContNo + "') ";
		ExeSQL ComNameExeSQL = new ExeSQL();
		String tAppntName = ComNameExeSQL.getOneValue(mComNameSQL);
		// ---------------- 查询 给付项目 -----------------
		String tSQLJ = " select d.codename from ldcode d where d.codetype = 'llclaimtype' "
				+ " and d.code in (select a.reasoncode from llappclaimreason a "
				+ " where a.rgtno in (select r.rgtno from llregister r "
				+ " where r.rgtobjno='"
				+ mGrpClmNo
				+ "') group by a.reasoncode) ";
		ExeSQL jExeSQL = new ExeSQL();
		SSRS jSSRS = new SSRS();
		jSSRS = jExeSQL.execSQL(tSQLJ);
		if (jSSRS.getMaxRow() == 0) {
			CError tError = new CError();
			tError.moduleName = "LLPRTMedBillGrpBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有查到赔案理赔类型信息！";
			mErrors.addOneError(tError);
			return false;
		}
		String tClaimType = "";
		String tMClaimType = "";
		for (int j = 1; j <= jSSRS.getMaxRow(); j++) {
			tMClaimType = jSSRS.GetText(j, 1);
			tClaimType = tClaimType + tMClaimType;
		}
		// ---------------- 查询 给付保险总总额 -----------------
		String tRealPaySQL = " select nvl(sum(realpay),0) from llclaim where  clmno in "
				+ " (select rgtno from llregister where rgtobjno= '"
				+ mGrpClmNo + "') ";
		ExeSQL tExeSQL = new ExeSQL();
		String tRealPaySum = tExeSQL.getOneValue(tRealPaySQL);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入单个字段的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("GrpContNo", tGrpContNo); // 保险合同号
		tTextTag.add("GrpClmNo", mGrpClmNo); // 团体赔案号
		tTextTag.add("ComName", tAppntName); // 投保单位名称
		tTextTag.add("PayItem", tClaimType); // 给付项目
		tTextTag.add("PayFeeamount", tRealPaySum); // 给付保险金总额

		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("PdLpjfpzTtC00020.vts", "");
		tXmlExport.createDocument("批单-理赔给付批注[团体]");
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpClmNo", "70000000228");
		VData tVData = new VData();
		tVData.add(tTransferData);
		LLPRTPostilGrpBL tLLPRTPostilGrpBL = new LLPRTPostilGrpBL();
		if (!tLLPRTPostilGrpBL.submitData(tVData, "")) {
			String str = "提交失败，原因是: "
					+ tLLPRTPostilGrpBL.mErrors.getError(0).errorMessage;
			logger.debug(str);
		}
	}
}
