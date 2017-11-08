<%@include file="../common/jsp/UsrCheck.jsp" %>
<%
/**
 * Created by IntelliJ IDEA。
 * User: jiyt
 * Date: 2012-6-25
 * Time: 9:42:15
 */
%><!--用户校验类-->
<%@page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
    BusinessDelegate tBusinessDelegate; 
    TransferData tTransferData = new TransferData();
    /**
     * 从前台获取一些字段
     */
    String OperFlag = request.getParameter("OperFlag");
    if ("Insert".equalsIgnoreCase(OperFlag) || "Update".equalsIgnoreCase(OperFlag) || "Delete".equalsIgnoreCase(OperFlag))
    {
    	String tFunctionID=request.getParameter("FunctionID");
    	String tBusiNessTable=request.getParameter("BusiNessTable");
    	String tFieldCode=request.getParameter("FieldCode");
    	String tColWidth=request.getParameter("ColWidth");
    	String tColSerialNo = request.getParameter("ColSerialNo");
    	String tIsShow = request.getParameter("IsShow");
 //   	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@  tFunctionID "+tFunctionID+"tBusiNessTable"+tBusiNessTable+"tFieldCode"+tFieldCode+"tColWidth"+tColWidth+"tColSerialNo"+tColSerialNo+"tIsShow"+tIsShow);
    	tTransferData.setNameAndValue("FunctionID", tFunctionID);
    	tTransferData.setNameAndValue("BusiNessTable", tBusiNessTable);
    	tTransferData.setNameAndValue("FieldCode", tFieldCode);
    	tTransferData.setNameAndValue("ColWidth", tColWidth);
    	tTransferData.setNameAndValue("ColSerialNo", tColSerialNo);
    	tTransferData.setNameAndValue("IsShow", tIsShow);
    }
    /**
     * 获取页面 session
     * **/
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    /**
     * 初始化后始实例,并进行数据的封装,完后进行调用
     * */
 
    VData mVData = new VData();
    mVData.add(tG);
    mVData.add(tTransferData);
    /**
     * 调用完成后和页面进行交互
     * */
    String FlagStr="";      //操作结果
    String Content = "";    //控制台信息
    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(mVData, OperFlag, "LdwfMulLineUI"))
  	{
		Content = " 保存失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
	   	FlagStr = "Fail";
	}
	else
	{
	  	Content = " 保存成功! ";
		FlagStr = "Succ";
    }

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
