/*
 * <p>ClassName: OLDUWUserUI </p>
 * <p>Description: OLDUWUserUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-01-24 18:15:01
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCRReportPrtSchema;
import com.sinosoft.lis.vschema.LCRReportPrtSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GrpReportUI {
private static Logger logger = Logger.getLogger(GrpReportUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private LCRReportPrtSchema mLCRReportPrtSchema = new LCRReportPrtSchema();
	private LCRReportPrtSet mLCRReportPrtSet = new LCRReportPrtSet();

	public GrpReportUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		GrpReportBL tGrpReportBL = new GrpReportBL();
		tGrpReportBL.submitData(cInputData, mOperate);
		if (tGrpReportBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpReportBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "OLDUWUserUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
		LCRReportPrtSchema tLCRReportPrtSchema = new LCRReportPrtSchema();

		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tLCRReportPrtSchema.setGrpContNo("140110000000510");
		tLCRReportPrtSchema.setAskCode("88");
		tLCRReportPrtSchema.setPrtSeq("810000000000577");
		tLCRReportPrtSchema.setContNo("130110000013693");
		tLCRReportPrtSchema.setCustomerNo("0000492280");
		tLCRReportPrtSchema.setCustomerName("0000492280");
		tLCRReportPrtSet.add(tLCRReportPrtSchema);

		VData tVData = new VData();
		tVData.add(tLCRReportPrtSet);
		tVData.add(tGlobalInput);

		GrpReportUI tGrpReportUI = new GrpReportUI();
		tGrpReportUI.submitData(tVData, "");

	}

	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mLCRReportPrtSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpReportUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean dealData() {
		boolean tReturn = false;
		// 此处增加一些校验代码
		tReturn = true;
		return tReturn;
	}

	private boolean getInputData(VData cInputData) {
		// 全局变量
		this.mLCRReportPrtSchema.setSchema((LCRReportPrtSchema) cInputData
				.getObjectByObjectName("LCRReportPrtSchema", 0));
		return true;
	}

}
