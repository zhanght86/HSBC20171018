<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//�������ƣ�.jsp
//�����ܣ�sql�༭ҳ��ģ��
//          SQLID��SQL�Ǳ�����
//�������ڣ�YYYY-MM-DD
//������  ��XXX 
%>
<%//���������²��ֱ༭SQL
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
