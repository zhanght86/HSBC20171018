package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class EdorUWNoticeBLF {
private static Logger logger = Logger.getLogger(EdorUWNoticeBLF.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public EdorUWNoticeBLF() {
	}

	public boolean submitData(VData aInputData, String aOperate) {
		mInputData = (VData) aInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 执行服务
		if (!callService()) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean callService() {
		// 查找打印服务
		// =====del====liuxiaosong===2006-11-04============strat=========================
		// String strSQL = "SELECT * FROM LDCode WHERE CodeType='bquwnotice'";
		// =====del====liuxiaosong===2006-11-04============end=========================
		// =====add====liuxiaosong===2006-11-04============start=========================
		// 加入对团体保全核保通知书的支持，此时应该确保 在ldcode中，定义以下两个codetype时不会有
		// 重复的CODE, 否则会出错，最好能在定义时统一！
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String strSQL = "SELECT * FROM LDCode WHERE CodeType in('bquwnotice','bqgrpuwnotice')";
		// =====add====liuxiaosong===2006-11-04============end=========================
		strSQL += " AND Code = '" + "?Code?" + "'";
		// strSQL += " AND OtherSign = '0'";
		sqlbv.sql(strSQL);
		sqlbv.put("Code", mLOPRTManagerSchema.getCode());
		LDCodeSet tLDCodeSet = new LDCodeDB().executeQuery(sqlbv);

		if (tLDCodeSet.size() == 0) {
			mErrors.addOneError("找不到对应的打印服务类(Code = '"
					+ mLOPRTManagerSchema.getCode() + "')");
			return false;
		}

		// 调用打印服务
		LDCodeSchema tLDCodeSchema = tLDCodeSet.get(1);

		try {
			Class cls = Class.forName(tLDCodeSchema.getCodeAlias());
			PrintService bn = (PrintService) cls.newInstance();

			// 准备数据
			String strOperate = "PRINT";

			VData vData = new VData();

			vData.add(mGlobalInput);
			vData.add(mLOPRTManagerSchema);

			if (!bn.submitData(vData, strOperate)) {
				mErrors.copyAllErrors(bn.getErrors());
				return false;
			}

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
