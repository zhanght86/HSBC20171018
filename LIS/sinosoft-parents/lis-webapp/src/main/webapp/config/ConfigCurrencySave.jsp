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
  LDCurrencySchema tLDCurrencySchema   = new LDCurrencySchema();
  LDCurrencySet tLDCurrencySet = new LDCurrencySet();
  //�������
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
  String busiName="LDCurrencyConfigUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  String tCurrencyName[]  = request.getParameterValues("CurrencyGrid1");
  String tCurrencyEn[]     = request.getParameterValues("CurrencyGrid2");
  String tCurrencyCode[] = request.getParameterValues("CurrencyGrid3");
  String tValidFlag[] = request.getParameterValues("CurrencyGrid4");
  for(int i=0;i<tCurrencyName.length;i++)
  {
	  String tempCurrencyName = tCurrencyName[i];
	  String tempCurrencyEn = tCurrencyEn[i];
	  String tempCurrencyCode = tCurrencyCode[i];
	  String tempValidFlag = tValidFlag[i];
	  
	  if(tempCurrencyName!=null&&!tempCurrencyName.equals("")
			  &&tempCurrencyEn!=null&&!tempCurrencyEn.equals("")
			  &&tempCurrencyCode!=null&&!tempCurrencyCode.equals("")
			  &&tempValidFlag!=null&&!tempValidFlag.equals(""))
	  {
		  tLDCurrencySchema   = new LDCurrencySchema();
		  tLDCurrencySchema.setCodeAlias(tempCurrencyEn);
		  tLDCurrencySchema.setCurrName(tempCurrencyName);
		  tLDCurrencySchema.setCurrCode(tempCurrencyCode);
		  tLDCurrencySchema.setValidation(tempValidFlag);
		  
		  tLDCurrencySet.add(tLDCurrencySchema);
	  }
  }
 // TransferData tTransferData = new TransferData();
 // tTransferData.setNameAndValue("CodeType","currencyprecision");
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
  try
  {
  // ׼���������� VData
  	VData tVData = new VData();
	tVData.add(tLDCurrencySet);
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
