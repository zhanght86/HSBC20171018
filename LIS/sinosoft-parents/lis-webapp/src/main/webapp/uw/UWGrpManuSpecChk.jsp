
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpecChk.jsp
//�����ܣ��˹��˱���Լ�б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
//modify by zhangxing
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  boolean flag = true;
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null) {
		out.println("session has expired");
		return;
   } 
    //������Ϣ
  	LCSpecSchema tLCSpecSchema = new LCSpecSchema();
  	LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
  	TransferData tTransferData = new TransferData();
        String tContNo = request.getParameter("ContNo");
	
	String tRemark = request.getParameter("Remark");
	String tSpecReason = request.getParameter("SpecReason");
	String tPrtNo = request.getParameter("PrtNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	
	String tPolNo = request.getParameter("PolNo");
	String tOperatetype = request.getParameter("operate");
	String tProposalno = request.getParameter("proposalno");
	String tSerialno = request.getParameter("serialno");
	
	
	loggerDebug("UWGrpManuSpecChk","PrtNo:"+tPrtNo);
	loggerDebug("UWGrpManuSpecChk","ContNo:"+tContNo);
	loggerDebug("UWGrpManuSpecChk","remark:"+tRemark);
	loggerDebug("UWGrpManuSpecChk","PolNo:"+tPolNo);
	loggerDebug("UWGrpManuSpecChk","operate:"+tProposalno);
	if (tContNo == "" || (tRemark == "" ) )
	{
		Content = "��¼��б��ر�Լ����Ϣ��б���ע��Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{     
	      if(tContNo != null && tPrtNo != null )
	      {
		   //׼����Լ��Ϣ
		   	tLCSpecSchema.setContNo(tContNo); 
		   	//tLCSpecSchema.setSpecType("1");
		   	
		   	tLCSpecSchema.setSpecContent(tRemark);
		   	tLCSpecSchema.setSpecType("1");
		   	tLCSpecSchema.setSpecCode("1");
		   //׼����Լԭ��
		tLCUWMasterSchema.setSpecReason(tSpecReason);
				   	
                
		   } // End of if
		  else
		  {
			Content = "��������ʧ��!";
			flag = false;
		  }
	}
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tLCSpecSchema);
		tVData.add( tLCUWMasterSchema );
		TransferData mTransferData = new TransferData();
		tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("Operatetype",tOperatetype);
		tTransferData.setNameAndValue("Proposalno",tProposalno);
		tTransferData.setNameAndValue("Serialno",tSerialno);
		tVData.add(tTransferData);
		tVData.add(tPolNo);
		tVData.add( tG );
		
		// ���ݴ���
		UWSpecUI tUWSpecUI   = new UWSpecUI();
		if (!tUWSpecUI.submitData(tVData,""))
		  {     
		        
			int n = tUWSpecUI.mErrors.getErrorCount();
			Content = " �˱���Լʧ�ܣ�ԭ����: " + tUWSpecUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tUWSpecUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �����˱���Լ�ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+"��ʾ���쳣��ֹ!";
}
%>                    
                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

