<%
//�������ƣ�GEdorAppSave.jsp
//�����ܣ��ŵ���ȫ����
//�������ڣ�2005-08-16 16:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//			zhangtao	2005-04-28
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	String busiName="EdorWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����     
     
    LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema(); 
    tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo")); //��ȫ�����
    tLPEdorAppSchema.setOtherNo(request.getParameter("ContNoApp")); //������� ���屣��
    tLPEdorAppSchema.setPostalAddress(request.getParameter("OtherNo")); //���ø��ֶα����ҵ������
    tLPEdorAppSchema.setOtherNoType(request.getParameter("OtherNoType")); //�����������
    tLPEdorAppSchema.setEdorAppName(request.getParameter("EdorAppName")); //����������
    tLPEdorAppSchema.setAppType(request.getParameter("AppType")); //���뷽ʽ    
	tLPEdorAppSchema.setEdorAppDate(request.getParameter("EdorAppDate")); //������������
	tLPEdorAppSchema.setBankCode(request.getParameter("BankCode"));
	tLPEdorAppSchema.setBankAccNo(request.getParameter("BankAccNo"));
	tLPEdorAppSchema.setAccName(request.getParameter("AccName"));
	
	tLPEdorAppSchema.setPayGetName(request.getParameter("PayGetName"));  //���˷���ȡ��
	tLPEdorAppSchema.setPersonID(request.getParameter("PersonID"));  //���˷���ȡ�����֤��
	
	tLPEdorAppSchema.setManageCom(tG.ManageCom); //�������	
	tLPEdorAppSchema.setEdorState("3");

		
  /** ������� */
	CErrors tError = new CErrors();   
	String tRela  = "";                
	String FlagStr = "";
	String Content = "";
	String transact = request.getParameter("LoadFlag");
    String Result = "";
    String sActivityID = "";
    if (transact.equals("edorApp"))
    {
    	sActivityID = "0000008002";
    }
    else if (transact.equals("scanApp"))
    {
    	sActivityID = "0000008001";
    }
    else if (transact.equals("edorTest"))
    {
    	sActivityID = "0000008092";
    }
  	/**  Ϊ������[��ȫ����ȷ��]��[��ȫ����]�ڵ�׼������  */
	TransferData mTransferData = new TransferData();
	String sOtherNoType = request.getParameter("OtherNoType");
	mTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));   			
	mTransferData.setNameAndValue("MissionID", request.getParameter("MissionID"));
	mTransferData.setNameAndValue("SubMissionID", request.getParameter("SubMissionID"));	
	mTransferData.setNameAndValue("OtherNo", request.getParameter("ContNoApp"));
	mTransferData.setNameAndValue("OtherNoType", request.getParameter("OtherNoType"));
	mTransferData.setNameAndValue("EdorAppName", request.getParameter("EdorAppName"));
	mTransferData.setNameAndValue("Apptype", request.getParameter("AppType"));
	mTransferData.setNameAndValue("EdorAppDate", request.getParameter("EdorAppDate"));	
	mTransferData.setNameAndValue("ManageCom",tG.ManageCom );          
	mTransferData.setNameAndValue("EdorState","3");	
	mTransferData.setNameAndValue("NodeID",sActivityID);		
	mTransferData.setNameAndValue("Transact","INSERT||EDORAPP");
	if (sOtherNoType != null && sOtherNoType.trim().equals("4"))
	{
		mTransferData.setNameAndValue("AppntName", request.getParameter("GrpName"));
		mTransferData.setNameAndValue("PaytoDate", "");
	}
	else
	{
		mTransferData.setNameAndValue("AppntName", request.getParameter("GrpName2"));
		mTransferData.setNameAndValue("PaytoDate", "");
	}
	VData tVData = new VData();       

	
	
	try
	{		   
		tVData.addElement(tG);
		tVData.addElement(tLPEdorAppSchema);
		tVData.add(mTransferData);
		tBusinessDelegate.submitData(tVData, sActivityID,busiName);
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
				Content ="����ɹ���";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

