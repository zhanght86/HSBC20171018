<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LABranchGroupSave.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:12:33
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.wagecal.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //������Ϣ������У�鴦��
  //�������
  LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();

  //ALABranchGroupUI tLABranchGroup = new ALABranchGroupUI();

  //�������
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String tRela  = "";                
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();

	//tG.Operator = "Admin";
	//tG.ComCode  = "001";
  //session.putValue("GI",tG);

  tG=(GlobalInput)session.getValue("GI");


    tLABranchGroupSchema.setAgentGroup(request.getParameter("AgentGroup"));
    tLABranchGroupSchema.setName(request.getParameter("Name"));
    tLABranchGroupSchema.setManageCom(request.getParameter("ManageCom"));
    tLABranchGroupSchema.setUpBranch(request.getParameter("UpBranch"));
    tLABranchGroupSchema.setBranchAttr(request.getParameter("BranchAttr"));
    tLABranchGroupSchema.setBranchType(request.getParameter("BranchType"));
    loggerDebug("LABranchGroupSave","BranchType:"+request.getParameter("BranchType"));
    tLABranchGroupSchema.setBranchLevel(request.getParameter("BranchLevel"));
  //  tLABranchGroupSchema.setBranchManager(request.getParameter("BranchManager"));
 //   tLABranchGroupSchema.setBranchManagerName(request.getParameter("BranchManagerName"));
    tLABranchGroupSchema.setBranchAddressCode(request.getParameter("BranchAddressCode"));
    tLABranchGroupSchema.setBranchAddress(request.getParameter("BranchAddress"));
    tLABranchGroupSchema.setBranchPhone(request.getParameter("BranchPhone"));
    tLABranchGroupSchema.setBranchFax(request.getParameter("BranchFax"));
    tLABranchGroupSchema.setBranchZipcode(request.getParameter("BranchZipcode"));
    tLABranchGroupSchema.setFoundDate(request.getParameter("FoundDate"));
    tLABranchGroupSchema.setEndDate(request.getParameter("EndDate"));
    tLABranchGroupSchema.setEndFlag(request.getParameter("EndFlag"));
    tLABranchGroupSchema.setCertifyFlag(request.getParameter("CertifyFlag"));
    tLABranchGroupSchema.setFieldFlag(request.getParameter("FieldFlag"));
    tLABranchGroupSchema.setState(request.getParameter("State"));
    tLABranchGroupSchema.setOperator(request.getParameter("Operator"));
    tLABranchGroupSchema.setUpBranchAttr(request.getParameter("UpBranchAttr"));

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tLABranchGroupSchema);
	tVData.add(tG);
	/*
  try
  {
    loggerDebug("LABranchGroupSave","come in");
    tLABranchGroup.submitData(tVData,tOperate);
    loggerDebug("LABranchGroupSave","come out");
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
    tError = tLABranchGroup.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " ����ɹ�! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }*/
  String busiName="ALABranchGroupUI";
  String mDescType="����";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
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
  
  //��Ӹ���Ԥ����

%>                      
<html>
<script language="javascript">
        parent.fraInterface.document.all('Operator').value = "<%=tG.Operator%>";
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

