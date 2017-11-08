package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJABonusGetDB;
import com.sinosoft.lis.db.LJAGetClaimDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LJAGetEndorseDB;
import com.sinosoft.lis.db.LJAGetOtherDB;
import com.sinosoft.lis.db.LJAGetTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubLock;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.schema.LYBankLogSchema;
import com.sinosoft.lis.schema.LYDupGetSchema;
import com.sinosoft.lis.schema.LYReturnFromBankBSchema;
import com.sinosoft.lis.schema.LYReturnFromBankSchema;
import com.sinosoft.lis.schema.LYSendToBankBSchema;
import com.sinosoft.lis.schema.LYSendToBankSchema;
import com.sinosoft.lis.vschema.LJABonusGetSet;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJFIGetSet;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.lis.vschema.LYDupGetSet;
import com.sinosoft.lis.vschema.LYReturnFromBankBSet;
import com.sinosoft.lis.vschema.LYReturnFromBankSet;
import com.sinosoft.lis.vschema.LYSendToBankBSet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 银行数据转换到业务系统
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class PayReturnFromBankBL {
private static Logger logger = Logger.getLogger(PayReturnFromBankBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// 业务数据
	private String totalMoney = "";
	private int sumNum = 0;
	private String serialNo = "";

	private LYReturnFromBankSchema inLYReturnFromBankSchema = new LYReturnFromBankSchema();
	private TransferData inTransferData = new TransferData();
	private LYReturnFromBankSet inLYReturnFromBankSet = new LYReturnFromBankSet();
	private GlobalInput mGlobalInput = new GlobalInput();

	private LYDupGetSet outLYDupGetSet = new LYDupGetSet();
	private LYBankLogSet outLYBankLogSet = new LYBankLogSet();
	private LYReturnFromBankSet outDelLYReturnFromBankSet = new LYReturnFromBankSet();
	private LJFIGetSet outLJFIGetSet = new LJFIGetSet();
	private LJAGetSet outLJAGetSet = new LJAGetSet();
	private LYReturnFromBankBSet outLYReturnFromBankBSet = new LYReturnFromBankBSet();
	private LYSendToBankSet outDelLYSendToBankSet = new LYSendToBankSet();
	private LYSendToBankBSet outLYSendToBankBSet = new LYSendToBankBSet();

	private LJAGetDrawSet outLJAGetDrawSet = new LJAGetDrawSet();
	private LJAGetEndorseSet outLJAGetEndorseSet = new LJAGetEndorseSet();
	private LJAGetTempFeeSet outLJAGetTempFeeSet = new LJAGetTempFeeSet();
	private LJAGetClaimSet outLJAGetClaimSet = new LJAGetClaimSet();
	private LJAGetOtherSet outLJAGetOtherSet = new LJAGetOtherSet();
	private LJABonusGetSet outLJABonusGetSet = new LJABonusGetSet();
	private MMap map = new MMap();

	private boolean mLjManagecom = false;
	private String mBankManage = "";

	public PayReturnFromBankBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");
		// 增加并发校验
		  String serialNo=inLYReturnFromBankSchema.getSerialNo();
		String key = "Bank" + serialNo;
		PubLock tPubLock = new PubLock();
		if (!tPubLock.lock(key, "准备" + serialNo + "回盘确认数据")) {
			this.mErrors.copyAllErrors(tPubLock.mErrors);
			return false;
		}
		try{
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("---End prepareOutputData---");
		PubSubmit tPubSubmit=new PubSubmit();
		if(!tPubSubmit.submitData(mInputData)){
			CError.buildErr(this, "数据提交失败!"+tPubSubmit.mErrors.getFirstError());
			return false;
		}
		}finally{
			if (!tPubLock.unLock(key)) {
				CError.buildErr(this, serialNo + "解锁失败:"
						+ tPubLock.mErrors.getFirstError());
				return false;
			}
		}
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			inLYReturnFromBankSchema = (LYReturnFromBankSchema) mInputData
					.getObjectByObjectName("LYReturnFromBankSchema", 0);
			inTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);

			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PayReturnFromBankBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}


	/**
	 * 获取银行返回数据
	 * 
	 * @param inLYReturnFromBankSchema
	 * @return
	 */
	private LYReturnFromBankSet getLYReturnFromBank(
			LYReturnFromBankSchema inLYReturnFromBankSchema) {
		LYReturnFromBankSet tLYReturnFromBankSet = inLYReturnFromBankSchema
				.getDB().query();

		return tLYReturnFromBankSet;
	}

	private void verifyBankSucc() {
//		String bankSuccFlag = getBankSuccFlag(inLYReturnFromBankSet.get(1));

		for (int i = 0; i < inLYReturnFromBankSet.size(); i++) {
			LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet
					.get(i + 1);
			if (tLYReturnFromBankSchema.getConvertFlag() != null) {
				continue;
			}

			if(!"0000".equals(tLYReturnFromBankSchema.getBankSuccFlag())){	//在读入文件时使用统一正确编码
				tLYReturnFromBankSchema.setConvertFlag("1");
			}

		}
	}

	/**
	 * 校验财务总金额
	 * 
	 * @param tLYReturnFromBankSet
	 * @param totalMoney
	 * @return
	 */
	private boolean confirmTotalMoney(LYReturnFromBankSet tLYReturnFromBankSet,
			String totalMoney) {
		int i;
		double sumMoney = 0;
		double fTotalMoney = Double.valueOf(totalMoney).doubleValue();

		for (i = 0; i < tLYReturnFromBankSet.size(); i++) {
			LYReturnFromBankSchema tLYReturnFromBankSchema = tLYReturnFromBankSet
					.get(i + 1);
			if (tLYReturnFromBankSchema.getConvertFlag() != null) {
				continue;
			}

			sumMoney = sumMoney + tLYReturnFromBankSchema.getPayMoney();
			sumMoney = PubFun.setPrecision(sumMoney, "0.00");
			sumNum = sumNum + 1;
		}

		logger.debug("输入金额：" + fTotalMoney + "\n银行返还金额：" + sumMoney);
//		if (sumMoney == 0) // 回盘信息中没有含金额信息的情况
//			return true;
//		else
		return (fTotalMoney == sumMoney);
	}

	/**
	 * 记录入重复交费记录表
	 * 
	 * @param tLYReturnFromBankSchema
	 */
	private void setLYDupGet(LYReturnFromBankSchema tLYReturnFromBankSchema) {
		Reflections tReflections = new Reflections();
		LYDupGetSchema tLYDupGetSchema = new LYDupGetSchema();
		tReflections.transFields(tLYDupGetSchema, tLYReturnFromBankSchema);
		tLYDupGetSchema.setDataType("F");
		outLYDupGetSet.add(tLYDupGetSchema);
	}

	/**
	 * 校验每笔金额
	 */
	private void verifyUnitMoney() {
		int i;

		for (i = 0; i < inLYReturnFromBankSet.size(); i++) {
			LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet
					.get(i + 1);
			if (tLYReturnFromBankSchema.getConvertFlag() != null) {
				continue;
			}

			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema.setActuGetNo(tLYReturnFromBankSchema.getPayCode());
			tLJAGetSchema.setSchema(tLJAGetSchema.getDB().query().get(1));

			// 付款不等于实付表中的信息，则转入重复给付表，否则通过
			if (tLJAGetSchema.getSumGetMoney() != tLYReturnFromBankSchema
					.getPayMoney()
					&& tLYReturnFromBankSchema.getPayMoney() > 0) // 付款信息中没有含金额情况时该字段值为0
			{
				setLYDupGet(tLYReturnFromBankSchema);
				tLYReturnFromBankSchema.setConvertFlag("2");
			}

			inLYReturnFromBankSet.set(i + 1, tLYReturnFromBankSchema);
		}
	}


	/**
	 * 通过银行返回数据获取应收表数据
	 * 
	 * @param tLYReturnFromBankSchema
	 * @return
	 */
	private LJAGetSchema getLJAGetByLYReturnFromBank(
			LYReturnFromBankSchema tLYReturnFromBankSchema) {
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();

		tLJAGetSchema.setActuGetNo(tLYReturnFromBankSchema.getPayCode());
		return tLJAGetSchema.getDB().query().get(1);
	}

	/**
	 * 转入财务付费表
	 * 
	 * @param tLYReturnFromBankSchema
	 */
	private void setLJFIGet(LYReturnFromBankSchema tLYReturnFromBankSchema) {
		LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();
		LJAGetSchema tLJAGetSchema = getLJAGetByLYReturnFromBank(tLYReturnFromBankSchema);

		tLJFIGetSchema.setActuGetNo(tLYReturnFromBankSchema.getPayCode());
		tLJFIGetSchema.setPayMode("4");
		tLJFIGetSchema.setOtherNo(tLYReturnFromBankSchema.getPolNo());
		tLJFIGetSchema.setOtherNoType(tLJAGetSchema.getOtherNoType());
		tLJFIGetSchema.setGetMoney(tLYReturnFromBankSchema.getPayMoney());
		tLJFIGetSchema.setEnterAccDate(tLYReturnFromBankSchema
				.getBankDealDate());
		tLJFIGetSchema.setConfDate(PubFun.getCurrentDate());
		tLJFIGetSchema.setShouldDate(tLJAGetSchema.getShouldDate());
		tLJFIGetSchema.setOperator(mGlobalInput.Operator);
		tLJFIGetSchema.setMakeDate(PubFun.getCurrentDate());
		tLJFIGetSchema.setMakeTime(PubFun.getCurrentTime());
		tLJFIGetSchema.setModifyDate(PubFun.getCurrentDate());
		tLJFIGetSchema.setModifyTime(PubFun.getCurrentTime());

		tLJFIGetSchema.setBankCode(tLYReturnFromBankSchema.getBankCode());
		tLJFIGetSchema.setConfMakeDate(PubFun.getCurrentDate());
		String tSerialNo = PubFun1.CreateMaxNo("ReturnFromBank", 20);
		tLJFIGetSchema.setSerialNo(tSerialNo);
		
		//20110513 modified by Cuizhe 未解决插入ljfiget记录时Currency为空异常
		tLJFIGetSchema.setCurrency(tLJAGetSchema.getCurrency());

		LJAGetDB tLJAGetDB = new LJAGetDB();
		tLJAGetDB.setActuGetNo(tLYReturnFromBankSchema.getPayCode());
		if (tLJAGetDB.getInfo()) {
			tLJFIGetSchema.setSaleChnl(tLJAGetDB.getSaleChnl());
			tLJFIGetSchema.setManageCom(tLJAGetDB.getManageCom());

			tLJFIGetSchema.setAgentCom(tLJAGetDB.getAgentCom());
			tLJFIGetSchema.setAgentType(tLJAGetDB.getAgentType());
			tLJFIGetSchema.setAgentGroup(tLJAGetDB.getAgentGroup());
			tLJFIGetSchema.setAgentCode(tLJAGetDB.getAgentCode());
			tLJFIGetSchema.setOtherNoType(tLJAGetDB.getOtherNoType());
			tLJFIGetSchema.setDrawer(tLJAGetDB.getAccName());
			tLJFIGetSchema.setDrawerID(tLJAGetDB.getDrawerID());
			tLJFIGetSchema.setPolicyCom(tLJAGetDB.getManageCom());
		}

		outLJFIGetSet.add(tLJFIGetSchema);
	}

	/**
	 * 校验实付表
	 */
	private void verifyLJAGet() {
		int i;

		for (i = 0; i < inLYReturnFromBankSet.size(); i++) {
			// 遍历每一条返回盘记录
			LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet
					.get(i + 1);
			if (tLYReturnFromBankSchema.getConvertFlag() != null) {
				continue;
			}

			// 转入财务付费表
			setLJFIGet(tLYReturnFromBankSchema);
		}
	}

	/**
	 * 备份银行返回盘记录表到B表
	 */
	private void getLYReturnFromBankB() {
		int i;
		Reflections tReflections = new Reflections();

		for (i = 0; i < inLYReturnFromBankSet.size(); i++) {
			LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet
					.get(i + 1);

			// 判断成功标记
			LYReturnFromBankBSchema tLYReturnFromBankBSchema = new LYReturnFromBankBSchema();
			tReflections.transFields(tLYReturnFromBankBSchema,
					tLYReturnFromBankSchema);
			tLYReturnFromBankBSchema.setRemark(mGlobalInput.Operator);
			outLYReturnFromBankBSet.add(tLYReturnFromBankBSchema);
		}
	}

	/**
	 * 备份银行返回盘记录表到B表
	 */
	private void getLYSendToBankB() {
		LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();
		tLYSendToBankSchema.setSerialNo(serialNo);
		LYSendToBankSet tLYSendToBankSet = tLYSendToBankSchema.getDB().query();

		Reflections tReflections = new Reflections();
		for (int i = 1; i <= tLYSendToBankSet.size(); i++) {
			LYSendToBankBSchema tLYSendToBankBSchema = new LYSendToBankBSchema();
			LYSendToBankSchema tmpLYSendToBankSchema = tLYSendToBankSet.get(i);

			tReflections.transFields(tLYSendToBankBSchema,
					tmpLYSendToBankSchema);

			// tLYSendToBankBSchema.setOperator(mGlobalInput.Operator);
			tLYSendToBankBSchema.setModifyDate(PubFun.getCurrentDate());
			tLYSendToBankBSchema.setModifyTime(PubFun.getCurrentTime());

			outLYSendToBankBSet.add(tLYSendToBankBSchema);
		}
	}

	/**
	 * 获取发送盘数据，用于删除
	 */
	private void getLYSendToBank() {
		LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();

		tLYSendToBankSchema.setSerialNo(serialNo);
		outDelLYSendToBankSet = tLYSendToBankSchema.getDB().query();
	}

	/**
	 * 获取实付表数据，修改银行在途标志、财务到帐日期、财务确认日期
	 */
	private void getLJAGet() throws Exception {
		try {
			for (int i = 0; i < inLYReturnFromBankSet.size(); i++) {
				LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet
						.get(i + 1);

				// 获取实付总表数据
				LJAGetSchema tLJAGetSchema = getLJAGetByLYReturnFromBank(tLYReturnFromBankSchema);
				tLJAGetSchema.setBankOnTheWayFlag("0");
				tLJAGetSchema.setModifyDate(PubFun.getCurrentDate());
				tLJAGetSchema.setModifyTime(PubFun.getCurrentTime());

				// 如果不能正常核销
				if (tLYReturnFromBankSchema.getConvertFlag() != null) {
					outLJAGetSet.add(tLJAGetSchema);
					continue;
				}
				
				// 核销
				tLJAGetSchema.setEnterAccDate(PubFun.getCurrentDate());
				tLJAGetSchema.setConfDate(PubFun.getCurrentDate());
				outLJAGetSet.add(tLJAGetSchema);

				String OtherNoType = tLJAGetSchema.getOtherNoType();

				// 更新给付表(生存领取_实付) LJAGetDraw
				if (OtherNoType.equals("1") || OtherNoType.equals("2")
						|| OtherNoType.equals("0")) {
					updateLJAGetDraw(tLJAGetSchema);
				}

				// 更新暂交费退费实付表 LJAGetTempFee
				if (OtherNoType.equals("4")) {
					updateLJAGetTempFee(tLJAGetSchema);
				}

				// 更新赔付实付表 LJAGetClaim
				if (OtherNoType.equals("5")) {
					updateLJAGetClaim(tLJAGetSchema);
				}

				// 更新其他退费实付表 LJAGetOther
				if (OtherNoType.equals("6") || OtherNoType.equals("8")) {
					updateLJAGetOther(tLJAGetSchema);
				}

				// 更新红利给付实付表 LJABonusGet
				if (OtherNoType.equals("7")) {
					updateLJABonusGet(tLJAGetSchema);
				}
				if (OtherNoType.equals("10")) {
					updateLJAGetDraw(tLJAGetSchema);
					updateLJAGetEndorse(tLJAGetSchema);
					updateLJABonusGet(tLJAGetSchema);
					updateLJAGetEndorse(tLJAGetSchema);
				}
				}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 查询给付表(生存领取_实付)
	 * 
	 * @param LJAGetBL
	 * @return
	 */
	private void updateLJAGetDraw(LJAGetSchema mLJAGetSchema) throws Exception {
		String sqlStr = "select * from LJAGetDraw where ActuGetNo='"
				+ "?ActuGetNo?" + "'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sqlStr);
		sqlbv1.put("ActuGetNo", mLJAGetSchema.getActuGetNo());
		logger.debug("查询给付表(生存领取_实付):" + sqlStr);
		LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
		LJAGetDrawSet tLJAGetDrawSet = tLJAGetDrawDB.executeQuery(sqlbv1);

		// if (tLJAGetDrawSet.size() == 0)
		// {
		// throw new Exception("给付表无数据，不能核销！");
		// }
			for (int n = 1; n <= tLJAGetDrawSet.size(); n++) {
				LJAGetDrawSchema tLJAGetDrawSchema = tLJAGetDrawSet.get(n);
				tLJAGetDrawSchema.setConfDate(mLJAGetSchema.getConfDate());
				tLJAGetDrawSchema.setEnterAccDate(mLJAGetSchema
						.getEnterAccDate());
				tLJAGetDrawSchema.setModifyDate(PubFun.getCurrentDate());
				tLJAGetDrawSchema.setModifyTime(PubFun.getCurrentTime());
				outLJAGetDrawSet.add(tLJAGetDrawSchema);
			}
	}

	/**
	 * 查询批改补退费表(实收/实付)
	 * 
	 * @param LJAGetSchema
	 * @return
	 */
	private void updateLJAGetEndorse(LJAGetSchema mLJAGetSchema)
			throws Exception {
		String sqlStr = "select * from LJAGetEndorse where ActuGetNo='"
				+ "?ActuGetNo1?" + "'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sqlStr);
		sqlbv2.put("ActuGetNo1", mLJAGetSchema.getActuGetNo());
		logger.debug("查询给付表(生存领取_实付):" + sqlStr);
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
		LJAGetEndorseSet tLJAGetEndorseSet = tLJAGetEndorseDB
				.executeQuery(sqlbv2);

		// if (tLJAGetEndorseSet.size() == 0)
		// {
		// throw new Exception("批改补退费表无数据，不能核销！");
		// }
			for (int n = 1; n <= tLJAGetEndorseSet.size(); n++) {
				LJAGetEndorseSchema tLJAGetEndorseSchema = tLJAGetEndorseSet
						.get(n);
				tLJAGetEndorseSchema.setGetConfirmDate(mLJAGetSchema
						.getConfDate());
				tLJAGetEndorseSchema.setEnterAccDate(mLJAGetSchema
						.getEnterAccDate());
				tLJAGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
				tLJAGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
				outLJAGetEndorseSet.add(tLJAGetEndorseSchema);
			}
	}

	/**
	 * 查询其他退费实付表
	 * 
	 * @param LJAGetSchema
	 * @return
	 */
	private void updateLJAGetOther(LJAGetSchema mLJAGetSchema) throws Exception {
		String sqlStr = "select * from LJAGetOther where ActuGetNo='"
				+ "?ActuGetNo2?" + "'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sqlStr);
		sqlbv3.put("ActuGetNo2", mLJAGetSchema.getActuGetNo());
		logger.debug("其他退费实付表:" + sqlStr);
		LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
		LJAGetOtherSet tLJAGetOtherSet = tLJAGetOtherDB.executeQuery(sqlbv3);

		if (tLJAGetOtherSet.size() == 0) {
			throw new Exception("其他退费实付表无数据，不能核销！");
		}

		for (int n = 1; n <= tLJAGetOtherSet.size(); n++) {
			LJAGetOtherSchema tLJAGetOtherSchema = tLJAGetOtherSet.get(n);
			tLJAGetOtherSchema.setConfDate(mLJAGetSchema.getConfDate());
			tLJAGetOtherSchema.setEnterAccDate(mLJAGetSchema.getEnterAccDate());
			tLJAGetOtherSchema.setModifyDate(PubFun.getCurrentDate());
			tLJAGetOtherSchema.setModifyTime(PubFun.getCurrentTime());
			outLJAGetOtherSet.add(tLJAGetOtherSchema);
		}
	}

	/**
	 * 赔付实付表
	 * 
	 * @param LJAGetSchema
	 * @return
	 */
	private void updateLJAGetClaim(LJAGetSchema mLJAGetSchema) throws Exception {
		String sqlStr = "select * from LJAGetClaim where ActuGetNo='"
				+ "?ActuGetNo3?" + "'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sqlStr);
		sqlbv4.put("ActuGetNo3", mLJAGetSchema.getActuGetNo());
		logger.debug("赔付实付表:" + sqlStr);
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		LJAGetClaimSet tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv4);

		if (tLJAGetClaimSet.size() == 0) {
			throw new Exception("赔付实付表无数据，不能核销！");
		}

		for (int n = 1; n <= tLJAGetClaimSet.size(); n++) {
			LJAGetClaimSchema tLJAGetClaimSchema = tLJAGetClaimSet.get(n);
			tLJAGetClaimSchema.setConfDate(mLJAGetSchema.getConfDate());
			tLJAGetClaimSchema.setEnterAccDate(mLJAGetSchema.getEnterAccDate());
			tLJAGetClaimSchema.setModifyDate(PubFun.getCurrentDate());
			tLJAGetClaimSchema.setModifyTime(PubFun.getCurrentTime());
			outLJAGetClaimSet.add(tLJAGetClaimSchema);
		}
	}

	/**
	 * 暂交费退费实付表
	 * 
	 * @param LJAGetSchema
	 * @return
	 */
	private void updateLJAGetTempFee(LJAGetSchema mLJAGetSchema)
			throws Exception {
		String sqlStr = "select * from LJAGetTempFee where ActuGetNo='"
				+ "?ActuGetNo4?" + "'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sqlStr);
		sqlbv5.put("ActuGetNo4", mLJAGetSchema.getActuGetNo());
		logger.debug("暂交费退费实付表:" + sqlStr);
		LJAGetTempFeeDB tLJAGetTempFeeDB = new LJAGetTempFeeDB();
		LJAGetTempFeeSet tLJAGetTempFeeSet = tLJAGetTempFeeDB
				.executeQuery(sqlbv5);

		if (tLJAGetTempFeeSet.size() == 0) {
			throw new Exception("暂交费退费实付表无数据，不能核销！");
		}

		for (int n = 1; n <= tLJAGetTempFeeSet.size(); n++) {
			LJAGetTempFeeSchema tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(n);
			tLJAGetTempFeeSchema.setConfDate(mLJAGetSchema.getConfDate());
			tLJAGetTempFeeSchema.setEnterAccDate(mLJAGetSchema
					.getEnterAccDate());
			tLJAGetTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
			tLJAGetTempFeeSchema.setModifyTime(PubFun.getCurrentTime());
			outLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
		}
	}

	/**
	 * 红利给付实付表
	 * 
	 * @param LJAGetSchema
	 * @return
	 */
	private void updateLJABonusGet(LJAGetSchema mLJAGetSchema) throws Exception {
		String sqlStr = "select * from LJABonusGet where ActuGetNo='"
				+ "?ActuGetNo6?" + "'";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sqlStr);
		sqlbv6.put("ActuGetNo6", mLJAGetSchema.getActuGetNo());
		logger.debug("红利给付实付表:" + sqlStr);
		LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
		LJABonusGetSet tLJABonusGetSet = tLJABonusGetDB.executeQuery(sqlbv6);

		// if (tLJABonusGetSet.size() == 0)
		// {
		// throw new Exception("红利给付实付表无数据，不能核销！");
		// }
			for (int n = 1; n <= tLJABonusGetSet.size(); n++) {
				LJABonusGetSchema tLJABonusGetSchema = tLJABonusGetSet.get(n);
				tLJABonusGetSchema.setConfDate(mLJAGetSchema.getConfDate());
				tLJABonusGetSchema.setEnterAccDate(mLJAGetSchema
						.getEnterAccDate());
				tLJABonusGetSchema.setModifyDate(PubFun.getCurrentDate());
				tLJABonusGetSchema.setModifyTime(PubFun.getCurrentTime());
				outLJABonusGetSet.add(tLJABonusGetSchema);
			}
	}

	/**
	 * 生成银行日志数据
	 * 
	 * @param tLYSendToBankSchema
	 * @return
	 */
	private LYBankLogSet getLYBankLog() {
		LYBankLogSchema tLYBankLogSchema = new LYBankLogSchema();
		LYBankLogSet tLYBankLogSet = new LYBankLogSet();

		// 获取日志记录
		tLYBankLogSchema.setSerialNo(serialNo);
		tLYBankLogSet.set(tLYBankLogSchema.getDB().query());

		if (tLYBankLogSet.size() > 0) {
			tLYBankLogSchema.setSchema(tLYBankLogSet.get(1));

			// 修改日志
			tLYBankLogSchema.setAccTotalMoney(totalMoney); // 财务确认总金额
			tLYBankLogSchema.setBankSuccMoney(totalMoney); // 银行成功总金额
			tLYBankLogSchema.setBankSuccNum(sumNum + ""); // 银行成功总数量
			tLYBankLogSchema.setDealState("1"); // 处理状态
			tLYBankLogSchema.setModifyDate(PubFun.getCurrentDate());
			tLYBankLogSchema.setModifyTime(PubFun.getCurrentTime());

			tLYBankLogSet.clear();
			tLYBankLogSet.add(tLYBankLogSchema);
		}

		return tLYBankLogSet;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 银行代付
			if (mOperate.equals("PAYMONEY")) {
				// 获取银行返回数据
				inLYReturnFromBankSet = getLYReturnFromBank(inLYReturnFromBankSchema);
				if (inLYReturnFromBankSet.size() == 0) {
					throw new NullPointerException("无银行返回数据！");
				}
				logger.debug("---End getLYReturnFromBank---");

				// 记录批次号、总金额、备份银行返回盘表
				serialNo = inLYReturnFromBankSet.get(1).getSerialNo();
				totalMoney = (String) inTransferData
						.getValueByName("totalMoney");
				outDelLYReturnFromBankSet.set(inLYReturnFromBankSet);

				// 校验银行付款成功与否
				verifyBankSucc();
				logger.debug("---End verifyBankSucc---");

				// 校验财务总金额
				if (!confirmTotalMoney(inLYReturnFromBankSet, totalMoney)) {
					throw new NullPointerException("财务总金额确认失败！请与银行对帐！");
				}
				logger.debug("---End confirmTotalMoney---");

				// 校验每笔金额
				verifyUnitMoney();
				logger.debug("---End verifyUnitMoney---");

				// 判断该银行是否是统一扣划的银行
				SSRS tSSRS = new SSRS(); // mLjManagecom mBankManage
				ExeSQL tExeSQL = new ExeSQL();
				String t1Sql = "select codealias  from ldcode1 where codetype = 'BankBigList' and code = '"
						+ "?code?"
						+ "'"
						+ " and code1 = 'Y' and comcode = '"
						+ "?comcode?" + "'";
				SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
				sqlbv7.sql(t1Sql);
				sqlbv7.put("code", inLYReturnFromBankSet.get(1).getBankCode());
				sqlbv7.put("comcode", mGlobalInput.ManageCom);
				tSSRS = tExeSQL.execSQL(sqlbv7);
				if (tSSRS.getMaxRow() > 0) {
					mLjManagecom = true;
					mBankManage = tSSRS.GetText(1, 1);
				}

				// 校验实付表，转入财务付费表
				verifyLJAGet();
				logger.debug("---End verifyLJAGet---");

				// 备份返回盘数据到返回盘B表
				getLYReturnFromBankB();
				logger.debug("---End getLYReturnFromBankB---");

				// 备份发送盘数据到发盘B表
				getLYSendToBankB();
				logger.debug("---End getLYSendToBankB---");
				// 获取发送盘数据，用于删除
				getLYSendToBank();
				logger.debug("---End getLYSendToBank---");

				// 获取实付表数据，修改银行在途标志、财务到帐日期、财务确认日期
				getLJAGet();
				logger.debug("---End getLJAGet---");

				// 记录日志
				outLYBankLogSet = getLYBankLog();
				if (outLYBankLogSet.size() == 0) {
					throw new NullPointerException("无银行日志数据！");
				}
				logger.debug("---End verifyUnitMoney---");
			}

			// 银行代付
			else if (mOperate.equals("")) {

			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PayReturnFromBankBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mInputData = new VData();

		try {
			map.put(outLYDupGetSet, "INSERT");
			map.put(outLYBankLogSet, "UPDATE");
			map.put(outDelLYReturnFromBankSet, "DELETE");
			map.put(outLYSendToBankBSet, "INSERT");
			map.put(outLJFIGetSet, "INSERT");
			map.put(outLJAGetSet, "UPDATE");
			map.put(outLYReturnFromBankBSet, "INSERT");
			map.put(outDelLYSendToBankSet, "DELETE");
			map.put(outLJAGetDrawSet, "UPDATE");
			map.put(outLJAGetEndorseSet, "UPDATE");
			map.put(outLJAGetTempFeeSet, "UPDATE");
			map.put(outLJAGetClaimSet, "UPDATE");
			map.put(outLJAGetOtherSet, "UPDATE");
			map.put(outLJABonusGetSet, "UPDATE");
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PayReturnFromBankBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		PayReturnFromBankUI PayReturnFromBankUI1 = new PayReturnFromBankUI();

		TransferData transferData1 = new TransferData();
		transferData1.setNameAndValue("totalMoney", "9990");

		LYReturnFromBankSchema tLYReturnFromBankSchema = new LYReturnFromBankSchema();
		tLYReturnFromBankSchema.setSerialNo("00000000000000010961");
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86110000";
		VData tVData = new VData();
		tVData.add(transferData1);
		tVData.add(tLYReturnFromBankSchema);
		tVData.add(tGlobalInput);

		if (!PayReturnFromBankUI1.submitData(tVData, "PAYMONEY")) {
			VData rVData = PayReturnFromBankUI1.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			logger.debug("Submit Succed!");
		}

		PayReturnFromBankBL payReturnFromBankBL1 = new PayReturnFromBankBL();
	}
}
