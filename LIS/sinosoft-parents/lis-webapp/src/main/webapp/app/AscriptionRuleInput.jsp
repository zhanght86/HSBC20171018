<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�AscriptionRuleInput.jsp
//�����ܣ�
//�������ڣ�2006-05-18
//������  ��
//���¼�¼��  ������    ��������    ����ԭ��/���� 
%>

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
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT> 
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="AscriptionRule.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="AscriptionRuleInit.jsp"%>  
<title>�������ֹ��������� </title>
</head>
<body onload="initForm();">
<form method=post name=fm id="fm" target="fraSubmit" action="AscriptionRuleSave.jsp">		
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
			<TD  class= input5><Input class="readonly wid" readonly name=GrpContNo id="GrpContNo"></TD>
			<TD  class= title5>�������</TD>
			<TD  class= input5><Input class="readonly wid" readonly name=ManageCom id="ManageCom"></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>����λ�ͻ���</TD>
			<TD  class= input5><Input class="readonly wid" readonly name=AppntNo id="AppntNo"></TD>
			<TD  class= title5>����λ����</TD>
			<TD  class= input5><Input class="readonly wid" readonly name=GrpName id="GrpName"></TD>
		</TR>
	</table>
</div>
</div>
		<!--input type=button class="cssButton" value="kick me" onclick="GrpPerPolDefineOld();">
	<input name="ff"-->

	<Div  id= "divAscriptionRuleOld" style= "display: ''">
	 	<table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
        		<td class= titleImg>����ӵĹ�������</td>
        	</tr>
        </table>
	    <table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1><span id="spanAscriptionRuleOldGrid" ></span> </td>
			</tr>
	    </table>
	    <div id="divAscriptionRuleGrid" style="display:none">
			<table>
    			<tr>
    				<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
    				<td class= titleImg>����������ϸ��Ϣ</td>
    			</tr>
		    </table>
    		<table  class= common>
    			<tr  class= common>
    				<td text-align: left colSpan=1><span id="spanAscriptionRuleGrid" ></span></td>
    			</tr>
    		</table>
	    </div>
    </Div>
   		<!--input type=button class="cssButton" value="kick me" onclick="GrpPerPolDefine();">
	<input name="ff"-->
		
    <Div  id= "divAscriptionRule" style= "display: ''">
	 	<table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
        		<td class= titleImg>���ƹ�������</td>
        	</tr>
        </table>
        <div class="maxbox1">
        <table>
	   		<TR  class= common>
				<TD  class= title5>Ա�����</TD>
				<TD  class= input5><Input class="common wid" name=AscriptionRuleCode id="AscriptionRuleCode" maxlength=2></TD>
				<TD  class= title5>����˵��</TD>
				<TD  class= input5><Input class="common wid" name=AscriptionRuleName id="AscriptionRuleName"></TD>
			</TR>
		</table>
		</div>
		<br>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1><span id="spanAscriptionRuleNewGrid" ></span> </td>
			</tr>
		</table>
    </Div>
    <a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>
	<!-- <INPUT VALUE="��һ��" class =cssButton  TYPE=button onclick="returnparent();"> -->
	<Div  id= "divRiskPlanSave" style= "display: ''" align= right> 
		<a href="javascript:void(0)" class=button onclick="submitForm();">�������򱣴�</a>
		<a href="javascript:void(0)" class=button onclick="updateClick();">���������޸�</a>
		<a href="javascript:void(0)" class=button onclick="DelContClick();">��������ɾ��</a>
		<!-- <INPUT VALUE="�������򱣴�" class =cssButton  TYPE=button onclick="submitForm();">
		<INPUT VALUE="���������޸�" class =cssButton  TYPE=button onclick="updateClick();">
		<INPUT VALUE="��������ɾ��" class =cssButton  TYPE=button onclick="DelContClick();"> -->
	</Div>
	<Input type=hidden name=ProposalGrpContNo id="ProposalGrpContNo">
	<input type=hidden name=mOperate id="mOperate">
	<input type=hidden name=AscriptionRuleCodeOld id="AscriptionRuleCodeOld">
	<input type=hidden name=AscriptionRuleNameOld id="AscriptionRuleNameOld">
</form>
<br>
<br>
<br>
<br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
