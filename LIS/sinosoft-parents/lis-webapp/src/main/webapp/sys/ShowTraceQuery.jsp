<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ShowTraceQuery.jsp
//�����ܣ���������켣��ѯ
//�������ڣ�2005-11-24 11:16
//������  ����Ρ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tContNo = request.getParameter("ContNo");   
%>

<head >
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="ShowTraceQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ShowTraceQueryInit.jsp"%>
</head>

<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  	
<table>
  <tr>
    <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCCollector1);">
    </td>
    <td class= titleImg>
  	  ��������켣 
  	</td>
  </tr>
</table>

<Div  id= "divLCTrace1" style= "display: ''" align=center>
  <table  class= common>
   	<tr  class= common>
  	  <td text-align: left colSpan=1 >
				<span id="spanTraceGrid" ></span> 
			</td>
		</tr>
	</table>     
</div>

    <p><INPUT VALUE="��  ��" class= cssButton TYPE=button onclick="parent.close();"></P>
    <!--��ȡ��Ϣ-->
    
    <Input type=hidden id="ContNo" name="ContNo"><!--�շ�Ա���-->
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
