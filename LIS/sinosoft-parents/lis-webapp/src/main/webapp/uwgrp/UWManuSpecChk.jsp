
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
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.service.*" %>
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
	
	loggerDebug("UWManuSpecChk","PrtNo:"+tPrtNo);
	loggerDebug("UWManuSpecChk","ContNo:"+tContNo);
	loggerDebug("UWManuSpecChk","remark:"+tRemark);
	loggerDebug("UWManuSpecChk","PolNo:"+tPolNo);
	if (tContNo == "" || (tRemark == "" ) )
	{
		Content = "��¼�������ر�Լ����Ϣ��������ע��Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{     
	      if(tContNo != null && tPrtNo != null && tMissionID != null && tSubMissionID != null)
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
		tVData.add(tPolNo);
		tVData.add( tG );
		
		// ���ݴ���
		String busiName="cbcheckgrpUWSpecUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//UWSpecUI tUWSpecUI   = new UWSpecUI();
		if (!tBusinessDelegate.submitData(tVData,"",busiName))
		  {     
		        
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			Content = " �˱���Լʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
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

