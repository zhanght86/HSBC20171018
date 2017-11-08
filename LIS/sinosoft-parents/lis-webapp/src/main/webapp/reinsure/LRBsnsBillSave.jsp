<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRBsnsBillSave.jsp
//程序功能：
//创建日期：2007-02-28
//创建人  ：zhangbin
//更新记录：  更新人: zhangbin 更新日期  2008-4-14   更新原因/内容
%>
<!--用户校验类-->
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page import="com.sinosoft.utility.*"%>
	<%@page import="com.sinosoft.lis.schema.*"%>
	<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
	<%@page import="com.sinosoft.lis.reinsure.*"%>
	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>

<%	
	GlobalInput globalInput = new GlobalInput();
	CErrors tError = null;	
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
	
	System.out.println("========Operator===="+globalInput.Operator);
	System.out.println("========ComCode===="+globalInput.ComCode);
	
	String FlagStr = "";
	String Content = "";
	TransferData tTransferData = new TransferData();
	VData tVData = new VData();
	
	RIBsnsBillBatchSchema tRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();
	//LRBsnsBillUI mLRBsnsBillUI = new LRBsnsBillUI();
	BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	String mStartDate = request.getParameter("StartDate");
	String mEndDate = request.getParameter("EndDate");
	String mContNo = request.getParameter("ContNo");	
	String mRIcomCode = request.getParameter("RIcomCode");

	//封装前台数据，传到后台
	
	//=======================
	tRIBsnsBillBatchSchema.setStartDate(mStartDate);
	tRIBsnsBillBatchSchema.setEndDate(mEndDate);
	tRIBsnsBillBatchSchema.setRIContNo(mContNo) ;
	tRIBsnsBillBatchSchema.setIncomeCompanyNo(mRIcomCode) ;
	//=====================	
	
	//try
	//{
	  	tTransferData.setNameAndValue("","");
	  	
	  	tVData.addElement(tTransferData);
	  	tVData.addElement(globalInput);
	    tVData.addElement(tRIBsnsBillBatchSchema);
	    
	    //mLRBsnsBillUI.submitData(tVData," ");
	//}
	//catch(Exception ex)
	//{
	//	Content = "操作失败，原因是:" + ex.toString();
	//	FlagStr = "Fail";
	//}
	if(!uiBusinessDelegate.submitData(tVData," ","LRBsnsBillUI"))
   {    
        if(uiBusinessDelegate.getCErrors()!=null&&uiBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = ""+"操作失败，原因是:"+"" + uiBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = ""+"保存失败"+"";
				   FlagStr = "Fail";				
				}
   }
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr=="")
	{
	    tError = uiBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {
			System.out.println("保存成功!");
			Content = ""+"操作成功!"+"";
	    	FlagStr = "Succ";
	    
	    }
	    else
	    {
	    	Content = ""+"操作失败，原因是:"+"" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	}
	
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); 
</script>
</html>