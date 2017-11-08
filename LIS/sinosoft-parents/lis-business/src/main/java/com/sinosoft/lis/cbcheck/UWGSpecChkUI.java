package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保团体人工核保条件承保录入部分
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
 * @author WHN
 * @version 1.0
 */
public class UWGSpecChkUI {
private static Logger logger = Logger.getLogger(UWGSpecChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public UWGSpecChkUI() {
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
		LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();

		tLCSpecSchema.setPolNo("86110020030120000203");
		tLCSpecSchema.setSpecContent("NNN");

		tLCGUWMasterSchema.setGrpPolNo("86110020030110003977");
		tLCGUWMasterSchema.setPassFlag("2");
		tLCGUWMasterSchema.setSpecReason("MMM");

		LCPolSet tLCPolSet = new LCPolSet();
		LCSpecSet tLCSpecSet = new LCSpecSet();
		LCPremSet tLCPremSet = new LCPremSet();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();

		tLCPolSet.add(p);
		tLCSpecSet.add(tLCSpecSchema);

		tLCPremSchema = new LCPremSchema();
		tLCPremSchema.setDutyCode("606001");
		tLCPremSchema.setPayPlanType("1");
		tLCPremSchema.setPayStartDate("2002-12-18");
		tLCPremSchema.setPayEndDate("2005-12-18");
		tLCPremSchema.setPrem(111);

		tLCPremSet.add(tLCPremSchema);
		tLCUWMasterSet.add(tLCUWMasterSchema);
		tLCGUWMasterSet.add(tLCGUWMasterSchema);

		VData tVData = new VData();
		tVData.add(tLCPolSet);
		tVData.add(tLCSpecSet);
		tVData.add(tLCPremSet);
		tVData.add(tLCUWMasterSet);
		tVData.add(tLCGUWMasterSet);
		tVData.add(tG);

		UWGSpecChkUI ui = new UWGSpecChkUI();
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

		UWGSpecChkBL tUWGSpecChkBL = new UWGSpecChkBL();

		logger.debug("---UWGSpecBL UI BEGIN---");
		if (tUWGSpecChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWGSpecChkBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkUI";
			tError.functionName = "submitData";
			// tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}

		return true;
	}

}
