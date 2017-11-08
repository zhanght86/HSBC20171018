package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import java.util.Enumeration;
import java.util.Hashtable;

import com.sinosoft.lis.claim.LLBalRecedeFeeBL;
import com.sinosoft.lis.claim.LLClaimPubFunBL;
import com.sinosoft.lis.claim.LLLcContReleaseBL;
import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LLCUWMasterDB;
import com.sinosoft.lis.db.LLUWSpecMasterDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJSPayClaimSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LLCUWBatchSchema;
import com.sinosoft.lis.schema.LLCUWMasterSchema;
import com.sinosoft.lis.schema.LLCUWSubSchema;
import com.sinosoft.lis.schema.LLUWMasterSchema;
import com.sinosoft.lis.schema.LLUWPENoticeSubSchema;
import com.sinosoft.lis.schema.LLUWPremMasterSchema;
import com.sinosoft.lis.schema.LLUWPremSubSchema;
import com.sinosoft.lis.schema.LLUWSpecMasterSchema;
import com.sinosoft.lis.schema.LLUWSpecSubSchema;
import com.sinosoft.lis.schema.LLUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.tb.DiscountCalBL;
import com.sinosoft.lis.tb.TBPrepareLJS;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPayClaimSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LLCUWMasterSet;
import com.sinosoft.lis.vschema.LLCUWSubSet;
import com.sinosoft.lis.vschema.LLUWMasterSet;
import com.sinosoft.lis.vschema.LLUWPENoticeSet;
import com.sinosoft.lis.vschema.LLUWPENoticeSubSet;
import com.sinosoft.lis.vschema.LLUWPremMasterSet;
import com.sinosoft.lis.vschema.LLUWPremSubSet;
import com.sinosoft.lis.vschema.LLUWSpecMasterSet;
import com.sinosoft.lis.vschema.LLUWSpecSubSet;
import com.sinosoft.lis.vschema.LLUWSubSet;
import com.sinosoft.lis.vschema.LWMissionSet;
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
 * Title: 理赔二核结论生效
 * </p>
 * <p>
 * Description: 理赔二核结论生效
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class LLUWResultCvalidateBL {
private static Logger logger = Logger.getLogger(LLUWResultCvalidateBL.class);

	public CErrors mErrors = new CErrors();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	private String mOperate = "";

	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();

	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private Reflections mReflections = new Reflections();

	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	private LCContSchema mLCContSchema = new LCContSchema(); // 保单表

	private LCCSpecSet mLCCSpecSet = new LCCSpecSet(); // 保单表

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private LCDutySet mLCDutySaveSet = new LCDutySet();

	private LCPremSet mLCPremSaveSet = new LCPremSet();

	private LJSPaySet mLJSPaySaveSet = new LJSPaySet();

	private LJSPayPersonSet mLJSPayPersonSaveSet = new LJSPayPersonSet();

	private LJSPayClaimSet mLJSPayClaimSaveSet = new LJSPayClaimSet();

	/** 数据操作字符串 */
	private String mClmNo = ""; // 赔案号

	private String mBatNo = ""; // 批次号

	private String mContNo = ""; // 保单号

	private String mInEffectFlag = ""; // 生效标志

	private String mInsDate = ""; // 出险时间

	private String mRgtDate = ""; // 立案时间

	private double mDAddFeeSum = 0; // 加费总额

	//private double mDAddFeeIns = 0; // 加费利息
    private PubConcurrencyLock mLock = new PubConcurrencyLock();
	public LLUWResultCvalidateBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------二核结论处理--LLUWResultCvalidateBL开始----------");
		try
		{
			if (!getInputData(cInputData, cOperate)) {
				return false;
			}
	
			if (!checkData()) {
				return false;
			}
	
			// 增加并发控制，zy
			String tOperatedNo = mClmNo;//进行发起二核的立案号并发控制
	
			//进行并发组的控制
			if(!mLock.lock(tOperatedNo, "LP0004", mGlobalInput.Operator))
			{
				CError tError = new CError(mLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
				return false;
		
			}
	    
	
			if (!dealData(cInputData)) {
				return false;
			}
	
			if (!prepareOutputData()) {
				return false;
			}
	
			if (!pubSubmit()) {
				return false;
			}
		}
		catch(Exception ex)
		{
			CError.buildErr(this, ex.toString());
			return false;
		}
		finally
		{
			mLock.unLock();
		}

		logger.debug("----------二核结论处理--LLUWResultCvalidateBL结束----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mOperate = StrTool.cTrim(cOperate);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);

		mInEffectFlag = StrTool.cTrim((String) mTransferData.getValueByName("InEffectFlag"));
		mClmNo = StrTool.cTrim((String) mTransferData.getValueByName("ClmNo"));
		mBatNo = StrTool.cTrim((String) mTransferData.getValueByName("BatNo"));
		mContNo = StrTool.cTrim((String) mTransferData.getValueByName("ContNo"));

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (mClmNo.length() == 0 || mClmNo.equals("")) {
			mErrors.addOneError("赔案号为空，不能执行此操作!!!");
			return false;
		}

		if (mBatNo.length() == 0 || mBatNo.equals("")) {
			mErrors.addOneError("批次号为空，不能执行此操作!!!");
			return false;
		}

		if (mContNo.length() == 0 || mContNo.equals("")) {
			mErrors.addOneError("合同号为空，不能执行此操作!!!");
			return false;
		}

		if (mInEffectFlag.length() == 0 || mInEffectFlag.equals("")) {
			mErrors.addOneError("是否生效标志为空，不能执行此操作!!!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(VData cInputData) {
		if (mOperate.equals("AddFeeCancel")) {// 核保加费核销
			if (!getDealLJSPayCancel(mContNo)) {
				return false;
			}
		}

		if (mInEffectFlag.equals("1")) {// mInEffectFlag生效标志，"1"代表生效，"2"代表不生效，"3"代表撤消
			if (!dealYesInEffect()) {
				return false;
			}
		}

		if (mInEffectFlag.equals("2")) {// mInEffectFlag生效标志，"1"代表生效，"2"代表不生效，"3"代表撤消
			if (!getUWConclusionCancel()) {
				return false;
			}
		}

		//解除保单挂起
		if (mInEffectFlag.equals("1") || mInEffectFlag.equals("2")) {
			LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
			if (!tLLLcContReleaseBL.submitData(cInputData, "CLAIMUW")) {
				CError.buildErr(this, "解除保单挂起失败,"+tLLLcContReleaseBL.mErrors.getLastError());
				return false;
			} else {
				VData tempVData = new VData();
				tempVData = tLLLcContReleaseBL.getResult();
				MMap tMap = new MMap();
				tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				mMMap.add(tMap);
			}		
		}		
		
		return true;
	}

	/**
	 * 删除理赔核保加费的应收数据
	 * 
	 * @param pContNo
	 * @param pPolNo
	 * @return
	 */
	private boolean getDealLJSPayCancel(String pContNo) {

		logger.debug("-------------------------核销加费应收记录-----开始-------------------------");
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = " select * from LJSPay where 1=1 "
				+ " and GetNoticeNo in (select distinct GetNoticeNo from LJSPayPerson where 1=1 "
				+ " and ContNo = '" + "?ContNo?" + "')" + "  and OtherNo = '" + "?clmno?" + "'"
				+ " and OtherNoType='5'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ContNo", pContNo);
		sqlbv.put("clmno", mClmNo);
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sqlbv);
		logger.debug("------------------------------------------------------");
		logger.debug("--个人应收汇总信息[" + tLJSPaySet.size() + "]:" + tSql);
		logger.debug("------------------------------------------------------");

		if (tLJSPayDB.mErrors.needDealError() == true) {
			this.mErrors
					.addOneError("查询个人应收汇总信息发生错误!" + tLJSPayDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 循环应收汇总记录
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LJSPaySet tLJSPaySaveSet = new LJSPaySet();
		LJSPayPersonSet tLJSPayPersonSaveSet = new LJSPayPersonSet();

		// LLJSPaySet tLLJSPaySaveSet = new LLJSPaySet();
		LJAPaySet tLJAPaySet = new LJAPaySet();
		// LLJSPayPersonSet tLLJSPayPersonSaveSet = new LLJSPayPersonSet();
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
		for (int i = 1; i <= tLJSPaySet.size(); i++) {
			LJSPaySchema tLJSPaySchema = tLJSPaySet.get(i);
			String tGetNoticeNo = tLJSPaySchema.getGetNoticeNo();
			String tBankOnTheWayFlag = StrTool.cTrim(tLJSPaySchema.getBankOnTheWayFlag());// 1--银行在途标志，不允许删除

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1 处理银行在途
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tBankOnTheWayFlag.equals("1")) {
				mErrors.addOneError("通知书号码[" + tGetNoticeNo + "]的交费信息银行在途,合同号[" + pContNo
						+ "]不允许删除其应收记录,等回销后在进行此操作!!!");
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.2
			 * 处理暂交费，如果为空，可以删除应收汇总记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LJTempFeeSet tLJTempFeeSet = mLLClaimPubFunBL.getLJTempFee(tGetNoticeNo);
			if (tLJTempFeeSet != null) {
				// mErrors
				// .addOneError("通知书号码[" + tGetNoticeNo + "],合同号["
				// + pContNo
				// + "]存在暂交费记录,不允许删除其应收记录，请先进行暂交费退费,然后再进行此操作!!!");
				// return false;
				for (int j = 1; j <= tLJTempFeeSet.size(); j++) {
					LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(j);
					tLJTempFeeSchema.setConfDate(PubFun.getCurrentDate());
					tLJTempFeeSchema.setConfFlag("1");
					mLJTempFeeSet.add(tLJTempFeeSchema);
				}

			}
			LJTempFeeClassSet tLJTempFeeClassSet = mLLClaimPubFunBL.getLJTempFeeClass(tGetNoticeNo);
			if (tLJTempFeeClassSet != null) {

				for (int j = 1; j <= tLJTempFeeClassSet.size(); j++) {
					LJTempFeeClassSchema tLJTempFeeClassSchema = tLJTempFeeClassSet.get(j);
					tLJTempFeeClassSchema.setConfDate(PubFun.getCurrentDate());
					tLJTempFeeClassSchema.setConfFlag("1");
					mLJTempFeeClassSet.add(tLJTempFeeClassSchema);
				}

			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.3 将应收汇总记录放入理赔暂存表中
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			// LLJSPaySchema tLLJSPaySchema = new LLJSPaySchema();
			LJAPaySchema tLJAPaySchema = new LJAPaySchema();
			String tLimit = PubFun.getNoLimit(tLJSPaySchema.getManageCom());
			String tPaySerialNo = PubFun1.CreateMaxNo("PAYNO", tLimit);
			tLJAPaySchema.setPayNo(tPaySerialNo);
			mReflections.transFields(tLJAPaySchema, tLJSPaySchema);
			tLJAPaySchema.setIncomeNo(tLJSPaySchema.getOtherNo()); // 应收/实收编号
			tLJAPaySchema.setIncomeType(tLJSPaySchema.getOtherNoType()); // 应收/实收编号类型
			tLJAPaySchema.setSumActuPayMoney(tLJSPaySchema.getSumDuePayMoney());
			tLJAPaySchema.setSerialNo(pContNo); // 流水号
			tLJAPaySchema.setMakeDate(CurrentDate);
			tLJAPaySchema.setMakeTime(CurrentTime);
			tLJAPaySchema.setModifyDate(CurrentDate); // 最后一次修改日期
			tLJAPaySchema.setModifyTime(CurrentTime); // 最后一次修改时间
			tLJAPaySchema.setConfDate(CurrentDate);
			tLJAPaySchema.setEnterAccDate(CurrentDate);
			tLJAPaySchema.setOperator(mGlobalInput.Operator);
			// tLLJSPaySchema.setClmNo(this.mClmNo);

			tLJAPaySet.add(tLJAPaySchema);

			tLJSPaySaveSet.add(tLJSPaySchema);// 准备删除

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.4 删除应收明细记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			tLJSPayPersonDB.setGetNoticeNo(tGetNoticeNo);
			LJSPayPersonSet tLJSPayPersonSet = tLJSPayPersonDB.query();

			if (tLJSPayPersonSet.size() > 0) {
				tLJSPayPersonSaveSet.add(tLJSPayPersonSet);
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.6 将应收明细记录放入理赔暂存表中
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			for (int j = 1; j <= tLJSPayPersonSet.size(); j++) {
				LJSPayPersonSchema tLJSPayPersonSchema = tLJSPayPersonSet.get(j);

				// LLJSPayPersonSchema tLLJSPayPersonSchema = new
				// LLJSPayPersonSchema();
				LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
				tLJAPayPersonSchema.setPayNo(tPaySerialNo);

				mReflections.transFields(tLJAPayPersonSchema, tLJSPayPersonSchema);
				// tLLJSPayPersonSchema.setClmNo(this.mClmNo);
				tLJAPayPersonSchema.setPayType("BF");
				if(!tLJSPayPersonSchema.getPayType().equals("ZC"))
					tLJAPayPersonSchema.setPayType(tLJSPayPersonSchema.getPayType());
				tLJAPayPersonSchema.setEnterAccDate(CurrentDate); // 到帐日期
				tLJAPayPersonSchema.setConfDate(CurrentDate); // 确认日期
				tLJAPayPersonSchema.setSerialNo(pContNo); // 流水号
				tLJAPayPersonSchema.setOperator(mGlobalInput.Operator); // 操作员
				tLJAPayPersonSchema.setMakeDate(CurrentDate); // 入机日期
				tLJAPayPersonSchema.setMakeTime(CurrentTime); // 入机时间
				tLJAPayPersonSchema.setModifyDate(CurrentDate); // 最后一次修改日期
				tLJAPayPersonSchema.setModifyTime(CurrentTime); // 最后一次修改时间

				tLJAPayPersonSet.add(tLJAPayPersonSchema);
			}
		}

		mMMap.put(tLJSPaySaveSet, "DELETE");
		mMMap.put(tLJSPayPersonSaveSet, "DELETE");

		mMMap.put(tLJAPaySet, "DELETE&INSERT");
		mMMap.put(tLJAPayPersonSet, "DELETE&INSERT");
		mMMap.put(mLJTempFeeSet, "UPDATE");
		mMMap.put(mLJTempFeeClassSet, "UPDATE");

		for(int i=1;i<=tLJAPaySet.size();i++)
		{
			LJAPaySchema tLJAPaySchema = new LJAPaySchema();
			tLJAPaySchema  = tLJAPaySet.get(i);
		//2012-02-14 add
		//补充客户账户核销处理
		logger.debug("开始理赔核销客户账户处理....");
		// 添加客户账户处理
		VData nInputData = new VData();
		TransferData nTransferData = new TransferData();
		// 指定用途
		//理赔
		nTransferData.setNameAndValue("OperationType", "4");
		// 传入本次核销金额
		nTransferData.setNameAndValue("SumDuePayMoney", tLJAPaySchema.getSumActuPayMoney());
		//传入使用余额
		//nTransferData.setNameAndValue("YEShouleUseMoney", tYEShouleUseMoney);
		//传入本次使用的币种
		nTransferData.setNameAndValue("Currency", tLJAPaySchema.getCurrency());
		nTransferData.setNameAndValue("GetNoticeNo", tLJAPaySchema.getGetNoticeNo());
		nInputData.add(mLJTempFeeSet);
		nInputData.add(mLJTempFeeClassSet);
		nInputData.add(nTransferData);
		FICustomerMain tFICustomerMain = new FICustomerMain();
		// 调用客户账户收费接口，传入财务标志RN
		if (tFICustomerMain.submitData(nInputData, "RN"))
		{
			// 获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
			mMMap.add(tFICustomerMain.getMMap());
		}
		else
		{
			mErrors.copyAllErrors(tFICustomerMain.mErrors);
		}
		
		logger.debug("结束理赔核销客户账户处理....");
		}
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0
		 * 删除理赔收费表(应收)的二次核保加费利息记录
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "delete from LJSPayClaim where 1=1" + " and OtherNo='" + "?clmno?" + "'"
				+ " and OtherNoType='5'" + " and ContNo='" + "?ContNo?" + "'"
				+ " and substr(FeeOperationType,1,3)='C30'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("ContNo", pContNo);
		sqlbv1.put("clmno", this.mClmNo);
		mMMap.put(sqlbv1, "DELETE");

		logger.debug("-------------------------核销加费应收记录--结束-------------------------");
		return true;
	}

	/**
	 * 二核结论处理-不生效
	 * 
	 * @return
	 */
	private boolean getUWConclusionCancel() {
		/**
		 * No1.0 更新理赔核保批次生效标志信息
		 */
		LLCUWBatchSchema tLLCUWBatchSchema = mLLClaimPubFunBL
				.getLLCUWBatch(mClmNo, mBatNo, mContNo);
		if (tLLCUWBatchSchema == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}
		if (tLLCUWBatchSchema.getState().equals("0")) {
			this.mErrors.addOneError("合同号[" + mContNo + "],批次号[" + mBatNo + "]的核保处理没有完成,不能进行此操作!");
			return false;
		}
		if (!tLLCUWBatchSchema.getInEffectFlag().equals("0")) {
			this.mErrors.addOneError("合同号[" + mContNo + "],批次号[" + mBatNo
					+ "]的核保结论已经被处理,不能进行此操作!!!");
			return false;
		}

		tLLCUWBatchSchema.setInEffectFlag(mInEffectFlag);
		tLLCUWBatchSchema.setModifyDate(PubFun.getCurrentDate());
		tLLCUWBatchSchema.setModifyTime(PubFun.getCurrentTime());
		mMMap.put(tLLCUWBatchSchema, "UPDATE");

		ExeSQL mExeSQL = new ExeSQL();
		int umNumber = 0;// 核保顺序号

		/**
		 * No2.0 将个人合同理陪核保信息转出到备份表中
		 */
		LLCUWMasterSet mLLCUWMasterSet = mLLClaimPubFunBL
				.getLLCUWMasterSet(mClmNo, mBatNo, mContNo);

		LLCUWSubSchema mLLCUWSubSchema = null;// 个人合同理陪核保信息

		if (mLLCUWMasterSet.size() > 0) {
			mExeSQL = new ExeSQL();
			umNumber = 0;
			String uwNoSql = "select (case when max(uwNo+1) is null then 1 else max(uwNo+1) end) from LLCUWSub where caseno='" + "?clmno?"
					+ "' and contno='" + "?contno?" + "' and batno='" + "?batno?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(uwNoSql);
			sqlbv2.put("contno", mContNo);
			sqlbv2.put("clmno", mClmNo);
			sqlbv2.put("batno", mBatNo);
			umNumber = Integer.parseInt(mExeSQL.getOneValue(sqlbv2));
			logger.debug("umNumber:" + umNumber);

			LLCUWSubSet mLLCUWSubSet = new LLCUWSubSet();// 个人合同理陪核保信息
			for (int i = 1; i <= mLLCUWMasterSet.size(); i++) {
				mLLCUWSubSchema = new LLCUWSubSchema();
				mReflections.transFields(mLLCUWSubSchema, mLLCUWMasterSet.get(i));
				mLLCUWSubSchema.setUWNo(umNumber);
				mLLCUWSubSet.add(mLLCUWSubSchema);
			}

			mMMap.put(mLLCUWMasterSet, "DELETE");
			mMMap.put(mLLCUWSubSet, "INSERT");
		}

		/**
		 * No3.0 将个人合同险种理陪核保信息转出到备份表中
		 */
		LLUWMasterSet mLLUWMasterSet = mLLClaimPubFunBL.getLLUWMasterSet(mClmNo, mBatNo, mContNo,
				null);

		LLUWSubSchema mLLUWSubSchema = null;// 个人合同险种理陪核保信息

		if (mLLUWMasterSet.size() > 0) {
			mExeSQL = new ExeSQL();
			umNumber = 0;
			String uwNoSql = "select (case when max(uwNo+1) is null then 1 else max(uwNo+1) end) from LLUWSub where caseno='" + "?clmno?"
					+ "' and contno='" + "?contno?" + "' and batno='" + "?batno?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(uwNoSql);
			sqlbv3.put("contno", mContNo);
			sqlbv3.put("clmno", mClmNo);
			sqlbv3.put("batno", mBatNo);
			umNumber = Integer.parseInt(mExeSQL.getOneValue(sqlbv3));
			logger.debug("umNumber:" + umNumber);

			LLUWSubSet mLLUWSubSet = new LLUWSubSet();// 个人合同险种理陪核保信息
			for (int i = 1; i <= mLLUWMasterSet.size(); i++) {
				mLLUWSubSchema = new LLUWSubSchema();
				mReflections.transFields(mLLUWSubSchema, mLLUWMasterSet.get(i));
				mLLUWSubSchema.setUWNo(umNumber);
				mLLUWSubSet.add(mLLUWSubSchema);
			}

			mMMap.put(mLLUWMasterSet, "DELETE");
			mMMap.put(mLLUWSubSet, "INSERT");
		}

		/**
		 * No4.0 将加费信息转出到备份表中
		 */
		LLUWPremMasterSet mLLUWPremMasterSet = mLLClaimPubFunBL.getLLUWPremMasterSet(mClmNo,
				mBatNo, mContNo, null);

		LLUWPremSubSchema mLLUWPremSubSchema = null;// 加费轨迹表

		if (mLLUWPremMasterSet.size() > 0) {
			LLUWPremSubSet mLLUWPremSubSet = new LLUWPremSubSet();// 加费轨迹表

			for (int i = 1; i <= mLLUWPremMasterSet.size(); i++) {
				mLLUWPremSubSchema = new LLUWPremSubSchema();
				mReflections.transFields(mLLUWPremSubSchema, mLLUWPremMasterSet.get(i));
				mLLUWPremSubSet.add(mLLUWPremSubSchema);
			}
			mMMap.put(mLLUWPremSubSet, "INSERT");

			String sql = "delete from LLUWPremMaster where ClmNo='" + "?clmno?" + "' and batno='"
					+ "?batno?" + "' and contno='" + "?contno?" + "'";
			logger.debug("sql:" + sql);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sql);
			sqlbv4.put("contno", mContNo);
			sqlbv4.put("clmno", mClmNo);
			sqlbv4.put("batno", mBatNo);
			mMMap.put(sqlbv4, "DELETE");

		}

		/**
		 * No5.0 将特约信息转出到备份表中
		 */
		LLUWSpecMasterSet mLLUWSpecMasterSet = mLLClaimPubFunBL.getLLUWSpecMasterSet(mClmNo,
				mBatNo, mContNo);

		LLUWSpecSubSchema mLLUWSpecSubSchema = null;// 特约轨迹表

		if (mLLUWSpecMasterSet.size() > 0) {
			LLUWSpecSubSet mLLUWSpecSubSet = new LLUWSpecSubSet();// 特约轨迹表

			for (int i = 1; i <= mLLUWSpecMasterSet.size(); i++) {
				mLLUWSpecSubSchema = new LLUWSpecSubSchema();
				mReflections.transFields(mLLUWSpecSubSchema, mLLUWSpecMasterSet.get(i));
				mLLUWSpecSubSet.add(mLLUWSpecSubSchema);
			}
			mMMap.put(mLLUWSpecSubSet, "INSERT");

			String sql = "delete from LLUWSpecMaster where ClmNo='" + "?clmno?" + "' and batno='"
					+ "?batno?" + "' and contno='" + "?contno?" + "'";
			logger.debug("sql:" + sql);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(sql);
			sqlbv5.put("contno", mContNo);
			sqlbv5.put("clmno", mClmNo);
			sqlbv5.put("batno", mBatNo);
			mMMap.put(sqlbv5, "DELETE");
		}

		/**
		 * No6.0 将体检信息转出到备份表中
		 */
		LLUWPENoticeSet mLLUWPENoticeSet = mLLClaimPubFunBL.getLLUWPENoticeSet(mClmNo, mBatNo,
				mContNo);

		LLUWPENoticeSubSchema mLLUWPENoticeSubSchema = null;// 特约轨迹表

		if (mLLUWPENoticeSet.size() > 0) {
			LLUWPENoticeSubSet mLLUWPENoticeSubSet = new LLUWPENoticeSubSet();// 特约轨迹表

			for (int i = 1; i <= mLLUWPENoticeSet.size(); i++) {
				mLLUWPENoticeSubSchema = new LLUWPENoticeSubSchema();
				mReflections.transFields(mLLUWPENoticeSubSchema, mLLUWPENoticeSet.get(i));
				String tPESerielNo = PubFun1.CreateMaxNo("LLPE", 10);
				mLLUWPENoticeSubSchema.setSerialNo(tPESerielNo);
				mLLUWPENoticeSubSet.add(mLLUWPENoticeSubSchema);
			}
			mMMap.put(mLLUWPENoticeSubSet, "INSERT");

			String sql = "delete from LLUWPENotice where ClmNo='" + "?clmno?" + "' and batno='"
					+ "?batno?" + "' and contno='" + "?contno?" + "'";
			logger.debug("sql:" + sql);
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(sql);
			sqlbv6.put("contno", mContNo);
			sqlbv6.put("clmno", mClmNo);
			sqlbv6.put("batno", mBatNo);
			mMMap.put(sqlbv6, "DELETE");
		}
		/****************工作流升级，现在增加理赔发起二核节点0000005500，从理赔立案确认生成，生命周期到审批处理完毕
		 * 为了保证赔案对应的所有保单都能发核保通知书，0000005510节点的生命周期也是到审批完毕，这里不用删除
		 * 0000005520在理赔二核确认时已经删除，这里也不用处理，所以把下面工作流处理去掉
		 * ***********************/
		/**
		 * No7.0 将发起二核节点和通知书初始化节点信息转出到备份表中
		 * 05-12增加校验条件如果同一批次下的所有合同都已经进行了处理才删除工作流节点
		 */
		

		return true;
	}

	/**
	 * 合同生效处理
	 * 
	 * @return
	 */
	private boolean dealYesInEffect() {
		logger.debug("----------处理理赔二次核保时,根据主险的加费承保结论做生效处理--开始----------");
		mInsDate = mLLClaimPubFunBL.getInsDate(mClmNo);// 出险时间
		mRgtDate = mLLClaimPubFunBL.getRgtDate(mClmNo);// 立案日期

		/**
		 * No0.0
		 * 如果有续期催收，调用续期抽档撤销处理类IndiDueFeeCancelBL，如果撤销失败则不允许进行生效处理，并提示明确原因信息。
		 */
		/**LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNoType("2");
		tLJSPayDB.setOtherNo(mContNo);
		LJSPaySet tLJSPaySet = tLJSPayDB.query();

		if (tLJSPaySet != null && tLJSPaySet.size() >= 1) {
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("SubmitFlag", "noSubmit");

			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema.setContNo(mContNo);

			VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tLCContSchema);
			tVData.add(mGlobalInput);

			IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
			if (!tIndiDueFeeCancelBL.submitData(tVData, "")) {
				this.mErrors.copyAllErrors(tIndiDueFeeCancelBL.mErrors);
				return false;
			} else {
				mMMap.add(tIndiDueFeeCancelBL.getMap());
			}
		}*/

		/**
		 * No1.0 更新理赔核保批次表LLCUWBatch生效标志信息
		 */
		LLCUWBatchSchema tLLCUWBatchSchema = mLLClaimPubFunBL
				.getLLCUWBatch(mClmNo, mBatNo, mContNo);
		if (tLLCUWBatchSchema == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}
		if (tLLCUWBatchSchema.getState().equals("0")) {
			this.mErrors.addOneError("合同号[" + mContNo + "],批次号[" + mBatNo + "]的核保处理没有完成,不能进行此操作!");
			return false;
		}

		if (!tLLCUWBatchSchema.getInEffectFlag().equals("0")) {
			this.mErrors.addOneError("合同号[" + mContNo + "],批次号[" + mBatNo + "]的核保结论已经被处理,不能进行此操作!");
			return false;
		}
		tLLCUWBatchSchema.setInEffectFlag(mInEffectFlag);
		tLLCUWBatchSchema.setModifyDate(PubFun.getCurrentDate());
		tLLCUWBatchSchema.setModifyTime(PubFun.getCurrentTime());
		mMMap.put(tLLCUWBatchSchema, "UPDATE");

		/**
		 * No2.0 更新LCCont表中的核保结论标志
		 */
		LLCUWMasterSchema tLLCUWMasterSchema = mLLClaimPubFunBL.getLLCUWMaster(mClmNo, mBatNo,
				mContNo);
		if (tLLCUWMasterSchema == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		mLCContSchema = mLLClaimPubFunBL.getLCCont(mContNo);
		if (mLCContSchema == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		// PassFlag：2-维持原条件，5-自核未通过
		if (!"2".equals(tLLCUWMasterSchema.getPassFlag())
				&& !"5".equals(tLLCUWMasterSchema.getPassFlag())) {
			mLCContSchema.setUWFlag(tLLCUWMasterSchema.getPassFlag());
		}
		mLCContSchema.setModifyDate(PubFun.getCurrentDate());
		mLCContSchema.setModifyTime(PubFun.getCurrentTime());

		/**
		 * No3.0 查询个人理陪险种核保结果信息
		 */
		LLUWMasterSet tLLUWMasterSet = mLLClaimPubFunBL.getLLUWMasterSet(mClmNo, mBatNo, mContNo,
				null);
		if (tLLUWMasterSet == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		LCPolSet tLCPolSaveSet = new LCPolSet();
		for (int i = 1; i <= tLLUWMasterSet.size(); i++) {
			LLUWMasterSchema tLLUWMasterSchema = tLLUWMasterSet.get(i);
			String tPolNo = StrTool.cTrim(tLLUWMasterSchema.getPolNo()); // 险种保单号
			String tUWFlag = StrTool.cTrim(tLLUWMasterSchema.getPassFlag()); // 核保结论
			 mDAddFeeSum = 0;//重新初始化加费总金额
			/**
			 * No4.1 更新LCPol表中的核保结论标志
			 */
			mLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
			if (mLCPolSchema == null) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			}
			if (!"c".equals(tUWFlag) && !"5".equals(tUWFlag)) {// c-维持原条件承保,5-自核未通过
				mLCPolSchema.setUWFlag(tUWFlag);
			}
			if ("1".equals(tUWFlag) || "6".equals(tUWFlag)) {// 1-拒保，6-不予续保
				mLCPolSchema.setRnewFlag("-2");// -2—不续保
			}
			mLCPolSchema.setModifyDate(PubFun.getCurrentDate());
			mLCPolSchema.setModifyTime(PubFun.getCurrentTime());

			/**
			 * No4.2 处理理赔核保加费信息
			 */
			if (tUWFlag.equals("4")) {// 4-次标准承保
				// 得到该险种的核保加费信息
				LLUWPremMasterSet tLLUWPremMasterSet = mLLClaimPubFunBL.getLLUWPremMasterSet(
						mClmNo, mBatNo, null, tPolNo);
				if (tLLUWPremMasterSet == null) {
					this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return true;
				}
				logger.debug("--加费信息的记录数[" + tLLUWPremMasterSet.size() + "]");

				String tPayNo = PubFun1.CreateMaxNo("PAYNOTICENO", PubFun.getNoLimit(mLCPolSchema
						.getManageCom()));

				// 循环每个加费信息，同时更新承保相应保费信息数据
				for (int j = 1; j <= tLLUWPremMasterSet.size(); j++) {
					LLUWPremMasterSchema tLLUWPremMasterSchema = tLLUWPremMasterSet.get(j);
					String tAddForm = StrTool.cTrim(tLLUWPremMasterSchema.getAddForm());// 加费开始时间类型:0-追溯，1-当期加费，2-下期加费
					String tPayStartDate = "";
					if (tAddForm == null || tAddForm.equals("") || tAddForm.equals("0")) {
						tPayStartDate = StrTool.cTrim(tLLUWPremMasterSchema.getPayStartDate());// 起交日期,先判断加费开始时间类型，若为空或者0，则取LLUWPremMaster.PayStartDate
					} else if ("2".equals(tAddForm)) {
						tPayStartDate = mLCPolSchema.getPaytoDate();// 起交日期,若为2，则取LCPol.PaytoDate
					}

					// 处理LCPrem表数据
					if (!this.getDealLCPrem(tLLUWPremMasterSchema)) {
						return false;
					}

					// 处理应收数据--做删除处理
					if (!this.getDealLJSPay(tLLUWPremMasterSchema, tPayStartDate)) {
						return false;
					}

					// 处理实收明细数据--做补费处理
					if (!this.getDealLJSPayPerson(tLLUWPremMasterSchema, tPayStartDate, tPayNo)) {
						return false;
					}
				}// end for

				// 处理实收汇总数据--做补费处理,同时生成利息数据
				String tCalDate = PubFun.calDate(CurrentDate, 60, "D", CurrentDate);
				/*logger.debug("--总加费为[" + mDAddFeeSum + "],总利息为[" + 0 + "]");
				if (mDAddFeeSum > 0) {
					this.getLJSPay(tPayNo, mDAddFeeSum, tCalDate);
				}*/
				this.getLJSPay(tPayNo, tCalDate);

				// 处理理赔收费表(应收)--做加费利息处理
				//this.getLJSPayClaim(tPayNo, mDAddFeeIns);
			}// end if tUWFlag=4

			tLCPolSaveSet.add(mLCPolSchema);
		}// end for

		// 06-22 增加对特约信息的向C表转化处理
		if (!this.getDealLCCSpec(tLLCUWBatchSchema)) {
			return false;
		}

		mMMap.put(mLCContSchema, "UPDATE");
		mMMap.put(tLCPolSaveSet, "UPDATE");
		mMMap.put(mLCPremSaveSet, "DELETE&INSERT");
		mMMap.put(mLCDutySaveSet, "UPDATE");

		TaxCalculator.calBySchemaSet(mLJSPayPersonSaveSet);
		mMMap.put(mLJSPayPersonSaveSet, "DELETE&INSERT");
		TaxCalculator.calBySchemaSet(mLJSPayClaimSaveSet);
		mMMap.put(mLJSPayClaimSaveSet, "DELETE&INSERT");
		mMMap.put(mLJSPaySaveSet, "DELETE&INSERT");

		/**
		 * No6.0当合同核保结论为1-拒保时，打印拒保通知书
		 */
		if ("1".equals(tLLCUWMasterSchema.getPassFlag())) {
			LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();
			tLLCUWMasterDB.setCaseNo(mClmNo);
			tLLCUWMasterDB.setContNo(mContNo);
			tLLCUWMasterDB.getInfo();

			String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String mPrtSeqUW = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);

			// 通知书号
			// String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			// String tPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ",
			// "LP00");//LP00-理赔拒保通知书
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setPrtSeq(mPrtSeqUW);
			tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
			tLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);
			tLOPRTManagerSchema.setCode("LP00");// LP00 理赔拒保通知书
			tLOPRTManagerSchema.setOtherNo(mLCContSchema.getContNo());
			// tLOPRTManagerSchema.setStandbyFlag1(cLPEdorItemSchema.getEdorNo());
			// tLOPRTManagerSchema.setStandbyFlag7("BQJB");
			tLOPRTManagerSchema.setRemark(tLLCUWMasterDB.getUWIdea());
			tLOPRTManagerSchema.setStateFlag("0");
			mMMap.put(tLOPRTManagerSchema, "INSERT");
		}
		/****************工作流升级，现在增加理赔发起二核节点0000005500，从理赔立案确认生成，生命周期到审批处理完毕
		 * 为了保证赔案对应的所有保单都能发核保通知书，0000005510节点的生命周期也是到审批完毕，这里不用删除
		 * 0000005520在理赔二核确认时已经删除，这里也不用处理，所以把下面工作流处理去掉
		 * ***********************/
		/**
		 * No7.0 将发起二核节点和通知书初始化节点信息转出到备份表中
		 * 05-12增加校验条件如果同一批次下的所有合同都已经进行了处理才删除工作流节点
		 */
		

		logger.debug("----------处理理赔二次核保时,根据主险的加费承保结论做生效处理--结束----------");

		return true;
	}

	/**
	 * 处理理赔核保加费的数据
	 * 
	 * @return
	 */
	private boolean getDealLCPrem(LLUWPremMasterSchema pLLUWPremMasterSchema) {
		logger.debug("----处理理赔二次核保时,根据主险的加费承保结论-----生成LCPrem数据处理-----开始-----");
		/**
		 * No1.0 定义变量
		 */
		String tPolNo = StrTool.cTrim(pLLUWPremMasterSchema.getPolNo()); // 险种保单号
		String tDutyCode = StrTool.cTrim(pLLUWPremMasterSchema.getDutyCode());
		String tPayPlanType = StrTool.cTrim(pLLUWPremMasterSchema.getPayPlanType());// 交费计划类型

		/**
		 * No4.1 查询责任LCDuty的信息
		 */
		LCDutySchema tLCDutySchema = mLLClaimPubFunBL.getLCDuty(tPolNo, tDutyCode);
		if (tLCDutySchema == null) {
			CError.buildErr(this, "查询不到险种" + tPolNo + "的责任信息!");
			return false;
		}

		/**
		 * No4.2 先查询出原先保费项LCPrem的保费信息
		 */
		String tSql = "select * from LCPrem where 1=1 " + " and PolNo = '" + "?PolNo?" + "'"
				+ " and DutyCode = '" + "?DutyCode?" + "'" + " and PayPlanCode  like '000000%'"
				+ " and PayPlanType = '" + "?PayPlanType?" + "'";
		LCPremDB tLCPremDB = new LCPremDB();
		if (tLCPremDB.mErrors.needDealError()) {
			mErrors.addOneError("保单险种[" + tPolNo + "]原责任信息没有取到!!!");
			return false;
		}
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("PolNo", tPolNo);
		sqlbv7.put("DutyCode", tDutyCode);
		sqlbv7.put("PayPlanType", tPayPlanType);
		LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv7);
		logger.debug("保单险种[" + tPolNo + "]原责任信息记录数:[" + tLCPremSet.size() + "]");

		/**
		 * No4.3 先减去原先保费项的保费信息
		 */
		LLUWPremSubSet tLLUWPremSubSaveSet = new LLUWPremSubSet();
		for (int j = 1; j <= tLCPremSet.size(); j++) {
			/**
			 * No4.3.1 将LCPrem的准备删除的数据备份到LLUWPremSub表中
			 */
			LCPremSchema tLCPremSchema = tLCPremSet.get(j);
			LLUWPremSubSchema tLLUWPremSubSchema = new LLUWPremSubSchema();
			mReflections.transFields(tLLUWPremSubSchema, tLCPremSchema);

			tLLUWPremSubSchema.setClmNo(mClmNo);
			tLLUWPremSubSchema.setBatNo(mBatNo);
			tLLUWPremSubSaveSet.add(tLLUWPremSubSchema);

			/**
			 * No4.3.2 减去保费
			 */
			mLCContSchema.setPrem(mLCContSchema.getPrem() - tLCPremSet.get(j).getPrem());
			mLCPolSchema.setPrem(mLCPolSchema.getPrem() - tLCPremSet.get(j).getPrem());
			tLCDutySchema.setPrem(tLCDutySchema.getPrem() - tLCPremSet.get(j).getPrem());
		}
		mMMap.put(tLLUWPremSubSaveSet, "DELETE&INSERT");

		/**
		 * No4.4 再加上本次加费项的保费信息
		 */
		mLCContSchema.setPrem(mLCContSchema.getPrem() + pLLUWPremMasterSchema.getPrem());
		mLCPolSchema.setPrem(mLCPolSchema.getPrem() + pLLUWPremMasterSchema.getPrem());
		tLCDutySchema.setPrem(tLCDutySchema.getPrem() + pLLUWPremMasterSchema.getPrem());
		mLCDutySaveSet.add(tLCDutySchema);

		/**
		 * No4.5 删除LCPrem中的原数据
		 */
		String tDelSql = "delete from LCPrem where 1=1 " + " and PolNo='" + "?PolNo?" + "'"
				+ " and DutyCode = '" + "?DutyCode?" + "'" + " and PayPlanCode  like '000000%'"
				+ " and PayPlanType = '" + "?PayPlanType?" + "'";
		logger.debug("保单险种[" + tPolNo + "],删除原保费项信息:[" + tDelSql + "]");
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tDelSql);
		sqlbv8.put("PolNo", tPolNo);
		sqlbv8.put("DutyCode", tDutyCode);
		sqlbv8.put("PayPlanType", tPayPlanType);
		mMMap.put(sqlbv8, "DELETE");

		/**
		 * No4.6 增加新的LCPrem的数据
		 */
		LCPremSchema tLCPremSchema = new LCPremSchema();
		this.mReflections.transFields(tLCPremSchema, pLLUWPremMasterSchema);
		mLCPremSaveSet.add(tLCPremSchema);

		logger.debug("----处理理赔二次核保时,根据主险的加费承保结论-----生成LCPrem数据处理-----结束---");
		return true;
	}

	/**
	 * 将特约转到C表
	 */
	private boolean getDealLCCSpec(LLCUWBatchSchema tLLCUWBatchSchema) {
		LLUWSpecMasterDB tLLUWSpecMasterDB = new LLUWSpecMasterDB();
		tLLUWSpecMasterDB.setBatNo(tLLCUWBatchSchema.getBatNo());
		tLLUWSpecMasterDB.setClmNo(tLLCUWBatchSchema.getCaseNo());
		tLLUWSpecMasterDB.setContNo(tLLCUWBatchSchema.getContNo());
		LLUWSpecMasterSet tLLUWSpecMasterSet = new LLUWSpecMasterSet();
		tLLUWSpecMasterSet = tLLUWSpecMasterDB.query();

		LLUWSpecSubSet tLLUWSpecSubSet = new LLUWSpecSubSet();
		for (int i = 1; i <= tLLUWSpecMasterSet.size(); i++) {
			LLUWSpecMasterSchema tLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
			LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
			LLUWSpecSubSchema tLLUWSpecSubSchema = new LLUWSpecSubSchema();

			tLLUWSpecMasterSchema = tLLUWSpecMasterSet.get(i);
			this.mReflections.transFields(tLCCSpecSchema, tLLUWSpecMasterSchema);
			this.mReflections.transFields(tLLUWSpecSubSchema, tLLUWSpecMasterSchema);
			mLCCSpecSet.add(tLCCSpecSchema);
			tLLUWSpecSubSet.add(tLLUWSpecSubSchema);
			// tLLUWSpecSubSchema.setMakeDate(CurrentDate);
			// tLLUWSpecSubSchema.setMakeTime(CurrentTime);
		}
		if (tLLUWSpecMasterSet.size() > 0) {
			mMMap.put(tLLUWSpecMasterSet, "DELETE");
			mMMap.put(tLLUWSpecSubSet, "DELETE&INSERT");
			mMMap.put(mLCCSpecSet, "INSERT");
		}
		return true;
	}

	/**
	 * 删除理赔核保加费的应收数据
	 * 
	 * @param pContNo
	 * @param pPolNo
	 * @return
	 */
	private boolean getDealLJSPay(LLUWPremMasterSchema tLLUWPremMasterSchema, String pDate) {

		logger.debug("-----处理理赔二次核保时,根据主险的加费承保结论-----处理应收-----开始-----");

		/**
		 * No1.0 -----缴费时点4.1---<---出险时点5.1---<---缴费时点6.1-----A
		 */
		String tSql = " select * from LJSPay where 1=1 "
				+ " and GetNoticeNo in (select distinct GetNoticeNo from LJSPayPerson where 1=1 "
				+ " and PolNo = '" + tLLUWPremMasterSchema.getPolNo() + "'"
				+ " and LastPayToDate >= to_date('" + pDate + "','yyyy-mm-dd') )"
				+ " and GetNoticeNo like 'CLMUW%'" + " and OtherNo = '" + mClmNo + "'"
				+ " and OtherNoType='9'";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", this.mClmNo); // 赔案号
		tTransferData.setNameAndValue("CalDate", pDate); // 计算时点
		tTransferData.setNameAndValue("DealDate", "1"); // 处理时间,1-包括计算时点,2不包括计算时点
		tTransferData.setNameAndValue("DealType", "1"); // 处理方式,1-指定日期之后,2-指定日期之前
		tTransferData.setNameAndValue("DealMode", "1"); // 处理模式,1-直接删除,2-不删除
		tTransferData.setNameAndValue("DealSql", tSql); // 指定查找的SQL语句

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(mLCPolSchema);

		LLBalRecedeFeeBL tLLBalRecedeFeeBL = new LLBalRecedeFeeBL();
		if (tLLBalRecedeFeeBL.submitData(tVData, mOperate) == false) {
			this.mErrors.copyAllErrors(tLLBalRecedeFeeBL.mErrors);
			return false;
		} else {
			VData tempVData = tLLBalRecedeFeeBL.getResult();
			MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			this.mMMap.add(tMMap);
		}

		logger.debug("-----处理理赔二次核保时,根据主险的加费承保结论-----处理应收-----结束-----");
		return true;
	}

	/**
	 * 处理理赔核保加费的实收数据
	 * 
	 * @param pContNo
	 * @param pPolNo
	 * @return
	 */
	private boolean getDealLJSPayPerson(LLUWPremMasterSchema pLLUWPremMasterSchema,
			String tPayStartDate, String pPayNo) {
		logger.debug("-----处理理赔二次核保时,根据主险的加费承保结论-----做加费处理-----开始-----");

		/**
		 * No1.0 定义变量
		 */
		double tDAddFee = 0;// 加费的金额
		double tDAddFeeLX = 0;// 加费的利息

		String tPolNo = StrTool.cTrim(pLLUWPremMasterSchema.getPolNo()); // 险种保单号
		String tDutyCode = StrTool.cTrim(pLLUWPremMasterSchema.getDutyCode());
		String tPayPlanType = StrTool.cTrim(pLLUWPremMasterSchema.getPayPlanType());// 交费计划类型
		String tPayIntv = StrTool.cTrim(String.valueOf(pLLUWPremMasterSchema.getPayIntv())); // 交费间隔
		double tDLLPrem = pLLUWPremMasterSchema.getPrem();// 加费金额

		/**
		 * No2.0 取实收信息的最大 原交至日期
		 */
		String tLastPayToDate = mLCPolSchema.getPaytoDate();

		/**
		 * No3.0 计算需要加费的期数
		 */
		int tIPeriodTimes = (int) mLLClaimPubFunBL.getLCPremPeriodTimes(tPayStartDate,
				tLastPayToDate, tPayIntv, tLastPayToDate);
		if (tIPeriodTimes <= 0) {
			logger.debug("--计算的期数为[" + tIPeriodTimes + "],小于0被过滤!!!");
			return true;
		}

		/**
		 * No4.0 找出原缴费项
		 */
		String tSql = "select * from LCPrem where 1=1 " + " and PolNo = '" + "?PolNo?" + "'"
				+ " and DutyCode = '" + "?DutyCode?" + "'" + " and PayPlanCode  like '000000%'"
				+ " and PayPlanType = '" + "?PayPlanType?" + "'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("PolNo", tPolNo);
		sqlbv9.put("DutyCode", tDutyCode);
		sqlbv9.put("PayPlanType", tPayPlanType);
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv9);
		if (tLCPremDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单险种[" + tPolNo + "]原责任信息没有取到!!!");
			return false;
		}
		if (tLCPremSet.size() > 1) {
			CError.buildErr(this, "保单险种[" + tPolNo + "]原责任信息大于1!!!");
			return false;
		}
		logger.debug("--------------------------------------------------------------------");
		logger.debug("--保单险种[" + tPolNo + "]原责任信息记录数:[" + tLCPremSet.size() + "]");
		logger.debug("--原责任信息SQL:[" + tSql + "]");
		logger.debug("--------------------------------------------------------------------");

		/**
		 * No5.0 如果原先有缴费信息，进行业务处理，判断是否生成生成缴费的应收信息
		 */
		if (tLCPremSet.size() == 1) {
			LCPremSchema tLCPremSchema = tLCPremSet.get(1);
			double tDLCPrem = tLCPremSchema.getPrem();// 加费金额

			if (tDLCPrem >= tDLLPrem) {
				logger.debug("--原加费为[" + tDLCPrem + "],核保加费为[" + tDLLPrem
						+ "],核保加费小于原加费被过滤!!!");
				return true;
			} else {
				tDAddFee = tDLLPrem - tDLCPrem;
			}
		} else {
			tDAddFee = tDLLPrem;
		}

		/**
		 * No8.0 生成核保加费的应收汇总，应收明细信息
		 */
		String tIntervalType = "M"; // 交费类型，按年、月或日。
		if (mLCPolSchema.getPayIntv() == 0 || mLCPolSchema.getPayIntv() == 12) {
			tIntervalType = "Y";
		}

		/**
		 * No8.1 声明计算利息的方法
		 */
		AccountManage tAccountManage = new AccountManage();
		tAccountManage.setPayEndYear(Integer.toString(mLCPolSchema.getPayEndYear()));
		tAccountManage.setPayIntv(Integer.toString(mLCPolSchema.getPayIntv()));

		String tCalDate = tPayStartDate; // 利息计算的开始时间
		for (int j = 1; j <= tIPeriodTimes; j++) {
			/**
			 * No8.3 生成加费明细信息
			 */
			// int tPayCount =
			// Integer.parseInt(String.valueOf(CurrentDate).substring(1,4)+String.valueOf(CurrentDate).substring(5,7)+String.valueOf(CurrentDate).substring(8,10)
			// + "0"+String.valueOf(j));
			int tPayCount = j;
			getLJSPayPerson(pLLUWPremMasterSchema, pPayNo, tDAddFee, tCalDate, tPayCount,"ZC");
			//************************************2011-2-10**************************************
			logger.debug("本次处理交费号:::::::::::::::::::" + pPayNo);			
			LCPremBLSet tLCPremBLSet = new LCPremBLSet();
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema.setContNo(pLLUWPremMasterSchema.getContNo());
			tLCPremSchema.setPolNo(pLLUWPremMasterSchema.getPolNo());
			tLCPremSchema.setDutyCode(pLLUWPremMasterSchema.getDutyCode());
			tLCPremSchema.setPayPlanCode(pLLUWPremMasterSchema.getPayPlanCode());
			tLCPremSchema.setPrem(pLLUWPremMasterSchema.getPrem());
			tLCPremSchema.setCurrency(mLCPolSchema.getCurrency());
			tLCPremBLSet.add(tLCPremSchema);
			
			LCDiscountSet qLCDiscountSet = new LCDiscountSet();
			LCDiscountDB tLCDiscountDB = new LCDiscountDB();
			tLCDiscountDB.setContNo(mLCContSchema.getContNo());
			tLCDiscountDB.setPolNo(mLCPolSchema.getPolNo());			
			qLCDiscountSet = tLCDiscountDB.query();
			//************增加折扣处理 start
			if(qLCDiscountSet!=null && qLCDiscountSet.size()>0)
			{
				DiscountCalBL tDiscountCalBL = new DiscountCalBL();
				VData tzkVData = new VData();
				tzkVData.add(mLCPolSchema);
				tzkVData.add(tLCPremBLSet);
				tzkVData.add(qLCDiscountSet);
				tzkVData.add(pPayNo);
				//得到该保单折扣减去的钱 ，为负值
				if(!tDiscountCalBL.calculate(tzkVData))
				{
					CError.buildErr(this, "折扣计算失败！");
					return false;
				}
				
				//得到折扣和应收子表数据
				VData rVData = tDiscountCalBL.getResult();
				LJSPayPersonSet tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
				if(tLJSPayPersonSet!=null)
				{
					for(int k=1;k<=tLJSPayPersonSet.size();k++)
					{
						getLJSPayPerson(pLLUWPremMasterSchema, pPayNo, tLJSPayPersonSet.get(k).getSumActuPayMoney(), tCalDate, tPayCount,tLJSPayPersonSet.get(k).getPayType());	
					}
				}
										
			}	
			//************增加折扣处理 end			
			//************************************2011-2-10***********************************************

			/**
			 * No8.4 加费的总金额
			 */
			mDAddFeeSum = mDAddFeeSum + tDAddFee;

			/**
			 * No8.5 计算利息
			 */
			String tRiskcode = mLCPolSchema.getRiskCode();
			String tCurrency = mLCPolSchema.getCurrency();

			// 复利 趸交保费本金及利息 趸交利息为贷款信息
			double tDInsMoney = mDAddFeeSum
					* AccountManage.calMultiRateMS(tPayStartDate, mRgtDate, tRiskcode, "00", "L",
							"C", "Y",tCurrency);

			/**
			 * No8.6 利息的总金额
			 */
			tDAddFeeLX = Arith.round(tDAddFeeLX + tDInsMoney, 2);// 用于生成加费利息记录
			//mDAddFeeIns = Arith.round(mDAddFeeIns + tDInsMoney, 2);// 用于生成应收汇总记录

			/**
			 * No8.7 计算下一期的开始时间
			 */
			tCalDate = PubFun.calDate(tCalDate, Integer.parseInt(tPayIntv), "M", tPayStartDate);// 利息计算的下一期的开始时间
		}

		logger.debug("----处理理赔二次核保时,根据主险的加费承保结论-----做加费处理-----结束---");
		return true;
	}

	/**
	 * 目的：生成应收汇总信息
	 * 
	 * @param pDAddFee
	 * @param pCalDate
	 */
	private void getLJSPay(String pPayNo, String pCalDate) {		
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		//按照币种汇总LJSpay
		Hashtable tCurrencyMap = new Hashtable();
		Hashtable taxMap = new Hashtable(); //获取净额
		Hashtable netMap = new Hashtable();  //获取税额
		for(int i=1;i<=mLJSPayPersonSaveSet.size();i++)
		{
			//按照币种汇总
			
			String tCurrency = mLJSPayPersonSaveSet.get(i).getCurrency();
			if(!tCurrencyMap.containsKey(tCurrency))
			{
				tCurrencyMap.put(tCurrency, mLJSPayPersonSaveSet.get(i).getSumDuePayMoney());
				taxMap.put(tCurrency, mLJSPayPersonSaveSet.get(i).getTaxAmount());
				netMap.put(tCurrency, mLJSPayPersonSaveSet.get(i).getNetAmount());
			}
			else
			{
				double tempSumpay = PubFun.round((Double)tCurrencyMap.get(tCurrency), 2) + PubFun.round(mLJSPayPersonSaveSet.get(i).getSumDuePayMoney(),2);
				tCurrencyMap.put(tCurrency, tempSumpay);
				
				double taxSumpay = PubFun.round((Double)taxMap.get(tCurrency), 2) + PubFun.round(mLJSPayPersonSaveSet.get(i).getTaxAmount(),2);
				taxMap.put(tCurrency, taxSumpay);
				
				double netSumpay = PubFun.round((Double)netMap.get(tCurrency), 2) + PubFun.round(mLJSPayPersonSaveSet.get(i).getNetAmount(),2);
				netMap.put(tCurrency, netSumpay);
				
			}			
		}	
		
		Enumeration eKey=tCurrencyMap.keys(); 
		while(eKey.hasMoreElements()) 
		{ 
			String key=(String)eKey.nextElement();
			double tValue = PubFun.round(((Double)tCurrencyMap.get(key)),2);
			double aTaxAmount = PubFun.round(((Double)taxMap.get(key)),2);
			double aNetAmount = PubFun.round(((Double)netMap.get(key)),2);
			tLJSPaySchema = new LJSPaySchema();
			tLJSPaySchema.setGetNoticeNo(pPayNo);// 应收通知书号码
			tLJSPaySchema.setOtherNo(mClmNo); // 赔案号
			tLJSPaySchema.setOtherNoType("5"); // 类型(应收表中5已被使用)
			tLJSPaySchema.setAppntNo(mLCPolSchema.getAppntNo()); // 投保人客户号码
			tLJSPaySchema.setCurrency(key);
			tLJSPaySchema.setSumDuePayMoney(tValue); // 应收金额
			tLJSPaySchema.setTaxAmount(aTaxAmount); //应收总税额
			tLJSPaySchema.setNetAmount(aNetAmount); //应收总净额
			tLJSPaySchema.setPayDate(pCalDate); // 交费日期
			tLJSPaySchema.setSerialNo(mLCContSchema.getContNo()); // 流水号存成合同号
			tLJSPaySchema.setStartPayDate(pCalDate); // 交费最早应缴日期保存上次交至日期

			tLJSPaySchema.setBankOnTheWayFlag("0");
			tLJSPaySchema.setBankSuccFlag("0");
			tLJSPaySchema.setSendBankCount(0); // 送银行次数
			tLJSPaySchema.setApproveCode(mLCContSchema.getApproveCode());
			tLJSPaySchema.setApproveDate(mLCContSchema.getApproveDate());
			tLJSPaySchema.setRiskCode("000000");
			tLJSPaySchema.setSendBankCount(0);

			tLJSPaySchema.setAgentCom(mLCContSchema.getAgentCom());
			tLJSPaySchema.setAgentCode(mLCContSchema.getAgentCode());
			tLJSPaySchema.setAgentType(mLCContSchema.getAgentType());
			tLJSPaySchema.setAgentGroup(mLCContSchema.getAgentGroup());

			tLJSPaySchema.setOperator(mGlobalInput.Operator);
			tLJSPaySchema.setManageCom(mLCContSchema.getManageCom());
			tLJSPaySchema.setMakeDate(CurrentDate);
			tLJSPaySchema.setMakeTime(CurrentTime);
			tLJSPaySchema.setModifyDate(CurrentDate);
			tLJSPaySchema.setModifyTime(CurrentTime);

			mLJSPaySaveSet.add(tLJSPaySchema);
		}		
	}

	/**
	 * 目的：生成应收明细信息
	 * 
	 * @param pLLUWPremMasterSchema
	 * @param pPayNo
	 * @param pDAddFee
	 * @param pCalDate
	 * @param pPayCount
	 */
	private void getLJSPayPerson(LLUWPremMasterSchema pLLUWPremMasterSchema, String pPayNo,
			double pDAddFee, String pCalDate, int pPayCount,String pPayType) {
		LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
		tLJSPayPersonSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
		tLJSPayPersonSchema.setContNo(mLCPolSchema.getContNo());
		tLJSPayPersonSchema.setPolNo(mLCPolSchema.getPolNo());
		tLJSPayPersonSchema.setAppntNo(mLCPolSchema.getAppntNo());
		tLJSPayPersonSchema.setGetNoticeNo(pPayNo);

		tLJSPayPersonSchema.setPayCount(pPayCount);
		tLJSPayPersonSchema.setDutyCode(pLLUWPremMasterSchema.getDutyCode());
		tLJSPayPersonSchema.setPayPlanCode(pLLUWPremMasterSchema.getPayPlanCode());

		tLJSPayPersonSchema.setManageCom(pLLUWPremMasterSchema.getManageCom());
		tLJSPayPersonSchema.setPayIntv(pLLUWPremMasterSchema.getPayIntv());
		tLJSPayPersonSchema.setAgentCode(mLCPolSchema.getAgentCode());

		tLJSPayPersonSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
		tLJSPayPersonSchema.setAgentCom(mLCPolSchema.getAgentCom());
		tLJSPayPersonSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
		tLJSPayPersonSchema.setAgentType(mLCPolSchema.getAgentType());
		tLJSPayPersonSchema.setPayAimClass("1");
		tLJSPayPersonSchema.setSumActuPayMoney(pDAddFee);
		tLJSPayPersonSchema.setSumDuePayMoney(pDAddFee);
		tLJSPayPersonSchema.setLastPayToDate(pCalDate);

		String tCurPayToDate = PubFun.calDate(pCalDate, pLLUWPremMasterSchema.getPayIntv(), "M",
				pCalDate);
		tLJSPayPersonSchema.setCurPayToDate(tCurPayToDate);
		tLJSPayPersonSchema.setPayType(pPayType);
		tLJSPayPersonSchema.setPayDate(pCalDate);
		tLJSPayPersonSchema.setRiskCode(mLCPolSchema.getRiskCode());

		tLJSPayPersonSchema.setBankOnTheWayFlag("0");
		tLJSPayPersonSchema.setBankSuccFlag("0");

		tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
		tLJSPayPersonSchema.setMakeDate(CurrentDate);
		tLJSPayPersonSchema.setMakeTime(CurrentTime);
		tLJSPayPersonSchema.setModifyDate(CurrentDate);
		tLJSPayPersonSchema.setModifyTime(CurrentTime);
		tLJSPayPersonSchema.setCurrency(mLCPolSchema.getCurrency());

		mLJSPayPersonSaveSet.add(tLJSPayPersonSchema);
	}

	/**
	 * 目的：生成二次核保加费利息的收费记录
	 * 
	 * @param pPayNo
	 * @param pCalValue
	 */
	private void getLJSPayClaim(String pPayNo, double pCalValue) {
		LJSPayClaimSchema tLJSPayClaimSchema = new LJSPayClaimSchema();

		tLJSPayClaimSchema.setGetNoticeNo(pPayNo);
		tLJSPayClaimSchema.setFeeOperationType("C30"); // 业务类型
		tLJSPayClaimSchema.setSubFeeOperationType("C3002"); // 子业务类型
		tLJSPayClaimSchema.setFeeFinaType("LX"); // 财务类型

		tLJSPayClaimSchema.setOtherNo(mClmNo); // 其它号码
		tLJSPayClaimSchema.setOtherNoType("5"); // 其它号码类型

		tLJSPayClaimSchema.setDutyCode("0"); // 责任编码
		tLJSPayClaimSchema.setGetDutyKind("0"); // 给付责任类型
		tLJSPayClaimSchema.setGetDutyCode("0"); // 给付责任编码

		tLJSPayClaimSchema.setGrpContNo(mLCPolSchema.getGrpContNo()); // 集体合同号码
		tLJSPayClaimSchema.setContNo(mLCPolSchema.getContNo()); // 合同号码
		tLJSPayClaimSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo()); // 集体保单号码
		tLJSPayClaimSchema.setPolNo(mLCPolSchema.getPolNo()); // 保单号码

		tLJSPayClaimSchema.setKindCode(mLCPolSchema.getKindCode()); // 险类编码
		tLJSPayClaimSchema.setRiskCode(mLCPolSchema.getRiskCode()); // 险种编码
		tLJSPayClaimSchema.setRiskVersion(mLCPolSchema.getRiskVersion()); // 险种版本

		tLJSPayClaimSchema.setSaleChnl(mLCPolSchema.getSaleChnl()); // 销售渠道
		tLJSPayClaimSchema.setAgentCode(mLCPolSchema.getAgentCode()); // 代理人编码
		tLJSPayClaimSchema.setAgentGroup(mLCPolSchema.getAgentGroup()); // 代理人组别

		tLJSPayClaimSchema.setGetDate(this.CurrentDate); // 给付日期
		tLJSPayClaimSchema.setPay(pCalValue); // 赔付金额

		tLJSPayClaimSchema.setManageCom(mLCPolSchema.getManageCom()); // 管理机构
		tLJSPayClaimSchema.setAgentCom(mLCPolSchema.getAgentCom()); // 代理机构
		tLJSPayClaimSchema.setAgentType(mLCPolSchema.getAgentType()); // 代理机构内部分类

		tLJSPayClaimSchema.setOperator(mGlobalInput.Operator); // 操作员
		tLJSPayClaimSchema.setMakeDate(this.CurrentDate); // 入机日期
		tLJSPayClaimSchema.setMakeTime(this.CurrentTime); // 入机时间
		tLJSPayClaimSchema.setModifyDate(this.CurrentDate); // 入机日期
		tLJSPayClaimSchema.setModifyTime(this.CurrentTime); // 入机时间

		mLJSPayClaimSaveSet.add(tLJSPayClaimSchema);

	}

	/**
	 * 合同不生效处理
	 * 
	 * @return
	 */
	private boolean dealNoInEffect() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 更新理赔核保批次生效标志信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LLCUWBatchSchema tLLCUWBatchSchema = mLLClaimPubFunBL
				.getLLCUWBatch(mClmNo, mBatNo, mContNo);
		if (tLLCUWBatchSchema == null) {

			CError.buildErr(this, "查询不到案件的核保信息!");
			return false;
		}

		if (tLLCUWBatchSchema.getState().equals("0")) {

			CError.buildErr(this, "合同号[" + mContNo + "],批次号[" + mBatNo + "]的核保处理没有完成,不能进行此操作!!!");
			return false;
		}

		if (tLLCUWBatchSchema.getInEffectFlag().equals("1")
				|| tLLCUWBatchSchema.getInEffectFlag().equals("2")) {

			CError.buildErr(this, "合同号[" + mContNo + "],批次号[" + mBatNo + "]的理赔处理结论已经生效,不能进行此操作!!!");
			return false;
		}

		tLLCUWBatchSchema.setInEffectFlag(mInEffectFlag);
		tLLCUWBatchSchema.setModifyDate(PubFun.getCurrentDate());
		tLLCUWBatchSchema.setModifyTime(PubFun.getCurrentTime());

		mMMap.put(tLLCUWBatchSchema, "UPDATE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 如果存在加费信息,则转移到轨迹表中,并删除
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);

		mResult.clear();
		mResult.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			CError.buildErr(this, "数据提交失败,原因" + tPubSubmit.mErrors.getError(0).errorMessage);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "96";

		String tOperate = "AddFeeCancela1"; // DELETE
		String tInEffectFlag = "1"; // 1生效，2不生效
		String tClmNo = "90000019641";
		String tBatNo = "6100000000753";
		String tContNo = "BJ510227011000249";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", tClmNo);
		tTransferData.setNameAndValue("BatNo", tBatNo);
		tTransferData.setNameAndValue("ContNo", tContNo);
		tTransferData.setNameAndValue("InEffectFlag", tInEffectFlag);

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLUWResultCvalidateBL tLLUWResultCvalidateBL = new LLUWResultCvalidateBL();
		tLLUWResultCvalidateBL.submitData(tVData, tOperate);

		logger.debug("----------------二次核保生效处理后台提示信息打印------开始----------------");
		int n = tLLUWResultCvalidateBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "提示信息: " + tLLUWResultCvalidateBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
		logger.debug("----------------二次核保生效处理后台提示信息打印------结束----------------");
	}
}
