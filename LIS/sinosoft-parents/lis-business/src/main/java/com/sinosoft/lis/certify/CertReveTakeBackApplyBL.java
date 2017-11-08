package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LZCardAppSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LZCardAppSet;
import com.sinosoft.lis.vschema.LZCardBSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.lis.vschema.LZCardTrackBSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
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
 * @author mw
 * @version 1.0
 */

public class CertReveTakeBackApplyBL extends CertifyBO {
private static Logger logger = Logger.getLogger(CertReveTakeBackApplyBL.class);
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

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	private VData mResult = new VData();

	public CertReveTakeBackApplyBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		mszOperate = cOperate;

		if (!getInputData(cInputData))
			return false;
		
		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}

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
		} else {
			buildError("getInputData", "不支持的操作字符串");
			return false;
		}
		
		long cardnum=0;
		for (int nIndex = 1; nIndex <= mLZCardAppSet.size(); nIndex++)
		{
			LZCardAppSchema tLZCardAppSchema =mLZCardAppSet.get(nIndex);
			//20101-02-01增加单证数量的控制
			cardnum=cardnum+ CertifyFunc.bigIntegerDiff(tLZCardAppSchema.getEndNo(), tLZCardAppSchema
						.getStartNo()) + 1;		
		}
		if(cardnum>10000)
		{
			CError.buildErr(this, "一次修订申请的单证数量不能超过10000张，请减少单证数量");
			return false;
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	private boolean dealData() {
		for (int nIndex = 0; nIndex < mLZCardAppSet.size(); nIndex++) {
			LZCardAppSchema tLZCardAppSchema = mLZCardAppSet.get(nIndex + 1);
			// 校验录入单证是否已经做过核销修订申请待确认
			String sql = "select * from lzcardapp a where a.operateflag = '2' and a.stateflag = '1' "
					+ " and a.certifycode = '" + "?certifycode?" + "' and a.startno <= '"
					+ "?startno?" + "' and a.endno >= '" + "?endno?"
					+ "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("certifycode", tLZCardAppSchema.getCertifyCode());
			sqlbv.put("startno", tLZCardAppSchema.getEndNo());
			sqlbv.put("endno", tLZCardAppSchema.getStartNo());
			LZCardAppDB tLZCardAppDB = new LZCardAppDB();
			LZCardAppSet tLZCardAppSet = tLZCardAppDB.executeQuery(sqlbv);
			if (tLZCardAppSet != null && tLZCardAppSet.size() > 0) {
				buildError("formatCardSet", "单证" + tLZCardAppSchema.getCertifyCode() + "已做过核销修订申请，请检查录入信息！");
				return false;
			}

			// 产生申请号码,记录申请轨迹LZCardApp
			mApplyNo = PubFun1.CreateMaxNo("ApplyNo", PubFun.getNoLimit(globalInput.ComCode));
			tLZCardAppSchema.setApplyNo(mApplyNo);
			tLZCardAppSchema.setSubCode("0");
			tLZCardAppSchema.setRiskCode("0");
			tLZCardAppSchema.setRiskVersion("0");
			tLZCardAppSchema.setOperateFlag("2");// 2、核销修订申请
			tLZCardAppSchema.setApplyCom(globalInput.ComCode);
			tLZCardAppSchema.setOperator(globalInput.Operator);
			tLZCardAppSchema.setMakeDate(curDate);
			tLZCardAppSchema.setMakeTime(curTime);
			tLZCardAppSchema.setModifyDate(curDate);
			tLZCardAppSchema.setModifyTime(curTime);
			tLZCardAppSchema.setStateFlag("1");// 1、申请待批复
			tLZCardAppSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(tLZCardAppSchema.getEndNo(),
					tLZCardAppSchema.getStartNo()) + 1);

			LZCardSchema tLZCardSchema = new LZCardSchema();
			tLZCardSchema.setCertifyCode(tLZCardAppSchema.getCertifyCode());
			tLZCardSchema.setStartNo(tLZCardAppSchema.getStartNo());
			tLZCardSchema.setEndNo(tLZCardAppSchema.getEndNo());
			tLZCardSchema.setHandler(tLZCardAppSchema.getHandler());
			tLZCardSchema.setHandleDate(tLZCardAppSchema.getHandleDate());

			// 校验核销修订申请的单证状态是否合法
			LZCardBSet tLZCardBSet = CertifyFunc.formatCardListReveTakeBack(tLZCardSchema);
			if (tLZCardBSet == null) {
				mErrors.addOneError(CertifyFunc.mErrors.getFirstError());
				return false;
			}

			// else {
			// for (int i = 1; i <= tLZCardBSet.size(); i++) {
			// tLZCardBSet.get(i).setApplyNo(mApplyNo);
			// }
			// }
			// map.put(tLZCardBSet, "UPDATE");
			//
			// // 校验接收者(即单证所属代理人、8位机构、部门)是否属于登录机构
			// for (int i = 1; i <= tLZCardBSet.size(); i++) {
			// if (!CertifyFunc.verifyComsTackBack(globalInput,
			// tLZCardBSet.get(i).getReceiveCom())) {
			// mErrors.copyAllErrors(CertifyFunc.mErrors);
			// return false;
			// }
			// }
			//
			// String strSQL = "SELECT * FROM LZCardTrackB WHERE CertifyCode =
			// '"
			// + tLZCardAppSchema.getCertifyCode() + "' AND StartNo <= '" +
			// tLZCardAppSchema.getEndNo()
			// + "' AND StartNo >= '" + tLZCardAppSchema.getStartNo()
			// + "' AND StateFlag in ('4','5') ORDER BY StartNo";
			// LZCardTrackBDB tLZCardTrackBDB = new LZCardTrackBDB();
			// LZCardTrackBSet tLZCardTrackBSet =
			// tLZCardTrackBDB.executeQuery(strSQL);
			// if (tLZCardTrackBDB.mErrors.needDealError()) {
			// mErrors.copyAllErrors(tLZCardTrackBDB.mErrors);
			// return false;
			// }
			// if (tLZCardTrackBSet == null || tLZCardTrackBSet.size() <= 0) {
			// buildError("formatCardSet", "未查询到可以核销修订的单证，请检查录入信息！");
			// return false;
			// }
			// for (int i = 1; i <= tLZCardTrackBSet.size(); i++) {
			// tLZCardTrackBSet.get(i).setHandler(tLZCardAppSchema.getHandler());
			// tLZCardTrackBSet.get(i).setHandleDate(tLZCardAppSchema.getHandleDate());
			// tLZCardTrackBSet.get(i).setApplyNo(mApplyNo);
			// }
			// map.put(tLZCardTrackBSet, "UPDATE");
		}
		map.put(mLZCardAppSet, "INSERT");

		return true;
	}

	private boolean verifyStateFlag(LZCardAppSchema tLZCardAppSchema) {
		LZCardSchema tLZCardSchema = new LZCardSchema();
		tLZCardSchema.setCertifyCode(tLZCardAppSchema.getCertifyCode());
		tLZCardSchema.setStartNo(tLZCardAppSchema.getStartNo());
		tLZCardSchema.setEndNo(tLZCardAppSchema.getEndNo());

		LZCardSet tLZCardSet = CertifyFunc.formatCardList(tLZCardSchema);
		if (tLZCardSet == null || tLZCardSet.size() == 0) {
			return false;
		} else {
			tLZCardAppSchema.setSendOutCom(tLZCardSet.get(1).getSendOutCom());
			tLZCardAppSchema.setReceiveCom(tLZCardSet.get(1).getReceiveCom());
		}

		return true;
	}
	
	
	/**
	 * 校验操作员只能操作本级及下级单证
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		
		for (int nIndex = 0; nIndex < mLZCardAppSet.size(); nIndex++) {
			
			LZCardAppSchema tLZCardAppSchema = mLZCardAppSet.get(nIndex + 1);
			
			// 校验录入单证是否已经做过核销修订申请待确认
			String sql = "select * from lzcardb a   "
					+ " where a.certifycode = '" + "?certifycode?" + "' and a.startno <= '"
					+ "?startno?" + "' and a.endno >= '" + "?endno?"
					+ "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("certifycode", tLZCardAppSchema.getCertifyCode());
			sqlbv1.put("startno", tLZCardAppSchema.getEndNo());
			sqlbv1.put("endno", tLZCardAppSchema.getStartNo());
			LZCardBSet tLZCardBBet = new LZCardBSet();
			LZCardBDB tLZCardBDB = new LZCardBDB();
			tLZCardBBet = tLZCardBDB.executeQuery(sqlbv1);
//			if (!CertifyFunc.verifyComsTackBack2(globalInput,
//					tLZCardBBet.get(nIndex+1).getReceiveCom())) {
			if(tLZCardBBet.size()>0)
			{
				if (!CertifyFunc.verifyComsTackBack2(globalInput,
						tLZCardBBet.get(1).getReceiveCom())) {
					
					buildError("verifyComsTackBack2", "该单证所属机构与登录机构不符，您不具有操作该单证的权限！");
					return false;
				}
			}
			// 单证状态校验,与单证修订确认校验保持一致
			String szSql = "SELECT * FROM LZCardB WHERE CertifyCode = '" + "?CertifyCode?"
					+ "' AND StateFlag in ('4','5','6','7') AND StartNo <= '" + "?StartNo?" + "' AND EndNo >= '"
					+ "?EndNo?" + "'";
			logger.debug(" 单证状态校验的SQL:" + szSql);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(szSql);
			sqlbv2.put("CertifyCode", tLZCardAppSchema.getCertifyCode());
			sqlbv2.put("StartNo", tLZCardAppSchema.getStartNo());
			sqlbv2.put("EndNo", tLZCardAppSchema.getEndNo());
			LZCardBDB dbLZCardB = new LZCardBDB();
			LZCardBSet set = dbLZCardB.executeQuery(sqlbv2);
			if (set.size() != 1) { // 输入的单证号在可用的单证号内
				buildError("CertReveTakeBackApplyBL", "校验单证状态失败，输入的单证号不在可用单证号的范围内!");
				return false;
			}

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
		cError.moduleName = "CertLossRegisterBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
