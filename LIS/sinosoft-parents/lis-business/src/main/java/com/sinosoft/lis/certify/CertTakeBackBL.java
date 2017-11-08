package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证管理回收操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author kevin
 * @version 1.0
 */

public class CertTakeBackBL {
private static Logger logger = Logger.getLogger(CertTakeBackBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/* 私有成员 */
	private String mszOperate = "";

	/* 业务相关的数据 */
	private GlobalInput globalInput = new GlobalInput();

	private LZCardSet mLZCardSet = new LZCardSet();

	private VData mResult = new VData();

	// 记录下当前操作到哪一条记录，如果操作没有成功完成，给用户返回所有未能成功处理的数据。
	private int m_nOperIndex = 0;

	public CertTakeBackBL() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		mszOperate = cOperate;

		if (!getInputData(cInputData))
		{
			buildFailSet();
			return false;
		}

		if (!dealData()) {
			buildFailSet();
			return false;
		}

		return true;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	private boolean getInputData(VData vData) {
		if (mszOperate.equals("INSERT")) {// 手工缴销
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardSet.set((LZCardSet) vData.getObjectByObjectName("LZCardSet", 0));
		} else if (mszOperate.equals("TAKEBACK")) {// 外部调用回收操作
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardSet.set((LZCardSet) vData.getObjectByObjectName("LZCardSet", 0));
		} else {
			buildError("getInputData", "不支持的操作字符串");
			return false;
		}
		long cardnum=0;
		for (int nIndex = 1; nIndex <= mLZCardSet.size(); nIndex++)
		{
			LZCardSchema tLZCardSchema =mLZCardSet.get(nIndex);
			//20101-01-21增加单证数量的控制
			cardnum=cardnum+ CertifyFunc.bigIntegerDiff(tLZCardSchema.getEndNo(), tLZCardSchema
						.getStartNo()) + 1;		
		}
		if(cardnum>10000)
		{
			buildError("getInputData", "一次单证缴销的单证数量不能超过10000张，请减少单证数量");
			return false;
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		// 产生回收清算单号
		String strTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun.getNoLimit(globalInput.ComCode));

		mResult.clear();
		mResult.add(strTakeBackNo);

		m_nOperIndex = 0;
		for (int nIndex = 0; nIndex < mLZCardSet.size(); nIndex++) {
			LZCardSchema tLZCardSchema = mLZCardSet.get(nIndex + 1);

			// 格式化数据，为回收单证单独写的
			LZCardSet tLZCardSet = CertifyFunc.formatCardListTakeBack(tLZCardSchema);
			if (tLZCardSet == null) {
				mErrors.copyAllErrors(CertifyFunc.mErrors);
				return false;
			}

			// 校验起止号之间的单证是否都拆分成单条记录，是否都可以核销
			long count = CertifyFunc.bigIntegerDiff(tLZCardSchema.getEndNo(), tLZCardSchema.getStartNo()) + 1;
			if (tLZCardSet.size() != count) {
				buildError("getInputData", tLZCardSchema.getStartNo() + "-" + tLZCardSchema.getEndNo()
						+ "之间不能核销的单证数量为:" + (count - tLZCardSet.size()));
				return false;
			}

			VData vResult = new VData();
			for (int nInnerIndex = 0; nInnerIndex < tLZCardSet.size(); nInnerIndex++) {
				tLZCardSchema = tLZCardSet.get(nInnerIndex + 1);
				tLZCardSchema.setTakeBackNo(strTakeBackNo);// 设置回收清算单号

				if (mszOperate.equals("INSERT")) {
					tLZCardSchema.setStateFlag("5");
				} else if (mszOperate.equals("TAKEBACK")) {
					tLZCardSchema.setStateFlag("4");
				}

				// 单证回收操作
				VData vOneResult = new VData();
				if (!CertifyFunc.splitCertifyTakeBack(globalInput, tLZCardSchema, vOneResult)) {
					mErrors.copyAllErrors(CertifyFunc.mErrors);
					return false;
				}

				vResult.add(vOneResult);
			}

			CertTakeBackBLS tCertTakeBackBLS = new CertTakeBackBLS();
			if (!tCertTakeBackBLS.submitData(vResult, "INSERT")) {
				mErrors.copyAllErrors(tCertTakeBackBLS.mErrors);
				return false;
			}
			m_nOperIndex++;
		} // end of for

		// 回收接口的实现过程
		// if (mszOperate.equals("TAKEBACK")) {
		// for (int nIndex = 1; nIndex <= mLZCardSet.size(); nIndex++) {
		// LZCardSchema tLZCardSchema = mLZCardSet.get(nIndex);
		//
		// // 格式化数据，为回收单证单独写的
		// LZCardSet tLZCardSet =
		// CertifyFunc.formatCardListTakeBack(tLZCardSchema);
		// if (tLZCardSet == null) {
		// mErrors.copyAllErrors(CertifyFunc.mErrors);
		// return false;
		// }
		//
		// VData vData = new VData();
		// for (int nInnerIndex = 0; nInnerIndex < tLZCardSet.size();
		// nInnerIndex++) {
		// tLZCardSchema = tLZCardSet.get(nInnerIndex + 1);
		//
		// // 单证回收操作
		// VData vOneResult = new VData();
		// if (!CertifyFunc.handleTakeBack(tLZCardSchema, vOneResult)) {
		// mErrors.copyAllErrors(CertifyFunc.mErrors);
		// return false;
		// }
		// vData.add(vOneResult);
		// }
		//
		// CertTakeBackBLS tCertTakeBackBLS = new CertTakeBackBLS();
		// if (!tCertTakeBackBLS.submitData(vData, "TAKEBACK")) {
		// mErrors.copyAllErrors(tCertTakeBackBLS.mErrors);
		// return false;
		// }
		// }// end of for
		// }// end of if

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "CertifyTakeBackUI";
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
}
