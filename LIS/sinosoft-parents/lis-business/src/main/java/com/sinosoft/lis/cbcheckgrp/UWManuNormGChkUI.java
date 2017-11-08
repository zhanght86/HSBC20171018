package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
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
public class UWManuNormGChkUI {
private static Logger logger = Logger.getLogger(UWManuNormGChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public UWManuNormGChkUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86000000";
		tG.ManageCom = "86000000";

		LCPolSchema p = new LCPolSchema();
		LCSpecSchema tLCSpecSchema = new LCSpecSchema();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();

		p.setProposalNo("10110000014500");
		p.setPolNo("10110000014500");
		p.setRemark("测试");
		tLCUWMasterSchema.setUWIdea("测试");
		tLCUWMasterSchema.setPostponeDay("1");
		p.setUWFlag("9");

		LCPolSet tLCPolSet = new LCPolSet();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();

		tLCPolSet.add(p);
		tLCUWMasterSet.add(tLCUWMasterSchema);

		VData tVData = new VData();
		tVData.add(tLCPolSet);
		tVData.add(tLCUWMasterSet);
		tVData.add(tG);

		UWManuNormGChkUI ui = new UWManuNormGChkUI();

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

		UWManuNormGChkBL tUWManuNormGChkBL = new UWManuNormGChkBL();

		logger.debug("---UWManuNormGChkBL UI BEGIN---");

		if (tUWManuNormGChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWManuNormGChkBL.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWAutoChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			// this.mErrors .addOneError(tError) ;
			mResult.clear();

			return false;
		}

		logger.debug("---UWManuNormGChkBL UI End OK---");

		return true;
	}
}
