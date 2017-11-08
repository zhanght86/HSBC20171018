package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LZCardAppDB;
import com.sinosoft.lis.db.LZCardBDB;
import com.sinosoft.lis.db.LZCardTrackBDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LZCardAppSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.schema.LZCardTrackSchema;
import com.sinosoft.lis.vschema.LZCardAppSet;
import com.sinosoft.lis.vschema.LZCardBSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.lis.vschema.LZCardTrackBSet;
import com.sinosoft.lis.vschema.LZCardTrackSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
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

public class CertLossConfirmBL {
private static Logger logger = Logger.getLogger(CertLossConfirmBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/* 私有成员 */
	private String mszOperate = "";

	/* 业务相关的数据 */
	private GlobalInput globalInput = new GlobalInput();

	private LZCardAppSet mLZCardAppSet = new LZCardAppSet();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	private VData mResult = new VData();

	public CertLossConfirmBL() {
	}

	public static void main(String[] args) {
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
		if (mszOperate.equals("CONFIRM") || mszOperate.equals("CANCEL")) {
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
			CError.buildErr(this, "一次单证操作的单证数量不能超过10000张，请减少单证数量");
			return false;
		}

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		// 产生回收清算单号
		Reflections tReflections = new Reflections();
		String strNoLimit = "";
		String strTakeBackNo = "";
		strNoLimit = PubFun.getNoLimit(globalInput.ComCode);
		strTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", strNoLimit);

		mResult.clear();
		mResult.add(strTakeBackNo);

		if (mszOperate.equals("CANCEL")) {// 解除挂失
			for (int nIndex = 0; nIndex < mLZCardAppSet.size(); nIndex++) {
				LZCardAppSchema tLZCardAppSchema = mLZCardAppSet.get(nIndex + 1);

				LZCardAppDB tLZCardAppDB = new LZCardAppDB();
				tLZCardAppDB.setApplyNo(tLZCardAppSchema.getApplyNo());
				if (tLZCardAppDB.getInfo()) {
					tLZCardAppDB.setStateFlag("4");// 4、解除挂失
					tLZCardAppDB.setReplyPerson(tLZCardAppSchema.getReplyPerson());
					tLZCardAppDB.setReplyDate(tLZCardAppSchema.getReplyDate());
					tLZCardAppDB.setnote(tLZCardAppSchema.getnote());

					map.put(tLZCardAppDB.getSchema(), "UPDATE");// 更新LZCardApp
				}

				LZCardSet tLZCardSet = new LZCardSet();
				LZCardBDB tLZCardBDB = new LZCardBDB();
				LZCardBSet tLZCardBSet = new LZCardBSet();
				tLZCardBDB.setApplyNo(tLZCardAppSchema.getApplyNo());
				tLZCardBSet = tLZCardBDB.query();
				if (tLZCardBSet.size() >= 1) {
					for (int i = 1; i <= tLZCardBSet.size(); i++) {
						if (tLZCardBSet.get(i).getInvaliDate() != null
								&& PubFun.checkDate(tLZCardBSet.get(i).getInvaliDate(), PubFun
										.getCurrentDate())) {
							tLZCardBSet.get(i).setStateFlag("8");// 8、逾期
						} else {
							tLZCardBSet.get(i).setStateFlag("3");// 3、已发放未核销
						}
						tLZCardBSet.get(i).setTakeBackNo(strTakeBackNo);
						tLZCardBSet.get(i).setModifyDate(PubFun.getCurrentDate());
						tLZCardBSet.get(i).setModifyTime(PubFun.getCurrentTime());

						LZCardSchema tLZCardSchema = new LZCardSchema();
						tReflections.transFields(tLZCardSchema, tLZCardBSet.get(i));

						// 将单证返回给原来代理人
						String tSendOutCom = tLZCardSchema.getSendOutCom();
						tLZCardSchema.setSendOutCom(tLZCardSchema.getReceiveCom());
						tLZCardSchema.setReceiveCom(tSendOutCom);

						tLZCardSet.add(tLZCardSchema);
					}
					map.put(tLZCardBSet, "DELETE");
					map.put(tLZCardSet, "INSERT");// 将LZCardB的遗失数据重新保存到LZCard
				}

				LZCardTrackSet tLZCardTrackSet = new LZCardTrackSet();
				LZCardTrackBDB tLZCardTrackBDB = new LZCardTrackBDB();
				LZCardTrackBSet tLZCardTrackBSet = new LZCardTrackBSet();
				tLZCardTrackBDB.setApplyNo(tLZCardAppSchema.getApplyNo());
				tLZCardTrackBSet = tLZCardTrackBDB.query();
				if (tLZCardTrackBSet.size() >= 1) {
					for (int i = 1; i <= tLZCardTrackBSet.size(); i++) {
						if (tLZCardTrackBSet.get(i).getInvaliDate() != null
								&& PubFun.checkDate(tLZCardTrackBSet.get(i).getInvaliDate(), PubFun
										.getCurrentDate())) {
							tLZCardTrackBSet.get(i).setStateFlag("8");// 8、逾期
						} else {
							tLZCardTrackBSet.get(i).setStateFlag("3");// 3、已发放未核销
						}
						tLZCardTrackBSet.get(i).setTakeBackNo(strTakeBackNo);
						tLZCardTrackBSet.get(i).setMakeDate(PubFun.getCurrentDate());
						tLZCardTrackBSet.get(i).setMakeTime(PubFun.getCurrentTime());
						tLZCardTrackBSet.get(i).setModifyDate(PubFun.getCurrentDate());
						tLZCardTrackBSet.get(i).setModifyTime(PubFun.getCurrentTime());

						LZCardTrackSchema tLZCardTrackSchema = new LZCardTrackSchema();
						tReflections.transFields(tLZCardTrackSchema, tLZCardTrackBSet.get(i));

						// 将单证返回给原来代理人
						String tSendOutCom = tLZCardTrackSchema.getSendOutCom();
						tLZCardTrackSchema.setSendOutCom(tLZCardTrackSchema.getReceiveCom());
						tLZCardTrackSchema.setReceiveCom(tSendOutCom);

						tLZCardTrackSet.add(tLZCardTrackSchema);
					}
					map.put(tLZCardTrackSet, "INSERT");// 保存LZCardTrack
				}
			} // end of for(int nIndex = 0; ...
		} else if (mszOperate.equals("CONFIRM")) {// 遗失确认
			for (int nIndex = 0; nIndex < mLZCardAppSet.size(); nIndex++) {
				LZCardAppSchema tLZCardAppSchema = mLZCardAppSet.get(nIndex + 1);

				LZCardAppDB tLZCardAppDB = new LZCardAppDB();
				tLZCardAppDB.setApplyNo(tLZCardAppSchema.getApplyNo());
				if (tLZCardAppDB.getInfo()) {
					tLZCardAppDB.setStateFlag("3");// 3、遗失确认
					tLZCardAppDB.setReplyPerson(tLZCardAppSchema.getReplyPerson());
					tLZCardAppDB.setReplyDate(tLZCardAppSchema.getReplyDate());
					tLZCardAppDB.setnote(tLZCardAppSchema.getnote());

					map.put(tLZCardAppDB.getSchema(), "UPDATE");// 更新LZCardApp
				}

				LZCardBDB tLZCardBDB = new LZCardBDB();
				LZCardBSet tLZCardBSet = new LZCardBSet();
				tLZCardBDB.setApplyNo(tLZCardAppSchema.getApplyNo());
				tLZCardBSet = tLZCardBDB.query();
				if (tLZCardBSet.size() >= 1) {

					for (int i = 1; i <= tLZCardBSet.size(); i++) {
						tLZCardBSet.get(i).setStateFlag("10");// 10、遗失
						tLZCardBSet.get(i).setTakeBackNo(strTakeBackNo);
						tLZCardBSet.get(i).setModifyDate(PubFun.getCurrentDate());
						tLZCardBSet.get(i).setModifyTime(PubFun.getCurrentTime());
					}
					map.put(tLZCardBSet, "UPDATE");// 更新LZCardB
				}

				LZCardTrackBDB tLZCardTrackBDB = new LZCardTrackBDB();
				LZCardTrackBSet tLZCardTrackBSet = new LZCardTrackBSet();
				tLZCardTrackBDB.setApplyNo(tLZCardAppSchema.getApplyNo());
				tLZCardTrackBSet = tLZCardTrackBDB.query();
				if (tLZCardTrackBSet.size() >= 1) {
					for (int i = 1; i <= tLZCardTrackBSet.size(); i++) {
						tLZCardTrackBSet.get(i).setStateFlag("10");// 10、遗失
						tLZCardTrackBSet.get(i).setTakeBackNo(strTakeBackNo);
						tLZCardTrackBSet.get(i).setMakeDate(PubFun.getCurrentDate());
						tLZCardTrackBSet.get(i).setMakeTime(PubFun.getCurrentTime());
						tLZCardTrackBSet.get(i).setModifyDate(PubFun.getCurrentDate());
						tLZCardTrackBSet.get(i).setModifyTime(PubFun.getCurrentTime());
					}
					map.put(tLZCardTrackBSet, "INSERT");// 保存LZCardTrackB
				}
			} // end of for(int nIndex = 0; ...

		}
		return true;
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
		cError.moduleName = "CertifyBlackOutBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// private void buildFailSet() {
	// LZCardSet tLZCardSet = new LZCardSet();
	// for (int nIndex = m_nOperIndex; nIndex < mLZCardSet.size(); nIndex++) {
	// tLZCardSet.add(mLZCardSet.get(nIndex + 1));
	// }
	// mResult.add(tLZCardSet);
	// }

	public VData getResult() {
		mResult.clear();
		return mResult;
	}
}
