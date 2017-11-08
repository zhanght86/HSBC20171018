<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
//程序名称：
//程序功能：影像复制
//创建日期：2006-4-5
//创建人  ：梁聪
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.service.*" %>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
    String tEdorAcceptNo    = request.getParameter("EdorAcceptNo"); //保全受理号
    
    String FlagStr = "Fail";
    String Content = "";
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorAcceptNo",tEdorAcceptNo);
    tTransferData.setNameAndValue("ManageCom",tGI.ManageCom);
    tTransferData.setNameAndValue("Operator",tGI.Operator);
 
    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.addElement(tTransferData);

 
    String busiName="PEdorImageCopy";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,"COPY",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "拷贝失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "拷贝失败";
				   FlagStr = "Fail";				
				}		
	 
	 }
	 else
	 {
				  Content ="拷贝成功！";
		    	FlagStr = "Succ";	
	 }	     
   
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>