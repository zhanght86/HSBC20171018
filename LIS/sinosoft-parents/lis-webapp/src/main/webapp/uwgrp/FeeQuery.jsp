<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="FeeQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FeeQueryInit.jsp"%>
</head>
<script language="javascript">
var cGrpContNo = "<%=request.getParameter("GrpContNo")%>";
</script>
<body onload="initForm();">
	<form method=post name=fm target="fraSubmit" action = "./FeeManage.jsp">
	  
		
		<Div id="divLCPol1" style="display: ''" >
			<table>
				<tr>
					
					<td class=titleImg>�������ֲ�ѯ���(�밴��ȡ��¼������)</td>
				</tr>
			</table>
			<table class=common>  
			<tr class=common>    
				<tr class=common> 
				<TD  class= title>
            ��ȡ��
          </TD>
          <TD  class="input">
            <Input class="common" name=Drawer elementtype=nacessary verify="����|notnull&len<=50" aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="confirmSecondInput(this,'onblur');">
          </TD>
          <TD  class="title">
            ��ȡ��ID
          </TD>
          <TD  class="input">
            <Input class="common" name=DrawerID >
          </TD>
				</tr>
					<td colspan="8" text-align: left colSpan=1><span id="spanPolGrid"></span></td>
				</tr>
				<tr>
					<td align=center colspan="6">
			<INPUT VALUE="��  ҳ" class="cssButton" TYPE=button onclick="getFirstPage();">
			<INPUT VALUE="��һҳ" class="cssButton" TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="��һҳ" class="cssButton" TYPE=button onclick="getNextPage();">
			<INPUT VALUE="β  ҳ" class="cssButton" TYPE=button onclick="getLastPage();">
					</td>
				</tr>					
			</table>
		


			<P>
			<INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="confirmform();">
			<INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="goBack();">
			
			</P>
			
			
			
			<table>
				<tr>
					
					<td class=titleImg>����������</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanHavePolGrid"></span></td>
				</tr>
						
			</table>
			
			<P>
			<INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="confirmform2();">
			<INPUT VALUE="ɾ  ��" class="cssButton" TYPE=button onclick="confirmform3();">
			<INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="goBack();">
			
			</P>
		</div>

	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
