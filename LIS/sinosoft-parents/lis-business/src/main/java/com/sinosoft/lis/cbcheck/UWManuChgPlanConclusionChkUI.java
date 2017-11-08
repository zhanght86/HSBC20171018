package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

public class UWManuChgPlanConclusionChkUI  implements BusinessService{
private static Logger logger = Logger.getLogger(UWManuChgPlanConclusionChkUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public UWManuChgPlanConclusionChkUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		UWManuChgPlanConclusionChkBL tUWManuChgPlanConclusionChkBL = new UWManuChgPlanConclusionChkBL();

		if (!tUWManuChgPlanConclusionChkBL.submitData(cInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWManuChgPlanConclusionChkBL.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuChgPlanConclusionChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();

			return false;

		} else {
			this.mErrors.copyAllErrors(tUWManuChgPlanConclusionChkBL.mErrors);
		}

		return true;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";

		LCContSchema tLCContSchema = new LCContSchema();
		LCContSet tLCContSet = new LCContSet();

		tLCContSchema.setContNo("130110000000139");
		tLCContSchema.setUWFlag("0");
		tLCContSchema.setRemark("I want to change without any reasons");
		tLCContSet.add(tLCContSchema);

		VData tVData = new VData();
		tVData.add(tLCContSet);
		tVData.add(tG);

		UWManuChgPlanConclusionChkUI tUWManuChgPlanConclusionChkUI = new UWManuChgPlanConclusionChkUI();

		if (!tUWManuChgPlanConclusionChkUI.submitData(tVData, "")) {
			int n = tUWManuChgPlanConclusionChkUI.mErrors.getErrorCount();
			String tContent = " 承保计划变更原因录入失败，原因是:\n";

			for (int i = 0; i < n; i++) {
				tContent += (tUWManuChgPlanConclusionChkUI.mErrors.getError(i).errorMessage + "\n");
			}

			logger.debug(tContent);
		}

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
