package com.sinosoft.lis.fininterface.checkdata;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;


/*****
 * 实现对应的接口处理
 *    校验类对外接口实现类
 * @author lijs
 * @createTime 2008-08-26
 *
 */
public class FIRuleEngineService {
private static Logger logger = Logger.getLogger(FIRuleEngineService.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
   /****
	 *
	 * @param oVData 保存的数据列表如下
	 * @param strOperator
	 *        其中 quantity 表示质量
	 *            difference 表示差异
	 * 固定的参数：GlobalInput 操作员信息
	 * quantity需要的参数信息:
	 *    批次号码、版本号码、事件点、日志文件保存路径
	 *      Eg、
	 *	      ms_BatchNo = (String) oData.get(1);
	 *		  ms_VersionNo = (String) oData.get(2);
	 *		  ms_CallPointID = (String) oData.get(3);
     * difference
	 *	  开始日期、结束日期、版本号码、事件点、日志文件保存路径
	 *		Eg、
	 * 		  ms_StartDate = (String) oData.get(1);
	 *		  ms_EndDate = (String) oData.get(2);
	 *		  ms_VersionNo = (String) oData.get(3);
	 *        ms_CallPointID = (String) oData.get(4);
	 * @return true  -- 永远返回True 不影响调用该接口的程序流程
	 */
	public boolean dealData(VData oVData,String strOperator)
        {

		FIRuleEngine oRuleEngine = new FIRuleEngine();

		if(!oRuleEngine.submitData(oVData, strOperator)){

			//不管校验是否成功返回的都为成功，仅仅在日志处理中保存对应的错误信息
			logger.debug("校验失败!-------但不返回失败信息,仅仅在界面上校验时出现错误信息");
		}
		return true;

	}

}
