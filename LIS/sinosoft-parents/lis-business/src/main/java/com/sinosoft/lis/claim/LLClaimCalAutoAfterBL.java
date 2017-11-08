package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLClaimDutyKindSet;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:理算步骤六，理算后续加减保计算
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
public class LLClaimCalAutoAfterBL {
private static Logger logger = Logger.getLogger(LLClaimCalAutoAfterBL.class);

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

	// private String mAccNo = ""; //事件号
	// private String mAccDate = ""; //意外事故发生日期
	private String mClmNo = ""; // 赔案号
	// private String mCusNo = ""; //客户号
	// private String mContType = ""; //总单类型,1-个人总投保单,2-集体总单
	// private String mGetDutyKind; //理赔类型
	// private String mGetDutyCode; //责任编码

	// 理赔--赔案信息
	private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
	private LLClaimDutyKindSet mLLClaimDutyKindSet = new LLClaimDutyKindSet();
	private LLClaimPolicySet mLLClaimPolicySet = new LLClaimPolicySet();

	private double m_Sum_ClaimFee = 0; /* 赔案给付金额 */
	private double m_Sum_JFFee = 0; /* 赔案拒付金额 */

	public LLClaimCalAutoAfterBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------理算步骤六-----理算后续加减保计算-----LLClaimCalAutoAfterBL测试-----开始----------");
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

		logger.debug("----------理算步骤六-----理算后续加减保计算-----LLClaimCalAutoAfterBL测试-----结束----------");
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

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// this.mAccNo = (String) mTransferData.getValueByName("AccNo"); //事件号
		// this.mAccDate = (String) mTransferData.getValueByName("AccDate");
		// //意外事故发生日期
		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号

		return true;
	}

	private boolean dealData() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 处理加保数据，先冲减加保的保项的保额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		//zy 2009-12-21 MS目前的加保只是针对增额缴清方式，所以暂时屏蔽掉加保的冲减处理，如MS若有新的加保处理，则需要特殊处理
//		if (!dealAddInsAmnt()) {
//			return false;
//		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 如果没有匹配出豁免责任，则自动将其理赔类型删除
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!dealDelExempt()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.0 进行后续数据的处理
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!dealAfterData()) {
			return false;
		}

		return true;
	}

	/**
	 * 处理加保保额冲减的情况 先冲减加保的保项的保额,再冲减未加保保项的保额
	 * 
	 * @return
	 */
	private boolean dealAddInsAmnt() {
		String tSql = "";

		LLClaimDetailSet tLLClaimDetailSaveSet = new LLClaimDetailSet();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询该赔案下的所有保项信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setClmNo(this.mClmNo);
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
		if (tLLClaimDetailDB.mErrors.needDealError()) {
			mErrors.addOneError("理算后进行后续处理，查询保项信息失败,不能理算!!!"
					+ tLLClaimDetailDB.mErrors.getError(0).errorMessage);
			return false;
		}

		if (tLLClaimDetailSet == null || tLLClaimDetailSet.size() == 0) {
			mErrors.addOneError("理算后进行后续处理，查询保项信息为0,不能理算!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 循环该赔案下的所有保项信息,
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(i);

			String tPolNo = tLLClaimDetailSchema.getPolNo();
			String tDutyCode = tLLClaimDetailSchema.getDutyCode();
			String tGetDutyKind = tLLClaimDetailSchema.getGetDutyKind();
			String tGetDutyCode = tLLClaimDetailSchema.getGetDutyCode();

			double tAddAmnt = tLLClaimDetailSchema.getAddAmnt();// 加保的保额
			double tRealPay = tLLClaimDetailSchema.getRealPay();// 给付金额

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 判断保项的加保保额是否大于0,
			 * 如果大于0,则认为是加保的项,用赔付给付金先冲减加保保项的给付金
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tAddAmnt > 0) {
				tSql = "select * from LLClaimDetail where 1=1" + " and ClmNo='"
						+ "?ClmNo?" + "'" + " and PolNo='" + "?PolNo?" + "'"
						+ " and GetDutyCode='" + "?GetDutyCode?" + "'"
						+ " and GetDutyKind='" + "?GetDutyKind?" + "'"
						+ " and DutyCode like concat('" + "?DutyCode?" + "','%')"
						+ " and DutyCode not in ('" + "?DutyCode?" + "')";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("ClmNo", this.mClmNo);
				sqlbv.put("PolNo", tPolNo);
				sqlbv.put("GetDutyCode", tGetDutyCode);
				sqlbv.put("GetDutyKind", tGetDutyKind);
				sqlbv.put("DutyCode", tDutyCode);
				LLClaimDetailDB tAMLLClaimDetailDB = new LLClaimDetailDB();
				LLClaimDetailSet tAMLLClaimDetailSet = tAMLLClaimDetailDB
						.executeQuery(sqlbv);

				if (tAMLLClaimDetailDB.mErrors.needDealError()) {
					this.mErrors.copyAllErrors(tAMLLClaimDetailDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "LLClaimCalAutoBL";
					tError.functionName = "getPolPay";
					tError.errorMessage = "查询赔案加保保项信息出现错误!";
					this.mErrors.addOneError(tError);
					logger.debug("------------------------------------------------------");
					logger.debug("--LLClaimCalDutyKindBL.getAddInsAmnt()--查询赔案加保保项信息出现错误!"
									+ tSql);
					logger.debug("------------------------------------------------------");
					return false;
				}

				if (tAMLLClaimDetailSet == null
						|| tAMLLClaimDetailSet.size() == 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLClaimCalMatchBL";
					tError.functionName = "getLLAppClaimReason";
					tError.errorMessage = "查询赔案加保保项信息出现错误!";
					logger.debug("------------------------------------------------------");
					logger.debug("--LLClaimCalDutyKindBL.getAddInsAmnt()--查询赔案加保保项信息出现错误!"
									+ tSql);
					logger.debug("------------------------------------------------------");
					this.mErrors.addOneError(tError);
					return false;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4
				 * 循环加保的保项,进行给付金冲减
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

				double tBalAmnt = tRealPay;
//				LLClaimDetailSchema tLLClaimDetailSaveSchema = new LLClaimDetailSchema();
				for (int m = 1; m <= tAMLLClaimDetailSet.size(); m++) {
					LLClaimDetailSchema tAMLLClaimDetailSchema = new LLClaimDetailSchema();
					tAMLLClaimDetailSchema = tAMLLClaimDetailSet.get(m);

					// //加保保项的保额 - 给付的金额
					// double tAMAmnt = tAMLLClaimDetailSchema.getAmnt() -
					// tAMLLClaimDetailSchema.getRealPay() ;
					double tAMAmnt = tAMLLClaimDetailSchema.getAmnt()
							- tAMLLClaimDetailSchema.getRealPay();

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.6
					 * 加保前保项的给付金 > 加保保项的保额 , 则加保保项的给付金 = 加保保项的保额 加保前保项的给付金 <
					 * 加保保项的保额 , 则加保保项的给付金 = 加保前保项的给付金
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					if (tBalAmnt > tAMAmnt) {
						tAMLLClaimDetailSchema.setRealPay(tAMAmnt);
						tBalAmnt = tBalAmnt - tAMAmnt;
					} else if (tBalAmnt < tAMAmnt) {
						tAMLLClaimDetailSchema.setRealPay(tBalAmnt);
						tBalAmnt = 0;
					}

//					tLLClaimDetailSaveSchema.setSchema(tAMLLClaimDetailSchema);
					tLLClaimDetailSaveSet.add(tAMLLClaimDetailSchema);
				}

				tLLClaimDetailSchema.setRealPay(tBalAmnt);
				tLLClaimDetailSaveSet.add(tLLClaimDetailSchema);

			}

		}

		mMMap.put(tLLClaimDetailSaveSet, "UPDATE");
		return true;
	}

	/**
	 * 如果没有匹配出豁免责任，则自动将其理赔类型删除
	 * 
	 * @return
	 */
	private boolean dealDelExempt() {

		String tSql = "select * from LLClaimDetail where 1=1 "
				+ " and ClmNo = '" + "?ClmNo?" + "'"
				+ " and substr(GetDutyKind,2,2)='09'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("ClmNo", mClmNo);
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setClmNo(this.mClmNo);
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB
				.executeQuery(sqlbv1);

		logger.debug("---------------------------------------------------------------");
		logger.debug("--理算后进行后续处理,查询非豁免的理赔责任[" + tLLClaimDetailSet.size()
				+ "]:" + tSql);
		logger.debug("---------------------------------------------------------------");

		if (tLLClaimDetailDB.mErrors.needDealError()) {
			mErrors.addOneError("理算后进行后续处理，查询保项信息失败,不能理算!!!"
					+ tLLClaimDetailDB.mErrors.getError(0).errorMessage);
			return false;
		}

		if (tLLClaimDetailSet.size() == 0) {
			String tDSqlA = "delete from LLAppClaimReason where CaseNo='"
					+ "?ClmNo?" + "' and substr(ReasonCode,3,1) = ('9')";
			String tDSqlB = "delete from LLExempt where ClmNo='" + "?ClmNo?"
					+ "'";

			logger.debug("---------------------------------------------------------------");
			logger.debug("--理算后进行后续处理,删除豁免的理赔责任:" + tDSqlA);
			logger.debug("--理算后进行后续处理,删除豁免:" + tDSqlB);
			logger.debug("---------------------------------------------------------------");
			
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tDSqlA);
			sqlbv2.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv2, "DELETE");
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tDSqlB);
			sqlbv3.put("ClmNo", this.mClmNo);
			mMMap.put(sqlbv3, "DELETE");
		}

		return true;
	}

	/**
	 * 进行后续数据的处理
	 * 
	 * @return
	 */
	private boolean dealAfterData() {
		String tTempSqlA = "delete from LLToClaimDuty    where CaseNo='"
				+ "?ClmNo?" + "'";
		String tTempSqlB = "delete from LLToClaimDutyFee where ClmNo ='"
				+ "?ClmNo?" + "'";

		logger.debug("---------------------------------------------------------------");
		logger.debug("--理算后进行后续数据处理,删除临时表信息:" + tTempSqlA);
		logger.debug("--理算后进行后续数据处理,删除临时表信息:" + tTempSqlB);
		logger.debug("---------------------------------------------------------------");

		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tTempSqlA);
		sqlbv4.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv4, "DELETE");
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tTempSqlB);
		sqlbv5.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv5, "DELETE");

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
			tError.moduleName = "LLClaimCalDutyKindBL";
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

	}

}
