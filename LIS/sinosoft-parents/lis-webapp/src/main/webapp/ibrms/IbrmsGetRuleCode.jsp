<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2008-09-02 15:12:33
//������  ��dxy���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.ibrms.*"%>
  <%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
  //������Ϣ������У�鴦��
  //�������   
 
  String FlagStr = "Fail";
  String Content = "";
  String tNo = "";
  String tModulName=request.getParameter("ModulName");
  TransferData sTransferData=new TransferData();
  sTransferData.setNameAndValue("ModulName", tModulName);
  VData sVData = new VData();
  sVData.add(sTransferData);
  try
  {
    loggerDebug("IbrmsGetRuleCode","Create MaxNo:"+tModulName);
    //tNo = PubFun1.CreateRuleCalCode(tModulName);
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(tBusinessDelegate.submitData(sVData, "CreateRuleCalCode", "PubFun1UI"))
    {
    	tNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
    }   
  }
   catch(Exception ex)
  {
    Content = "����ʧ��,ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
    //request.getRequestDispatcher("RuleQuerySaveResponse.jsp?Content="+Content+"&flag="+FlagStr).forward(request,response);
%>
<html>
<script language="javascript">
	try
	{
		parent.fraInterface.afterInitRuleCode("<%=tNo%>");
	}
	catch(e)
	{
		//alert(e);
		//top.afterSubmit("<%=FlagStr%>","<%=Content%>");
	}
</script>
</html>



