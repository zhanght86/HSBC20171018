<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDBaseFieldSave.jsp
//�����ܣ�������Ϣ�ֶ�����
//�������ڣ�2009-3-17
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
 	//������Ϣ������У�鴦��
 	//�������
  PDBaseFieldBL pDBaseFieldBL = new PDBaseFieldBL();
  
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String operator = "";
  TransferData transferData = new TransferData();
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getAttribute("GI");
  
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  operator = request.getParameter("operator");
 
 	String tTableCode = request.getParameter("TableCode");//������������
 	String tTableName = request.getParameter("TableName");//��������
 	String tFieldCode = request.getParameter("FieldCode");//�����ֶ�������
 	String tFieldName = request.getParameter("FieldName");//�����ֶ��� 		
 	String tFieldType = request.getParameter("FieldType");//�ֶ���������
 	String tFieldTypeName = request.getParameter("FieldTypeName");//�ֶ�����
 	String tIsDisplayCode = request.getParameter("IsDisplayCode");//�Ƿ���ʾ
 	String tOfficialDesc = request.getParameter("OfficialDesc");//�ٷ��ֶ�����
 	String tBusiDesc = request.getParameter("BusiDesc");//ҵ���ֶα�ע
 	
	transferData.setNameAndValue("TableCode", tTableCode);
	transferData.setNameAndValue("TableName", tTableName);
	transferData.setNameAndValue("FieldCode", tFieldCode);
	transferData.setNameAndValue("FieldName", tFieldName);
	transferData.setNameAndValue("FieldType", tFieldType);
	transferData.setNameAndValue("FieldTypeName", tFieldTypeName);
	transferData.setNameAndValue("IsDisplayCode", tIsDisplayCode);
	transferData.setNameAndValue("OfficialDesc", tOfficialDesc);
	transferData.setNameAndValue("BusiDesc", tBusiDesc);	

  //String values[] = request.getParameterValues("");
  //java.util.ArrayList list = new java.util.ArrayList();
  //for(int i = 0; i < values.length; i++)
  //{
  // list.add(values[i]);
  //}
	//transferData.setNameAndValue("list",list);
	//transferData.setNameAndValue("tableName",request.getParameter("tableName"));
  try
  {
  	// ׼���������� VData
  	VData tVData = new VData();
  	tVData.add(tG);
  	tVData.add(transferData);
  	pDBaseFieldBL.submitData(tVData,operator);
  }
  catch(Exception ex)
  {
  	Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  	FlagStr = "Fail";
 	}

 	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 	if (FlagStr=="")
 	{
  	tError = pDBaseFieldBL.mErrors;
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
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

