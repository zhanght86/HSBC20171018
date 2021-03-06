package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPGCUWMasterSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:团体保全人工核保对团险批改项目下核保结论
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZhangRong
 * @version 1.0
 */
public class PGrpEdorTypeManuUWUI {
private static Logger logger = Logger.getLogger(PGrpEdorTypeManuUWUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PGrpEdorTypeManuUWUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PGrpEdorTypeManuUWBL tPGrpEdorTypeManuUWBL = new PGrpEdorTypeManuUWBL();
		if (tPGrpEdorTypeManuUWBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPGrpEdorTypeManuUWBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PGrpEdorTypeManuUWUI";
			tError.functionName = "submitData";
			tError.errorMessage = "提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tPGrpEdorTypeManuUWBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PGrpEdorTypeManuUWUI aPGrpEdorTypeManuUWUI = new PGrpEdorTypeManuUWUI();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LPGCUWMasterSchema tLPGCUWMasterSchema = new LPGCUWMasterSchema();

		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPGrpEdorItemSchema.setEdorNo("410110000000015");
		tLPGrpEdorItemSchema.setEdorType("AC");
		tLPGrpEdorItemSchema.setUWFlag("9");

		tLPGCUWMasterSchema.setUWIdea("temp test");
		tLPGCUWMasterSchema.setPassFlag("9");

		tInputData.addElement(tLPGrpEdorItemSchema);
		tInputData.addElement(tLPGCUWMasterSchema);
		tInputData.addElement(tGlobalInput);

		aPGrpEdorTypeManuUWUI.submitData(tInputData, "");
		logger.debug(aPGrpEdorTypeManuUWUI.mErrors.getErrContent());
	}
}
