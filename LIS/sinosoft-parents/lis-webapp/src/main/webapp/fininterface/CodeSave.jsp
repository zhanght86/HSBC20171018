<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CodeInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:43
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //������Ϣ������У�鴦��
  //�������
  FICodeTransSchema tFICodeTransSchema   = null;
  VData tVData = new VData();
  FICodeTransSet tFICodeTransSet = new FICodeTransSet();
  String CodeGridNo[] = request.getParameterValues("CodeGridNo");  //�õ�����е�����ֵ
  String tCodeType[] = request.getParameterValues("CodeGrid1");
  String tCode[] = request.getParameterValues("CodeGrid2");
  String tCodeName[] = request.getParameterValues("CodeGrid3");
  String tCodeAlias[] = request.getParameterValues("CodeGrid4");
  String tOtherSign[] = request.getParameterValues("CodeGrid5");

  int  lineCount = CodeGridNo.length; //�õ����ܵ��ļ�¼��

  //CodeUI tCodeUI   = new CodeUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
	
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
  loggerDebug("CodeSave","------transact:"+transact);
  loggerDebug("CodeSave","------lineCount:"+lineCount);
  
  try
  {
	  	for(int i=0; i<lineCount; i++)
	  	{
	  		tFICodeTransSchema   = new FICodeTransSchema();
		    tFICodeTransSchema.setCodeType(tCodeType[i]);
		    tFICodeTransSchema.setCode(tCode[i]);
		    tFICodeTransSchema.setCodeName(tCodeName[i]);
		    tFICodeTransSchema.setCodeAlias(tCodeAlias[i]);
		    tFICodeTransSchema.setOtherSign(tOtherSign[i]);
		    tFICodeTransSet.add(tFICodeTransSchema);
	    }
	 tVData.addElement(tFICodeTransSet);  
	 loggerDebug("CodeSave","------setCodeType:"+tFICodeTransSchema.getCodeType());
	 loggerDebug("CodeSave","------setCode:"+tFICodeTransSchema.getCode());
	 loggerDebug("CodeSave","------setCodeName:"+tFICodeTransSchema.getCodeName());
	 loggerDebug("CodeSave","------setCodeAlias:"+tFICodeTransSchema.getCodeAlias());
	 loggerDebug("CodeSave","------setOtherSign:"+tFICodeTransSchema.getOtherSign());
  // ׼���������� VData  	
   uiBusinessDelegate.submitData(tVData,transact,"CodeUI");

  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
    ex.printStackTrace();
  }
  
//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
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
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

