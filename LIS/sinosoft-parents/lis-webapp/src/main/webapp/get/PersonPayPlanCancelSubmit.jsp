<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�PersonPayPlanCancelSubmit.jsp
//�����ܣ�
//�������ڣ�2005-7-6 16:43
//������  ��JL
//���¼�¼��  ������    ��������     ����ԭ��/����      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.service.*"%>

<%
  //������Ϣ������У�鴦��
  //�������
                                               
  loggerDebug("PersonPayPlanCancelSubmit","==> Begin to Cancel LFGET");         
  
  LJSGetSchema tLJSGetSchema = new LJSGetSchema();
 // PersonPayPlanCancelUI tPersonPayPlanCancelUI = new PersonPayPlanCancelUI();
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  //�������
  String FlagStr = "";
  String Content = "";
  CErrors tError = null; 
  
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("PersonPayPlanCancelSubmit","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
    //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
    String transact =request.getParameter("fmtransact");
    loggerDebug("PersonPayPlanCancelSubmit","transact:"+transact);   
    
    tLJSGetSchema.setGetNoticeNo(request.getParameter("OutGetNoticeNo"));
        
    try
    {
      // ׼���������� VData
      VData tVData = new VData();
      tVData.addElement(tGI);
      tVData.addElement(tLJSGetSchema);
    
      //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
      //tPersonPayPlanCancelUI.submitData(tVData,transact);
      tBusinessDelegate.submitData(tVData,transact,"PersonPayPlanCancelUI");
    }
    catch(Exception ex)
    {
      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
      FlagStr = "Fail";
    }
  
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr=="")
    {
      //tError = tPersonPayPlanCancelUI.mErrors;
      tError = tBusinessDelegate.getCErrors();
      if (!tError.needDealError())
      {                          
        Content ="�����ɹ���";
    	FlagStr = "Succ";
      }
      else                                                                           
      {
    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
      }
    }
  } //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

