<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  
  String sql="select a.InsuredNo,a.insuredname,a.insuredsex,a.insuredbirthday,a.insuredidtype,a.insuredidno,b.OccupationType,b.OccupationCode,a.prem,a.amnt From lcinsured b,lccont a where a.contno=b.contno and a.grpcontno='"+request.getParameter("ProposalGrpContNo")+"'";
  loggerDebug("PrintOut",request.getParameter("ProposalGrpContNo"));
  ExeSQL exeSql = new ExeSQL();
  SSRS qrySSRS = new SSRS();
  qrySSRS = exeSql.execSQL(sql);
  loggerDebug("PrintOut",qrySSRS.MaxRow);
    
 %>                                 
<html>
<head>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  </head>
<form id="frmLIST" action="" target="" method="post">
<body leftmargin="0" topmargin="0">
<input type="button" class="cssButton" name="export" value="导出数据" onclick="MakeExcel();">
</body>

<script language="javascript">
function MakeExcel()
	{
	try {
          var xls    = new ActiveXObject ( "Excel.Application" );
       }
       catch(e) {
          alert( "要导入数据到Excel，您必须安装Excel电子表格软件，同时设置IE浏览器的安全级别允许使用“ActiveX 控件”！！");
          return "";
       }

          xls.visible = true;

      var xlBook = xls.Workbooks.Add;
      var xlsheet = xlBook.Worksheets(1);
      xls.Cells.Select;
      xls.Selection.NumberFormatLocal = "@";
xlsheet.Cells(1,1).Value="编号";
xlsheet.Cells(1,2).Value="客户号码";
xlsheet.Cells(1,3).Value="名字";
xlsheet.Cells(1,4).Value="性别";
xlsheet.Cells(1,5).Value="出生日期";
xlsheet.Cells(1,6).Value="证件类型";
xlsheet.Cells(1,7).Value="证件号码";
xlsheet.Cells(1,8).Value="职业类别";
xlsheet.Cells(1,9).Value="工种";
xlsheet.Cells(1,10).Value="保费";
xlsheet.Cells(1,11).Value="保额";

<%
      int colLen=8;
      int rowlen=qrySSRS.MaxRow;
      for (int i=1;i<=rowlen;i++){
%>
xlsheet.Cells(<%=i+1%>,1).Value="<%=i%>";
xlsheet.Cells(<%=i+1%>,2).Value="<%=qrySSRS.GetText(i,1)%>";
xlsheet.Cells(<%=i+1%>,3).Value="<%=qrySSRS.GetText(i,2)%>";
xlsheet.Cells(<%=i+1%>,4).Value="<%=qrySSRS.GetText(i,3)%>";
xlsheet.Cells(<%=i+1%>,5).Value="<%=qrySSRS.GetText(i,4)%>";
xlsheet.Cells(<%=i+1%>,6).Value="<%=qrySSRS.GetText(i,5)%>";
xlsheet.Cells(<%=i+1%>,7).Value="<%=qrySSRS.GetText(i,6)%>";
xlsheet.Cells(<%=i+1%>,8).Value="<%=qrySSRS.GetText(i,7)%>";
xlsheet.Cells(<%=i+1%>,9).Value="<%=qrySSRS.GetText(i,8)%>";
xlsheet.Cells(<%=i+1%>,10).Value="<%=qrySSRS.GetText(i,9)%>";
xlsheet.Cells(<%=i+1%>,11).Value="<%=qrySSRS.GetText(i,10)%>";

<%
}
%>
}
</script>
</html>


