package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 责任查询业务逻辑处理类
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
public class DutyQueryBL {
private static Logger logger = Logger.getLogger(DutyQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 责任 */
	private LCDutySchema mLCDutySchema = new LCDutySchema();
	private LCDutySet mLCDutySet = new LCDutySet();
	/** 保费项 */
	private LCPremSet mLCPremSet = new LCPremSet();
	/** 领取项 */
	private LCGetSet mLCGetSet = new LCGetSet();

	public DutyQueryBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("---getInputData---");

		// 进行业务处理
		if (cOperate.equals("QUERY||MAIN")) {
			if (!queryData())
				return false;
			logger.debug("---queryData---");
		}
		if (cOperate.equals("QUERY||DETAIL")) {
			if (!queryDetail())
				return false;
			logger.debug("---queryDetail---");
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 保单查询条件
		mLCDutySchema.setSchema((LCDutySchema) cInputData
				.getObjectByObjectName("LCDutySchema", 0));

		return true;
	}

	/**
	 * 查询符合条件的保单 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean queryData() {
		// 责任表
		LCDutyDB tLCDutyDB = mLCDutySchema.getDB();
		mLCDutySet = tLCDutyDB.query();
		if (tLCDutyDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "DutyQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保单查询失败!";
			this.mErrors.addOneError(tError);
			mLCDutySet.clear();
			return false;
		}
		if (mLCDutySet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DutyQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLCDutySet.clear();
			return false;
		}

		// 保费项表
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(mLCDutySchema.getPolNo());
		mLCPremSet = tLCPremDB.query();
		if (tLCPremDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "DutyQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保单查询失败!";
			this.mErrors.addOneError(tError);
			mLCPremSet.clear();
			return false;
		}
		if (mLCPremSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DutyQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLCPremSet.clear();
			return false;
		}

		// 领取项表
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(mLCDutySchema.getPolNo());
		mLCGetSet = tLCGetDB.query();
		if (tLCGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGetDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "DutyQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保单查询失败!";
			this.mErrors.addOneError(tError);
			mLCGetSet.clear();
			return false;
		}
		if (mLCGetSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DutyQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLCGetSet.clear();
			return false;
		}

		mResult.clear();
		mResult.add(mLCDutySet);
		mResult.add(mLCPremSet);
		mResult.add(mLCGetSet);

		return true;
	}

	/**
	 * 查询保单明细信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryDetail() {
		return true;
	}

}
