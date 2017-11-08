package com.sinosoft.lis.fininterface.checkdata;
import org.apache.log4j.Logger;

import com.sinosoft.lis.fininterface.LogInfoDeal;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class FIRuleEngineService1  implements BusinessService
{
private static Logger logger = Logger.getLogger(FIRuleEngineService1.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();	
	
	/** 起始日期 * */
	private String StartDate = null;

	/** 结束日期 * */
	private String EndDate = null;
	
	/** 查询起始日期 * */
	private String StartDay = null;

	/** 查询结束日期 * */
	private String EndDay = null;
	
	/** 查询起始日期 * */
	private String VersionNo = null;

	/** 查询结束日期 * */
	private String callpoint = null;
	
	/**全局变量**/
	GlobalInput tG = new GlobalInput();
	
	
	/***************************************************************************
	 * 获取相关校验需要的参数
	 * @param cInputData
	 * @return
	 */
	private boolean getInputData(VData cInputData) {
   
		try {
			StartDate = (String)cInputData.get(1);
			EndDate = (String)cInputData.get(2);
			VersionNo = (String)cInputData.get(3);
			callpoint = (String)cInputData.get(4);
			StartDay = (String)cInputData.get(5);
			EndDay = (String)cInputData.get(6);
			tG = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0);
		} catch (Exception e) {
			buildError("FIRuleEngineService1","getInputData", "FIRuleEngineService1获取校验参数时出现异常,异常信息：" + e.getMessage());
			return false;
		}
		return true;
	}
	/***************************************************************************
	 * 调用业财差异校验规则引擎
	 * @param strOperator
	 * @return
	 */	
	private boolean dealData(String strOperator)
	{
		try{

            /**校验项目**/
			String[] temp = callpoint.split(",");
			logger.debug(temp.length);
					
			for(int i = 0;i<temp.length;i++){
				/**传规则引擎所需参数**/
				VData tVData = new VData();	
				tVData.addElement(tG);
				tVData.addElement(StartDate);
				tVData.addElement(EndDate);
				tVData.addElement(VersionNo);
				logger.debug(temp[i]);
				tVData.addElement(temp[i]);
				/**业财规则引擎类**/
				FIRuleEngine mFIRuleEngine = new FIRuleEngine();
				mFIRuleEngine.submitData(tVData, strOperator);
			 }
		    }
			catch(Exception ex)
			{
				buildError("FIRuleEngineService1","SubmitData", "业财校验功能调用时出现异常，异常信息为：" + ex.getMessage());
				ex.printStackTrace();
				return false;
			}
		return true;
	}
	/***************************************************************************
	 * 业财差异校验参数提交
	 * @param cInputData,strOperator
	 * @return
	 */	
	public boolean submitData(VData cInputData,String strOperator)
	{
		
		if (!getInputData(cInputData))
		{
			return false;
		}
		if (!dealData(strOperator))
		{
			return false;
		}
        return true;
	}
	/***************************************************************************
	 * 报错处理
	 * @param szModuleName,szFunc,szErrMsg
	 * @return
	 */		
    private void buildError(String szModuleName, String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = szModuleName;
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        logger.debug(szErrMsg);
    }
    
	public static void main(String[] args) 
	{
		FIRuleEngineService1 oFIRuleEngineService1 = new FIRuleEngineService1();
		 
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tVData.clear();

		tVData.addElement(tG);
		tVData.addElement("2009-02-01");
		tVData.addElement("2009-02-28");
		tVData.addElement("00000000000000000001");
		tVData.addElement("01,02");
		tVData.addElement("2009-02-01");
		tVData.addElement("2009-02-28");
		if (oFIRuleEngineService1.submitData(tVData, "difference")) {
			logger.debug("T");
		} else {
			logger.debug("S");
		}


	}
	public CErrors getErrors() {
		return this.mErrors;
	}
	public VData getResult() {
		return null;
	}

	
}
