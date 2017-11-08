package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author sxy
 * @version 1.0
 */

public class LCUWMasterQueryBL {
private static Logger logger = Logger.getLogger(LCUWMasterQueryBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 保单 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	/** 核保主表 */
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();

	public LCUWMasterQueryBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("begin  LCUWMasterQueryBL");
		// 进行业务处理
		if (cOperate.equals("QUERY||MAIN")) {
			if (!queryData()) {
				return false;
			}
			logger.debug("---LCUWMasterQueryBL-queryData---");
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
		mLCPolSchema.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0));

		if (mLCPolSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalQueryBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请输入查询条件!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 查询符合条件的保单 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean queryData() {
		LCPolDB tLCPolDB = mLCPolSchema.getDB();
		mLCPolSet = tLCPolDB.query();

		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();

		if (tLCPolDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCUWMasterQuery";
			tError.functionName = "queryData";
			tError.errorMessage = "保单查询失败!";
			this.mErrors.addOneError(tError);
			mLCPolSet.clear();
			return false;
		}
		if (mLCPolSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWMasterQuery";
			tError.functionName = "queryData";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLCPolSet.clear();
			return false;
		}

		tLCUWMasterDB.setPolNo(mLCPolSet.get(1).getPolNo());
		tLCUWMasterSet = tLCUWMasterDB.query();
		if (tLCUWMasterDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCUWMasterQuery";
			tError.functionName = "queryData";
			tError.errorMessage = "核保主表查询失败!";
			this.mErrors.addOneError(tError);
			mLCPolSet.clear();
			return false;
		}
		if (tLCUWMasterSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWMasterQuery";
			tError.functionName = "queryData";
			tError.errorMessage = "未找到核保主表相关数据!";
			this.mErrors.addOneError(tError);
			mLCPolSet.clear();
			return false;
		}
		logger.debug("LCUWMasterSet.size()" + tLCUWMasterSet.size());
		mResult.clear();
		mResult.add(tLCUWMasterSet);

		return true;
	}

}
