<%@include file="../i18n/language.jsp"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDSugIncomeDataAlgSave.jsp
//�����ܣ������㷨����
//�������ڣ�2011-10-24
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.sinosoft.service.*" %>  
<%@page import="org.codehaus.xfire.addressing.RandomGUID" %>  
  
<%
 //������Ϣ������У�鴦��
 //�������

 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 String tRiskCode=null;
 
 Hashtable tHashtable = new Hashtable();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 tRiskCode=request.getParameter("RiskCode");
 
 VData tVData = new VData();
 PD_LMTabHeadRelaSet pD_LMTabHeadRelaSet = new PD_LMTabHeadRelaSet();
 String tTabHeadConfNum[] = request.getParameterValues("SaveTabHeadConfGridNo");
 String tTabHeadConfId[] = request.getParameterValues("SaveTabHeadConfGrid1");
 for(int i=0;i<tTabHeadConfNum.length;i++){
	 PD_LMTabHeadRelaSchema pD_LMTabHeadRelaSchema = new PD_LMTabHeadRelaSchema();
	 pD_LMTabHeadRelaSchema.setRiskCode(tRiskCode);
	 pD_LMTabHeadRelaSchema.setHeadID(tTabHeadConfId[i]);
	 pD_LMTabHeadRelaSchema.setType(request.getParameter("TYPE"));
	 pD_LMTabHeadRelaSet.add(pD_LMTabHeadRelaSchema);
 }
 try
 {

  tVData.add(tG);
  tVData.add(pD_LMTabHeadRelaSet);
  String busiName="PDSugTabHeadConfBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if (!tBusinessDelegate.submitData(tVData, "UPDATE",busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"����ʧ�ܣ�ԭ����:"+"" + (String)rVData.get(0);
  	  FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"����ɹ�!"+" ";
  	  FlagStr = "Succ";

  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


>","<%=Content%>");
</script>
</html>


