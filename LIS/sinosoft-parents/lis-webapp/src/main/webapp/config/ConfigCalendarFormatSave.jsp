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
  LDCode1Schema tLDCode1Schema   = new LDCode1Schema();
  LDCode1Set tLDCode1Set = new LDCode1Set();
  //�������
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
  String busiName="LDCode1ConfigUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  String tConutryCode[]  = request.getParameterValues("MulCalendarGrid1");
  String tCalendarFormat[]     = request.getParameterValues("MulCalendarGrid2");
  for(int i=0;i<tConutryCode.length;i++)
  {
	  String tempCountryCode = tConutryCode[i];
	  String tempCalendarFormat = tCalendarFormat[i];
	  if(tempCountryCode!=null&&!tempCountryCode.equals("")
			  &&tempCalendarFormat!=null&&!tempCalendarFormat.equals(""))
	  {
		  tLDCode1Schema   = new LDCode1Schema();
		  tLDCode1Schema.setCodeType("dateformat");
		  tLDCode1Schema.setCode(tempCalendarFormat);
		  tLDCode1Schema.setCode1(tempCountryCode);
		  
		  tLDCode1Set.add(tLDCode1Schema);
	  }
  }
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("CodeType","dateformat");
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
  try
  {
  // ׼���������� VData
  	VData tVData = new VData();
	tVData.add(tLDCode1Set);
  	tVData.add(tG);
  	tVData.add(tTransferData);
    
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
