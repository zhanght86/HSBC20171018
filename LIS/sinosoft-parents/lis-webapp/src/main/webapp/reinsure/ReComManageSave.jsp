<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�ReComManageSave.jsp
//�����ܣ�
//�������ڣ�2006-08-17
//������  ���ű�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.reinsure.*"%>
 <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>


<%
  GlobalInput globalInput = new GlobalInput( );
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
  
  RIComInfoSchema mRIComInfoSchema = new RIComInfoSchema();
  RIComLinkManInfoSet mRIComLinkManInfoSet = new RIComLinkManInfoSet();
  
  //ReComManageUI mReComManageUI = new ReComManageUI();
	BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  System.out.println("������������"+mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String ContentNO = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ

  
  PubFun tPubFun = new PubFun();
        String currentDate = tPubFun.getCurrentDate();
        String currentTime = tPubFun.getCurrentTime();
  
  
  
  //��ȡ�ٱ���˾������Ϣ 
  mRIComInfoSchema.setComPanyNo  (	request.getParameter("ReComCode"));
  mRIComInfoSchema.setComPanyName(	request.getParameter("ReComName"));
  mRIComInfoSchema.setGrpZipCode(	request.getParameter("PostalCode"));
  mRIComInfoSchema.setGrpAddress(	request.getParameter("Address"));
  mRIComInfoSchema.setFax       (	request.getParameter("FaxNo"));
  mRIComInfoSchema.setRemark    (	request.getParameter("Note"));
  System.out.println("aaaa: "+request.getParameter("Note"));
  mRIComInfoSchema.setOperator (globalInput.Operator);
  mRIComInfoSchema.setMakeDate (currentDate);
  mRIComInfoSchema.setMakeTime (currentDate);
  
  //��ȡ�ٱ���˾��ϵ����Ϣ
  String[] strNumber 			= request.getParameterValues("RelateGridNo");
  String[] strRelaName	  = request.getParameterValues("RelateGrid1");
  String[] strDepartment 	= request.getParameterValues("RelateGrid2");
  String[] strDuty 				= request.getParameterValues("RelateGrid3");
  String[] strRelaTel 		= request.getParameterValues("RelateGrid4");
  String[] strMobileTel 	= request.getParameterValues("RelateGrid5");
  String[] strFaxNo 			= request.getParameterValues("RelateGrid6");
  String[] strEmail 			= request.getParameterValues("RelateGrid7"); 
	String[] strRelaCode 		= request.getParameterValues("RelateGrid8");
  if(strNumber!=null)
  {
  	int tLength = strNumber.length;
    for(int i = 0 ;i < tLength ;i++){
    
      RIComLinkManInfoSchema tRIComLinkManInfoSchema = new RIComLinkManInfoSchema();
		
			tRIComLinkManInfoSchema.setReComCode(request.getParameter("ReComCode"));
			
      tRIComLinkManInfoSchema.setRelaCode   (strRelaCode[i]);
      tRIComLinkManInfoSchema.setDepartment (strDepartment[i]);
      tRIComLinkManInfoSchema.setDuty       (strDuty[i]);
      tRIComLinkManInfoSchema.setRelaTel    (strRelaTel[i]);
      tRIComLinkManInfoSchema.setMobileTel  (strMobileTel[i]);
      tRIComLinkManInfoSchema.setFaxNo      (strFaxNo[i]);
      tRIComLinkManInfoSchema.setEmail      (strEmail[i]);
      tRIComLinkManInfoSchema.setRelaName   (strRelaName[i]);
      
     
      mRIComLinkManInfoSet.add(tRIComLinkManInfoSchema);
    }
  }

  if(mOperateType.equals("INSERT"))
  {
    mDescType = ""+"������˾"+"";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = ""+"�޸Ĺ�˾��Ϣ"+"";
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = ""+"ɾ����˾"+"";
  }
  if(mOperateType.equals("QUERY"))
  {
    mDescType = ""+"��ѯ��˾"+"";
  }
  
  VData tVData = new VData();
 // try
  //{
  	tVData.addElement(globalInput);
    tVData.addElement(mRIComInfoSchema);
    tVData.addElement(mRIComLinkManInfoSet);
    
   // mReComManageUI.submitData(tVData,mOperateType);
  //}
 // catch(Exception ex)
 // {
 //   Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
  //  FlagStr = "Fail";
  //}
if(!uiBusinessDelegate.submitData(tVData,mOperateType,"ReComManageUI"))
   {    
        if(uiBusinessDelegate.getCErrors()!=null&&uiBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = mDescType+""+"ʧ�ܣ�ԭ����:"+"" + uiBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = ""+"����ʧ��"+"";
				   FlagStr = "Fail";				
				}
   }
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  String result = "";
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
      TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
	  result = (String)sTransferData.getValueByName("String");
      Content = mDescType+""+"�ɹ���"+""+" "+"��˾���룺"+""+result;
      FlagStr = "Succ";
      ContentNO = result;
      
      
     
    }
    else
    {
    	Content = mDescType+" "+"ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>");
</script>
</html>