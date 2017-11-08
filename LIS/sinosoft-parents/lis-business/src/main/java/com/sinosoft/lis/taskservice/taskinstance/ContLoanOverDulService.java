
package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

/**
 * <p>Title: 保单借款逾期催付批处理</p>
 * <p>Description: 
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author  pst
 * @version 1.0
 */

import com.sinosoft.lis.bq.ContLoanOverDulBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.taskservice.TaskThread;
import java.util.HashMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


public class ContLoanOverDulService extends TaskThread
{
private static Logger logger = Logger.getLogger(ContLoanOverDulService.class);

    public ContLoanOverDulService()
    {
    }
   
    public boolean dealMain()
    {
        logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝   保单借款逾期催付批处理开始 !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        logger.debug("参数个数:" + mParameters.size());
        GlobalInput tGlobalInput =null;

        //处理的机构
        tGlobalInput = (GlobalInput)mParameters.get("GlobalInput");
        
        //统计止期
		String tCalDate = (String) mParameters.get("CalDate");		
		logger.debug("The CalDate is :" + tCalDate);    
		// 保单号
		String tContNo = (String) mParameters.get("ContNo");
		
		logger.debug("The ContNo is :" + tContNo);
		
        if(tGlobalInput==null)
        {
        	tGlobalInput = new GlobalInput();
            tGlobalInput.Operator = "001";
            tGlobalInput.ManageCom = "86";
        }
        //日志监控,初始化           	
        tGlobalInput.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
        tGlobalInput.LogID[1]=(String)mParameters.get("SerialNo"); 
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", tContNo);
		tTransferData.setNameAndValue("CalDate", tCalDate);
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tGlobalInput);
		ContLoanOverDulBL tContLoanOverDulBL = new ContLoanOverDulBL();
		if (!tContLoanOverDulBL.submitData(tVData, "")) {
			return false;
		}
		return true;
    }
    
	public static void main(String str[]) {
		ContLoanOverDulService tContLoanOverDulService = new ContLoanOverDulService();
		HashMap aParameters =new HashMap();
//		aParameters.put("ContNo", "86110020080219002444");
		tContLoanOverDulService.setParameter(aParameters);
		tContLoanOverDulService.dealMain();
	
	}
}
