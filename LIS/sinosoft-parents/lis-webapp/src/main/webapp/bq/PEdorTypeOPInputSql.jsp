<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//程序名称：.jsp
//程序功能：sql编辑页面模版
//          SQLID和SQL是保留字
//创建日期：YYYY-MM-DD
//创建人  ：XXX 
%>
<%//必须在以下部分编辑SQL
/*if(SQLID.equals("PEdorTypeOPInputSql_0"))
{
  SQL="select standbyflag3 from lpedoritem where edoracceptno = '" 
     + edorAccpetNo 
     + "' and edortype = '" 
     + edorType 
     +"'";
}*/
/*if(SQLID.equals("PEdorTypeOPInputSql_1"))
{
  SQL="select abs(getmoney) from ljsgetendorse where endorsementno='"
     +EdorNo
     +"' and feeoperationtype = 'OP' and feefinatype = 'TB'";
}*/
%>
<%
System.out.println("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>
