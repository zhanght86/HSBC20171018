package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Hezy Lys
 * @version 1.0
 * @date:2003-05-28
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class FinDayCheckUI implements BusinessService{
private static Logger logger = Logger.getLogger(FinDayCheckUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mDay[] = null;// 接收开始日期和结束日期
	private String mOpt = "";// 接收标志 ：赞收OR预收

	public FinDayCheckUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINTGET") && !cOperate.equals("PRINTPAY")&& !cOperate.equals("YingFu")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}
			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			// 进行业务处理
			if (!dealData()) {
				return false;
			}
			// 准备传往后台的数据
			VData vData = new VData();
			if (!prepareOutputData(vData)) {
				return false;
			}
			FinDayCheckBL tFinDayCheckBL = new FinDayCheckBL();
			logger.debug("Start FinDayCheckBL Submit ...");

			if (!tFinDayCheckBL.submitData(vData, cOperate)) {
				if (tFinDayCheckBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tFinDayCheckBL.mErrors);
					return false;
				} else {
					buildError("submitData", "FinDayCheckBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tFinDayCheckBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "tFinDayCheckUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mDay);
			vData.add(mOpt);
			vData.add(mGlobalInput);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mDay = (String[]) cInputData.get(0);
		mOpt = (String) cInputData.get(1);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		logger.debug("lys  2003-05-28");
		logger.debug("收费标志是" + mOpt);
		logger.debug(mGlobalInput.Operator);
		logger.debug(mGlobalInput.ManageCom);
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "FinDayCheckUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		FinDayCheckUI tFinDayCheckUI = new FinDayCheckUI();
		String ttDay[] = new String[2];
		ttDay[0] = "2003-05-14";
		ttDay[1] = "2003-05-14";
		String tOpt = "Get";
		// String tOpt = "YSGet";
		VData tVData = new VData();
		tVData.addElement(ttDay);
		tVData.addElement(tOpt);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		tVData.addElement(tG);
		tFinDayCheckUI.submitData(tVData, "PRINTGET");
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
