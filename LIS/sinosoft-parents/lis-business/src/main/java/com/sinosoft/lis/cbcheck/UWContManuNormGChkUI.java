package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人人工核保部分
 * </p>
 * <p>
 * Description:接口功能类
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
public class UWContManuNormGChkUI {
private static Logger logger = Logger.getLogger(UWContManuNormGChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public UWContManuNormGChkUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		UWContManuNormGChkBL tUWContManuNormGChkBL = new UWContManuNormGChkBL();

		logger.debug("---UWContManuNormGChkBL UI BEGIN---");

		if (tUWContManuNormGChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWContManuNormGChkBL.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAutoChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			// this.mErrors .addOneError(tError) ;
			mResult.clear();

			return false;
		}

		logger.debug("---UWContManuNormGChkBL UI End OK---");

		return true;
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86000000";
		tG.ManageCom = "86000000";

		LCContSchema p = new LCContSchema();
		LCSpecSchema tLCSpecSchema = new LCSpecSchema();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();

		p.setContNo("130000000000223");
		p.setRemark("测试");
		tLCCUWMasterSchema.setUWIdea("测试");
		tLCCUWMasterSchema.setPostponeDay("1");
		p.setUWFlag("1");

		LCContSet tLCContSet = new LCContSet();
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();

		tLCContSet.add(p);
		tLCCUWMasterSet.add(tLCCUWMasterSchema);

		VData tVData = new VData();
		tVData.add(tLCContSet);
		tVData.add(tLCCUWMasterSet);
		tVData.add(tG);

		UWContManuNormGChkUI ui = new UWContManuNormGChkUI();

		if (ui.submitData(tVData, "") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}
	}
}
