<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LDCode1Save.jsp
//程序功能：
//创建日期：2005-01-26 11:24:08
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.config.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //接收信息，并作校验处理。
  //输入参数
  LDCurrencySchema tLDCurrencySchema   = new LDCurrencySchema();
  LDCurrencySet tLDCurrencySet = new LDCurrencySet();
  //输出参数
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
  String busiName="LDCurrencyConfigUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  String tCurrencyName[]  = request.getParameterValues("CurrencyGrid1");
  String tCurrencyEn[]     = request.getParameterValues("CurrencyGrid2");
  String tCurrencyCode[] = request.getParameterValues("CurrencyGrid3");
  String tValidFlag[] = request.getParameterValues("CurrencyGrid4");
  for(int i=0;i<tCurrencyName.length;i++)
  {
	  String tempCurrencyName = tCurrencyName[i];
	  String tempCurrencyEn = tCurrencyEn[i];
	  String tempCurrencyCode = tCurrencyCode[i];
	  String tempValidFlag = tValidFlag[i];
	  
	  if(tempCurrencyName!=null&&!tempCurrencyName.equals("")
			  &&tempCurrencyEn!=null&&!tempCurrencyEn.equals("")
			  &&tempCurrencyCode!=null&&!tempCurrencyCode.equals("")
			  &&tempValidFlag!=null&&!tempValidFlag.equals(""))
	  {
		  tLDCurrencySchema   = new LDCurrencySchema();
		  tLDCurrencySchema.setCodeAlias(tempCurrencyEn);
		  tLDCurrencySchema.setCurrName(tempCurrencyName);
		  tLDCurrencySchema.setCurrCode(tempCurrencyCode);
		  tLDCurrencySchema.setValidation(tempValidFlag);
		  
		  tLDCurrencySet.add(tLDCurrencySchema);
	  }
  }
 // TransferData tTransferData = new TransferData();
 // tTransferData.setNameAndValue("CodeType","currencyprecision");
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
  try
  {
  // 准备传输数据 VData
  	VData tVData = new VData();
	tVData.add(tLDCurrencySet);
  	tVData.add(tG);
  //	tVData.add(tTransferData);
    
    if( tBusinessDelegate.submitData( tVData, transact,busiName ) == false )                       
	{                                                                               
		Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
		
	}
	else
	{
		Content = " 操作成功! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = tBusinessDelegate.getResult();
	}
		
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  
//如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
    	Content = " 保存成功! ";
    	FlagStr = "Success";
    }
    else                                                                           
    {
    	Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  
  //添加各种预处理
%>                      
<%=Content%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
