package com.sinosoft.lis.bq;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMLoanDB;
import com.sinosoft.lis.db.LMRevenueRateDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LMLoanSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LMLoanSet;
import com.sinosoft.lis.vschema.LMRevenueRateSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单质押贷款BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorLNDetailBL {
private static Logger logger = Logger.getLogger(PEdorLNDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 改变保单状态相关 */
	private Reflections mReflections = new Reflections();

	private LPContStateSet mLPContStateSet = new LPContStateSet();
	
	private LPLoanSet tLPLoanSet = new LPLoanSet();

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private TransferData mTransferData = new TransferData();
	private String mCurrency = "";
	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 用户选择要贷款的金额 */
	private double mUserWantLoan = 0.0;

	/** 查到的“（半）年利率”标志，“Y”是年，“H”是半年 */
	private String mRateFlag = "";

	/** 查到的利息” */
	private double mInterest = 0.0;


	/** 代扣印花税率 */
	private double mRevenueRate = 0.0; // add by zhangtao 2006-06-27

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();
	
	

	// 主险号码
	String tMainPolNo = "";
	
	String tMainRiskCode = "";

	/**
	 * 是否是帐户险
	 */
	String tFlag = "0";

	public PEdorLNDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
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

		// 数据查询，初始化界面时初始化MulLine用
		if (mOperate.equals("QUERY||MAIN")) {
			if (!getArrayForFormatPage()) {
				return false;
			} else {
				return true;
			}
		}

		// 数据校验操作
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

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

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 保全项目校验
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			if(mLPEdorItemSchema==null ||  "".equals(mLPEdorItemSchema.getEdorAcceptNo()) || mLPEdorItemSchema.getEdorAcceptNo()==null)
			{
				CError tError = new CError();
				tError.moduleName = "PEdorLNDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "接收数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}else
			{

				LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
				tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
				tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());

				mLPEdorItemSchema = tLPEdorItemDB.query().get(1);	
			}

			// 如果是查询
			if (mOperate.equals("QUERY||MAIN")) {
				return true;
			}
			// 不是查询
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			Double tempDouble = new Double(0.0);
			tempDouble = (Double) mInputData.getObjectByObjectName("Double", 0);
			mUserWantLoan = tempDouble.doubleValue();
			//取两位精度
			mUserWantLoan=PubFun.round(mUserWantLoan, 2);
			
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			
			mCurrency = (String)mTransferData.getValueByName("Currency");
			if(mCurrency == null || mCurrency.equals("")){
				CError.buildErr(this, "传输币种信息失败!");
				return false;
			}

			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorLNDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
//		if (!mOperate.equals("QUERY||MAIN")) {
//			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
//			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
//			mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));
//			if (tLPEdorItemDB.getEdorState().trim().equals("2")) {
//				// @@错误处理
//				CError tError = new CError();
//				tError.moduleName = "PEdorLNDetailBL";
//				tError.functionName = "checkData";
//				tError.errorMessage = "该保全已经申请确认不能修改！";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			//删除保全暂收费数据
			String DelStr="delete from LJSGetEndorse where contno='?contno?' and Endorsementno='?Endorsementno?' "
			             + " and  Feeoperationtype='?Feeoperationtype?' and currency = '?mCurrency?'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(DelStr);
			sbv.put("contno", mLPEdorItemSchema.getContNo());
			sbv.put("Endorsementno", mLPEdorItemSchema.getEdorNo());
			sbv.put("Feeoperationtype", mLPEdorItemSchema.getEdorType());
			sbv.put("mCurrency", mCurrency);
			mMap.put(sbv, "DELETE");
			
			 //获得贷款截止日期，一般为贷款日6个月后
			 String tRequitalDate =
			 PubFun.calDate(this.mLPEdorItemSchema.getEdorValiDate(),6,"M",null);
			 tRequitalDate = PubFun.calDate(tRequitalDate,-1,"D",null);

			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();

			// 生成“保全借款业务表”数据
			LPLoanSchema tLPLoanSchema = new LPLoanSchema();
			tLPLoanSchema.setContNo(mLPEdorItemSchema.getContNo());
			// 查询主险号
			String tSql = "SELECT PolNo,riskcode FROM LCPol WHERE ContNo='?ContNo?' and currency = '?mCurrency?' order by polno";
			SSRS tSSRS = new SSRS();
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSql);
			sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
			sbv1.put("mCurrency", mCurrency);
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sbv1);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorLNDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询主险号时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tMainRiskCode=tSSRS.GetText(1, 2);
			// 取主险保单号，若是多主险则取最早的主险保单号
			tLPLoanSchema.setPolNo(tSSRS.GetText(1, 1));
			tLPLoanSchema.setEdorType("LN");
			tLPLoanSchema.setSerialNo(""); // 现在不用了
			tLPLoanSchema.setActuGetNo(mLPEdorItemSchema.getEdorAcceptNo()); // 暂时填这个，确认时从实付总表里取
			tLPLoanSchema.setLoanType("0"); // 贷款
			tLPLoanSchema.setCurrency(mCurrency);
			// 获得本次贷款是第几次
			tSql = "SELECT to_number(max(OrderNo))+1 FROM LOLoan WHERE ContNo='?ContNo?' and LoanType='0' and currency = '?mCurrency?'";

			int tOrderNo = 1;
			try {
				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql(tSql);
				sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
				sbv2.put("mCurrency", mCurrency);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sbv2);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					tOrderNo = Integer.parseInt(tSSRS.GetText(1, 1));
				}
			} catch (Exception e) {
				tOrderNo = 1;
			}
			tLPLoanSchema.setOrderNo(String.valueOf(tOrderNo));
			tLPLoanSchema.setLoanDate(mLPEdorItemSchema.getEdorAppDate()); // 取值为申请日期此值在还款后后不变

			tLPLoanSchema.setInputFlag("1"); // 按照描述进行利息计算
			tLPLoanSchema.setSpecifyRate("2");
//			String tCountDate="";	
//			tCountDate=PubFun.calDate(mLPEdorItemSchema.getEdorAppDate(), 1, "D", "");
//			double [] tResult = BqNameFun.getLastSumLoanMoney(mLPEdorItemSchema
//					.getContNo(),tCountDate,"0");
//			if(tResult==null)
//			{
//				CError tError = new CError();
//				tError.moduleName = "PEdorLNDetailBL";
//				tError.functionName = "dealData";
//				tError.errorMessage = "计算保单借款有误！";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//			double tLastLoanMoney=tResult[0];
//			double tLastSumLoanMoney=tResult[0]+tResult[1];
			//tLPLoanSchema.setLastSumLoanMoney(0);
			//tLPLoanSchema.setCurLoanMoney(mUserWantLoan);
			//tLPLoanSchema.setPreInterest(0);
			tLPLoanSchema.setLeaveMoney(mUserWantLoan);// 
			tLPLoanSchema.setSumMoney( mUserWantLoan); //
			tLPLoanSchema.setPayOffFlag("0");
			tLPLoanSchema.setPayOffDate(""); //贷款止期
			tLPLoanSchema.setOperator(this.mGlobalInput.Operator);
			tLPLoanSchema.setMakeDate(strCurrentDate);
			tLPLoanSchema.setMakeTime(strCurrentTime);
			tLPLoanSchema.setModifyDate(strCurrentDate);
			tLPLoanSchema.setModifyTime(strCurrentTime);
			tLPLoanSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			//存放本次借款日期,以后每次还款会不断发生变化，因为每次还款，至少要还清截止还款日时的利息，并更新本金，重新置此借款日期
			//
			tLPLoanSchema.setNewLoanDate(mLPEdorItemSchema.getEdorAppDate());
			// 获得主险其它信息
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLPLoanSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorLNDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询主险相关信息时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCPolSchema = tLCPolDB.getSchema();

			LMLoanDB tLMLoanDB = new LMLoanDB();
			LMLoanSchema tLMLoanSchema = new LMLoanSchema();
			LMLoanSet tLMLoanSet = new LMLoanSet();
			tLMLoanDB.setRiskCode(tLCPolSchema.getRiskCode());
			tLMLoanSet = tLMLoanDB.query();
			if (tLMLoanDB.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "PEdorLNDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询主险借款描叙信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLMLoanSet.size() < 1) {
				CError tError = new CError();
				tError.moduleName = "PEdorLNDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询主险借款描叙信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLMLoanSchema = tLMLoanSet.get(1);
			// 计算利息，返回到界面用
            
			double mRate=calRateByDate(mLPEdorItemSchema.getEdorAppDate(),mCurrency);
			 if(mRate+1==0)
			 {
					CError tError = new CError();
					tError.moduleName = "PEdorLNDetailBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询借款利率信息失败！";
					this.mErrors.addOneError(tError);
					return false;
			 }
			tLPLoanSchema.setInterestType(tLMLoanSchema.getInterestType());
			tLPLoanSchema.setInterestRate(mRate);
			tLPLoanSchema.setInterestMode(tLMLoanSchema.getInterestMode());
			tLPLoanSchema.setRateCalType(tLMLoanSchema.getRateCalType());
			tLPLoanSchema.setRateCalCode(tLMLoanSchema.getRateCalCode());
			tLPLoanSet.add(tLPLoanSchema);
			mMap.put(tLPLoanSet, "DELETE&INSERT");

			// 生成“批改补退费表（应收/应付）”数据用
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			// 查询“单位名称”
			String tGrpName = "";
			tSql = "SELECT a.GrpName" + " FROM LCAddress a,LCInsured b,LCPol c"
					+ " WHERE a.CustomerNo=b.InsuredNo"
					+ " and a.AddressNo=b.AddressNo"
					+ " and b.InsuredNo=c.InsuredNo" + " and b.ContNo=c.ContNo"
					+ " and c.PolNo='?PolNo?'"; // 主险
			tSSRS = new SSRS();
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(tSql);
			sbv3.put("PolNo", tLCPolSchema.getPolNo());
			tSSRS = tExeSQL.execSQL(sbv3);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				tGrpName = tSSRS.GetText(1, 1);
			}

			tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchema
					.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchema.setFeeOperationType("LN");

			// 查询“补/退费财务类型”
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = "SELECT CodeName" + " FROM ((select '1' as flag,CodeName"
					+ " from LDCode1"
					+ " where CodeType='LN' and Code='DK' and Code1='?Code1?')" + " union"
					+ " (select '2' as flag,CodeName" + " from LDCode1"
					+ " where CodeType='LN' and Code='DK' and Code1='000000'))"
					+ " WHERE rownum=1" + " ORDER BY flag";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "SELECT CodeName" + " FROM ((select '1' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='LN' and Code='DK' and Code1='?Code1?')" + " union"
						+ " (select '2' as flag,CodeName" + " from LDCode1"
						+ " where CodeType='LN' and Code='DK' and Code1='000000')) g"
						+ " ORDER BY flag" + " limit 0,1";
			}
			// 查询
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql(tSql);
			sbv4.put("Code1", tLCPolSchema.getRiskCode());
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sbv4);
			if (tSSRS == null && tSSRS.MaxRow <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorLNDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询还款的“补/退费财务类型”时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1, 1));

			tLJSGetEndorseSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLJSGetEndorseSchema.setContNo(tLCPolSchema.getContNo());
			tLJSGetEndorseSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLJSGetEndorseSchema.setPolNo(tLCPolSchema.getPolNo());
			tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchema.setOtherNoType("3");
			tLJSGetEndorseSchema.setDutyCode("000000");
			tLJSGetEndorseSchema.setPayPlanCode("000000");
			tLJSGetEndorseSchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLJSGetEndorseSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLJSGetEndorseSchema.setGetDate(strCurrentDate);
			tLJSGetEndorseSchema.setGetMoney(BqNameFun
					.getRound(mUserWantLoan));
			tLJSGetEndorseSchema.setKindCode(tLCPolSchema.getKindCode());
			tLJSGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLJSGetEndorseSchema.setRiskVersion(tLCPolSchema.getRiskVersion());
			tLJSGetEndorseSchema.setManageCom(tLCPolSchema.getManageCom());
			tLJSGetEndorseSchema.setAgentCom(tLCPolSchema.getAgentCom());
			tLJSGetEndorseSchema.setAgentType(tLCPolSchema.getAgentType());
			tLJSGetEndorseSchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLJSGetEndorseSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
			tLJSGetEndorseSchema.setGrpName(tGrpName);
			tLJSGetEndorseSchema.setHandler(""); // 暂时不知道如何处理
			tLJSGetEndorseSchema.setPolType("1"); // 相当于退费
			tLJSGetEndorseSchema.setGetFlag("1");
			tLJSGetEndorseSchema.setSerialNo(""); // 暂时不知道如何处理
			tLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
			tLJSGetEndorseSchema.setMakeDate(strCurrentDate);
			tLJSGetEndorseSchema.setMakeTime(strCurrentTime);
			tLJSGetEndorseSchema.setModifyDate(strCurrentDate);
			tLJSGetEndorseSchema.setModifyTime(strCurrentTime);
			tLJSGetEndorseSchema.setCurrency(mCurrency);
			tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_LoanCorpus);
	          //营改增 add zhangyingfeng 2016-07-13
	          //价税分离 计算器
	          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
	          //end zhangyingfeng 2016-07-13
			mMap.put(tLJSGetEndorseSchema, "DELETE&INSERT");

			// 修改保单状态为贷款状态
			if (!changeContState(mLPEdorItemSchema.getContNo(), "Loan", "1",
					mLPEdorItemSchema.getEdorAppDate(), "000000")) {
				return false;
			}
			
			
			//add by jiaqiangli 2009-04-24 增加校验以防止主键冲突
			if (mLPContStateSet.size() >= 2) {
				if (mLPContStateSet.get(1).getStartDate() != null && mLPContStateSet.get(2).getStartDate() != null) {
					//这里只是简单地判断startdate是否相同，默认主键中的其他字段都置成相同
					if (mLPContStateSet.get(1).getStartDate().equals(mLPContStateSet.get(2).getStartDate())) {
						CError.buildErr(this, "当天借款当天还清的保全操作不允许，反之亦然！");
						return false;
					}
				}
			}
			//add by jiaqiangli 2009-04-24 增加校验以防止主键冲突
			
			mMap.put(mLPContStateSet, "DELETE&INSERT");
			double dRevenueAmount = getRevenueAmount(mUserWantLoan); // 代扣印花税金额
			if (dRevenueAmount == -1) {
				return false;
			}
			double GetMoney = mUserWantLoan - dRevenueAmount;
			// 创建印花税财务记录
			if(dRevenueAmount>0)
			{
				String sFeeType = BqCalBL.getFinType(mLPEdorItemSchema
						.getEdorType(), "RV", tLCPolSchema.getPolNo());
				LJSGetEndorseSchema tLJSGetEndorseSchema2 = new LJSGetEndorseSchema();
				tLJSGetEndorseSchema2.setSchema(tLJSGetEndorseSchema);
				tLJSGetEndorseSchema2.setFeeFinaType(sFeeType);
				tLJSGetEndorseSchema2.setGetMoney(BqNameFun
						.getRound(dRevenueAmount));
				tLJSGetEndorseSchema2.setSubFeeOperationType(BqCode.Pay_Revenue);
				tLJSGetEndorseSchema2.setGetFlag("0");
		          //营改增 add zhangyingfeng 2016-07-13
		          //价税分离 计算器
		          TaxCalculator.calBySchema(tLJSGetEndorseSchema2);
		          //end zhangyingfeng 2016-07-13
				mMap.put(tLJSGetEndorseSchema2, "DELETE&INSERT");
				
			}

			mLPEdorItemSchema.setEdorState("3");
			mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
			mLPEdorItemSchema.setModifyDate(strCurrentDate);
			mLPEdorItemSchema.setModifyTime(strCurrentTime);
			mLPEdorItemSchema.setGetMoney(GetMoney); // 付费金额需要从贷款金额中扣除印花税
			//保存借款金额
			mLPEdorItemSchema.setStandbyFlag1(String.valueOf(mUserWantLoan));
			mMap.put(mLPEdorItemSchema, "UPDATE");

			mResult.clear();
			mResult.add(mMap);
			mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
			mBqCalBase.setPolNo(tLCPolSchema.getPolNo());
			mBqCalBase.setRiskCode(tLCPolSchema.getRiskCode());
			mBqCalBase.setCashFlag("Y");
			mResult.add(mBqCalBase);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorLNDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误！" + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 获得一个二维数组，初始化MulLine用 行内容：险种代码、险种名称、保额、保费、现金价值、贷款限额、贷款利率、险种号（隐藏列）
	 * 
	 * @return true--Success,false--Fail
	 */
	private boolean getArrayForFormatPage() {

		// 获得险种项目，即二维数组行数（注意：此处的sql查询所有险种，包括短期附加险和可以借款的险种）
		String tSql = "SELECT  "
			+ " Amnt,Prem,riskcode,polno,a.Currency"
			+ " FROM LCPol a" + " WHERE ContNo='?ContNo?' and AppFlag='1' and riskcode in (select riskcode from lmriskapp where loanflag='Y')";
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ContNo", this.mLPEdorItemSchema.getContNo());
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow == 0) {
			// 没有险种信息
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorLNDetailBL";
			tError.functionName = "getArrayForFormatPage";
			tError.errorMessage = "没有查询到险种项目信息！";
			this.mErrors.addOneError(tError);
			return false;
		}


		double tResult[]= new double[2]; 

		Map curCashMap = new HashMap();
		Map curAmntMap = new HashMap();
		Map curPremMap = new HashMap();
		Map curCanLoanMap = new HashMap();
		Map curHasLoanMap = new HashMap();
		Map curLoanDateMap = new HashMap();
		Map curRateMap = new HashMap();
		Map curLoanMap = new HashMap();
		for(int k=1;k<=tSSRS.MaxRow;k++)
		{
			String tCurrency = tSSRS.GetText(k, 5);
			double tAmnt = Double.parseDouble(tSSRS.GetText(k, 1));
			double tPrem = Double.parseDouble(tSSRS.GetText(k, 2));	
			double tCash=getPolCashValue(tSSRS.GetText(k, 3),tSSRS.GetText(k, 4), mLPEdorItemSchema.getEdorAppDate());
			// 现金价值
			if (tCash+1==0) {
				CError tError = new CError();
				tError.moduleName = "PEdorLNDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询借款利率信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			double tCurCash = 0.0;
			double tCurAmnt = 0.0;
			double tCurPrem = 0.0;
			if(curCashMap.containsKey(tCurrency)){
				tCurCash = (Double)curCashMap.get(tCurrency);
				tCurAmnt = (Double)curAmntMap.get(tCurrency);
				tCurPrem = (Double)curPremMap.get(tCurrency);
			}
			tCurCash += tCash;
			tCurAmnt += tAmnt;
			tCurPrem += tPrem;

			curCashMap.put(tCurrency, tCurCash);
			curAmntMap.put(tCurrency, tCurAmnt);
			curPremMap.put(tCurrency, tCurPrem);
		}

		Iterator curIT = curCashMap.keySet().iterator();
		while(curIT.hasNext()){
			String tCurrency = (String)curIT.next();
			double tSumCash = (Double)curCashMap.get(tCurrency);
			tResult=getMaxLoan(tSumCash, mLPEdorItemSchema.getContNo(),tCurrency);
			double tCanLoan = tResult[0];
			double tHasLoan = tResult[1];
			String tSLoan = "SELECT max(loandate) FROM loloan a WHERE ContNo='?ContNo?' and PayOffFlag='0' and loantype='0' and Currency = '?tCurrency?'";
            SQLwithBindVariables sbv=new SQLwithBindVariables();
            sbv.sql(tSLoan);
            sbv.put("ContNo", this.mLPEdorItemSchema.getContNo());
            sbv.put("tCurrency", tCurrency);
			String tLoanDate=tExeSQL.getOneValue(sbv);
			if("".equals(tLoanDate))
			{
				tLoanDate=mLPEdorItemSchema.getEdorAppDate();
			}

			double tRate=calRateByDate(mLPEdorItemSchema.getEdorAppDate(),tCurrency);
			if (tRate+1==0) {
				CError tError = new CError();
				tError.moduleName = "PEdorLNDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询借款利率信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			String tLoanSql = "select sum(SumMoney) From LPLoan where EdorNo = '?EdorNo?' and currency = '?tCurrency?' and contno = '?contno?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tLoanSql);
			sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv1.put("tCurrency", tCurrency);
			sbv1.put("contno", mLPEdorItemSchema.getContNo());
			String tLoanMoney = tExeSQL.getOneValue(sbv1);
			if(tLoanMoney == null){
				tLoanMoney = "";
			}
			curCanLoanMap.put(tCurrency, tCanLoan);
			curHasLoanMap.put(tCurrency, tHasLoan);
			curLoanDateMap.put(tCurrency, tLoanDate.substring(0, 10));
			curRateMap.put(tCurrency, tRate);
			curLoanMap.put(tCurrency, tLoanMoney);
		}

		// 计算利率，在界面显示用
		int curSize = curCashMap.keySet().size();
		String rStrArray[][] = new String[curSize][10];
		int c = 0;

		curIT = curCashMap.keySet().iterator();
		while(curIT.hasNext()){
			String tCurrency = (String)curIT.next();
			// 合同号
			rStrArray[c][0] =mLPEdorItemSchema.getContNo();
			// 最近一次借款日期
			rStrArray[c][1] =(String)curLoanDateMap.get(tCurrency);
			// 保额
			rStrArray[c][2] =String.valueOf((Double)curAmntMap.get(tCurrency)) ;
			// 保费
			rStrArray[c][3] =String.valueOf((Double)curPremMap.get(tCurrency)) ;
			// 现价
			rStrArray[c][4] = BqNameFun.getRound((Double)curCashMap.get(tCurrency));
			// 贷款额度
			rStrArray[c][5] = String.valueOf(BqNameFun.getRound((Double)curCanLoanMap.get(tCurrency)));
			rStrArray[c][6]=String.valueOf((Double)curRateMap.get(tCurrency));
			rStrArray[c][7] = String.valueOf(BqNameFun.getRound((Double)curHasLoanMap.get(tCurrency)));
			rStrArray[c][8] = tCurrency;
			rStrArray[c][9] =(String)curLoanMap.get(tCurrency);;
			c++;
		}


		mResult.clear();
		// 数组
		mResult.add(rStrArray);
		return true;
	}

	/**
	 * 获得贷款限额
	 * 
	 * @param double
	 *            tValue 现金价值
	 * @param String
	 *            tRiskCode 险种编码
	 * @return double 贷款限额
	 */
	private double[] getMaxLoan(double tValue, String ContNo,String tCurrency) {
		double temp[] = new double[2];
		// 查询算法编码
		LMLoanDB tLMLoanDB = new LMLoanDB();
		//根据主险编码取查询借款比例
		tLMLoanDB.setRiskCode(BqNameFun.getMainRiskCodeByContNo(ContNo));
		LMLoanSet tLMLoanSet = tLMLoanDB.query();
		temp[0] = tValue * tLMLoanSet.get(1).getCanRate();
		logger.debug("---限额前值3---" + tValue);
		logger.debug("---限额后值3---" + temp[0]);
		if (tLMLoanSet == null || tLMLoanSet.size() <= 0) {
			mErrors.copyAllErrors(tLMLoanSet.mErrors);
			temp[0] = tValue * 8.0 / 10.0;
			logger.debug("---限额前值4---" + tValue);
			logger.debug("---限额后值4---" + temp[0]);
		}
		// 所借金额累计不能多余现金价值的80%
		double tSumAccMoney = 0.0, tIntrest = 0.0;

		LOLoanSet tLOLoanSet = new LOLoanSet();
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setContNo(ContNo);
		tLOLoanDB.setPayOffFlag("0");
		tLOLoanDB.setLoanType("0"); // 说明是借款，与自垫区分开来
		tLOLoanDB.setCurrency(tCurrency);
		tLOLoanSet = tLOLoanDB.query();
		if (tLOLoanSet.size() < 1) // 说明以前没有借款记录，此是第一次借款
		{
			logger.debug("此前有" + tLOLoanSet.size() + "笔借款");
		} else {

			for (int i = 1; i <= tLOLoanSet.size(); i++) {
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				tLOLoanSchema = tLOLoanSet.get(i);
				tSumAccMoney += tLOLoanSchema.getLeaveMoney();
				String tCountDate="";	
//				tCountDate=PubFun.calDate(mLPEdorItemSchema.getEdorAppDate(), 1, "D", "");
				tCountDate=mLPEdorItemSchema.getEdorAppDate();
				// 分段计息
				double tRate = AccountManage.calMultiRateMS(tLOLoanSchema.getLoanDate(),  tCountDate, "000000","LN","L","C","Y",tLOLoanSchema.getCurrency());
				if (tRate+1==0) {
					CError.buildErr(this, "本息和计算失败！");
					return temp;
				}
				//累计计息
				tIntrest+=tLOLoanSchema.getLeaveMoney()*tRate;
				tSumAccMoney += tIntrest;
			}
			temp[1]=tSumAccMoney;
		}
		// 根据条款借款金额不得低于帐户价值的0.8而且剩下余额不得少于2000
		// 根据条款借款金额不得低于帐户价值的0.8而且剩下余额不得少于2000
		if ("1".equals(tFlag)) {
			double tCanLoanMaxAcc = (tValue )
					* (1 - tLMLoanSet.get(1).getCanRate())- tSumAccMoney;
			double tIntv = 2000 - tCanLoanMaxAcc;
			// double tIntv=tCanLoanMaxAcc-tCanLoanMax;
			if (tIntv >= 0) {
				temp[0] = (tValue * tLMLoanSet.get(1).getCanRate()) - tSumAccMoney
						- tIntv;
			} else {
				temp[0] = (tValue * tLMLoanSet.get(1).getCanRate()) - tSumAccMoney;
			}
		} else {
			temp[0] = (tValue * tLMLoanSet.get(1).getCanRate()) - tSumAccMoney;
		}
		logger.debug("---限额前值5---" + tValue);
		logger.debug("---限额后值5---" + temp);
		return temp;
	}


	/**
	 * 获得某一天所在利率期
	 * 
	 * @param String
	 *            tFindDate 要查询的日期
	 * @return double mRate
	 */
	private double calRateByDate(String tFindDate,String tCurrency) {

		double mRate;
		String tSql = "SELECT rate FROM ldpubrate WHERE rltype='L' and sctype='C' and ymdinterest='Y' and riskcode='000000' and caltype='00'"
			         +" and startdate<='?date?' and enddate>='?date?' and currency = '?tCurrency?'";
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tSql);
		sbv.put("date", tFindDate);
		sbv.put("tCurrency", tCurrency);
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sbv);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			CError.buildErr(this, "查询利率失败！");
			mRate = -1;
			return mRate;
		}
		mRate=Double.parseDouble(tSSRS.GetText(1, 1));
		return mRate;
	}

	/**
	 * 获取保单（险种）的现金价值 临时写在这里的函数，以后再替换成真正的计算函数
	 * 
	 * @param String
	 *            tPolNo
	 * @param String
	 *            tCalDate 计算日期
	 * @return double
	 */
	private double getPolCashValue(String tRiskCode, String tPolNo,
			String tCalDate) {
		double mPolCashValue=0;
		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
		try {
			tEdorCalZT.setEdorInfo(mLPEdorItemSchema);
			String sPayByAcc=BqNameFun.isAccRisk(tRiskCode);
			if (sPayByAcc.equals("4")) // 1-帐户计算现金价值
			{
				tFlag = "1";
				mPolCashValue =tEdorCalZT.getCashValue(tPolNo, tCalDate);
			} else // 查询现金价值表
			{
				mPolCashValue = tEdorCalZT.getCashValue(tPolNo, tCalDate);
			}
			if (mPolCashValue == -1) {
				return mPolCashValue;
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorLNDetailBL";
			tError.functionName = "getPolCashValue";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);
			return mPolCashValue;
		}
		return mPolCashValue;
	}

	/**
	 * 改变保单的状态（注：是保单级状态）
	 * 
	 * @param tContNo
	 * @param tStateType
	 * @param tState
	 * @param tNewStateDate
	 * @return boolean true--成功，false--失败。结果放在mLPContStateSet变量中（累计）
	 */
	private boolean changeContState(String tContNo, String tStateType,
			String tState, String tNewStateDate, String tPolNo) {
		try {
			// 当前日期时间
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			LCContStateSchema tLCContStateSchema = null;
			LPContStateSchema tLPContStateSchema = null;
			// 先查询当前状态是否是要改变的状态，如果是，则保持
			String tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='?tContNo?'" + " and PolNo='?tPolNo?'"
					+ " and StateType='?tStateType?'" + " and State='?tState?'" + " and EndDate is null";
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(tSql);
			sbv.put("tContNo", tContNo);
			sbv.put("tPolNo", tPolNo);
			sbv.put("tStateType", tStateType);
			sbv.put("tState", tState);
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 现在的状态就是要改变后的状态，所以，保持
				return true;
			}

			// 按张容所说，如果新旧状态主键重复（指在C表中重复），则直接用新状态把旧状态替掉。2005-09-21日修改。
			LCContStateDB tLCContStateDB = new LCContStateDB();
			tLCContStateDB.setContNo(tContNo);
			tLCContStateDB.setPolNo(tPolNo);
			tLCContStateDB.setStateType(tStateType);
			tLCContStateDB.setStartDate(tNewStateDate);
			if (!tLCContStateDB.getInfo()) {
				// 查询现在状态，将此状态结束
				tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='?tContNo?'" + " and StateType='?tStateType?'"
						+ " and PolNo='?tPolNo?'"
						+ " and EndDate is null";
				tLCContStateDB = new LCContStateDB();
				sbv=new SQLwithBindVariables();
				sbv.sql(tSql);
				sbv.put("tContNo", tContNo);
				sbv.put("tPolNo", tPolNo);
				sbv.put("tStateType", tStateType);
				LCContStateSet tLCContStateSet = new LCContStateSet();
				tLCContStateSet = tLCContStateDB.executeQuery(sbv);
				if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
					tLCContStateSchema = new LCContStateSchema();
					tLCContStateSchema = tLCContStateSet.get(1);
					tLCContStateSchema.setEndDate(PubFun.calDate(tNewStateDate,
							-1, "D", null)); // 状态在前一天结束
					tLCContStateSchema.setOperator(mGlobalInput.Operator);
					tLCContStateSchema.setModifyDate(tCurrentDate);
					tLCContStateSchema.setModifyTime(tCurrentTime);
					tLPContStateSchema = new LPContStateSchema();
					this.mReflections.transFields(tLPContStateSchema,
							tLCContStateSchema);
					tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPContStateSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					mLPContStateSet.add(tLPContStateSchema);
				}
			}

			// 新状态信息
			tLPContStateSchema = new LPContStateSchema();
			tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContStateSchema.setContNo(tContNo);
			tLPContStateSchema.setInsuredNo("000000");
			tLPContStateSchema.setPolNo(tPolNo);
			tLPContStateSchema.setStateType(tStateType);
			tLPContStateSchema.setState(tState);
			if (tStateType != null && tStateType.equals("Terminate")) {
				tLPContStateSchema.setStateReason("06");
			}
			tLPContStateSchema.setStartDate(tNewStateDate);
			tLPContStateSchema.setOperator(mGlobalInput.Operator);
			tLPContStateSchema.setMakeDate(tCurrentDate);
			tLPContStateSchema.setMakeTime(tCurrentTime);
			tLPContStateSchema.setModifyDate(tCurrentDate);
			tLPContStateSchema.setModifyTime(tCurrentTime);
			mLPContStateSet.add(tLPContStateSchema);
			return true;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorLNDetailBL";
			tError.functionName = "changeContState";
			tError.errorMessage = "修改保单状态时产生错误！保单号：" + tContNo;
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 计算代扣印花税金额
	 * 
	 * @param dLoanMoney
	 *            贷款本金
	 * @return double
	 */
	private double getRevenueAmount(double dLoanMoney) {
		double dRevenueAmount = 0.0;
		String sql = " select * from LMRevenueRate where ratetype = 'YH' "
				+ " and ratestartdate < '?date?' and (rateenddate is null or rateenddate > '?date?')";
		logger.debug("-0000000000000000000000000000000000000000-" + sql);
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("date", mLPEdorItemSchema.getEdorValiDate());
		LMRevenueRateDB tLMRevenueRateDB = new LMRevenueRateDB();
		LMRevenueRateSet tLMRevenueRateSet = tLMRevenueRateDB.executeQuery(sbv);
		if (tLMRevenueRateDB.mErrors.needDealError()) {
			CError.buildErr(this, "税率查询失败");
			return -1;
		}
		if (tLMRevenueRateSet == null || tLMRevenueRateSet.size() < 1) {
			CError.buildErr(this, "税率查询失败");
			return -1;
		}
		mRevenueRate = tLMRevenueRateSet.get(1).getRate();

		dRevenueAmount = dLoanMoney * mRevenueRate;
		// ////////////////// add by pst on 2007-11-22///////////////
		if (dRevenueAmount < 0.1) // 若印花税不足0.1则不扣除
		{
			dRevenueAmount = 0.0;
		} else {
			dRevenueAmount = (int) (dRevenueAmount * 10 + 0.5) / 10.0;
		}
		// ////////////////// end add ////////////////
		return dRevenueAmount;
	}
}
