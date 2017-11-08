package com.sinosoft.lis.tb;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.CManageFee;
import com.sinosoft.lis.pubfun.DealAccount;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCGetToAccSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 帐户余额试算接口类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006-05-17
 * </p>
 * <p>
 * Company: sinodoft
 * </p>
 * 
 * @author Chenrong
 * @version 1.0
 */

public class PreComputAccBala {
private static Logger logger = Logger.getLogger(PreComputAccBala.class);

	/** 存放结果 */
	public VData mVResult = new VData();
	public String mResult = "";

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 个人投保单表 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	/**
	 * sql绑定变量类
	 */
	private SQLwithBindVariables sqlbv= new SQLwithBindVariables();
	public PreComputAccBala() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (this.getInputData() == false) {
			return false;
		}

		// 数据操作业务处理
		if (this.dealData() == false) {
			return false;
		}

		return true;

	}

	public boolean getInputData() {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		LCPolSchema tLCPolSchema = (LCPolSchema) mInputData
				.getObjectByObjectName("LCPolSchema", 1);
		if (tLCPolSchema != null) {
			mLCPolSchema = tLCPolSchema;
			return true;
		} else {
			CError.buildErr(this, "没有得到保单信息！");
			return false;
		}

	}

	public boolean dealData() {
		LCPremSet tLCPremSet = queryLCPrem(mLCPolSchema.getPolNo());
		VData tPolDetail = new VData();
		tPolDetail.add(tLCPremSet);
		// 把tLMRiskSchema置为空，到DealAccount方法去查询险种描述
		LMRiskSchema tLMRiskSchema = null;

		if (DealAccount(tPolDetail, mLCPolSchema, tLMRiskSchema) == false) {
			CError.buildErr(this, "帐户余额试算失败！");
			return false;
		}
		return true;
	}

	/**
	 * 根据保单号查询保费项表
	 * 
	 * @param PolNo
	 *            保单号
	 * @return LCPremSet or null
	 */
	public LCPremSet queryLCPrem(String PolNo) {
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String sqlStr = "select * from LCPrem where PolNo='?PolNo?' and needacc='1'";
		sqlbv.sql(sqlStr);
		sqlbv.put("PolNo", PolNo);
		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = tLCPremSchema.getDB();
		tLCPremSet = tLCPremDB.executeQuery(sqlbv);
		if (tLCPremDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCPrem";
			tError.errorMessage = "保费项表查询失败!";
			this.mErrors.addOneError(tError);
			tLCPremSet.clear();

			return null;
		}
		if (tLCPremSet.size() == 0) {
			return null;
		}

		return tLCPremSet;
	}

	/**
	 * 处理账户类险种的账户信息
	 * 
	 * @param PolDetail
	 *            VData
	 * @param inLCPolSchema
	 *            LCPolSchema
	 * @param inLMRiskSchema
	 *            LMRiskSchema
	 * @return boolean
	 */
	private boolean DealAccount(VData PolDetail, LCPolSchema inLCPolSchema,
			LMRiskSchema inLMRiskSchema) {

		if (inLMRiskSchema == null) {
			// 查询险种描述表：是否和帐户相关
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(inLCPolSchema.getRiskCode());

			if (tLMRiskDB.getInfo() == false) {
				CError.buildErr(this, "查询险种描述表失败！");

				return false;
			}

			inLMRiskSchema = tLMRiskDB.getSchema();
		}

		if ((inLMRiskSchema.getInsuAccFlag() == null)
				|| inLMRiskSchema.getInsuAccFlag().equals("N")) {
			CError.buildErr(this, "该险种没有帐户！");
			return false;
		}

		String Rate = null; // 分配比率置空，待调整

		LCPremSet tLCPremSet = (LCPremSet) PolDetail.getObjectByObjectName(
				"LCPremSet", 0);
		DealAccount tDealAccount = new DealAccount(this.mGlobalInput);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("AccCreatePos", "1"); // 生成位置：承保时
		tTransferData.setNameAndValue("OtherNoType", "1"); // 其它号码类型：个人险种保单号
		tTransferData.setNameAndValue("Rate", Rate);

		// 注意：此时数据尚未存入数据库,因此，暂时用投保单号代替保单号。
		tTransferData.setNameAndValue("PolNo", inLCPolSchema.getProposalNo());
		tTransferData.setNameAndValue("OtherNo", inLCPolSchema.getProposalNo());

		// 生成帐户结构
		VData tVData = tDealAccount.createInsureAccForBat(tTransferData,
				inLCPolSchema, inLMRiskSchema);

		if (tDealAccount.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tDealAccount.mErrors);
			return false;
		}
		// 计算并填充账户结构
		if (tVData == null) {
			CError.buildErr(this, "创建账户结构失败");
			return false;
		}

		// 创建管理费结构表
		LCInsureAccSet tmpLCInsureAccSet = (LCInsureAccSet) (tVData
				.getObjectByObjectName("LCInsureAccSet", 0));

		LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) (tVData
				.getObjectByObjectName("LCInsureAccSet", 0));
		LCPremToAccSet tLCPremToAccSet = (LCPremToAccSet) (tVData
				.getObjectByObjectName("LCPremToAccSet", 0));
		LCGetToAccSet tLCGetToAccSet = (LCGetToAccSet) (tVData
				.getObjectByObjectName("LCGetToAccSet", 0));
		LCInsureAccTraceSet tLCInsureAccTraceSet = (LCInsureAccTraceSet) (tVData
				.getObjectByObjectName("LCInsureAccTraceSet", 0));
		LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet) (tVData
				.getObjectByObjectName("LCInsureAccClassSet", 0));
		if (tLCInsureAccTraceSet == null) {
			tLCInsureAccTraceSet = new LCInsureAccTraceSet();
			tVData.add(tLCInsureAccTraceSet);
		}
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = null;
		VData feeData = null;
		// 创建管理费结构
		feeData = tDealAccount.getManageFeeStru(inLCPolSchema, tLCPremToAccSet,
				tmpLCInsureAccSet);
		if (feeData != null) {
			tLCInsureAccFeeSet = (LCInsureAccFeeSet) feeData
					.getObjectByObjectName("LCInsureAccFeeSet", 0);
			tLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet) feeData
					.getObjectByObjectName("LCInsureAccClassFeeSet", 0);
			tLCInsureAccFeeTraceSet = (LCInsureAccFeeTraceSet) feeData
					.getObjectByObjectName("LCInsureAccFeeTraceSet", 0);
			if (tLCInsureAccFeeTraceSet == null) {
				tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
			}

		}
		tVData.add(tLCInsureAccFeeSet);
		tVData.add(tLCInsureAccClassFeeSet);
		tVData.add(tLCInsureAccFeeTraceSet);

		// 计算管理费和账户注入资金
		tVData.add(tLCPremSet);
		tVData.add(inLCPolSchema);
		CManageFee cmanageFee = new CManageFee();
		boolean cal = cmanageFee.calPremManaFee(tVData, "1", inLCPolSchema
				.getPolNo(), "1", "BF");

		if (!cal || cmanageFee.mErrors.needDealError()) {
			if (cmanageFee.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(cmanageFee.mErrors);
			} else {
				CError.buildErr(this, "计算账户金额或管理费时出错");
			}
			return false;
		}

		mVResult.add(tLCInsureAccSet);
		mVResult.add(tLCInsureAccClassSet);
		return true;
	}

	public boolean getSumAccBala() {
		double tResult = 0;

		LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) mVResult
				.getObjectByObjectName("LCInsureAccSet", 0);
		if (tLCInsureAccSet == null || tLCInsureAccSet.size() == 0) {
			CError.buildErr(this, "读取账户表数据时出错！");
			return false;
		}
		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
			tResult = tResult + tLCInsureAccSet.get(i).getInsuAccBala();
			logger.debug("tResult== " + tResult);
		}
		mResult = mDecimalFormat.format(tResult);
		return true;
	}

	public String getResult() {
		getSumAccBala();
		return mResult;
	}

	public static void main(String[] args) {
		PreComputAccBala tPreComputAccBala = new PreComputAccBala();
		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "001";

		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo("110210000001096");
		tLCPolSchema.setSchema(tLCPolDB.query().get(1));

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLCPolSchema);
		tPreComputAccBala.submitData(tVData, null);
		String mResult = tPreComputAccBala.getResult();
		if (mResult == null || mResult == "") {
			logger.debug("计算失败！！！");
		} else {
			logger.debug("计算成功！！！");
			logger.debug("帐户余额：" + mResult);
		}
	}

}
