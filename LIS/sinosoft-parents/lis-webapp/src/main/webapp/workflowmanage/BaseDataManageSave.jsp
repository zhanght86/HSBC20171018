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
    String FlagStr="";      //�������
    String Content = "";    //����̨��Ϣ
    /**
     * ��ǰ̨��ȡһЩҵ���ֶ�
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
     * ����TransferData����̨����
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
     * ��ȡҳ�� session
     * **/
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    /**
     * ��ʼ����ʼʵ��,���������ݵķ�װ,�����е���
     * */
     
    BusinessDelegate tBusinessDelegate; 
 
    VData mVData = new VData();
    mVData.add(tG);
    mVData.add(tTransferData);
    /**
     * ������ɺ��ҳ����н���
     * */
    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(mVData, OperFlag, "BaseDataUI"))
  	{
		  Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getFirstError();
	   	FlagStr = "Fail";
	  }
	  else
	  {
	  	Content = " ����ɹ�! ";
		  FlagStr = "Succ";
    }


%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
