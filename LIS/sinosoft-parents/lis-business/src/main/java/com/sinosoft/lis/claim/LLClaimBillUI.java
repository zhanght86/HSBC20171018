package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * Title: 理赔清单打印：个险理赔案件统计清单
 * 
 * Description:
 * （一）统计条件：机构、起始日期、结束日期、统计类型。均为必选项，统计类型分为已结案件和未结案件。（不予立案和撤件案件不在该统计范围内）
 * （二）清单显示内容： 1.表头：标题、统计机构、统计区间、统计人、统计日期
 * 2.表格内容：序号、立案号、保单号、险种代码、险种名称、事故者姓名、立案日期、签批同意日期、赔付金额、拒付金额、保单生效日期、出险日期、
 * 出险类型、案件类型、案件结论类型、保项结论类型、案件状态、扫描件标志。
 * 3.统计精确至保项层。一个立案号对应一个序号，同一立案号下涉及多个保项的赔付时，每个保项对应一条显示记录，但归属于同一序号下。
 * 4.案件状态包括："签批同意"、"完成"。统计时点以相应的案件状态为准。 5.案件结论类型包括："给付"和"拒付"，精确至保项层。
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company:sinosoft
 * 
 * @author mw,2009-04-14
 */

public class LLClaimBillUI {
private static Logger logger = Logger.getLogger(LLClaimBillUI.class);

	public CErrors mErrors = new CErrors();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	// private String mOperate; /** 数据操作字符串 */
	public LLClaimBillUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLClaimBillBL tLLClaimBillBL = new LLClaimBillBL();

		if (tLLClaimBillBL.submitData(cInputData, cOperate) == false) {
			this.mErrors.copyAllErrors(tLLClaimBillBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimBillBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印清单失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			mResult = tLLClaimBillBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
