/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;
import com.sinosoft.lis.taskservice.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.finfee.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 财务外包数据处理－后台自动处理入口</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author ln
 * @version 1.0
 * @CreateDate：2008-06-26
 */
public class FineBPOAutoTask extends TaskThread
{
private static Logger logger = Logger.getLogger(FineBPOAutoTask.class);


  public FineBPOAutoTask()
  {
  }

  public boolean dealMain()
  {
    /*业务处理逻辑*/
    logger.debug("...FineBPOAutoTask Start..."+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
    try
    {
    	//add 2008.5.22
    	String tOperator = (String)this.mParameters.get("WBOperator");
    	//test 
//    	String tOperator = "";
    	//ended add 2008.5.22
			// 准备传输数据 VData
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("tOperator",tOperator);
			tVData.add(tTransferData);
			// 数据传输
			FineBPODealInputDataUI tBPODealInputDataUI   = new FineBPODealInputDataUI();
			tBPODealInputDataUI.submitData( tVData, "INSERT" );

      int n = tBPODealInputDataUI.mErrors.getErrorCount();
      logger.debug("---ErrCount---"+n);
      String Content ;
			if( n == 0 )
		    {
		    	Content = "处理成功! ";
		    }
		  else
		    {
				 String strErr = "\\n";
				 for (int i = 0; i < n; i++)
				 {
					 strErr += (i+1) + ": " + tBPODealInputDataUI.mErrors.getError(i).errorMessage + "; \\n";
				 }
				 Content = "部分财务收费单处理失败，原因是: " + strErr;
			  }
			  logger.debug(Content);
      }
      catch(Exception ex)
      {
      	ex.printStackTrace();
      }
      logger.debug("...FineBPOAutoTask End..."+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
    /*end 业务处理逻辑 */
    return true;
  }

  public static void main(String[] args)
  {
    FineBPOAutoTask tFineBPOAutoTask = new FineBPOAutoTask();
    tFineBPOAutoTask.run();
  }
}
