<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�GrpCutPersonBonus.jsp
//�����ܣ��ŵ��¸��˷ֺ촦��
//�������ڣ�2005-08-15
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%	
	String tGrpContNo = "";
	String tFiscalYear = "";
	String tRiskCode = "";
	try
	{
		tFiscalYear = request.getParameter("FiscalYear");
		tGrpContNo = request.getParameter("GrpContNo");
		tRiskCode = request.getParameter("RiskCode");
	}
	catch( Exception e )
	{
		tGrpContNo = "";
		tFiscalYear = "";
	}
	loggerDebug("GrpCutPersonBonus",tGrpContNo + "  " + tFiscalYear + "  " + tRiskCode);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GrpCutPersonBonus.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpCutPersonBonusInit.jsp"%>
  <title>�����¸��˷ֺ�</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action= "./GrpCutPersonBonusChk.jsp">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
        <td class= titleImg>�ŵ���Ϣ��</td></tr>
		</table>
        <Div  id= "peer" style= "display: ''" class="maxbox1">
    <table  class= common>
				<TR  class= common>
						<TD  class= title5>������</TD> 
						<TD  class= input5><Input class="wid" class= readonly readonly name=FiscalYear id=FiscalYear value = <%=tFiscalYear%>></TD>
						<TD  class= title5>�ŵ���</TD> 
						<TD  class= input5><Input class="wid" class= readonly readonly name=GrpContNo id=GrpContNo value = <%=tGrpContNo%>></TD>
				</TR> 
				<TR  class= common>
						<TD  class= title5>������</TD> 
						<TD  class= input5><Input class="wid" class= common name=ContNo id=ContNo></TD>
				</TR> 
    </table>

      <!--<INPUT VALUE="��    ѯ" class=cssButton TYPE=button onclick = "queryGrpPol();"> -->
<a href="javascript:void(0);" class="button" onClick="queryGrpPol();">��    ѯ</a>
    <table>
				<tr>
						<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
						<td class= titleImg>�ŵ��¸��˱���</td>
				</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align ="center">
			<table  class= common>
					<tr  class= common>
							<td text-align: left colSpan=1>
								<span id="spanGrpPolGrid" ></span> 
							</td>
					</tr>
			</table>
            <center>
      <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();"> </center>					
  	</div>
  	<p>
      <!--<INPUT VALUE="�� �� �� ��" class=cssButton TYPE=button name=assignbtn onclick = "assignPerson();"> 
			<INPUT VALUE="�鿴�������������־��" class=cssButton TYPE=button onclick="viewErrLog();"> -->	
            <a href="javascript:void(0);" class="button"  name=assignbtn onClick="assignPerson();">�������</a>
            <a href="javascript:void(0);" class="button" onClick="viewErrLog();">�鿴�������������־��</a>
		</p>
	<INPUT type=hidden id="RiskCode" name="RiskCode" value=<%=tRiskCode%>>
  	
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
