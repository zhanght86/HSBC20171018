<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLClaimClearAccSave.jsp
//程序功能：投连结算
//创建日期：2007-09-10
//创建人  ：陈亮
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.acc.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("hideOperate");    
    if(tG == null) 
    {
        loggerDebug("LLClaimClearAccSave","登录信息没有获取!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimClearAccSave","没有获取操作符!!!");
        return;   
    }
    
    
    
    //准备数据容器信息
    String tClmNo = request.getParameter("RptNo");
             
    TransferData tTransferData = new TransferData();    
    tTransferData.setNameAndValue("ClmNo",tClmNo);      
        
    loggerDebug("LLClaimClearAccSave","LLClaimClearAccSave.jsp测试--"+tClmNo); 
  
    try
    {    
        // 准备传输数据 VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        

        String busiName="LLClaimClearAccUI";
        String mDescType="保存";
      	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      	  if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
      	  {    
      	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
      	       { 
      				Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
      				FlagStr = "FAIL";
      		   }
      		   else
      		   {
      				Content = mDescType+"失败";
      				FlagStr = "FAIL";				
      		   }
      	  }
      	  else
      	  {
      	     	Content = mDescType+"成功! ";
      	      	FlagStr = "SUCC";  
      	  }
    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterMatchDutyPay("<%=FlagStr%>","<%=Content%>");
</script>
</html>
