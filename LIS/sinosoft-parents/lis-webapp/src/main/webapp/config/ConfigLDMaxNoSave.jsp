<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LDCode1Save.jsp
//�����ܣ�
//�������ڣ�2005-01-26 11:24:08
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.config.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������

  //�������
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
  String busiName="LDMaxNoConfigUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

  String NoCode  = request.getParameter("NoCode");
  String NoName  = request.getParameter("NoName");
  String ShowRule  = request.getParameter("ShowRule");
  String StartDate  = request.getParameter("StartDate");
  String EndDate  = request.getParameter("EndDate");
  
  LDMaxNoConfigSet tLDMaxNoConfigSet = new LDMaxNoConfigSet();
  LDMaxNoConfigSchema tLDMaxNoConfigSchema = new LDMaxNoConfigSchema();
  tLDMaxNoConfigSchema.setNoCode(NoCode);
  tLDMaxNoConfigSchema.setNoName(NoName);
  tLDMaxNoConfigSchema.setShowRule(ShowRule);
  tLDMaxNoConfigSchema.setStartDate(StartDate);
  tLDMaxNoConfigSchema.setEndDate(EndDate);
  tLDMaxNoConfigSet.add(tLDMaxNoConfigSchema);

  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
  try
  {
  // ׼���������� VData
  	VData tVData = new VData();
	tVData.add(tLDMaxNoConfigSet);
  	tVData.add(tG);
  //	tVData.add(tTransferData);
    
    if( tBusinessDelegate.submitData( tVData, transact,busiName ) == false )                       
	{                                                                               
		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
		
	}
	else
	{
		Content = " �����ɹ�! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = tBusinessDelegate.getResult();
	}
		
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  
//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
    	Content = " ����ɹ�! ";
    	FlagStr = "Success";
    }
    else                                                                           
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  
  //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
