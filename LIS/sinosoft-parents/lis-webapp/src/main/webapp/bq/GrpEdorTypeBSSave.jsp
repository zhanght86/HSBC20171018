<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GrpEdorTypeBSSave.jsp
//程序功能：
//创建日期：2006-04-10 08:49:52
//创建人  ：万泽辉
//      
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
  

<%
  	loggerDebug("GrpEdorTypeBSSave","=====This is GrpEdorTypeBSSave.jsp=====\n");
    TransferData tTransferData = new TransferData(); 
	  //PGrpEdorBSDetailBL tPGrpEdorBSDetailUI  = new PGrpEdorBSDetailBL();
	   String busiName="bqgrpPGrpEdorBSDetailBL";
       BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
       
	  LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
	  
	  //输出参数
	  String FlagStr = "";
	  String Content = "";
	  GlobalInput tGI = new GlobalInput(); //repair:
	  tGI = (GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
	  loggerDebug("GrpEdorTypeBSSave","tGI" + tGI);
	  if(tGI == null)
	  {
	    loggerDebug("GrpEdorTypeBSSave","页面失效,请重新登陆");   
	    FlagStr = "Fail";        
	    Content = "页面失效,请重新登陆";  
	  }
	  else //页面有效
	  {
	    CErrors tError = null;
	    String tBmCert = "";
	              
	  }    
    try
    {
       
             
        loggerDebug("GrpEdorTypeBSSave","tLCGrpPolSet = " + tLCGrpPolSet.size()); 
        tTransferData.setNameAndValue("EndDate",   request.getParameter("EndDate"));
        tTransferData.setNameAndValue("EdorNo",    request.getParameter("EdorNo"));
        tTransferData.setNameAndValue("EdorType",  request.getParameter("EdorType"));
        tTransferData.setNameAndValue("GrpContNo", request.getParameter("GrpContNo"));
        VData tVData = new VData();
        tVData.add(tLCGrpPolSet);
        tVData.add(tTransferData);
        tVData.add(tGI);
        String tOpertor = request.getParameter("Operator"); 
        if ( tBusinessDelegate .submitData(tVData,tOpertor,busiName))
        {
            Content = "保存成功!";
			      FlagStr = "Succ";
        }
        else
        {
		        Content = "保存失败，原因是:"+ tBusinessDelegate.getCErrors().getError(0).errorMessage;;
			      FlagStr = "Fail";
        }
     
		}
		catch(Exception ex)
    {
	      Content = "保存失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
    }
		  
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

