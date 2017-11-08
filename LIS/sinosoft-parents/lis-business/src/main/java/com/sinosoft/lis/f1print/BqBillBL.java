package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全清单打印
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
 * @author Liurx
 * @version 1.0
 */
public class BqBillBL {
private static Logger logger = Logger.getLogger(BqBillBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mChnlType;//
	private String mBillType;// 清单类型
	private String mStartDate;// 统计起期
	private String mEndDate;// 统计止期
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	public BqBillBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("before BqBillBL getInputData.........");
		if (!getInputData()) {
			return false;
		}
		logger.debug("before BqBillBL checkData.........");
		// 数据校验
		if (!checkData()) {
			return false;
		}
		logger.debug("before BqBillBL callService.........");
		// 执行服务
		if (!callService()) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mBillType = (String) mTransferData.getValueByName("Type");
		if (mBillType == null || mBillType.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:清单类型不能为空！"));
			return false;
		}
//		mChnlType = (String) mTransferData.getValueByName("ChnlType");
//		if (mChnlType == null || mChnlType.trim().equals("")) {
//			mErrors.addOneError(new CError("没有得到足够的信息:codetype不能为空！"));
//			return false;
//		}
//		mStartDate = (String) mTransferData.getValueByName("StartDate");
//		if (mStartDate == null || mStartDate.trim().equals("")) {
//			mErrors.addOneError(new CError("没有得到足够的信息:统计起期不能为空！"));
//			return false;
//		}
//		mEndDate = (String) mTransferData.getValueByName("EndDate");
//		if (mEndDate == null || mEndDate.trim().equals("")) {
//			mErrors.addOneError(new CError("没有得到足够的信息:统计止期不能为空！"));
//			return false;
//		}
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean callService() {
		// 查找打印服务
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String strSQL = "SELECT * FROM LDCode WHERE CodeType = 'bqbill'";
		strSQL += " AND Code = '" + "?mBillType?" + "'";
		sqlbv.sql(strSQL);
		sqlbv.put("mBillType", mBillType);
		logger.debug("in BqBillBL:" + strSQL);
		LDCodeSet tLDCodeSet = new LDCodeDB().executeQuery(sqlbv);

		if (tLDCodeSet.size() == 0) {
			mErrors.addOneError("找不到对应的打印服务类(Code = '" + mBillType + "')");
			return false;
		}

		// 调用打印服务
		LDCodeSchema tLDCodeSchema = tLDCodeSet.get(1);

		try {
			Class cls = Class.forName(tLDCodeSchema.getCodeAlias());
			BqBill bn = (BqBill) cls.newInstance();

			// 准备数据
			String strOperate = "PRINT";

			VData vData = new VData();

			vData.add(mGlobalInput);
			vData.add(mTransferData);
			logger.debug("before call bqbill submitdata.......");
			if (!bn.submitData(vData, strOperate)) {
				mErrors.copyAllErrors(bn.getErrors());
				return false;
			}
			logger.debug("after call bqbill submitdata.");
			mResult = bn.getResult();

		} catch (Exception ex) {
			ex.printStackTrace();
			mErrors.addOneError("callPrintService" + ex.toString());
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
