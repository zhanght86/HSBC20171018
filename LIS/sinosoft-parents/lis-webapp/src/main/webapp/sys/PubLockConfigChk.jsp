<%--
    
--%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.net.*"%>
<%@page import="com.sinosoft.service.*" %>

<%	         
	
	String FlagStr="";      //�������
	String Content = "";    //����̨��Ϣ
	String tAction = "";    //�������ͣ�delete update insert
	String tOperate = "";   //��������

	VData tData = new VData();

	
	//������������
	LockAppGroupSchema tLockAppGroupSchema = new LockAppGroupSchema();
	LockAppGroupSet tLockAppGroupSet = new LockAppGroupSet();
	//����ģ��
	LockBaseSchema tLockBaseSchema = new LockBaseSchema();
	LockBaseSet tLockBaseSet = new LockBaseSet();
	//����������ģ��
	LockGroupSchema tLockGroupSchema = new LockGroupSchema();
	LockGroupSet tLockGroupSet = new LockGroupSet();
	
	//��������
	LockConfigSchema tLockConfigSchema = new LockConfigSchema();
	LockConfigSet tLockConfigSet = new LockConfigSet();

	tAction = request.getParameter("hiddenAction");
	String tHiddenLockData = request.getParameter("hiddenLockData");
	String tHiddenLockGroup = request.getParameter("hiddenLockGroup");
	
	//��������
	tLockAppGroupSchema.setOperatedNo(tHiddenLockData);
	tLockAppGroupSchema.setLockGroup(tHiddenLockGroup);
	tLockAppGroupSet.add(tLockAppGroupSchema);
	
	String tUnLockReason = request.getParameter("UnLockReason");
	
	//����ģ�����ò���
	String tBaseModuleCode = request.getParameter("BaseModuleCode");
	String tBaseModuleName = request.getParameter("BaseModuleName");
	String tModuleDescribe = request.getParameter("ModuleDescribe");
	tLockBaseSchema.setLockModule(tBaseModuleCode);
	tLockBaseSchema.setModuleName(tBaseModuleName);
	tLockBaseSchema.setRemark(tModuleDescribe);
	tLockBaseSet.add(tLockBaseSchema);
	//������������ɾ��
	String tLockGroupID = request.getParameter("hiddenLockGroupConfig");
	String tLockGroupName = request.getParameter("LockGroupName");
	String tLockGroupDescribe = request.getParameter("LockGroupDescribe");
	tLockGroupSchema.setLockGroup(tLockGroupID);
	tLockGroupSchema.setLockGroupName(tLockGroupName);
	tLockGroupSchema.setRemark(tLockGroupDescribe);
	tLockGroupSet.add(tLockGroupSchema);
	
	
	//�������������ģ������
	String tParamValue[] = request.getParameterValues("LockGroupConfigGrid2");
	int n = 0;
	if(tParamValue != null){
		n = tParamValue.length;
	}
	for (int i = 0; i < n; i++)
	{ 
		//�жϲ�����Ϣ�Ƿ�Ϊ�գ�ʡȥɾ�����п��еı�Ҫ
		if (tParamValue[i] != null && !tParamValue[i].equals(""))
		{   
			tLockConfigSchema = new LockConfigSchema();
			tLockConfigSchema.setLockGroup(tLockGroupID);
			tLockConfigSchema.setLockModule(tParamValue[i]);
			tLockConfigSet.add(tLockConfigSchema);
		}
	}
	
	
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

	
  // ׼���������� VData
  tData.add(tUnLockReason);
	tData.add( tG );
	tData.add(tLockAppGroupSet);
	tData.add(tLockBaseSet);
	tData.add(tLockGroupSet);
	tData.add(tLockConfigSet);
//	tData.add(tLDTaskSet);
	
//	DealLockAppGroupBL tDealLockAppGroupBL = new DealLockAppGroupBL();
//	if(!tDealLockAppGroupBL.submitData(tData,tAction ))
//	{
//		Content = " ����ʧ�ܣ�ԭ����: " + tDealLockAppGroupBL.mErrors.getError(0).errorMessage;
//		FlagStr = "Fail";
//	}
	String busiName="DealLockAppGroupBL";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(tData,tAction,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "����ʧ��";
			FlagStr = "Fail";				
		}
	}

	else
	{
		Content = " �����ɹ�! ";
		FlagStr = "Succ";

		tData.clear();
	}

%>
<%		


%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
