package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
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
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class SelAssignPlanUI {
private static Logger logger = Logger.getLogger(SelAssignPlanUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;

	public SelAssignPlanUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;

		SelAssignPlanBL tSelAssignPlanBL = new SelAssignPlanBL();

		logger.debug("--------SelAssignPlanUI Start!---------");
		 if (tSelAssignPlanBL.submitData(cInputData, mOperate) == false||tSelAssignPlanBL.mErrors.getErrorCount()>0)
		 {
			  this.mErrors.clearErrors();
	            boolean flag;

	            for (int i = 0; i < tSelAssignPlanBL.mErrors.getErrorCount(); i++)
	            {
	                flag = true;

	                for (int j = 0; j < this.mErrors.getErrorCount(); j++)
	                {
	                    if (tSelAssignPlanBL.mErrors.getError(i).errorMessage.equals(
	                                this.mErrors.getError(j).errorMessage))
	                    {
	                        flag = false;

	                        break;
	                    }
	                }

	                if (flag)
	                {
	                    this.mErrors.addOneError(tSelAssignPlanBL.mErrors.getError(i));
	                }
	            }

	            return false;
		 }
		//mResult = tSelAssignPlanBL.getResult();
		logger.debug("--------SelAssignPlanUI End!---------");

		// 如果有需要处理的错误，则返回
//		if (tSelAssignPlanBL.mErrors.needDealError()) {
//			this.mErrors.copyAllErrors(tSelAssignPlanBL.mErrors);
//		}
		logger.debug("error num=" + mErrors.getErrorCount());
		mInputData = null;

		return true;
	}

	/**
	 * 返回结果方法
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "002";
		tG.ManageCom = "86";
		tG.ComCode = "01";

//		ldassignplanDBSet tldassignplanDBSet = new ldassignplanDBSet();
//		ldassignplanSchema tldassignplanSchema = new ldassignplanSchema();
//		ldassignplanSet tldassignplanSet = new ldassignplanSet();
//		
//		tldassignplanSchema.setactivityid("0000001090");
//		tldassignplanSchema.setassignno("002");
//		tldassignplanSchema.setservicename("异常件处理");
//		tldassignplanSchema.settaskstarttime("2008-10-07 12:00:00");
//		tldassignplanSchema.settaskendtime("2008-10-07 15:00:00");
//		tldassignplanSchema.setplanamount("110");
//		tldassignplanSet.add(tldassignplanSchema);
//		ldassignplanSchema tldSchema = new ldassignplanSchema();
//		tldSchema.setactivityid("0000001002");
//		tldSchema.setassignno("002");
//		tldSchema.setservicename("问题件处理");
//		tldSchema.settaskstarttime("2008-10-07 12:00:00");
//		tldSchema.settaskendtime("2008-10-07 15:00:00");
//		tldSchema.setplanamount("110");
//		tldassignplanSet.add(tldSchema);
//		VData tVData = new VData();
//        tVData.add(tldassignplanSet);
//		tVData.add(tG);

//		SelAssignPlanUI ui = new SelAssignPlanUI();
//		if (ui.submitData(tVData, "INSERT") == true) {
//			logger.debug("---ok---");
//		} else {
//			logger.debug("---NO---");
//		}
	}

}
