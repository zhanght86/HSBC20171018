package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 合同险种终止处理,自动进行合同终止处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimContPolDealAutoBL {
private static Logger logger = Logger.getLogger(LLClaimContPolDealAutoBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mVData = new VData();
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private List mBomList = new ArrayList();

	private PrepareBOMClaimBL mPrepareBOMClaimBL = new PrepareBOMClaimBL();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();

	private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
	private LLContStateSet mLLContStateSet = new LLContStateSet();

	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	private ExeSQL mExeSQL = new ExeSQL();

	private String mClmNo = ""; // 赔案号
	// private String mCustNo = ""; //客户号
	// private String mContType = ""; //总单类型,1-个人总投保单,2-集体总单
	// private String mContNo = ""; //合同号
	// private String mPolNo = ""; //险种号
	// private String mContStateReason="";//合同险种终止处理意见
	// private String mContEndDate=""; //合同险种终止时间
	// private String mAccDate = ""; //意外事故发生日期
	private String mInsDate = ""; // 出险时间

	public LLClaimContPolDealAutoBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------赔案合同险种自动终止处理-----LLClaimContPolDealAutoBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkBalAmnt()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("----------赔案合同险种自动终止处理-----LLClaimContPolDealAutoBL测试-----结束----------");
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
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		// this.mCustNo = (String) mTransferData.getValueByName("CustNo"); //客户号
		// this.mContType= (String) mTransferData.getValueByName("ContType");
		// //总单类型,1-个人总投保单,2-集体总单
		// this.mContNo = (String) mTransferData.getValueByName("ContNo"); //合同号
		// this.mPolNo = (String) mTransferData.getValueByName("PolNo"); //险种号
		// this.mContStateReason = (String)
		// mTransferData.getValueByName("ContStateReason");//合同终止处理意见
		// this.mContEndDate = (String)
		// mTransferData.getValueByName("ContEndDate");//合同终止时间

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－保额冲减校验－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	/**
	 * 处理数据前做数据校验
	 * 
	 * @return
	 */
	private boolean checkBalAmnt() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 判断该赔案是否有给付记录
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		logger.debug("-------------------------------保额冲减校验开始------------------------------------");

		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		String tQuerySql = "";
		tQuerySql = "select a.* from LLClaimDetail a,LLClaim b "
				+ "where a.clmno = b.clmno "
				+ "and a.givetype = '0' and b.givetype = '0'"
				+ "and a.clmno = '" + "?clmno?" + "'" + " order by a.risktype ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tQuerySql);
		sqlbv.put("clmno", mClmNo);
		logger.debug("------给付保项查询:" + tQuerySql);
		mLLClaimDetailSet = tLLClaimDetailDB.executeQuery(sqlbv);
		if (tLLClaimDetailDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimDetailDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmPassBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询赔案给付赔付信息失败,不能进行此操作!";
			this.mErrors.addOneError(tError);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 和LCGet表的有效保额进行比较
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2 查询领取项表
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCGetDB tLCGetDB = new LCGetDB();
			String tSql1 = "select * from LCGet where 1=1 " + " and PolNo = '"
					+ "?PolNo?" + "'"
					+ " and DutyCode = '" + "?DutyCode?"
					+ "'" + " and GetDutyCode = '"
					+ "?GetDutyCode?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql1);
			sqlbv1.put("PolNo", tLLClaimDetailSchema.getPolNo());
			sqlbv1.put("DutyCode", tLLClaimDetailSchema.getDutyCode());
			sqlbv1.put("GetDutyCode", tLLClaimDetailSchema.getGetDutyCode());
			LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv1);
			if (tLCGetSet.size() != 1 || tLCGetDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGetDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimConfirmPassBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询承保领取项数据时出错!";
				this.mErrors.addOneError(tError);
				logger.debug("------------------------------------------------------");
				logger.debug("--LLClaimContPolDealAutoBL.checkData()--查询承保领取项数据时出错!"
								+ tSql1);
				logger.debug("------------------------------------------------------");
				return false;
			}
			LCGetSchema tLCGetSchema = tLCGetSet.get(1);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.3
			 * 取出各种标志位,进行保额冲减的判断
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tRiskCode = StrTool
					.cTrim(tLLClaimDetailSchema.getRiskCode());
			String tPosFlag = StrTool.cTrim(tLLClaimDetailSchema.getPosFlag());// 0未做保全,1做过保全
			String tPosPosEdorNo = StrTool.cTrim(tLLClaimDetailSchema
					.getPosEdorNo());// 保全批单号
			String tNBPolNo = StrTool.cTrim(tLLClaimDetailSchema.getNBPolNo());// 承保时的保单号

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4 判断保项剩余的有效保额
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "7")) {
				double tRealPay = tLLClaimDetailSchema.getRealPay();// 赔案给付金额
				double tBalAmnt = 0;

				String tGetDutyName = mLLClaimPubFunBL
						.getGetDutyName(tLCGetSchema);
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4 对做过保全的进行处理
				 * 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 如果tNBAmnt > tPosAmnt
				 * {[做保全前]--5万 > 减保后的保额[做保全后]--2万}
				 * 
				 * tRealPay > tBalAmnt {用给付金额 与 做保全前的有效保额相比较}
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (tPosFlag.equals("2")) {
					LPGetSchema tLPGetSchema = null;
					tLPGetSchema = mLLClaimPubFunBL.getLPGet(tPosPosEdorNo,
							tNBPolNo, tLCGetSchema);
					if (tLPGetSchema == null) {
						return false;
					}
					double tNBAmnt = Arith.round(tLPGetSchema.getStandMoney(),
							2); // 承保时的保额[做保全前]--5万
					double tPosAmnt = Arith.round(tLCGetSchema.getStandMoney(),
							2); // 减保后的保额[做保全后]--2万
					tBalAmnt = Arith.round(tLPGetSchema.getStandMoney()
							- tLPGetSchema.getSumMoney(), 2);// 做保全前的有效保额

					if ((tNBAmnt > tPosAmnt) && (tRealPay > tBalAmnt)) {
						mErrors.addOneError("该合同做过保全减保,出险时点的有效保额不足以支付"
								+ tGetDutyName + "的给付金额!");
						return false;
					}
				} else {
					tBalAmnt = Arith.round(tLCGetSchema.getStandMoney()
							- tLCGetSchema.getSumMoney(), 2);// 做保全前的有效保额
					if (tRealPay > tBalAmnt) {
						mErrors.addOneError("有效保额不足以支付" + tGetDutyName
								+ "的给付金额!");
						return false;
					}
				}

			}

		}

		logger.debug("-------------------------------保额冲减校验结束------------------------------------");
		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－保额冲减校验－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－合同终止处理－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 先删除相关数据,在进行后续操作
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tSql1 = "delete from LLBalance where ClmNo='" + "?ClmNo?"
				+ "' and substr(FeeOperationType,1,1) in ('D')";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql1);
		sqlbv2.put("ClmNo", this.mClmNo);
		this.mMMap.put(sqlbv2, "DELETE");

		String tSql2 = "delete from LLBnf where ClmNo='" + "?ClmNo?"
				+ "' and BnfKind = 'A'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql2);
		sqlbv3.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv3, "DELETE");

		String tSql3 = "delete from LJSGet where OtherNo='" + "?ClmNo?"
				+ "'  and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql3);
		sqlbv4.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv4, "DELETE");

		String tSql4 = "delete from LJSGetClaim where OtherNo='" + "?ClmNo?"
				+ "'  and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql4);
		sqlbv5.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv5, "DELETE");
		
		String tSql5 = "delete from LLBnfGather where ClmNo='" + "?ClmNo?"+ "' and BnfKind = 'A'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSql5);
		sqlbv6.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv6, "DELETE");

		String tSql6 = "delete from LLContState where ClmNo='" + "?ClmNo?"
				+ "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSql6);
		sqlbv7.put("ClmNo", this.mClmNo);
		this.mMMap.put(sqlbv7, "DELETE");
		
		

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 得到出险时间
		 * 
		 * 将赔案表的合同处理状态置为1[已处理]
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		this.mInsDate = mLLClaimPubFunBL.getInsDate(mClmNo); // 出险时间

		LLClaimSchema tLLClaimSchema = mLLClaimPubFunBL.getLLClaim(mClmNo);
		if (tLLClaimSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}
		tLLClaimSchema.setConDealFlag("1");
		this.mMMap.put(tLLClaimSchema, "UPDATE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 对合同进行自动处理操作
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getAfterGet()) {
			return false;
		}

		return true;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－给付后动作处理－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 根据终止结论,对理赔的合同状态表进行操作
	 * 
	 * @return boolean
	 */
	private boolean getAfterGet() {
		logger.debug("-------------------------------自动终止合同-----开始------------------------------------");

		for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);
			String tPolNo = StrTool.cTrim(tLLClaimDetailSchema.getPolNo());
			String tRiskCode = StrTool
					.cTrim(tLLClaimDetailSchema.getRiskCode());
			String tDutyCode = StrTool
					.cTrim(tLLClaimDetailSchema.getDutyCode());
			String tGetDutyKind = StrTool.cTrim(tLLClaimDetailSchema
					.getGetDutyKind());
			String tGetDutyCode = StrTool.cTrim(tLLClaimDetailSchema
					.getGetDutyCode());

			String tSubRiskFlag = StrTool.cTrim(tLLClaimDetailSchema
					.getRiskType());// 主附险标志,M-主险,S-附加险
			String tEffectOnMainRisk = StrTool.cTrim(tLLClaimDetailSchema
					.getEffectOnMainRisk());// 01-影响主险标志

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
			 * 查询LMDutyGetClm是否有记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LMDutyGetClmSchema tLMDutyGetClmSchema = mLLClaimPubFunBL
					.getLMDutyGetClm(tGetDutyKind, tGetDutyCode);

			if (tLMDutyGetClmSchema == null) {
				mLLClaimPubFunBL.mErrors.clearErrors();
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
			 * 查询LMDutyGetClm是否有记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tAfterGet = StrTool.cTrim(tLMDutyGetClmSchema.getAfterGet());

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 AfterGet＝000 无动作
			 * AfterGet＝001 [审批通过]保额递减，只冲减保额 AfterGet＝003 无条件销户时,终止该合同(包括所有的险种)
			 * AfterGet＝004 最后一次给付销户,判断保额是否冲减完毕，如果冲减完毕执行003的动作 AfterGet＝005 [待定]
			 * AfterGet＝006 [审批通过]终止该责任给付时,终止LCGet的数据 AfterGet＝007
			 * [待定]终止该责任时：根据DutyCode的前六位，在LCDuty表中查找总数，如果与总数相等，
			 * 则终止LCContState表中的状态,终止本险种. AfterGet＝008 终止本险种
			 * 
			 * 
			 * LMRiskApp.SubRiskFlag='S'[附险],
			 *   第一种情况:附加险只是扩展主险责任,赔付也是赔付主险责任的情况:如老系统的两个附加提前给付险种 LMDutyGetClm.EffectOnMainRisk='01',终止本身及主险;
			 *   第二种情况:附加险赔付自身,即附加险的给付责任在系统中存在描述:如新系统的MS附加提前给付重大疾病保险（2009）lmdutygetclm的EffectOnMainRisk需描述为'02',终止本身及主险;；
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			logger.debug("----------处理第[" + i + "]个,险种["
					+ tLLClaimDetailSchema.getRiskCode() + "],给付责任["
					+ tLLClaimDetailSchema.getGetDutyCode() + "],给付后动作["
					+ tAfterGet + "]");

			if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("01")||tEffectOnMainRisk.equals("02"))) {
				logger.debug("----------险类[" + tSubRiskFlag + "],影响["
						+ tEffectOnMainRisk + "]");
				if (!dealRisk(tLLClaimDetailSchema,tLMDutyGetClmSchema)) {
					return false;
				}
			} else {
				if (tAfterGet.equals("003")) {
					if (!dealAfterGet003(tLLClaimDetailSchema)) {
						return false;
					}

				} else if (tAfterGet.equals("004")) {
					if (!dealAfterGet004(tLLClaimDetailSchema)) {
						return false;
					}
				} else if (tAfterGet.equals("008")) {
					if (!dealAfterGet008(tLLClaimDetailSchema)) {
						return false;
					}

				}
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 判断本次赔案是否有
			 * 9.附加险退未满期保费[225,264]的记录 如果有,判断已经结案的赔案是否有过该记录,如果有,退费,存负值
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "15")
					&& tDutyCode.length() == 6) {

				LCPolSchema tRSLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
				if (tRSLCPolSchema == null) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}

				if (!getRSRiskRecedeFee(tRSLCPolSchema)) {
					return false;
				}
			}// if end

		}

		logger.debug("-------------------------------自动终止合同-----结束------------------------------------");
		return true;
	}

	/**
	 * 处理AfterGet＝003 无条件销户时,终止该合同(包括所有的险种)
	 * 
	 * @param pLLClaimDetailSchema
	 * @return
	 */
	private boolean dealAfterGet003(LLClaimDetailSchema pLLClaimDetailSchema) {

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(pLLClaimDetailSchema.getContNo());
		tLCPolDB.setPolNo(pLLClaimDetailSchema.getPolNo());
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError() || tLCPolSet.size() == 0) {
			this.mErrors.addOneError("查询合同号为[" + pLLClaimDetailSchema.getContNo() + "],险种号为["
					+pLLClaimDetailSchema.getPolNo()+"]的数据失败!");
			return false;
		}

		for (int i = 1; i <= tLCPolSet.size(); i++) {

			LCPolSchema tLCPolSchema = tLCPolSet.get(i);
			setLLContState(tLCPolSchema, "D00");
		}

		return true;
	}

	/**
	 * AfterGet＝004 最后一次给付销户,判断保额是否冲减完毕，如果冲减完毕执行003的动作
	 * 
	 * @param pLLClaimDetailSchema
	 * @return
	 */
	private boolean dealAfterGet004(LLClaimDetailSchema pLLClaimDetailSchema) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 查询领取项表
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LCGetDB tLCGetDB = new LCGetDB();
		String tSql1 = "select * from LCGet where 1=1 " + " and PolNo = '"
				+ "?PolNo?" + "'" + " and DutyCode = '"
				+ "?DutyCode?" + "'"
				+ " and GetDutyCode = '"
				+ "?GetDutyCode?" + "'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSql1);
		sqlbv8.put("PolNo", pLLClaimDetailSchema.getPolNo());
		sqlbv8.put("DutyCode", pLLClaimDetailSchema.getDutyCode());
		sqlbv8.put("GetDutyCode", pLLClaimDetailSchema.getGetDutyCode());
		LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv8);
		if (tLCGetDB.mErrors.needDealError() || tLCGetSet.size() == 0) {
			this.mErrors.addOneError("查询承保领取项数据时出错!");
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimContPolDealAutoBL.dealAfterGet004()--查询承保领取项数据时出错!"
							+ tSql1);
			logger.debug("------------------------------------------------------");
			return false;
		}
		
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2 计算保项的剩余保额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		
		/**
		 * 2009-03-18 zhangzheng 增加对共享险种保额的销户处理(risksorttype='9')
		 * 当多个给付责任共享保额时理算会自动扣减,如三个给付责任共享保额10000,即在lcget的standmoney字段都是10000,
		 * 但理算时分别算出,9500,500,0,则无论是哪个责任在循环处理时都不符合下面的判断条件,所以增加处理,以险种的
		 * 总赔付金额与保额相比较,相等则销户;
		 */
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		String tSql2=" select * from lmrisksort where riskcode='"+"?riskcode?"+"'"
		            +" and risksorttype='9'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tSql2);
		sqlbv9.put("riskcode", pLLClaimDetailSchema.getRiskCode());
		logger.debug("查询险种:"+pLLClaimDetailSchema.getRiskCode()+"的共享险种保额的给付责任集合sql:"+tSql2);
		LMRiskSortSet tLMRiskSortSet = tLMRiskSortDB.executeQuery(sqlbv9);
		
		double tRealPay=0.0;
		double tStandM=tLCGetSet.get(1).getStandMoney();
		double tSumM=0.0;
		double tMinus=0.0;
		
		if(tLMRiskSortSet.size()>0)
		{
			String tSql3="select (case when sum(RealPay) is null then 0 else sum(RealPay) end) from llclaimdetail where clmno='"+"?clmno?"+"'"
			            +" and riskcode='"+"?riskcode?"+"'";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSql3);
			sqlbv10.put("clmno", pLLClaimDetailSchema.getClmNo());
			sqlbv10.put("riskcode", pLLClaimDetailSchema.getRiskCode());
			logger.debug("查询赔案:"+pLLClaimDetailSchema.getClmNo()+",下险种:"+pLLClaimDetailSchema.getRiskCode()+"的给付金额sql:"+tSql3);
			ExeSQL tExeSQL=new ExeSQL();
			
			tSumM=Double.parseDouble(tExeSQL.getOneValue(sqlbv10));
			
			//相等则表示本次已赔付完毕
			if(tStandM==tSumM)
			{
				if (!dealAfterGet003(pLLClaimDetailSchema)) {
					return false;
				}
			}
		}
		else
		{

			// 因为主键查询,肯定至多一条,故不做循环
			tRealPay = pLLClaimDetailSchema.getRealPay();
			tSumM = tLCGetSet.get(1).getSumMoney();
			tMinus = tStandM - tSumM;
			
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.3 保项的剩余保额 = 给付总额
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tMinus == tRealPay) {
				if (!dealAfterGet003(pLLClaimDetailSchema)) {
					return false;
				}
			}
		}
		



		return true;
	}

	/**
	 * AfterGet＝008 终止本险种
	 * 
	 * @param pLLClaimDetailSchema
	 * @return
	 */
	private boolean dealAfterGet008(LLClaimDetailSchema pLLClaimDetailSchema) {
		String tPolNo = pLLClaimDetailSchema.getPolNo();

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tPolNo);
		LCPolSet tLCPolSet = tLCPolDB.query();

		if (tLCPolDB.mErrors.needDealError() || tLCPolSet.size() != 1) {
			this.mErrors.addOneError("查询险种号为[" + tPolNo + "]的数据失败!");
			return false;
		}

		LCPolSchema tLCPolSchema = tLCPolSet.get(1);
		setLLContState(tLCPolSchema, "P00");

		return true;
	}

	/**
	 * LMRiskApp.SubRiskFlag='S'[附险],LMDutyGetClm.EffectOnMainRisk='01',终止本身及主险
	 * 
	 * @param pLLClaimDetailSchema
	 * @return
	 */
	private boolean dealRisk(LLClaimDetailSchema pLLClaimDetailSchema,LMDutyGetClmSchema pLMDutyGetClmSchema) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 获取该险种所在合同的所有信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tContNo = pLLClaimDetailSchema.getContNo();

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(tContNo);
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError() || tLCPolSet.size() == 0) {
			this.mErrors.addOneError("查询合同号为[" + tContNo + "]的数据失败!");
			return false;
		}
		
		boolean terminateFlag=true;//销户标志

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 循环该险种所在合同的所有险种定义信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			
			LCPolSchema tLCPolSchema = tLCPolSet.get(i);
			terminateFlag=true;

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 获取险种定义信息
			 * zhangzheng 主险及其所有该主险下附加险也终止
 			 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL
					.getLMRiskApp(tLCPolSchema.getRiskCode());
			if (tLMRiskAppSchema == null) {
				return false;
			}

			// 终止主险
			String tSubRiskFlag = tLMRiskAppSchema.getSubRiskFlag();
			
			/**
			 * 2009-04-25 zhangzheng
			 * 附加险终止主险要根据条款执行过滤算法进行过滤,符合调教则销户主险
			 */
			if (tSubRiskFlag.equals("M")) {
				
				
				if (pLMDutyGetClmSchema.getOthCalCode() != null && !pLMDutyGetClmSchema.getOthCalCode().equals("")) {
					
					int tFlag = (int)executePay(tLCPolSchema, pLMDutyGetClmSchema.getOthCalCode());
					
					if(tFlag==0)
					{
						terminateFlag=false;//主险不符合销户条件,不销户
					}
				}
			}
			
			//附加险或附加险影响的主险满足销户条件
			if((terminateFlag==true&&tSubRiskFlag.equals("M"))||(tSubRiskFlag.equals("S")))
			{
				setLLContState(tLCPolSchema, "D00");
			}
			

			// 终止本身
//			if (pLLClaimDetailSchema.getPolNo().equals(tLCPolSchema.getPolNo())) {
//				setLLContState(tLCPolSchema, "D00");
//			}
			
			// 

		}

		return true;
	}

	/**
	 * 根据保项所在的合同号到LCContState表中找到该合同所有数据,
	 * 将StateType类型为Terminate的记录全部置为1终止，并填写终止时间 ClmState=1,赔案处理涉及的保单
	 */
	private boolean setLLContState(LCPolSchema pLCPolSchema, String pDealState) {

		if (pLCPolSchema.getAppFlag().equals("1")) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 防止重复终止险种,做个循环校验
			 * 如果重复,不进行终止操作
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			for (int i = 1; i <= mLLContStateSet.size(); i++) {
				String tNewContNo = StrTool.cTrim(pLCPolSchema.getContNo());
				String tNewPolNo = StrTool.cTrim(pLCPolSchema.getPolNo());
				
				logger.debug("tNewContNo:"+tNewContNo+",tNewPolNo:"+tNewPolNo);

				LLContStateSchema tLLContStateSchema = mLLContStateSet.get(i);
				String tOldContNo = StrTool.cTrim(tLLContStateSchema
						.getContNo());
				String tOldPolNo = StrTool.cTrim(tLLContStateSchema.getPolNo());
				
				logger.debug("tOldContNo:"+tOldContNo+",tOldPolNo:"+tOldPolNo);

				if (tNewContNo.equals(tOldContNo)
						&& tNewPolNo.equals(tOldPolNo)) {
					return true;
				}
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
			 * 判断是否是附加险,如果是附加险,判断是否是必须终止 如果附加险不是立即终止,则退出 20.主险终止附加险必须终止
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tRiskCode = pLCPolSchema.getRiskCode();
			LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL
					.getLMRiskApp(tRiskCode);

			// 根据ClmNo查询出主险保单终止给付责任类型
			String ttsql = " select distinct 1 from LLClaimDutyKind "
					+ " where ClmNo = '" + "?ClmNo?"
					+ "' and getdutykind in('102','202')";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(ttsql);
			sqlbv11.put("ClmNo", mClmNo);
			ExeSQL tExeSQL = new ExeSQL();
			String tDeal = tExeSQL.getOneValue(sqlbv11);
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "查询保单终止给付责任类型失败!");
				return false;
			}
			if (tDeal == null || tDeal.equals("")) {
				tDeal = "00";
			}

			if (tLMRiskAppSchema.getSubRiskFlag().trim().equals("S")
					&& (!mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "20")
							//|| !(mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "28") && "1".equals(tDeal))
							))
			{
				return true;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 进行终止处理
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLContStateSchema tLLContStateSchema = new LLContStateSchema();
			tLLContStateSchema.setClmNo(this.mClmNo);
			tLLContStateSchema.setContNo(pLCPolSchema.getContNo());
			tLLContStateSchema.setInsuredNo("000000");
			tLLContStateSchema.setPolNo(pLCPolSchema.getPolNo());
			tLLContStateSchema.setStateType("Terminate");
			tLLContStateSchema.setState("1");
			tLLContStateSchema.setStateReason("04");
			tLLContStateSchema.setStartDate(this.mInsDate);
			tLLContStateSchema.setEndDate("");
			tLLContStateSchema.setRemark("[" + mClmNo + "]--理赔合同自动终止处理");
			tLLContStateSchema.setOperator(mGlobalInput.Operator);
			tLLContStateSchema.setMakeDate(this.CurrentDate);
			tLLContStateSchema.setMakeTime(this.CurrentTime);
			tLLContStateSchema.setModifyDate(this.CurrentDate);
			tLLContStateSchema.setModifyTime(this.CurrentTime);
			tLLContStateSchema.setDealState(pDealState);
			tLLContStateSchema.setClmState("1"); // 1赔案处理涉及的保单

			mLLContStateSet.add(tLLContStateSchema);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 退未满期保费
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getRSRiskBackFee(pLCPolSchema)) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 退财务的应收费用信息
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getRecedeFee(pLCPolSchema)) {
				return false;
			}

		}
		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－给付后动作处理－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－附加险退还未满期保费－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

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
		tTransferData.setNameAndValue("CalDate", this.mInsDate); // 计算时点
		tTransferData.setNameAndValue("DealDate", "2"); // 处理时间,1-包括计算时点,2不包括计算时点
		tTransferData.setNameAndValue("DealType", "1"); // 处理方式,1-指定日期之后,2-指定日期之前
		tTransferData.setNameAndValue("DealMode", "2"); // 处理模式,1-直接删除,2-不删除
		tVData.add(tTransferData);
		tVData.add(tLCPolSchema);

		LLBalRecedeFeeBL tLLBalRecedeFeeBL = new LLBalRecedeFeeBL();
		if (tLLBalRecedeFeeBL.submitData(tVData, mOperate) == false) {
			this.mErrors.copyAllErrors(tLLBalRecedeFeeBL.mErrors);
			return false;
		} else {
			VData tempVData = tLLBalRecedeFeeBL.getResult();
			MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			this.mMMap.add(tMMap);
		}

		logger.debug("----------处理出险时点[包括该时点]之前的应收数据----------");
		tVData = new VData();
		tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", this.mClmNo); // 赔案号
		tTransferData.setNameAndValue("CalDate", this.mInsDate); // 计算时点
		tTransferData.setNameAndValue("DealDate", "1"); // 处理时间,1-包括计算时点,2不包括计算时点
		tTransferData.setNameAndValue("DealType", "2"); // 处理方式,1-指定日期之后,2-指定日期之前
		tTransferData.setNameAndValue("DealMode", "2"); // 处理模式,1-直接删除,2-不删除
		tVData.add(tTransferData);
		tVData.add(tLCPolSchema);

		tLLBalRecedeFeeBL = new LLBalRecedeFeeBL();
		if (tLLBalRecedeFeeBL.submitData(tVData, mOperate) == false) {
			this.mErrors.copyAllErrors(tLLBalRecedeFeeBL.mErrors);
			return false;
		} else {
			VData tempVData = tLLBalRecedeFeeBL.getResult();
			MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			this.mMMap.add(tMMap);
		}

		logger.debug("----------退财务的应收记录-----getRecedeFee-----结束----------");
		return true;
	}

	/**
	 * 目的:收回已经给客户的--附加险退还未满期保费 判断本次赔案是否有,9.附加险退未满期保费[225,264]的记录
	 * 如果有,判断已经结案的赔案是否有过该记录,如果有,退费,存负值
	 */
	private boolean getRSRiskRecedeFee(LCPolSchema pLCPolSchema) {

		logger.debug("--------------------------------收回已经退给客户的附加险未满期保费-----开始--------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tPolNo = StrTool.cTrim(pLCPolSchema.getPolNo());
		String tRiskCode = StrTool.cTrim(pLCPolSchema.getRiskCode());

		if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "15")) {
			double tCal = mLLClaimPubFunBL.getPolClmBalPay(pLCPolSchema,
					mClmNo, "D0501");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 插入新的数据
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tCal == -1) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			} else if (tCal > 0) {
				LLBalanceSchema tLLBalanceSchema = setLLBalance("D05", "D0501",
						"TF", -tCal, pLCPolSchema);
				this.mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
			}

		}

		logger.debug("--------------------------------收回已经退给客户的附加险未满期保费-----结束--------------------------------");
		return true;
	}

	/**
	 * 目的:附加险退还未满期保费
	 * 
	 * @param tLCPolSchema
	 * @return
	 */
	private boolean getRSRiskBackFee(LCPolSchema pLCPolSchema) {

		logger.debug("--------------------------------附加险退还未满期保费-----开始--------------------------------");

		String tRiskCode = StrTool.cTrim(pLCPolSchema.getRiskCode());
		if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "15")
				&& !mLLClaimPubFunBL.getPolNoAddPay(this.mClmNo, "a",
						pLCPolSchema)) {

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 调用保全公式
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tPolNo = pLCPolSchema.getPolNo();
			double tCal = mLLClaimPubFunBL.getCashValue(tPolNo, tRiskCode,
					this.mInsDate);

			logger.debug("----合同号:[" + pLCPolSchema.getContNo()
					+ "],险种号:[" + pLCPolSchema.getPolNo() + "],险种:["
					+ pLCPolSchema.getRiskCode() + "],计算金额:[" + tCal + "]");
			if (tCal == -1) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			} else {
				LLBalanceSchema tLLBalanceSchema = setLLBalance("D05", "D0501",
						"TF", tCal, pLCPolSchema);
				this.mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
			}
		}

		logger.debug("--------------------------------附加险退还未满期保费-----结束--------------------------------");

		return true;

	}

	/**
	 * 设置结算信息
	 * 
	 * @param pFeeType:业务类型
	 * @param pSubFeeType:子业务类型
	 * @param pFeeFinaType:财务类型
	 * @param pCalValue:计算金额
	 * @param pLCPolSchema:
	 * @return
	 */
	private LLBalanceSchema setLLBalance(String pFeeType, String pSubFeeType,
			String pFeeFinaType, double pCalValue, LCPolSchema pLCPolSchema) {

		LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
		tLLBalanceSchema.setClmNo(this.mClmNo);
		tLLBalanceSchema.setFeeOperationType(pFeeType); // 业务类型
		tLLBalanceSchema.setSubFeeOperationType(pSubFeeType); // 子业务类型
		tLLBalanceSchema.setFeeFinaType(pFeeFinaType); // 财务类型

		tLLBalanceSchema.setBatNo("0"); // 批次号
		tLLBalanceSchema.setOtherNo("0"); // 其它号码
		tLLBalanceSchema.setOtherNoType("0"); // 财务类型

		tLLBalanceSchema.setGrpContNo(pLCPolSchema.getGrpContNo()); // 集体合同号码
		tLLBalanceSchema.setContNo(pLCPolSchema.getContNo()); // 合同号码
		tLLBalanceSchema.setGrpPolNo(pLCPolSchema.getGrpPolNo()); // 集体保单号码
		tLLBalanceSchema.setPolNo(pLCPolSchema.getPolNo()); // 保单号码

		tLLBalanceSchema.setDutyCode("0"); // 责任编码
		tLLBalanceSchema.setGetDutyKind("0"); // 给付责任类型
		tLLBalanceSchema.setGetDutyCode("0"); // 给付责任编码

		tLLBalanceSchema.setKindCode(pLCPolSchema.getKindCode()); // 险类编码
		tLLBalanceSchema.setRiskCode(pLCPolSchema.getRiskCode()); // 险种编码
		tLLBalanceSchema.setRiskVersion(pLCPolSchema.getRiskVersion()); // 险种版本

		tLLBalanceSchema.setSaleChnl(pLCPolSchema.getSaleChnl()); // 销售渠道
		tLLBalanceSchema.setAgentCode(pLCPolSchema.getAgentCode()); // 代理人编码
		tLLBalanceSchema.setAgentGroup(pLCPolSchema.getAgentGroup()); // 代理人组别

		tLLBalanceSchema.setGetDate(this.CurrentDate); // 给付日期
		tLLBalanceSchema.setPay(pCalValue); // 赔付金额

		tLLBalanceSchema.setPayFlag("0"); // 支付标志,0未支付,1已支付
		tLLBalanceSchema.setState("0"); // 状态,0有效
		tLLBalanceSchema.setDealFlag("0"); // 处理标志,0未处理

		tLLBalanceSchema.setManageCom(pLCPolSchema.getManageCom()); // 管理机构
		tLLBalanceSchema.setAgentCom(pLCPolSchema.getAgentCom()); // 代理机构
		tLLBalanceSchema.setAgentType(pLCPolSchema.getAgentType()); // 代理机构内部分类

		tLLBalanceSchema.setOperator(mGlobalInput.Operator); // 操作员
		tLLBalanceSchema.setMakeDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setMakeTime(this.CurrentTime); // 入机时间
		tLLBalanceSchema.setModifyDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setModifyTime(this.CurrentTime); // 入机时间

		tLLBalanceSchema.setOriPay(pCalValue); // 原始金额

		return tLLBalanceSchema;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－附加险退还未满期保费－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */

	private boolean prepareOutputData() {
		String tSql = "delete from LLContState where 1=1" + " and ClmNo  ='"
				+ "?ClmNo?" + "'";
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(tSql);
		sqlbv12.put("ClmNo", this.mClmNo);
		this.mMMap.put(sqlbv12, "DELETE");
		this.mMMap.put(mLLContStateSet, "DELETE&INSERT");

		mInputData.clear();
		mInputData.add(mMMap);
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
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimContDealBLF";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
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
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "Y")));
		
		tTransferData.setNameAndValue("CValiDate", mLCPolSchema.getCValiDate());
		
		tTransferData.setNameAndValue("AccidentDate",this.mInsDate);


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

	public static void main(String[] args) {

		String tClmNo = "90000108846"; /* 赔案号 */

		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(tClmNo);
		if (tLLRegisterDB.getInfo() == false) {
			logger.debug("------------------------------------------------------");
			logger.debug("--查询立案信息失败!");
			logger.debug("------------------------------------------------------");
		}

		String tClmState = tLLRegisterDB.getClmState();
		String tRgtClass = tLLRegisterDB.getRgtClass();

		GlobalInput tG = new GlobalInput();
		tG.Operator = tLLRegisterDB.getOperator();
		tG.ManageCom = tLLRegisterDB.getMngCom();
		tG.ComCode = tLLRegisterDB.getMngCom();

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ClmNo", tClmNo);

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		LLClaimContPolDealAutoBL tLLClaimContPolDealAutoBL = new LLClaimContPolDealAutoBL();

		if (tLLClaimContPolDealAutoBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}

		int n = tLLClaimContPolDealAutoBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content
					+ "原因是: "
					+ tLLClaimContPolDealAutoBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}

}
