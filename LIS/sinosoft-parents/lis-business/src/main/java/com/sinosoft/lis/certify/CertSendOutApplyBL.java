package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LZCardAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LZCardAppSchema;
import com.sinosoft.lis.vschema.LZCardAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证管理:增领申请，增领批复
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

public class CertSendOutApplyBL extends CertifyBO {
private static Logger logger = Logger.getLogger(CertSendOutApplyBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 私有成员 */
	private String mszOperate = "";

	private String mApplyNo = "";

	/** 业务相关的数据 */
	private GlobalInput globalInput = new GlobalInput();

	private String curDate = PubFun.getCurrentDate();

	private String curTime = PubFun.getCurrentTime();

	private LZCardAppSet mLZCardAppSet = new LZCardAppSet();

	private LZCardAppSet updateLZCardAppSet = new LZCardAppSet();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	private VData mResult = new VData();

	public CertSendOutApplyBL() {
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
	private boolean getInputData(VData vData) {
		if (mszOperate.equals("APPLY")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardAppSet.set((LZCardAppSet) vData.getObjectByObjectName("LZCardAppSet", 0));
		} else if (mszOperate.equals("REPLY")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardAppSet.set((LZCardAppSet) vData.getObjectByObjectName("LZCardAppSet", 0));
		} else {
			buildError("getInputData", "不支持的操作字符串");
			return false;
		}

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	private boolean dealData() {
		if (mszOperate.equals("APPLY")) {
			for (int nIndex = 0; nIndex < mLZCardAppSet.size(); nIndex++) {
				LZCardAppSchema tLZCardAppSchema = mLZCardAppSet.get(nIndex + 1);

				// 校验发放者和接受者
				if (!CertifyFunc.verifyComs(globalInput, tLZCardAppSchema.getSendOutCom(), tLZCardAppSchema
						.getReceiveCom())) {
					mErrors.copyAllErrors(CertifyFunc.mErrors);
					return false;
				}

				// 校验代理人最大发放数量
				// if (m_bLimitFlag == true
				// && !CertifyFunc.verifyMaxCount(tLZCardAppSchema)) {
				// mErrors.copyAllErrors(CertifyFunc.mErrors);
				// return false;
				// }

				// 产生申请号码
				mApplyNo = PubFun1.CreateMaxNo("ApplyNo", PubFun.getNoLimit(globalInput.ComCode));
				tLZCardAppSchema.setApplyNo(mApplyNo);
				tLZCardAppSchema.setSubCode("0");
				tLZCardAppSchema.setRiskCode("0");
				tLZCardAppSchema.setRiskVersion("0");
				tLZCardAppSchema.setOperateFlag("1");// 1、增领申请
				tLZCardAppSchema.setApplyCom(globalInput.ComCode);
				tLZCardAppSchema.setOperator(globalInput.Operator);
				tLZCardAppSchema.setStateFlag("1");// 1、申请待批复
				tLZCardAppSchema.setMakeDate(curDate);
				tLZCardAppSchema.setMakeTime(curTime);
				tLZCardAppSchema.setModifyDate(curDate);
				tLZCardAppSchema.setModifyTime(curTime);
			}
			map.put(mLZCardAppSet, "INSERT");
		} else if (mszOperate.equals("REPLY")) {
			for (int nIndex = 0; nIndex < mLZCardAppSet.size(); nIndex++) {
				LZCardAppSchema tLZCardAppSchema = mLZCardAppSet.get(nIndex + 1);
				LZCardAppDB tLZCardAppDB = new LZCardAppDB();
				tLZCardAppDB.setApplyNo(tLZCardAppSchema.getApplyNo());
				if (tLZCardAppDB.getInfo()) {
					tLZCardAppDB.setnote(tLZCardAppSchema.getnote());
					tLZCardAppDB.setReplyPerson(tLZCardAppSchema.getReplyPerson());
					tLZCardAppDB.setReplyDate(tLZCardAppSchema.getReplyDate());
					tLZCardAppDB.setReplyCom(globalInput.ComCode);
					tLZCardAppDB.setStateFlag(tLZCardAppSchema.getStateFlag());
					tLZCardAppDB.setModifyDate(curDate);
					tLZCardAppDB.setModifyTime(curTime);
					updateLZCardAppSet.add(tLZCardAppDB.getSchema());
				}
			}
			map.put(updateLZCardAppSet, "UPDATE");
		} else {
			buildError("dealData", "不支持的操作字符串");
			return false;
		}
		return true;
	}

	public VData getResult() {
		mResult.clear();
		return mResult;
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
}
