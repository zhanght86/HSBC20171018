<%@include file="/i18n/language.jsp"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�RIAccFeaturesSave.jsp
//�����ܣ��ֳ����ζ���-�ֱ�����
//�������ڣ�2011/6/16
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
 //������Ϣ������У�鴦��
 
 //�������
 //��������Schema t��������Schema = new ��������Schema();
 RIAccFeaturesUI tRIAccFeaturesUI = new RIAccFeaturesUI();
 
 //�������
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String transact = "";
 
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 transact = request.getParameter("fmtransact");
 
 //��url��ȡ������������Ӧ��schema
 //t��������Schema.set��������(request.getParameter("��������"));
 
 try
 {
  //׼����������VData
  VData tVData = new VData();
  
  //����schema
  //tVData.addElement(t��������Schema);
  
  tVData.add(tG);
  tRIAccFeaturesUI.submitData(tVData,transact);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }
 
 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = tRIAccFeaturesUI.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " "+"����ɹ�!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }
 
 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

