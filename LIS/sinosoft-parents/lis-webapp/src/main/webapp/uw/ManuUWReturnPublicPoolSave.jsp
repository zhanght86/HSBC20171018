<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--用户校验类--%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：
//程序功能：人工核保返回到公共池
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期    更新原因/内容
//            续涛      2005-04-20  呈报
//            韩霖      2005-06-15  呈报
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%> 
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  GlobalInput tG = new GlobalInput();
  
  tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null) {
      out.println("session has expired");
      return;
  }
  
  String tMissionID = request.getParameter("MissionID");                  //工作流任务号
  String tPrtNo = request.getParameter("PrtNo");                          //印刷号
  String tSubMissionID = request.getParameter("SubMissionID");	         //工作流子任务号
           
  VData tVData = new VData();
  String tContNo = "";
  tContNo = request.getParameter("ContNo");


  try
  {
       
        FlagStr = "Succ";		
        String tUWSendFlag;
        String tUserCode;

        loggerDebug("ManuUWReturnPublicPoolSave","----------------返回公共池---开始----------------");			

        
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("ContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
                             
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        nTransferData.setNameAndValue("ActivityID","0000001100");
               
        loggerDebug("ManuUWReturnPublicPoolSave","----合同号:["+tContNo+"]");
        loggerDebug("ManuUWReturnPublicPoolSave","----任务号:["+tMissionID+"]");
        loggerDebug("ManuUWReturnPublicPoolSave","----子任务号:["+tSubMissionID+"]");
        
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tG);

        UWReturnPublicPoolUI tUWReturnPublicPoolUI = new UWReturnPublicPoolUI();
       if (tUWReturnPublicPoolUI.submitData(tVData,"0000001100") == false)
        {
          int n = tUWReturnPublicPoolUI.mErrors.getErrorCount();
          for (int i = 0; i < n; i++)
            {
                Content = "返回公共池失败，原因是：" + tUWReturnPublicPoolUI.mErrors.getError(0).errorMessage;
            }
            FlagStr = "Fail";
        }
       else
       {
		    	Content = "返回公共池操作成功!";
		    	FlagStr = "Succ";
        }
                					
        loggerDebug("ManuUWReturnPublicPoolSave","----------------返回公共池---结束----------------");
                                     
  } //END OF TRY
  catch(Exception e)
  {
	  e.printStackTrace();
	  Content = Content.trim()+".提示：异常终止!";
  }

  loggerDebug("ManuUWReturnPublicPoolSave","flag="+FlagStr);
  loggerDebug("ManuUWReturnPublicPoolSave","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterReturnApprove("<%=FlagStr%>","<%=Content%>");    
</script>
</html>
