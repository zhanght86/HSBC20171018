package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WuHao
 * @version 1.0
 */
public class PEdorGMDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorGMDetailBLF.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 封装将要提交数据 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 控制信息传输类 */
	private TransferData mTransferData = new TransferData();
	/** 重算后的领取标准 */
	private String mStandMoney;

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPGetSchema mLPGetSchema = new LPGetSchema();
	private LPDutySchema mLPDutySchema = new LPDutySchema();
	private LPPolSchema mLPPolSchema = new LPPolSchema();

	public PEdorGMDetailBLF() {
	}

	// 获得重算后的领取标准金额，可在保全操作完毕后由BLF层加入mResult
	public String getStandMoney() {
		return this.mStandMoney;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据查询业务处理
		if (!cOperate.equals("UPDATE||MAIN")) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorGMDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("pubsubmit ok");
		return true;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			mLPGetSchema = (LPGetSchema) mInputData.getObjectByObjectName(
					"LPGetSchema", 0);

			if (mLPEdorItemSchema == null || mGlobalInput == null
					|| mLPGetSchema == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据 调用业务逻辑处理类PEdorGMDetailBL进行处理
	 */
	private boolean dealData() {
		// UpdateEdorState tUpdateEdorState = new UpdateEdorState();
		// MMap mMap1 = new MMap();
		// mMap1.put(tUpdateEdorState.getUpdateEdorStateSql(mLPEdorItemSchema),
		// "UPDATE");
		// mMap.add(mMap1);
		PEdorGMDetailBL tPEdorGMDetailBL = new PEdorGMDetailBL();
		if (!tPEdorGMDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorGMDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorGMDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		VData tResult = tPEdorGMDetailBL.getResult();
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));
		mResult.add(tResult.getObjectByObjectName("TransferData", 0));
		mResult.add(mMap);

		return true;
	}

	public static void main(String[] args) {
		PEdorGMDetailBLF pedorgmdetailblf = new PEdorGMDetailBLF();
	}
}
