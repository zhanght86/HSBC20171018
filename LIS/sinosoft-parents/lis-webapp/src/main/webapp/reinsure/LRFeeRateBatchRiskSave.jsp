<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRFeeRateBatchSave11.jsp
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
  //LRFeeRateBatchRiskUI mLRFeeRateBatchRiskUI = new LRFeeRateBatchRiskUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getAttribute("GI"));
	
  RIAssociateFeeTableSet mRIAssociateFeeTableSet = new RIAssociateFeeTableSet();
  TransferData tTransferData = new TransferData();
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
    
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
  System.out.println(" 开始进行获取数据的操作! ");
  
  String[] StrNum							=request.getParameterValues("FeeRateBatchGridNo");
  String[] tAccumulateDefNO		=request.getParameterValues("FeeRateBatchGrid1");
  String[] tDeTailFlag			  =request.getParameterValues("FeeRateBatchGrid2");
  String[] tRIPreceptNo	      =request.getParameterValues("FeeRateBatchGrid4");
  String[] tAssociateCode			=request.getParameterValues("FeeRateBatchGrid5");
  String[] tAreaID		        =request.getParameterValues("FeeRateBatchGrid6");
  //String[] tAreaLevel			  =request.getParameterValues("FeeRateBatchGrid");
  String[] tIncomeCompanyNo	  =request.getParameterValues("FeeRateBatchGrid7");
  String[] tUpperLimit			  =request.getParameterValues("FeeRateBatchGrid9");
  String[] tLowerLimit		    =request.getParameterValues("FeeRateBatchGrid10");
  String[] tPremFeeTableNo		=request.getParameterValues("FeeRateBatchGrid11");
  String[] tComFeeTableNo			=request.getParameterValues("FeeRateBatchGrid13");
  
  if(StrNum!=null)
  {
  	for(int i=0;i<StrNum.length;i++)
  	{
  			RIAssociateFeeTableSchema 	mRIAssociateFeeTableSchema = new RIAssociateFeeTableSchema();
				
  	    mRIAssociateFeeTableSchema.setAccumulateDefNO (tAccumulateDefNO[i]);
  	    mRIAssociateFeeTableSchema.setDeTailFlag      (tDeTailFlag[i]);
  	    mRIAssociateFeeTableSchema.setRIPreceptNo     (tRIPreceptNo[i]);
  	    mRIAssociateFeeTableSchema.setAssociateCode   (tAssociateCode[i]);   
  	    mRIAssociateFeeTableSchema.setAreaID          (tAreaID[i]);
  	    mRIAssociateFeeTableSchema.setAreaLevel       ("0"); //无意义
  	    mRIAssociateFeeTableSchema.setIncomeCompanyNo (tIncomeCompanyNo[i]);
  	    mRIAssociateFeeTableSchema.setUpperLimit      (tUpperLimit[i]);
  	    mRIAssociateFeeTableSchema.setLowerLimit      (tLowerLimit[i]);
  	    mRIAssociateFeeTableSchema.setPremFeeTableNo  (tPremFeeTableNo[i]);
  	    mRIAssociateFeeTableSchema.setComFeeTableNo   (tComFeeTableNo[i]);
  	    mRIAssociateFeeTableSet.add(mRIAssociateFeeTableSchema);
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
  //{
  	tVData.addElement(globalInput);
    tVData.addElement(mRIAssociateFeeTableSet);
   // mLRFeeRateBatchRiskUI.submitData(tVData,mOperateType);
 // }
  //catch(Exception ex)
  //{
  //  Content = mDescType+"失败，原因是:" + ex.toString();
  //  FlagStr = "Fail";
  //}
  if(!uiBusinessDelegate.submitData(tVData,mOperateType,"LRFeeRateBatchRiskUI"))
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
  TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
  result=(String) sTransferData.getValueByName("String");
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	System.out.println("保存成功!");
      Content = mDescType+""+"成功!"+"";
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