package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
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
 * @author unascribed
 * @version 1.0
 */

public class GrpUWSetSpecialFlagBL {
private static Logger logger = Logger.getLogger(GrpUWSetSpecialFlagBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 团体保单表 */
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCGrpPolSet mAllLCGrpPolSet = new LCGrpPolSet();
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();

	public GrpUWSetSpecialFlagBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		logger.debug("---GrpUWSetSpecialFlagBL BEGIN---" + mOperate);

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		GrpUWSetSpecialFlagBLS tGrpUWSetSpecialFlagBLS = new GrpUWSetSpecialFlagBLS();
		// logger.debug("size0:"+mResult.size());
		logger.debug("---GrpUWSetSpecialFlagBLS BEGIN---");
		if (tGrpUWSetSpecialFlagBLS.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			// this.mErrors.copyAllErrors(tGrpUWSetSpecialFlagBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWSetSpecialFlagBLS";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// mResult.clear();
		// mResult = tGrpUWSetSpecialFlagBLS.getResult();
		// logger.debug("size:"+mResult.size());
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		mLCGrpPolSchema.setSchema((LCGrpPolSchema) cInputData
				.getObjectByObjectName("LCGrpPolSchema", 0));
		if (mLCGrpPolSchema != null) {
			if (mLCGrpPolSchema.getPrtNo() == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "GrpUWSetSpecialFlagBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "没有传入数据!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("---in GrpUWSetSpecialFlagBLS dealData()---");
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();

		tLCGrpPolDB.setPrtNo(mLCGrpPolSchema.getPrtNo());
		tLCGrpPolSet = tLCGrpPolDB.query();
		if (tLCGrpPolSet == null || tLCGrpPolSet.size() == 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWSetSpecialFlagBL";
			tError.functionName = "dealData()";
			tError.errorMessage = "未查询到集体单信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("tLCGrpPolSet.size()" + tLCGrpPolSet.size());
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			if (tLCGrpPolSet.get(i).getSpecFlag() == null
					|| !tLCGrpPolSet.get(i).getSpecFlag().trim().equals("1")) {
				tLCGrpPolSet.get(i).setSpecFlag("1");
			} else if (tLCGrpPolSet.get(i).getSpecFlag().trim().equals("1")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "GrpUWSetSpecialFlagBL";
				tError.functionName = "dealData()";
				tError.errorMessage = "集体投保单已被置为特殊投保单!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}

		mLCGrpPolSet.set(tLCGrpPolSet);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareOutputData() {
		mInputData.add(mLCGrpPolSet);
		return true;

	}

	public VData getResult() {
		return mResult;
	}

}
