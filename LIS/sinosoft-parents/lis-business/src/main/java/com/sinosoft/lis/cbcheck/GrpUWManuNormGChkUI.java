package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: 团单在人工核保时为集体险种下核保结论
 * </p>
 * <p>
 * Description:团单在人工核保时为集体险种下核保结论
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */
public class GrpUWManuNormGChkUI  implements BusinessService{
private static Logger logger = Logger.getLogger(GrpUWManuNormGChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpUWManuNormGChkUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86110000";
		tG.ManageCom = "86110000";
		LCGrpPolSchema p = new LCGrpPolSchema();
		p.setGrpPolNo("120110000001499");
		p.setRemark("测试a");
		p.setUWFlag("1");

		VData tVData = new VData();
		tVData.add(p);
		tVData.add(tG);

		GrpUWManuNormGChkUI ui = new GrpUWManuNormGChkUI();
		if (ui.submitData(tVData, "") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		GrpUWManuNormGChkBL tGrpUWManuNormGChkBL = new GrpUWManuNormGChkBL();

		logger.debug("---UWManuNormChkBL UI BEGIN---");
		if (tGrpUWManuNormGChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpUWManuNormGChkBL.mErrors);
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
