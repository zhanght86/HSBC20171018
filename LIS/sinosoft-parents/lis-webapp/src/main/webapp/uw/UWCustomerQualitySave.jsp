<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�UWCustomerQualitySave.jsp
//�����ܣ�
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��    ������      ��������      ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%
  loggerDebug("UWCustomerQualitySave","\n\n---UWCustomerQualitySave Start---");
  loggerDebug("UWCustomerQualitySave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("UWCustomerQualitySave","ContNo:" + request.getParameter("ContNo"));
//  loggerDebug("UWCustomerQualitySave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LACustomerQualityRecordSchema tLACustomerQualityRecordSchema = new LACustomerQualityRecordSchema();
  
  String tCustomerNo = request.getParameter("CustomerNo");
  String tContNo = request.getParameter("ContNo");
  String tName =request.getParameter("Name");
  String tBirthday = request.getParameter("Birthday");
  String tSex = request.getParameter("Sex");
  String tIDType = request.getParameter("IDType");
  String tIDNumber = request.getParameter("IDNumber");
  String tQualityFlag = request.getParameter("QualityFlag");
  String tRemark = request.getParameter("Remark");
  
  tLACustomerQualityRecordSchema.setCustomerNo(tCustomerNo);
  tLACustomerQualityRecordSchema.setContNo(tContNo);
  tLACustomerQualityRecordSchema.setName(tName);
  tLACustomerQualityRecordSchema.setBirthday(tBirthday);
  tLACustomerQualityRecordSchema.setSex(tSex);
  tLACustomerQualityRecordSchema.setIDType(tIDType);
  tLACustomerQualityRecordSchema.setIDNo(tIDNumber);
  tLACustomerQualityRecordSchema.setQualityFlag(tQualityFlag);
  tLACustomerQualityRecordSchema.setRemark(tRemark);
  tLACustomerQualityRecordSchema.setOperator(tGlobalInput.Operator);
  
  loggerDebug("UWCustomerQualitySave","tCustomerNo:"+tCustomerNo);
  loggerDebug("UWCustomerQualitySave","tContNo:"+tContNo);
  loggerDebug("UWCustomerQualitySave","tName:"+tName);
  loggerDebug("UWCustomerQualitySave","tBirthday:"+tBirthday);
  loggerDebug("UWCustomerQualitySave","tSex:"+tSex);
  loggerDebug("UWCustomerQualitySave","tIDType:"+tIDType);
  loggerDebug("UWCustomerQualitySave","tIDNumber:"+tIDNumber);
  loggerDebug("UWCustomerQualitySave","tQualityFlag:"+tQualityFlag);
  loggerDebug("UWCustomerQualitySave","tRemark:"+tRemark);
  
  VData inVData = new VData();
  inVData.add(tLACustomerQualityRecordSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  /*UWCustomerQualityRecordUI UWCustomerQualityRecordUI = new UWCustomerQualityRecordUI();

  if (!UWCustomerQualityRecordUI.submitData(inVData, "INSERT")) {
    VData rVData = UWCustomerQualityRecordUI.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }*/
  String busiName="UWCustomerQualityRecordUI";
  String mDescType="����";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(inVData,"INSERT",busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
				Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
		   }
		   else
		   {
				Content = mDescType+"ʧ��";
				FlagStr = "Fail";				
		   }
	  }
	  else
	  {
	     	Content = mDescType+"�ɹ�! ";
	      	FlagStr = "Succ";  
	  }

	loggerDebug("UWCustomerQualitySave",Content + "\n" + FlagStr + "\n---UWCustomerQualitySave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

