<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String LoadFlag = request.getParameter("LoadFlag");
%>
<head>
<script>
GrpContNo="<%=GrpContNo%>";
var LoadFlag ="<%=request.getParameter("LoadFlag")%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT> 
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="PayRuleInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="PayRuleInit.jsp"%>
<title>�������ֽɷѹ����� </title>
</head>
<body onload="initForm();">
	<form method=post name=fm target="fraSubmit" action="PayRuleSave.jsp">		
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>��ͬ��Ϣ</td>
			</tr>
		</table>
		<table  class= common align=center>  
			<TR  class= common>
				<TD  class= title>�����ͬ��</TD>
				<TD  class= input>
					<Input class= common  name=GrpContNo onblur="GrpPerPolDefine(); GrpPerPolDefineOld();queryrelate();">
					
					<input type=hidden name=mOperate>
				</TD>
				<TD  class= title>&nbsp;</TD>
				<TD  class= input>
					&nbsp;
				</TD>
				<TD  class= title>&nbsp;</TD>
				<TD  class= input>
					&nbsp;
				</TD>
			</TR>
			
			</table>
			

		<Div  id= "divPayRuleOld" style= "display: ''">
	 	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
    		<td class= titleImg>
    			 ����ӵĽɷѹ���
    		</td>
    	</tr>
    	    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanPayRuleOldGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<div id="divPayRuleGrid" style="display:none">
				<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>�ɷѹ�����ϸ��Ϣ</td>
			</tr>
		</table>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanPayRuleGrid" ></span>
				</td>
			</tr>
		</table>
		</div>
       </table>
       
       </Div>
       		
		
		<Div  id= "divPayRule" style= "display: ''">
	 	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
    		<td class= titleImg>
    			 ���ƽɷѹ���
    		</td>
    	</tr>
       </table>
       			<TR  class= common>
				<TD  class= title>Ա�����</TD>
				<TD  class= input>
					<Input class=common name=PayRuleCode maxlength=2>
				</TD>
				<TD  class= title>����˵��</TD>
				<TD  class= input>
					<Input class=common name=PayRuleName>
				</TD>
			</TR>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanPayRuleNewGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
		<!--INPUT VALUE="��һ��" class =cssButton  TYPE=button onclick="returnparent();"-->
	<Div  id= "divRiskPlanSave" style= "display: ''" align= right> 
		<INPUT VALUE="�ɷѹ��򱣴�" class =cssButton  TYPE=button onclick="submitForm();">
		<INPUT VALUE="�ɷѹ����޸�" class =cssButton  TYPE=button onclick="updateClick();">
		<INPUT VALUE="�ɷѹ���ɾ��" class =cssButton  TYPE=button onclick="DelContClick();">
	</Div>
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
