<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：BQManuUWChk.jsp
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
	LPContSet tLPContSet = new LPContSet();

	String tMissionID = request.getParameter("MissionID");
	String tActivityID = request.getParameter("ActivityID");
	System.out.println("ssssssssssssssssssssssssssss" +tActivityID);
  String tSubMissionID = request.getParameter("SubMissionID");
  String tContNo = request.getParameter("ContNo");
  String tPrtNo = request.getParameter("PrtNo");
  String tEdorno  =request.getParameter("EdorNo");
  loggerDebug("BQManuUWChk","Edorno=="+tEdorno);
  String tCreateFlag ="0";
	boolean flag = false;
	if (!tContNo.equals("") ){
		flag = true;
	}			
		try
		{
		loggerDebug("BQManuUWChk","flag=="+flag);
		  	if (flag == true)
		  	{
		  String tSQL =
		        //取投保人数量	
		        " select appntno,'A' from lpappnt a where contno='"+tContNo+"' and edorno='"+tEdorno+"' "
				+ " and not exists " 
				+ " (select 1 from lwmission where missionid='"+tMissionID+"' and activityid in (select activityid from lwactivity where functionid='10020100') "
				+ " and missionprop15=a.appntno and missionprop16='A' ) " 
				+ " union "
                //取被保人数量 not exists(select 1 from lpedoritem where edortype in('CT','WT'))
				+ " select insuredno,'I' from lpinsured a where contno='"+tContNo+"' and edorno='"+tEdorno+"' "
				+ " and  not exists " 
				+ " (select 1 from lwmission where missionid='"+tMissionID+"' and activityid in(select activityid from lwactivity where functionid='10020100') "
				+ " and missionprop15=a.insuredno and missionprop16='I' ) "
				;
    loggerDebug("BQManuUWChk","####tSQL:"+tSQL);
	SSRS tSSRS = new SSRS();
	ExeSQL tExeSQL = new ExeSQL();
	tSSRS = tExeSQL.execSQL(tSQL);
	if(tSSRS.getMaxRow()==0){
		tCreateFlag = "0";
		tSQL =
	        //取投保人数量	
	        " select appntno,'A' from lpcont a where edorno='"+tEdorno+"' and contno='"+tContNo+"' "
			+ " and not exists " 
			+ " (select 1 from lwmission where missionid='"+tMissionID+"' and activityid in(select activityid from lwactivity where functionid='10020100') "
			+ " and missionprop15=a.appntno and missionprop16='A' ) " 
			+ " union "
            //取被保人数量
			+ " select insuredno,'I' from lpcont a where edorno='"+tEdorno+"' and contno='"+tContNo+"'"
			+ " and not exists " 
			+ " (select 1 from lwmission where missionid='"+tMissionID+"' and activityid in(select activityid from lwactivity where functionid='10020100') "
			+ " and missionprop15=a.insuredno and missionprop16='I' ) "
			;
		tSSRS = tExeSQL.execSQL(tSQL);
	}
	 int x=0;
	 for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				// 准备传输数据 VData
				
				TransferData tTransferData = new TransferData();
 				LCContSchema tLCContSchema = new LCContSchema();
				if (!tContNo.equals("") )
				{
						loggerDebug("BQManuUWChk","ContNo:"+tContNo);
	  		
		    		tLCContSchema.setContNo( tContNo );
		    		tTransferData.setNameAndValue("ContNo",tContNo);
				    tTransferData.setNameAndValue("PrtNo",tPrtNo);
	    			tTransferData.setNameAndValue("MissionID",tMissionID);
	     			//tTransferData.setNameAndValue("SubMissionID",String.valueOf(i));
	     			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
	     			
	     			tTransferData.setNameAndValue("DefaultOperator", tG.Operator);
	     			//tTransferData.setNameAndValue("CreateFlag",tCreateFlag);
		    		flag = true;
				}
				
				tCreateFlag="1";
				x++;
				loggerDebug("BQManuUWChk","tCreateFlag:"+tCreateFlag);
				String tCustomerNo = tSSRS.GetText(i,1);
				String tCustomerNoType = tSSRS.GetText(i,2);
				tTransferData.removeByName("CustomerNo");
				tTransferData.removeByName("CustomerNoType");
				tTransferData.setNameAndValue("CustomerNo", tCustomerNo);
				tTransferData.setNameAndValue("CustomerNoType",tCustomerNoType);
				tTransferData.setNameAndValue("ActivityID",tActivityID);
				tTransferData.setNameAndValue("BusiType", "1002");
				tTransferData.setNameAndValue("OldPrtSeq", "");
				tTransferData.setNameAndValue("CreateFlag", tCreateFlag);
				tTransferData.setNameAndValue("DefaultOperator", tG.Operator);
				System.out.println("1++++++++++++++" +tSSRS.getMaxRow()+"=="+i);
				//if(i!=tSSRS.getMaxRow()){
				//	tTransferData.setNameAndValue("DeleteActivity005","1");
				//}
				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tG);
				// 数据传输
				//EdorWorkFlowUI tEdorWorkFlowUI   = new EdorWorkFlowUI();
				//String busiName="tWorkFlowUI";
				String busiName="WorkFlowUI";
	 			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  		 if(!tBusinessDelegate.submitData(tVData,"execute",busiName))				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					loggerDebug("BQManuUWChk","n=="+n);
					for (int j = 0; j < n; j++)         
					loggerDebug("BQManuUWChk","Error: "+tBusinessDelegate.getCErrors().getError(j).moduleName);
					Content = " 自动核保失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    //tErrors = tTbWorkFlowUI.mErrors;
				    Content = "";
				    if (!tError.needDealError())
				    {                          
				    	//Content = "自动核保成功!";
				    	int n = tError.getErrorCount();
		    			if (n > 0)
		    			{    			      
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
					    }
		
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	int n = tError.getErrorCount();
				    	//Content = "自动核保失败!";
		    			if (n > 0)
		    			{
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
							}
				    	FlagStr = "Fail";
				    }
				}
			}
 	      if(tSSRS.getMaxRow()==0)
 	      {
 	    	 FlagStr = "Succ";
 	      }
			}
			else
			{
				Content = "请选择保单！";
			}  
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Content = Content.trim() +" 提示:异常退出.";
		}
	
	
loggerDebug("BQManuUWChk","AUTO UW END!");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.initPolGrid();
	<%
	//parent.fraInterface.fm.submit();
	%>
</script>
</html>
