package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class UWBatchManuNormGChkUI {
private static Logger logger = Logger.getLogger(UWBatchManuNormGChkUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public UWBatchManuNormGChkUI() {
	}

	public static void main(String[] args) {

		logger.debug("Begin ");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86000000";
		tG.ManageCom = "86000000";

		LCGrpContSchema p = new LCGrpContSchema();

		p.setGrpContNo("140110000000007");
		p.setRemark("测试");
		p.setUWFlag("9");

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();

		tLCGrpContSet.add(p);

		VData tVData = new VData();
		tVData.add(tLCGrpContSet);
		tVData.add(tG);

		UWBatchManuNormGChkUI ui = new UWBatchManuNormGChkUI();

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

		UWBatchManuNormGChkBL tUWBatchManuNormGChkBL = new UWBatchManuNormGChkBL();

		logger.debug("---UWBatchManuNormGChkBL UI BEGIN---");

		if (tUWBatchManuNormGChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWBatchManuNormGChkBL.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAutoChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			// this.mErrors .addOneError(tError) ;
			mResult.clear();

			return false;
		}

		logger.debug("---UWBatchManuNormGChkBL UI End OK---");

		return true;
	}

}
