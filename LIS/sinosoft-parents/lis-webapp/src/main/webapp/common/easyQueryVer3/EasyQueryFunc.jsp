<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�EasyQueryFunc.jsp
//�����ܣ�EasyQuery��ѯ���ܵĸ�������
//
//�������ڣ�2006-04-21
//������  ��Ī��
%>
<%!
public String getWherePart(HttpServletRequest request,String fieldName)
{
	return getWherePart(request,fieldName,fieldName,"=","0");
}
public String getWherePart(HttpServletRequest request,String fieldName, String controlName)
{
	return getWherePart(request,fieldName,controlName,"=","0");
}

public String getWherePart(HttpServletRequest request,String fieldName, String controlName, String strOperate)
{
	return getWherePart(request,fieldName,controlName,strOperate,"0");
}

//�������ƣ�getWherePart
//����˵����
//request:����JSP�����ּ���
//fieldName:���ݿ��е��ֶ�����
//controlName:ҳ���д�����ֵ������
//strOperate���������ţ���=,like,>,>=,<,<=
//fieldType:"0"��ʾֵ���ַ����ͣ�"1"��ʾ����������
//����ֵ��sql��WHERE�е�һ���־�
public String getWherePart(HttpServletRequest request,String fieldName, String controlName, String strOperate, String fieldType)
{
	String strWherePart = "";
	String value = "";
	if(controlName == null || controlName.equals("")) controlName = fieldName;
	value=(String)request.getAttribute(controlName.toUpperCase());
	if(value == null || value.equals("")) return strWherePart;
	if(fieldType == null || fieldType.equals("")) fieldType = "0";
	if(strOperate == null || strOperate .equals("")) strOperate = "=";
	if(fieldType.equals("0"))
	{
		// 0:�ַ���
		if(strOperate.toUpperCase().equals("LIKE"))
		{
			strWherePart = " and " + fieldName.trim() + " like '" + value.trim() + "%'";
		}
		else
		  if(strOperate.toUpperCase().equals("IN"))
		  {
		  loggerDebug("EasyQueryFunc","*********"+value.trim());
		   strWherePart = " and " + fieldName.trim() + "  in  " + value.trim() ;
		  }
		else
		{
			strWherePart = " and " + fieldName.trim() +  strOperate.trim() + "'" +  value.trim() + "' ";
		}
	}
	if(fieldType.equals("1"))
	{
		// 1:������
		strWherePart = " and " + fieldName.trim() + strOperate.trim() + value.trim() + " ";
	}
	return strWherePart;
}
public String getValue(HttpServletRequest request,String fieldName)
{
  String value=(String)request.getAttribute(fieldName.toUpperCase());
  if(value==null||value=="")
   {
   return "''";
   }
  else
   return  "'"+value.trim()+"'" ;
}
public String getManageComLimit(HttpSession session,String fieldName)
{
	GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI");
	return " and "+ fieldName + " like '"+ tGI.ManageCom+ "%'";
}

%>

<%
String SQLID="";
String SQL="";
SQLID=(String)request.getAttribute("EASYQUERYSQLID");
loggerDebug("EasyQueryFunc","SQLID:"+SQLID);
%>
