<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RuleTestApprove.jsp
//�����ܣ�
//�������ڣ�2008-11-07 15:12:33
//������  ��L.J
%>
  <%@page import="com.sinosoft.utility.*" %>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.service.*" %>
<%
  //��ȡ����
  String id = request.getParameter("TemplateId");
  String control = request.getParameter("control");
  if(id !=null&&id.length()!=0){
	 // ExeSQL es = new ExeSQL();
	  String strSql = null;
	  if(control.equalsIgnoreCase("true")){
	      strSql = "UPDATE LRTemplateT SET STATE = '3' WHERE ID = '" + id+"'";
	  }else{
		  strSql = "UPDATE LRTemplateT SET STATE = '2' WHERE ID = '" + id+"'";
	  }
	  //
	  TransferData sTransferData=new TransferData();
      sTransferData.setNameAndValue("SQL", strSql);
      VData sVData = new VData();
      sVData.add(sTransferData);
      BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      boolean flag = tBusinessDelegate.submitData(sVData, "execUpdateSQL", "ExeSQLUI");
    	  
	  //boolean flag = es.execUpdateSQL(strSql);
	  String  Content;
	  String FlagStr;
	  CErrors tError = tBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {

	    	Content = "����ɹ�! ";
	    	FlagStr = "Succ";
	    }
	    else
	    {
	    	Content = "����ʧ��,ԭ����" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	    request.getRequestDispatcher("RuleQuerySaveResponse.jsp?Content="+Content+"&flag="+FlagStr).forward(request,response);
/*	  if(!flag){
		  loggerDebug("RuleTestApprove","Errors in RuleTestApprove.jsp:"+es.mErrors);
	  }	  */
  }	  
%>
<html>
<head>
</head>
<body>
</body>
</html>
