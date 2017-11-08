package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LJTempFeeBL;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 */
public class GrpDifSetBL {
private static Logger logger = Logger.getLogger(GrpDifSetBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	MMap timeMap = new MMap();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperator;
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LJTempFeeSchema mLJTempFeeSchema = new LJTempFeeSchema();
	private double mDif;
	private String mPayDate;
	private String mEnterAccDate;
	public MMap mp = new MMap();

	public GrpDifSetBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("团体开始啦！！！！！！！！！");
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}


		// 进行业务处理
		if (!dealData()) {
			return false;
		}


		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 提交数据
		//zy 2009-05-27 如果是暂收的预收调用则不进行提交
		if(!("YSTemp".equals(cOperate)))
		{
			PubSubmit tSubmit = new PubSubmit();
				if (!tSubmit.submitData(mResult, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					CError.buildErr(this, "数据提交失败!");
					return false;
				}
		}

		return true;
	}

	private boolean checkData() {

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLJTempFeeSchema.getOtherNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "未找到保单相关信息!");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		LJTempFeeBL tLJTempFeeBL = new LJTempFeeBL();
		tLJTempFeeBL = (LJTempFeeBL) cInputData.getObjectByObjectName("LJTempFeeBL", 0);
		mLJTempFeeSchema.setSchema(tLJTempFeeBL.getSchema());
		if (tLJTempFeeBL == null || mLJTempFeeSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			logger.debug("mLCGrpContSchema == null");
			CError.buildErr(this, "前台传输业务数据LCGrpContSchema失败!");
			return false;
		}

		return true;
	}

	private boolean dealData() {
		mDif = mLJTempFeeSchema.getPayMoney();
		mEnterAccDate = mLJTempFeeSchema.getEnterAccDate();
		mPayDate = mLJTempFeeSchema.getPayDate();

		logger.debug("余额===========" + mDif);
		if (mEnterAccDate != null) {
			mLCGrpContSchema.setDif(mLCGrpContSchema.getDif() + mDif);
		}
		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	private boolean prepareOutputData() {
		mResult.clear();
//		mResult.add(mTransferData);
		mp = new MMap();
		mp.put(mLCGrpContSchema, "UPDATE");
		mResult.add(mp);
		return true;
	}

}
