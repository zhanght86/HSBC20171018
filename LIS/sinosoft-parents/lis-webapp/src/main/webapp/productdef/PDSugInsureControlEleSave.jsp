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
 String tRiskCode = "";
 
 TransferData transferData = new TransferData();
 Hashtable tHashtable = new Hashtable();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 tRiskCode=request.getParameter("RiskCode");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 
 
 String tCONTROLCODE =  request.getParameter("CONTROLCODE");
 String tCONTROLVALUE =  request.getParameter("CONTROLVALUE");
 String tFUNCTIONTYPE =  request.getParameter("FUNCTIONTYPE");
 String tRELACODE =  request.getParameter("RELACODE");
 String tRELASHOWFLAG =  request.getParameter("RELASHOWFLAG");
 String tRELAVALUESQL =  request.getParameter("RELAVALUESQL");
 String tRELASHOWVALUE =  request.getParameter("RELASHOWVALUE");

 transferData.setNameAndValue("CONTROLCODE",tCONTROLCODE);
 transferData.setNameAndValue("CONTROLVALUE",tCONTROLVALUE);
 transferData.setNameAndValue("FUNCTIONTYPE",tFUNCTIONTYPE);
 transferData.setNameAndValue("RELACODE",tRELACODE);
 transferData.setNameAndValue("RELASHOWFLAG",tRELASHOWFLAG);
 transferData.setNameAndValue("RELAVALUESQL",tRELAVALUESQL);
 transferData.setNameAndValue("RELASHOWVALUE",tRELASHOWVALUE);
 transferData.setNameAndValue("tableName",request.getParameter("tableName"));
 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));

 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDSugInsureControlEleBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
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
	
	if('<%=FlagStr%>'=='Fail'){
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + encodeURI(encodeURI('<%=Content%>')) ;  
    	top.fraInterface.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    	parent.fraInterface.unLockPage();
	}else{
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + encodeURI(encodeURI('<%=Content%>')) ;  
    	top.fraInterface.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  
		top.fraInterface.refresh_tab();
	}
	
</script>
</html>


