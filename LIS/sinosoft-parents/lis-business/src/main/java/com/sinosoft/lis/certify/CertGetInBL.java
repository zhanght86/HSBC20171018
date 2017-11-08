package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LZCardPrintSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LZCardPrintSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证管理单证发放操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 周平
 * @version 1.0
 */

public class CertGetInBL extends CertifyBO {
private static Logger logger = Logger.getLogger(CertGetInBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/* 私有成员 */
	private String mszOperate = "";

	private String mszTakeBackNo = "";

	/* 业务相关的数据 */
	private GlobalInput globalInput = new GlobalInput();

	private LZCardSet mLZCardSet = new LZCardSet();

	private LZCardPrintSet mLZCardPrintSet = new LZCardPrintSet();

	private boolean m_bLimitFlag = false;

	private VData mResult = new VData();

	// 记录下当前操作到哪一条记录，如果操作没有成功完成，给用户返回所有未能成功处理的数据。
	private int m_nOperIndex = 0;

	public CertGetInBL() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		mszOperate = cOperate;

		if (!getInputData(cInputData))
			return false;

		if (!dealData()) {
			buildFailSet();
			return false;
		}

		return true;
	}

	// 从输入数据中得到所有对象
	private boolean getInputData(VData vData) {
		if (mszOperate.equals("INSERT")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardSet.set((LZCardSet) vData.getObjectByObjectName("LZCardSet", 0));
			mLZCardPrintSet.set((LZCardPrintSet) vData.getObjectByObjectName("LZCardPrintSet", 0));
		} else if (mszOperate.equals("CANCEL")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardSet.set((LZCardSet) vData.getObjectByObjectName("LZCardSet", 0));
		} else {
			buildError("getInputData", "不支持的操作字符串!");
			return false;
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	private boolean dealData() {
		// 产生回收清算单号
		mszTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun.getNoLimit(globalInput.ComCode));
		mResult.clear();
		mResult.add(mszTakeBackNo);

		m_nOperIndex = 0;
		for (int nIndex = 0; nIndex < mLZCardSet.size(); nIndex++) {
			LZCardSchema tLZCardSchema = mLZCardSet.get(nIndex + 1);
			tLZCardSchema.setTakeBackNo(mszTakeBackNo);

			LZCardPrintSchema tLZCardPrintSchema = mLZCardPrintSet.get(nIndex + 1);// 单证印刷列表

			// 处理数据
			VData vOneResult = new VData();
			if (mszOperate.equals("INSERT")) {// 入库
				if (!dealOne(tLZCardSchema, tLZCardPrintSchema, vOneResult)) {
					return false;
				}
			} else if (mszOperate.equals("CANCEL")) {// 拒绝入库
				if (!dealOneCanel(tLZCardSchema, vOneResult)) {
					return false;
				}
			}

			VData vResult = new VData();
			vResult.add(vOneResult);

			// 保存数据
			CertSendOutBLS tCertSendOutBLS = new CertSendOutBLS();
			if (!tCertSendOutBLS.submitData(vResult, "INSERT")) {
				if (tCertSendOutBLS.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCertSendOutBLS.mErrors);
					return false;
				} else {
					buildError("submitData", "CertSendOutBL出错，但是没有提供详细的信息");
					return false;
				}
			}

			m_nOperIndex++; // 记录下当前操作到哪一条记录
		}
		return true;
	}

	/**
	 * 处理一条单证入库的信息
	 * 
	 * @param aLZCardSchema
	 * @return
	 */
	private boolean dealOne(LZCardSchema aLZCardSchema, LZCardPrintSchema aLZCardPrintSchema, VData vResult) {
		// Verify SendOutCom and ReceiveCom
		if (!CertifyFunc.verifyComsIn(globalInput, aLZCardSchema.getSendOutCom(), aLZCardSchema
				.getReceiveCom())) {
			mErrors.copyAllErrors(CertifyFunc.mErrors);
			return false;
		}

		if (aLZCardSchema.getSendOutCom().equals(CertifyFunc.INPUT_COM)) { // 如果是印刷后入库操作
			if (!CertifyFunc.inputCertify(globalInput, aLZCardSchema, aLZCardPrintSchema,
					CertifyFunc.CERTIFY_CLASS_CERTIFY, vResult)) {
				mErrors.copyAllErrors(CertifyFunc.mErrors);
				return false;
			}
		} else { // 如果是发放后,下级机构入库操作，则进行单证拆分操作
			if (!CertifyFunc.splitCertifyGetIn(globalInput, aLZCardSchema, m_bLimitFlag, vResult)) {
				mErrors.copyAllErrors(CertifyFunc.mErrors);
				return false;
			}
		}

		return true;
	}

	/**
	 * 处理一条单证拒绝入库的信息
	 * 
	 * @param aLZCardSchema
	 * @return
	 */
	private boolean dealOneCanel(LZCardSchema aLZCardSchema, VData vResult) {
		// Verify SendOutCom and ReceiveCom
		if (!CertifyFunc.verifyComsIn(globalInput, aLZCardSchema.getSendOutCom(), aLZCardSchema
				.getReceiveCom())) {
			mErrors.copyAllErrors(CertifyFunc.mErrors);
			return false;
		}

		if (!CertifyFunc.splitCertifyGetInCanel(globalInput, aLZCardSchema, m_bLimitFlag, vResult)) {
			mErrors.copyAllErrors(CertifyFunc.mErrors);
			return false;
		}

		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData(VData vData) {
		try {
			if (mszOperate.equals("INSERT")) {
				vData.clear();
				vData.addElement(globalInput);
				vData.addElement(mLZCardSet);
				vData.addElement(mLZCardPrintSet);
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "CertSendOutBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CertSendOutBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;

		this.mErrors.addOneError(cError);
	}

	private void buildFailSet() {
		LZCardSet tLZCardSet = new LZCardSet();
		for (int nIndex = m_nOperIndex; nIndex < mLZCardSet.size(); nIndex++) {
			tLZCardSet.add(mLZCardSet.get(nIndex + 1));
		}
		mResult.add(tLZCardSet);
	}

	public VData getResult() {
		return mResult;
	}
}
