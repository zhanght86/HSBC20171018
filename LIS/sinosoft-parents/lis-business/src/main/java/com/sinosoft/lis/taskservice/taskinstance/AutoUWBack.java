package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.tb.SelAssignPlanBL;
import com.sinosoft.workflow.tb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.TransferData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 个单自动签单－后台自动处理入口</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author:ck
 * @version 1.0
 * @CreateDate：2005-09-07
 */
public class AutoUWBack extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoUWBack.class);

    public CErrors mErrors = new CErrors();
   // protected HashMap mParameters = new HashMap();
    private MMap map = new MMap();
    int mcount =1;
    private VData mInputData;

    public AutoUWBack()
    {
    }

    
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC2000")) {
			return false;
		}
		return true;
	}
	
	
    

    public boolean dealMain()
    {
      /*业务处理逻辑*/
      logger.debug("进入业务逻辑处理!");
      GlobalInput tGI = new GlobalInput();
      TransferData mTransferData =new TransferData();
      tGI.Operator = "AUTO";
      tGI.ManageCom = "86";
      tGI.ComCode = "86";
		 //日志监控,初始化           	
		tGI.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
		tGI.LogID[1]=(String)mParameters.get("SerialNo"); 
      CErrors tError = null;
      String Content = "";
      String FlagStr = "";
      LCIndAppSignLogSchema tLCIndAppSignLogSchema = new LCIndAppSignLogSchema();
      LCIndAppSignLogDB tLCIndAppSignLogDB =new LCIndAppSignLogDB();
      LCIndAppSignLogSet tLCIndAppSignLogSet = new LCIndAppSignLogSet();
      LWMissionDB tLWMissionDB =new LWMissionDB();
      LWMissionSet tLWMissionSet = new LWMissionSet();
	    //日志监控,过程监控        
  	PubFun.logTrack(tGI,tGI.LogID[1],"自动核保订正批处理开始");
  	int sucnum=0,failnum=0;
      //String strSql = " select * from lcindappsignlog where errtype = '01' and state = '00' and (ErrCode like '%035%' or ErrCode like '%005%' or ErrCode like '%004%' or ErrCode like '%040%' ) ";
      String strSql = " select * from lcindappsignlog where errtype = '01' and state = '00' and (ErrCode like '%005%' or ErrCode like '%040%' ) ";
      SQLwithBindVariables sqlbv=new SQLwithBindVariables();
      sqlbv.sql(strSql);
      tLCIndAppSignLogSet = tLCIndAppSignLogDB.executeQuery(sqlbv);
      if (tLCIndAppSignLogSet.size()>0)
      {
    	  for(int i=1;i<=tLCIndAppSignLogSet.size();i++){
    		  logger.debug("需要自动订正的保单印刷号为： "+tLCIndAppSignLogSet.get(i).getPrtNo());
    		  //用lwactivity.functionid替换activityid，此时可以去掉processid。2013-04-23 lzf
//    		  String tLwmSql = "select * from lwmission where 1=1 and activityid = '0000001149' " 
//    		  				  +"and processid = '0000000003' and MissionProp2 like '86%' and missionprop1 ='"+tLCIndAppSignLogSet.get(i).getPrtNo()+"'";
    		  String tLwmSql = "select * from lwmission where 1=1 and activityid in (select activityid from lwactivity  where functionid ='10010041') " 
	  				  +" and MissionProp2 like '86%' and missionprop1 ='"+"?missionprop1?"+"'";
    		  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    	      sqlbv1.sql(tLwmSql);
    	      sqlbv1.put("missionprop1", tLCIndAppSignLogSet.get(i).getPrtNo());
    		  tLWMissionSet =tLWMissionDB.executeQuery(sqlbv1);
    		  if(tLWMissionSet.size()>1)
    		  {
    			  failnum++;	
    			//日志监控,状态监控          
  		    	PubFun.logState(tGI,tLCIndAppSignLogSet.get(i).getPrtNo(),"投保单（印刷号："+tLCIndAppSignLogSet.get(i).getPrtNo()+"）核保订正错误","0");
    			  logger.debug("核保订正错误！");
    			  continue;
    		  }else if(tLWMissionSet.size()==0){
    			  failnum++;
    			  logger.debug("合同号为： "+tLCIndAppSignLogSet.get(i).getPrtNo()+"不在核保订正节点！");
    			    //日志监控,状态监控         
    		    	PubFun.logState(tGI,tLCIndAppSignLogSet.get(i).getPrtNo(),"投保单（印刷号："+tLCIndAppSignLogSet.get(i).getPrtNo()+"）不在核保订正节点","1");
    			  continue;
    		  }else{
    			  //tongmeng 2009-06-18 modify
    			  //如果合同下有暂収费未到账,不进行自动核保订正处理.
    			  String tSQL_TempFee = "select count(*) from ljtempfee where tempfeetype='1' "
    				                  + " and enteraccdate is null "
    				                  + " and otherno='"+"?otherno?"+"'";
    			  SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        	      sqlbv2.sql(tSQL_TempFee);
        	      sqlbv2.put("otherno", tLWMissionSet.get(1).getMissionProp1());
    			  ExeSQL tExeSQL = new ExeSQL();
    			  String tValue = "";
    			  tValue = tExeSQL.getOneValue(sqlbv2);
    			  if(tValue!=null&&!tValue.equals(""))
    			  {
    				  if(Integer.parseInt(tValue)>0)
    				  {
    					   //日志监控,状态监控        
    	    		    	PubFun.logState(tGI,tLCIndAppSignLogSet.get(i).getPrtNo(),"投保单（印刷号："+tLCIndAppSignLogSet.get(i).getPrtNo()+"）暂収费未到账,不进行自动核保订正处理","2"); 
    	    		    	failnum++;
    	    		    	continue;  
    				  }
    			  }
    			  //准备相应的数据
    			  mTransferData =new TransferData();
    			  mTransferData.setNameAndValue("UWFlag", "z");
    			  mTransferData.setNameAndValue("ContNo", tLWMissionSet.get(1).getMissionProp1());
    			  logger.debug("-----------------ContNo-" + tLWMissionSet.get(1).getMissionProp1());
    			  mTransferData.setNameAndValue("AppntName", tLWMissionSet.get(1).getMissionProp7());
    			  mTransferData.setNameAndValue("InsuredName", tLWMissionSet.get(1).getMissionProp2());
    			  mTransferData.setNameAndValue("AgentGroup", tLWMissionSet.get(1).getMissionProp4());
    			  mTransferData.setNameAndValue("MissionID", tLWMissionSet.get(1).getMissionID());
    			  mTransferData.setNameAndValue("SubMissionID", tLWMissionSet.get(1).getSubMissionID());
    			  //2008-12-24 ln add
    			 // mTransferData.setNameAndValue("UWIdea", "自动订正");
//    			  if(tLCIndAppSignLogSet.get(i).getErrCode().indexOf("004")!=-1 || tLCIndAppSignLogSet.get(i).getErrCode().indexOf("035")!=-1 )
//    			  { 
//    				  logger.debug("自动订正原因：保费不足！");
//    				  mTransferData.setNameAndValue("UWIdea", "保费不足"); 
//    			  }
//    			  else 
    			  if(tLCIndAppSignLogSet.get(i).getErrCode().indexOf("005")!=-1 )
    			  { 
    				  logger.debug("自动订正原因：生日单！");
    				   //日志监控,过程监控        
	    		    	PubFun.logTrack(tGI,tLCIndAppSignLogSet.get(i).getPrtNo(),"投保单（印刷号："+tLCIndAppSignLogSet.get(i).getPrtNo()+"）自动订正原因为生日单"); 
    				  mTransferData.setNameAndValue("UWIdea", "生日单"); 
    			  }
    			  else
    			  { 
    				  logger.debug("自动订正原因：投保单代理人与暂交费代理人不一致！");
      				   //日志监控,过程监控        
	    		    	PubFun.logTrack(tGI,tLCIndAppSignLogSet.get(i).getPrtNo(),"投保单（印刷号："+tLCIndAppSignLogSet.get(i).getPrtNo()+"）自动订正原因为投保单代理人与暂交费代理人不一致"); 
    				  mTransferData.setNameAndValue("UWIdea", "投保单代理人与暂交费代理人不一致"); 
    			  }
    			  //2008-12-24 end
    			  mTransferData.setNameAndValue("Uw_State", "5");
    			  try {
    			            // 准备传输数据 VData
    			            VData tVData = new VData();
    			            tVData.add(mTransferData);
    			            tVData.add(tGI);

    			            // 数据传输
    			            TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
    			            if (tTbWorkFlowUI.submitData(tVData, "0000001149") == false) {
    			            	failnum++;
    			                int n = tTbWorkFlowUI.mErrors.getErrorCount();
    			                for (int j = 0; j < n; j++)
    			                    Content = "核保订正失败，原因是: " + tTbWorkFlowUI.mErrors.getError(j).errorMessage;
    			 				   //日志监控,状态监控        
    		    		    	PubFun.logState(tGI,tLCIndAppSignLogSet.get(i).getPrtNo(),"投保单（印刷号："+tLCIndAppSignLogSet.get(i).getPrtNo()+"）"+Content,"3"); 
    			                
    			                logger.debug(Content);
//    			                tLWMissionSet =tLWMissionDB.executeQuery("select * from lwmission where activityid = '0000001149' and missionprop1 ='"+tLCIndAppSignLogSet.get(i).getPrtNo()+"'");
    			                SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
    			        	      sqlbv3.sql("select * from lwmission where activityid in " +
      			                		"(select activityid from lwactivity  where functionid ='10010041') and missionprop1" +
      			                		" ='"+"?missionprop1?"+"'");
    			        	      sqlbv3.put("missionprop1", tLCIndAppSignLogSet.get(i).getPrtNo());
    			                tLWMissionSet =tLWMissionDB.executeQuery(sqlbv3);
    			            }else{
//    			            	tLWMissionSet =tLWMissionDB.executeQuery("select * from lwmission where activityid = '0000001149' and missionprop1 ='"+tLCIndAppSignLogSet.get(i).getPrtNo()+"'");
    			            	 SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
   			        	      sqlbv4.sql("select * from lwmission where activityid in" +
  			            			" (select activityid from lwactivity  where functionid ='10010041') and missionprop1 " +
  			            			"='"+"?missionprop1?"+"'");
   			        	      sqlbv4.put("missionprop1", tLCIndAppSignLogSet.get(i).getPrtNo());
    			            	tLWMissionSet =tLWMissionDB.executeQuery(sqlbv4);

    			            }
    			            if(tLWMissionSet.size()<=0){
    			         	   //日志监控,状态监控        
    		    		    	PubFun.logState(tGI,tLCIndAppSignLogSet.get(i).getPrtNo(),"投保单（印刷号："+tLCIndAppSignLogSet.get(i).getPrtNo()+"）核保订正成功","4"); 
    			            	//订正成功 将LCIndappSignLog表中的State改为“01”
    			            	LCIndAppSignLogSet tLCISLSet = new LCIndAppSignLogSet();
    			            	tLCIndAppSignLogSchema = tLCIndAppSignLogSet.get(i);
    			            	logger.debug("错误记录的当前状态为："+tLCIndAppSignLogSchema.getState());
    			            	tLCIndAppSignLogSchema.setState("01");
    			            	tLCISLSet.add(tLCIndAppSignLogSchema);
    			            	map.put(tLCISLSet, "UPDATE");
    			            	sucnum++;    			            	
    			            }
    			            else{
    			            	failnum++;
    			            	continue;
    			            }
    			            
    			            if (!prepareOutputData()) {
    			    			return false;
    			    		}
    			    	  PubSubmit tPubSubmit = new PubSubmit();
    			    		if (!tPubSubmit.submitData(mInputData, "")) {
    			    			// @@错误处理
    			    			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
    			    			CError.buildErr(this, "PubSubmit 提交数据时错误！");
    			    			return false;
    			    		}
    			    }
    			    catch (Exception e) {
    			        e.printStackTrace();
    			        Content = Content.trim() + ".提示：异常终止!";
    			    }
    		  }
    		  
    	  }
    	  
      }else{
    	  logger.debug("没有发现需要自动订正的保单");
		    //日志监控,过程监控        
	    	PubFun.logTrack(tGI,tGI.LogID[1],"没有发现需要自动核保订正的保单");
      }
	   
//		日志监控,结果监控
		PubFun.logResult (tGI,tGI.LogID[1],"共有"+sucnum+"个投保单自动核保订正成功");
		PubFun.logResult (tGI,tGI.LogID[1],"共有"+failnum+"个投保单自动核保订正失败");	    
		
      //日志监控,过程监控        
    	PubFun.logTrack(tGI,tGI.LogID[1],"自动核保订正批处理结束");
      
      





return true;
}
/**
* prepareOutputData
* 
* @return boolean
*/
private boolean prepareOutputData() {
	try {
		mInputData=new VData();
		mInputData.clear();
		mInputData.add(map);
	} catch (Exception ex) {
		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "SelAssignPlanBL";
		tError.functionName = "prepareOutputData";
		tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
		mErrors.addOneError(tError);
		return false;
	}

	return true;
}
	

    public static void main(String[] args)
    {
    	AutoUWBack tAutoUWBack = new AutoUWBack();
    	tAutoUWBack.dealMain();
    	logger.debug("测试完毕！");
    }
}
