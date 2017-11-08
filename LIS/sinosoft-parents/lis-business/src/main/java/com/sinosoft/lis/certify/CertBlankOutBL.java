package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LZCardAppSchema;
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
 * Description:单证作废
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

public class CertBlankOutBL {
private static Logger logger = Logger.getLogger(CertBlankOutBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/* 私有成员 */
	private String mszOperate = "";

	/* 业务相关的数据 */
	private GlobalInput globalInput = new GlobalInput();

	private LZCardSet mLZCardSet = new LZCardSet();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	private VData mResult = new VData();

	// 记录下当前操作到哪一条记录，如果操作没有成功完成，给用户返回所有未能成功处理的数据。
	private int m_nOperIndex = 0;

	public CertBlankOutBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		mszOperate = cOperate;

		if (!getInputData(cInputData))
			return false;

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			buildError("submitData", "数据提交失败!");
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	private boolean getInputData(VData vData) {
		if (mszOperate.equals("INSERT")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardSet.set((LZCardSet) vData.getObjectByObjectName("LZCardSet", 0));
		} else {
			buildError("getInputData", "不支持的操作字符串");
			return false;
		}
		long cardnum=0;
		for (int nIndex = 1; nIndex <= mLZCardSet.size(); nIndex++) {
			LZCardSchema tLZCardSchema =mLZCardSet.get(nIndex);
			//20101-01-21增加单证数量的控制
			cardnum=cardnum+ CertifyFunc.bigIntegerDiff(tLZCardSchema.getEndNo(), tLZCardSchema
						.getStartNo()) + 1;		
		}

		if(cardnum>10000)
		{
			CError.buildErr(this, "一次单证作废的单证数量不能超过10000张，请减少单证数量");
			return false;
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		if (mszOperate.equals("INSERT")) {
			String strTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun.getNoLimit(globalInput.ComCode));// 产生回收清算单号
			logger.debug("回收清算单号:" + strTakeBackNo);

			for (int nIndex = 0; nIndex < mLZCardSet.size(); nIndex++) {
				LZCardSchema tLZCardSchema = mLZCardSet.get(nIndex + 1);

				// 格式化数据
				LZCardSet tLZCardSet = CertifyFunc.formatCardListLoss(tLZCardSchema);
				if (tLZCardSet == null) {
					mErrors.copyAllErrors(CertifyFunc.mErrors);
					return false;
				}

				for (int nInnerIndex = 1; nInnerIndex <= tLZCardSet.size(); nInnerIndex++) {
					tLZCardSchema = tLZCardSet.get(nInnerIndex);
					tLZCardSchema.setTakeBackNo(strTakeBackNo);// 设置回收清算单号

					// 单证作废操作
					VData vOneResult = new VData();
					if (!CertifyFunc.splitCertifyLoss(globalInput, tLZCardSchema, vOneResult)) {
						mErrors.copyAllErrors(CertifyFunc.mErrors);
						return false;
					} else {
						map.put(vOneResult.get(0), "DELETE");// LZCard
						map.put(vOneResult.get(1), "INSERT");// LZCard
						map.put(vOneResult.get(2), "INSERT");// LZCard
						map.put(vOneResult.get(3), "INSERT");// LZCardB
						map.put(vOneResult.get(4), "INSERT");// LZCardTrackB
					}
				}
			} // end of for(int nIndex = 0; ...
		} // end of if( mszOperate.equals("INSERT") );

		return true;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CertifyBlackOutBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	private void buildFailSet() {
		LZCardSet tLZCardSet = new LZCardSet();
		for (int nIndex = m_nOperIndex; nIndex < mLZCardSet.size(); nIndex++) {
			tLZCardSet.add(mLZCardSet.get(nIndex + 1));
		}
		mResult.add(tLZCardSet);
	}

	public VData getResult() {
		mResult.clear();
		return mResult;
	}

	public static void main(String[] args) {
	}
}
