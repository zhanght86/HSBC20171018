<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�NewGrpPolFeeWithDrowInput.jsp
//�����ܣ��µ����罻�˷�
//�������ڣ�2007-12-11
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<% 
	//Add by lujun 2006-10-8 20:49
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var ManageCom = "<%=tGI.ComCode%>"; //��¼��½����
	if(ManageCom.length != 6)
{
	//alert(manageCom);
	alert("�罻�˷Ѳ���ֻ������λ�����½��У�������ѡ����λ������½��");
}
</script>
<html>
<head>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="NewGrpPolFeeWithDrowInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="NewGrpPolFeeWithDrowInputInit.jsp"%>
</head>
<body  onload="initForm();" >
	<form name=fm id="fm" action=./NewGrpPolFeeWithDrow.jsp target=fraSubmit method=post>
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPersonSingle);">
				</td>
				<td class= titleImg>�����µ��罻�����˷�</td>
			</tr>
		</table>
		<div class="maxbox1">
		<Div id="divPersonSingle" style= "display: ''">
			<Table  class= common width="100%">
				<TR  class= common>
					<TD  class= title5>�����ͬ������</TD>
					<TD  class= input5>
						<Input class= "common wid" name=GrpContNo id="GrpContNo">
					</TD>
					<TD  class= title5>�����ͬӡˢ��</TD>
					<TD  class= input5>
						<Input class= "common wid" name=PrtNo id="PrtNo">
					</TD>
				</TR>
				<TR  class= common>
					<TD  class= title5>Ͷ����λ����</TD>
					<TD  class= input5>
						<Input class= "common wid" name=GrpName id="GrpName">
					</TD>
					<TD  class= title5>�����˱���</TD>
					<TD  class= input5>
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=AgentCode maxlength=10 onclick="return queryAgent();" ondblclick="return queryAgent();" id="AgentCode">	
					</TD>
				</TR>
			</table>
			</Div>
		</div>
			<br>
			<TR class= common>
				<td class=title>
					<a href="javascript:void(0)" class=button onclick="queryClick();">��  ѯ</a>
					<a href="javascript:void(0)" class=button onclick="submitForm();">ȷ���˷�</a>
					<!-- <input class="cssButton" value="��  ѯ" type=button onclick="queryClick()">
					<INPUT class="cssButton" VALUE="ȷ���˷�" TYPE=button onclick="submitForm()"> -->
				</td>
				<td class=input>						
					�뵽�ۺϴ�ӡ��ӡ֪ͨ��					
				</TD>
			</TR>			
		<br>
		<Table>
	    	<TR>
	        	<TD class=common>
		           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFee1);">
	    		</TD>
	    		<TD class= titleImg>
	    			 �罻�����˷���Ϣ
	    		</TD>
	    	</TR>
    	</Table>    	
    	<Div  id= "divFinFee1" style= "display: ''" align=center>
	       <Table  class= common>
	         <TR  class= common>
	          <TD text-align: left colSpan=1>
	            <span id="spanFinFeeWithDrawGrid" ></span> 
	  	      </TD>
	        </TR>
	       </Table>					
	       <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
	       <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
	       <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
	       <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
    	</Div>
      </table>
	<input type=hidden id="ActuGetNo" name="ActuGetNo">
	<input type=hidden name="ManageCom" value=<%=tGI.ComCode%> id="ManageCom">
	</form>
	<br>
	<br>
	<br>
	<br>
</body>
</html>
