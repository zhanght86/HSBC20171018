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
import com.sinosoft.lis.schema.LZCardBSchema;
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

public class CertReveTakeBackBL {
private static Logger logger = Logger.getLogger(CertReveTakeBackBL.class);
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

	public CertReveTakeBackBL() {
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

//		if (!prepareOutputData()) {
//			return false;
//		}
//
//		// 数据提交
//		PubSubmit tPubSubmit = new PubSubmit();
//		if (!tPubSubmit.submitData(mResult, "")) {
//			buildError("submitData", "数据提交失败!");
//			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
//			return false;
//		}

		return true;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	private boolean getInputData(VData vData) {
		if (mszOperate.equals("CONFIRM")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardAppSet.set((LZCardAppSet) vData.getObjectByObjectName("LZCardAppSet", 0));
		} else {
			buildError("getInputData", "不支持的操作字符串");
			return false;
		}
		long cardnum=0;
		for (int n = 1; n <= mLZCardAppSet.size(); n++) {
			LZCardAppSchema tLZCardAppSchema =mLZCardAppSet.get(n);
			//20101-01-21增加单证数量的控制
			cardnum=cardnum+ CertifyFunc.bigIntegerDiff(tLZCardAppSchema.getEndNo(), tLZCardAppSchema
					.getStartNo()) + 1;	
		}
		if(cardnum>10000)
		{
			CError.buildErr(this, "一次修订确认的单证数量不能超过10000张，请减少单证数量");
			return false;
		}

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		// 产生回收清算单号
		String strTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun.getNoLimit(globalInput.ComCode));

		for (int nIndex = 0; nIndex < mLZCardAppSet.size(); nIndex++) {
			map = new MMap();
			LZCardAppSchema tLZCardAppSchema = mLZCardAppSet.get(nIndex + 1);

			LZCardAppDB tLZCardAppDB = new LZCardAppDB();
			tLZCardAppDB.setApplyNo(tLZCardAppSchema.getApplyNo());
			if (tLZCardAppDB.getInfo()) {
				tLZCardAppDB.setStateFlag("3");// 3、核销修订批复确认
				tLZCardAppDB.setReplyPerson(tLZCardAppSchema.getReplyPerson());
				tLZCardAppDB.setReplyDate(tLZCardAppSchema.getReplyDate());

				map.put(tLZCardAppDB.getSchema(), "UPDATE");// 更新LZCardApp
			}

			// 构造要核销修订的单证信息
			LZCardSchema tLZCardSchema = new LZCardSchema();
			tLZCardSchema.setCertifyCode(tLZCardAppDB.getCertifyCode());
			tLZCardSchema.setStartNo(tLZCardAppDB.getStartNo());
			tLZCardSchema.setEndNo(tLZCardAppDB.getEndNo());

			// 格式化数据
			LZCardBSet tLZCardBSet = CertifyFunc.formatCardListReveTakeBack(tLZCardSchema);
			if (tLZCardBSet == null) {
				mErrors.copyAllErrors(CertifyFunc.mErrors);
				return false;
			}

			for (int nInnerIndex = 1; nInnerIndex <= tLZCardBSet.size(); nInnerIndex++) {
				LZCardBSchema tLZCardBSchema = tLZCardBSet.get(nInnerIndex);
				tLZCardBSchema.setTakeBackNo(strTakeBackNo);// 设置回收清算单号

				// 单证作废操作
				VData vOneResult = new VData();
				if (!CertifyFunc.splitCertReveTakeBack(globalInput, tLZCardBSchema, vOneResult)) {
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
			
			try {
				mResult.clear();
				mResult.add(map);
			} catch (Exception ex) {
				buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
				return false;
			}
			
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mResult, "")) {
				buildError("submitData", "数据提交失败!");
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				return false;
			}
			
			
		} // end of for(int nIndex = 0; ...
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

	public VData getResult() {
		mResult.clear();
		return mResult;
	}
}
