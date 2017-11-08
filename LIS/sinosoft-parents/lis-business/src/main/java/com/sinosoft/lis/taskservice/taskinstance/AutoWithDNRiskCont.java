package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.DelPrtUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 业务系统
 * </p>
 * <p>
 * Description: 自动删除投保单数据处理－后台自动处理入口
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

public class AutoWithDNRiskCont extends TaskThread {
private static Logger logger = Logger.getLogger(AutoWithDNRiskCont.class);
	public AutoWithDNRiskCont() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("...AutoWithDNRiskCont Start..."
				+ PubFun.getCurrentDate() + " " + PubFun.getCurrentTime());
		try {	
			String Content;
			// 准备传输数据 VData
			GlobalInput tG = new GlobalInput();  
			tG.Operator = "AUTO";
			tG.ComCode = "86";
			tG.ManageCom = "86";
			 //日志监控,初始化           	
			tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
			tG.LogID[1]=(String)mParameters.get("SerialNo"); 
		    //日志监控,过程监控        
		  	PubFun.logTrack(tG,tG.LogID[1],"销售限制自动撤单批处理开始");
			String tPrtNo = "";			
			
			String strSql = " select a.prtno from lccont a,lwmission b where 1=1 and activityid='0000001501' and a.contno=b.missionprop1 " ;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS.getMaxRow() > 0) {
				int sucnum=0,failnum=0;
				for(int i=1;i<=tSSRS.getMaxRow();i++)
				{
					tPrtNo = tSSRS.GetText(i, 1);
					TransferData tTransferData=new TransferData();					
					tTransferData.setNameAndValue("PrtNo",tPrtNo);
					VData tVData = new VData();
					tVData.add(tTransferData);
					tVData.add( tG );
					
					// 数据传输
					DelPrtUI tDelPrtUI = new DelPrtUI();
					tDelPrtUI.submitData(tVData,"DELETE||PRT");

					int n = tDelPrtUI.mErrors.getErrorCount();
					logger.debug("---ErrCount---" + n);			
					if (n == 0) {
						sucnum++;
						Content = "投保单(印刷号：" +tPrtNo+ ")自动删除成功! ";
//						日志监控,状态监控          
		  		    	PubFun.logState(tG,tPrtNo,Content,"1");
					} else {
						failnum++;
						String strErr = "\\n";
						for (int j = 0; j < n; j++) {
							strErr += (j + 1)
									+ ": "
									+ tDelPrtUI.mErrors.getError(j).errorMessage
									+ "; \\n";
						}
						Content = "投保单(印刷号：" +tPrtNo+ ")自动删除处理失败，原因是: " + strErr;
//						日志监控,状态监控          
		  		    	PubFun.logState(tG,tPrtNo,Content,"0");
					}	
					logger.debug(Content);
				}	
//				日志监控,结果监控
				PubFun.logResult (tG,tG.LogID[1],"共有"+sucnum+"个待自动删除的投保单删除成功");
				PubFun.logResult (tG,tG.LogID[1],"共有"+failnum+"个待自动删除的投保单删除失败");	
			}
			else 
			{
				Content = "没有待自动删除的投保单！";
			    //日志监控,过程监控        
		    	PubFun.logTrack(tG,tG.LogID[1],"没有待自动删除的投保单");
				logger.debug(Content);
				return false;
			}	
		    //日志监控,过程监控        
		  	PubFun.logTrack(tG,tG.LogID[1],"销售限制自动撤单批处理结束");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		logger.debug("...AutoWithDNRiskCont End..."
				+ PubFun.getCurrentDate() + " " + PubFun.getCurrentTime());

		return true;
		/* end 业务处理逻辑 */
	}

	public static void main(String[] args) {
		AutoWithDNRiskCont tAutoWithDNRiskCont = new AutoWithDNRiskCont();
		tAutoWithDNRiskCont.dealMain();
	}
}
