<%
//�������ƣ�PEdorSave.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//			zhangtao	2005-04-28
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %>
    <%@page import="com.sinosoft.workflow.bq.*" %>
    <%@page import="com.sinosoft.lis.bq.*" %>
<%
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����     
     
    LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema(); 
    tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo")); //��ȫ�����
    tLPEdorAppSchema.setOtherNo(request.getParameter("OtherNo")); //�������
    tLPEdorAppSchema.setOtherNoType(request.getParameter("OtherNoType")); //�����������
    tLPEdorAppSchema.setEdorAppName(request.getParameter("EdorAppName")); //����������
    tLPEdorAppSchema.setAppType(request.getParameter("AppType")); //���뷽ʽ    
	tLPEdorAppSchema.setEdorAppDate(request.getParameter("EdorAppDate")); //���Ĺ�����������
	tLPEdorAppSchema.setBankCode(request.getParameter("BankCode"));
	tLPEdorAppSchema.setBankAccNo(request.getParameter("BankAccNo"));
	tLPEdorAppSchema.setAccName(request.getParameter("AccName"));
	
	//add by jiaqiangli 2009-01-04���Ӵ����������Ϣ
	tLPEdorAppSchema.setBehalfName(request.getParameter("BfName"));
	tLPEdorAppSchema.setBehalfIDType(request.getParameter("BfIDType"));
	tLPEdorAppSchema.setBehalfIDNo(request.getParameter("BfIDNo"));
	tLPEdorAppSchema.setBehalfPhone(request.getParameter("BfPhone"));
	tLPEdorAppSchema.setBehalfCode(request.getParameter("BfAgentCode"));
	
	//add by jiaqiangli 2009-01-04���Ӵ����������Ϣ �����ʸ���
	tLPEdorAppSchema.setEdorAppPhone(request.getParameter("CustomerPhone"));
	tLPEdorAppSchema.setBehalfCodeCom(request.getParameter("ManageCom"));
	tLPEdorAppSchema.setSwitchChnlType(request.getParameter("InternalSwitchChnl"));
	tLPEdorAppSchema.setSwitchChnlName(request.getParameter("InternalSwitchChnlName"));
	//add by jiaqiangli 2009-01-04���Ӵ����������Ϣ
	
	tLPEdorAppSchema.setPayGetName(request.getParameter("PayGetName"));  //���˷���ȡ��
	tLPEdorAppSchema.setPersonID(request.getParameter("PersonID"));  //���˷���ȡ�����֤��
	
	//add by sunsx 2010-04-27 ���������ʸ���������Ϣ
	tLPEdorAppSchema.setPhone(request.getParameter("Mobile"));
	tLPEdorAppSchema.setPostalAddress(request.getParameter("PostalAddress"));
	tLPEdorAppSchema.setZipCode(request.getParameter("ZipCode"));
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
  ExeSQL yExeSQL = new ExeSQL();
  SSRS ySSRS = new SSRS();
  /**zhangfh**/
  if (transact.equals("edorApp"))
  {
    	sActivityID = request.getParameter("ActivityID");
   		
    	
  }
  else if (transact.equals("scanApp"))
  {
  		sActivityID = request.getParameter("ActivityID");
  }
  else if (transact.equals("edorTest"))
  {
  		sActivityID = request.getParameter("ActivityID");
  }
  	/**  Ϊ������[��ȫ����ȷ��]��[��ȫ����]�ڵ�׼������  */
	TransferData mTransferData = new TransferData();
	String sOtherNoType = request.getParameter("OtherNoType");
	mTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));   			
	mTransferData.setNameAndValue("MissionID", request.getParameter("MissionID"));
	mTransferData.setNameAndValue("SubMissionID", request.getParameter("SubMissionID"));	
	mTransferData.setNameAndValue("ActivityID", sActivityID);
    mTransferData.setNameAndValue("BusiType", "1002");
	mTransferData.setNameAndValue("OtherNo", request.getParameter("OtherNo"));
	mTransferData.setNameAndValue("OtherNoType", request.getParameter("OtherNoType"));
	mTransferData.setNameAndValue("DefaultOperator", tG.Operator);
	mTransferData.setNameAndValue("ICFlag", "");
	mTransferData.setNameAndValue("EdorAppName", request.getParameter("EdorAppName"));
	mTransferData.setNameAndValue("Apptype", request.getParameter("AppType"));
	mTransferData.setNameAndValue("EdorAppDate", request.getParameter("EdorAppDate"));	
	mTransferData.setNameAndValue("ManageCom",tG.ManageCom );          
	mTransferData.setNameAndValue("EdorState","3");	
	mTransferData.setNameAndValue("NodeID",sActivityID);		
	mTransferData.setNameAndValue("Transact","INSERT||EDORAPP");
	if (sOtherNoType != null && sOtherNoType.trim().equals("3"))
	{
		mTransferData.setNameAndValue("AppntName", request.getParameter("AppntName"));
		mTransferData.setNameAndValue("PaytoDate", request.getParameter("MainPolPaytoDate"));
	}
	else
	{
		mTransferData.setNameAndValue("AppntName", "");
		mTransferData.setNameAndValue("PaytoDate", "");
	}
	VData tVData = new VData();       
	

		tVData.addElement(tG);
		tVData.addElement(tLPEdorAppSchema);
		tVData.add(mTransferData);
		String ErrorMessage = "";
   // String busiName="tWorkFlowUI";
    // String busiName="WorkFlowUI";
    PEdorAppBeforeEndService tPEdorAppBeforeEndService=new PEdorAppBeforeEndService();
    if(!tPEdorAppBeforeEndService.submitData(tVData,"")){
    	Content = "����ʧ�ܣ�ԭ����:" +tPEdorAppBeforeEndService.mErrors.getFirstError();
		FlagStr = "Fail";
		System.out.println("error="+Content);
    }
    else{
	    VData qVData=tPEdorAppBeforeEndService.getResult();
	    MMap pedorMap =(MMap) qVData.getObjectByObjectName("MMap",0);
	    
	    PEdorAppAfterEndService tPEdorAppAfterEndService=new PEdorAppAfterEndService();
	    if(!tPEdorAppAfterEndService.submitData(tVData,"")){
	    	Content = "����ʧ�ܣ�ԭ����:" +tPEdorAppAfterEndService.mErrors.getFirstError();
			FlagStr = "Fail";
			System.out.println("error="+Content);
	    } else{
		    VData kVData=tPEdorAppAfterEndService.getResult();
		    MMap appMap =(MMap) kVData.getObjectByObjectName("MMap",0);
		    pedorMap.add(appMap);
		    
		    VData tResult=new VData();
		    tResult.add(pedorMap);
		    PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tResult, "")) {
				// @@������
				//this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "tPubSubmit";
				// tError.functionName = "submitData";
				// tError.errorMessage = "�����ύʧ��!";
				// this.mErrors.addOneError(tError);
				Content = "����ʧ�ܣ�ԭ����:" +tPubSubmit.mErrors.getFirstError();
				FlagStr = "Fail";
				System.out.println("error="+Content);
				//return false;
			}
			 else
		 	{
				Content ="����ɹ���";
				FlagStr = "Succ";	
			}  	
	    }
    }
	//
//	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//	    if(!tBusinessDelegate.submitData(tVData,"create",busiName))
//	    {    
//	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
//	        { 
//	           ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
//					   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
//					   FlagStr = "Fail";
//					}
//					else
//					{
//					   Content = "����ʧ��";
//					   FlagStr = "Fail";				
//					}		
//		 
//		 }
//		 else
//		 {
//					  Content ="����ɹ���";
//			    	FlagStr = "Succ";	
//		 }  	
      
	System.out.println("ErrorMessage="+ErrorMessage);
%>                      
<html>
<script language="javascript">
    parent.fraInterface.CusPrintcheck("<%=ErrorMessage%>");
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

