<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//�������ƣ�.jsp
//�����ܣ�sql�༭ҳ��ģ��
//          SQLID��SQL�Ǳ�����
//�������ڣ�2007-06-19
//������  ��pst 
%>

<%
//���������²��ֱ༭SQL
if(SQLID.equals("PEdorTypePSInputSql_00"))
{
  System.out.println("**********1********");
	SQL="select edorno,edortype,(select edorname from lmedoritem where edorcode=edortype),"
	             +"(select * from ), edorappdate,edorvalidate from lpedoritem  where 1=1";
	SQL+=getWherePart(request,"p.ContNo","ContNo");     
}

if(SQLID.equals("PEdorTypePSInputSql_01"))
{
  SQL="select StandbyFlag1,standbyflag3 from lpedoritem where 1=1";
  SQL+=getWherePart(request,"ContNo"); 
	SQL+=getWherePart(request,"EdorNo"); 
	SQL+=getWherePart(request,"EdorType"); 
	    
}
if(SQLID.equals("PEdorTypePSInputSql_0"))
{
  SQL="select AppntName from LCCont where ContNo = '?ContNo?'";	    
}
if(SQLID.equals("PEdorTypePSInputSql_1"))
{
  SQL="select InsuredName from LCCont where ContNo = '?ContNo?'";	    
}

if(SQLID.equals("PEdorTypePSInputSql_2"))
{
  SQL="select edorname from lmedoritem where edorcode = '?tEdortype?'";   
}    
if(SQLID.equals("PEdorTypePSInputSql_3"))
{
  SQL="select AppntName, InsuredName from LCCont where ContNo = '?sOtherNo?'";   
}  
%>
<%
System.out.println("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>
