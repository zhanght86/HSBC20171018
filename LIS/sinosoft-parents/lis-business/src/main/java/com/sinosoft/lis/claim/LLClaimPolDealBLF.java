package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import bsh.This;

import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔保单(险种)结算
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version: 1.0
 */
public class LLClaimPolDealBLF {
private static Logger logger = Logger.getLogger(LLClaimPolDealBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private MMap mMap = new MMap();

	/** 全局数据 */
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private TransferData cTransferData = new TransferData(); // 向BL传送数据用
	private Reflections mReflections = new Reflections();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	private List mBomList = new ArrayList();

	private PrepareBOMClaimBL mPrepareBOMClaimBL = new PrepareBOMClaimBL();
	private String mClmNo = ""; // 赔案号
	private String mContNo = ""; // 合同号
	private String mAccDate = ""; // 出险日期
	private String mRgtDate = ""; // 立案日期
	private String mFeeOperationType = ""; // 业务类型
	private String mSubFeeOperationType = ""; // 子业务类型
	private String mFeeFinaType = ""; // 财务类型
	private double mCal = 0; // 赔付金额

	public LLClaimPolDealBLF() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLClaimConfirmPassBL begin submitData----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		logger.debug("----------LLClaimConfirmPassBL after getInputData----------");
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLClaimConfirmPassBL after dealData----------");
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLClaimConfirmPassBL after prepareOutputData----------");
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimPolDealBLF";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return true or false
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No0.0 删除受益人分配信息,待重新分配
		 * 保单结算前清空改赔案下所有保单结算信息 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tDsql1 = " delete from LLBalance where ClmNo='"
				+ "?ClmNo?"
				+ "'"
				+ " and FeeOperationType like 'C%'"
				+ " and substr(FeeOperationType,1,3) not in ('C08','C30') ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tDsql1);
		sqlbv.put("ClmNo", mClmNo);
		mMap.put(sqlbv, "DELETE");

		String tDSql2 = "delete from LLBnf where ClmNo='" + "?ClmNo?"
				+ "' and BnfKind = 'A'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tDSql2);
		sqlbv1.put("ClmNo", mClmNo);
		mMap.put(sqlbv1, "DELETE");

		String tDSql3 = "delete from LJSGet where OtherNo='" + "?ClmNo?"
				+ "'  and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tDSql3);
		sqlbv2.put("ClmNo", mClmNo);
		mMap.put(sqlbv2, "DELETE");

		String tDSql4 = "delete from LJSGetClaim where OtherNo='" + "?ClmNo?"
				+ "'  and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tDSql4);
		sqlbv3.put("ClmNo", mClmNo);
		mMap.put(sqlbv3, "DELETE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No0.1 得到计算需要的信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getPrepareCalInfo()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 查询出需要做保单结算的合同号
		 * 注：产品定义定义的特殊副险项目，需要核实判断方法！ －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 AfterGet＝000 无动作
		 * AfterGet＝001 [待定][审批通过]保额递减，只冲减保额 AfterGet＝003 无条件销户时,终止该合同(包括所有的险种)
		 * AfterGet＝004 最后一次给付销户,判断保额是否冲减完毕，如果冲减完毕执行003的动作 AfterGet＝005 [待定]
		 * AfterGet＝006 [审批通过]终止该责任给付时,终止LCGet的数据 AfterGet＝007
		 * [待定]终止该责任时：根据DutyCode的前六位，在LCDuty表中查找总数，如果与总数相等，
		 * 则终止LCContState表中的状态,终止本险种. AfterGet＝008 终止本险种
		 * 
		 * 
		 * 第一种情况:附加险只是扩展主险责任,赔付也是赔付主险责任的情况:如老系统的两个附加提前给付险种 LMDutyGetClm.EffectOnMainRisk='01',终止本身及主险;
		 * 第二种情况:附加险赔付自身,即附加险的给付责任在系统中存在描述:如新系统的MS附加提前给付重大疾病保险（2009）lmdutygetclm的EffectOnMainRisk需描述为'02',终止本身及主险;；
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "";
		String tSqlmain = "";
		// tSql = "Select distinct a.contno from LLClaimDetail a, LMRiskApp b "
		// + " where a.clmno = '" + mClmNo + "'"
		// + " and a.givetype = '0'"
		// + " and a.RiskCode = b.RiskCode"
		// + " and (b.SubRiskFlag = 'M'"
		// + " or a.getdutycode in ("
		// + " select getdutycode from LMDutyGetClm "
		// + " Where Getdutycode = a.getdutycode "
		// + " and EffectOnMainRisk = '01'))";
		//对于整单终止的清算
		tSql = " Select distinct a.contno from LLClaimDetail a, LMDutyGetClm b,lmriskapp c "
				+ " where a.riskcode=c.riskcode and a.clmno = '" + "?clmno?" + "'" + " "
				+ " and (c.SubRiskFlag='M' or (c.SubRiskFlag='S' and a.EffectOnMainRisk in('01','02')))" 
				+ " and a.getdutykind = b.getdutykind "
				+ " and a.getdutycode = b.getdutycode "
//				+ " and b.afterget in ('003','008')";
				+ " and b.afterget in ('003')";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("clmno", mClmNo);
		ExeSQL exesql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = exesql.execSQL(sqlbv4);
		int tCount = tSSRS.getMaxRow();

		logger.debug("------------------------------------------------------");
		logger.debug("--保单结算合同记录数[" + tCount + "]：" + tSql);
		logger.debug("------------------------------------------------------");
		

		if (tCount >= 1) {
			
			//根据过滤条件校验附加险是否满足影响主险条件,不满足则不进行保单清算
			for (int j = 1; j <= tCount; j++) {
				
				mContNo = tSSRS.GetText(j, 1);
				
				tSqlmain = " Select distinct a.contno from LLClaimDetail a, LMDutyGetClm b,lmriskapp c "
					+ " where a.riskcode=c.riskcode and a.clmno = '" + "?clmno?" + "'" + " "
					+ " and c.SubRiskFlag='M'" 
					+ " and a.getdutykind = b.getdutykind "
					+ " and a.getdutycode = b.getdutycode "
//					+ " and b.afterget in ('003','008')";
					+ " and b.afterget in ('003') and a.contno = '"
					+ "?contno?" + "'";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tSqlmain);
				sqlbv5.put("clmno", mClmNo);
				sqlbv5.put("contno", mContNo);
				ExeSQL exesqlmain = new ExeSQL();
				SSRS tSSRSmain = new SSRS();
				tSSRSmain = exesqlmain.execSQL(sqlbv5);
				
				if ((tSSRSmain.getMaxRow())<=0||(tSSRSmain == null)){

					if (!mContNo.equals("")) {
						if (!checkLLPolDeal()) {
						return false;
						}
					}
				}

			}
		
		
			for (int j = 1; j <= tCount; j++) {
				
				mContNo = tSSRS.GetText(j, 1);
				
				if (!mContNo.equals("")) {
					if (!getLLPolDeal()) {
						return false;
					}
				}

			}

			/**
			 * ---------------------------------------------------------------------BEG
			 * 以下是针对“No3.2 保单结算－－补交保费 加 利息”中可能要发的补交保费通知书做数据处理
			 * 判断是和LLBalRecruitFeeBL类的private boolean dealData()方法中判断是一样的。
			 * 对可能有多个合同的情况，保证打印管理表的只写一条补交保费通知书数据。
			 * ----------------------------------------------------------------------
			 */
//			System.out
//					.println("------------------ Deal 补交保费通知书 start --------------------");
//			String tSQLF = " select polno,PaytoDate,riskcode from lcpol where "
//					+ " contno in ( "
//					+ " Select distinct a.contno from LLClaimDetail a, LMDutyGetClm b "
//					+ " where a.clmno = '" + mClmNo + "'"
//					+ " and a.givetype = '0'"
//					+ " and (a.RiskType = 'M' or a.EffectOnMainRisk = '01')"
//					+ " and a.getdutykind = b.getdutykind "
//					+ " and a.getdutycode = b.getdutycode "
//					+ " and b.afterget in ('003','008')) ";
//			ExeSQL tExeSQLF = new ExeSQL();
//			SSRS tSSRSF = new SSRS();
//			tSSRSF = tExeSQLF.execSQL(tSQLF);
//			if (tSSRSF.getMaxRow() > 0) {
//				for (int z = 1; z <= tSSRSF.getMaxRow(); z++) {
//					String tPolNo = tSSRSF.GetText(z, 1);
//					String tPaytoDate = tSSRSF.GetText(z, 2);
//					String tRiskCode = tSSRSF.GetText(z, 3);
//
//					/**
//					 * ---------------------------------------------------------------------BEG
//					 * No.2 计算缴费宽限期
//					 * ----------------------------------------------------------------------
//					 */
//					String tApseDate = PubFun.calLapseDate(tPaytoDate,
//							tRiskCode);
//
//					/**
//					 * ---------------------------------------------------------------------BEG
//					 * No.3 计算缴费宽限期
//					 * ----------------------------------------------------------------------
//					 */
//					LLRegisterDB tLLRegisterDB = new LLRegisterDB();
//					LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
//					tLLRegisterDB.setRgtNo(mClmNo);
//					if (tLLRegisterDB.getInfo()) {
//						tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
//					}
//					mRgtDate = tLLRegisterSchema.getRgtDate(); // 立案日期
//
//					/**
//					 * ---------------------------------------------------------------------BEG
//					 * No.4 判断出险日期(意外事故发生日期)、立案时间是否在宽限期内
//					 * ----------------------------------------------------------------------
//					 */
//					String tBegin = PubFun.getLaterDate(tPaytoDate, mAccDate);
//					String tEnd = PubFun.getBeforeDate(tApseDate, mAccDate);
//					if (tBegin.equals(mAccDate) && tEnd.equals(mAccDate)) {
//						String tSQLS = "";
//						tSQLS = " select sum(a.SumDuePayMoney)  from LJSPayPerson a "
//								+ " where a.polno = '"
//								+ tPolNo
//								+ "'"
//								+ " and a.LastPayToDate = '"
//								+ tPaytoDate
//								+ "'"
//								+ " group by polno ";
//						ExeSQL tExeSQLS = new ExeSQL();
//						String tSum = tExeSQLS.getOneValue(tSQLS);
//						if (tSum != "0") {
//							String tDSql = " delete from LOPRTManager where 1=1 "
//									+ " and OtherNo = '"
//									+ mClmNo
//									+ "'"
//									+ " and Code = 'PCT008'";
//							mMap.put(tDSql, "DELETE");
//							if (insertLOPRTManager("PCT008")) { // 插入补交保费通知书
//								break;
//							}
//						}
//					}
//				}
//			}
//			System.out
//					.println("------------------ Deal 补交保费通知书 end --------------------");

		}
		
		/**
		 * -------------------------------------------------------------------------------No3.0
		 * -------------------------------------------------------------------------------单独清算豁免险
		 */
//		tSql = "select distinct polno from llclaimdetail a,lmrisksort b  where a.riskcode=b.riskcode and risksorttype in ('18','19') "
//			+" and clmno='"+mClmNo+"' ";
//
//		exesql = new ExeSQL();
//		tSSRS = new SSRS();
//		tSSRS = exesql.execSQL(tSql);
//		if(tSSRS.getMaxRow()>0)
//		{
//			for(int i=1;i<=tSSRS.getMaxRow();i++)
//			{
//				LCPolDB tLCPolDB = new LCPolDB();
//				tLCPolDB.setPolNo(tSSRS.GetText(i,1));
//				if(!tLCPolDB.getInfo())
//				{
//					
//					return false;
//				}
//				if(!getPolDealC0801(tLCPolDB.getSchema()))
//				{
//					return false;
//				}
//			}
//		}
		

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 更改赔案表的保单结算标志
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql2 = "update llclaim set conbalflag = '1' where "
				+ " clmno = '" + "?clmno?" + "'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSql2);
		sqlbv6.put("clmno", mClmNo);
		mMap.put(sqlbv6, "UPDATE");

		// /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// * No4.0 添加补交保费通知书打印 -----------------------周磊---移动到补交保费中
		// * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// */
		// //首先删除
		// String tDSql = "delete from LOPRTManager where 1=1 "
		// + " and OtherNo = '" + mClmNo + "'"
		// + " and Code = 'PCT008'";
		// mMap.put(tDSql, "DELETE");
		//
		// if (!insertLOPRTManager("PCT008")) //插入补交保费通知书
		// {
		// return false;
		// }

		return true;
	}

	/**
	 * 得到结算需要的信息
	 * 
	 * @return boolean
	 */
	private boolean getPrepareCalInfo() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 得到意外事故发生日期
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "";
		tSql = "select to_char(a.accDate,'yyyy-mm-dd') from LLAccident a,LLCaseRela b where 1=1 "
				+ " and a.AccNo = b.CaseRelaNo "
				+ " and b.CaseNo in ('"
				+ "?clmno?" + "')";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("clmno", this.mClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tAccDate = tExeSQL.getOneValue(sqlbv7);
		if (tAccDate == null || tAccDate.equals("")) {
			mErrors.addOneError("意外事故发生日期没有找到!");
			return false;
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 查询立案日期
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql2 = "select to_char(rgtdate,'yyyy-mm-dd') from llregister a where "
				+ " a.rgtno = '" + "?clmno?" + "'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSql2);
		sqlbv8.put("clmno", mClmNo);
		ExeSQL tExeSQL2 = new ExeSQL();
		String tRgtDate = tExeSQL2.getOneValue(sqlbv8);
		if (tRgtDate.equals("") || tRgtDate == null) {
			CError tError = new CError();
			tError.moduleName = "LLClaimPolDealBLF";
			tError.functionName = "getPolDealC0301";
			tError.errorMessage = "查询立案日期失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mRgtDate = tRgtDate.substring(0, 10).trim();
		this.mAccDate = mLLClaimPubFunBL.getInsDate(mClmNo);

		cTransferData.setNameAndValue("AccDate", mAccDate);
		cTransferData.setNameAndValue("ClmNo", mClmNo);

		return true;
	}
	
	
	/**
	 * 根据过滤条件校验附加险是否满足影响主险条件,不满足则不进行保单清算
	 * 
	 * @return boolean
	 */
	private boolean checkLLPolDeal() {
		

		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据合同号查询出保单险种表的数据
		 * 加入合同状态表的Terminate状态为0有效的判断条件
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "";
//		tSql = "select LCPol.* from LCPol where 1=1 " + " and LCPol.ContNo = '"
//				+ mContNo + "' and appflag='1'";
		//此处包含appflag!=1的也需要进行清算
		tSql = "select LCPol.* from LCPol where 1=1 " + " and LCPol.ContNo = '"
		+ "?ContNo?" + "'";

		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("ContNo", mContNo);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv9);
		if (tLCPolSet == null || tLCPolSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimPolDealBLF";
			tError.functionName = "getLLContState";
			tError.errorMessage = "没有查询到保单险种信息状态为有效的数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		boolean terminateFlag=true;//销户标志

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 根据保单(险种)进行结算操作
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema.setSchema(tLCPolSet.get(i));
			
			
			
			/**
			 * 2009-6-19 zhangzheng 
			 * 增加过滤条件 当附加险不满足影响主险条件时不执行理赔清算
			 */
			LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL.getLMRiskApp(tLCPolSchema.getRiskCode());
			if (tLMRiskAppSchema == null) {
				return false;
			}
			
			LMDutyGetClmSet tLMDutyGetClmSet=mLLClaimPubFunBL.getLMDutyGetClmSet(tLCPolSchema.getRiskCode());
			LMDutyGetClmSchema tLMDutyGetClmSchema = null;
			
			for(int k=1;k<=tLMDutyGetClmSet.size();k++)
			{
				tLMDutyGetClmSchema = new LMDutyGetClmSchema();
				tLMDutyGetClmSchema = tLMDutyGetClmSet.get(k);
				
				/**
				 * 2009-04-25 zhangzheng
				 * 附加险终止主险要根据条款执行过滤算法进行过滤,符合调教则销户主险,进行清算
				 */
				if (tLMRiskAppSchema.getSubRiskFlag().equals("S")) {
					
					
					if (tLMDutyGetClmSchema.getOthCalCode() != null && !tLMDutyGetClmSchema.getOthCalCode().equals("")) {
						
						int tFlag = (int)executePay(tLCPolSchema, tLMDutyGetClmSchema.getOthCalCode());
						
						if(tFlag==0)
						{
							terminateFlag=false;//附加险不满足影响主险条件时不执行理赔清算
							tLMDutyGetClmSchema = null;
							break;
						}
					}
				}
				
				tLMDutyGetClmSchema =null;
				
			}
			

			
			//附加险或附加险影响的主险满足销户条件,不需要清算,同时需要更新赔案表的清算标志
			if(terminateFlag==false)
			{
				// 进行数据提交
				String sql="update llclaim set ConBalFlag='1' where clmno='"+"?clmno?"+"'";
				MMap pMap = new MMap();
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(sql);
				sqlbv10.put("clmno", this.mClmNo);
				pMap.put(sqlbv10, "UPDATE");
				VData pInputData = new VData();
				pInputData.add(pMap);
				PubSubmit tPubSubmit = new PubSubmit();
				if (!tPubSubmit.submitData(pInputData, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					CError tError = new CError();
					tError.moduleName = "LLClaimPolDealBLF";
					tError.functionName = "PubSubmit.submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					pInputData=null;
					sql=null;
					pMap=null;
					return false;
				}
				
				pInputData=null;
				sql=null;
				pMap=null;
				
				// @@错误处理
				CError.buildErr(this, "主险不满足销户条件,不用保单清算!");
				return false;
			}
			
		}

		return true;
	}

	/**
	 * 根据终止结论,对理赔的合同状态表进行操作
	 * 
	 * @return boolean
	 */
	private boolean getLLPolDeal() {
		

		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据合同号查询出保单险种表的数据
		 * 加入合同状态表的Terminate状态为0有效的判断条件
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "";
//		tSql = "select LCPol.* from LCPol where 1=1 " + " and LCPol.ContNo = '"
//				+ mContNo + "' and appflag='1'";
		//此处包含appflag!=1的也需要进行清算
		tSql = "select LCPol.* from LCPol where 1=1 " + " and LCPol.ContNo = '"
		+ "?ContNo?" + "'";

		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tSql);
		sqlbv11.put("ContNo", mContNo);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv11);
		if (tLCPolSet == null || tLCPolSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimPolDealBLF";
			tError.functionName = "getLLContState";
			tError.errorMessage = "没有查询到保单险种信息状态为有效的数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		boolean terminateFlag=true;//销户标志

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 根据保单(险种)进行结算操作
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema.setSchema(tLCPolSet.get(i));

			
			mVData.add(tLCPolSchema);
			
			//校验是否存在 lcpol.leavingmoney>0
			//add by weikai  如果存在余额就不能进行清算
			//一定是未终止的保单，终止的保单不能进行退余额
			if(tLCPolSchema.getAppFlag().equals("1")&&tLCPolSchema.getLeavingMoney()>0)
			{
				CError.buildErr(this, "保单号为：【"+tLCPolSchema.getContNo()+"】 的保单存在保单余额，请先进行退还余额操作！");
				return false;
			}
			
			
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1 保单结算－－退出险以后所交的保费
			 * 对应的业务类型编码为C01,C0101 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getPolDealC01(tLCPolSchema)) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.2 保单结算－－补交保费 加 利息
			 * 对应的业务类型编码为C02,C0201和C0202 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getPolDealC02(tLCPolSchema)) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.3 保单结算－－质押贷款
			 * 对应的业务类型编码为C03 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			// 清偿贷款C0301
			if (!getPolDealC0301(tLCPolSchema)) {
				return false;
			}
//			 清偿利息C0302
			if (!getPolDealC0302(tLCPolSchema)) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.4
			 * 保单结算－－退生存金\养老金(客户退还保险公司) 对应的业务类型编码为C04,C0401
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getPolDealC0401(tLCPolSchema)) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.5 保单结算－－红利
			 * 对应的业务类型编码为C05 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tBonusFlag = getBonusFlag(tLCPolSchema.getRiskCode());
			if (tBonusFlag.equals("Y")) {
				// //累计红利保额C0501
				// if (!getPolDealC0501(tLCPolSchema))
				// {
				// return false;
				// }
				// 终了红利C0502
				if (!getPolDealC0502(tLCPolSchema)) {
					return false;
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.6 保单结算－－利差返还
			 * 对应的业务类型编码为C06,C0601 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			// if (!getPolDealC0601(tLCPolSchema))
			// {
			// return false;
			// }
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.7 保单结算－－自动垫缴
			 * 对应的业务类型编码为C07,C0701 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			// 自动垫缴C0701
			if (!getPolDealC0701(tLCPolSchema)) {
				return false;
			}
//			 自动垫缴利息C0702
			if (!getPolDealC0702(tLCPolSchema)) {
				return false;
			}

			// 2005-8-14 15:36 注释掉 周磊 原因:与退出险之后保费在主险重复
			// /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			// * No3.8 保单结算－－保费豁免
			// * 对应的业务类型编码为C08,C0801
			// * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			// */
			// if (!getPolDealC0801(tLCPolSchema))
			// {
			// return false;
			// }

		}

		return true;
	}

	/**
	 * 对应保单结算退出险以后的保费 对应的业务类型编码为C01,C0101
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC01(LCPolSchema tLCPolSchema) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 退出险日期之后的保费
		 * DealType:处理方式,1-指定日期之后,2-指定日期之前 DealDate:处理时间,1-包括计算时点,2-不包括计算时点
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLJAPayPerson(tLCPolSchema, mAccDate, "1", "2", "C01", "C0101",
				"TF")) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 退财务的应收费用信息
		 * 因为D01已经退过，所以不在退费 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getRecedeFee(tLCPolSchema)) {
			return false;
		}

		return true;
	}

	/**
	 * 保单结算--补交保费 对应的业务类型编码为C02,C0201和C0202
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC02(LCPolSchema tLCPolSchema) {
		mVData.clear();
		mVData.add(mGlobalInput);
		mVData.add(tLCPolSchema);
		cTransferData.removeByName("FeeOperationType");
		cTransferData.removeByName("SubFeeOperationType");
		cTransferData.removeByName("FeeFinaType");
		cTransferData.setNameAndValue("FeeOperationType", "C02");
		cTransferData.setNameAndValue("SubFeeOperationType", "C0201");
		cTransferData.setNameAndValue("FeeFinaType", "BF");
		mVData.add(cTransferData);

		LLBalRecruitFeeBL tLLBalRecruitFeeBL = new LLBalRecruitFeeBL();
		if (tLLBalRecruitFeeBL.submitData(mVData, "") == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLBalRecruitFeeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimPolDealBLF";
			tError.functionName = "getPolDealC01";
			tError.errorMessage = "补交保费处理失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			VData tempVData = tLLBalRecruitFeeBL.getResult();
			MMap tMMap = new MMap();
			tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			mMap.add(tMMap);
		}
		return true;
	}

	/**
	 * 保单结算－－质押贷款--清偿贷款本金 对应的业务类型编码为C03,C0301
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC0301(LCPolSchema tLCPolSchema) {
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		// 调用保全方法
		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (tBqPolBalBL.calLoanCorpus(tLCPolSchema, mRgtDate)) {
			t_Sum_Return = tBqPolBalBL.getCalResult();
		}
		if (t_Sum_Return <= 0) {
			t_Sum_Return = 0;
			logger.debug("#############################");
			logger.debug("#计算质押贷款--清偿贷款本金为零！");
			logger.debug("#############################");
			return true;
		}
		// 准备数据
		mFeeOperationType = "C03";
		mSubFeeOperationType = "C0301";
		mFeeFinaType = "HK";
		mCal = 0 - t_Sum_Return;

		if (!updateLLBalance(tLCPolSchema)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 保单结算－－质押贷款--清偿利息 对应的业务类型编码为C03,C0302
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC0302(LCPolSchema tLCPolSchema) {
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		// 调用保全方法
		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (tBqPolBalBL.calLoanInterest(tLCPolSchema, mRgtDate)) {
			t_Sum_Return = tBqPolBalBL.getCalResult();
		}
		if (t_Sum_Return <= 0) {
			t_Sum_Return = 0;
			logger.debug("#############################");
			logger.debug("#####计算质押贷款--清偿利息为零！");
			logger.debug("#############################");
			return true;
		}
		// 准备数据
		mFeeOperationType = "C03";
		mSubFeeOperationType = "C0302";
		mFeeFinaType = "HKLX";
		mCal = 0 - t_Sum_Return;

		if (!updateLLBalance(tLCPolSchema)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 保单结算，退生存金养老金(客户退还保险公司) 对应的业务类型编码为C04,C0401
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC0401(LCPolSchema tLCPolSchema) {
		
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorValiDate(this.mRgtDate);//设置结息时点
		tEdorCalZT.setEdorInfo(tLPEdorItemSchema);

//		if(!tEdorCalZT.checkGetDrew(tLCPolSchema.getPolNo(), this.mRgtDate))
//		{
//			CError.buildErr(this, "有应领未领保险金尚未入生存金帐户，不能退保。请应先进行催付!");
//			return false;
//		}
		t_Sum_Return = tEdorCalZT.getGetDraw(tLCPolSchema.getPolNo(), this.mRgtDate);
		if(t_Sum_Return==-1)
		{
			CError.buildErr(this, "生存金清算失败:"+tEdorCalZT.mErrors.getLastError());
			return false;
		}
		VData tVData = tEdorCalZT.getResult();
		LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) tVData.getObjectByObjectName(
				"LCInsureAccSet", 0);
		LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet) tVData
				.getObjectByObjectName("LCInsureAccClassSet", 0);
		LCInsureAccTraceSet  tLCInsureAccTraceSet = (LCInsureAccTraceSet) tVData
				.getObjectByObjectName("LCInsureAccTraceSet", 0);
		this.mMap.put(tLCInsureAccSet, "UPDATE");
		this.mMap.put(tLCInsureAccClassSet, "UPDATE");
		if (tLCInsureAccTraceSet != null && tLCInsureAccTraceSet.size() > 0) {
			for (int k = 1; k <= tLCInsureAccTraceSet.size(); k++) 
			{
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(k);
				if(tLCInsureAccTraceSchema.getMoney()==0)
				{
					continue;
				}
				String tLimit = PubFun.getNoLimit(tLCInsureAccTraceSchema
						.getManageCom());
				String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
				tLCInsureAccTraceSchema.setPayDate(this.mRgtDate);
				tLCInsureAccTraceSchema.setSerialNo(serNo);
				if (tLCInsureAccTraceSchema.getInsuAccNo().equals("000001")
						|| tLCInsureAccTraceSchema.getInsuAccNo().equals(
								"000007")
						|| tLCInsureAccTraceSchema.getInsuAccNo().equals(
								"000008")) {
					String trace_finType = BqCalBL.getFinType_HL_SC("HL", tLCInsureAccTraceSchema.getInsuAccNo(), "LX");
					if(trace_finType.equals(""))
					{
						CError.buildErr(this, "红利领取时的财务类型获取失败！");
						return false;
					}
					tLCInsureAccTraceSchema.setMoneyType(trace_finType);
				} else if (tLCInsureAccTraceSchema.getInsuAccNo().equals(
						"000005")) {
					String trace_finType = BqCalBL.getFinType_HL_SC("SC", tLCInsureAccTraceSchema.getInsuAccNo(), "LX");
					if(trace_finType.equals(""))
					{
						CError.buildErr(this, "生存金领取时的财务类型获取失败！");
						return false;
					}
					tLCInsureAccTraceSchema.setMoneyType(trace_finType);
				}
				else
				{
					CError.buildErr(this, "出现红利和年金以外的帐户，可能是问题数据！");
					return false;
				}
//				pLPInsureAccTraceSchema.setMoney(-rLCInsureAccTraceSet.get(k)
//						.getMoney());
				tLCInsureAccTraceSchema.setModifyDate(this.CurrentDate);
				tLCInsureAccTraceSchema.setModifyTime(this.CurrentTime);
				tLCInsureAccTraceSchema.setMakeDate(this.CurrentDate);
//				pLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
				tLCInsureAccTraceSchema.setMakeTime("00:00:00");
				tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				this.mMap.put(tLCInsureAccTraceSchema, "INSERT"); 
			}
		}
		// 准备数据
		if(tLCInsureAccSet!=null&&tLCInsureAccSet.size()>0)
		{
			for(int i=1;i<=tLCInsureAccSet.size();i++)
			{
				if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000005"))
				{
					mFeeOperationType = "C04";
					mSubFeeOperationType = "C0401"; //生存金
					mFeeFinaType = "YF";
				}
				else if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000009"))
				{
					mFeeOperationType = "C04";
					mSubFeeOperationType = "C0402"; //满期金
					mFeeFinaType = "EF";
				}
				else
				{
					return false;
				}
				
				mCal = tLCInsureAccSet.get(i).getInsuAccBala();
	
				if (!updateLLBalance(tLCPolSchema)) {
					return false;
				} 
			}
		}
		
		//追回的处理
		if (tLCInsureAccSet != null && tLCInsureAccSet.size() > 0) {
			for(int i=1;i<=tLCInsureAccSet.size();i++)
			{
				String sum_money_sql="select (case when sum(-money) is null then 0 else sum(-money) end) from lcinsureacctrace where polno='"
					+"?polno?"+"'"
					+" and insuaccno='"+"?insuaccno?"+"' and money<0";
				SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				sqlbv12.sql(sum_money_sql);
				sqlbv12.put("polno", tLCInsureAccSet.get(i).getPolNo());
				sqlbv12.put("insuaccno", tLCInsureAccSet.get(i).getInsuAccNo());
				ExeSQL tExe = new ExeSQL();
				double sum_money = Double.parseDouble(tExe.getOneValue(sqlbv12));
				//派发给客户的帐户总价值 =派发给客户的金额+结算后的帐户价值
				sum_money = sum_money+tLCInsureAccSet.get(i).getInsuAccBala();
				
				//查询出出险点前的资金进出，然后结息到出险点，就是截止到出险点的账户价值，也就是客户应领取的帐户价值
				String trace_sql="select * from lcinsureacctrace where polno='"+"?polno?"+"'"
				+" and insuaccno='"+"?insuaccno?"+"' and moneytype!='YFLX' and paydate<='"+"?paydate?"+"'";
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				sqlbv13.sql(trace_sql);
				sqlbv13.put("polno", tLCInsureAccSet.get(i).getPolNo());
				sqlbv13.put("insuaccno", tLCInsureAccSet.get(i).getInsuAccNo());
				sqlbv13.put("paydate", this.mAccDate);
				double act_money=0.0;//客户应领取的帐户价值
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				LCInsureAccTraceSet tempLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv13);
				LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
				tLMRiskInsuAccDB.setInsuAccNo(tLCInsureAccSet.get(i).getInsuAccNo());
				if(!tLMRiskInsuAccDB.getInfo())
				{
					CError.buildErr(this, "保险帐户："+tLCInsureAccSet.get(i).getInsuAccNo()+"查询失败！");
					return false;
				}
				for(int j=1;j<=tempLCInsureAccTraceSet.size();j++)
				{
					LCInsureAccTraceSchema tLCInsureAccTraceSchema = tempLCInsureAccTraceSet.get(j);
					double rate =
							AccountManage.calMultiRateForAccMS(tLMRiskInsuAccDB.getSchema(), 
									tLCInsureAccTraceSchema.getPayDate(), this.mAccDate,tLCInsureAccTraceSchema.getCurrency());
					double act_money_interest = PubFun.round(tLCInsureAccTraceSchema.getMoney()*rate,2); 
					act_money = act_money+tLCInsureAccTraceSchema.getMoney()+act_money_interest;
				}
				if(act_money<0.01)
				{
					act_money=0;
				}
				
				//截止到出险点派发给客户的金额
				String Acc_sum_money_sql="select (case when sum(-money) is null then 0 else sum(-money) end) from lcinsureacctrace where polno='"
					+"?polno?"+"'"
					+" and insuaccno='"+"?insuaccno?"+"' and money<0 and paydate<='"+"?paydate?"+"'";
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				sqlbv14.sql(Acc_sum_money_sql);
				sqlbv14.put("polno", tLCInsureAccSet.get(i).getPolNo());
				sqlbv14.put("insuaccno", tLCInsureAccSet.get(i).getInsuAccNo());
				sqlbv14.put("paydate", this.mAccDate);
				ExeSQL AcctExe = new ExeSQL();
				double Acc_sum_money = Double.parseDouble(AcctExe.getOneValue(sqlbv14));

				
				//客户应领金额=截止到出险点的账户价值+截止到出险点派发给客户的金额
				act_money=act_money+Acc_sum_money;
				
				//追回金额=派发给客户的帐户总价值 - 客户应领取的帐户价值
				double zhhl=sum_money - act_money;
				if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000005"))
				{
					mFeeOperationType = "C04";
					mSubFeeOperationType = "C0403"; //生存金追回
					mFeeFinaType = "YFRB";
				}
				else if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000009"))
				{
					mFeeOperationType = "C04";
					mSubFeeOperationType = "C0403"; //满期金追回
					mFeeFinaType = "YFRB";
				}
				else
				{
					return false;
				}
				
				mCal = zhhl; 
				if(mCal<0)//实际领取金额比实际追回金额小   问题数据，
				{
					CError.buildErr(this, "客户应领取的生存金帐户价值大于派发给客户的帐户总价值，可能是数据有问题，请提请核实！");
					return false;
				}
				if(mCal==0) //没有需要追回的金额
				{
					return true;
				}
				mCal=-mCal;

				if (!updateLLBalance(tLCPolSchema)) {
					return false;
				} 
			}
		}
		
		
		return true;
	}

	/**
	 * 保单结算－－红利--累计红利保额 对应的业务类型编码为C05,C0501
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC0501(LCPolSchema tLCPolSchema) {
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		ExeSQL tExeSQL = new ExeSQL();

		// 得到当前年度
		String tYear = CurrentDate.substring(0, 4);

		// 得到年度红利
		tSql = "select (case when a.BonusAmnt is null then 0 else a.BonusAmnt end) from LOEngBonusPol a where 1=1 "
				+ " and a.PolNo ='" + "?polno?" + "'";
		// + " and a.FiscalYear = to_number('" + tYear + "')";
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(tSql);
		sqlbv15.put("polno", tLCPolSchema.getPolNo());
		String tCal = tExeSQL.getOneValue(sqlbv15);
		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimPolDealBLF";
			tError.functionName = "getPolDealC0501";
			tError.errorMessage = "计算年度红利发生错误!";
			this.mErrors.addOneError(tError);
		}

		if (tCal != null && tCal.length() > 0) {
			t_Sum_Return = Double.parseDouble(tCal);

			if (t_Sum_Return != 0) {
				// 准备数据
				mFeeOperationType = "C05";
				mSubFeeOperationType = "C0501";
				mFeeFinaType = "EF";
				mCal = t_Sum_Return;

				if (!updateLLBalance(tLCPolSchema)) {
					return false;
				} else {
					return true;
				}
			}
		}
		logger.debug("#############################");
		logger.debug("#####计算累计红利保额为零！");
		logger.debug("#############################");
		return true;
	}

	/**
	 * 保单结算－－红利--终了红利 对应的业务类型编码为C05,C0502
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC0502(LCPolSchema tLCPolSchema) {
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

//		t_Sum_Return = mLLClaimPubFunBL.getFinalBonus(tLCPolSchema.getPolNo(),
//				tLCPolSchema.getRiskCode(), mAccDate);
//		t_Sum_Return = Arith.round(t_Sum_Return, 2);
//		if (t_Sum_Return <= 0) {
//			t_Sum_Return = 0;
//			logger.debug("#############################");
//			logger.debug("#####计算终了红利为零！");
//			logger.debug("#############################");
//			return true;
//		}
		ExeSQL tExe = new ExeSQL();
		//对特殊险种已领取年金不允许分红
		String mRiskSql = "select risksortvalue from lmrisksort where riskcode='"+"?riskcode?"+"' and risksorttype='H1'";
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(mRiskSql);
		sqlbv16.put("riskcode", tLCPolSchema.getRiskCode());
		String mRiskSortVaule="";
		mRiskSortVaule=tExe.getOneValue(sqlbv16);
		String checksql="";
		if("1".equals(mRiskSortVaule))
		{
			checksql="select case when exists(select 1 from lcpol a where contno='"+"?contno?"
					+"' and Exists (select 1 from lmriskapp b where a.riskcode=b.riskcode and bonusflag='1') "
					+" and not exists(select 1 from lcget where polno=a.polno and getstartdate<='"+"?mRgtDate?"+"' "
					+" and exists(select 1 from lmdutyget where getdutycode=lcget.getdutycode and type='0'))"
					+" and (( '"+"?mRgtDate?"+"'>=paytodate and floor(months_between(paytodate,cvalidate)/12)> "
					+" (select count(1) from lobonuspol where polno=a.polno and bonusflag='1')) or  "
					+" ( '"+"?mRgtDate?"+"'<paytodate and floor(months_between('"+"?mRgtDate?"+"',cvalidate)/12)> "
					+" (select count(1) from lobonuspol where polno=a.polno and bonusflag='1'))))"
					+"  then 0 else 1 end from dual ";
			
		}
		else
		{
		  checksql="select case when exists(select 1 from lcpol a where contno='"+"?contno?"
				+"' and Exists (select 1 from lmriskapp b where a.riskcode=b.riskcode and bonusflag='1') "
				+" and (( '"+"?mRgtDate?"+"'>=paytodate and floor(months_between(paytodate,cvalidate)/12)> "
				+" (select count(1) from lobonuspol where polno=a.polno and bonusflag='1')) or  "
				+" ( '"+"?mRgtDate?"+"'<paytodate and floor(months_between('"+"?mRgtDate?"+"',cvalidate)/12)> "
				+" (select count(1) from lobonuspol where polno=a.polno and bonusflag='1'))))"
				+"  then 0 else 1 end from dual ";
		}
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql(checksql);
		sqlbv18.put("contno", tLCPolSchema.getContNo());
		sqlbv18.put("mRgtDate", this.mRgtDate);
		if("0".equals(tExe.getOneValue(sqlbv18)))
		{
			//CError.buildErr(this, "存在尚未分配的红利，请先进行红利分配，再做清算！");
			//return false;
		}
		
		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorValiDate(this.mRgtDate);//设置结息时点
		tEdorCalZT.setEdorInfo(tLPEdorItemSchema);
		t_Sum_Return = tEdorCalZT.getBonusLJSX(tLCPolSchema.getPolNo(), this.mRgtDate);
		if(t_Sum_Return==-1)
		{
			CError.buildErr(this, "红利清算失败！");
			return false;
		}
		VData tVData = tEdorCalZT.getResult();
		LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) tVData.getObjectByObjectName(
				"LCInsureAccSet", 0);
		LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet) tVData
				.getObjectByObjectName("LCInsureAccClassSet", 0);
		LCInsureAccTraceSet  tLCInsureAccTraceSet = (LCInsureAccTraceSet) tVData
				.getObjectByObjectName("LCInsureAccTraceSet", 0);
		this.mMap.put(tLCInsureAccSet, "UPDATE");
		this.mMap.put(tLCInsureAccClassSet, "UPDATE");
		if (tLCInsureAccTraceSet != null && tLCInsureAccTraceSet.size() > 0) {
			for (int k = 1; k <= tLCInsureAccTraceSet.size(); k++) 
			{
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(k);
				if(tLCInsureAccTraceSchema.getMoney()==0)
				{
					continue;
				}
				String tLimit = PubFun.getNoLimit(tLCInsureAccTraceSchema
						.getManageCom());
				String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
				tLCInsureAccTraceSchema.setPayDate(this.mRgtDate);
				tLCInsureAccTraceSchema.setSerialNo(serNo);
				if (tLCInsureAccTraceSchema.getInsuAccNo().equals("000001")
						|| tLCInsureAccTraceSchema.getInsuAccNo().equals(
								"000007")
						|| tLCInsureAccTraceSchema.getInsuAccNo().equals(
								"000008")) {
					String trace_finType = BqCalBL.getFinType_HL_SC("HL", tLCInsureAccTraceSchema.getInsuAccNo(), "LX");
					if(trace_finType.equals(""))
					{
						CError.buildErr(this, "红利领取时的财务类型获取失败！");
						return false;
					}
					tLCInsureAccTraceSchema.setMoneyType(trace_finType);
				} else if (tLCInsureAccTraceSchema.getInsuAccNo().equals(
						"000005")) {
					String trace_finType = BqCalBL.getFinType_HL_SC("SC", tLCInsureAccTraceSchema.getInsuAccNo(), "LX");
					if(trace_finType.equals(""))
					{
						CError.buildErr(this, "生存金领取时的财务类型获取失败！");
						return false;
					}
					tLCInsureAccTraceSchema.setMoneyType(trace_finType);
				}
				else
				{
					CError.buildErr(this, "出现红利和年金以外的帐户，可能是问题数据！");
					return false;
				}
//				pLPInsureAccTraceSchema.setMoney(-rLCInsureAccTraceSet.get(k)
//						.getMoney());
				tLCInsureAccTraceSchema.setModifyDate(this.CurrentDate);
				tLCInsureAccTraceSchema.setModifyTime(this.CurrentTime);
				tLCInsureAccTraceSchema.setMakeDate(this.CurrentDate);
//				pLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
				tLCInsureAccTraceSchema.setMakeTime("00:00:00");
				tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				this.mMap.put(tLCInsureAccTraceSchema, "INSERT"); 
			}
		}
		// 准备数据
		if(tLCInsureAccSet!=null&&tLCInsureAccSet.size()>0)
		{
			for(int i=1;i<=tLCInsureAccSet.size();i++)
			{
				if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000001"))
				{
					mFeeOperationType = "C05";
					mSubFeeOperationType = "C0501"; //累计生息退费
					mFeeFinaType = "LJTF";
				}
				else if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000007"))
				{
					mFeeOperationType = "C05";
					mSubFeeOperationType = "C0502"; //抵交红利
					mFeeFinaType = "DJTF";
				}
				else if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000008"))
				{
					mFeeOperationType = "C05";
					mSubFeeOperationType = "C0503"; //现金红利
					mFeeFinaType = "XJTF";
				}
				else
				{
					return false;
				}
				
				mCal = tLCInsureAccSet.get(i).getInsuAccBala();
	
				if (!updateLLBalance(tLCPolSchema)) {
					return false;
				} 
			}
		}
		
		//追回红利的处理
		if (tLCInsureAccSet != null && tLCInsureAccSet.size() > 0) {
			for(int i=1;i<=tLCInsureAccSet.size();i++)
			{
				String sum_money_sql="select (case when sum(-money) is null then 0 else sum(-money) end) from lcinsureacctrace where polno='"
					+"?polno?"+"'"
					+" and insuaccno='"+"?insuaccno?"+"' and money<0";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(sum_money_sql);
				sqlbv19.put("polno", tLCInsureAccSet.get(i).getPolNo());
				sqlbv19.put("insuaccno", tLCInsureAccSet.get(i).getInsuAccNo());
				double sum_money = Double.parseDouble(tExe.getOneValue(sqlbv19));
				//派发给客户的帐户总价值 =派发给客户的金额+结算后的帐户价值
				sum_money = sum_money+tLCInsureAccSet.get(i).getInsuAccBala();
				
				//查询出出险点前的资金进出，然后结息到出险点，就是截止到出险点的账户价值，也就是客户应领取的帐户价值
				String trace_sql="select * from lcinsureacctrace where polno='"+"?polno?"+"'"
				+" and insuaccno='"+"?insuaccno?"+"' and moneytype not in ('HLLX','DJLX','YCLX','LX') and paydate<='"+"?paydate?"+"'";
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql(trace_sql);
				sqlbv20.put("polno", tLCInsureAccSet.get(i).getPolNo());
				sqlbv20.put("insuaccno", tLCInsureAccSet.get(i).getInsuAccNo());
				sqlbv20.put("paydate", this.mAccDate);
				double act_money=0.0;
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				LCInsureAccTraceSet tempLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv20);
				LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
				tLMRiskInsuAccDB.setInsuAccNo(tLCInsureAccSet.get(i).getInsuAccNo());
				if(!tLMRiskInsuAccDB.getInfo())
				{
					CError.buildErr(this, "保险帐户："+tLCInsureAccSet.get(i).getInsuAccNo()+"查询失败！");
					return false;
				}
				for(int j=1;j<=tempLCInsureAccTraceSet.size();j++)
				{
					LCInsureAccTraceSchema tLCInsureAccTraceSchema = tempLCInsureAccTraceSet.get(j);
					double rate =
							AccountManage.calMultiRateForAccMS(tLMRiskInsuAccDB.getSchema(), 
									tLCInsureAccTraceSchema.getPayDate(), this.mAccDate,tLCInsureAccTraceSchema.getCurrency());
					double act_money_interest = PubFun.round(tLCInsureAccTraceSchema.getMoney()*rate,2); 
					act_money = act_money+tLCInsureAccTraceSchema.getMoney()+act_money_interest;
				}
				if(act_money<0.01)
				{
					act_money=0;
				}
				//截止到出险点派发给客户的金额
				String Acc_sum_money_sql="select (case when sum(-money) is null then 0 else sum(-money) end) from lcinsureacctrace where polno='"
					+"?polno?"+"'"
					+" and insuaccno='"+"?insuaccno?"+"' and money<0 and paydate<='"+"?paydate?"+"'";
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql(Acc_sum_money_sql);
				sqlbv21.put("polno", tLCInsureAccSet.get(i).getPolNo());
				sqlbv21.put("insuaccno", tLCInsureAccSet.get(i).getInsuAccNo());
				sqlbv21.put("paydate", this.mAccDate);
				ExeSQL AcctExe = new ExeSQL();
				double Acc_sum_money = Double.parseDouble(AcctExe.getOneValue(sqlbv21));

				//客户应领金额=截止到出险点的账户价值+截止到出险点派发给客户的金额
				act_money=act_money+Acc_sum_money;
				
				//追回金额=派发给客户的帐户总价值 - 客户应领取的帐户价值
				double zhhl=sum_money - act_money;

				if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000001"))
				{
					mFeeOperationType = "C05";
					mSubFeeOperationType = "C0504"; //累计生息退费  
					mFeeFinaType = "HLRB";  //暂不区分
				}
				else if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000007"))
				{
					mFeeOperationType = "C05";
					mSubFeeOperationType = "C0504"; //抵交红利
					mFeeFinaType = "HLRB";//暂不区分
				}
				else if(tLCInsureAccSet.get(i).getInsuAccNo().equals("000008"))
				{
					mFeeOperationType = "C05";
					mSubFeeOperationType = "C0504"; //现金红利
					mFeeFinaType = "HLRB";//暂不区分
				}
				else
				{
					return false;
				}
				
				mCal = zhhl; 
				if(mCal<0)//实际领取金额比实际追回金额小   问题数据，
				{
					CError.buildErr(this, "客户应领取的红利帐户价值大于派发给客户的帐户总价值，可能是数据有问题，请提请核实！");
					return false;
				}
				if(mCal==0) //没有需要追回的金额
				{
					return true;
				}
				mCal=-mCal;
				if (!updateLLBalance(tLCPolSchema)) {
					return false;
				} 
			}
		}
		
		return true;
		
	}

	/**
	 * 保单结算－－自动垫缴--本金 对应的业务类型编码为C07,C0701
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC0701(LCPolSchema tLCPolSchema) {
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		// 调用保全方法
		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (tBqPolBalBL.calAutoPayPrem(tLCPolSchema.getPolNo(), this.mRgtDate)) {
			t_Sum_Return = tBqPolBalBL.getCalResult();
		}
		if (t_Sum_Return <= 0) {
			t_Sum_Return = 0;
			logger.debug("#############################");
			logger.debug("#####计算自动垫缴--本金为零！");
			logger.debug("#############################");
			return true;
		}
		// 准备数据
		mFeeOperationType = "C07";
		mSubFeeOperationType = "C0701";
		mFeeFinaType = "HD";
		mCal = 0 - t_Sum_Return;

		if (!updateLLBalance(tLCPolSchema)) {
			return false;
		} 
//		else {
//			return true;
//		}
		
		//出现时点后发生的垫款需要还给客户
//		LOLoanDB tLOLoanDB = new LOLoanDB();
//		String loloan_sql="select * from loloan where loantype='1' and payoffflag='0' and polno='"+tLCPolSchema.getPolNo()+"' and newloandate>'"+this.mAccDate+"'";
//		LOLoanSet tLOLoanSet = tLOLoanDB.executeQuery(loloan_sql);
//		if (tLOLoanSet != null && tLOLoanSet.size() > 0) {
//			
//			// 准备计算参数
//			String tEdorType = "TR"; // 批改类型为保费自垫
//			String tLoanDate = ""; // 本金产生日期tCorpusDate 借款日期 YYYY-MM-DD
//			
//			double tSumMoney = 0.0; // 每期借款总金额，即为本金tCorpus
//			double tInterestDouble = 0.0;
//
//			
//			for (int i = 1; i <= tLOLoanSet.size(); i++) {
//				tLoanDate = tLOLoanSet.get(i).getNewLoanDate();
//				tSumMoney = tLOLoanSet.get(i).getLeaveMoney();
//				// 计算分段利率值，但不是利率，可以说是一个参数，参数×本金=利息
//				double tRate = AccountManage.calMultiRateMS(tLoanDate,  this.mRgtDate, "000000",tEdorType,"L","C","Y");
//				if (tRate+1==0) {
//					CError.buildErr(this, "本息和计算失败！");
//					return false;
//				}
//				//累计利息
//				tInterestDouble += tSumMoney*tRate;
//			}
//			
//			//本金
//			mFeeOperationType = "C07";
//			mSubFeeOperationType = "C0701";
//			mFeeFinaType = "BF";
//			mCal = tSumMoney;
//
//			if (!updateLLBalance(tLCPolSchema)) {
//				return false;
//			} 
//			//利息
//			mFeeOperationType = "C07";
//			mSubFeeOperationType = "C0701";
//			mFeeFinaType = "BF";
//			mCal = tInterestDouble;
//
//			if (!updateLLBalance(tLCPolSchema)) {
//				return false;
//			} 
//		}
		
		
		
		return true;
	}

	/**
	 * 保单结算－－自动垫缴--自垫保费的利息 对应的业务类型编码为C07,C0702
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC0702(LCPolSchema tLCPolSchema) {
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		// 调用保全方法
		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (tBqPolBalBL.calAutoPayInterest(tLCPolSchema.getPolNo(), this.mRgtDate)) {
			t_Sum_Return = tBqPolBalBL.getCalResult();
		}
		if (t_Sum_Return <= 0) {
			t_Sum_Return = 0;
			logger.debug("####################################");
			logger.debug("#####计算自动垫缴--自垫保费的利息为零！");
			logger.debug("####################################");
			return true;
		}
		// 准备数据
		mFeeOperationType = "C07";
		mSubFeeOperationType = "C0702";
		mFeeFinaType = "HDLX";
		mCal = 0 - t_Sum_Return;

		if (!updateLLBalance(tLCPolSchema)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 保单结算，保费豁免 对应的业务类型编码为C08,C0801
	 * 
	 * @return boolean
	 */
	private boolean getPolDealC0801(LCPolSchema tLCPolSchema) {
		mVData.clear();
		mVData.add(mGlobalInput);
		mVData.add(tLCPolSchema);
		cTransferData.removeByName("FeeOperationType");
		cTransferData.removeByName("SubFeeOperationType");
		cTransferData.removeByName("FeeFinaType");
		cTransferData.setNameAndValue("FeeOperationType", "C08");
		cTransferData.setNameAndValue("SubFeeOperationType", "C0801");
		cTransferData.setNameAndValue("FeeFinaType", "TF");
		mVData.add(cTransferData);

		LLBalExemptFeeBL tLLBalExemptFeeBL = new LLBalExemptFeeBL();
		if (tLLBalExemptFeeBL.submitData(mVData, "") == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLBalExemptFeeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimPolDealBLF";
			tError.functionName = "getPolDealC01";
			tError.errorMessage = "保费豁免处理失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			VData tempVData = tLLBalExemptFeeBL.getResult();
			MMap tMMap = new MMap();
			tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			mMap.add(tMMap);
		}
		return true;
	}

	/**
	 * 公用:插入数据到理赔结算表
	 * 
	 * @return boolean
	 */
	private boolean updateLLBalance(LCPolSchema tLCPolSchema) {
		if (mClmNo.equals("") || mFeeOperationType.equals("")
				|| mSubFeeOperationType.equals("") || mFeeFinaType.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLClaimPolDealBLF";
			tError.functionName = "updateLLBalance";
			tError.errorMessage = "传输到理赔结算表数据不齐全!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
		tLLBalanceSchema.setClmNo(mClmNo);
		tLLBalanceSchema.setFeeOperationType(mFeeOperationType); // 业务类型
		tLLBalanceSchema.setSubFeeOperationType(mSubFeeOperationType); // 子业务类型
		tLLBalanceSchema.setFeeFinaType(mFeeFinaType); // 财务类型

		tLLBalanceSchema.setBatNo("0"); // 批次号
		tLLBalanceSchema.setOtherNo("0"); // 其它号码
		tLLBalanceSchema.setOtherNoType("0"); // 其它号码类型
		tLLBalanceSchema.setSaleChnl(tLCPolSchema.getSaleChnl()); // 销售渠道

		tLLBalanceSchema.setGrpContNo(tLCPolSchema.getGrpContNo()); // 集体合同号码
		tLLBalanceSchema.setContNo(tLCPolSchema.getContNo()); // 合同号码
		tLLBalanceSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo()); // 集体保单号码
		tLLBalanceSchema.setPolNo(tLCPolSchema.getPolNo()); // 保单号码

		tLLBalanceSchema.setDutyCode("0"); // 责任编码
		tLLBalanceSchema.setGetDutyKind("0"); // 给付责任类型
		tLLBalanceSchema.setGetDutyCode("0"); // 给付责任编码

		tLLBalanceSchema.setKindCode(tLCPolSchema.getKindCode()); // 险类编码
		tLLBalanceSchema.setRiskCode(tLCPolSchema.getRiskCode()); // 险种编码
		tLLBalanceSchema.setRiskVersion(tLCPolSchema.getRiskVersion()); // 险种版本

		tLLBalanceSchema.setAgentCode(tLCPolSchema.getAgentCode()); // 代理人编码
		tLLBalanceSchema.setAgentGroup(tLCPolSchema.getAgentGroup()); // 代理人组别

		tLLBalanceSchema.setGetDate(this.CurrentDate); // 给付日期
		tLLBalanceSchema.setPay(mCal); // 赔付金额

		tLLBalanceSchema.setPayFlag("0"); // 支付标志,0未支付,1已支付
		tLLBalanceSchema.setState("0"); // 状态,0有效
		tLLBalanceSchema.setDealFlag("0"); // 处理标志,0未处理

		tLLBalanceSchema.setManageCom(tLCPolSchema.getManageCom()); // 管理机构
		tLLBalanceSchema.setAgentCom(tLCPolSchema.getAgentCom()); // 代理机构
		tLLBalanceSchema.setAgentType(tLCPolSchema.getAgentType()); // 代理机构内部分类

		tLLBalanceSchema.setOperator(mGlobalInput.Operator); // 操作员
		tLLBalanceSchema.setMakeDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setMakeTime(this.CurrentTime); // 入机时间
		tLLBalanceSchema.setModifyDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setModifyTime(this.CurrentTime); // 入机时间
		tLLBalanceSchema.setCustomerNo(tLCPolSchema.getInsuredNo());

		mMap.put(tLLBalanceSchema, "DELETE&INSERT");

		return true;
	}

	/**
	 * 进行个人实收表退费处理
	 * 
	 * @param tLLExemptSchema
	 * @return
	 */
	private boolean getLJAPayPerson(LCPolSchema pLCPolSchema, String pDate,
			String pDealType, String pDealDate, String pFeeType,
			String pSubFeeType, String pFinaType) {

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("LCPolSchema", pLCPolSchema);

		tTransferData.setNameAndValue("ClmNo", mClmNo); // 赔案号
		tTransferData.setNameAndValue("Date", pDate); // 指定日期
		tTransferData.setNameAndValue("DealType", pDealType); // 处理方式,1-指定日期之后,2-指定日期之前
		tTransferData.setNameAndValue("DealDate", pDealDate); // 处理时间,1-包括计算时点,其它不包括计算时点
		tTransferData.setNameAndValue("Operator", this.mGlobalInput.Operator); // 操作人员

		tTransferData.setNameAndValue("FeeType", pFeeType); // 业务类型
		tTransferData.setNameAndValue("SubFeeType", pSubFeeType);// 子业务类型
		tTransferData.setNameAndValue("FinaType", pFinaType); // 财务类型

		LLBalanceSchema tLLBalanceSchema = mLLClaimPubFunBL
				.getLJAPayPerson(tTransferData);

		if (tLLBalanceSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
		} else {
			mMap.put(tLLBalanceSchema, "DELETE&INSERT");
		}

		return true;
	}

	/**
	 * 退财务的应收记录
	 * 
	 * @param tLCPolSchema
	 * @return
	 */
	private boolean getRecedeFee(LCPolSchema tLCPolSchema) {
		logger.debug("----------退财务的应收记录-----getRecedeFee-----开始----------");
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", this.mClmNo); // 赔案号
		tTransferData.setNameAndValue("CalDate", this.mAccDate); // 计算时点
		tTransferData.setNameAndValue("DealDate", "2"); // 处理时间,1-包括计算时点,2不包括计算时点
		tTransferData.setNameAndValue("DealType", "1"); // 处理方式,1-指定日期之后,2-指定日期之前
		tTransferData.setNameAndValue("DealMode", "1"); // 处理模式,1-直接删除,2-不删除
		tVData.add(tTransferData);
		tVData.add(tLCPolSchema);

		LLBalRecedeFeeBL tLLBalRecedeFeeBL = new LLBalRecedeFeeBL();
		if (tLLBalRecedeFeeBL.submitData(tVData, mOperate) == false) {
			this.mErrors.copyAllErrors(tLLBalRecedeFeeBL.mErrors);
			return false;
		} else {
			VData tempVData = tLLBalRecedeFeeBL.getResult();
			MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			this.mMap.add(tMMap);
		}
		logger.debug("----------退财务的应收记录-----getRecedeFee-----结束----------");
		return true;
	}

	/**
	 * 判断险种是否是分红险
	 * 
	 * @param aRiskCode
	 * @param aPayToDate
	 * @return
	 */
	private String getBonusFlag(String sRiskcode) {
		// 判断险种是否是分红险
		String sql = " select BonusFlag from lmriskapp "
				+ " where riskcode = '" + "?riskcode?" + "'";
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql(sql);
		sqlbv22.put("riskcode", sRiskcode);
		ExeSQL tExeSQL = new ExeSQL();
		String sBonusFlag = tExeSQL.getOneValue(sqlbv22);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询分红险标志失败!");
			return null;
		}
		if (sBonusFlag == null || sBonusFlag.equals("")
				|| sBonusFlag.equals("0")) {
			// 不是分红险
			sBonusFlag = "N";
		} else {
			sBonusFlag = "Y";
		}

		return sBonusFlag;
	}

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// 插入新值,生成印刷流水号
		String strNolimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit);
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mClmNo); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式
		tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate);

		// 查询立案信息
		LLCaseDB tLLCaseDB = new LLCaseDB();
		String tSSql = "select * from llcase where 1=1 " + " and caseno = '"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(tSSql);
		sqlbv23.put("clmno", mClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.executeQuery(sqlbv23);
		String tCusNo = tLLCaseSet.get(1).getCustomerNo();

		tLOPRTManagerSchema.setStandbyFlag4(tCusNo); // 客户号码
		tLOPRTManagerSchema.setStandbyFlag5(mRgtDate); // 立案日期
		tLOPRTManagerSchema.setStandbyFlag6("40"); // 赔案状态

		mMap.put(tLOPRTManagerSchema, "INSERT");
		return true;
	}
	
	
	/**
	 * 执行过滤算法
	 * @param mLCPolSchema
	 * @param pCalCode
	 * @return
	 */
	private double executePay(LCPolSchema mLCPolSchema, String pCalCode) {
		double rValue;
		if (null == pCalCode || "".equals(pCalCode) || pCalCode.length() == 0) {
			return 0;
		}
		logger.debug("\n=========================================进行公式运算=====开始=========================================\n");


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 设置各种计算要素
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();
		

		// 出险时已保年期
		tTransferData.setNameAndValue("RgtYears", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mAccDate, "Y")));
		
		tTransferData.setNameAndValue("CValiDate", mLCPolSchema.getCValiDate());
		
		tTransferData.setNameAndValue("AccidentDate",this.mAccDate);


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
		PubCalculator tPubCalculator = new PubCalculator();

		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			// logger.debug("PubCalculator.addBasicFactor--tName:" + tName
			// + " tValue:" + tValue);
			tPubCalculator.addBasicFactor(tName, tValue);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		mBomList=mPrepareBOMClaimBL.dealData(tTransferData);
		tCalculator.setBOMList(mBomList);
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		logger.debug("======================================================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("第[" + i + "]个理算要素名称--" + tName + "--[" + tValue
					+ "]");
			tCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("======================================================================================================");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 进行计算，Calculator.calculate()
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tStr = "";
		logger.debug("----------------------------------------------------------------------------------\n");
		logger.debug("计算公式==[" + tCalculator.getCalSQL() + "]");
		logger.debug("\n----------------------------------------------------------------------------------");
		tStr = tCalculator.calculate();
		if (tStr.trim().equals("")) {
			rValue = 0;
		} else {
			rValue = Arith.round(Double.parseDouble(tStr), 2);
		}

		if (tCalculator.mErrors.needDealError()) {
			this.mErrors.addOneError("计算发生错误!原公式:" + pCalCode + "||,解析后的公式:"
					+ tCalculator.getCalSQL());
		}
		logger.debug("\n=========================================进行公式运算=====结束=========================================\n");
		return rValue;
	}


	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMap);
		return true;
	}

	/**
	 * 返回前台需要的数据
	 * 
	 * @return vdata
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		String tClaimFeeCode = "abcdefg";
		tClaimFeeCode = tClaimFeeCode.substring(1);

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ClmNo", "86320020090510000623");
//		tTransferData.setNameAndValue("AccDate", "2004-06-11");

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		LLClaimPolDealBLF tLLClaimPolDealBLF = new LLClaimPolDealBLF();

		if (tLLClaimPolDealBLF.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}

		int n = tLLClaimPolDealBLF.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLClaimPolDealBLF.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}

}
