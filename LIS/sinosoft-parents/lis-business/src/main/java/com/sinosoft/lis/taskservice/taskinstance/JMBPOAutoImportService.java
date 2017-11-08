package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.tb.JMBPODealInputDataUI;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 业务系统
 * </p>
 * <p>
 * Description: 三岗录入录入外包数据处理－后台自动处理入口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ln
 * @version 1.0
 */

public class JMBPOAutoImportService extends TaskThread {
private static Logger logger = Logger.getLogger(JMBPOAutoImportService.class);
	public JMBPOAutoImportService() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("...JMBPOAutoImportService Start..."
				+ PubFun.getCurrentDate() + " " + PubFun.getCurrentTime());
		try {
			// 准备传输数据 VData
			VData tVData = new VData();
			// 数据传输		
			 
			 LWMissionDB tLWMissionDB =new LWMissionDB();
			 LWMissionSet tLWMissionSet = new LWMissionSet();
		      String strSql = " select * from LWMission where activityid='0000001403' order by makedate";
		      SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql(strSql);
		      logger.debug(strSql);
		      tLWMissionSet = tLWMissionDB.executeQuery(sqlbv5);
		      logger.debug(tLWMissionSet.size());
		      if (tLWMissionSet.size()>0)
		      {
		    	  for(int i=1;i<=tLWMissionSet.size();i++)
		    	  {
		    		  GlobalInput tGI = new GlobalInput();
		 			// tGI.Operator=tLWMissionSet.get(i).getCreateOperator();
		    		 tGI.Operator="021";//内部导入bpoid
		 			 tGI.ManageCom="86" ;
		 			 tGI.ComCode="86" ;
		    		  String tBussNo = tLWMissionSet.get(i).getMissionProp1();
		    		  logger.debug("*******************开始处理投保单"+tBussNo+"********************");
		    		  JMBPODealInputDataUI tJMBPODealInputDataUI = new JMBPODealInputDataUI();
			  			tJMBPODealInputDataUI.submitData(tBussNo , tGI);
	
			  			int n = tJMBPODealInputDataUI.mErrors.getErrorCount();
			  			logger.debug("---ErrCount---" + n);
			  			String Content;
			  			if (n == 0) {
			  				Content = "处理成功! ";
			  			} else {
			  				String strErr = "\\n";
			  				for (int j = 0; j < n; j++) {
			  					strErr += (j + 1)
			  							+ ": "
			  							+ tJMBPODealInputDataUI.mErrors.getError(j).errorMessage
			  							+ "; \\n";
			  				}
			  				Content = "投保单(印刷号："+tBussNo+")处理失败，原因是: " + strErr;
			  			}
			  			logger.debug(Content);
	    	  
		          }
		      }else{
		    	  logger.debug("没有需要处理的投保单！");
		      }
		
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("...JMBPOAutoImportService End..."
				+ PubFun.getCurrentDate() + " " + PubFun.getCurrentTime());
		return true;
		/* end 业务处理逻辑 */
	}

	public static void main(String[] args) {
		JMBPOAutoImportService jmbpoautoimportservice = new JMBPOAutoImportService();
		jmbpoautoimportservice.dealMain();
	}
}
