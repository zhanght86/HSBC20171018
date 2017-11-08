package com.sinosoft.lis.riskdefine;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Lis_New
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author Sinosoft
 * @version 1.0
 */
public class RiskCalcodeTrial {
private static Logger logger = Logger.getLogger(RiskCalcodeTrial.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// 险种计算要素存储结构
	private TransferData riskfactor = new TransferData();
	private TransferData calresult = new TransferData();
	private String fmcation = "";
	private String Riskcode = "";
	private String CalCode = "";

	public RiskCalcodeTrial() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		this.mInputData = (VData) cInputData.clone();

		if (!getInputData()) {
			return false;
		}
		if ("QUERY||CalModeDetail".equals(fmcation)) {

			CalCode = (String) mInputData.getObjectByObjectName("String", 1);
			if (CalCode == null || CalCode.equals("")) {
				CError tError = new CError();
				tError.moduleName = "RiskCalcodeTrail";
				tError.functionName = "getInputData";
				tError.errorMessage = "请输入算法编码!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (!queryData()) {
				return false;
			}
			mResult.add(calresult);
			// for (int i=0;i<calresult.getValueNames().size();i++)
			// {
			// (String)calresult.getValueByName(calresult.getValueNames().get(i));
			// }
			mResult.add(riskfactor);

		} else {
			riskfactor = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			CalCode = (String) mInputData.getObjectByObjectName("String", 1);

			if (riskfactor == null) {
				CError tError = new CError();
				tError.moduleName = "RiskCalcodeTrail";
				tError.functionName = "getInputData";
				tError.errorMessage = "输入数据有误!";
				this.mErrors.addOneError(tError);
				return false;
			}

			if (!dealData()) {
				return false;
			}
			mResult.add(calresult);
		}
		return true;

	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			fmcation = (String) mInputData.getObjectByObjectName("String", 0);

			// mLJSGetEndorseSchema = (LJSGetEndorseSchema) mInputData.
			// getObjectByObjectName("LJSGetEndorseSchema",
			// 0);

		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "RiskCalcodeTrail";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "RiskCalcodeTrail";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean dealData() {
		Calculator tcal = new Calculator();
		for (int i = 0; i < riskfactor.getValueNames().size(); i++) {
			String factorname = (String) riskfactor.getValueNames().get(i);
			String factorvalue = (String) riskfactor.getValueByName(factorname);
			tcal.addBasicFactor(factorname, factorvalue);
		}
		tcal.setCalCode(CalCode);
		String result = tcal.calculate();
		if (tcal.mErrors.needDealError()) {
			mErrors.copyAllErrors(tcal.mErrors);
			return false;
		} else {
			calresult.setNameAndValue("CalResult", result);
		}
		return true;
	}

	private boolean queryData() {
		LMCalModeDB tLMCalModeDB = new LMCalModeDB();
		tLMCalModeDB.setCalCode(CalCode);
		if (!tLMCalModeDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "RiskCalcodeTrail";
			tError.functionName = "queryData";
			tError.errorMessage = "查询算法表失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		calresult.setNameAndValue("CALSQL", tLMCalModeDB.getCalSQL());
		calresult.setNameAndValue("Remark", tLMCalModeDB.getRemark());
		String tStr = "";
		String tSql = tLMCalModeDB.getCalSQL();
		try {
			while (true) {
				tStr = PubFun.getStr(tSql, 2, "?");
				if (tStr.equals("")) {
					break;
				}
				riskfactor.setNameAndValue(tStr, "");
				String tStr1 = "?" + tStr.trim() + "?";
				// 替换变量
				tSql = StrTool.replaceEx(tSql, tStr1, "123");
				// tSql=tSql.replace(tStr,getValueByName(tStr));
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "interpretFactorInSQL";
			tError.errorMessage = "解释" + tSql + "的变量:" + tStr + "时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

}
