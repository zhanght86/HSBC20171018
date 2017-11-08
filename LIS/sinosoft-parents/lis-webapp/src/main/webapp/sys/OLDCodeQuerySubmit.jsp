<%
//程序名称：OLDCodeQuery.js
//程序功能：
//创建日期：2002-08-16 17:44:48
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  LDCodeSchema tLDCodeSchema   = new LDCodeSchema();

  OLDCodeUI tOLDCodeQueryUI   = new OLDCodeUI();
  //读取Session中的全局类
	GlobalInput tG = new GlobalInput();

	tG.Operator = "Admin";
	tG.ComCode  = "001";
  session.putValue("GI",tG);

  tG=(GlobalInput)session.getValue("GI");

    tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
    tLDCodeSchema.setCode(request.getParameter("Code"));
    tLDCodeSchema.setCodeName(request.getParameter("CodeName"));
    tLDCodeSchema.setCodeAlias(request.getParameter("CodeAlias"));
    tLDCodeSchema.setComCode(request.getParameter("ComCode"));
    tLDCodeSchema.setOtherSign(request.getParameter("OtherSign"));



  // 准备传输数据 VData
  VData tVData = new VData();

	tVData.addElement(tLDCodeSchema);
	tVData.add(tG);

  FlagStr="";
  // 数据传输
	if (!tOLDCodeQueryUI.submitData(tVData,"QUERY||MAIN"))
	{
      Content = " 查询失败，原因是: " +  tError.getFirstError();
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tOLDCodeQueryUI.getResult();
		
		// 显示
		LDCodeSet mLDCodeSet = new LDCodeSet();
		mLDCodeSet.set((LDCodeSet)tVData.getObjectByObjectName("LDCodeSet",0));
		int n = mLDCodeSet.size();
		LDCodeSchema mLDCodeSchema;
		for (int i = 1; i <= n; i++)
		{
		  	mLDCodeSchema = mLDCodeSet.get(i);
		   	%>
		   	<script language="javascript">
        parent.fraInterface.CodeGrid.addOne("CodeGrid")
parent.fraInterface.fm.CodeGrid1[<%=i-1%>].value="<%=mLDCodeSchema.getCodeType()%>";
parent.fraInterface.fm.CodeGrid2[<%=i-1%>].value="<%=mLDCodeSchema.getCode()%>";
parent.fraInterface.fm.CodeGrid3[<%=i-1%>].value="<%=mLDCodeSchema.getCodeName()%>";

			</script>
			<%
		} // end of for
	} // end of if
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (!FlagStr.equals("Fail"))
  {
    	Content = " 查询成功! ";
    	FlagStr = "Succ";
  }

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

