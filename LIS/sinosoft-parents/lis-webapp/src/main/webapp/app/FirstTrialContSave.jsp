<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupPolInput.jsp
//�����ܣ�
//�������ڣ�2002-08-15 11:48:43
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	//�������
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr="";
	String Content = "";
	String tAction = "";
	String tOperate = "INSERT||CONT";

  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  String Stype = request.getParameter("stype");
  if(Stype.equals("1"))
  {
	  LCContSchema tLCContSchema = new LCContSchema();
	  LCAppntSchema tLCAppntSchema = new LCAppntSchema();
	  LDPersonSet tLDPersonSet = new LDPersonSet();
	  LDPersonSchema tLDPersonSchema = new LDPersonSchema();
	  LCAddressSchema tLCAddressSchema = new LCAddressSchema();
	  LCAccountSchema tLCAccountSchema = new LCAccountSchema();
	  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
       
	 tLCAppntSchema.setAppntNo(request.getParameter("AppntNo"));       
   tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              
   tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));               
   tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));          
   tLCAppntSchema.setIDType(request.getParameter("AppntIDType"));            
   tLCAppntSchema.setIDNo(request.getParameter("AppntIDNo"));
    

   tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
   tLCContSchema.setManageCom(request.getParameter("ManageCom"));
   tLCContSchema.setSaleChnl(request.getParameter("SaleChnl"));
   tLCContSchema.setAgentCode(request.getParameter("AgentCode"));
   tLCContSchema.setAgentCode1(request.getParameter("AgentCode1"));
   tLCContSchema.setAgentGroup(request.getParameter("AgentGroup"));
   tLCContSchema.setRemark(request.getParameter("Remark"));
   tLCContSchema.setAgentCom(request.getParameter("AgentCom"));
   tLCContSchema.setAppntSex(request.getParameter("AppntSex"));
   tLCContSchema.setAgentType(request.getParameter("AgentType"));
   tLCContSchema.setPolType("0");
   tLCContSchema.setContType("1");          
          
  tLCAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress"));        
  tLCAppntSchema.setMarriage(request.getParameter("AppntMarriage"));          
  tLCAppntSchema.setDegree(request.getParameter("AppntDegree"));                    
  tLCAppntSchema.setOccupationType(request.getParameter("AppntOccupationType"));    
  tLCAppntSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));    
  tLCAppntSchema.setWorkType(request.getParameter("AppntWorkType"));
 	
 	VData tVData = new VData();
 					
  tVData.add( tLCCustomerImpartSet );
  tVData.add( tLCAddressSchema );                                                                                   
	tVData.add( tLCContSchema );     
	tVData.add( tLCAppntSchema );   
	tVData.add( tLCAccountSchema );                                                 
	tVData.add( tGI );                                                               
	                                                                                
	//���ݷ�SCHEMA��Ϣ                                                              
  TransferData tTransferData = new TransferData();                                                             
  tTransferData.setNameAndValue("GrpNo","0000");                                                                                 
  tVData.add(tTransferData);                                                                              
                                                                                    
	//ContUI tContUI = new ContUI();   
	String busiName="tbContUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	loggerDebug("FirstTrialContSave","before submit");                             
	if( tBusinessDelegate.submitData( tVData, tOperate,busiName ) == false )                       
	{                                                                               
		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{ 
	
	  LCContSchema mLCContSchema = new LCContSchema();
		Content = " ����ɹ�! ";
		FlagStr = "Succ";
		tVData.clear();
		tVData = tBusinessDelegate.getResult();
    mLCContSchema=(LCContSchema)tVData.getObjectByObjectName("LCContSchema",0);
	%>
	 <script>
	   parent.fraInterface.document.all("ContNo").value="<%=mLCContSchema.getContNo()%>";
	   parent.fraInterface.document.all("AppntNo").value="<%=mLCContSchema.getAppntNo()%>";
	 </script>
	<%
	}
	}else if(Stype.equals("0"))
	{
	  VData tVData = new VData();
	  TransferData tTransferData = new TransferData();                                                           
    tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));
    tTransferData.setNameAndValue("PrtNo",request.getParameter("PrtNo"));
	  tTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
	  tTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
	  tTransferData.setNameAndValue("ManageCom",request.getParameter("ManageCom"));
	  tVData.add(tTransferData);
	  tVData.add(tGI);
	  TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
	 if( tTbWorkFlowUI.submitData( tVData,"0000001061") == false )                       
	{                                                                               
		Content = " ����ʧ�ܣ�ԭ����: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{ 
	  LCContSchema mLCContSchema = new LCContSchema();
		Content = " ����ɹ�! ";
		FlagStr = "Succ";
	}
 }
loggerDebug("FirstTrialContSave","Content:"+Content);	

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

