package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

/**
 * <p>Title: 红利分配批处理</p>
 * <p>Description: 
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author  pst
 * @version 1.0
 */

import com.sinosoft.lis.get.BonusAssignManuTmp;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.taskservice.TaskThread;
import java.util.HashMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


public class BonusAssignServiceTmp extends TaskThread
{
private static Logger logger = Logger.getLogger(BonusAssignServiceTmp.class);

    public BonusAssignServiceTmp()
    {
    }
   
	/**选数SQL，循环处理，筛选一部分保单，也可以指定某一保单,时间控制，
	 * 即选出在系统时间之前尚未进行分红的保单,但不包括失效和终止的报单
	 * */ 
    private String tSql="";
    public boolean dealMain()
    {
        logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  红利分配批处理开始 !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        logger.debug("参数个数:" + mParameters.size());
        GlobalInput tGlobalInput =null;

        //处理的机构
        tGlobalInput = (GlobalInput)mParameters.get("GlobalInput");

        //分红年度
        String tFiscalYear = (String)mParameters.get("FiscalYear");
        logger.debug("The CalDate is :"+tFiscalYear);
        
		// 红利分配保单号
		String tContNo = (String) mParameters.get("ContNo");
		logger.debug("The ContNo is :" + tContNo);
		
        //如果会计年度录入直接将其返回
        if(tFiscalYear==null || "".equals(tFiscalYear))
        {
        	return false;
        }
        
        if(tGlobalInput==null)
        {
        	tGlobalInput = new GlobalInput();
            tGlobalInput.Operator = "001";
            tGlobalInput.ManageCom = "86";
        }

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FiscalYear", tFiscalYear);
		tTransferData.setNameAndValue("ContNo", tContNo);
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tGlobalInput);
		BonusAssignManuTmp tBonusAssignManuTmp = new BonusAssignManuTmp();
		if (!tBonusAssignManuTmp.submitData(tVData, "")) {
			return false;
		}
		return true;
    }
    
	public static void main(String str[]) {
		BonusAssignServiceTmp tBonusAssignService = new BonusAssignServiceTmp();
		HashMap aParameters =new HashMap();
		aParameters.put("FiscalYear", "2007");
//		aParameters.put("ContNo", "86320320040210001136");
		tBonusAssignService.setParameter(aParameters);
		tBonusAssignService.dealMain();
	
	}
}
