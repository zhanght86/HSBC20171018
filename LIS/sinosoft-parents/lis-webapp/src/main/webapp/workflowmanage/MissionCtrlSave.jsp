<%@include file="../common/jsp/UsrCheck.jsp" %>
<%
    /**
     * Created by IntelliJ IDEA.
     * User: jinsh
     * Date: 2009-1-7
     * Time: 15:32:15
     * To change this template use File | Settings | File Templates.
     */
%><!--�û�У����-->
<%@page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
    TransferData tTransferData = new TransferData();
    /**
     * ��ǰ̨��ȡһЩҵ���ֶ�
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
     * ��ȡҳ�� session
     * **/
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    /**
     * ��ʼ����ʼʵ��,���������ݵķ�װ,�����е���
     * */
 
    VData mVData = new VData();
    mVData.add(tG);
    mVData.add(tTransferData);
    /**
     * ������ɺ��ҳ����н���
     * */
    String FlagStr = "Succ";
    String Content = "";
  BusinessDelegate tBusinessDelegate; 
  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(mVData, OperFlag, "MissionCtrlUI"))
  {
	  Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getFirstError();
	 	FlagStr = "Fail";
	}
	else
	{
		Content = " �����ɹ�! ";
	  FlagStr = "Succ";
  }  

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
