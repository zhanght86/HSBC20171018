<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�CollectorQuery.jsp
//�����ܣ��շ�Ա������Ϣ��ѯ
//�������ڣ�2005-09-30
//������  ��wuhao2
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tAgentCode = request.getParameter("AgentCode"); 
%>

<head >
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="CollectorQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CollectorQueryInit.jsp"%>
</head>

<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
  	
<table>
  <tr>
    <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCCollector1);">
    </td>
    <td class= titleImg>
  	  �շ�Ա������Ϣ 
  	</td>
  </tr>
</table>

<Div  id= "divLCCollector1" style= "display: ''" align=center>
  <table  class= common>
   	<tr  class= common>
  	  <td text-align: left colSpan=1 >
				<span id="spanCollectorGrid" ></span> 
			</td>
		</tr>
	</table>     
</div>

    <p><INPUT VALUE="��  ��" class= cssButton TYPE=button onClick="parent.close();"></P>
    <!--��ȡ��Ϣ-->
    
    <Input type=hidden id="AgentCode" name="AgentCode"><!--�շ�Ա���-->
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
