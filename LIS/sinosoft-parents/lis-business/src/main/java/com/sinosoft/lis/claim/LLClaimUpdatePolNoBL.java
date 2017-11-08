package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJAGetClaimDB;
import com.sinosoft.lis.db.LJSGetClaimDB;
import com.sinosoft.lis.db.LLBalanceDB;
import com.sinosoft.lis.db.LLBnfDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLClaimDutyFeeDB;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.db.LLClaimUWDetailDB;
import com.sinosoft.lis.db.LLClaimUWDutyFeeDB;
import com.sinosoft.lis.db.LLExemptDB;
import com.sinosoft.lis.db.LLPrepayDetailDB;
import com.sinosoft.lis.db.LLToClaimDutyDB;
import com.sinosoft.lis.db.LLToClaimDutyFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.schema.LJSGetClaimSchema;
import com.sinosoft.lis.schema.LLBalanceSchema;
import com.sinosoft.lis.schema.LLBnfSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LLClaimPolicySchema;
import com.sinosoft.lis.schema.LLClaimUWDetailSchema;
import com.sinosoft.lis.schema.LLClaimUWDutyFeeSchema;
import com.sinosoft.lis.schema.LLExemptSchema;
import com.sinosoft.lis.schema.LLPrepayDetailSchema;
import com.sinosoft.lis.schema.LLToClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LLToClaimDutySchema;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.vschema.LLBalanceSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLClaimDutyFeeSet;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.lis.vschema.LLClaimUWDetailSet;
import com.sinosoft.lis.vschema.LLClaimUWDutyFeeSet;
import com.sinosoft.lis.vschema.LLExemptSet;
import com.sinosoft.lis.vschema.LLPrepayDetailSet;
import com.sinosoft.lis.vschema.LLToClaimDutyFeeSet;
import com.sinosoft.lis.vschema.LLToClaimDutySet;
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
 * Description: 因为续保抽档更换了保单险种号,所以理赔涉及到的表也要同步更新
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.08.24--2005.08.24
 * @version 1.0
 */
public class LLClaimUpdatePolNoBL {
private static Logger logger = Logger.getLogger(LLClaimUpdatePolNoBL.class);

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

	private String mOldPolNo = ""; // 旧的保单号
	private String mNewPolNo = ""; // 新的保单号

	public LLClaimUpdatePolNoBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------理赔保单险种号换号-----LLClaimUpdatePolNoBL测试-----开始----------");

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
		logger.debug("----------理赔保单险种号换号-----LLClaimUpdatePolNoBL测试-----结束----------");
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mOldPolNo = (String) mTransferData.getValueByName("OldPolNo"); // 旧的保单号
		this.mNewPolNo = (String) mTransferData.getValueByName("NewPolNo"); // 新的保单号
		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－开始－－－－－－－理赔保单险种号换号－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		String tSql = "";
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 赔案保单名细
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
		tLLClaimPolicyDB.setPolNo(this.mOldPolNo);
		LLClaimPolicySet tLLClaimPolicySet = tLLClaimPolicyDB.query();
		if (tLLClaimPolicyDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimPolicyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLClaimPolicy";
			tError.errorMessage = "LLClaimPolicy查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLClaimPolicySet tLLClaimPolicySaveSet = new LLClaimPolicySet();
		for (int i = 1; i <= tLLClaimPolicySet.size(); i++) {
			LLClaimPolicySchema tLLClaimPolicySchema = tLLClaimPolicySet.get(i);
			tLLClaimPolicySchema.setPolNo(this.mNewPolNo);
			tLLClaimPolicySchema.setNBPolNo(this.mOldPolNo);
			tLLClaimPolicySaveSet.add(tLLClaimPolicySchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLClaimPolicy--更新数据为:" + tLLClaimPolicySet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLClaimPolicy where polno='" + "?polno?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv, "DELETE");
		mMMap.put(tLLClaimPolicySaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 赔付明细
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setPolNo(this.mOldPolNo);
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
		if (tLLClaimDetailDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimDetailDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLClaimDetail";
			tError.errorMessage = "LLClaimDetail查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLClaimDetailSet tLLClaimDetailSaveSet = new LLClaimDetailSet();
		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(i);
			tLLClaimDetailSchema.setPolNo(this.mNewPolNo);
			tLLClaimDetailSchema.setNBPolNo(this.mOldPolNo);
			tLLClaimDetailSaveSet.add(tLLClaimDetailSchema);

		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLClaimDetail--更新数据为:" + tLLClaimDetailSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLClaimDetail where polno='" + "?polno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv1, "DELETE");
		mMMap.put(tLLClaimDetailSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 责任费用统计
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDutyFeeDB tLLClaimDutyFeeDB = new LLClaimDutyFeeDB();
		tLLClaimDutyFeeDB.setPolNo(this.mOldPolNo);
		LLClaimDutyFeeSet tLLClaimDutyFeeSet = tLLClaimDutyFeeDB.query();
		if (tLLClaimDutyFeeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimDutyFeeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLClaimDutyFee";
			tError.errorMessage = "LLClaimDutyFee查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLClaimDutyFeeSet tLLClaimDutyFeeSaveSet = new LLClaimDutyFeeSet();
		for (int i = 1; i <= tLLClaimDutyFeeSet.size(); i++) {
			LLClaimDutyFeeSchema tLLClaimDutyFeeSchema = tLLClaimDutyFeeSet
					.get(i);
			tLLClaimDutyFeeSchema.setPolNo(this.mNewPolNo);
			tLLClaimDutyFeeSchema.setNBPolNo(this.mOldPolNo);
			tLLClaimDutyFeeSaveSet.add(tLLClaimDutyFeeSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLClaimDutyFee--更新数据为:"
				+ tLLClaimDutyFeeSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLClaimDutyFee where polno='" + "?polno?"
				+ "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv2, "DELETE");
		mMMap.put(tLLClaimDutyFeeSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 核赔履历
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimUWDetailDB tLLClaimUWDetailDB = new LLClaimUWDetailDB();
		tLLClaimUWDetailDB.setPolNo(this.mOldPolNo);
		LLClaimUWDetailSet tLLClaimUWDetailSet = tLLClaimUWDetailDB.query();
		if (tLLClaimUWDetailDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimUWDetailDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLClaimUWDetail";
			tError.errorMessage = "LLClaimUWDetail查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLClaimUWDetailSet tLLClaimUWDetailSaveSet = new LLClaimUWDetailSet();
		for (int i = 1; i <= tLLClaimUWDetailSet.size(); i++) {
			LLClaimUWDetailSchema tLLClaimUWDetailSchema = tLLClaimUWDetailSet
					.get(i);
			tLLClaimUWDetailSchema.setPolNo(this.mNewPolNo);
			tLLClaimUWDetailSchema.setNBPolNo(this.mOldPolNo);
			tLLClaimUWDetailSaveSet.add(tLLClaimUWDetailSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLClaimUWDetail--更新数据为:"
				+ tLLClaimUWDetailSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLClaimUWDetail where polno='" + "?polno?"
				+ "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv3, "DELETE");
		mMMap.put(tLLClaimUWDetailSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 核赔责任费用统计
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimUWDutyFeeDB tLLClaimUWDutyFeeDB = new LLClaimUWDutyFeeDB();
		tLLClaimUWDutyFeeDB.setPolNo(this.mOldPolNo);
		LLClaimUWDutyFeeSet tLLClaimUWDutyFeeSet = tLLClaimUWDutyFeeDB.query();
		if (tLLClaimUWDutyFeeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimUWDutyFeeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLClaimUWDutyFee";
			tError.errorMessage = "LLClaimUWDutyFee查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLClaimUWDutyFeeSet tLLClaimUWDutyFeeSaveSet = new LLClaimUWDutyFeeSet();
		for (int i = 1; i <= tLLClaimUWDutyFeeSet.size(); i++) {
			LLClaimUWDutyFeeSchema tLLClaimUWDutyFeeSchema = tLLClaimUWDutyFeeSet
					.get(i);
			tLLClaimUWDutyFeeSchema.setPolNo(this.mNewPolNo);
			tLLClaimUWDutyFeeSchema.setNBPolNo(this.mOldPolNo);
			tLLClaimUWDutyFeeSaveSet.add(tLLClaimUWDutyFeeSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLClaimUWDutyFee--更新数据为:"
				+ tLLClaimUWDutyFeeSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLClaimUWDutyFee where polno='" + "?polno?"
				+ "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv4, "DELETE");
		mMMap.put(tLLClaimUWDutyFeeSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 预付明细记录
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLPrepayDetailDB tLLPrepayDetailDB = new LLPrepayDetailDB();
		tLLPrepayDetailDB.setPolNo(this.mOldPolNo);
		LLPrepayDetailSet tLLPrepayDetailSet = tLLPrepayDetailDB.query();
		if (tLLPrepayDetailDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPrepayDetailDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLPrepayDetail";
			tError.errorMessage = "LLPrepayDetail查询失败!";
			this.mErrors.addOneError(tError);
		}
		for (int i = 1; i <= tLLPrepayDetailSet.size(); i++) {
			tLLPrepayDetailSet.get(i).setPolNo(this.mNewPolNo);
		}
		LLPrepayDetailSet tLLPrepayDetailSaveSet = new LLPrepayDetailSet();
		for (int i = 1; i <= tLLPrepayDetailSet.size(); i++) {
			LLPrepayDetailSchema tLLPrepayDetailSchema = tLLPrepayDetailSet
					.get(i);
			tLLPrepayDetailSchema.setPolNo(this.mNewPolNo);
			tLLPrepayDetailSchema.setNBPolNo(this.mOldPolNo);
			tLLPrepayDetailSaveSet.add(tLLPrepayDetailSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLPrepayDetail--更新数据为:"
				+ tLLPrepayDetailSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLPrepayDetail where polno='" + "?polno?"
				+ "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv5, "DELETE");
		mMMap.put(tLLPrepayDetailSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 理赔结算表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBalanceDB tLLBalanceDB = new LLBalanceDB();
		tLLBalanceDB.setPolNo(this.mOldPolNo);
		LLBalanceSet tLLBalanceSet = tLLBalanceDB.query();
		if (tLLBalanceDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLBalanceDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLBalance";
			tError.errorMessage = "LLBalance查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLBalanceSet tLLBalanceSaveSet = new LLBalanceSet();
		for (int i = 1; i <= tLLBalanceSet.size(); i++) {
			LLBalanceSchema tLLBalanceSchema = tLLBalanceSet.get(i);
			tLLBalanceSchema.setPolNo(this.mNewPolNo);
			tLLBalanceSchema.setNBPolNo(this.mOldPolNo);
			tLLBalanceSaveSet.add(tLLBalanceSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLBalance--更新数据为:" + tLLBalanceSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLBalance where polno='" + "?polno?" + "'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSql);
		sqlbv6.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv6, "DELETE");
		mMMap.put(tLLBalanceSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0 理赔受益人账户
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBnfDB tLLBnfDB = new LLBnfDB();
		tLLBnfDB.setPolNo(this.mOldPolNo);
		LLBnfSet tLLBnfSet = tLLBnfDB.query();
		if (tLLBnfDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLBnfDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLBnf";
			tError.errorMessage = "LLBnf查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLBnfSet tLLBnfSaveSet = new LLBnfSet();
		for (int i = 1; i <= tLLBnfSet.size(); i++) {
			LLBnfSchema tLLBnfSchema = tLLBnfSet.get(i);
			tLLBnfSchema.setPolNo(this.mNewPolNo);
			tLLBnfSchema.setNBPolNo(this.mOldPolNo);
			tLLBnfSaveSet.add(tLLBnfSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLBnf--更新数据为:" + tLLBnfSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLBnf where polno='" + "?polno?" + "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv7, "DELETE");
		mMMap.put(tLLBnfSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.0 保费豁免记录表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLExemptDB tLLExemptDB = new LLExemptDB();
		tLLExemptDB.setPolNo(this.mOldPolNo);
		LLExemptSet tLLExemptSet = tLLExemptDB.query();
		if (tLLExemptDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLExemptDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLExempt";
			tError.errorMessage = "LLExempt查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLExemptSet tLLExemptSaveSet = new LLExemptSet();
		for (int i = 1; i <= tLLExemptSet.size(); i++) {
			LLExemptSchema tLLExemptSchema = tLLExemptSet.get(i);
			tLLExemptSchema.setPolNo(this.mNewPolNo);
			tLLExemptSchema.setNBPolNo(this.mOldPolNo);
			tLLExemptSaveSet.add(tLLExemptSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLExempt--更新数据为:" + tLLExemptSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLExempt where polno='" + "?polno?" + "'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		sqlbv8.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv8, "DELETE");
		mMMap.put(tLLExemptSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.0 待理算责任
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLToClaimDutyDB tLLToClaimDutyDB = new LLToClaimDutyDB();
		tLLToClaimDutyDB.setPolNo(this.mOldPolNo);
		LLToClaimDutySet tLLToClaimDutySet = tLLToClaimDutyDB.query();
		if (tLLToClaimDutyDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLToClaimDutyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLToClaimDuty";
			tError.errorMessage = "LLToClaimDuty查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLToClaimDutySet tLLToClaimDutySaveSet = new LLToClaimDutySet();
		for (int i = 1; i <= tLLToClaimDutySet.size(); i++) {
			LLToClaimDutySchema tLLToClaimDutySchema = tLLToClaimDutySet.get(i);
			tLLToClaimDutySchema.setPolNo(this.mNewPolNo);
			tLLToClaimDutySchema.setNBPolNo(this.mOldPolNo);
			tLLToClaimDutySaveSet.add(tLLToClaimDutySchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLToClaimDuty--更新数据为:" + tLLToClaimDutySet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLToClaimDuty where polno='" + "?polno?" + "'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv9, "DELETE");
		mMMap.put(tLLToClaimDutySaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No11.0 待理算责任费用
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLToClaimDutyFeeDB tLLToClaimDutyFeeDB = new LLToClaimDutyFeeDB();
		tLLToClaimDutyFeeDB.setPolNo(this.mOldPolNo);
		LLToClaimDutyFeeSet tLLToClaimDutyFeeSet = tLLToClaimDutyFeeDB.query();
		if (tLLToClaimDutyFeeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLToClaimDutyFeeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLLToClaimDutyFee";
			tError.errorMessage = "LLToClaimDutyFee查询失败!";
			this.mErrors.addOneError(tError);
		}
		LLToClaimDutyFeeSet tLLToClaimDutyFeeSaveSet = new LLToClaimDutyFeeSet();
		for (int i = 1; i <= tLLToClaimDutyFeeSet.size(); i++) {
			LLToClaimDutyFeeSchema tLLToClaimDutyFeeSchema = tLLToClaimDutyFeeSet
					.get(i);
			tLLToClaimDutyFeeSchema.setPolNo(this.mNewPolNo);
			tLLToClaimDutyFeeSchema.setNBPolNo(this.mOldPolNo);
			tLLToClaimDutyFeeSaveSet.add(tLLToClaimDutyFeeSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LLToClaimDutyFee--更新数据为:"
				+ tLLToClaimDutyFeeSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LLToClaimDutyFee where polno='" + "?polno?"
				+ "'";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(tSql);
		sqlbv10.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv10, "DELETE");
		mMMap.put(tLLToClaimDutyFeeSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No12.0 理赔应付明细
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB();
		tLJSGetClaimDB.setPolNo(this.mOldPolNo);
		LJSGetClaimSet tLJSGetClaimSet = tLJSGetClaimDB.query();
		if (tLJSGetClaimDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSGetClaimDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLJSGetClaim";
			tError.errorMessage = "LJSGetClaim查询失败!";
			this.mErrors.addOneError(tError);
		}
		LJSGetClaimSet tLJSGetClaimSaveSet = new LJSGetClaimSet();
		for (int i = 1; i <= tLJSGetClaimSet.size(); i++) {
			LJSGetClaimSchema tLJSGetClaimSchema = tLJSGetClaimSet.get(i);
			tLJSGetClaimSchema.setPolNo(this.mNewPolNo);
			tLJSGetClaimSaveSet.add(tLJSGetClaimSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LJSGetClaim--更新数据为:" + tLJSGetClaimSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LJSGetClaim where polno='" + "?polno?" + "'";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tSql);
		sqlbv11.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv11, "DELETE");
		mMMap.put(tLJSGetClaimSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No13.0 理赔实付明细
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimDB.setPolNo(this.mOldPolNo);
		LJAGetClaimSet tLJAGetClaimSet = tLJAGetClaimDB.query();
		if (tLJAGetClaimDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAGetClaimDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimUpdatePolNoBL";
			tError.functionName = "getLJAGetClaim";
			tError.errorMessage = "LJAGetClaim查询失败!";
			this.mErrors.addOneError(tError);
		}
		LJAGetClaimSet tLJAGetClaimSaveSet = new LJAGetClaimSet();
		for (int i = 1; i <= tLJAGetClaimSet.size(); i++) {
			LJAGetClaimSchema tLJAGetClaimSchema = tLJAGetClaimSet.get(i);
			tLJAGetClaimSchema.setPolNo(this.mNewPolNo);
			tLJAGetClaimSaveSet.add(tLJAGetClaimSchema);
		}
		logger.debug("------------------------------------------------------");
		logger.debug("--LJAGetClaim--更新数据为:" + tLJAGetClaimSet.size());
		logger.debug("------------------------------------------------------");
		tSql = "delete from LJAGetClaim where polno='" + "?polno?" + "'";
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(tSql);
		sqlbv12.put("polno", this.mOldPolNo);
		mMMap.put(sqlbv12, "DELETE");
		mMMap.put(tLJAGetClaimSaveSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－结束－－－－－－－理赔保单险种号换号－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
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

	public MMap getMMap() {
		return mMMap;
	}

	public static void main(String[] args) {

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("OldPolNo", "345");
		tTransferData.setNameAndValue("NewPolNo", "210110000003981");

		VData tVData = new VData();
		tVData.addElement(tTransferData);

		LLClaimUpdatePolNoBL tLLClaimUpdatePolNoBL = new LLClaimUpdatePolNoBL();
		tLLClaimUpdatePolNoBL.submitData(tVData, "");
		tLLClaimUpdatePolNoBL.pubSubmit();

		int n = tLLClaimUpdatePolNoBL.mErrors.getErrorCount();
		logger.debug("-------------------------------------------------------");
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLClaimUpdatePolNoBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}

	}

}
