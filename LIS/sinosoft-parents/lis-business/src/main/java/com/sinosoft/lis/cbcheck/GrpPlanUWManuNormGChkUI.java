package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: 团单在人工核保时为集体产品组合下核保结论
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class GrpPlanUWManuNormGChkUI  implements BusinessService{
private static Logger logger = Logger.getLogger(GrpPlanUWManuNormGChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpPlanUWManuNormGChkUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		GrpPlanUWManuNormGChkBL tGrpPlanUWManuNormGChkBL = new GrpPlanUWManuNormGChkBL();

		logger.debug("---UWManuNormChkBL UI BEGIN---");
		if (tGrpPlanUWManuNormGChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpPlanUWManuNormGChkBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkUI";
			tError.functionName = "submitData";
			// tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}

		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
