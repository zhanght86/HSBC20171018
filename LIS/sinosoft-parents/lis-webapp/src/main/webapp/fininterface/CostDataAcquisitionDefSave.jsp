<%
//�������� :CostDataAcquisitionDefInput.jsp
//������ :ƾ֤�������ݲɼ�����
//������ :���
//�������� :2008-08-18
//
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("CostDataAcquisitionDefSave","1��ʼִ��Saveҳ��");
  FICostDataAcquisitionDefSchema mFICostDataAcquisitionDefSchema = new FICostDataAcquisitionDefSchema();
  //FICostDataAcquisitionDefUI mFICostDataAcquisitionDefUI = new FICostDataAcquisitionDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //����û���Ϣ
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("CostDataAcquisitionDefSave","2������������" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";

  loggerDebug("CostDataAcquisitionDefSave","3��ʼ���л�ȡ���ݵĲ���������");
  if(mOperateType.equals("INSERT||MAIN"))
  {
  	mFICostDataAcquisitionDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionID(request.getParameter("AcquisitionID"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionType(request.getParameter("AcquisitionType"));
  	mFICostDataAcquisitionDefSchema.setDataSourceType(request.getParameter("DataSourceType"));
  	mFICostDataAcquisitionDefSchema.setCostOrDataID(request.getParameter("CostOrDataID"));
  	mFICostDataAcquisitionDefSchema.setDistillMode(request.getParameter("DistillMode"));
  	mFICostDataAcquisitionDefSchema.setDistillSQL(request.getParameter("DistillSQL"));
  	mFICostDataAcquisitionDefSchema.setDistillSQLForOne(request.getParameter("DistillSQLForOne"));
  	mFICostDataAcquisitionDefSchema.setDistillClass(request.getParameter("DistillClass"));
  	mFICostDataAcquisitionDefSchema.setDistillClassForOne(request.getParameter("DistillClassForOne"));  	
  	mFICostDataAcquisitionDefSchema.setRemark(request.getParameter("Remark"));   
  	VData tVData = new VData();
  	try
  	{
  	   tVData.add(tG);
  	   tVData.addElement(mOperateType);
    	   tVData.addElement(mFICostDataAcquisitionDefSchema);
    	   uiBusinessDelegate.submitData(tVData,mOperateType,"FICostDataAcquisitionDefUI");
  	}
  	catch(Exception ex)
  	{
    	   Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    	   FlagStr = "Fail";
  	}
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
  	mFICostDataAcquisitionDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionID(request.getParameter("AcquisitionID"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionType(request.getParameter("AcquisitionType"));
  	mFICostDataAcquisitionDefSchema.setDataSourceType(request.getParameter("DataSourceType"));
  	mFICostDataAcquisitionDefSchema.setCostOrDataID(request.getParameter("CostOrDataID"));
  	mFICostDataAcquisitionDefSchema.setDistillMode(request.getParameter("DistillMode"));
  	mFICostDataAcquisitionDefSchema.setDistillSQL(request.getParameter("DistillSQL"));
  	mFICostDataAcquisitionDefSchema.setDistillSQLForOne(request.getParameter("DistillSQLForOne"));
  	mFICostDataAcquisitionDefSchema.setDistillClass(request.getParameter("DistillClass"));
  	mFICostDataAcquisitionDefSchema.setDistillClassForOne(request.getParameter("DistillClassForOne")); 
  	mFICostDataAcquisitionDefSchema.setRemark(request.getParameter("Remark"));   
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFICostDataAcquisitionDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FICostDataAcquisitionDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  
  if(mOperateType.equals("DELETE||MAIN"))
  {
  	mFICostDataAcquisitionDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionID(request.getParameter("AcquisitionID"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionType(request.getParameter("AcquisitionType"));
  	mFICostDataAcquisitionDefSchema.setDataSourceType(request.getParameter("DataSourceType"));	
  	mFICostDataAcquisitionDefSchema.setCostOrDataID(request.getParameter("CostOrDataID"));
  	mFICostDataAcquisitionDefSchema.setDistillMode(request.getParameter("DistillMode"));
  	mFICostDataAcquisitionDefSchema.setDistillSQL(request.getParameter("DistillSQL"));
  	mFICostDataAcquisitionDefSchema.setDistillSQLForOne(request.getParameter("DistillSQLForOne"));
  	mFICostDataAcquisitionDefSchema.setDistillClass(request.getParameter("DistillClass"));
  	mFICostDataAcquisitionDefSchema.setDistillClassForOne(request.getParameter("DistillClassForOne")); 
  	mFICostDataAcquisitionDefSchema.setRemark(request.getParameter("Remark"));   

  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFICostDataAcquisitionDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FICostDataAcquisitionDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
     tError = uiBusinessDelegate.getCErrors();
    	   if (!tError.needDealError())
    		{     
    				Content = "�����ѳɹ�!";
    				FlagStr = "Succ";
    		}
   	  else
    		{
    			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    			FlagStr = "Fail";
    		}
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
