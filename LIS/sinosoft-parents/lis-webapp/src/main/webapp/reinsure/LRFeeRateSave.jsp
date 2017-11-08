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
  //LRFeeRateDefUI mLRFeeRateDefUI = new LRFeeRateDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getAttribute("GI"));
  RIFeeRateTableDefSchema 	mRIFeeRateTableDefSchema = new RIFeeRateTableDefSchema();
  RIFeeRateTableTraceSchema mRIFeeRateTableTraceSchema = new RIFeeRateTableTraceSchema();
  RIFeeRateTableClumnDefSet mRIFeeRateTableClumnDefSet = new RIFeeRateTableClumnDefSet();
	TransferData mTransferData = new TransferData();  
  CErrors tError = null;
  String mDeleteType = request.getParameter("DeleteType");
  String mOperateType = request.getParameter("OperateType");
  
  mTransferData.setNameAndValue("DeleteType", mDeleteType);
  
  System.out.println("操作的类型是"+mOperateType);
  System.out.println("类型是"+mDeleteType);
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
  System.out.println(" 开始进行获取数据的操作! ");
  mRIFeeRateTableDefSchema.setFeeTableNo	(request.getParameter("FeeTableNo"	));
  mRIFeeRateTableDefSchema.setFeeTableName(request.getParameter("FeeTableName"));
  mRIFeeRateTableDefSchema.setFeeTableType(request.getParameter("FeeTableType"));
  mRIFeeRateTableDefSchema.setReMark			(request.getParameter("ReMark"));
  mRIFeeRateTableDefSchema.setState			(request.getParameter("State"));
  
  String[] StrNum							=request.getParameterValues("TableClumnDefGridNo");
  String[] tFeeClumnName			=request.getParameterValues("TableClumnDefGrid1");
  String[] tFeeClumnDataCode	=request.getParameterValues("TableClumnDefGrid2");
  String[] tFeeClumnType			=request.getParameterValues("TableClumnDefGrid4");
  String[] tTagetClumn				=request.getParameterValues("TableClumnDefGrid5");
  String[] tTagetDLClumn			=request.getParameterValues("TableClumnDefGrid7");
  String[] tTagetULClumn			=request.getParameterValues("TableClumnDefGrid9");
  
  if(StrNum!=null){
  	RIFeeRateTableClumnDefSchema tRIFeeRateTableClumnDefSchema;
  	for(int i=0;i<StrNum.length;i++){
  			tRIFeeRateTableClumnDefSchema=new RIFeeRateTableClumnDefSchema();
  			
  	    tRIFeeRateTableClumnDefSchema.setFeeTableNo				(request.getParameter("FeeTableNo"	));
  	    tRIFeeRateTableClumnDefSchema.setFeeTableName     (request.getParameter("FeeTableName"));
  	    tRIFeeRateTableClumnDefSchema.setFeeClumnName     (tFeeClumnName[i]);
  	    tRIFeeRateTableClumnDefSchema.setFeeClumnDataCode (tFeeClumnDataCode[i]);
  	    tRIFeeRateTableClumnDefSchema.setFeeClumnType     (tFeeClumnType[i]);
  	    tRIFeeRateTableClumnDefSchema.setTagetClumn       (tTagetClumn[i]);
  	    tRIFeeRateTableClumnDefSchema.setTagetULClumn     (tTagetULClumn[i]);
  	    tRIFeeRateTableClumnDefSchema.setTagetDLClumn     (tTagetDLClumn[i]);
  	    
  	    mRIFeeRateTableClumnDefSet.add(tRIFeeRateTableClumnDefSchema);
  	}
  }
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
 // try
 // {
  	tVData.addElement(globalInput);
  	tVData.addElement(mTransferData);
    tVData.addElement(mRIFeeRateTableDefSchema);
    tVData.addElement(mRIFeeRateTableTraceSchema);
    tVData.addElement(mRIFeeRateTableClumnDefSet);
   // mLRFeeRateDefUI.submitData(tVData,mOperateType);
 // }
  //catch(Exception ex)
  //{
  //  Content = mDescType+"失败，原因是:" + ex.toString();
  //  FlagStr = "Fail";
 // }
  if(!uiBusinessDelegate.submitData(tVData,mOperateType,"LRFeeRateDefUI"))
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
    	System.out.println("保存成功!");
    	TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		result = (String)sTransferData.getValueByName("String");
      	Content = mDescType+""+"成功，"+""+" "+"费率表编号："+""+result;
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>"); 
</script>
</html>