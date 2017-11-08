<%@include file="/i18n/language.jsp"%>
<%
//程序名称：ReComManageSave.jsp
//程序功能：
//创建日期：2006-08-17
//创建人  ：张斌
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
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
  System.out.println("操作的类型是"+mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String ContentNO = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式

  
  PubFun tPubFun = new PubFun();
        String currentDate = tPubFun.getCurrentDate();
        String currentTime = tPubFun.getCurrentTime();
  
  
  
  //获取再保公司基本信息 
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
  
  //获取再保公司联系人信息
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
    mDescType = ""+"新增公司"+"";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = ""+"修改公司信息"+"";
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = ""+"删除公司"+"";
  }
  if(mOperateType.equals("QUERY"))
  {
    mDescType = ""+"查询公司"+"";
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
 //   Content = mDescType+"失败，原因是:" + ex.toString();
  //  FlagStr = "Fail";
  //}
if(!uiBusinessDelegate.submitData(tVData,mOperateType,"ReComManageUI"))
   {    
        if(uiBusinessDelegate.getCErrors()!=null&&uiBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = mDescType+""+"失败，原因是:"+"" + uiBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = ""+"保存失败"+"";
				   FlagStr = "Fail";				
				}
   }
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  String result = "";
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
      TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
	  result = (String)sTransferData.getValueByName("String");
      Content = mDescType+""+"成功，"+""+" "+"公司代码："+""+result;
      FlagStr = "Succ";
      ContentNO = result;
      
      
     
    }
    else
    {
    	Content = mDescType+" "+"失败，原因是:"+"" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>");
</script>
</html>