package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSGetDrawDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LMDutyGetAliveDB;
import com.sinosoft.lis.db.LMDutyGetDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LMDutyGetAliveSchema;
import com.sinosoft.lis.schema.LMDutyGetSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单催付处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ck
 * @version 1.0
 */
public class PersonPayPlanBL {
private static Logger logger = Logger.getLogger(PersonPayPlanBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往后面传输数据的容器 */
	private VData mResult;

	/** 数据操作字符串 */
	private String mOperate;

	/** 控制信息传输类 */
	private TransferData mTransferData = new TransferData();

	/** 参数描述 */
	private String mTimeStart;

	private String mTimeEnd;

	private Date mTimeEndDate;

	private String mSerialNo="";

	/** 给付至日期 */
	private Date mGetToDate;

	/** 上次给付至日期 */
	private Date mLastGettoDate;

	/** 销户标志 */
	private String mCancelFlag;

	private String mCalFlag;

	/** 业务处理相关变量 */
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mOperator;

	private String mManageCom;

	private LCPolSchema inLCPolSchema = new LCPolSchema();

	private LCGetSchema inLCGetSchema = new LCGetSchema();

	private LCGetSchema mLCGetSchema = new LCGetSchema();

	private LCGetSet mLCGetSet = new LCGetSet();

	private LJSGetSet mLJSGetSet = new LJSGetSet();

	private LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();

	private LMDutyGetAliveSchema mLMDutyGetAliveSchema = new LMDutyGetAliveSchema();

	private LMDutyGetSchema mLMDutyGetSchema = new LMDutyGetSchema();

	private LMDutySchema mLMDutySchema = new LMDutySchema();

	private LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();

	private FDate tFDate = new FDate();

	private String mDutyCode = "";
	
	private int mCount=0;
	
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	public PersonPayPlanBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("==> Begin to PersonPayPlanBL");

		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("==> End InputData");

		// 进行业务处理,提交催付数据
		if (!dealData(cInputData)) {
			CError.buildErr(this, "数据处理失败PersonPayPlanBL-->dealData!");
			return false;
		}
		mResult = new VData();
		mResult.addElement(mSerialNo);
		mResult.addElement(String.valueOf(this.mCount));

		mInputData = null;

		return true;
	}

	/**
	 * 初始化查询条件
	 * 
	 * @param Schema
	 * @return
	 */
	private String initCondGet(SQLwithBindVariables sqlbv) {
		String aSql = "select * from LCGet where 1 = 1";
		logger.debug("inLCGetSchema.getManageCom:"+inLCGetSchema.getManageCom() +"    mManageCom:"+mManageCom);

		if ((inLCGetSchema.getManageCom() == null)
				|| inLCGetSchema.getManageCom().trim().equals("")) {
			//aSql = aSql + " and ManageCom like '" + mManageCom + "%'";
			//modify by jiaqiangli 2009-06-08
			aSql = aSql + " and ManageCom like concat('?ManageCom?','%')";	
			sqlbv.put("ManageCom", mGlobalInput.ManageCom);
		} else {
			aSql = aSql + " and ManageCom like concat('?ManageCom?','%')";
			sqlbv.put("ManageCom", inLCGetSchema.getManageCom());
		}

		if ((inLCGetSchema.getContNo() != null)
				&& !inLCGetSchema.getContNo().trim().equals("")) {
			aSql = aSql + " and ContNo= '?ContNo?'";
			sqlbv.put("ContNo", inLCGetSchema.getContNo());
		}

		if ((inLCGetSchema.getPolNo() != null)
				&& !inLCGetSchema.getPolNo().trim().equals("")) {
			aSql = aSql + " and PolNo= '?PolNo?'";
			sqlbv.put("PolNo", inLCGetSchema.getPolNo());
		}

		// if ((inLCGetSchema.getAppntNo() != null)
		// && !inLCGetSchema.getAppntNo().trim().equals("")) {
		// aSql = aSql + " and AppntNo= '" + inLCGetSchema.getAppntNo() + "'";
		// }

		if ((inLCGetSchema.getInsuredNo() != null)
				&& !inLCGetSchema.getInsuredNo().trim().equals("")) {
			aSql = aSql + " and InsuredNo= '?InsuredNo?'";
			sqlbv.put("InsuredNo", inLCGetSchema.getInsuredNo());
		}

		if (!((mTimeEnd == null) && mTimeEnd.trim().equals(""))) {
			aSql = aSql + " and gettodate<='?mTimeEnd1?' and gettodate>='?mTimeStart?' and getenddate>='?mTimeStart?'";
			sqlbv.put("mTimeEnd1", mTimeEnd);
			sqlbv.put("mTimeStart", mTimeStart);
		}

		// LiveGetType=0（生存给付）,UrgeGetFlag Y--发催付 
		// 无垫款，无借款，不能失效，满期终止后的保单允许催付
		// 由于MS生存金也做清算，所以有垫款、借款和失效的都可以完成催付，但是不能领取
		// add by jiaqiangli 2009-03-31 排除团险
		aSql += " and grpcontno = '00000000000000000000' and LiveGetType='0' and UrgeGetFlag = 'Y' "
			+ " and ((getintv =0 and summoney=0) or getintv>0) "//add by jiaqiangli 趸领的只允许催付一次，即便催付的金额为0
			//+ " and not exists(select 'C' from LCContState where ContNo=LCGet.ContNo and StateType='Loan' and State='1' and EndDate is null)"
			//+ " and not exists(select 'V' from LCContState where PolNo=LCGet.PolNo and StateType='Available' and State='1' and EndDate is null)"
			//+ " and not exists(select 'B' from LCContState where PolNo=LCGet.PolNo and StateType='PayPrem' and State='1' and EndDate is null)"
			// add by jiaqiangli 2009-04-08 保全挂起不允许催付 其他如理赔挂起可以催付
			+ " and not exists(select 'X' from lcconthangupstate where posflag = '1' and hanguptype = '2' and contno = LCGet.contno) "
			+ " and exists(select 'X' from LCPol where conttype='1' and PolNo=LCGet.PolNo and (AppFlag='1' or (AppFlag='4' and exists(select 'T' from LCContState where PolNo=LCPol.PolNo and StateType='Terminate' and State='1' and StartDate <= '?mTimeEnd2?' and EndDate is null and StateReason in ('01')) or UrgeGetFlag = 'L')))"
			// add by jiaqiangli 2009-04-09 use as testcase
			//+ " and rownum <= 1 "
			;
		sqlbv.put("mTimeEnd2", mTimeEnd);
		logger.debug("==> 查询LCGet表SQL：" + aSql);

		return aSql;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	public boolean dealData(VData tVDate) {
		LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
		LJSGetSet tLJSGetSet = new LJSGetSet();

		// 生成批次号
		mSerialNo = PubFun1.CreateMaxNo("SERIALNO", PubFun
				.getNoLimit(mManageCom));
		logger.debug("==> serialNo:" + mSerialNo);

		// 初始化催付查询条件
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		String condSql = initCondGet(sbv)
				+ " order by ContNo,PolNo,getdutycode,dutycode";
		sbv.sql(condSql);
		LCGetDB tLCGetDB = new LCGetDB();
		mLCGetSet = tLCGetDB.executeQuery(sbv);
		logger.debug("有" + mLCGetSet.size() + "条符合条件的给付数据!");

		// 每次循环处理一个ContNo
		for (int j = 1; j <= mLCGetSet.size(); j++) {
			mLJSGetSet.clear();
			mLJSGetDrawSet.clear();

			String thisContNo = mLCGetSet.get(j).getContNo();
			if (j > 1 && thisContNo.equals(mLCGetSet.get(j - 1).getContNo())) {
				continue;// 每次循环处理一个ContNo
			}
			
			//从这里表示要处理一个contno
			//开始加锁
			//BQCF01 保全确认并发控制组
			try {
				
			if (mPubLock.lock(thisContNo, "CFU001", mGlobalInput.Operator) == false) {
				logger.debug(thisContNo+"催付并发加锁失败！");
				continue;
			}

			mLCGetSchema.setSchema(mLCGetSet.get(j));
			// mManageCom = mLCGetSchema.getManageCom();
			// mManageCom = "86";

			// 处理当前循环下ContNo下所有数据
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			String strsql = initCondGet(sbv1) + " and ContNo='?thisContNo?' order by ContNo,PolNo,getdutycode,dutycode";
			sbv1.sql(strsql);
			sbv1.put("thisContNo", thisContNo);
			logger.debug("strsql:" + strsql);

			LCGetSet tempLCGetSet = new LCGetSet();
			LCGetDB tempLCGetDB = new LCGetDB();
			tempLCGetSet = tempLCGetDB.executeQuery(sbv1);
			logger.debug("tempLCGetSet.size=" + tempLCGetSet.size());

			// 生成相应生存领取应付子表信息
			for (int k = 1; k <= tempLCGetSet.size(); k++) {
				LCGetSchema tempLCGetSchema = new LCGetSchema();
				tempLCGetSchema = tempLCGetSet.get(k);

				// 校验各种限制条件同时准备数据
				if (!this.checkDataToGet(tempLCGetSchema)) {
//					CError.buildErr(this, "保单" + tempLCGetSchema.getPolNo()
//							+ "校验失败!");
					continue;
				}
				mLJSGetDrawSet.clear();
				// 生成给付子表的信息,若有多期给付责任则生成相应多条催付记录
				tLJSGetDrawSet.clear();
				tLJSGetDrawSet = this.getLJSGetDraw(tempLCGetSchema);
				if (tLJSGetDrawSet.size() < 1) {
					CError.buildErr(this, "保单" + mLCPolSchema.getPolNo()
							+ "生成给付子表的信息失败!");
					continue;
				}
				
				mLJSGetDrawSet.add(tLJSGetDrawSet);
			

			if (tLJSGetDrawSet == null || tLJSGetDrawSet.size() <= 0) {
				CError tError = new CError();
				tError.moduleName = "PersonPayPlanBL";
				tError.functionName = "dealData()-";
				tError.errorMessage = "保单校验失败,没有符合条件的催付保单";
				this.mErrors.addOneError(tError);
				// CError.buildErr(this, "保单校验失败,没有符合条件的催付保单!");
//				return false;
				continue;
			}

			// 生成相应生存领取应付总表信息
			logger.debug("准备总表数据");
			
			tLJSGetSet = this.getLJSGet(mLJSGetDrawSet);
			mLJSGetSet.clear();
			mLJSGetSet.add(tLJSGetSet);

			logger.debug("准备打印管理表数据");
			tLOPRTManagerSet.clear();
			tLOPRTManagerSet = this.getPrintData(mLJSGetSet, tempLCGetSchema);

			// 准备往后台的数据
			if (!prepareData()) {
//				return false;
				continue;
			}
			logger.debug("==> End PrepareData");

			logger.debug("Start PersonPayPlan BLS Submit...");

			PersonPayPlanBLS tPersonPayPlanBLS = new PersonPayPlanBLS();
			tPersonPayPlanBLS.submitData(mInputData, mOperate);
			
			logger.debug("End PersonPayPlan BLS Submit...");

			// 如果有需要处理的错误，则返回
			if (tPersonPayPlanBLS.mErrors.needDealError()) {
				CError.buildErr(this, "数据提交失败!", tPersonPayPlanBLS.mErrors);
//				return false;
				continue;
			}
			for (int n = 1; n <= mLJSGetSet.size(); n++) {
				LJSGetSchema stateLJSGetSchema = new LJSGetSchema();
				stateLJSGetSchema = mLJSGetSet.get(n);
//			 日志监控,状态监控                 		
 			PubFun.logState(mGlobalInput,stateLJSGetSchema.getGetNoticeNo(), "生存金(通知书号：" +stateLJSGetSchema.getGetNoticeNo()+ ")催付成功","2");
			}
			mCount++;
		}
			}
			finally {
				//注意解锁
				mPubLock.unLock();
			}
			//催付完成后判断系统时间是否大于等于应领时间，如果大于或者等于，直接入帐户
//			for (int n=1;n<=tLJSGetSet.size();n++)
//			{
//				LJSGetSchema tLJSGetSchema = tLJSGetSet.get(n);
//				String  currentdate=PubFun.getCurrentDate();
//				if(tFDate.getDate(tLJSGetSchema.getGetDate()).compareTo(tFDate.getDate(currentdate))<=0)
//				{
//					TransferData tTransferData = new TransferData();
//					tTransferData.setNameAndValue("GetNoticeNo", tLJSGetSchema.getGetNoticeNo());
//					VData tVData = new VData();
//					tVData.add(tTransferData);
//					tVData.add(mGlobalInput);
//					PersonPaytoAccBL tPersonPaytoAccBL = new PersonPaytoAccBL();
//					if(!tPersonPaytoAccBL.submitData(tVData, ""))
//					{
//					    CError.buildErr(this, "生存金如帐户失败,原因是:"+tPersonPaytoAccBL.mCErrors.getFirstError());
//						return false;
//					}
//				}
//				else
//				{
//					logger.debug("尚未到应领日期，不入帐户！");
//					return true;
//				}
//			}
		}
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共催付产生"+mCount+"笔生存金");
		
		for (int j = 1; j <= mLCGetSet.size(); j++) 
		{

			String thisContNo = mLCGetSet.get(j).getContNo();
			if (j > 1 && thisContNo.equals(mLCGetSet.get(j - 1).getContNo())) {
				continue;// 每次循环处理一个ContNo
			}
			
			//从这开始处理一个保单号，进行加锁处理
			try {
				
				if (mPubLock.lock(thisContNo, "CFU001", mGlobalInput.Operator) == false) {
					logger.debug(thisContNo+"生存金入帐户并发加锁失败！");
					continue;
				}
			
			String ljsget_sql = "select * from ljsget  where getnoticeno in (select getnoticeno from ljsgetdraw where contno='?thisContNo?') order by getdate ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(ljsget_sql);
			sqlbv.put("thisContNo", thisContNo);
			LJSGetSet temp_LJSGetSet = new LJSGetSet();
			LJSGetDB tLJSGetDB = new LJSGetDB();
			
			temp_LJSGetSet = tLJSGetDB.executeQuery(sqlbv);
			for(int i=1;i<=temp_LJSGetSet.size();i++)
			{
				LJSGetSchema tLJSGetSchema = temp_LJSGetSet.get(i);
				String  currentdate=PubFun.getCurrentDate();
				if(tFDate.getDate(tLJSGetSchema.getGetDate()).compareTo(tFDate.getDate(currentdate))<=0)
				{ 
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("GetNoticeNo", tLJSGetSchema.getGetNoticeNo());
					VData tVData = new VData();
					tVData.add(tTransferData);
					tVData.add(mGlobalInput);
					PersonPaytoAccBL tPersonPaytoAccBL = new PersonPaytoAccBL();
					if(!tPersonPaytoAccBL.submitData(tVData, ""))
					{
					    CError.buildErr(this, "生存金入帐户失败,原因是:"+tPersonPaytoAccBL.mErrors.getFirstError());
//						return false;
					    continue;
					}
				}
				else
				{
					logger.debug("尚未到应领日期，不入帐户！");
//					return true;
					continue;
				}
			}
			}
			finally {
				//注意解锁
				mPubLock.unLock();
			}
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		inLCPolSchema.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0));
		inLCGetSchema.setSchema((LCGetSchema) cInputData.getObjectByObjectName(
				"LCGetSchema", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mTimeEnd = (String) mTransferData.getValueByName("timeEnd");
		mTimeStart = (String) mTransferData.getValueByName("timeStart");

		// 如果没传入催付开始日期，则默认为MS开业日
		if ((mTimeStart == null) || mTimeStart.equals("")) {
			mTimeStart = "2003-01-01";
		}

		if ((mTimeEnd == null) || mTimeEnd.equals("")) {
			CError.buildErr(this, "催付至日期不能为空!");
			return false;
		}

		// 规范字符串类型的日期，下面是把"2005-6-1"转为"2005-06-01",便与后面日期的比较
		// 也可以选择都使用日期类型比较，而不是字符串类型
		mTimeEndDate = tFDate.getDate(mTimeEnd);
		mTimeEnd = tFDate.getString(mTimeEndDate);
		logger.debug("催付至日期为：" + mTimeEnd);

		// 记录操作员
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	public boolean getEdorData(VData cInputData) {
		mLCGetSchema.setSchema((LCGetSchema) cInputData.getObjectByObjectName(
				"LCGetSchema", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mTimeEnd = (String) mTransferData.getValueByName("timeEnd");

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareData() {
		mInputData = new VData();

		try {
			mInputData.add(this.mLJSGetDrawSet);
			mInputData.add(this.mLJSGetSet);
			mInputData.add(this.tLOPRTManagerSet);
			mInputData.add(this.mGlobalInput);

//			if ((mLJSGetSet != null) && (mLJSGetSet.size() > 0)) {
//				mResult = new VData();
//				mResult.addElement(mSerialNo);
//				mResult.addElement(String.valueOf(mLJSGetSet.size()));
//			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "LFGetPayBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// 领取时给付校验
	private boolean checkDataToGet(LCGetSchema aLCGetSchema) {
		logger.debug("开始处理保单" + aLCGetSchema.getPolNo() + "下的"
				+ aLCGetSchema.getGetDutyCode() + "给付责任!");

		// 判断该给付责任是否已经催付
		String strSQL = "select * from LJSGetDraw where polno='?polno?' and dutycode='?dutycode?' and getdutycode='?getdutycode?' and getdutykind='?getdutykind?' order by curgettodate desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("polno", aLCGetSchema.getPolNo());
		sqlbv1.put("dutycode", aLCGetSchema.getDutyCode());
		sqlbv1.put("getdutycode", aLCGetSchema.getGetDutyCode());
		sqlbv1.put("getdutykind", aLCGetSchema.getGetDutyKind());
		logger.debug("strSQL:" + strSQL);
		LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
		LJSGetDrawSet tLJSGetDrawSet = tLJSGetDrawDB.executeQuery(sqlbv1);

		if (tLJSGetDrawSet.size() > 0) {
			// 取得最后一次催付的GetToDate
			this.setGetToDate(tFDate.getDate(tLJSGetDrawSet.get(1)
					.getCurGetToDate()));
			logger.debug("getGetToDate():" + this.getGetToDate());
			logger.debug("mTimeEndDate:" + mTimeEndDate);

			if (this.getGetToDate().after(mTimeEndDate)) {
				// 催至日期超过催付日期
				CError.buildErr(this, "保单(" + aLCGetSchema.getPolNo()
						+ ")催付数据已经存在!");
				return false;
			}

			// 判断是否是趸领，如果是则返回错误 add by ck
			if (aLCGetSchema.getGetIntv() == 0) {
				CError.buildErr(this, "保单(" + aLCGetSchema.getPolNo()
						+ ")催付数据已经存在!");
				return false;
			}
		} else {
			this.setGetToDate(tFDate.getDate(aLCGetSchema.getGettoDate()));
		}
		// logger.debug(this.getGetToDate());

		// 查询LCPol，并校验保单状态
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(aLCGetSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			CError.buildErr(this, "保单(" + aLCGetSchema.getPolNo() + ")查询失败!");
			return false;
		}
		mLCPolSchema.setSchema(tLCPolDB.getSchema());
		mManageCom = aLCGetSchema.getManageCom(); //由于存在lcpol管理机构和lcget管理机构不一致，所以此处必须修改为用lcget的管理机构
//		if (!mLCPolSchema.getAppFlag().trim().equals("1")) {
//			CError.buildErr(this, "保单(" + aLCGetSchema.getPolNo() + ")没有签单!");
//			return false;
//		}
//		if (!mLCPolSchema.getPolState().trim().substring(0, 2).equals("00")) {
//			CError
//					.buildErr(this, "保单(" + aLCGetSchema.getPolNo()
//							+ ")不在有效状态内!");
//			return false;
//		}

		// 查询被保人信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mLCPolSchema.getContNo());
		tLCInsuredDB.setInsuredNo(mLCPolSchema.getInsuredNo());
		if (!tLCInsuredDB.getInfo()) {
			CError.buildErr(this, "保单(" + mLCPolSchema.getPolNo()
					+ ")主被保人信息不足无法计算!");
			return false;
		}
		mLCInsuredSchema.setSchema(tLCInsuredDB.getSchema());

		// 把非标准的给付责任转换为标准6位给付责任
		if (aLCGetSchema.getDutyCode().trim().length() > 6) {
			mDutyCode = aLCGetSchema.getDutyCode().trim().substring(0, 6);
		} else {
			mDutyCode = aLCGetSchema.getDutyCode();
		}

		// 有垫交且未还清的保单不允许做催付
//		LOLoanSet tLOLoanSet = new LOLoanSet();
//		LOLoanDB tLOLoanDB = new LOLoanDB();
//		tLOLoanSet = tLOLoanDB
//				.executeQuery("select * from LOLoan where polno='"
//						+ aLCGetSchema.getPolNo()
//						+ "' and payoffflag='0' and loantype='1' and leavemoney<>0");
//		if (tLOLoanSet.size() > 0) {
//			CError.buildErr(this, "该保单有理垫交且未还清垫交款项!");
//			return false;
//		}

//		// 死亡报案的不进行催付
//		LDSysTraceSet tLDSysTraceSet = new LDSysTraceSet();
//		LDSysTraceDB tLDSysTraceDB = new LDSysTraceDB();
//		tLDSysTraceDB.setPolNo(aLCGetSchema.getPolNo());
//		tLDSysTraceDB.setPolState("4001");
//		tLDSysTraceDB.setValiFlag("1");
//		tLDSysTraceSet = tLDSysTraceDB.query();
//		if (tLDSysTraceSet.size() > 0) {
//			CError.buildErr(this, "该保单有理赔死亡报案!");
//			return false;
//		}

		// 查询LMDuty
		LMDutyDB tLMDutyDB = new LMDutyDB();
		tLMDutyDB.setDutyCode(mDutyCode);
		if (!tLMDutyDB.getInfo()) {
			CError.buildErr(this, "查询责任表失败!");
			return false;
		}
		mLMDutySchema.setSchema(tLMDutyDB.getSchema());

		// 取得计算给付信息的方式
		mCalFlag = mLMDutySchema.getCalMode();

		// 判断是否为生存给付责任,并准备给付责任数据
		LMDutyGetDB tLMDutyGetDB = new LMDutyGetDB();
		tLMDutyGetDB.setGetDutyCode(aLCGetSchema.getGetDutyCode());
		if (!tLMDutyGetDB.getInfo()) {
			CError.buildErr(this, "查询给付责任描述数据失败!");
			return false;
		}
		mLMDutyGetSchema.setSchema(tLMDutyGetDB.getSchema());

		// 检查生存给付描述表是否存在,并准备给付责任数据。
		LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
		tLMDutyGetAliveDB.setGetDutyCode(aLCGetSchema.getGetDutyCode());
		tLMDutyGetAliveDB.setGetDutyKind(aLCGetSchema.getGetDutyKind());
		if (!tLMDutyGetAliveDB.getInfo()) {
			CError.buildErr(this, "无生存给付描述数据");
			return false;
		}
		mLMDutyGetAliveSchema.setSchema(tLMDutyGetAliveDB.getSchema());

		/*
		 * //校验责任领取条件 if (mLMDutyGetAliveSchema.getGetCond().equals("0")) { if
		 * (mLCPolSchema.getPayEndDate().compareTo(aLCGetSchema.getGetStartDate()) <
		 * 0) { CError.buildErr(this, "该责任不满足领取条件(交费不足)!");
		 * 
		 * return false; } }
		 */
		return true;
	}

	// 取得生存领取子表记录
	public LJSGetDrawSet getLJSGetDraw(LCGetSchema aLCGetSchema) {
		LJSGetDrawSet rLJSGetDrawSet = new LJSGetDrawSet();

		double aGetMoney = 0;
		BqCalBase aBqCalBase = new BqCalBase();

		if (aLCGetSchema.getGetIntv() == 0) {
			// 趸领生存给付计算
			if (aLCGetSchema.getSumMoney() <= 0) {
				if ((mLMDutyGetAliveSchema.getNeedReCompute() == null)
						|| !mLMDutyGetAliveSchema.getNeedReCompute()
								.equals("1")) {
					aGetMoney = aLCGetSchema.getStandMoney();// 不需要重新计算，直接去标准给付金额
				} else {
					// 该给付责任在领取时需要重新计算,现在只有增额交清的红利在给付时需要重新计算
					// 查找计算 CalCode
					String mCalCode = null;

					if (StrTool.cTrim(mCalFlag).equals("P")) {
						mCalCode = mLMDutyGetAliveSchema.getCalCode(); // 保费算保额
					}

					if (StrTool.cTrim(mCalFlag).equals("G")) {
						mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 保额算保费
					}

					if (StrTool.cTrim(mCalFlag).equals("O")) {
						mCalCode = mLMDutyGetAliveSchema.getOthCalCode(); // 其他算保费
					}

					if (StrTool.cTrim(mCalFlag).equals("A")) {
						mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 保费、保额互算
					}

					if (StrTool.cTrim(mCalFlag).equals("I")) {
						mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 录入保费保额
					}

					if ((mCalCode == null) || mCalCode.equals("")) {
						logger.debug("保单" + aLCGetSchema.getPolNo()
								+ "没有重算公式");
						// return 0;
					}

					// 重新计算给付金额
					aBqCalBase = new BqCalBase();
					aBqCalBase = initCalBase(aLCGetSchema);
					logger.debug("aCalCode :" + mCalCode);

					BqCalBL tBqCalBL = new BqCalBL(aBqCalBase, mCalCode, "");
					aGetMoney = tBqCalBL.calGetDraw();
				}
			}

			this.setGetToDate(tFDate.getDate(aLCGetSchema.getGetStartDate()));
			mLastGettoDate = this.getGetToDate(); // 一定要有

			LJSGetDrawSchema tLJSGetDrawSchema = initLJSGetDraw(aGetMoney,
					aLCGetSchema);
			rLJSGetDrawSet.add(tLJSGetDrawSchema);
		} else {
			// 期领责任给付计算
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tLCGetSchema.setSchema(aLCGetSchema);

			// 循环处理多期催付数据
//			while (!this.getGetToDate().after((mTimeEndDate))
//					&& this.getGetToDate().before(
//							tFDate.getDate(tLCGetSchema.getGetEndDate())))
		     while (((mLMDutyGetAliveSchema.getMaxGetEndPeriod()==0)&&!this.getGetToDate().after(mTimeEndDate) &&
                     this.getGetToDate().before(tFDate.getDate(tLCGetSchema.getGetEndDate())))
                     //上半部分为富贵年年意外的险种
                     //下半部分为处理富贵年年中 ：生存保险金-60岁-B计划； 生存保险金-60岁（含）后-A计划
                     ||((mLMDutyGetAliveSchema.getMaxGetEndPeriod()==1)&& !this.getGetToDate().after(mTimeEndDate) &&
                             !this.getGetToDate().after(tFDate.getDate(tLCGetSchema.getGetEndDate()))))
			{
				logger.debug("GetToDate:"
						+ tFDate.getString(this.getGetToDate()));
				logger.debug("mTimeEndDate:"
						+ tFDate.getString(mTimeEndDate));
				aBqCalBase = new BqCalBase();
				aBqCalBase = initCalBase(tLCGetSchema);
				aGetMoney = 0;

				if (mLMDutyGetAliveSchema.getMaxGetCount() == 0) {
					if (tLCGetSchema.getGettoDate().compareTo(
							tLCGetSchema.getGetEndDate()) > 0) {
						this.setCancelFlag("1");
						break;
					}
				} else {
					// 无条件给付最大次数
					if (mLMDutyGetAliveSchema.getMaxGetCountType().equals("0")
							&& (aBqCalBase.getGetTimes().compareTo(
									String.valueOf(mLMDutyGetAliveSchema
											.getMaxGetCount())) > 0)) {
						this.setCancelFlag("1");
						break;
					}
					// 无条件给付最大年龄
					else if (mLMDutyGetAliveSchema.getMaxGetCountType().equals(
							"1")
							&& (aBqCalBase.getGetAge().compareTo(
									String.valueOf(mLMDutyGetAliveSchema
											.getMaxGetCount())) > 0)) {
						this.setCancelFlag("1");
						break;
					}
					// 被保人死亡标志给付最大次数
					else if (mLCPolSchema.getDeadFlag().equals("1")
							&& mLMDutyGetAliveSchema.getMaxGetCountType()
									.equals("2")
							&& (aBqCalBase.getGetTimes().compareTo(
									String.valueOf(mLMDutyGetAliveSchema
											.getMaxGetCount())) > 0)) {
						this.setCancelFlag("1");
						break;
					}
					// 被保人死亡标志给付最大年龄
					else if (mLCPolSchema.getDeadFlag().equals("1")
							&& mLMDutyGetAliveSchema.getMaxGetCountType()
									.equals("3")
							&& (aBqCalBase.getGetAge().compareTo(
									String.valueOf(mLMDutyGetAliveSchema
											.getMaxGetCount())) > 0)) {
						this.setCancelFlag("1");
						break;
					}

					// 投保人死亡标志给付最大次数
					else if (mLCPolSchema.getDeadFlag().equals("2")
							&& mLMDutyGetAliveSchema.getMaxGetCountType()
									.equals("4")
							&& (aBqCalBase.getGetTimes().compareTo(
									String.valueOf(mLMDutyGetAliveSchema
											.getMaxGetCount())) > 0)) {
						this.setCancelFlag("1");
						break;
					}
					// 投保人死亡标志给付最大年龄
					else if (mLCPolSchema.getDeadFlag().equals("2")
							&& mLMDutyGetAliveSchema.getMaxGetCountType()
									.equals("5")
							&& (aBqCalBase.getGetAge().compareTo(
									String.valueOf(mLMDutyGetAliveSchema
											.getMaxGetCount())) > 0)) {
						this.setCancelFlag("1");
						break;
					} else {
						logger.debug("-----------no descriptions----");
					}
				}

				// 计算应领金额
				if ((mLMDutyGetAliveSchema.getNeedReCompute() == null)
						|| !mLMDutyGetAliveSchema.getNeedReCompute()
								.equals("1")) {// 不需要重新计算
					if (mLMDutyGetAliveSchema.getAddFlag().equals("N")) {// 非递增
						aGetMoney = aGetMoney + tLCGetSchema.getStandMoney();
					} else {
						aGetMoney = aGetMoney
								+ calAddGetMoney(aBqCalBase, tLCGetSchema);
					}
				} else {
					// 该给付责任在领取时需要重新计算,查找计算 CalCode
					String mCalCode = null;

					if (StrTool.cTrim(mCalFlag).equals("P")) {
						mCalCode = mLMDutyGetAliveSchema.getCalCode(); // 保费算保额
					}

					if (StrTool.cTrim(mCalFlag).equals("G")) {
						mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 保额算保费
					}

					if (StrTool.cTrim(mCalFlag).equals("O")) {
						mCalCode = mLMDutyGetAliveSchema.getOthCalCode(); // 其他算保费
					}

					if (StrTool.cTrim(mCalFlag).equals("A")) {
						mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 保费、保额互算
					}

					if (StrTool.cTrim(mCalFlag).equals("I")) {
						mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 录入保费保额
					}

					BqCalBL tBqCalBL = new BqCalBL(aBqCalBase, mCalCode, "");
					aGetMoney = aGetMoney + tBqCalBL.calGetDraw();
				}
				logger.debug("aGetMoney:" + aGetMoney);

				// 保存上次领至日期
				mLastGettoDate = this.getGetToDate();

				// 计算应领至日期
				tLCGetSchema.setGettoDate(this.calGetToDate(tLCGetSchema));
				this.setGetToDate(tFDate.getDate(tLCGetSchema.getGettoDate()));
				logger.debug("---oridate:" + tLCGetSchema.getGettoDate());

				LJSGetDrawSchema tLJSGetDrawSchema = initLJSGetDraw(aGetMoney,
						aLCGetSchema);
				rLJSGetDrawSet.add(tLJSGetDrawSchema);
			}
			logger.debug("------end while-----");
		}

		return rLJSGetDrawSet;
	}

	// 生成LJSGetDrawSchema
	public LJSGetDrawSchema initLJSGetDraw(double money,
			LCGetSchema aLCGetSchema) {
		LJSGetDrawSchema rLJSGetDrawSchema = new LJSGetDrawSchema();
		String tGetNoticeNo = "";
		rLJSGetDrawSchema.setGetMoney(ReportPubFun.functionJD(money, "0.00"));
		rLJSGetDrawSchema.setDestrayFlag(this.getContCancelFlag(this
				.getCancelFlag()));

		String CreateFlag = "1";
		// 对每个相同ContNo和GettoDate聚合，即通知书号GetNoticeNo相同
		for (int count = 1; count <= mLJSGetDrawSet.size(); count++) {
			if (aLCGetSchema.getContNo().equals(
					mLJSGetDrawSet.get(count).getContNo())
					&&aLCGetSchema.getGetDutyCode().equals(mLJSGetDrawSet.get(count).getGetDutyCode())
					&& tFDate.getString(mLastGettoDate).equals(
							mLJSGetDrawSet.get(count).getLastGettoDate())) {
				tGetNoticeNo = mLJSGetDrawSet.get(count).getGetNoticeNo();
				logger.debug("==> old GetNoticeNo is " + tGetNoticeNo);
				CreateFlag = "0";
			}
		}
		// 新生成通知书号
		if (CreateFlag.equals("1")) {
			logger.debug("==> Creat new GetNoticeNo is " + tGetNoticeNo);
			tGetNoticeNo = PubFun1.CreateMaxNo("GetNoticeNo", PubFun
					.getNoLimit(mLCPolSchema.getManageCom()));
		}

		rLJSGetDrawSchema.setSerialNo(mSerialNo);
		rLJSGetDrawSchema.setGetNoticeNo(tGetNoticeNo);
		rLJSGetDrawSchema.setDutyCode(aLCGetSchema.getDutyCode());
		rLJSGetDrawSchema.setGetDutyCode(aLCGetSchema.getGetDutyCode());
		rLJSGetDrawSchema.setGetDutyKind(aLCGetSchema.getGetDutyKind());
		rLJSGetDrawSchema.setGetDate(mLastGettoDate);
		rLJSGetDrawSchema.setLastGettoDate(mLastGettoDate);
		Date aGetDate = this.getGetToDate();
		logger.debug("==> GetDate:" + aGetDate);
		rLJSGetDrawSchema.setCurGetToDate(aGetDate);
		rLJSGetDrawSchema.setCurrency(aLCGetSchema.getCurrency());
		// 获取FinaTyp
		String sysType = "";
//		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
//		tLMRiskAppDB.setRiskCode(mLCPolSchema.getRiskCode());
//		if (!tLMRiskAppDB.getInfo()) {
//			CError.buildErr(this, "险种" + mLCPolSchema.getRiskCode() + "无描述数据");
//			return null;
//		}
//		if ("0".equals(tLMRiskAppDB.getHealthType())) {// 0为满期给付
//			sysType = "MQJJF";
//		} else if ("1".equals(tLMRiskAppDB.getHealthType())) {// 1为年金
//			sysType = "NJJF";
//		} else {
//			CError.buildErr(this, "险种" + mLCPolSchema.getRiskCode()
//					+ "未描述健康委托产品分类");
//			return null;
//		}
		LMDutyGetDB tLMDutyGetDB = new LMDutyGetDB();
		tLMDutyGetDB.setGetDutyCode(aLCGetSchema.getGetDutyCode());
		if(!tLMDutyGetDB.getInfo())
		{
			CError.buildErr(this, "给付责任信息获取失败！");
			return null;
		}
		if(tLMDutyGetDB.getGetType1().equals("1"))
		{
			sysType = "NJJF";
		}
		else if (tLMDutyGetDB.getGetType1().equals("0"))
		{
			sysType = "MQJJF";
		}
		else
		{
			CError.buildErr(this, "给付责任财务信息获取失败！");
			return null;
		}
		String finType = BqCalBL.getFinType("AG", sysType, rLJSGetDrawSchema
				.getPolNo());
		rLJSGetDrawSchema.setFeeFinaType(finType);
		rLJSGetDrawSchema.setFeeOperationType("AG");

		// 是否首期给付
		if (aLCGetSchema.getGetStartDate().equals(aLCGetSchema.getGettoDate())) {
			rLJSGetDrawSchema.setGetFirstFlag("1");
		} else {
			rLJSGetDrawSchema.setGetFirstFlag("0");
		}

		// 给付渠道
		rLJSGetDrawSchema.setGetChannelFlag("1");
		rLJSGetDrawSchema.setAppntNo(mLCPolSchema.getAppntNo());
		rLJSGetDrawSchema.setGrpName(mLCPolSchema.getAppntName());
		rLJSGetDrawSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
		rLJSGetDrawSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
		rLJSGetDrawSchema.setContNo(mLCPolSchema.getContNo());
		rLJSGetDrawSchema.setPolNo(mLCPolSchema.getPolNo());
		rLJSGetDrawSchema.setPolType("1");
		rLJSGetDrawSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
		rLJSGetDrawSchema.setAgentCode(mLCPolSchema.getAgentCode());
		rLJSGetDrawSchema.setAgentCom(mLCPolSchema.getAgentCom());
		rLJSGetDrawSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
		rLJSGetDrawSchema.setAgentType(mLCPolSchema.getAgentType());
		rLJSGetDrawSchema.setRiskCode(mLCPolSchema.getRiskCode());
		rLJSGetDrawSchema.setKindCode(mLCPolSchema.getKindCode());
		rLJSGetDrawSchema.setRiskVersion(mLCPolSchema.getRiskVersion());

		rLJSGetDrawSchema.setMakeDate(PubFun.getCurrentDate());
		rLJSGetDrawSchema.setMakeTime(PubFun.getCurrentTime());
		rLJSGetDrawSchema.setModifyDate(PubFun.getCurrentDate());
		rLJSGetDrawSchema.setModifyTime(PubFun.getCurrentTime());
		rLJSGetDrawSchema.setOperator(mOperator);
		rLJSGetDrawSchema.setManageCom(mManageCom);
		rLJSGetDrawSchema.setCurrency(aLCGetSchema.getCurrency());
		return rLJSGetDrawSchema;
	}

	/**
	 * 准备打印数据
	 * 
	 * @param tLCPolSchema
	 * @return
	 */
	public LOPRTManagerSet getPrintData(LJSGetSet tLJSGetSet,
			LCGetSchema tLCGetSchema) {
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();

		for (int i = 1; i <= tLJSGetSet.size(); i++) {
			LJSGetSchema tLJSGetSchema = new LJSGetSchema();
			tLJSGetSchema = tLJSGetSet.get(i);
			ExeSQL tExeSQL = new ExeSQL();
			// 月领只在保单对应日打印催付清单
			if (tLCGetSchema.getGetIntv() == 1) {
				String strsql = "select floor(months_between('?Date1?','?Date2?')) from dual";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(strsql);
				sqlbv2.put("Date1", tLJSGetSchema.getGetDate());
				sqlbv2.put("Date2", tLJSGetSchema.getStartGetDate());
				logger.debug("strsql:" + strsql);
				
				String month = tExeSQL.getOneValue(sqlbv2);
				logger.debug("month:" + month);
				if (!month.equals("") && ((Integer.parseInt(month) % 12) != 0)) {
					logger.debug("月领但不需打印领取清单!");
					continue;
				}
			}
			//如果是增额交清产生的生存金不打印催付通知书
			if(tLCGetSchema.getDutyCode().length()==10)
			{
				continue;
			}
			
			
			LCBnfDB tLCBnfDB = new LCBnfDB();
			tLCBnfDB.setContNo(tLCGetSchema.getContNo());
			tLCBnfDB.setBnfType("0");
			tLCBnfDB.setBnfGrade("1");
			LCBnfSet tLCBnfSet = tLCBnfDB.query();
			
			LMDutyGetDB tLMDutyGetDB = new LMDutyGetDB();
			tLMDutyGetDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
			if(!tLMDutyGetDB.getInfo())
			{
				return null;
			}
			
			if(tLCBnfSet.size()<=0)
			{	
				LCBnfSchema tLCBnfSchema = new LCBnfSchema();
				//无限制默认被保人
				if("".equals(tLMDutyGetDB.getBnfFlag())||"N".equals(tLMDutyGetDB.getBnfFlag())
						||"I".equals(tLMDutyGetDB.getBnfFlag()))
				{
					LCInsuredDB tLCInsuredDB = new LCInsuredDB();
					tLCInsuredDB.setContNo(tLCGetSchema.getContNo());
					LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
					tLCBnfSchema.setName(tLCInsuredSet.get(1).getName());
					tLCBnfSchema.setBnfLot(1);
					tLCBnfSchema.setRemark("1");//用于记录领取方式 1-现金、4-银行转账
					LCAddressDB tLCAddressDB = new LCAddressDB();
					tLCAddressDB.setCustomerNo(tLCInsuredSet.get(1).getInsuredNo());
					tLCAddressDB.setAddressNo(tLCInsuredSet.get(1).getAddressNo());
					if(!tLCAddressDB.getInfo())
					{
						return null;
					}
					tLCBnfSchema.setZipCode(tLCAddressDB.getZipCode());
					tLCBnfSchema.setPostalAddress(tLCAddressDB.getPostalAddress());
				}
				//投保人
				else if("A".equals(tLMDutyGetDB.getBnfFlag()))
				{
					LCAppntDB tLCAppntDB = new LCAppntDB();
					tLCAppntDB.setContNo(tLCGetSchema.getContNo());
					if(!tLCAppntDB.getInfo())
					{
						return null;
					}
					tLCBnfSchema.setName(tLCAppntDB.getAppntName());
					tLCBnfSchema.setBnfLot(1);
					tLCBnfSchema.setRemark("1");//用于记录领取方式 1-现金、4-银行转账
					
					LCAddressDB tLCAddressDB = new LCAddressDB();
					tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
					tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
					if(!tLCAddressDB.getInfo())
					{
						return null;
					}
					tLCBnfSchema.setZipCode(tLCAddressDB.getZipCode());
					tLCBnfSchema.setPostalAddress(tLCAddressDB.getPostalAddress());
				}
				tLCBnfSet.add(tLCBnfSchema);
			}
			//生成列表信息，打印时再拆分出来放到模板列表中
			String listInfo = "";
			for(int j=1;j<=tLCBnfSet.size();j++)
			{
				
				String payform = "";
	            switch (tLCGetSchema.getGetIntv())
	            {
	            	case 0:  payform = "趸领"; break;
	            	case 1:  payform = "月领"; break;
	            	case 3:  payform = "季领"; break;
	            	case 6:  payform = "半年领"; break;
	            	case 12:  payform = "年领"; break;
	            	case 24:  payform = "每2年领"; break;
	            	case 36:  payform = "每3年领"; break; 
	            }
	            if(payform.equals(""))
	            {
	            	return null;
	            }
	            
				listInfo =listInfo+ tLMDutyGetDB.getGetDutyName()+"|"+tLCBnfSet.get(j).getName()+"|"
				+tLCBnfSet.get(j).getBnfLot()+"|"+
				PubFun.round(tLJSGetSchema.getSumGetMoney()*tLCBnfSet.get(j).getBnfLot(),2)
				+"|"+payform+"$";
				
			}
			
			for(int j=1;j<=tLCBnfSet.size();j++)
			{
				LCBnfSchema tLCBnfSchema = tLCBnfSet.get(j);
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	
				String tLimit = PubFun.getNoLimit(tLJSGetSchema.getManageCom());
				String prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
				tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
				tLOPRTManagerSchema.setOtherNo(tLJSGetSchema.getOtherNo());
				tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);// 00:个单合同号,01:集体合同号
				tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorAutoPayAG); // 个险催付通知书
				tLOPRTManagerSchema.setManageCom(tLJSGetSchema.getManageCom());
				tLOPRTManagerSchema.setAgentCode(tLJSGetSchema.getAgentCode());
				tLOPRTManagerSchema.setReqCom(tLJSGetSchema.getManageCom());
				tLOPRTManagerSchema.setReqOperator(tLJSGetSchema.getOperator());
				tLOPRTManagerSchema.setPrtType("0");
				tLOPRTManagerSchema.setStateFlag("0");
				tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
				tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
				tLOPRTManagerSchema.setOldPrtSeq(prtSeqNo);
				tLOPRTManagerSchema.setStandbyFlag1(tLJSGetSchema.getGetNoticeNo());
				tLOPRTManagerSchema.setPatchFlag("0");
				tLOPRTManagerSchema.setRemark(listInfo);
				tLOPRTManagerSchema.setStandbyFlag2(tLCBnfSchema.getName());
				tLOPRTManagerSchema.setStandbyFlag3(tLCBnfSchema.getZipCode());
				tLOPRTManagerSchema.setStandbyFlag4(tLCBnfSchema.getPostalAddress());
				String sql="select riskname from lmrisk where riskcode in (select riskcode from lcpol where polno='?polno?')";
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql(sql);
				sqlbv3.put("polno", tLCGetSchema.getPolNo());
				tLOPRTManagerSchema.setStandbyFlag5(tExeSQL.getOneValue(sqlbv3));
				tLOPRTManagerSchema.setStandbyFlag6(tLJSGetSchema.getGetDate());
				tLOPRTManagerSet.add(tLOPRTManagerSchema);			 
			}
			   //日志监控,过程监控        
	    	PubFun.logTrack(mGlobalInput,tLJSGetSchema.getGetNoticeNo(),"生成催付通知书打印数据");
		}		
		return tLOPRTManagerSet;
	}

	public LJSGetSet getLJSGet(LJSGetDrawSet aLJSGetDrawSet) {
		LJSGetSet rLJSGetSet = new LJSGetSet();

		for (int k = 1; k <= aLJSGetDrawSet.size(); k++) {
			// 如果是以前计算过的,跳过
			String conFlag = "1";
			for (int i = 1; i < k; i++) {
				if (aLJSGetDrawSet.get(k).getGetNoticeNo().equals(
						aLJSGetDrawSet.get(i).getGetNoticeNo())) {
					conFlag = "0";
					break;
				}
			}
			if (conFlag.equals("0")) {
				continue;
			}

			// 所有相同GetNoticeNo的数据聚合成一个ljsget，金额累加
			double SumGetMoney = aLJSGetDrawSet.get(k).getGetMoney();
			for (int j = k + 1; j <= aLJSGetDrawSet.size(); j++) {
				if (aLJSGetDrawSet.get(k).getGetNoticeNo().equals(
						aLJSGetDrawSet.get(j).getGetNoticeNo())) {
					SumGetMoney += aLJSGetDrawSet.get(j).getGetMoney();
				}
			}

			LJSGetSchema tLJSGetSchema = new LJSGetSchema();
			tLJSGetSchema
					.setGetNoticeNo(aLJSGetDrawSet.get(k).getGetNoticeNo());
			tLJSGetSchema.setSerialNo(mSerialNo);

			tLJSGetSchema.setOtherNo(aLJSGetDrawSet.get(k).getContNo());
			tLJSGetSchema.setOtherNoType("2");// 2 ---表示生存领取对应的个人保单号

			tLJSGetSchema.setAppntNo(mLCPolSchema.getAppntNo());
			tLJSGetSchema.setAgentCode(mLCPolSchema.getAgentCode());
			tLJSGetSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
			tLJSGetSchema.setAgentCom(mLCPolSchema.getAgentCom());
			tLJSGetSchema.setAgentType(mLCPolSchema.getAgentType());

			tLJSGetSchema.setSumGetMoney(SumGetMoney);
			tLJSGetSchema.setGetDate(aLJSGetDrawSet.get(k).getGetDate());
			tLJSGetSchema.setStartGetDate(mLCGetSchema.getGetStartDate());

			tLJSGetSchema.setMakeDate(PubFun.getCurrentDate());
			tLJSGetSchema.setMakeTime(PubFun.getCurrentTime());
			tLJSGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSGetSchema.setModifyTime(PubFun.getCurrentTime());
			tLJSGetSchema.setOperator(mOperator);
			tLJSGetSchema.setManageCom(mManageCom);
			tLJSGetSchema.setCurrency(aLJSGetDrawSet.get(k).getCurrency());
			rLJSGetSet.add(tLJSGetSchema);
		}

		return rLJSGetSet;
	}

	// 计算新的领至日期
	public Date calGetToDate(LCGetSchema aLCGetSchema) {
		Date rGetToDate;

		Date aLastToDate = this.getGetToDate();

		// 趸领记成原领至日期即起领日期；
		// Modify by JL at 2005-7-12
		// 由于原来的PubFun中的函数不能满足需求,这里不在调用PubFun中的函数。
		// 计算事例：
		// 开始日期 参考日期 时间间隔(M) 计算结果
		// 2006-2-28 2004-2-29 12 2007-2-28
		// 2007-2-28 2004-2-29 12 2008-2-29
		// 2011-1-31 2010-12-31 1 2011-2-28
		// 2011-2-28 2010-12-31 1 2011-3-31
		GregorianCalendar bCalendar = new GregorianCalendar();
		GregorianCalendar cCalendar = new GregorianCalendar();
		bCalendar.setTime(aLastToDate); // 计算起始日期
		cCalendar.setTime(tFDate.getDate(aLCGetSchema.getGetStartDate())); // 参考日期

		int mDays = bCalendar.get(Calendar.DATE);
		int cDays = cCalendar.get(Calendar.DATE);

		bCalendar.add(Calendar.MONTH, aLCGetSchema.getGetIntv()); // 增加时间间隔

		if (mDays != cDays) // mDays < cDays
		{
			int LastDay = bCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			bCalendar.set(bCalendar.get(Calendar.YEAR), bCalendar
					.get(Calendar.MONTH), (cDays < LastDay) ? cDays : LastDay);
		}

		rGetToDate = bCalendar.getTime();
		logger.debug("==> 新的领至日期为：" + tFDate.getString(rGetToDate));

		// End Add
		return rGetToDate;
	}

	private BqCalBase initCalBase(LCGetSchema aLCGetSchema) {
		logger.debug("......initCalBase ");

		BqCalBase aBqCalBase = new BqCalBase();
		int aGetYears;
		int aLastGetTimes;
		int aCurGetTimes;
		int aGetAge;
		int aAppYears;
		String aGetIntv;
		Date tGetToDate;
		Date aGetStartDate;
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();

		if (aLCGetSchema.getGetIntv() == 0) {
			tGetToDate = tFDate.getDate(mTimeEnd);
		} else {
			tGetToDate = this.calGetToDate(aLCGetSchema);
		}

		aGetStartDate = tFDate.getDate(aLCGetSchema.getGetStartDate());
		aGetIntv = String.valueOf(aLCGetSchema.getGetIntv());

		// 本次是第几年给付(领取时的领取年期)
		aGetYears = PubFun.calInterval(aGetStartDate, tGetToDate, "Y");

		// 上次是第几次给付
		aLastGetTimes = PubFun.calInterval(aLCGetSchema.getGetStartDate(),
				aLCGetSchema.getGettoDate(), aGetIntv) + 1;

		// 本次是第几次给付
		aCurGetTimes = PubFun.calInterval(aGetStartDate, tGetToDate, aGetIntv) + 1;

		// 领取时被保人年龄,投保年期
		tLCPolSchema = this.getPolInfo();
		tLCInsuredSchema = this.getInsuredInfo();

		aGetAge = PubFun.calInterval(tFDate.getDate(tLCInsuredSchema
				.getBirthday()), tGetToDate, "Y");
		aAppYears = PubFun.calInterval(tFDate.getDate(tLCPolSchema
				.getCValiDate()), tGetToDate, "Y");

		// 投保年龄，已交费年期，始领年龄，始领年期
		int aAppAge;

		// 投保年龄，已交费年期，始领年龄，始领年期
		int aPayYear;

		// 投保年龄，已交费年期，始领年龄，始领年期
		int aGetStartAge;

		// 投保年龄，已交费年期，始领年龄，始领年期
		int aGetStartYear;
		aAppAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(),
				tLCPolSchema.getCValiDate(), "Y");
		aPayYear = PubFun.calInterval(tLCPolSchema.getCValiDate(), tLCPolSchema
				.getPaytoDate(), "Y");
		aGetStartAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(),
				aLCGetSchema.getGetStartDate(), "Y");
		aGetStartYear = PubFun.calInterval(tLCPolSchema.getCValiDate(),
				aLCGetSchema.getGetStartDate(), "Y");

		// 得到getblance值
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(aLCGetSchema.getPolNo());
		tLCInsureAccDB.setRiskCode(tLCPolSchema.getRiskCode());
		tLCInsureAccDB.setAccType("003");

		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
		logger.debug("tLCInsureAccSet :" + tLCInsureAccSet.size());

		double aGetBalance = 0.0;

		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
			aGetBalance += tLCInsureAccSet.get(i).getInsuAccGetMoney();
		}

		aBqCalBase.setAppAge(aAppAge);

		aBqCalBase.setContNo(aLCGetSchema.getContNo());
		aBqCalBase.setAddRate(aLCGetSchema.getAddRate());

		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(aLCGetSchema.getPolNo());
		tLCDutyDB.setDutyCode(aLCGetSchema.getDutyCode());
		tLCDutyDB.getInfo();
		aBqCalBase.setGet(tLCDutyDB.getAmnt());
		aBqCalBase.setGetIntv(aLCGetSchema.getGetIntv());
		aBqCalBase.setGDuty(aLCGetSchema.getGetDutyCode());
		aBqCalBase.setGetStartDate(aLCGetSchema.getGetStartDate());
		aBqCalBase.setGetDutyKind(aLCGetSchema.getGetDutyKind());

		aBqCalBase.setGetStartYear(aGetStartYear);
		aBqCalBase.setGetStartAge(aGetStartAge);
		aBqCalBase.setGetAge(aGetAge);
		aBqCalBase.setGetTimes(aCurGetTimes);
		aBqCalBase.setPayEndYear(aPayYear);
		aBqCalBase.setGetYear(aGetYears);
		aBqCalBase.setGetAppYear(aAppYears);
		aBqCalBase.setInterval(1);
		aBqCalBase.setPayEndYear(tLCPolSchema.getPayEndYear());

		aBqCalBase.setMult(tLCPolSchema.getMult());
		aBqCalBase.setPayIntv(tLCPolSchema.getPayIntv());
		aBqCalBase.setGetIntv(aLCGetSchema.getGetIntv());
		aBqCalBase.setPolNo(tLCPolSchema.getPolNo());
		aBqCalBase.setCValiDate(tLCPolSchema.getCValiDate());
		aBqCalBase.setYears(tLCPolSchema.getYears());
		aBqCalBase.setPrem(tLCPolSchema.getPrem());

		aBqCalBase.setSex(tLCInsuredSchema.getSex());
		aBqCalBase.setJob(tLCInsuredSchema.getOccupationCode());

		aBqCalBase.setGetBalance(aGetBalance);

		this.displayObject(aBqCalBase);

		return aBqCalBase;
	}

	// 递增给付领取处理
	private double calAddGetMoney(BqCalBase aBqCalBase, LCGetSchema aLCGetSchema) {
		double aGetMoney = 0;
		LMDutyGetAliveSchema tLMDutyGetAliveSchema = new LMDutyGetAliveSchema();
		tLMDutyGetAliveSchema.setSchema(mLMDutyGetAliveSchema);

		int aGetAge;
		int aGetAppYear;
		int aGetYear;
		int aTimes;

		aGetAge = Integer.parseInt(aBqCalBase.getGetAge());
		aGetAppYear = Integer.parseInt(aBqCalBase.getGetAppYear());
		aGetYear = Integer.parseInt(aBqCalBase.getGetYear());
		aTimes = 0;

		// 递增开始年龄
		if (tLMDutyGetAliveSchema.getAddStartUnit().equals("0")) {
			if (aGetAge < tLMDutyGetAliveSchema.getAddStartPeriod()) {
				aGetMoney = aLCGetSchema.getActuGet();
			} else {
				if (aGetAge > tLMDutyGetAliveSchema.getAddEndPeriod()) {
					aGetAge = tLMDutyGetAliveSchema.getAddEndPeriod();
				}

				aGetAge = aGetAge - tLMDutyGetAliveSchema.getAddStartPeriod()
						+ 1;

				// 递增间隔单位默认为年
				if ((aGetAge % tLMDutyGetAliveSchema.getAddIntv()) != 0) {
					aTimes = (aGetAge / tLMDutyGetAliveSchema.getAddIntv()) + 1;
				} else {
					aTimes = aGetAge / tLMDutyGetAliveSchema.getAddIntv();
				}

				aGetMoney = this.calAddData(aLCGetSchema.getStandMoney(),
						tLMDutyGetAliveSchema.getAddValue(), aTimes,
						tLMDutyGetAliveSchema.getAddType());
			}
		}

		// 领取递增开始年期,领取后多少年开始递增
		else if (tLMDutyGetAliveSchema.getAddStartUnit().equals("1")) {
			if (aGetYear < tLMDutyGetAliveSchema.getAddStartPeriod()) {
				aGetMoney = aLCGetSchema.getStandMoney();
			} else {
				if (aGetYear > tLMDutyGetAliveSchema.getAddEndPeriod()) {
					aGetYear = tLMDutyGetAliveSchema.getAddEndPeriod();
				}

				aGetYear = aGetYear - tLMDutyGetAliveSchema.getAddStartPeriod()
						+ 1;

				// 递增间隔单位默认为年
				if ((aGetYear % tLMDutyGetAliveSchema.getAddIntv()) != 0) {
					aTimes = (aGetYear / tLMDutyGetAliveSchema.getAddIntv()) + 1;
				} else {
					aTimes = aGetYear / tLMDutyGetAliveSchema.getAddIntv();
				}

				aGetMoney = this.calAddData(aLCGetSchema.getStandMoney(),
						tLMDutyGetAliveSchema.getAddValue(), aTimes,
						tLMDutyGetAliveSchema.getAddType());
			}
		}

		// 投保递增开始年期,投保后多少年递增
		else if (tLMDutyGetAliveSchema.getAddStartUnit().equals("2")) {
			if (aGetAppYear < tLMDutyGetAliveSchema.getAddStartPeriod()) {
				aGetMoney = aLCGetSchema.getActuGet();
			} else {
				if (aGetAppYear > tLMDutyGetAliveSchema.getAddEndPeriod()) {
					aGetAppYear = tLMDutyGetAliveSchema.getAddEndPeriod();
				}

				aGetAppYear = aGetAppYear
						- tLMDutyGetAliveSchema.getAddStartPeriod() + 1;

				// 递增间隔单位默认为年
				if ((aGetAppYear % tLMDutyGetAliveSchema.getAddIntv()) != 0) {
					aTimes = (aGetAppYear / tLMDutyGetAliveSchema.getAddIntv()) + 1;
				} else {
					aTimes = aGetAppYear / tLMDutyGetAliveSchema.getAddIntv();
				}

				aGetMoney = this.calAddData(aLCGetSchema.getActuGet(),
						tLMDutyGetAliveSchema.getAddValue(), aTimes,
						tLMDutyGetAliveSchema.getAddType());
			}
		} else {
			logger.debug("-----------no this add descriptions--------");
		}

		return aGetMoney;
	}

	// 计算递增值
	private double calAddData(double aOriginData, double aAddRate,
			double aTimes, String aAddRateType) {
		double aResultData = 0.0;

		// 按照比例递增
		if (aAddRateType.equals("R")) {
			aResultData = aOriginData + (aOriginData * aAddRate * aTimes);
			logger.debug("==>aAddRate = " + aAddRate + ",aResultData = "
					+ aResultData);
		}

		// 几何递增
		else if (aAddRateType.equals("G")) {
			aResultData = aOriginData;

			for (int i = 1; i <= aTimes; i++) {
				aResultData = aResultData * (1 + aAddRate);
			}
		} else {
			aResultData = aOriginData + (aAddRate * aTimes);
		}

		return aResultData;
	}

	// 得到保单是否销户标志
	private String getContCancelFlag(String aCancelFlag) {
		LMDutyGetAliveSchema tLMDutyGetAliveSchema = new LMDutyGetAliveSchema();
		tLMDutyGetAliveSchema = this.getGetAliveInfo();

		// 默认为000，为防止描述造成的数据问题
		try {
			if ((tLMDutyGetAliveSchema.getAfterGet() == null)
					|| tLMDutyGetAliveSchema.getAfterGet().trim().equals("")) {
				tLMDutyGetAliveSchema.setAfterGet("000");
			}
		} catch (Exception ex) {
		}

		// "000" 无动作
		if (tLMDutyGetAliveSchema.getAfterGet().equals("000")) {
			aCancelFlag = "0";
		}

		// "001" 保额递减（暂不用）
		else if (tLMDutyGetAliveSchema.getAfterGet().equals("001")) {
			aCancelFlag = "0";
		}

		// "002" 保额递增（暂不用）
		else if (tLMDutyGetAliveSchema.getAfterGet().equals("002")) {
			aCancelFlag = "0";
		}

		// "003" 无条件销户
		else if (tLMDutyGetAliveSchema.getAfterGet().equals("003")) {
			aCancelFlag = "1";
		}

		// "004" 最后一次给付销户
		else if (tLMDutyGetAliveSchema.getAfterGet().equals("004")) {
			if (aCancelFlag.equals("1")) {
				aCancelFlag = "1";
			} else {
				aCancelFlag = "0";
			}
		} else {
			logger.debug("----no des getAliv---");
		}

		return aCancelFlag;
	}

	// 是否销户标志
	private void setCancelFlag(String aCancelFlag) {
		mCancelFlag = aCancelFlag;
	}

	private String getCancelFlag() {
		if (mCancelFlag == null) {
			mCancelFlag = "0";
		}

		return mCancelFlag;
	}

	// 给付至日期
	private void setGetToDate(Date aGetToDate) {
		mGetToDate = aGetToDate;
	}

	private Date getGetToDate() {
		return mGetToDate;
	}

	// 保单信息
	private LCPolSchema getPolInfo() {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setSchema(mLCPolSchema);

		return tLCPolSchema;
	}

	// 保单客户信息
	private LCInsuredSchema getInsuredInfo() {
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		tLCInsuredSchema.setSchema(mLCInsuredSchema);

		return tLCInsuredSchema;
	}

	// 生存给付项信息
	private LMDutyGetAliveSchema getGetAliveInfo() {
		LMDutyGetAliveSchema tLMDutyGetAliveSchema = new LMDutyGetAliveSchema();
		tLMDutyGetAliveSchema.setSchema(mLMDutyGetAliveSchema);

		return tLMDutyGetAliveSchema;
	}

	public VData getResult() {
		return mResult;
	}

	public LJSGetDrawSet getLJSGetDrawSet() {
		return mLJSGetDrawSet;
	}

	private void displayObject(Object a) {
		Reflections aReflections = new Reflections();
		aReflections.printFields(a);
	}

	public static void main(String[] args) {
		FDate tFDate = new FDate();
		Date bDate = tFDate.getDate("2005-9-1");
		String strdate = bDate.toString();
		logger.debug(strdate + "+" + bDate + "="
				+ tFDate.getString(bDate));
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCGetSchema tLCGetSchema = new LCGetSchema();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("timeEnd", "2009-09-1");
		tG.Operator = "tjj";
		tG.ManageCom = "86";
		tLCGetSchema.setPolNo("210110000001866");
		tVData.addElement(tG);
		tVData.addElement(tLCPolSchema);
		tVData.addElement(tLCGetSchema);
		tVData.addElement(tTransferData);
		PersonPayPlanBL tPersonPayPlanBL = new PersonPayPlanBL();

		tPersonPayPlanBL.submitData(tVData, "");
	}
}
