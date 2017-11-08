<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：DelIsuredRisk.jsp
//程序功能：
//创建日期：2002-02-05 08:49:52
//创建人  ：yuanaq
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*"%>
<%

  TransferData tTransferData = new TransferData(); 
//  ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  String busiName="tContInsuredUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  loggerDebug("DelIsuredRisk","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("DelIsuredRisk","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
  CErrors tError = null;
  String tBmCert = "";
  String fmAction=request.getParameter("fmAction");
  String loadflag=request.getParameter("LoadFlag");      
         loggerDebug("DelIsuredRisk","line 43 loadflag="+loadflag);                    
        tTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo")); //保全保存标记，默认为0，标识非保全  
        tTransferData.setNameAndValue("PolNo",request.getParameter("polno"));             
        tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));          
        try
         {
            // 准备传输数据 VData
             VData tVData = new VData();
             tVData.add(tTransferData);
             tVData.add(tGI);
                 loggerDebug("DelIsuredRisk","asdasdasdasd"+fmAction);  
                 loggerDebug("DelIsuredRisk","InsuredNo=="+request.getParameter("InsuredNo"));
                 loggerDebug("DelIsuredRisk","polno=="+request.getParameter("polno")); 
                 loggerDebug("DelIsuredRisk","ContNo=="+request.getParameter("ContNo")); 
//            if ( tContInsuredUI.submitData(tVData,fmAction))
            if ( tBusinessDelegate.submitData(tVData, fmAction,busiName))
            {

                            %>
		            <%		      
		        if (fmAction.equals("DELETE||INSUREDRISK"))
		        {
		    	    
		            %>
		            <SCRIPT language="javascript">
                            parent.fraInterface.PolGrid.delRadioTrueLine("PolGrid");                    
		            </SCRIPT>
		            <%
		        }
	        }
    }
    catch(Exception ex)
    {
      Content = "保存失败，原因是:" + ex.toString();
      FlagStr = "Fail";
    }
  

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if ("".equals(FlagStr))
  {
//    tError = tContInsuredUI.mErrors;
    tError = tBusinessDelegate.getCErrors(); 
    if (!tError.needDealError())
    {                          
      Content ="险种删除成功！";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = "删除失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  loggerDebug("DelIsuredRisk","FlagStr:"+FlagStr+"Content:"+Content);
  
} //页面有效区
%>
	<%
	   loggerDebug("DelIsuredRisk",request.getParameter("LoadFlag"));
	   
	  if ("2".equals(request.getParameter("LoadFlag")))
	   {
	%>                                       
<script language="javascript">
	 parent.fraInterface.fm.action="./GrpContInsuredSave.jsp";	
    
</script>

	<%
	}
	  else
	   {
	%>
	
  <script language="javascript">
  parent.fraInterface.fm.action="./ContInsuredSave.jsp";
  </script>
  <%
	}
	%>
<html>
  	<script language="javascript">
	parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
  </script>
 </html>	
	

