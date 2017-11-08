<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRFeeRateSave.jsp
//程序功能：
//创建日期：2008-01-04
//创建人  ：
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
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 	<%@page import="com.sinosoft.lis.reinsure.*"%>
 	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>

<%
  System.out.println("开始执行Save页面");
  
  //LRFeeRateBatchDefUI mLRFeeRateBatchDefUI = new LRFeeRateBatchDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getAttribute("GI"));
  RIFeeRateTableTraceSchema mRIFeeRateTableTraceSchema = new RIFeeRateTableTraceSchema();
	TransferData mTransferData = new TransferData();  
  CErrors tError = null;
  
  String mOperateType = request.getParameter("OperateType");
  
  System.out.println("操作的类型是"+mOperateType);
  
  mRIFeeRateTableTraceSchema.setFeeTableNo(request.getParameter("FeeTableNo"));
  mRIFeeRateTableTraceSchema.setFeeTableName(request.getParameter("FeeTableName"));
  mRIFeeRateTableTraceSchema.setBatchNo(request.getParameter("BatchNo"));
  mRIFeeRateTableTraceSchema.setSaveDataName(request.getParameter("SaveDataName"));
  mRIFeeRateTableTraceSchema.setInureDate(request.getParameter("InureDate"));
  mRIFeeRateTableTraceSchema.setLapseDate(request.getParameter("LapseDate"));
  mRIFeeRateTableTraceSchema.setState(request.getParameter("State"));
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
  System.out.println(" 开始进行获取数据的操作! ");
  if(mOperateType.equals("INSERT"))
  {
    mDescType = ""+"新增费率表"+"";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = ""+"修改费率表"+"";
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = ""+"删除费率表"+"";
  }
  if(mOperateType.equals("QUERY"))
  {
    mDescType = ""+"查询费率表"+"";
  }
  VData tVData = new VData();
  //try
  //{
  	tVData.addElement(globalInput);
    tVData.addElement(mRIFeeRateTableTraceSchema);
   // mLRFeeRateBatchDefUI.submitData(tVData,mOperateType);
  //}
 // catch(Exception ex)
  //{
    //Content = mDescType+"失败，原因是:" + ex.toString();
    //FlagStr = "Fail";
  //}
  if(!uiBusinessDelegate.submitData(tVData,mOperateType,"LRFeeRateBatchDefUI"))
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
	System.out.println("Content: "+Content);
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  String result = "";
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		result = (String)sTransferData.getValueByName("String");
     	 Content = mDescType+""+"成功，"+""+" "+"合同编号："+""+result;
    	FlagStr = "Succ";
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); 
</script>
</html>