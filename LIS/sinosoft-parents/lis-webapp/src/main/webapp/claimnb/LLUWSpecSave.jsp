
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLUWSpecSave.jsp
//�����ܣ�������Լ�б�
//�������ڣ�2005-11-04 
//������  �������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.claimnb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String tOperate = "";
  String tSerialNo = "";
  boolean flag = true;
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null) {
		out.println("session has expired");
		return;
    } 
   
    //������Ϣ
  	LCUWMasterSchema tLCUWMasterSchema         = new LCUWMasterSchema();
  	LLUWSpecMasterSchema tLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
  	LLUWSpecSubSchema tLLUWSpecSubSchema       = new LLUWSpecSubSchema();
  	TransferData tTransferData                 = new TransferData();
  
      String tContNo         = request.getParameter("ContNo");
	  String tRemark         = request.getParameter("Remark");
	  String tSpecReason     = request.getParameter("SpecReason");
	  String tPrtNo          = request.getParameter("PrtNo");
	  String tMissionID      = request.getParameter("MissionID");
	  String tSubMissionID   = request.getParameter("SubMissionID");
	  String tBaNo           = request.getParameter("BatNo");
	  String tPolNo          = request.getParameter("PolNo");
	  String tClmNo          = request.getParameter("ClmNo");
	  String tProposalNo     = request.getParameter("ProposalNo");
	  tSerialNo       = request.getParameter("SerialNo");
	  tOperate               = request.getParameter("hideOperate");
	  if(tSerialNo.trim().equals("")||tSerialNo == ""||tSerialNo == null)
	  {
	      tSerialNo = "";
	  }
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
		   	    tLLUWSpecMasterSchema.setContNo(tContNo); 
		        tLLUWSpecMasterSchema.setProposalContNo(tProposalNo); 
                tLLUWSpecMasterSchema.setSpecContent(tRemark);
                tLLUWSpecMasterSchema.setSerialNo(tSerialNo);
                tLLUWSpecMasterSchema.setSpecType("1");
		   	    tLLUWSpecMasterSchema.setSpecCode("1");
		        //׼����Լԭ��
		        tLCUWMasterSchema.setProposalNo(tProposalNo);
		        tLCUWMasterSchema.setSpecReason(tSpecReason);
		        
		        //ͬʱд������˱��켣��Ϣ��
		        tLLUWSpecSubSchema.setContNo(tContNo);
		        tLLUWSpecSubSchema.setProposalContNo(tProposalNo);
		        tLLUWSpecSubSchema.setSpecType("1");
		        tLLUWSpecSubSchema.setSpecCode("1");
		        tLLUWSpecSubSchema.setBatNo(tBaNo);
				   	
                
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
		//׼������������Ϣ
        tTransferData.setNameAndValue("ClmNo",tClmNo);
        tTransferData.setNameAndValue("ContNo",tContNo);
        tTransferData.setNameAndValue("PolNo",tPolNo);
        tTransferData.setNameAndValue("ProposalNo",tProposalNo);
        tTransferData.setNameAndValue("SpecContent",tRemark);
        tTransferData.setNameAndValue("SerialNo",tSerialNo);
        tTransferData.setNameAndValue("BatNo",tBaNo);
        tTransferData.setNameAndValue("Operatetype",tOperate);
        // ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLLUWSpecMasterSchema );
		tVData.add( tLCUWMasterSchema );
		tVData.add( tLLUWSpecSubSchema );
		tVData.add( tTransferData);
		tVData.add( tG );

		// ���ݴ���
//		LLUWSpecUI tLLUWSpecUI   = new LLUWSpecUI();
//		if (!tLLUWSpecUI.submitData(tVData,tOperate))
//		{     
//		        
//			int n = tLLUWSpecUI.mErrors.getErrorCount();
//			Content = " �˱���Լʧ�ܣ�ԭ����: " + tLLUWSpecUI.mErrors.getError(0).errorMessage;
//			FlagStr = "Fail";
//		}
		String busiName="LLUWSpecUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "�˱���Լʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "�˱���Լʧ��";
						   FlagStr = "Fail";				
				}
		   }

		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    //tError = tLLUWSpecUI.mErrors;
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " ����˱���Լ�ɹ�! ";
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

