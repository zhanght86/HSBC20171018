package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLBalanceDB;
import com.sinosoft.lis.db.LLContStateDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLBalanceSchema;
import com.sinosoft.lis.schema.LLContStateSchema;
import com.sinosoft.lis.vschema.LLBalanceSet;
import com.sinosoft.lis.vschema.LLContStateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
 * Description: 合同,险种结论--调整计算出来的金额
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
public class LLClaimContPolDealAdjustPayBL {
private static Logger logger = Logger.getLogger(LLClaimContPolDealAdjustPayBL.class);

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

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();

	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	// private LCPolSchema mLCPolSchema = new LCPolSchema();
	// private LLBalanceSchema mLLBalanceSchema = new LLBalanceSchema();

	private String mClmNo = ""; // 赔案号
	private String mContNo = ""; // 合同号
	private String mPolNo = ""; // 险种号

	public LLClaimContPolDealAdjustPayBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------合同处理调整-----LLClaimContPolDealAdjustPayBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkData()) {
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

		logger.debug("----------合同处理调整-----LLClaimContPolDealAdjustPayBL测试-----结束----------");
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
		this.mOperate = StrTool.cTrim(cOperate);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		this.mClmNo = StrTool.cTrim((String) mTransferData
				.getValueByName("ClmNo")); // 赔案号
		this.mContNo = StrTool.cTrim((String) mTransferData
				.getValueByName("ContNo")); // 合同号
		this.mPolNo = StrTool.cTrim((String) mTransferData
				.getValueByName("PolNo")); // 险种号

		logger.debug("--运算符：" + mOperate);

		return true;
	}

	/**
	 * 校验数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (mOperate.length() == 0) {
			mErrors.addOneError("前台传入的运算符号为空，操作终止!!!");
			return false;
		}
		return true;

	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No0.1 调整结算金额计算需要的信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mOperate.equals("AdjustPay")) {
			if (!getLLContPolAdjustPay()) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 调整合同状态表的开始时间,[针对于二核]
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mOperate.equals("AdjustDate")) {
			if (!getLLContStateAdjustDate()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－合同险种处理金额调整－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 调整结算的金额
	 * 
	 * @return
	 */
	private boolean getLLContPolAdjustPay() {

		logger.debug("---------------------------------进行合同险种处理金额----开始---------------------------------");
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 获取金额调整传进来的参数信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tFeeOperationType = (String) mTransferData
				.getValueByName("FeeOperationType"); // 业务类型
		String tSubFeeOperationType = (String) mTransferData
				.getValueByName("SubFeeOperationType"); // 子业务类型
		String tAdjRemark = (String) mTransferData.getValueByName("AdjRemark"); // 调整备注
		String tNewPay = (String) mTransferData.getValueByName("NewPay"); // 新的调整后的金额
		double tDNewPay = Arith.round(Double.parseDouble(tNewPay), 2); // 新的调整后的金额

		String tMessage = "";
		tMessage = "险种号:[" + mPolNo + "],业务类型:[" + tFeeOperationType
				+ "],子业务类型:[" + tSubFeeOperationType + "],新的调整金额:[" + tDNewPay
				+ "]";

		logger.debug("---------------------------------------------------------------");
		logger.debug("--" + tMessage);
		logger.debug("---------------------------------------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 进行结算类型的判断
		 * 只有合同处理，二次核保处理过的项目可以金额调整
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tFeeTypeA = tFeeOperationType.substring(0, 1); // 合同处理
		String tFeeTypeB = tFeeOperationType.substring(0, 2); // 二次核保处
		if (tFeeTypeA.equals("D") || tFeeTypeB.equals("C3")) {

		} else {
			// mErrors.addOneError("调整结算金额时,选择的业务类型不是合同,险种处理的业务类型!!!"+tMessage);
			// return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 得到结算信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBalanceDB tLLBalanceDB = new LLBalanceDB();
		tLLBalanceDB.setClmNo(mClmNo);
		tLLBalanceDB.setPolNo(mPolNo);
		tLLBalanceDB.setFeeOperationType(tFeeOperationType);
		tLLBalanceDB.setSubFeeOperationType(tSubFeeOperationType);
		LLBalanceSet tLLBalanceSet = tLLBalanceDB.query();

		if (tLLBalanceDB.mErrors.needDealError()) {
			mErrors.addOneError("调整结算金额时进行结算信息查询失败!!!" + tMessage);
			return false;
		}

		if (tLLBalanceSet.size() != 1) {
			mErrors.addOneError("调整结算金额时进行结算信息查询结果不唯一!!!" + tMessage);
			return false;
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 得到数据库的原始值,与新值进行比较,不能大于原始值
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBalanceSchema tLLBalanceSchema = tLLBalanceSet.get(1);
		double tOldPay = tLLBalanceSchema.getOriPay();
		if (tDNewPay > tOldPay) {
			mErrors.addOneError("调整后金额为:[" + tDNewPay + "],调整前的金额为:[" + tOldPay
					+ "].调整后的金额不能大于调整前的金额!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 校验通过,设置新值
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tNewAdjRemark = "[操作员:" + mGlobalInput.Operator + ",日期:"
				+ CurrentDate + ",时间:" + CurrentTime + ",本次调整为:" + tDNewPay
				+ ",内容:" + tAdjRemark + "]";
		String tOldAdjRemark = StrTool.cTrim(tLLBalanceSchema.getAdjRemark());

		logger.debug("---------------------------------------------------------------");
		logger.debug("--新备注：" + tNewAdjRemark);
		logger.debug("--老备注：" + tOldAdjRemark);
		logger.debug("---------------------------------------------------------------");

		tLLBalanceSchema.setAdjRemark(tNewAdjRemark + tOldAdjRemark);
		// tLLBalanceSchema.setAdjRemark(tNewAdjRemark);
		tLLBalanceSchema.setPay(tDNewPay);
		mMMap.put(tLLBalanceSchema, "UPDATE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 删除财务数据
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql3 = "delete from LLBnf where ClmNo='" + "?ClmNo?"
				+ "' and BnfKind = 'A'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql3);
		sqlbv.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv, "DELETE");

		String tSql4 = "delete from LJSGet where OtherNo='" + "?ClmNo?"
				+ "' and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql4);
		sqlbv1.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv1, "DELETE");

		String tSql5 = "delete from LJSGetClaim where OtherNo='" + "?ClmNo?"
				+ "' and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql5);
		sqlbv2.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv2, "DELETE");

		logger.debug("---------------------------------进行合同险种处理金额----结束---------------------------------");

		return true;
	}

	/**
	 * 调整合同状态表的开始时间,[针对于二核]
	 * 
	 * @return
	 */
	private boolean getLLContStateAdjustDate() {
		logger.debug("---------------------------------调整合同状态的开始时间----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 调整合同状态表的开始时间,[针对于二核]
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 调整合同状态表的开始时间,[针对于二核]
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSqlA = "delete from LLContState where ClmNo  ='" + "?ClmNo?"
				+ "' and PolNo  ='" + "?PolNo?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSqlA);
		sqlbv3.put("ClmNo", this.mClmNo);
		sqlbv3.put("PolNo", this.mPolNo);
		this.mMMap.put(sqlbv3, "DELETE");

		String tStartDate = StrTool.cTrim((String) mTransferData
				.getValueByName("ContStaDate"));
		if (tStartDate.length() == 0) {
			mErrors.addOneError("合同终止信息的开始时间不能为空!!!");
			return false;
		}

		LLContStateDB tLLContStateDB = new LLContStateDB();
		tLLContStateDB.setClmNo(mClmNo);
		tLLContStateDB.setContNo(mContNo);
		tLLContStateDB.setPolNo(mPolNo);
		LLContStateSet tLLContStateSet = tLLContStateDB.query();

		logger.debug("---------------------------------------------------------------");
		logger.debug("--查询出来的记录数:[" + tLLContStateSet.size() + "]");
		logger.debug("---------------------------------------------------------------");

		if (tLLContStateSet.size() != 1) {
			mErrors.addOneError("合同终止信息不唯一,操作终止!!!");
			return false;
		}

		LLContStateSchema tLLContStateSchema = tLLContStateSet.get(1);
		tLLContStateSchema.setRemark(tLLContStateSchema.getRemark()
				+ ",手工调整合同终止的开始时间,原时间为[" + tLLContStateSchema.getStartDate()
				+ "]");
		tLLContStateSchema.setStartDate(tStartDate);
		tLLContStateSchema.setEndDate("");
		tLLContStateSchema.setOperator(mGlobalInput.Operator);
		tLLContStateSchema.setMakeDate(this.CurrentDate);
		tLLContStateSchema.setMakeTime(this.CurrentTime);
		tLLContStateSchema.setModifyDate(this.CurrentDate);
		tLLContStateSchema.setModifyTime(this.CurrentTime);

		mMMap.put(tLLContStateSchema, "DELETE&INSERT");

		logger.debug("---------------------------------调整合同状态的开始时间----结束---------------------------------");
		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－合同险种处理金额调整－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

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

	public static void main(String[] args) {

		String tClaimFeeCode = "abcdefg";
		tClaimFeeCode = tClaimFeeCode.substring(1);

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86000000";
		tG.ComCode = "86110000";

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ClmNo", "90000004014");
		// tTransferData.setNameAndValue("ContNo","230110000003347");
		tTransferData.setNameAndValue("PolNo", "210110000007405");
		tTransferData.setNameAndValue("FeeOperationType", "D06");
		tTransferData.setNameAndValue("SubFeeOperationType", "D0601");
		tTransferData.setNameAndValue("NewPay", "3900");
		tTransferData.setNameAndValue("AdjRemark", "少给点");

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		LLClaimContPolDealAdjustPayBL tLLClaimContPolDealAdjustPayBL = new LLClaimContPolDealAdjustPayBL();

		if (tLLClaimContPolDealAdjustPayBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}

		int n = tLLClaimContPolDealAdjustPayBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content
					+ "原因是: "
					+ tLLClaimContPolDealAdjustPayBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}

}
