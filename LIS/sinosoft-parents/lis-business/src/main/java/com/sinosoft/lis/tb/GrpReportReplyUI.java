/*
 * <p>ClassName: GrpReportReplyUI </p>
 * <p>Description: GrpReportReplyUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-01-24 18:15:01
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.vschema.LCRReportPrtSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GrpReportReplyUI {
private static Logger logger = Logger.getLogger(GrpReportReplyUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private LCRReportSchema mLCRReportSchema = new LCRReportSchema();
	private LCRReportPrtSet mLCRReportPrtSet = new LCRReportPrtSet();

	public GrpReportReplyUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		GrpReportReplyBL tGrpReportReplyBL = new GrpReportReplyBL();
		tGrpReportReplyBL.submitData(cInputData, mOperate);
		if (tGrpReportReplyBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpReportReplyBL.mErrors);
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
		LCRReportSchema tLCRReportSchema = new LCRReportSchema();

		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tLCRReportSchema.setGrpPrtSeq("810000000000562");
		tLCRReportSchema.setProposalContNo("130110000013389");
		tLCRReportSchema.setGrpContNo("140110000000549");
		tLCRReportSchema.setReplyContente("88");
		tLCRReportSchema.setPrtSeq("810000000000561");
		tLCRReportSchema.setContNo("130110000013389");
		tLCRReportSchema.setReplyFlag("1");
		tLCRReportSchema.setCustomerNo("0000488540");
		tLCRReportSchema.setName("0000488540");
		tLCRReportSchema.setMakeDate("2005-01-01");
		tLCRReportSchema.setMakeTime("11:32:10");

		VData tVData = new VData();
		tVData.add(tLCRReportSchema);
		tVData.add(tGlobalInput);

		GrpReportReplyUI GrpReportReplyUI = new GrpReportReplyUI();
		GrpReportReplyUI.submitData(tVData, "");

	}

	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mLCRReportSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpReportReplyUI";
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
		this.mLCRReportSchema.setSchema((LCRReportSchema) cInputData
				.getObjectByObjectName("LCRReportSchema", 0));
		return true;
	}

}
