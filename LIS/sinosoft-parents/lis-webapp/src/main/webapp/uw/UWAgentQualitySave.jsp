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
  loggerDebug("UWAgentQualitySave","\n\n---UWCustomerQualitySave Start---");
  loggerDebug("UWAgentQualitySave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("UWAgentQualitySave","ContNo:" + request.getParameter("ContNo"));
//  loggerDebug("UWAgentQualitySave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LAAgentQualityRecordSchema tLAAgentQualityRecordSchema = new LAAgentQualityRecordSchema();
  
  String tAgentCode = request.getParameter("agent");
  String tName = request.getParameter("Name");
  String tManageCom = request.getParameter("ManageCom");
  String tEmployDate = request.getParameter("EmployDate");
  String tSex = request.getParameter("Sex");
  String tIDType = request.getParameter("IDType");
  String tIDNumber = request.getParameter("IDNumber");
  String tQualityFlag = request.getParameter("QualityFlag");
  String tUnusualType = request.getParameter("UnusualType");
  String tRemark = request.getParameter("Remark");
  String tContNo = request.getParameter("ContNo");
  
  tLAAgentQualityRecordSchema.setAgentCode(tAgentCode);
  tLAAgentQualityRecordSchema.setName(tName);
  tLAAgentQualityRecordSchema.setManageCom(tManageCom);
  tLAAgentQualityRecordSchema.setEmployDate(tEmployDate);
  tLAAgentQualityRecordSchema.setSex(tSex);
  tLAAgentQualityRecordSchema.setIDType(tIDType);
  tLAAgentQualityRecordSchema.setIDNo(tIDNumber);
  tLAAgentQualityRecordSchema.setQualityFlag(tQualityFlag);
  tLAAgentQualityRecordSchema.setUnusualType(tUnusualType);
  tLAAgentQualityRecordSchema.setRemark(tRemark);
  tLAAgentQualityRecordSchema.setContNo(tContNo);
  tLAAgentQualityRecordSchema.setOperator(tGlobalInput.Operator);
  
  loggerDebug("UWAgentQualitySave","tAgentCode:"+tAgentCode);
  loggerDebug("UWAgentQualitySave","tName:"+tName);
  loggerDebug("UWAgentQualitySave","tManageCom:"+tManageCom);
  loggerDebug("UWAgentQualitySave","tEmployDate:"+tEmployDate);
  loggerDebug("UWAgentQualitySave","tSex:"+tSex);
  loggerDebug("UWAgentQualitySave","tIDType:"+tIDType);
  loggerDebug("UWAgentQualitySave","tIDNumber:"+tIDNumber);
  loggerDebug("UWAgentQualitySave","tQualityFlag:"+tQualityFlag);
  loggerDebug("UWAgentQualitySave","tUnusualType:"+tUnusualType);
  loggerDebug("UWAgentQualitySave","tRemark:"+tRemark);
  loggerDebug("UWAgentQualitySave","tContNo:"+tContNo);

  VData inVData = new VData();
  inVData.add(tLAAgentQualityRecordSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  /*UWAgentQualityRecordUI tUWAgentQualityRecordUI = new UWAgentQualityRecordUI();

  if (!tUWAgentQualityRecordUI.submitData(inVData, "INSERT")) {
    VData rVData = tUWAgentQualityRecordUI.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }*/
  String busiName="UWAgentQualityRecordUI";
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

	loggerDebug("UWAgentQualitySave",Content + "\n" + FlagStr + "\n---UWAgentQualitySave End---\n\n");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

