<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
    <%@page import="com.sinosoft.workflow.bq.*"%>
<%
//�������
CErrors tError = null;
String tRela  = "";                
String FlagStr="";
String Content = "";
String tAction = "";
String tOperate = "";
String wFlag = "";

String tCustomerIdea[] = request.getParameterValues("GrpGrid9");
String ttCustomerIdea = tCustomerIdea[0];
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
    VData tVData = new VData();
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    TransferData mTransferData = new TransferData();
	
    mTransferData.setNameAndValue("ContNo", request.getParameter("ContNo"));
    mTransferData.setNameAndValue("EdorNo", request.getParameter("EdorNo"));
    mTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
    mTransferData.setNameAndValue("ManageCom", request.getParameter("ManageCom"));
    mTransferData.setNameAndValue("ActivityID", request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("BusiType", "1002");
    mTransferData.setNameAndValue("Operator",tG.Operator);
    mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate());
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("CustomerIdea",ttCustomerIdea);
    
    loggerDebug("BQUWConfirmConfirm","InputTime="+request.getParameter("InputTime"));
    loggerDebug("BQUWConfirmConfirm","ContNo="+request.getParameter("ContNo"));
    loggerDebug("BQUWConfirmConfirm","EdorNo="+request.getParameter("EdorNo"));
    loggerDebug("BQUWConfirmConfirm","EdorType="+request.getParameter("EdorType"));
    loggerDebug("BQUWConfirmConfirm","CustomerIdea="+ttCustomerIdea);
    loggerDebug("BQUWConfirmConfirm","AgentCode="+request.getParameter("AgentCode"));
    //loggerDebug("BQUWConfirmConfirm","ManageCom="+tG.ManageCom);
    //loggerDebug("BQUWConfirmConfirm","Operator="+tG.Operator);
    loggerDebug("BQUWConfirmConfirm","MakeDate="+PubFun.getCurrentDate());
    loggerDebug("BQUWConfirmConfirm","MissionID="+request.getParameter("MissionID"));
    loggerDebug("BQUWConfirmConfirm","SubMissionID="+request.getParameter("SubMissionID"));
    
    //����ȷ�����֮��ۺϵ��˹��˱����۽ڵ�	
	  EdorManuDataDeal tEdorManuDataDeal=new EdorManuDataDeal();
	  if(!tEdorManuDataDeal.submitData(mTransferData))
	  {
		  Content = "����ʧ�ܣ�ԭ����:" + tEdorManuDataDeal.getErrors().getFirstError();
	      FlagStr = "Fail";
	  }
	  mTransferData=tEdorManuDataDeal.getTransferData();
	  
    tVData.add(mTransferData);
    tVData.add(tG);
    loggerDebug("BQUWConfirmConfirm","-------------------start workflow---------------------");
		String ErrorMessage = "";
    String busiName="WorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,"create",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
           ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
				   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
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
				  Content ="����ɹ���";
		    	FlagStr = "Succ";	
	 }	 
 loggerDebug("BQUWConfirmConfirm","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
	var contract='<%=request.getParameter("ProposalContNo")%>';
	//alert(contract);
	
parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>",contract);
</script>
</html>
