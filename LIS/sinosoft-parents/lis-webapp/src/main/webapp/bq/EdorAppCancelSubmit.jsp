<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�AllPBqQuerySubmit.jsp
//�����ܣ�
//�������ڣ�2003-1-20
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>

<%
  //������Ϣ������У�鴦��
  //�������
  LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
//  LCPolSchema tLCPolSchema = new LCPolSchema();
  PEdorAppCancelUI tPEdorAppCancelUI = new PEdorAppCancelUI();
  
  //�������
  String FlagStr = "";
  String Content = "";
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("EdorAppCancelSubmit","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
   String Operator  = tGI.Operator ;  //�����½����Ա�˺�
   String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
  
  CErrors tError = null;
  String tBmCert = "";
  
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String transact=request.getParameter("Transact");
  loggerDebug("EdorAppCancelSubmit","transact:"+transact); 

    tLPEdorMainSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorMainSchema.setPolNo(request.getParameter("PolNo"));        
        
 try
  {
  // ׼���������� VData
   VData tVData = new VData();
   tVData.addElement(tGI);
   tVData.addElement(tLPEdorMainSchema);

    
   //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    tPEdorAppCancelUI.submitData(tVData,transact);
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tPEdorAppCancelUI.mErrors;
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

