<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�PD_LMDutyGetClmCalSave.jsp
//�����ܣ����θ����⸶������㹫ʽ
//�������ڣ�2009-3-16
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>

<%
 //������Ϣ������У�鴦��
 //�������
 
request.setCharacterEncoding("UTF-8"); 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getValue("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 String busiName = "LDMaxNoConfigUI";
 BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
 
 String jsonStr= request.getParameter("arr");  
 String tNoCode = request.getParameter("NoCode");  
 String tShowRule = request.getParameter("ShowRule");  
 String tLimitType = request.getParameter("LimitType");  
// JSONArray tJSONArray = JSONArray.fromObject(jsonStr);
try{
  // ׼���������� VData
  VData tVData = new VData();

   tVData.add(tG);
   tVData.add(1,jsonStr);
   tVData.add(2,tNoCode);
   tVData.add(3,tShowRule);
   tVData.add(4,tLimitType);
  
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, "SaveLDMaxNoRule",busiName)) {
	  out.print("[{\"success\":false,\"msg\":\""+tBusinessDelegate.getCErrors().getError(0).errorMessage+"\"}]");
	  
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	  out.print("[{\"success\":true,\"msg\":\"success\"}]");
  }
  }
 catch(Exception ex)
 {
 }
 
%>                      


