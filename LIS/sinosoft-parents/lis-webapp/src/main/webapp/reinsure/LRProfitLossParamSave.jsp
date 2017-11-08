<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRProfitLossCalSave.jsp
//程序功能：
//创建日期：2007-03-14
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
	<%@page import="com.sinosoft.lis.pubfun.*"%>
 	<%@page import="com.sinosoft.lis.reinsure.*"%>
 	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>

<%
	System.out.println("开始执行Save页面");
  
	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
  
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	System.out.println("操作的类型是"+mOperateType);
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
	System.out.println("开始进行获取数据的操作! ");
	RIProLossResultSchema mRIProLossResultSchema = new RIProLossResultSchema();
	RIProLossValueSet mRIProLossValueSet = new RIProLossValueSet();
	String[]incomNum = null;
	
	if(incomNum!=null)
	{
		for(int i=0;i<incomNum.length;i++)
		{
		}
	}
	if(mOperateType.equals("CALPARAM"))
	{
		mDescType = ""+"纯溢手续费参数初始化"+"";
	}
	
	VData tVData = new VData(); 
	//LRProfitLossCalBL mLRProfitLossCalBL = new LRProfitLossCalBL();
	BusinessDelegate blBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	//try
	//{
		tVData.addElement(globalInput); 
		tVData.addElement(mRIProLossResultSchema); 
		tVData.addElement(mRIProLossValueSet); 
	//	mLRProfitLossCalBL.submitData(tVData,mOperateType); 
	//}
	//catch(Exception ex)
	//{
	//	Content = mDescType+"失败，原因是:" + ex.toString();
	//	FlagStr = "Fail";
	//}
  if(!blBusinessDelegate.submitData(tVData,mOperateType,"LRProfitLossCalBL"))
   {    
        if(blBusinessDelegate.getCErrors()!=null&&blBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = mDescType+""+"失败，原因是:"+"" + blBusinessDelegate.getCErrors().getFirstError();
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
	    tError = blBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {
	    	System.out.println("保存成功!");
			Content = mDescType+""+"成功。"+"" ;
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>",""); 
</script>
</html>