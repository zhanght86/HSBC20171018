<%@page contentType="text/html;charset=GBK" %>
<html>   
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>     

<head>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<SCRIPT src="../app/ProposalInput.js"></SCRIPT>
	<SCRIPT src="./ProposalInput.js"></SCRIPT>
	<%@include file="ProposalInit.jsp"%>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	
	<%@page import="com.sinosoft.lis.pubfun.*"%>
</head>

<%
  GlobalInput tG1 = (GlobalInput)session.getValue("GI");
	
	String userCode = tG1.Operator;
%>

<body>
  
</body>
</html>

<script>
  var loadFlag = "<%=request.getParameter("loadFlag")%>";
  prtNo = "<%=request.getParameter("prtNo")%>";
  riskCode = "<%=request.getParameter("risk")%>";
  
  	
	//����������
	if (loadFlag == "8") {
	  top.mainPolNo = "<%=request.getParameter("polNo")%>";
	}
  
	getRiskInput("<%=request.getParameter("risk")%>", loadFlag);
	
	//�±�����
	if (loadFlag == "7") {
	  fm.all("SpecifyValiDate").value = "Y";
	  fm.all('CValiDate').value = "<%=PubFun.calDate(PubFun.getCurrentDate(), 1, "D", PubFun.getCurrentDate())%>";
	  
	 // var strSql = "select EdorPopeDom from lduser where usercode='<%=userCode%>'";
	 var sqlid3 = 'ProposalInputSql3';
	 var mySql1 = new SqlClass();
		mySql1.setResourceName("bq.ProposalInputSql"); // ָ��ʹ�õ�properties�ļ���
		mySql1.addSubPara('<%=userCode%>');// ָ������Ĳ���
		mySql1.setSqlId(sqlid3);// ָ��ʹ�õ�Sql��id
		strsql = mySql1.getString();
	  var arrResult = easyExecSql(strSql);
	  
	  if (arrResult >= "E") {
	    fm.all("CValiDate").readOnly = false;
      //fm.all("CValiDate").className = "common";
      //fm.all("SpecifyValiDate").ondblclick= showSaleChnl;
	  }
	  else {
	    fm.all("CValiDate").readOnly = true;
	    fm.all("CValiDate").className = "readonly";
	  }
	}
	
	var oldCValidate = "";
	if (loadFlag == "8") {
	  fm.all("SpecifyValiDate").value = "Y";
	  oldCValidate = fm.all('CValiDate').value;
	}
	
</script>


