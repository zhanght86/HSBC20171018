<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LDUWUserSave.jsp
//程序功能：
//创建日期：2005-01-24 18:15:01
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  //<%@page import="com.sinosoft.lis.config.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //接收信息，并作校验处理。
  //输入参数
  LDUWUserSchema tLDUWUserSchema   = new LDUWUserSchema();
  LDUWUserSchema tLDUWUserSchema1   = new LDUWUserSchema();
  //OLDEdorUserUI tOLDEdorUserUI   = new OLDEdorUserUI();
  //BusinessService tOLDEdorUserUI = new OLDEdorUserUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  String busiName="configOLDEdorUserUI";
  //输出参数
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";

  
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
	
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
   tLDUWUserSchema.setUserCode(request.getParameter("UserCode"));
    tLDUWUserSchema.setUWType("3");
    tLDUWUserSchema.setUWPopedom(request.getParameter("UWPopedom"));
    tLDUWUserSchema.setUpUWPopedom(request.getParameter("UpUwPopedom"));
    tLDUWUserSchema.setEdorPopedom("1");
      if(transact.equals("UPDATE||MAIN"))
    {
    	tLDUWUserSchema1.setUserCode(request.getParameter("UserCode1"));
    	tLDUWUserSchema1.setUWType(request.getParameter("UWType1"));
    	tLDUWUserSchema1.setUWPopedom(request.getParameter("UWPopedom1"));   
    }    
    
  try
  {
  // 准备传输数据 VData
  	VData tVData = new VData();
	tVData.add(tLDUWUserSchema);
	if(transact.equals("UPDATE||MAIN"))
	{
	  tVData.add(tLDUWUserSchema1);
	  loggerDebug("LDEdorUserSave","UPDATE MAIN " + tLDUWUserSchema1.getUserCode());
	}
  	tVData.add(tG);
  	  loggerDebug("LDEdorUserSave","操作是:"+transact);
    tBusinessDelegate.submitData(tVData,transact,busiName);
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
