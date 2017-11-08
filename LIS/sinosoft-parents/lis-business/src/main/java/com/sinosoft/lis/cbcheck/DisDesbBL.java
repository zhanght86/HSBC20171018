package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDiseaseResultDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LCPENoticeItemDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDiseaseResultSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.vschema.LCDiseaseResultSet;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPENoticeResultSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ modify by zhangxing 为了解决体检合并问题
 * @version 1.0
 */

public class DisDesbBL {
private static Logger logger = Logger.getLogger(DisDesbBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData mInputData;
	private GlobalInput tGI = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	private String mManageCom;

	/** 业务操作类 */
	private LCPENoticeResultSet mLCPENoticeResultSet = new LCPENoticeResultSet();
	private LCDiseaseResultSet mLCDiseaseResultSet = new LCDiseaseResultSet();
	private LCPENoticeItemSet mLCPENoticeItemSet = new LCPENoticeItemSet();
	private LCPENoticeItemSet mLCPENoticeItemSet1 = new LCPENoticeItemSet();
	private LCPENoticeItemSet mLCPENoticeItemSet2 = new LCPENoticeItemSet();
	private LCPENoticeItemSet mLCPENoticeItemSet3 = new LCPENoticeItemSet();
	private LCPENoticeSet mLCPENoticeSet = new LCPENoticeSet();
	private LCPENoticeSet mLCPENoticeSet1 = new LCPENoticeSet();
	private LCPENoticeSet mLCPENoticeSet2 = new LCPENoticeSet();
	private TransferData mTransferData = new TransferData();

	/** 业务数据 */
	private String mName = "";
	private String mDelSql = "";
	
	private SQLwithBindVariables sqlbv = new SQLwithBindVariables();

	public DisDesbBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Operate==" + cOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("After getinputdata");

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("After prepareOutputData");
		/***********************************************************************
		 * logger.debug("Start DisDesbBL Submit...");
		 * 
		 * PubSubmit tSubmit = new PubSubmit();
		 * 
		 * if (!tSubmit.submitData(mResult, "")) { //
		 * 
		 * @@错误处理 this.mErrors.copyAllErrors(tSubmit.mErrors);
		 * 
		 * CError tError = new CError(); tError.moduleName = "GrpUWAutoChkBL";
		 * tError.functionName = "submitData"; tError.errorMessage = "数据提交失败!";
		 * this.mErrors.addOneError(tError);
		 * 
		 * return false; }
		 **********************************************************************/
		logger.debug("DisDesbBL end");

		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(sqlbv, "DELETE");
		map.put(mLCPENoticeResultSet, "INSERT");
		map.put(mLCPENoticeItemSet2, "UPDATE");
		map.put(mLCPENoticeItemSet3, "INSERT");
		map.put(mLCPENoticeSet2, "UPDATE");

		mResult.add(map);

		return true;
	}

	/**
	 * dealData 业务逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		logger.debug("come in  dealData()");
		// 录入体检结果
		int tDisSerialNo;
		LCDiseaseResultSchema tLCDiseaseResultSchema;
		LCDiseaseResultDB tLCDiseaseResultDB = new LCDiseaseResultDB();
		// tLCDiseaseResultDB.setContNo(mLCPENoticeResultSet.get(1).getContNo());
		tLCDiseaseResultDB.setCustomerNo(mLCPENoticeResultSet.get(1)
				.getCustomerNo());
		LCDiseaseResultSet tLCDiseaseResultSet = tLCDiseaseResultDB.query();
		if (tLCDiseaseResultSet.size() == 0) {
			tDisSerialNo = 0;
		} else {
			tDisSerialNo = tLCDiseaseResultSet.size() + 1;
		}
		// 先删除已经存过的数据
		mDelSql = "delete from lcpenoticeresult where 1=1 " + " and prtseq = '"
				+ "?prtseq?" + "'";
		sqlbv.sql(mDelSql);
		sqlbv.put("prtseq", mLCPENoticeResultSet.get(1).getPrtSeq());
		int tSerialNo = 1;
		for (int i = 1; i <= mLCPENoticeResultSet.size(); i++) {
			mLCPENoticeResultSet.get(i).setName(mName);
			mLCPENoticeResultSet.get(i).setSerialNo("" + tSerialNo);
			mLCPENoticeResultSet.get(i).setOperator(mOperator);
			mLCPENoticeResultSet.get(i).setMakeDate(PubFun.getCurrentDate());
			mLCPENoticeResultSet.get(i).setMakeTime(PubFun.getCurrentTime());
			mLCPENoticeResultSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLCPENoticeResultSet.get(i).setModifyTime(PubFun.getCurrentTime());
			tSerialNo++;

			tLCDiseaseResultSchema = new LCDiseaseResultSchema();
			tLCDiseaseResultSchema.setSerialNo("" + tSerialNo);
			tLCDiseaseResultSchema.setContNo(mLCPENoticeResultSet.get(i)
					.getContNo());
			tLCDiseaseResultSchema.setProposalContNo(mLCPENoticeResultSet
					.get(i).getContNo());
			tLCDiseaseResultSchema.setName(mName);
			tLCDiseaseResultSchema.setCustomerNo(mLCPENoticeResultSet.get(i)
					.getCustomerNo());
			tLCDiseaseResultSchema.setDisDesb(mLCPENoticeResultSet.get(i)
					.getDisDesb());
			tLCDiseaseResultSchema.setDisResult(mLCPENoticeResultSet.get(i)
					.getDisResult());
			tLCDiseaseResultSchema.setDiseaseSource("2"); // 体检结果分析
			tLCDiseaseResultSchema.setICDCode(mLCPENoticeResultSet.get(i)
					.getICDCode());
			tLCDiseaseResultSchema.setMakeDate(PubFun.getCurrentDate());
			tLCDiseaseResultSchema.setMakeTime(PubFun.getCurrentTime());
			tLCDiseaseResultSchema.setModifyDate(PubFun.getCurrentDate());
			tLCDiseaseResultSchema.setModifyTime(PubFun.getCurrentTime());
			tLCDiseaseResultSchema.setOperator(mOperator);

			mLCDiseaseResultSet.add(tLCDiseaseResultSchema);
		}

		LCPENoticeItemDB tLCPENoticeItemDB;

		for (int i = 1; i <= mLCPENoticeItemSet.size(); i++) {
			tLCPENoticeItemDB = new LCPENoticeItemDB();
			// tLCPENoticeItemDB.setContNo(mLCPENoticeItemSet.get(i).getContNo());
			tLCPENoticeItemDB.setPrtSeq(mLCPENoticeItemSet.get(i).getPrtSeq());
			tLCPENoticeItemDB.setPEItemCode(mLCPENoticeItemSet.get(i)
					.getPEItemCode());
			mLCPENoticeItemSet1 = tLCPENoticeItemDB.query();
			for (int j = 1; j <= mLCPENoticeItemSet1.size(); j++) {
				if (mLCPENoticeItemSet1.size() != 0) {
					mLCPENoticeItemSet1.get(j).setPEItemResult(
							mLCPENoticeItemSet.get(i).getPEItemResult());
					if (mLCPENoticeItemSet.get(i).getFreePE() != null
							&& !mLCPENoticeItemSet.get(i).getFreePE()
									.equals("")
							&& !mLCPENoticeItemSet.get(i).getFreePE().trim()
									.equals("N")) {
						mLCPENoticeItemSet1.get(j).setFreePE(
								mLCPENoticeItemSet.get(i).getFreePE()
										.substring(0, 1));
					} else {
						mLCPENoticeItemSet1.get(j).setFreePE("");
					}
				}
				mLCPENoticeItemSet2.add(mLCPENoticeItemSet1.get(j));
			}

		}

		LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();

		tLCPENoticeDB.setPrtSeq(mLCPENoticeSet.get(1).getPrtSeq());
		tLCPENoticeDB.setCustomerNo(mLCPENoticeSet.get(1).getCustomerNo());
		mLCPENoticeSet1 = tLCPENoticeDB.query();

		for (int i = 1; i <= mLCPENoticeSet1.size(); i++) {
			if (mLCPENoticeSet1.size() != 0) {
				mLCPENoticeSet1.get(i).setContNo(
						mLCPENoticeSet1.get(i).getContNo());
				mLCPENoticeSet1.get(i).setModifyDate(PubFun.getCurrentDate());
				mLCPENoticeSet1.get(i).setModifyTime(PubFun.getCurrentTime());
				mLCPENoticeSet1.get(i).setPEResult(
						mLCPENoticeSet.get(1).getPEResult());
				mLCPENoticeSet1.get(i).setPEAddress(
						mLCPENoticeSet.get(1).getPEAddress());
				mLCPENoticeSet1.get(i).setPEDoctor(
						mLCPENoticeSet.get(1).getPEDoctor());
				mLCPENoticeSet1.get(i).setPEDate(
						mLCPENoticeSet.get(1).getPEDate());
				mLCPENoticeSet1.get(i).setREPEDate(
						mLCPENoticeSet.get(1).getREPEDate());
				mLCPENoticeSet1.get(i).setMasculineFlag(
						mLCPENoticeSet.get(1).getMasculineFlag());

			}
			mLCPENoticeSet2.add(mLCPENoticeSet1.get(i));

		}

		/*
		 * 增加其他的体检项目结论录入 add by zhangxing
		 */
		for (int i = 1; i <= mLCPENoticeItemSet3.size(); i++) {
			mLCPENoticeItemSet3.get(i).setGrpContNo("00000000000000000000");
			mLCPENoticeItemSet3.get(i).setContNo(
					mLCPENoticeResultSet.get(i).getContNo());
			mLCPENoticeItemSet3.get(i).setProposalContNo(
					mLCPENoticeResultSet.get(i).getContNo());
			mLCPENoticeItemSet3.get(i).setPrtSeq(
					mLCPENoticeSet.get(1).getPrtSeq());
			mLCPENoticeItemSet3.get(i).setPEItemCode(
					mLCPENoticeItemSet3.get(i).getPEItemCode()); // 体检项目代码
			mLCPENoticeItemSet3.get(i).setPEItemName(
					mLCPENoticeItemSet3.get(i).getPEItemName()); // 体检项目名称
			mLCPENoticeItemSet3.get(1).setPEItemResult(
					mLCPENoticeItemSet3.get(i).getPEItemResult());

			mLCPENoticeItemSet3.get(i).setModifyDate(PubFun.getCurrentDate()); // 当前值
			mLCPENoticeItemSet3.get(i).setModifyTime(PubFun.getCurrentTime());
		}

		return true;
	}

	/**
	 * checkData 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		String sCustomerNo = mLCPENoticeResultSet.get(1).getCustomerNo();
		LDPersonDB tLDPersonDB = new LDPersonDB();
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		tLDPersonDB.setCustomerNo(sCustomerNo);
		if (tLDPersonDB.getInfo()) {
			tLDPersonSchema = tLDPersonDB.getSchema();
			mName = tLDPersonSchema.getName();
		} else {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			// <!-- XinYQ modified on 2006-12-27 : 保全复用新契约体检 : BGN -->
			LPPersonDB tLPPersonDB = new LPPersonDB();
			tLPPersonDB.setCustomerNo(sCustomerNo);
			LPPersonSet tLPPersonSet = new LPPersonSet();
			try {
				tLPPersonSet = tLPPersonDB.query();
			} catch (Exception ex) {
				CError.buildErr(this, "查询保全客户表出现异常！");
				return false;
			}
			if (tLPPersonSet == null || tLPPersonSet.size() <= 0) {
				// @@错误处理
				CError.buildErr(this, "取被体检客户姓名失败！");
				return false;
			} else {
				LPPersonSchema tLPPersonSchema = new LPPersonSchema();
				tLPPersonSchema = tLPPersonSet.get(1);
				mName = tLPPersonSchema.getName();
				tLPPersonSchema = null;
			}
			tLPPersonDB = null;
			tLPPersonSet = null;
			// <!-- XinYQ modified on 2006-12-27 : 保全复用新契约体检 : END -->
		}
		// logger.debug("---mName" + mName);
		return true;
	}

	/**
	 * getInputData
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 公用变量
		tGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mLCPENoticeResultSet = (LCPENoticeResultSet) cInputData.getObjectByObjectName("LCPENoticeResultSet", 0);		
		mLCPENoticeItemSet = (LCPENoticeItemSet) cInputData.getObjectByObjectName("LCPENoticeItemSet", 0);
		mLCPENoticeItemSet3 = (LCPENoticeItemSet) cInputData.getObjectByObjectName("LCPENoticeItemSet", 1);
		mLCPENoticeSet = (LCPENoticeSet) cInputData.getObjectByObjectName("LCPENoticeSet", 0);

		mOperator = tGI.Operator;
		if (mOperator == null || mOperator.length() <= 0) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operator失败!");
			return false;
		}

		// 取得体检信息
		if (mLCPENoticeItemSet == null || mLCPENoticeItemSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据LCPENoticeItemSet失败!");
			return false;
		}

		// 取得疾病信息
		if (mLCPENoticeResultSet == null || mLCPENoticeResultSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据LCPENoticeResultSet失败!");
			return false;
		}
		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

}
