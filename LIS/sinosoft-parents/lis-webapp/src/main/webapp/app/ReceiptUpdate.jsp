<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
//程序名称：ReceiptUpdate.jsp
//程序功能：更新回销时间
//创建日期：2016-05-05 17:41
//创建人  ：lzj
%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
String FlagStr = "Fail";
String Content = "";
String sDate="";//字符串形式的签单日期
//选中的行数
int selectSum=0;
String tPolGrids[] = request.getParameterValues("PolGridNo");
String tPrtNos[] = request.getParameterValues("PolGrid1");//得到第一列的值
String tSingDates[] = request.getParameterValues("PolGrid5"); //得到第五列的值、也就是签单日期
String tChecks[] = request.getParameterValues("InpPolGridChk");
sDate=request.getParameter("ValidStartDate1");
StringBuffer tUpSql = new StringBuffer("update lccont l set l.GetPolDate=to_date('");
tUpSql.append(sDate);
tUpSql.append("','yyyy-mm-dd'),l.GetPolTime=to_char(now(),'hh24:mi:ss'),l.CustomGetPolDate=to_date('");
tUpSql.append(sDate);
tUpSql.append("','yyyy-mm-dd'),l.ModifyDate=to_date(to_char(now(),'yyyy-MM-dd'),'yyyy-MM-dd'),ModifyTime=to_char(now(),'hh24:mi:ss') where l.prtno in(");
for (int nIndex = 0; nIndex < tChecks.length; nIndex++) {
	if (tChecks[nIndex].equals("1")) { //选中的时候
		++selectSum; //[如果选中将该变量+1]用于计算总共选中的记录数
		tUpSql.append("'" + tPrtNos[nIndex] + "',");
	}
}
tUpSql.replace(tUpSql.length()-1, tUpSql.length(),")"); //将最后的一个逗号,替换为")"
boolean flag=false;
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
TransferData tTransferData=new TransferData();
tTransferData.setNameAndValue("SQL", tUpSql.toString());
VData tVData = new VData();
tVData.add(tTransferData);
try{
flag=tBusinessDelegate.submitData(tVData, "execUpdateSQL", "ExeSQLUI");
}catch(Exception e){
/* 	System.out.println("aeon:"+e.toString()); */
	loggerDebug("ReceiptUpdate.jsp","aeon:"+e.toString());
	flag=false;
	selectSum=0;
	Content = "更新失败!";
}
if(flag){
	Content="<strong>"+selectSum+"</strong>"+"条数据更新成功!";
	FlagStr = "Succ";
}
%>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>