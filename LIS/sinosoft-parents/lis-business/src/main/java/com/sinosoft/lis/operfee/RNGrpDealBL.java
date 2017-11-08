package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpContHangUpStateDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpContHangUpStateSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class RNGrpDealBL {
private static Logger logger = Logger.getLogger(RNGrpDealBL.class);
	// 错误处理
	public CErrors mErrors = new CErrors();
	private GlobalInput tGI = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 得到前台数据的容器 */
	private VData mResult = new VData();
	private VData m1Result = new VData();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	// 团体表

	/** 数据操作字符串 */
	private String mOperate;

	public RNGrpDealBL() {
	}

	public VData getResult() {
		return mResult;
	}

	public VData getLCResult() {
		return m1Result;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		tGI = ((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
		return true;
	}

	private boolean dealData() {
		// 定义处理的出错标记
		int ErrCount = 0;
		// 查询所有符合抽档条件的所有保单
		IndiDueFeePartQuery tIndiDueFeePartQuery = new IndiDueFeePartQuery();

		if (!tIndiDueFeePartQuery.submitData(mInputData, "GrpCont")) {
			this.mErrors.copyAllErrors(tIndiDueFeePartQuery.mErrors);
			return false;
		}

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		VData tVData = tIndiDueFeePartQuery.getResult();
		tLCGrpContSet = (LCGrpContSet) tVData.getObjectByObjectName(
				"LCGrpContSet", 0);
		if (!dealOneData(tLCGrpContSet)) {
			return false;
		}
		return true;
	}

	/**
	 * 生成应收总表
	 */

	private LJSPaySchema CreatLJSPay(LJSPayPersonSet tLJSPayPersonSet,
			String DealType) {
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		double tSumMoney = 0;

		for (int i = 1; i <= tLJSPayPersonSet.size(); i++) {
			tSumMoney = tSumMoney + tLJSPayPersonSet.get(i).getSumDuePayMoney();
		}

		if (DealType != null && DealType.equals("UPDATE")) {
			LJSPayDB tLJSPayDB = new LJSPayDB();
			tLJSPayDB.setGetNoticeNo(tLJSPayPersonSet.get(1).getGetNoticeNo());
			if (!tLJSPayDB.getInfo()) {
				CError.buildErr(this, "未查询到团单"
						+ tLJSPayPersonSet.get(1).getGrpContNo() + "下待催缴保单");
			} else {
				tLJSPaySchema = tLJSPayDB.getSchema();
				tLJSPaySchema.setSumDuePayMoney(tLJSPaySchema
						.getSumDuePayMoney()
						+ tSumMoney);
			}
		} else {
			String tGrpContNO = tLJSPayPersonSet.get(1).getGrpContNo();
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(tGrpContNO);
			tLCGrpContDB.getInfo();

			tLJSPaySchema.setGetNoticeNo(tLJSPayPersonSet.get(1)
					.getGetNoticeNo());
			tLJSPaySchema.setOtherNo(tGrpContNO);
			tLJSPaySchema.setOtherNoType("1");
			tLJSPaySchema.setManageCom(tLCGrpContDB.getManageCom());
			tLJSPaySchema.setAgentCode(tLCGrpContDB.getAgentCode());
			tLJSPaySchema.setAgentGroup(tLCGrpContDB.getAgentGroup());
			tLJSPaySchema.setAgentType(tLCGrpContDB.getAgentType());
			tLJSPaySchema.setAppntNo(tLCGrpContDB.getAppntNo());
			tLJSPaySchema.setBankAccNo(tLCGrpContDB.getBankAccNo());
			tLJSPaySchema.setBankCode(tLCGrpContDB.getBankCode());
			tLJSPaySchema.setBankOnTheWayFlag("0");
			tLJSPaySchema.setPayDate(tLJSPayPersonSet.get(1).getPayDate());
			tLJSPaySchema.setStartPayDate(tLJSPayPersonSet.get(1)
					.getLastPayToDate());
			tLJSPaySchema.setSumDuePayMoney(tSumMoney);
			tLJSPaySchema.setMakeDate(CurrentDate);
			tLJSPaySchema.setMakeTime(CurrentTime);
			tLJSPaySchema.setModifyDate(CurrentDate);
			tLJSPaySchema.setModifyTime(CurrentTime);
			tLJSPaySchema.setOperator(tGI.Operator);
		}
		return tLJSPaySchema;
	}

	/***************************************************************************
	 * 团险定期应首总表操作类型 如有生成缴费通知书,且未发放,直接更新应收总表 如已经发放,则新生成一次催收数据
	 **************************************************************************/
	private String[][] CreatOperater(String tGrpContNo) {
		String[][] tOperator = new String[1][2];
		String Ssql = "select * from ljspay where othernotype='1' and otherno='"
				+ tGrpContNo + "'";
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = new LJSPaySet();
		tLJSPaySet = tLJSPayDB.executeQuery(Ssql);
		if (tLJSPaySet.size() == 1) {
			String PrtSql = "select * from ljtempfee where tempfeetype='2' and StateFlag='1' and standbyflag2='"
					+ tLJSPaySet.get(1).getGetNoticeNo() + "'";
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
			tLOPRTManagerSet = tLOPRTManagerDB.executeQuery(PrtSql);
			if (tLOPRTManagerSet.size() > 0) {
				String tLimit = PubFun.getNoLimit(tGI.ManageCom);
				String tGetNoticeNo = PubFun1
						.CreateMaxNo("PAYNOTICENO", tLimit);
				tOperator[0][0] = "INSERT";
				tOperator[0][1] = tGetNoticeNo;
			} else {
				tOperator[0][0] = "UPDATE";
			}
		} else {
			String tLimit = PubFun.getNoLimit(tGI.ManageCom);
			String tGetNoticeNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
			tOperator[0][0] = "INSERT";
			tOperator[0][1] = tGetNoticeNo;
		}
		return tOperator;
	}

	/***************************************************************************
	 * 生成打印管理表数据
	 */
	private LOPRTManagerSet CreatPrintData(LJSPaySchema tLJSPaySchema) {
		String tLimit = PubFun.getNoLimit(tGI.ManageCom);

		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		try {
			String prtSeqNo1 = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			tLOPRTManagerSchema.setPrtSeq(prtSeqNo1);
			tLOPRTManagerSchema.setOtherNo(tLJSPaySchema.getOtherNo());
			tLOPRTManagerSchema.setOtherNoType("1");
			tLOPRTManagerSchema.setCode("34");
			tLOPRTManagerSchema.setManageCom(tLJSPaySchema.getManageCom());
			tLOPRTManagerSchema.setAgentCode(tLJSPaySchema.getAgentCode());
			tLOPRTManagerSchema.setReqCom(tLJSPaySchema.getManageCom());
			tLOPRTManagerSchema.setReqOperator(tLJSPaySchema.getOperator());
			tLOPRTManagerSchema.setPrtType("0");
			tLOPRTManagerSchema.setStateFlag("0");
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setOldPrtSeq(prtSeqNo1);
			tLOPRTManagerSchema.setStandbyFlag2(tLJSPaySchema.getGetNoticeNo());
			tLOPRTManagerSchema
					.setStandbyFlag3(tLJSPaySchema.getStartPayDate());
			tLOPRTManagerSchema.setStandbyFlag6(tLJSPaySchema.getAppntNo());

			LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
			tLCGrpAppntDB.setGrpContNo(tLJSPaySchema.getOtherNo());
			tLCGrpAppntDB.query();
			tLOPRTManagerSchema.setStandbyFlag7(tLCGrpAppntDB.getName());
			tLOPRTManagerSchema.setPatchFlag("0");
		} catch (Exception ex) {
			return null;
		}

		// 产生打印通知书号
		String prtSeqNo1 = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		// 发票（个人、银代）
		LOPRTManagerSchema dLOPRTManagerSchema = new LOPRTManagerSchema();
		try {
			String prtSeqNo2 = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			dLOPRTManagerSchema.setPrtSeq(prtSeqNo2);
			dLOPRTManagerSchema.setOtherNo(tLJSPaySchema.getOtherNo());
			dLOPRTManagerSchema.setOtherNoType("1");
			dLOPRTManagerSchema.setCode("37");
			dLOPRTManagerSchema.setManageCom(tLJSPaySchema.getManageCom());
			dLOPRTManagerSchema.setReqCom(tLJSPaySchema.getManageCom());
			dLOPRTManagerSchema.setAgentCode(tLJSPaySchema.getAgentCode());
			dLOPRTManagerSchema.setReqCom(tLJSPaySchema.getManageCom());
			dLOPRTManagerSchema.setReqOperator(tLJSPaySchema.getOperator());
			dLOPRTManagerSchema.setPrtType("0");
			dLOPRTManagerSchema.setStateFlag("0");
			dLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			dLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			dLOPRTManagerSchema.setOldPrtSeq(prtSeqNo1);
			dLOPRTManagerSchema
					.setStandbyFlag3(tLJSPaySchema.getStartPayDate());
			dLOPRTManagerSchema.setStandbyFlag4(String.valueOf(tLJSPaySchema
					.getSumDuePayMoney()));
			dLOPRTManagerSchema.setStandbyFlag1(tLJSPaySchema.getGetNoticeNo());
			dLOPRTManagerSchema.setPatchFlag("0");
		} catch (Exception ex) {
			return null;
		}

		tLOPRTManagerSet.add(tLOPRTManagerSchema);
		tLOPRTManagerSet.add(dLOPRTManagerSchema);
		return tLOPRTManagerSet;
	}

	/***************************************************************************
	 * 处理一个团体合同
	 **************************************************************************/
	public boolean dealOneData(LCGrpContSet tLCGrpContSet) {
		// 对抽档情况进行区分并进行操作
		int tError = 0;
		logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
				+ tLCGrpContSet.size());
		for (int i = 1; i <= tLCGrpContSet.size(); i++) {

			// 本次收据号，及操作类型
			String tGetNoticeNo = "";
			String[][] tOperator = null;
			String tManager = "";

			LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
			tLCGrpContSchema = tLCGrpContSet.get(i);

			String Sql = "select * from lcgrpconthangupstate where grpcontno='"
					+ tLCGrpContSchema.getGrpContNo() + "' and rnflag='1'";
			LCGrpContHangUpStateDB tLCGrpContHangUpStateDB = new LCGrpContHangUpStateDB();
			LCGrpContHangUpStateSet tLCGrpContHangUpStateSet = new LCGrpContHangUpStateSet();
			tLCGrpContHangUpStateSet = tLCGrpContHangUpStateDB
					.executeQuery(Sql);
			if (tLCGrpContHangUpStateSet.size() > 0) {
				String HangUpType = tLCGrpContHangUpStateSet.get(1)
						.getHangUpType();
				String sql = " select * from ldcode where code = '"
						+ HangUpType + "'" + " and codetype = 'conthanguptype'";
				LDCodeDB tLDCodeDB = new LDCodeDB();
				LDCodeSet tLDCodeSet = tLDCodeDB.executeQuery(sql);
				if (tLDCodeSet.size() == 0) {
					this.mErrors.addOneError("团单"
							+ tLCGrpContSchema.getGrpContNo()
							+ "已经被其他业务挂起，无法确定业务类型");
					tError++;
					continue;
				} else {
					this.mErrors.addOneError("团单"
							+ tLCGrpContSchema.getGrpContNo() + "已经被"
							+ tLDCodeSet.get(1).getCodeAlias() + "业务整单挂起");
					tError++;
					continue;
				}
			}

			// 生成本次对于应收集路的处理方法
			tOperator = CreatOperater(tLCGrpContSchema.getGrpContNo());
			tManager = tOperator[0][0];
			tGetNoticeNo = tOperator[0][1];
			logger.debug("本次操作类型======================" + tOperator[0][0]);
			logger.debug("本次收据号======================" + tOperator[0][1]);

			String tSql = "select * from LCCont where grpcontno='"
					+ tLCGrpContSchema.getGrpContNo() + "' and appflag='1'";
			logger.debug("查询团体下个人::::::::::::" + tSql);
			LCContSet tLCContSet = new LCContSet();
			LCContDB tLCContDB = new LCContDB();
			tLCContSet = tLCContDB.executeQuery(tSql);

			LCContSet NeedLCContSet = new LCContSet();
			// 筛选个人须催缴保单
			for (int t = 1; t <= tLCContSet.size(); t++) {
				LCContSchema tLCContSchema = tLCContSet.get(t);
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("Contno", tLCContSchema
						.getContNo());
				tTransferData.setNameAndValue("ContType", "2");
				VData tv = new VData();
				tv.add(tTransferData);
				tv.add(tGI);
				IndiDueFeePartQuery tIndiDueFeePartQuery = new IndiDueFeePartQuery();
				if (!tIndiDueFeePartQuery.submitData(tv, "AllCont")) {
					continue;
				} else {
					LCContSet nLCContSet = new LCContSet();
					VData tVData = tIndiDueFeePartQuery.getResult();
					nLCContSet = (LCContSet) tVData.getObjectByObjectName(
							"LCContSet", 0);
					NeedLCContSet.add(nLCContSet);
				}
			}

			if (NeedLCContSet.size() == 0) {
				this.mErrors.addOneError("未查询到团单"
						+ tLCGrpContSchema.getGrpContNo() + "下待催缴保单");
				tError++;
				continue;
			}

			// 定义团单下个人业务数据容器
			LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();

			// 自动续保
			LCPolSet tLCPolSet = new LCPolSet();
			LCDutySet tLCDutySet = new LCDutySet();
			LCPremSet tLCPremSet = new LCPremSet();
			LCGetSet tLCGetSet = new LCGetSet();

			for (int t = 1; t <= NeedLCContSet.size(); t++) {
				LCContSchema tLCContSchema = NeedLCContSet.get(t);
				RnDealBL tRnDealBL = new RnDealBL();
				VData tVData = new VData();
				tVData.add(tLCContSchema);
				tVData.add(tGI);
				if (!tRnDealBL.submitData(tVData, "ContNo")) {
					this.mErrors.copyAllErrors(tRnDealBL.mErrors);
					continue;
				} else {
					tLJSPayPersonSet.add(tRnDealBL.getLJSPayPerson());
					if (tRnDealBL.ReNewFlag().equals("Y")) {
						// 需要提交的个人附加险自动续保相关表
						tLCPolSet.add(tRnDealBL.getLCPol());
						tLCDutySet.add(tRnDealBL.getLCDuty());
						tLCPremSet.add(tRnDealBL.getLCPrem());
						tLCGetSet.add(tRnDealBL.getLCGet());
					}
				}
			}
			if (tLJSPayPersonSet.size() == 0) {
				this.mErrors.addOneError("团单" + tLCGrpContSchema.getGrpContNo()
						+ "生成个人下应收数据失败");
				tError++;
				continue;
			}
			for (int t = 1; t <= tLJSPayPersonSet.size(); t++) {
				// 需要提交的个人应收表
				tLJSPayPersonSet.get(t).setGetNoticeNo(tGetNoticeNo);
			}

			// 需要提交的应收总表
			LJSPaySchema tLJSPaySchema = new LJSPaySchema();
			tLJSPaySchema = CreatLJSPay(tLJSPayPersonSet, tManager);
			if (tLJSPaySchema == null) {
				tError++;
				continue;
			}

			// 需要提交的应收集体表
			String tLJSPayGrpSql = "insert into ljspaygrp select grppolno,paycount,grpcontno,managecom,'','',"
					+ " riskcode,agentcode,'','',appntno,getnoticeno,"
					+ " sum(sumduepaymoney),sum(sumactupaymoney),payintv,"
					+ " paydate,'ZC',lastpaytodate,curpaytodate,'','','',"
					+ " '','',operator, trunc(sysdate),to_char(sysdate, 'hh24:mi:ss'), trunc(sysdate),to_char(sysdate, 'hh24:mi:ss')"
					+ " from  ljspayperson"
					+ " where   grpcontno='"
					+ tLCGrpContSchema.getGrpContNo()
					+ "'"
					+ " group by grppolno,paycount,grpcontno,managecom,riskcode,agentcode,"
					+ " appntno,getnoticeno,payintv,paydate,'ZC',lastpaytodate,curpaytodate,"
					+ " operator";

			// 需要提交的打印管理表
			LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
			tLOPRTManagerSet = CreatPrintData(tLJSPaySchema);
			if (tLOPRTManagerSet.size() == 0) {
				this.mErrors.addOneError("团单" + tLCGrpContSchema.getGrpContNo()
						+ "生成打印管理表数据失败");
				tError++;
				continue;
			}

			MMap tMMap = new MMap();
			tMMap.put(tLJSPayPersonSet, "INSERT");
			tMMap.put(tLJSPaySchema, "DELETE&INSERT");
			tMMap.put("delete from ljspaygrp where grpcontno='"
					+ tLCGrpContSchema.getGrpContNo() + "' and paytype='ZC'",
					"DELETE");
			tMMap.put(tLJSPayGrpSql, "INSERT");
			tMMap.put(tLOPRTManagerSet, "INSERT");
			if (tLCPolSet.size() > 0 && tLCDutySet.size() > 0
					&& tLCPremSet.size() > 0 && tLCGetSet.size() > 0) {
				tMMap.put(tLCPolSet, "INSERT");
				tMMap.put(tLCDutySet, "INSERT");
				tMMap.put(tLCPremSet, "INSERT");
				tMMap.put(tLCGetSet, "INSERT");
			}

			if (mOperate.equals("Confirm")) {
				VData tVData = new VData();
				tVData.add(tMMap);
				// 提交数据
				PubSubmit tSubmit = new PubSubmit();
				if (!tSubmit.submitData(tVData, "")) {
					// @@错误处理
					tError++;
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					continue;
				}
			} else {
				mMap.add(tMMap);
			}
		}
		if (tError > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {
		// LCPolSchema tLCPolSchema = new LCPolSchema();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Contno", "880000009252"); // autorenew
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86";
		tGI.Operator = "001";

		// tTransferData.setNameAndValue("ContNo", "2301190000000023");
		VData tv = new VData();
		tv.add(tTransferData);
		tv.add(tGI);

		logger.debug("准备好了数据");
		// tv.add(tTransferData);

		RNGrpDealBL tRNGrpDealBL = new RNGrpDealBL();
		if (tRNGrpDealBL.submitData(tv, "Confirm")) {
			logger.debug("个单批处理催收完成");
		} else {
			logger.debug("个单批处理催收信息提示："
					+ tRNGrpDealBL.mErrors.getFirstError());
		}
	}

}
