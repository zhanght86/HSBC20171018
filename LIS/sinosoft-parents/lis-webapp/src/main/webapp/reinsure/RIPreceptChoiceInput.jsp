<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :ReComManage.jsp
//function :ReComManage
//Creator :
//date :2006-08-14
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.reinsure.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "./RIPreceptChoiceInput.js"></SCRIPT> 
<%@include file="./RIPreceptChoiceInit.jsp"%>
</head>
<body  onload="initElementtype();initForm();" >
	<form action="" method=post name=fm target="fraSubmit">
		
		<table>
			<tr>
				<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempCessConclusion1);"></td>
				<td class= titleImg>�ٱ���ͬ</td>
			</tr>
		</table>
		<Div id= "divTempCessConclusion1" style= "display: ''">
			<Table class= common>
				<TR class= common>
					<TD class= title5>�ٱ���ͬ���</TD>
					<TD class= input5>
						<Input class="codeno" name="RIContNoPage" style="width:37.74%"
						ondblClick="showCodeList('ricontno',[this,RIContNamePage],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('ricontno',[this,RIContNamePage],[0,1],null,null,null,1);" style= "display: ''"><Input 
						class= codename name= 'RIContNamePage' style="width:55.7%"   style= "display: ''">
					</TD>
					<TD class= title5></TD><TD class= input5></TD>
					<TD class= title5></TD><TD class= input5></TD>
				</TR> 
			</Table>
  			<BR><HR>
		</Div>
		<table>
			<tr class= common>
				<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPrecept);"></td>
				<td class= titleImg>�ٱ�����</td></tr>
		</table>
		<Div id= "divPrecept" style= "display: ''" align = center>
			<table  class= common>
				<tr  class= common>
					<td style="text-align:left;" colSpan=1><span id="spanPreceptSearchGrid" ></span></td>
				</tr>
			</table>
			<table>
			      <tr>
			          <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
			          <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
			          <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
			          <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
			      </tr>
			</table>
			<BR><HR>	
		</Div>
		<table>
			<tr class= common>
				<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPreceptInfo);"></td>
				<td class= titleImg>�ٱ�������Ϣ</td></tr>
		</table>
		<Div id= "divPreceptInfo" style= "display: ''" align = center>
			<table  class= common>
				<tr  class= common>
					<td style="text-align:left;" colSpan=1><span id="spanPreceptInfoGrid" ></span></td>
				</tr>
			</table>	
		</Div>
		<br>
		<br>	
		<input class="cssButton" type="button" style="display:''" value="�ֳ����β�ѯ" onclick="accuRiskInfo()" >
		<input class="cssButton" type="button" name='RelaTempPolBut' value="���������ٷֱ���" onclick="RelaTempPol()" style = "display:none;">
		<input class="cssButton" type="button" name='RelaGrpTempPolBut' value="���������ٷֱ���" onclick="RelaGrpTempPol()" style = "display:none;">
		<input class="cssButton" type="button" value="��  ��" onclick="ClosePage()" >
  	
	  	<Div id= "divHidden" style= "display:none;">
	  		OperateNo <input type="text" name="OperateNo" >
	  		CodeType <input type="text" name="CodeType" ><!--05:����-->
		  	OperateType <input type="text" name="OperateType" >
		  	RIContNo <input type="text" name="RIContNo" >
		  	SerialNo <input type="text" name="SerialNo" >
		  	RIPolno <input type="text" name="RIPolno" >
	  	</Div>
  	
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
