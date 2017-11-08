package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LZSysCertifyDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * *
 * <p>
 * Description: 回单处理
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author ---haopan
 * @CreateDate---2007-2-7
 * @version 1.0
 */
public class ContTakeBackBL implements BusinessService {
private static Logger logger = Logger.getLogger(ContTakeBackBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 全局数据 */
	private GlobalInput globalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String mOperation = "";
	/** ***保单号**** */
	private String mContno = "";
	private String mReasonCode = "";//延迟送达原因代码
	private String mReasonDesc = "";//延迟送达原因
	/** 业务处理相关变量 */
	private LZSysCertifySchema mLZSysCertifySchema = new LZSysCertifySchema();
	private MMap tmap = new MMap();

	public ContTakeBackBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据
		if (!getInputData(cInputData)) {
			return false;
		}
		// 检验数据
		if (!checkDate()) {

			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备数据
		VData vData = new VData();
		if (!prepareOutputData(vData)) {
			return false;
		}
		// 提交数据
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(vData, "")) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLZSysCertifySchema.setSchema((LZSysCertifySchema) cInputData
				.getObjectByObjectName("LZSysCertifySchema", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mContno = mLZSysCertifySchema.getCertifyNo();
		if (mTransferData != null)// 批量回单不传contno
		{
			mContno = (String) mTransferData.getValueByName("contno");
			mReasonCode = (String) mTransferData.getValueByName("reasoncode");
			mReasonDesc = (String) mTransferData.getValueByName("reasondesc");
		}

		return true;
	}

	/**
	 * 进行校验
	 * 
	 * @return boolean
	 */

	private boolean checkDate() {
		if (mLZSysCertifySchema.getTakeBackDate() == null
				|| mLZSysCertifySchema.getTakeBackDate().equals("")) {
			buildError("checkDate", "请录入回单日期！");
			return false;
		}
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLZSysCertifySchema.getCertifyNo());
		tLCContDB.getInfo();
		int date = PubFun.calInterval(tLCContDB.getSignDate(),
				mLZSysCertifySchema.getTakeBackDate(), "D");
		String CurDate = PubFun.getCurrentDate();
		int date2 = PubFun.calInterval(CurDate, mLZSysCertifySchema
				.getTakeBackDate(), "D");
		logger.debug("Cvalidate=" + tLCContDB.getSignDate());
		logger.debug("TakeBackDate="
				+ mLZSysCertifySchema.getTakeBackDate());
		logger.debug("date==" + date);
		if (date < 0) {
			buildError("checkDate", "回单日期早于保单出单日期，不能进行回单操作！");
			return false;
		}
		logger.debug("date2:" + date2);
		if (date2 > 0) {
			buildError("checkDate", "回单日期晚于当前日期，不能进行回单操作！");
			return false;
		}
		//added by ln 2010-04-09 回执签收日期距保单签发日期大于10日必须录入延迟原因
		if (date > 10 && (mReasonCode==null || mReasonCode.equals("") ||mReasonDesc==null||mReasonDesc.equals(""))) {
			buildError("checkDate", "回执签收日期距保单签发日期大于10日，请录入延迟原因！");
			return false;
		}
		// 校验业务员和机构
		if (!CertifyFunc.isComsExist2(mLZSysCertifySchema.getSendOutCom(),
				mLZSysCertifySchema.getReceiveCom())) {
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		String CurDate = PubFun.getCurrentDate();
		String CurTime = PubFun.getCurrentTime();

		// //////////////先更新LCCont
		String TakeBackSQl = "update lccont set GetPolDate='" + "?CurDate?"
				+ "',GetPolTime='" + "?CurTime?" + "'," + " CustomGetPolDate='"
				+ "?CustomGetPolDate?" + "' "
				+",ModifyDate='" + "?CurDate?" + "',ModifyTime='" + "?CurTime?" + "'"
				+",DelayReasonCode='" + "?DelayReasonCode?" + "',DelayReasonDesc='" + "?DelayReasonDesc?" + "'"
				+ "  where contno='" + "?contno?" + "' ";
		/*
		 * 此处增加修改P表数据，增加原因：2010-4-21出现一个BUG lzsyscertify表中有记录 但lccont表中
		 * 的getpoldate customergetpoldate两个字段都没有值   经过查找后发现该单于17:30通过内部转办
		 * 申请的保全，此时回执还没有回收，而内部转办事不需要校验回执是否回收的，所以生成的P表中
		 * getpoldate customergetpoldate两个字段都为空   而该单17:50做的回执回收 而后保全确认进行PC
		 * 转换时会将没有回执回收日期的数据转为C表，有回收日期的数据转为P表
		 * */
		//为避免通过“内部转办”做的保全P表与C表间有差异，在此处同时修改P表
		String TakeBackSQlP = "update lpcont set GetPolDate='" + "?CurDate?"
				+ "',GetPolTime='" + "?CurTime?" + "'," + " CustomGetPolDate='"
				+ "?CustomGetPolDate?" + "' "
				+",DelayReasonCode='" + "?DelayReasonCode?" + "',DelayReasonDesc='" + "?DelayReasonDesc?" + "'"
				+ "  where contno='" + "?contno?" + "' ";
		logger.debug("TakeBackSQl====" + TakeBackSQl);
		
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(TakeBackSQl);
		sqlbv.put("CurDate", CurDate);
		sqlbv.put("CurTime", CurTime);
		sqlbv.put("CustomGetPolDate", mLZSysCertifySchema.getTakeBackDate());
		sqlbv.put("DelayReasonCode", mReasonCode);
		sqlbv.put("DelayReasonDesc", mReasonDesc);
		sqlbv.put("contno", mContno);
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(TakeBackSQlP);
		sqlbv1.put("CurDate", CurDate);
		sqlbv1.put("CurTime", CurTime);
		sqlbv1.put("CustomGetPolDate", mLZSysCertifySchema.getTakeBackDate());
		sqlbv1.put("DelayReasonCode", mReasonCode);
		sqlbv1.put("DelayReasonDesc", mReasonDesc);
		sqlbv1.put("contno", mContno);
		ExeSQL tExeSQL = new ExeSQL();
		if (tExeSQL.execUpdateSQL(sqlbv) == false) {
			logger.debug("直接更新lccont失败");

			buildError("dealData", "更新保单表失败！");
			return false;
		}
		tmap.put(sqlbv, "UPDATE");
		tmap.put(sqlbv1, "UPDATE");

		// 更新lzsyscertify
		LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
		LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();
		LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		tLZSysCertifyDB.setCertifyNo(mLZSysCertifySchema.getCertifyNo());
		tLZSysCertifyDB.setCertifyCode(mLZSysCertifySchema.getCertifyCode());
		tLZSysCertifySet = tLZSysCertifyDB.query();
		if (tLZSysCertifySet != null && tLZSysCertifySet.size() > 0) {
			tLZSysCertifySchema = tLZSysCertifySet.get(1);
		}
		tLZSysCertifySchema
				.setCertifyCode(mLZSysCertifySchema.getCertifyCode());
		tLZSysCertifySchema.setCertifyNo(mLZSysCertifySchema.getCertifyNo());
		tLZSysCertifySchema.setReceiveCom(mLZSysCertifySchema.getSendOutCom());
		tLZSysCertifySchema.setSendOutCom(mLZSysCertifySchema.getReceiveCom());
		String szSendNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun
				.getNoLimit(globalInput.ComCode.substring(1)));

		String szTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun
				.getNoLimit(globalInput.ComCode.substring(1)));
		tLZSysCertifySchema.setSendNo(szSendNo);
		tLZSysCertifySchema.setTakeBackNo(szTakeBackNo);
		tLZSysCertifySchema.setStateFlag("1");
		tLZSysCertifySchema.setOperator(globalInput.Operator);
		tLZSysCertifySchema.setMakeDate(CurDate);
		tLZSysCertifySchema.setMakeTime(CurTime);
		tLZSysCertifySchema.setModifyDate(CurDate);
		tLZSysCertifySchema.setModifyTime(CurTime);
		tLZSysCertifySchema.setTakeBackDate(mLZSysCertifySchema
				.getTakeBackDate());
		tLZSysCertifySchema.setTakeBackMakeDate(CurDate);
		tLZSysCertifySchema.setTakeBackMakeTime(CurTime);
		tLZSysCertifySchema.setTakeBackOperator(globalInput.Operator);
		tmap.put(tLZSysCertifySchema, "DELETE&INSERT");

		return true;
	}

	// 向后面的提交准备数据
	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(tmap);
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "SysCertTakeBackBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
	
	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		GlobalInput tempGI = new GlobalInput();
		LZSysCertifySchema schemaLZSysCertify = new LZSysCertifySchema();
		schemaLZSysCertify.setCertifyCode("9995");
		schemaLZSysCertify.setCertifyNo("000000167600");
		schemaLZSysCertify.setTakeBackOperator("001");
		schemaLZSysCertify.setTakeBackDate("2007-08-09");
		schemaLZSysCertify.setSendOutCom("D35000337");
		schemaLZSysCertify.setReceiveCom("A86320700");
		TransferData mTransferData = new TransferData();
		String tContno = "000000167600";
		mTransferData.setNameAndValue("contno", tContno);
		tempGI.ComCode = "86";
		tempGI.ManageCom = "86";
		tempGI.Operator = "001";
		// 准备传输数据 VData
		VData vData = new VData();
		vData.addElement(tempGI);
		vData.addElement(schemaLZSysCertify);
		vData.addElement(mTransferData);

		// 数据传输
		ContTakeBackBL tContTakeBackBL = new ContTakeBackBL();
		tContTakeBackBL.submitData(vData, "");

	}

}
