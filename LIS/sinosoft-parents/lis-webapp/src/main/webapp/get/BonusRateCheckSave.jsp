<%
//�������ƣ�
//�����ܣ��ֺ���ϵ��У�����
//�������ڣ�2008-11-09 17:55:57
//������  ������ͥ

%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  String busiName="sysBonusCheckBL";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //������Ϣ������У�鴦��
  //�������
  LOBonusMainSchema tLOBonusMainSchema   = new LOBonusMainSchema();

  

  //�������
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼

  
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String mCurrentTime = PubFun.getCurrentTime();
  String mCurrentDate = PubFun.getCurrentDate();
  VData tVData = new VData();
  
  String tChecks[] = request.getParameterValues("InpLOBonusMainGridChk"); 
  String transact=request.getParameter("transact");
  loggerDebug("BonusRateCheckSave","ִ�� "+ transact + "����");
  GlobalInput tG = new GlobalInput();      
	tG = (GlobalInput)session.getValue("GI");

 
	tLOBonusMainSchema.setFiscalYear(request.getParameter("FiscalYear"));
	tLOBonusMainSchema.setGroupID("1");	
	tLOBonusMainSchema.setBonusCoefSum( request.getParameter("BonusCoefSum"));
	tLOBonusMainSchema.setFiscalYear(request.getParameter("FiscalYear"));
	tLOBonusMainSchema.setDistributeRate(request.getParameter("DistributeRate"));
	tLOBonusMainSchema.setDistributeValue(request.getParameter("DistributeValue"));


    
	  try
	  {
		   tVData.add(tLOBonusMainSchema);	 
		   tVData.add(tG);  
	    
	   //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	   tBusinessDelegate.submitData(tVData,transact,busiName);
	  }
	  catch(Exception ex)
	  {
	    Content = transact+"ʧ�ܣ�ԭ����:" + ex.toString();
	    FlagStr = "Fail";
	  }
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content = " ����ɹ�";
    	FlagStr = "Succ";
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

