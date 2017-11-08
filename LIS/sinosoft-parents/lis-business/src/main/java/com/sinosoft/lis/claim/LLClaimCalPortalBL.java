package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.config.CacheService;
import com.sinosoft.lis.db.LLAccidentDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.tb.CachedRiskInfo;
import com.sinosoft.lis.vschema.LLAccidentSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 计算步骤入口：个险理赔计算的入口BL
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
public class LLClaimCalPortalBL implements BusinessService{
private static Logger logger = Logger.getLogger(LLClaimCalPortalBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();
	// private LCContHangUpBL mLCContHangUpBL = new LCContHangUpBL(mGlobalInput);
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	private LLClaimPubCheckBL mLLClaimPubCheckBL = new LLClaimPubCheckBL();
	private JdbcUrl mJdbcUrl = new JdbcUrl();

	// 理赔公用方法类
	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();
	private ExeSQL mExeSQL = new ExeSQL();

	private String mAccNo = ""; // 事件号
	private String mAccDate = ""; // 意外事故发生日期
	private String mClmNo = ""; // 赔案号
	private String mCusNo = ""; // 客户号
	private String mContType = ""; // 总单类型,1-个人总投保单,2-集体总单
	private String mClmState = ""; // 赔案状态，20立案，30审核

	public LLClaimCalPortalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------个险理算入口-----LLClaimCalPortal测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
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

		logger.debug("----------个险理算入口-----LLClaimCalPortal测试-----结束----------");
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

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mAccNo = (String) mTransferData.getValueByName("AccNo"); // 事件号
		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		this.mAccDate = (String) mTransferData.getValueByName("AccDate"); // 意外事故发生日期
		this.mContType = (String) mTransferData.getValueByName("ContType"); // 总单类型,1-个人总投保单,2-集体总单

		this.mClmState = (String) mTransferData.getValueByName("ClmState"); // 赔案状态，20立案，30审核

		return true;
	}

	/**
	 * 匹配理算总入口
	 * 
	 * @return
	 */
	private boolean dealData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 个险理算
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mContType.equals("1")) {
			if (!dealPerCalPortal()) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 团险理算
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mContType.equals("2")) {
			throw new RuntimeException("6.5不支持的操作");
			// if ( !dealGrpCalPortal() )
			// {
			// return false;
			// }
		}

		return true;
	}

	/**
	 * 个险理算
	 * 
	 * @return
	 */
	private boolean dealPerCalPortal() {

		VData tResult = new VData();// 定义返回的结果集
		String tReturn = ""; // 理算后返回的状态

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 计算步骤一：理赔计算前的数据校验
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimCalCheckBL tLLClaimCalCheckBL = new LLClaimCalCheckBL();
		if (!tLLClaimCalCheckBL.submitData(mInputData, "")) {

			int n = tLLClaimCalCheckBL.mErrors.getErrorCount();
			String tContent = "";

			for (int i = 0; i < n; i++) {
				tContent = tContent
						+ tLLClaimCalCheckBL.mErrors.getError(i).errorMessage;
			}
			this.mErrors.copyAllErrors(tLLClaimCalCheckBL.mErrors);
			logger.debug("-----提示信息:[" + tContent + "]\n");
			logger.debug("-----理算步骤一-----数据校验-----LLClaimCalCheckBL测试错误提示信息-----");
			return false;
		}
		this.mErrors.copyAllErrors(tLLClaimCalCheckBL.mErrors);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 个险计算步骤二：找出匹配保项
		 * 总单类型,1-个人总投保单,2-集体总单 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimCalMatchBL tLLClaimCalMatchBL = new LLClaimCalMatchBL();
		if (!tLLClaimCalMatchBL.submitData(mInputData, "")) {
			int n = tLLClaimCalMatchBL.mErrors.getErrorCount();
			String tContent = "";

			for (int i = 0; i < n; i++) {
				tContent = tContent
						+ tLLClaimCalMatchBL.mErrors.getError(i).errorMessage;
			}
			this.mErrors.copyAllErrors(tLLClaimCalMatchBL.mErrors);
			logger.debug("-----提示信息:[" + tContent + "]\n");
			logger.debug("-----理算步骤二-----匹配保项-----LLClaimCalMatchBL测试错误提示信息-----");
			return false;
		}
		tResult = tLLClaimCalMatchBL.getResult();
		this.mErrors.copyAllErrors(tLLClaimCalMatchBL.mErrors);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 个险计算步骤三：对匹配后的保项和费用进行过滤，并提交到数据库
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimCalMatchFilterBL tLLClaimCalMatchFilterBL = new LLClaimCalMatchFilterBL();
		if (tLLClaimCalMatchFilterBL.submitData(tResult, "") == false) {
			int n = tLLClaimCalMatchFilterBL.mErrors.getErrorCount();
			String tContent = "";

			for (int i = 0; i < n; i++) {
				tContent = tContent
						+ tLLClaimCalMatchFilterBL.mErrors.getError(i).errorMessage;
			}
			this.mErrors.copyAllErrors(tLLClaimCalMatchFilterBL.mErrors);
			logger.debug("-----提示信息:[" + tContent + "]\n");
			logger.debug("-----理算步骤三-----过滤匹配保项并保存-----LLClaimCalMatchFilterBL测试错误提示信息-----");
			return false;
		}
		this.mErrors.copyAllErrors(tLLClaimCalMatchFilterBL.mErrors);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 个险计算步骤四：进行免赔计算
		 * 由于只有医疗险有免陪额和社保/第三方给付金额,所以将社保/第三方给付金额的当做另一种免赔额一起进行扣除
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimCalFranchiseBL tLLClaimCalFranchiseBL = new LLClaimCalFranchiseBL();
		if (tLLClaimCalFranchiseBL.submitData(mInputData, "") == false) {
			int n = tLLClaimCalFranchiseBL.mErrors.getErrorCount();
			String tContent = "";

			for (int i = 0; i < n; i++) {
				tContent = tContent
						+ tLLClaimCalFranchiseBL.mErrors.getError(i).errorMessage;
			}
			this.mErrors.copyAllErrors(tLLClaimCalFranchiseBL.mErrors);
			logger.debug("-----提示信息:[" + tContent + "]\n");
			logger.debug("-----理算步骤四-----免赔额计算-----LLClaimCalFranchiseBL测试错误提示信息-----");
			return false;
		}
		this.mErrors.copyAllErrors(tLLClaimCalFranchiseBL.mErrors);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 个险计算步骤五：理赔正式计算
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimCalAutoBL tLLClaimCalAutoBL = new LLClaimCalAutoBL();
		if (tLLClaimCalAutoBL.submitData(mInputData, "") == false) {
			int n = tLLClaimCalAutoBL.mErrors.getErrorCount();
			String tContent = "";

			for (int i = 0; i < n; i++) {
				tContent = tContent
						+ tLLClaimCalAutoBL.mErrors.getError(i).errorMessage;
			}
			this.mErrors.copyAllErrors(tLLClaimCalAutoBL.mErrors);
			logger.debug("-----提示信息:[" + tContent + "]\n");
			logger.debug("-----理算步骤五-----理赔正式计算-----LLClaimCalFranchiseBL测试错误提示信息-----");
			return false;
		}
		this.mErrors.copyAllErrors(tLLClaimCalAutoBL.mErrors);

		tResult = tLLClaimCalAutoBL.getResult();
		TransferData tTransferData = new TransferData();
		tTransferData = (TransferData) tResult.getObjectByObjectName(
				"TransferData", 0);
		tReturn = (String) tTransferData.getValueByName("Return");

		// 返回TRUE说明有计算结果，可以进行下一步操作
		if (tReturn.equals("TRUE")) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 计算步骤六：理算后续加减保计算
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLClaimCalAutoAfterBL tLLClaimAutoAfterBL = new LLClaimCalAutoAfterBL();
			if (tLLClaimAutoAfterBL.submitData(mInputData, "") == false) {
				int n = tLLClaimAutoAfterBL.mErrors.getErrorCount();
				String tContent = "";

				for (int i = 0; i < n; i++) {
					tContent = tContent
							+ tLLClaimAutoAfterBL.mErrors.getError(i).errorMessage;
				}
				this.mErrors.copyAllErrors(tLLClaimAutoAfterBL.mErrors);
				logger.debug("-----提示信息:[" + tContent + "]\n");
				logger.debug("-----理算步骤六-----理算后续加减保计算-----LLClaimCalAutoAfterBL测试错误提示信息-----");
				return false;
			}
			this.mErrors.copyAllErrors(tLLClaimAutoAfterBL.mErrors);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 计算步骤七：计算理赔类型赔付
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLClaimCalDutyKindBL tLLClaimCalDutyKindBL = new LLClaimCalDutyKindBL();
			if (tLLClaimCalDutyKindBL.submitData(mInputData, "") == false) {
				int n = tLLClaimCalDutyKindBL.mErrors.getErrorCount();
				String tContent = "";

				for (int i = 0; i < n; i++) {
					tContent = tContent
							+ tLLClaimCalDutyKindBL.mErrors.getError(i).errorMessage;
				}
				this.mErrors.copyAllErrors(tLLClaimCalDutyKindBL.mErrors);
				logger.debug("-----提示信息:[" + tContent + "]\n");
				logger.debug("-----理算步骤七-----计算理赔类型的赔付-----LLClaimCalDutyKindBL测试错误提示信息-----");
				return false;
			}
			this.mErrors.copyAllErrors(tLLClaimCalDutyKindBL.mErrors);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0 计算步骤八：得到结算数据
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLClaimCalShouldPayBL tLLClaimCalShouldPayBL = new LLClaimCalShouldPayBL();
			if (tLLClaimCalShouldPayBL.submitData(mInputData, "") == false) {
				int n = tLLClaimCalShouldPayBL.mErrors.getErrorCount();
				String tContent = "";

				for (int i = 0; i < n; i++) {
					tContent = tContent
							+ tLLClaimCalShouldPayBL.mErrors.getError(i).errorMessage;

				}
				this.mErrors.copyAllErrors(tLLClaimCalShouldPayBL.mErrors);
				logger.debug("-----提示信息:[" + tContent + "]\n");
				logger.debug("-----理算步骤八-----得到结算数据-----LLClaimCalShouldPayBL测试错误提示信息-----");
				return false;
			}
			this.mErrors.copyAllErrors(tLLClaimCalShouldPayBL.mErrors);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.0
			 * 计算步骤最终九：更新赔案各项金额信息 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			LLClaimCalFinalBL tLLClaimCalFinalBL = new LLClaimCalFinalBL();
			if (tLLClaimCalFinalBL.submitData(mInputData, "") == false) {
				int n = tLLClaimCalFinalBL.mErrors.getErrorCount();
				String tContent = "";

				for (int i = 0; i < n; i++) {
					tContent = tContent
							+ tLLClaimCalFinalBL.mErrors.getError(i).errorMessage;

				}
				this.mErrors.copyAllErrors(tLLClaimCalFinalBL.mErrors);
				logger.debug("-----提示信息:[" + tContent + "]\n");
				logger.debug("-----理算步骤最终-----产生赔付汇总数据-----LLClaimCalFinalBL测试错误提示信息-----");
				return false;
			}
			this.mErrors.copyAllErrors(tLLClaimCalFinalBL.mErrors);

		} else {
			String strSQL1 = "delete from LLClaim where ClmNo='" + "?ClmNo?"
					+ "'";
			String strSQL2 = "delete from LLClaimDutyKind where ClmNo='"
					+ "?ClmNo?" + "'";
			String strSQL3 = "delete from LLClaimPolicy where ClmNo='"
					+ "?ClmNo?" + "'";
			String strSQL4 = "delete from LLClaimDetail where ClmNo='"
					+ "?ClmNo?" + "'";
			String strSQL5 = "delete from LLClaimDutyFee where ClmNo='"
					+ "?ClmNo?" + "'";
			String strSQL6 = "delete from LLToClaimDuty    where CaseNo='"
					+ "?ClmNo?" + "'";
			String strSQL7 = "delete from LLToClaimDutyFee where ClmNo ='"
					+ "?ClmNo?" + "'";
			
			String tTempSqlA = "delete from LLBalance where ClmNo='"
					+ "?ClmNo?" + "' and FeeOperationType = 'A'";
			String tTempSqlB = "delete from LLBnf where ClmNo ='" + "?ClmNo?"
					+ "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSQL1);
			sqlbv1.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv1, "DELETE");
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSQL2);
			sqlbv2.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv2, "DELETE");
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSQL3);
			sqlbv3.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv3, "DELETE");
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(strSQL4);
			sqlbv4.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv4, "DELETE");
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(strSQL5);
			sqlbv5.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv5, "DELETE");
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(strSQL6);
			sqlbv6.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv6, "DELETE");
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(strSQL7);
			sqlbv7.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv7, "DELETE");
			
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tTempSqlA);
			sqlbv8.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv8, "DELETE");
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tTempSqlB);
			sqlbv9.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv9, "DELETE");
		}

		logger.debug("----------------个险理算后台提示信息打印------开始----------------");
		int n = this.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "提示信息: "
					+ this.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
		logger.debug("----------------个险理算后台提示信息打印------结束----------------");
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
			tError.moduleName = "LLClaimCalCheckBL";
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

	public static void main(String[] args) {

		String tClmNo = "86110020150510000001";

		logger.debug("start init LisCache");
		try{
			String sql = "select cacheName, cacheService from ldcacheservice where state = '1'";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(sql);
			ExeSQL exeSql = new ExeSQL();
			SSRS cSSRS = exeSql.execSQL(sqlbv10);
			for(int i=1;i<=cSSRS.getMaxRow();i++){
				String cacheName = cSSRS.GetText(i, 1);
				String cacheService = cSSRS.GetText(i, 2);
				Class tClass = Class.forName(cacheService);
				CacheService cs = (CacheService)tClass.newInstance();
				cs.setCacheName(cacheName);
				cs.initCache();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}

		logger.info("----------------------lalala-----------------------");
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 理赔--90000009175
		 * 生产--90000017746，90000016809 承保前--90000018856 22010219760819081X
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
		// mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
		// tLLUWAddFeeUI.mErrors.getError(0).errorMessage
		// mErrors.addOneError("合同号:["+tLCGetSchema.getContNo()+"]的查询出现问题,不能进行理赔计算!");

		String tSql = "select * from LLAccident where AccNo in "
				+ " (select CaseRelaNo from LLCaseRela where CaseNo = '"
				+ "?clmno?" + "')";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tSql);
		sqlbv11.put("clmno", tClmNo);
		LLAccidentDB tLLAccidentDB = new LLAccidentDB();
		LLAccidentSet tLLAccidentSet = tLLAccidentDB.executeQuery(sqlbv11);

		if (tLLAccidentSet.size() == 0) {
			logger.debug("----------------------无事故信息-----------------------");
			return;
		}

		String tAccNo = tLLAccidentSet.get(1).getAccNo();
		String tAccDate = tLLAccidentSet.get(1).getAccDate().substring(0, 10);

		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(tClmNo);
		if (tLLRegisterDB.getInfo() == false) {
			logger.debug("------------------------------------------------------------------------");
			logger.debug("--查询立案信息失败!");
			logger.debug("------------------------------------------------------------------------");
		}

		String tClmState = tLLRegisterDB.getClmState();
		String tRgtClass = tLLRegisterDB.getRgtClass();

		GlobalInput tG = new GlobalInput();
		tG.Operator = tLLRegisterDB.getOperator();
		tG.ManageCom = tLLRegisterDB.getMngCom();
		tG.ComCode = tLLRegisterDB.getMngCom();

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("AccNo", tAccNo);
		tTransferData.setNameAndValue("ClmNo", tClmNo);
		tTransferData.setNameAndValue("AccDate", tAccDate);
		tTransferData.setNameAndValue("ContType", tRgtClass); // 总单类型,1-个人总投保单,2-集体总单
		tTransferData.setNameAndValue("ClmState", tClmState); // 赔案状态，20立案，30审核

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		LLClaimCalPortalBL tLLClaimCalPortalBL = new LLClaimCalPortalBL();
		tLLClaimCalPortalBL.submitData(tVData, "");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No100.0 测试使用
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
