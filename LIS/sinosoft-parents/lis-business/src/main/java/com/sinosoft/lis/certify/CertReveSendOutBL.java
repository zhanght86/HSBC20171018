package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.util.*;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证管理普通单证回收操作
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

public class CertReveSendOutBL {
private static Logger logger = Logger.getLogger(CertReveSendOutBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/* 私有成员 */
	private String mszOperate = "";

	/* 业务相关的数据 */
	private GlobalInput globalInput = new GlobalInput();

	private LZCardSet mLZCardSet = new LZCardSet();

	private VData mResult = new VData();

	private Hashtable mParams = null;

	private int m_nOperIndex = 0;

	public CertReveSendOutBL() {
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

	public VData getResult() {
		return mResult;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		if (mszOperate.equals("INSERT")) {

			// 产生回收清算单号
			String strNoLimit = PubFun.getNoLimit(globalInput.ComCode);
			String strTakeBackNo = PubFun1
					.CreateMaxNo("TAKEBACKNO", strNoLimit);

			mResult.clear();
			mResult.add(strTakeBackNo);

			m_nOperIndex = 0;
			for (int nIndex = 0; nIndex < mLZCardSet.size(); nIndex++) {
				LZCardSchema tLZCardSchema = mLZCardSet.get(nIndex + 1);

				if (!CertifyFunc.verifyComsBack(globalInput, tLZCardSchema
						.getReceiveCom(), tLZCardSchema.getSendOutCom())) {
					mErrors.copyAllErrors(CertifyFunc.mErrors);
					return false;
				}

				LZCardSet tLZCardSet = CertifyFunc
						.formatCardList(tLZCardSchema);
				if (tLZCardSet == null) {
					mErrors.copyAllErrors(CertifyFunc.mErrors);
					return false;
				}

				VData vResult = new VData();
				for (int nInnerIndex = 0; nInnerIndex < tLZCardSet.size(); nInnerIndex++) {
					tLZCardSchema = tLZCardSet.get(nInnerIndex + 1);
					tLZCardSchema.setTakeBackNo(strTakeBackNo);

					// 单证反发放操作
					VData vOneResult = new VData();
					if (!CertifyFunc.splitCertReveSendOut(globalInput,
							tLZCardSchema, vOneResult)) {
						mErrors.copyAllErrors(CertifyFunc.mErrors);
						return false;
					}

					vResult.add(vOneResult);
				}

				CertReveSendOutBLS tCertReveSendOutBLS = new CertReveSendOutBLS();
				if (!tCertReveSendOutBLS.submitData(vResult, "INSERT")) {
					mErrors.copyAllErrors(tCertReveSendOutBLS.mErrors);
					return false;
				}

				m_nOperIndex++;
			} // end of for(int nIndex = 0; ...
		} // end of if( mszOperate.equals("INSERT") );

		return true;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	private boolean getInputData(VData vData) {
		if (mszOperate.equals("INSERT")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName(
					"GlobalInput", 0));
			mLZCardSet.set((LZCardSet) vData.getObjectByObjectName("LZCardSet",
					0));
			mParams = (Hashtable) vData.getObjectByObjectName("Hashtable", 0);

			if (mParams == null || mParams.get("CertifyClass") == null) {
				buildError("getInputData", "缺少必要的参数");
				return false;
			}
			long cardnum=0;
			for (int nIndex = 1; nIndex <= mLZCardSet.size(); nIndex++)
			{
				LZCardSchema tLZCardSchema =mLZCardSet.get(nIndex);
				char cFlag = tLZCardSchema.getSendOutCom().charAt(0);
				if ((cFlag == 'B' || cFlag == 'D' || tLZCardSchema.getSendOutCom().length() == 9))
				{
					//20101-02-20增加单证数量的控制
					cardnum=cardnum+ CertifyFunc.bigIntegerDiff(tLZCardSchema.getEndNo(), tLZCardSchema
								.getStartNo()) + 1;		
				}	
			}
			if(cardnum>10000)
			{
				buildError("getInputData", "一次发放回退的单证数量不能超过10000张，请减少单证数量");
				return false;
			}
		} else {
			buildError("getInputData", "不支持的操作字符串");
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
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "CertReveSendOutBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CertReveSendOutBL";
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
