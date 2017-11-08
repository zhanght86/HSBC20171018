package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCApplyRecallPolSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.cbcheck.ApplyRecallPolUI;
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
 * Description: 自动撤单数据处理－后台自动处理入口
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

public class AutoWithDCont extends TaskThread {
private static Logger logger = Logger.getLogger(AutoWithDCont.class);
	public AutoWithDCont() {
	}

	public boolean dealMain() {
		
		logger.debug("...AutoWithDCont Start..."
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
			int tSuc = 0, tFail = 0;			
		      //日志监控,过程监控        
	    	PubFun.logTrack(tG,tG.LogID[1],"投保单自动撤单开始");
			
			String tContNo = "";
			String tPrtNo = "";
			String tContent = "逾期未交费";
			String tUWWithDReason = "逾期未交费";	
			String tUWWithDReasonCode = "99";
			String tDay = "";
			
			//2008-12-23 add ln
			String strSql = " select sysvarvalue from ldsysvar where 1=1 and sysvar='autoWithDContDate'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if(tSSRS.getMaxRow() > 0)
			{
				tDay = tSSRS.GetText(1, 1);
			}
			else
			{
				Content = "系统没有定义自动撤单时间间隔！";
			      //日志监控,过程监控        
		    	PubFun.logTrack(tG,tG.LogID[1],"没有在系统中定义自动撤单的逾期未交费时限，不能撤单");
				logger.debug(Content);
				return false;
			}
			//2008-12-23 end
			strSql = " select missionprop1,missionprop2 from lwmission where 1=1 and activityid in (select activityid from lwactivity where functionid='10010042')" 
				+ " and adddate(indate," +"?tDay?"+ ")<'" + "?PubFun?" + "' and indate<='" + "?indate?" + "' order by indate, missionprop1";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(strSql);
			sqlbv1.put("tDay", tDay);
			sqlbv1.put("PubFun", PubFun.getCurrentDate());
			sqlbv1.put("indate", PubFun.getCurrentDate());
			tExeSQL = new ExeSQL();
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv1);
			if (tSSRS.getMaxRow() > 0) {
				for(int i=1;i<=tSSRS.getMaxRow();i++)
				{
					tPrtNo = tSSRS.GetText(i, 1);
					tContNo = tSSRS.GetText(i, 2);
					//补充附加险表			    				
					LCApplyRecallPolSchema tLCApplyRecallPolSchema = new LCApplyRecallPolSchema();		
					tLCApplyRecallPolSchema.setRemark(tContent);
					tLCApplyRecallPolSchema.setPrtNo(tPrtNo);	
					tLCApplyRecallPolSchema.setApplyType("0"); //撤单
					
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("UWWithDReason",tUWWithDReason);
					tTransferData.setNameAndValue("UWWithDReasonCode",tUWWithDReasonCode);
					tTransferData.setNameAndValue("Content",tContent);
					tTransferData.setNameAndValue("ContNo",tContNo);
					
					VData tVData = new VData();
					tVData.add(tTransferData);
					tVData.add( tLCApplyRecallPolSchema);
					tVData.add( tG );
					
					// 数据传输
					ApplyRecallPolUI tApplyRecallPolUI = new ApplyRecallPolUI();
					tApplyRecallPolUI.submitData(tVData, "Auto");

					int n = tApplyRecallPolUI.mErrors.getErrorCount();
					logger.debug("---ErrCount---" + n);			
					if (n == 0) {
						tSuc++;
//						 日志监控,状态监控                 		
	         			PubFun.logState(tG,tContNo, "投保单(印刷号：" +tContNo+ ")自动撤单处理成功","1");  
						Content = "投保单(印刷号：" +tContNo+ ")自动撤单处理成功! ";
					} else {
						String strErr = "\\n";
						for (int j = 0; j < n; j++) {
							strErr += (j + 1)
									+ ": "
									+ tApplyRecallPolUI.mErrors.getError(j).errorMessage
									+ "; \\n";
						}
						tFail++;
//						 日志监控,状态监控                 		
	         			PubFun.logState(tG,tContNo, "投保单(印刷号：" +tContNo+ ")自动撤单处理失败，原因是:"+strErr,"0");  
						Content = "投保单(印刷号：" +tContNo+ ")自动撤单处理失败，原因是: " + strErr;
					}	
					logger.debug(Content);
				}				
			}
			else 
			{
				Content = "没有待自动撤单的投保单！";
			      //日志监控,过程监控        
		    	PubFun.logTrack(tG,tG.LogID[1],"没有待自动撤单的投保单");
				logger.debug(Content);
				return true;
			}	
			
//			日志监控,结果监控
			PubFun.logResult (tG,tG.LogID[1],"本次投保单自动撤单"+tSuc+"笔处理成功");
			PubFun.logResult (tG,tG.LogID[1],"本次投保单自动撤单"+tFail+"笔处理失败");	
		      //日志监控,过程监控        
	    	PubFun.logTrack(tG,tG.LogID[1],"投保单自动撤单结束");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		logger.debug("...AutoWithDCont End..."
				+ PubFun.getCurrentDate() + " " + PubFun.getCurrentTime());
		

		return true;
		/* end 业务处理逻辑 */
	}

	public static void main(String[] args) {
		AutoWithDCont tAutoWithDCont = new AutoWithDCont();
		tAutoWithDCont.dealMain();
	}
}
