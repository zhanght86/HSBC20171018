<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDUMSave.jsp
//�����ܣ����ֺ˱�������
//�������ڣ�2009-3-14
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
  
<%
 //������Ϣ������У�鴦��
 //�������
 

 //PDUMBL pDUMBL = new PDUMBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 String tRiskCode=null;
 
 TransferData transferData = new TransferData();
 Hashtable tHashtable = new Hashtable();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 tRiskCode=request.getParameter("RiskCode");
 
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));

 /*
 String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }

 
	document.getElementById('UWCODE').value= '';
	document.getElementById('RELAPOLTYPE').value= '';
	document.getElementById('RELAPOLTYPEName').value= '';
	document.getElementById('UWTYPE').value= '';
	document.getElementById('UWTYPEName').value= '';
	document.getElementById('UWORDER').value= '';
	document.getElementById('REMARK').value = '';
	document.getElementById('CALCODE').value = '';
 */
 
 String tUWCODE =  request.getParameter("UWCODE");
 String tRELAPOLTYPE =  request.getParameter("RELAPOLTYPE");
 String tUWTYPE =  request.getParameter("UWTYPE");
 String tUWORDER =  request.getParameter("UWORDER");
 String tREMARK =  request.getParameter("REMARK");
 String tCALCODE =  request.getParameter("CALCODE");
 //if(tCALCODE==null||"".equals(tCALCODE.trim()))
 //tCALCODE =  tUWCODE;
 String tVALIFLAG =  request.getParameter("VALIFLAG");
 String tSTANDBYFLAG1 =  request.getParameter("STANDBYFLAG1");
 String tSTANDBYFLAG2 =  request.getParameter("STANDBYFLAG2");
 String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
 
 transferData.setNameAndValue("UWCODE",tUWCODE);
 transferData.setNameAndValue("RELAPOLTYPE",tRELAPOLTYPE);
 transferData.setNameAndValue("UWTYPE",tUWTYPE);
 transferData.setNameAndValue("UWORDER",tUWORDER);
 transferData.setNameAndValue("REMARK",tREMARK);
 transferData.setNameAndValue("CALCODE",tCALCODE);
 transferData.setNameAndValue("VALIFLAG",tVALIFLAG);
 transferData.setNameAndValue("CalCodeType",tCalCodeType);

 transferData.setNameAndValue("STANDBYFLAG1",tSTANDBYFLAG1);
 transferData.setNameAndValue("STANDBYFLAG2",tSTANDBYFLAG2);
 /*ExeSQL tExeSQL = new ExeSQL();
 String tSql = "select riskname from Pd_Lmrisk where riskcode='"+tRiskCode+"'";
 SSRS tSSRS = tExeSQL.execSQL(tSql);
 if(tSSRS != null && tSSRS.getMaxRow()>0)
 {
    tHashtable.put("RiskName",tSSRS.GetText(1,1));
 }
 tHashtable.put("RiskCode",tRiskCode);
 */
//transferData.setNameAndValue("list",list);
//transferData.setNameAndValue("Hashtable",tHashtable);
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));


 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
 // pDUMBL.submitData(tVData,operator);
 String busiName="PDUMBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	  tCALCODE = (String)rVData.get(0);
    Content = " "+"����ɹ�!"+" ";
  	FlagStr = "Succ";
  	// new RiskState().setState(request.getParameter("RiskCode"), "��Լҵ�����->���ֺ˱�����", "1");

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
parent.fraInterface.setCalCode("<%=tCALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>



