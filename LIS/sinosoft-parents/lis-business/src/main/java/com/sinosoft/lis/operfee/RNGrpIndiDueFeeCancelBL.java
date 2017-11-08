package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayGrpDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.vschema.LJSPayGrpSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团单抽档撤销
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author guanwei
 * @version 1.0
 */

public class RNGrpIndiDueFeeCancelBL {
private static Logger logger = Logger.getLogger(RNGrpIndiDueFeeCancelBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	private GlobalInput tGI = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate = "";
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	/** 数据表 保存数据 */

	// 团体保单表
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	// 应收个人交费表
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	// 应收总表
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	// 团单应收表
	private LJSPayGrpSet mLJSPayGrpSet = new LJSPayGrpSet();
	// 自动续保险种保单的撤销
	// private LCPolSet mLCPolSet = new LCPolSet();
	// 打印管理表
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	// private LOPRTManagerSubSet mLOPRTManagerSubSet = new
	// LOPRTManagerSubSet();
	private MMap mMMap = new MMap();
	private RNHangUp RNHangUp = new RNHangUp(tGI);

	public RNGrpIndiDueFeeCancelBL() {
	}

	public static void main(String[] args) {
		RNGrpIndiDueFeeCancelBL tRNGrpIndiDueFeeCancelBL = new RNGrpIndiDueFeeCancelBL();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema.setGrpContNo("240510000000001");

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "865100";
		tGI.ComCode = "865100";

		VData tv = new VData();
		tv.add(tGI);
		tv.add(tLCGrpContSchema);
		tRNGrpIndiDueFeeCancelBL.submitData(tv, "");
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("After getinputdata");

		// 如果附加险有续保，置标记退出
		if (checkData() == false) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 公共提交
		PubSubmit tPubSubmit = new PubSubmit();

		if (tPubSubmit.submitData(mResult, "") == false) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		logger.debug("go into getInputData");
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

		mLCGrpContSchema = (LCGrpContSchema) mInputData.getObjectByObjectName(
				"LCGrpContSchema", 0);

		if ((tGI == null) || (mLCGrpContSchema == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RNGrpIndiDueFeeCancelBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		return true;
	}

	/**
	 * 检查续保续期
	 * 
	 * @return
	 */
	private boolean checkData() {
		logger.debug("go into checkData");
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());

		if (tLCGrpContDB.getInfo() == false) {
			CError.buildErr(this, "保单查询失败");
			return false;
		}

		mLCGrpContSchema = tLCGrpContDB.getSchema();

		// 保全调用不校验挂起
		if (!RNHangUp.checkHangUP(mLCGrpContSchema.getGrpContNo())) {
			CError.buildErr(this, "保单已挂起,不能撤销!");
			return false;
		}
		// 得到应收总表保单数据
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(mLCGrpContSchema.getGrpContNo());
		tLJSPaySet = tLJSPayDB.query();

		if (tLJSPaySet.size() == 0) {
			CError.buildErr(this, "没有查询到保单续期催收的应收数据");
			return false;
		}

		mLJSPaySchema = tLJSPaySet.get(1);

		if (mLJSPaySchema.getBankOnTheWayFlag() != null) {
			if (mLJSPaySchema.getBankOnTheWayFlag().equals("1")) {
				CError.buildErr(this, "应收数据目前银行在途中,不能撤销!");
				return false;
			}
		}

		// 得到个人应收表保单数据
		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		tLJSPayPersonDB.setGetNoticeNo(mLJSPaySchema.getGetNoticeNo());
		mLJSPayPersonSet = tLJSPayPersonDB.query();

		// 得到集体应收表保单数据
		LJSPayGrpDB tLJSPayGrpDB = new LJSPayGrpDB();
		tLJSPayGrpDB.setGetNoticeNo(mLJSPaySchema.getGetNoticeNo());
		mLJSPayGrpSet = tLJSPayGrpDB.query();

		// 得到打印管理表保单数据
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		String LOPRTManagerSql = "select * from LOPRTManager where otherno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and (code='34' or code='37') and " + "(standbyflag1='"
				+ mLJSPaySchema.getGetNoticeNo() + "' or standbyflag2='"
				+ mLJSPaySchema.getGetNoticeNo() + "') ";
		mLOPRTManagerSet = tLOPRTManagerDB.executeQuery(LOPRTManagerSql);

		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		String TempfeeSql = "select * from LJTempFee where TempfeeNo = '"
				+ mLJSPaySchema.getGetNoticeNo() + "'";
		LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(TempfeeSql);
		logger.debug("查询暂缴费记录=============" + TempfeeSql);
		if (tLJTempFeeSet.size() > 0) {
			if (tLJTempFeeSet.get(1).getConfDate() != null
					&& !tLJTempFeeSet.get(1).getConfDate().equals("null")
					&& !tLJTempFeeSet.get(1).getConfDate().equals("")) {
				CError.buildErr(this, "财务录入暂交费已核销，不能撤销。");
				return false;
			}
			if (tLJTempFeeSet.get(1).getEnterAccDate() == null
					|| tLJTempFeeSet.get(1).getEnterAccDate().equals("")) {
				CError.buildErr(this, "财务录入暂交费未到账");
				return false;
			} else if (tLJTempFeeSet.size() > 0) {
				CError.buildErr(this, "财务已经录入暂交费,不能撤销！");
				return false;
			}
		} else {
			// CError.buildErr(this, "无暂交费记录，不能撤销。");
			// 抽档未暂交费可以撤消
			return true;
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		// 准备Map数据-提交
		mMMap.put(mLJSPaySchema, "DELETE");
		mMMap.put(mLJSPayPersonSet, "DELETE");
		mMMap.put(mLJSPayGrpSet, "DELETE");
		mMMap.put(mLOPRTManagerSet, "DELETE");
		mResult.add(mMMap);

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: MMap
	 */
	public MMap getMap() {
		return mMMap;
	}
}
