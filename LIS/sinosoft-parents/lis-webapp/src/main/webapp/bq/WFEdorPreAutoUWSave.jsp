<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorAutoUWSave.jsp
//程序功能：保全工作流-自动核保
//创建日期：2005-04-30 14:02:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //输出参数
  String FlagStr = "";
  String Content = ""; 
  CErrors tError = null;
  BusinessDelegate tBusinessDelegate;
 
  LPEdorAppSchema tLPEdorAppSchema;
  TransferData tTransferData;
  TransferData tResultData;
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
	  //===================
	  String tEdorAcceptNo[]  = request.getParameterValues("PrivateWorkPoolGrid1");
	  String tOtherNo[]       = request.getParameterValues("PrivateWorkPoolGrid2");
	  String tOtherNoType[]   = request.getParameterValues("PrivateWorkPoolGrid9");
	  String tEdorAppName[]   = request.getParameterValues("PrivateWorkPoolGrid10");
	  String tApptype[]       = request.getParameterValues("PrivateWorkPoolGrid11");  
	  String tManageCom[]     = request.getParameterValues("PrivateWorkPoolGrid12");
	  String tAppntName[]     = request.getParameterValues("PrivateWorkPoolGrid4");
	  String tPaytoDate[]     = request.getParameterValues("PrivateWorkPoolGrid13");
	  //===================
	  String tMissionID[]     = request.getParameterValues("PrivateWorkPoolGrid15");
	  String tSubMissionID[]  = request.getParameterValues("PrivateWorkPoolGrid16");
	  String tChk[] = request.getParameterValues("InpPrivateWorkPoolGridChk");
    System.out.println("1111");
    int n = tEdorAcceptNo.length;

    System.out.println(tEdorAcceptNo);
for (int i = 0; i < n; i++)
{
    if (!tChk[i].equals("1"))
    {
        continue;
    }
    //任务信息
    tTransferData = new TransferData();
    tTransferData.setNameAndValue("MissionID", tMissionID[i]);
    tTransferData.setNameAndValue("SubMissionID", tSubMissionID[i]);
    tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo[i]);
    tTransferData.setNameAndValue("OtherNo", tOtherNo[i]);
    tTransferData.setNameAndValue("OtherNoType", tOtherNoType[i]);
    tTransferData.setNameAndValue("EdorAppName", tEdorAppName[i]);
    tTransferData.setNameAndValue("Apptype", tApptype[i]); 
    tTransferData.setNameAndValue("ManageCom", tManageCom[i]);
    tTransferData.setNameAndValue("AppntName", tAppntName[i]);
    tTransferData.setNameAndValue("PaytoDate", tPaytoDate[i]);
        
    //
    tLPEdorAppSchema = new LPEdorAppSchema();
    tLPEdorAppSchema.setEdorAcceptNo(tEdorAcceptNo[i]); 

 		// ExeSQL yExeSQL = new ExeSQL();
   // SSRS ySSRS = new SSRS();
   // String sqlstr="select activityid from lwactivity where functionid='10020033'";
   // ySSRS = yExeSQL.execSQL(sqlstr);
   String xActivityID="";
   // if(ySSRS.MaxRow==0)
   // {}
   // else
   // {
   //     xActivityID = ySSRS.GetText(1,1);
  	//}
  	xActivityID =  request.getParameter("ActivityID");
  	tTransferData.setNameAndValue("ActivityID",xActivityID);
  	System.out.println(xActivityID);
  	
  	tTransferData.setNameAndValue("BusiType", "1002");
    VData tVData = new VData();

    String busiName="WorkFlowUI";
   //  String busiName="tWorkFlowUI";
     /**
     tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"create",busiName))
     {    
         if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
         { 
		          Content = "初次核保失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
		          FlagStr = "Fail";
		      }
		
	  }
	  else
	  {
          TransferData mNTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
              
          String tUWState = (String)mNTransferData.getValueByName("PreUWFlag");
          String tPreUWGrade = (String) mNTransferData.getValueByName("PreUWGrade");
 
          if (!(tUWState.equals("9")))
          {
              Content = "受理号为"+ tEdorAcceptNo[i] + "的保全作业需要审批!";
              FlagStr = "Fail";
              continue;
          }   
          else
          {
              Content ="初次核保成功！";
              FlagStr = "Succ";               
          }                              
	  }	 			
	  **/		
		/**
 
    if ("Succ".equals(FlagStr))
    {
  */
 
        	//需要重新查询自动核保节点的子任务ID
		String xMissionID = request.getParameter("MissionID");
		String sql = " select submissionid from lwmission " +
		              " where activityid = '"+xActivityID+"' " +
		              " and missionid = '" + xMissionID + 
		              "'";
		System.out.println(xMissionID);
		String sNewSubMissionID ="";
         tTransferData.setNameAndValue("SQL", sql);
         tVData = new VData();
         tVData.add(tTransferData);
         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
         if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
         {
             sNewSubMissionID=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
         }		   		      
		      
		     tTransferData.removeByName("SubMissionID");
			   tTransferData.setNameAndValue("SubMissionID", sNewSubMissionID);		 


  			tTransferData.setNameAndValue("ActivityID",xActivityID);
  		  tTransferData.setNameAndValue("BusiType", "1002");
         tVData.clear();
         tVData.addElement(tLPEdorAppSchema); 
         tVData.addElement(tTransferData);           
         tVData.addElement(tG); 

         //String busiName="EdorWorkFlowUI";
         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
         if(!tBusinessDelegate.submitData(tVData,"create",busiName))
         {    
             if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
             { 
		              Content = "保全申请"+ tEdorAcceptNo[i] + "初次核保成功，但是自动核保失败，原因是:" +tBusinessDelegate.getCErrors().getFirstError();
		              FlagStr = "Fail";
		              continue;
		          }
		          else
		          {
		              Content = "自动核保失败";
		              FlagStr = "Fail";				
		          }		
	       }
	       else
	       {
 
                 TransferData mNTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
                    
                 String tUWFlag = (String)mNTransferData.getValueByName("UWFlag");
                     
                 if (!"9".equals(tUWFlag))
                 {
                     Content = "通过初次自动核保，但是没有通过自动核保,需要进行人工核保";
                     FlagStr = "Succ";
                 }
                 else
                 {
                     Content = "通过初次自动核保，自动核保";
                     FlagStr = "Succ";
                 }
         }
            
    
}   

%>
   
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 