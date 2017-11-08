<%@include file="../common/jsp/UsrCheck.jsp" %>
<%
    /**
     * Created by IntelliJ IDEA.
     * User: jinsh
     * Date: 2009-1-7
     * Time: 15:32:15
     * To change this template use File | Settings | File Templates.
     */
%><!--用户校验类-->
<%@page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
    String FlagStr="";      //操作结果
    String Content = "";    //控制台信息
    /**
     * 从前台获取一些业务字段
     */
    String OperFlag = request.getParameter("OperFlag");
    String CodeType = request.getParameter("CodeType");
    String CodeTypeName = request.getParameter("CodeTypeName");
    String Code = request.getParameter("Code");
    String CodeName = request.getParameter("CodeName");
    String CodeAlias = request.getParameter("CodeAlias");
    //add by liuyuxiao 2011-05-23
    String OtherSignCode = request.getParameter("OtherSignCode");
    /**
     * 放入TransferData往后台传递
     * **/
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("OperFlag", OperFlag);
    tTransferData.setNameAndValue("CodeType", CodeType);
    tTransferData.setNameAndValue("CodeTypeName", CodeTypeName);
    tTransferData.setNameAndValue("Code", Code);
    tTransferData.setNameAndValue("CodeName", CodeName);
    tTransferData.setNameAndValue("CodeAlias", CodeAlias);
    //add by liuyuxiao 2011-05-23
    tTransferData.setNameAndValue("OtherSignCode", OtherSignCode);
    /**
     * 获取页面 session
     * **/
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    /**
     * 初始化后始实例,并进行数据的封装,完后进行调用
     * */
     
    BusinessDelegate tBusinessDelegate; 
 
    VData mVData = new VData();
    mVData.add(tG);
    mVData.add(tTransferData);
    /**
     * 调用完成后和页面进行交互
     * */
    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(mVData, OperFlag, "BaseDataUI"))
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
