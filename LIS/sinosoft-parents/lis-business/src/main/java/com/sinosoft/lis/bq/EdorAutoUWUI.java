package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:个人保全自动核保
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class EdorAutoUWUI {
private static Logger logger = Logger.getLogger(EdorAutoUWUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public EdorAutoUWUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		EdorAutoUWBL tEdorAutoUWBL = new EdorAutoUWBL();
		if (tEdorAutoUWBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tEdorAutoUWBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorAutoUWUI";
			tError.functionName = "submitData";
			tError.errorMessage = "提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tEdorAutoUWBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		tLPEdorMainSchema.setEdorAcceptNo("6120050926000110");
		tLPEdorMainSchema.setEdorNo("6020050926000121");
		tLPEdorMainSchema.setContNo("000000139104");

		VData tInputData = new VData();
		tInputData.add(tG);
		tInputData.add(tLPEdorMainSchema);
		EdorAutoUWBL tEdorAutoUWBL = new EdorAutoUWBL();
		if (!tEdorAutoUWBL.submitData(tInputData, "")) {
			logger.debug("核保失败!");
			logger.debug(tEdorAutoUWBL.mErrors.getErrContent());
		}

	}
}
