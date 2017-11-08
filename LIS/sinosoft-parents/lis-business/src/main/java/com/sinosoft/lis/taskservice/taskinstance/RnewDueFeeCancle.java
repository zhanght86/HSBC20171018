package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.operfee.IndiDueFeeCancelUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>Title: RnewDueFeeCancle</p>
 * <p>Description:续期催收自动撤销
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: SinoSoft</p>
 * @author duanyh
 * @version 1.0
 */
public class RnewDueFeeCancle extends TaskThread{
private static Logger logger = Logger.getLogger(RnewDueFeeCancle.class);
//全局变量
private GlobalInput mGlobalInput = new GlobalInput();

	public RnewDueFeeCancle() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RnewDueFeeCancle tRnewDueFeeCancle = new RnewDueFeeCancle();
		tRnewDueFeeCancle.dealMain();
		// TODO Auto-generated method stub
	}
	
	ExeSQL tExeSQL = new ExeSQL();
	String CurrentDay = PubFun.getCurrentDate();
	String CurrentTime = PubFun.getCurrentTime();
	PubSubmit tPubSubmit = new PubSubmit();
	CErrors tError = null;
	
	public boolean dealMain(VData aVData)
	{
		mGlobalInput=(GlobalInput) aVData.getObjectByObjectName(
				"GlobalInput", 0);	  
		
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"续期催收自动撤销批处理开始");
	  	int suc=0,fail=0;
		logger.debug("Start RnewDueFeeCancle ......"+PubFun.getCurrentTime());
//		GlobalInput tGI = new GlobalInput();
//		tGI.Operator = "Auto";
//		tGI.ManageCom = "86";
//		tGI.ComCode = "86";
		try
		{
			//需要撤销的单子：已经催收，但是保单状态变成失效或者终止  -- 终止：52464   --失效： 44584
			String sql =" select otherno from ljspay where othernotype = '2'"
	                   +" and exists (select 1 from lccont where contno = ljspay.otherno and conttype='1' and appflag = '4')"
	                   +" union"
	                   +" select otherno from ljspay where othernotype = '2'"
	                   +" and exists (select 1 from lccont where contno = ljspay.otherno and conttype='1' and appflag = '1')"
	                   +" and exists (select 1 from lccontstate where contno = ljspay.otherno"
					   +" and statetype = 'Available' and state = '1' and enddate is null)";	
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	 		sqlbv1.sql(sql);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv1);
			logger.debug("tSSRS.getMaxRow()="+tSSRS.getMaxRow());
			for(int i=1;i<=tSSRS.getMaxRow();i++)			
			{
				LCContSchema tLCContSchema = new LCContSchema(); // 个人保单表
				tLCContSchema.setContNo(tSSRS.GetText(i, 1));				
				VData tVData = new VData();
				tVData.add(tLCContSchema);
				tVData.add(mGlobalInput);				
				IndiDueFeeCancelUI tIndiDueFeeCancelUI = new IndiDueFeeCancelUI();
				if(!tIndiDueFeeCancelUI.submitData(tVData, "INSERT"))
				{
//					日志监控,状态监控          
	  		    	PubFun.logState(mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"续期催收自动撤销失败","0");
	  		    	fail++;
					continue;
				}	
//				日志监控,状态监控          
  		    	PubFun.logState(mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"续期催收自动撤销成功","1");
  		    	suc++;
			}
			logger.debug("End RnewDueFeeCancle ......"+PubFun.getCurrentTime());
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共有"+suc+"个保单续期催收自动撤销成功");
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共有"+fail+"个保单续期催收自动撤销失败");	
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"续期催收自动撤销批处理结束");
		return true;
	}	
	
	public boolean dealMain()
	{
		logger.debug("Start RnewDueFeeCancle ......"+PubFun.getCurrentTime());
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "Auto";
		tGI.ManageCom = "86";
		tGI.ComCode = "86";
		try
		{
			//需要撤销的单子：已经催收，但是保单状态变成失效或者终止  -- 终止：52464   --失效： 44584
			String sql =" select otherno from ljspay where othernotype = '2'"
	                   +" and exists (select 1 from lccont where contno = ljspay.otherno and conttype='1' and appflag = '4')"
	                   +" union"
	                   +" select otherno from ljspay where othernotype = '2'"
	                   +" and exists (select 1 from lccont where contno = ljspay.otherno and conttype='1' and appflag = '1')"
	                   +" and exists (select 1 from lccontstate where contno = ljspay.otherno"
					   +" and statetype = 'Available' and state = '1' and enddate is null)";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	 		sqlbv2.sql(sql);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv2);
			logger.debug("tSSRS.getMaxRow()="+tSSRS.getMaxRow());
			for(int i=1;i<=tSSRS.getMaxRow();i++)			
			{
				LCContSchema tLCContSchema = new LCContSchema(); // 个人保单表
				tLCContSchema.setContNo(tSSRS.GetText(i, 1));
				
				VData tVData = new VData();
				tVData.add(tLCContSchema);
				tVData.add(tGI);
				
				IndiDueFeeCancelUI tIndiDueFeeCancelUI = new IndiDueFeeCancelUI();
				if(!tIndiDueFeeCancelUI.submitData(tVData, "INSERT"))
				{
					continue;
				}				
			}
			logger.debug("End RnewDueFeeCancle ......"+PubFun.getCurrentTime());
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}	
	/**
	   * 生成错误信息
	   * @param FuncName
	   * @param ErrMsg
	   */
	private void dealError(String FuncName,String ErrMsg)
	{
		CError tError = new CError();
	    tError.moduleName = "RnewDueFeeCancle";
	    tError.functionName = FuncName;
	    tError.errorMessage = ErrMsg;	  
		mErrors.addOneError(tError);		
	}
}
