<%
//�������ƣ�PayRuleInput.jsp
//�����ܣ�
//�������ڣ�2004-08-30
//������  ��sunxy
//���¼�¼��  ������mqhu    ��������2005-04-01     ����ԭ��/���� ����Ѵ��ڽɷѹ���
%>


<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String LoadFlag = request.getParameter("LoadFlag");
String AskFlag = request.getParameter("AskFlag");
%>
<head>
<script>
GrpContNo="<%=GrpContNo%>";
var LoadFlag ="<%=request.getParameter("LoadFlag")%>";
AskFlag = "<%=AskFlag%>";
if (AskFlag == "" || AskFlag == "null" || AskFlag == null)
    AskFlag = 0;
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT> 
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="PayRuleInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="PayRuleInit.jsp"%>
<title>�������ֽɷѹ����� </title>
</head>
<body onload="initForm();">
	<form method=post name=fm id="fm" target="fraSubmit" action="PayRuleSave.jsp">		
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class= titleImg>��ͬ��Ϣ</td>
			</tr>
		</table>
		<div class="maxbox1">
		<div  id= "divFCDay" style= "display: ''">
		<table  class= common align=center>  
			<TR  class= common>
				<TD  class= title5>�����ͬ��</TD>
				<TD  class= input5>
					<Input class="readonly wid" readonly name=GrpContNo id="GrpContNo">
					<Input type=hidden name=ProposalGrpContNo id="ProposalGrpContNo">
					<input type=hidden name=mOperate id="mOperate">
				</TD>
				<TD  class= title5>�������</TD>
				<TD  class= input5>
					<Input class="readonly wid" readonly name=ManageCom id="ManageCom">
				</TD>
			</TR>
			<TR  class= common>
				<TD  class= title5>����λ�ͻ���</TD>
				<TD  class= input5>
					<Input class="readonly wid" readonly name=AppntNo id="AppntNo">
				</TD>
				<TD  class= title5>����λ����</TD>
				<TD  class= input5>
					<Input class="readonly wid"  readonly name=GrpName id="GrpName">
				</TD>
			</TR>
			</table>
		</div>
		</div>
			<!--input type=button class="cssButton" value="kick me" onclick="GrpPerPolDefineOld();">
		<input name="ff"-->

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
	    	</table>
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
       	</Div>
       		<!--input type=button class="cssButton" value="kick me" onclick="GrpPerPolDefine();">
		<input name="ff"-->
		
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
	       <div class="maxbox1">
	       <table>
       			<TR  class= common>
					<TD  class= title>Ա�����</TD>
					<TD  class= input>
						<Input class="common wid" name=PayRuleCode id="PayRuleCode" maxlength=2>
					</TD>
					<TD  class= title>����˵��</TD>
					<TD  class= input>
						<Input class="common wid" name=PayRuleName id="PayRuleName">
					</TD>
					<td class= title></td>
					<td class= input></td>
				</TR>
			</table>
			</div>
			<br>
	    	<table  class= common>
	        	<tr  class= common>
	    	  		<td text-align: left colSpan=1>
						<span id="spanPayRuleNewGrid" >
						</span> 
					</td>
				</tr>
			</table>
		</Div>
		<a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>
		<!-- <INPUT VALUE="��һ��" class =cssButton  TYPE=button onclick="returnparent();"> -->
		<Div  id= "divRiskPlanSave" style= "display: ''" align= right>
			<a href="javascript:void(0)" class=button onclick="submitForm();">�ɷѹ��򱣴�</a> 
			<a href="javascript:void(0)" class=button onclick="updateClick();">�ɷѹ����޸�</a> 
			<a href="javascript:void(0)" class=button onclick="DelContClick();">�ɷѹ���ɾ��</a> 
			<!-- <INPUT VALUE="�ɷѹ��򱣴�" class =cssButton  TYPE=button onclick="submitForm();">
			<INPUT VALUE="�ɷѹ����޸�" class =cssButton  TYPE=button onclick="updateClick();">
			<INPUT VALUE="�ɷѹ���ɾ��" class =cssButton  TYPE=button onclick="DelContClick();"> -->
		</Div>
	
	    <INPUT TYPE=hidden name=PayRuleCodeOld id="PayRuleCodeOld">
		<INPUT TYPE=hidden name=PayRuleNameOld id="PayRuleNameOld">
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
