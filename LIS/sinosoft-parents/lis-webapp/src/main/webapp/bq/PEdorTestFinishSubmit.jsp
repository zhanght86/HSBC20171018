<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PEdorTestFinishSubmit.jsp
//�����ܣ���ȫ������ϴ���
//�������ڣ�2005-11-10
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%> 
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.service.BusinessDelegate"%>

<%
  //������Ϣ������У�鴦��
  LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();

  //�������
  String FlagStr = "";
  String Content = "";
  GlobalInput tGI = new GlobalInput(); 
  tGI=(GlobalInput)session.getValue("GI");  
  String Operator  = tGI.Operator ;  //�����½����Ա�˺�
  String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom�ڴ��е�½��վ����
  CErrors tError = null;
  String delReason = ""; //ɾ��ԭ��
  String reasonCode = ""; //ԭ����


  TransferData tTransferData = new TransferData();
  VData tVData = new VData();

	//������ȫ����
	tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));     
	tLPEdorAppSchema.setEdorState(request.getParameter("EdorState"));
	
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");		
	
	tTransferData.setNameAndValue("MissionID",tMissionID);
	tTransferData.setNameAndValue("SubMissionID",tSubMissionID);		
	tTransferData.setNameAndValue("DelReason", delReason);
	tTransferData.setNameAndValue("ReasonCode", reasonCode);
	
	
	try
	{
    	// ׼���������� VData
    tVData.addElement(tGI);
    tVData.addElement(tLPEdorAppSchema);
		tVData.addElement(tTransferData);

 
    String busiName="EdorWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,"0000000098",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "ɾ��ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "ɾ��ʧ��";
				   FlagStr = "Fail";				
				}		
	 
	 }
	 else
	 {
				  Content ="ɾ���ɹ���";
		    	FlagStr = "Succ";	
	 }
	}
    catch(Exception ex){
		   Content = "ɾ��ʧ��";
		   FlagStr = "Fail";	
    }
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

