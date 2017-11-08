package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMCertifyDesDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LMCardRiskSchema;
import com.sinosoft.lis.schema.LMCertifyDesSchema;
import com.sinosoft.lis.schema.LMCertifyDesTraceSchema;
import com.sinosoft.lis.vschema.LMCardRiskSet;
import com.sinosoft.lis.vschema.LMCertifyDesSet;
import com.sinosoft.lis.vschema.LMCertifyDesTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 单证定义
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class CertifyDescBL {
private static Logger logger = Logger.getLogger(CertifyDescBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private GlobalInput mGlobalInput = new GlobalInput();

	private LMCertifyDesSet mLMCertifyDesSet = new LMCertifyDesSet();

	private LMCertifyDesTraceSet mLMCertifyDesTraceSet = new LMCertifyDesTraceSet();

	private LMCardRiskSet mLMCardRiskSet = new LMCardRiskSet();

	private String mOperateType = "";

	private String mCertifyClass = ""; // 记录单证类型P or D

	public CertifyDescBL() {
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
		mOperateType = cOperate;

		if (!getInputData())
			return false;

		// 数据操作业务处理
		if (mOperateType.equals("INSERT")) {
			if (!checkData())
				return false;
			if (!dealData())
				return false;
		}

		if (mOperateType.equals("DELETE")) {
			if (!dealData())
				return false;
		}

		if (mOperateType.equals("UPDATE")) {
			if (!checkData())
				return false;
			if (!dealData())
				return false;
		}
		return true;
	}

	/**
	 * 接收从CertifyDescSave.jsp中传递的数据
	 */
	private boolean getInputData() {
		mCertifyClass = (String) mInputData.get(0);// 接收单证类型
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mLMCertifyDesSet = ((LMCertifyDesSet) mInputData.getObjectByObjectName("LMCertifyDesSet", 0));

		// if (mCertifyClass.equals("D")) {
		// mLMCardRiskSet = ((LMCardRiskSet) cInputData.getObjectByObjectName(
		// "LMCardRiskSet", 0));
		// }
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		mInputData.clear();
		mInputData.add(mOperateType);
		mInputData.add(mCertifyClass);
		mInputData.add(mLMCertifyDesSet);
		mInputData.add(mLMCertifyDesTraceSet);
		// if (mCertifyClass.equals("D")) {
		// mInputData.add(mLMCardRiskSet);
		// }

		// 数据提交
		CertifyDescBLS tCertifyDescBLS = new CertifyDescBLS();
		if (!tCertifyDescBLS.submitData(mInputData, mOperateType)) {
			this.mErrors.copyAllErrors(tCertifyDescBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "CertifyDescBL";
			tError.functionName = "dealData";
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
	 * 校验传入的暂交费收据号是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	// 校验新增和修改时的数据，单证的号码不能长于20
	private boolean checkData() {
		// logger.debug("判断单证号码长度是否>20");
		LMCertifyDesSchema tLMCertifyDesSchema = new LMCertifyDesSchema();
		tLMCertifyDesSchema.setSchema(mLMCertifyDesSet.get(1));
		if (tLMCertifyDesSchema.getCertifyLength() > 20 || tLMCertifyDesSchema.getCertifyLength() < 0) {
			this.mErrors.copyAllErrors(tLMCertifyDesSchema.mErrors);
			CError tError = new CError();
			tError.moduleName = "CertifyDescBL";
			tError.functionName = "checkData";
			tError.errorMessage = "单证号码的长度不能小于0大于20位！";
			this.mErrors.addOneError(tError);
			return false;
		}

		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		tLMCertifyDesDB.setCertifyCode(mLMCertifyDesSet.get(1).getCertifyCode());
		LMCertifyDesSet tLMCertifyDesSet = new LMCertifyDesSet();
		tLMCertifyDesSet.set(tLMCertifyDesDB.query());

		if (mOperateType.equals("INSERT")) {
			if (tLMCertifyDesSet.size() > 0) {
				this.mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "CertifyDescBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该单证号码已经存在，不能进行新增操作！";
				this.mErrors.addOneError(tError);
				return false;
			}

			/*******************************************************************
			 * 判断若单证类型是D：定额单证时，所新增的不能是null
			 ******************************************************************/
			/*******************************************************************
			 * if (mCertifyClass.equals("D")) { for (int i = 1; i <=
			 * mLMCardRiskSet.size(); i++) { LMCardRiskSchema tLMCardRiskSchema =
			 * new LMCardRiskSchema();
			 * tLMCardRiskSchema.setSchema(mLMCardRiskSet.get(i)); String
			 * tRiskCode = tLMCardRiskSchema.getRiskCode(); String tPrem =
			 * String.valueOf(tLMCardRiskSchema.getPrem()); String tPremProp =
			 * tLMCardRiskSchema.getPremProp();
			 * 
			 * if (tRiskCode == null || tRiskCode.equals("")) {
			 * this.mErrors.copyAllErrors(tLMCardRiskSchema.mErrors); CError
			 * tError = new CError(); tError.moduleName = "CertifyDescBL";
			 * tError.functionName = "checkData"; tError.errorMessage = "在第" + i +
			 * "条记录中的险种代码不能是空！"; this.mErrors.addOneError(tError); return false; }
			 * if (tPrem == null || tPrem.equals("")) {
			 * this.mErrors.copyAllErrors(tLMCardRiskSchema.mErrors); CError
			 * tError = new CError(); tError.moduleName = "CertifyDescBL";
			 * tError.functionName = "checkData"; tError.errorMessage = "在第" + i +
			 * "条记录中的保费不能为空！"; this.mErrors.addOneError(tError); return false; }
			 * if (tPremProp == null || tPremProp.equals("")) {
			 * this.mErrors.copyAllErrors(tLMCardRiskSchema.mErrors); CError
			 * tError = new CError(); tError.moduleName = "CertifyDescBL";
			 * tError.functionName = "checkData"; tError.errorMessage = "在第" + i +
			 * "条记录中的保费特性不能是空！"; this.mErrors.addOneError(tError); return false; } } }
			 ******************************************************************/
			// 对一些特殊的字段进行赋值
			LMCertifyDesSchema mLMCertifyDesSchema = mLMCertifyDesSet.get(1);
			mLMCertifyDesSchema.setSubCode("0");
			mLMCertifyDesSchema.setRiskCode("0");
			mLMCertifyDesSchema.setRiskVersion("0");
			// mLMCertifyDesSchema.setState("0");// 0 启用，1 停用
			mLMCertifyDesSchema.setSplitOnSend("Y");// 发放到部门和代理人是否拆分标志，默认都是Y
			mLMCertifyDesSchema.setOperator(mGlobalInput.Operator);
			mLMCertifyDesSchema.setManageCom(mGlobalInput.ManageCom);
			mLMCertifyDesSchema.setMakeDate(PubFun.getCurrentDate());
			mLMCertifyDesSchema.setMakeTime(PubFun.getCurrentTime());

			LMCertifyDesTraceSchema mLMCertifyDesTraceSchema = new LMCertifyDesTraceSchema();
			Reflections tReflections = new Reflections();
			tReflections.transFields(mLMCertifyDesTraceSchema, mLMCertifyDesSchema);
			mLMCertifyDesTraceSet.add(mLMCertifyDesTraceSchema);// 记录轨迹
		}

		if (mOperateType.equals("UPDATE")) {
			if (tLMCertifyDesSet.size() <= 0) {
				this.mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "CertifyDescBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该单证号码不存在，不能进行修改操作！";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LMCertifyDesSchema mLMCertifyDesSchema = tLMCertifyDesSet.get(1);

				LMCertifyDesSchema tempLMCertifyDesSchema = mLMCertifyDesSet.get(1);// 临时存储
				mLMCertifyDesSet.clear();
				mLMCertifyDesSet.add(mLMCertifyDesSchema); // 填充除页面修改值以外的其他字段
				mLMCertifyDesSet.get(1).setState(tempLMCertifyDesSchema.getState());
				mLMCertifyDesSet.get(1).setHaveLimit(tempLMCertifyDesSchema.getHaveLimit());
				mLMCertifyDesSet.get(1).setMaxUnit1(tempLMCertifyDesSchema.getMaxUnit1());
				mLMCertifyDesSet.get(1).setMaxUnit2(tempLMCertifyDesSchema.getMaxUnit2());
				mLMCertifyDesSet.get(1).setHaveValidate(tempLMCertifyDesSchema.getHaveValidate());
				mLMCertifyDesSet.get(1).setValidate1(tempLMCertifyDesSchema.getValidate1());
				mLMCertifyDesSet.get(1).setValidate2(tempLMCertifyDesSchema.getValidate2());
				mLMCertifyDesSet.get(1).setNote(tempLMCertifyDesSchema.getNote());
				mLMCertifyDesSet.get(1).setOperator(mGlobalInput.Operator);
				mLMCertifyDesSet.get(1).setManageCom(mGlobalInput.ManageCom);
				mLMCertifyDesSet.get(1).setMakeDate(PubFun.getCurrentDate());
				mLMCertifyDesSet.get(1).setMakeTime(PubFun.getCurrentTime());

				LMCertifyDesTraceSchema mLMCertifyDesTraceSchema = new LMCertifyDesTraceSchema();
				Reflections tReflections = new Reflections();
				tReflections.transFields(mLMCertifyDesTraceSchema, mLMCertifyDesSet.get(1));
				mLMCertifyDesTraceSet.add(mLMCertifyDesTraceSchema);// 记录轨迹
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String tOperateType = "QUERY";
		String tCertifyCode = "04";
		String tCertifyCode_1 = "04";
		String tCertifyClass = "Y";
		LMCertifyDesSchema yLMCertifyDesSchema = new LMCertifyDesSchema();
		LMCertifyDesSet yLMCertifyDesSet = new LMCertifyDesSet();
		LMCardRiskSet yLMCardRiskSet = new LMCardRiskSet();

		LMCardRiskSchema yLMCardRiskSchema = new LMCardRiskSchema();
		yLMCardRiskSchema.setCertifyCode("1");
		yLMCardRiskSchema.setRiskCode("1");
		yLMCardRiskSchema.setPrem("1");
		yLMCardRiskSchema.setPremProp("1");
		yLMCardRiskSet.add(yLMCardRiskSchema);

		yLMCertifyDesSchema.setCertifyCode(tCertifyCode);
		yLMCertifyDesSchema.setRiskVersion(tCertifyCode_1);
		yLMCertifyDesSet.add(yLMCertifyDesSchema);

		VData tVData = new VData();

		tVData.addElement(tOperateType);
		tVData.add("Y");
		tVData.add(yLMCertifyDesSet);

		CertifyDescUI tCertifyDescUI = new CertifyDescUI();
		tCertifyDescUI.submitData(tVData, "QUERY");
	}
}
