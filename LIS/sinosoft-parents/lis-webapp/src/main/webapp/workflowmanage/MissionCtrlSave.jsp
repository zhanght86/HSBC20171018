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
    TransferData tTransferData = new TransferData();
    /**
     * 从前台获取一些业务字段
     */
    String OperFlag = request.getParameter("OperFlag");
    if ("MODIFY".equalsIgnoreCase(OperFlag))
    {
        String tUserCode        = request.getParameter("UserCode");
        String tMissionID       = request.getParameter("MissionID");
        String tSubMissionID    = request.getParameter("SubMissionID");
        String tActivityID      = request.getParameter("ActivityID");
        tTransferData.setNameAndValue("UserCode", tUserCode);
        tTransferData.setNameAndValue("MissionID", tMissionID);
        tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
        tTransferData.setNameAndValue("ActivityID", tActivityID);
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
    String FlagStr = "Succ";
    String Content = "";
  BusinessDelegate tBusinessDelegate; 
  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(mVData, OperFlag, "MissionCtrlUI"))
  {
	  Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
	 	FlagStr = "Fail";
	}
	else
	{
		Content = " 操作成功! ";
	  FlagStr = "Succ";
  }  

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
