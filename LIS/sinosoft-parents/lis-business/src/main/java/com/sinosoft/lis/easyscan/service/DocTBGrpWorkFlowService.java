package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 团体投保单证上载工作流处理接口实现类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author tuqiang
 * @version 1.0
 */

import com.sinosoft.lis.easyscan.RelationConfig;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

public class DocTBGrpWorkFlowService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBGrpWorkFlowService.class);

	public boolean deal() {
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		GlobalInput tGlobalInput = new GlobalInput();
		/** 全局变量 */
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";
		tGlobalInput.ManageCom = "86";

		/** 得到具体信息* */

		tTransferData.setNameAndValue("PrtNo", mES_DOC_MAINSchema.getDocCode());
		tTransferData.setNameAndValue("InputDate", mES_DOC_MAINSchema
				.getMakeDate());
		tTransferData.setNameAndValue("ScanOperator", mES_DOC_MAINSchema
				.getScanOperator());
		tTransferData.setNameAndValue("ManageCom", mES_DOC_MAINSchema
				.getManageCom());

		RelationConfig nRelationConfig = RelationConfig.getInstance();
		String strSubType = nRelationConfig.getBackRelation(mES_DOC_MAINSchema
				.getSubType());

		tTransferData.setNameAndValue("SubType", strSubType);

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		try {
			ActivityOperator tActivityOpertor = new ActivityOperator();
			if (tActivityOpertor.CreateStartMission("0000000004", "0000002099",
					tVData)) {
				mResult = tActivityOpertor.getResult();
			} else {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DocTBWorkFlowService";
				tError.functionName = "Save";
				tError.errorNo = "-500";
				tError.errorMessage = "扫描录入工作流节点生成失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DocTBWorkFlowService";
			tError.functionName = "Save";
			tError.errorNo = "-500";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}
