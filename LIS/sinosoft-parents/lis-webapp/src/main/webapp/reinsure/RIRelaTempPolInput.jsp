<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�TempReinAnswerInput.jsp
//�����ܣ��ٱ���˹���
//�������ڣ�2007-09-29 11:10:36
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
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

<SCRIPT src="RIRelaTempPolInput.js"></SCRIPT>
<%@include file="RIRelaTempPolInit.jsp"%>
<title>�ٱ��ظ�</title>
</head>
<body  onload="initElementtype();initForm();" >
<form method=post name=fm target="fraSubmit" action= "" >
  <%@include file="../common/jsp/InputButton.jsp"%>
  <!--���ղ���-->
  <Div id= "divIndPart1" style= "display: ''">
  	<table>
			<tr>
				<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIndPolInfo);">
				</td>
				<td class= titleImg>
������Ϣ
				</td>
			</tr>
  	</table>
		<Div  id= "divIndPolInfo" style= "display: ''">
			<Div id= "divPolInfo" style= "display: ''">
				<Table class= common>
  		    <TR>
  		    	<TD class= title5>
�ٱ���ͬ���
  		 			</TD>
  		 			<TD class= input5 >
  		 				<Input class=readonly name= RIContNo readonly="readonly" > 
  		 			</TD>
  		 			<TD class= title5>
�ٷַ������
  		    	</TD>
  		    	<TD class= input5>
  		    		<Input class=readonly name= RIPreceptNo readonly="readonly" > 
  		    	</TD>
  		    </TR>
  		  </Table>
  		</Div>
			
  	  <table  class= common>
  	   	<tr  class= common>
  	   		<td style="text-align:left;" colSpan=1 >
						<span id="spanIndTempListGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
			<Div id= "div4" align="center" style= "display: '' ">
				<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage4.firstPage();"> 
		  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage4.previousPage();"> 					
		  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage4.nextPage();"> 
		  	<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage4.lastPage();">  
			</Div>
  	</Div>
  <!--��Ӱ�ť-->
  <Div id= "divTable1" style= "display: '' ">
  	<Table>
  		<TR>
  			<TD>
  				<INPUT VALUE="������������" class=cssButton TYPE=button onclick="TempConclusionSel();"> 
  			</TD>
  			<TD>
  				<Div id= "divRelaAll" style= "display:none;">
  					<INPUT VALUE="&nbsp;������ѯ���&nbsp;" class=cssButton TYPE=button onclick="TempConclusionAll();" NAME="RelaAll" >
					</Div>
				</TD>
				<TD></TD>
			</TR>
		</Table>
	</Div>
	<!--�����б�-->
  <table>
		<tr>
	    	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRelaList);">
			</td>
			<td class= titleImg>
�����б�
			</td>
		</tr>
  </table>
  <Div id= "divRelaList" style= "display: ''">
		<Div  id= "divIndPart2" style= "display: ''">
  	  <table  class= common>
  	   	<tr  class= common>
  	   		<td style="text-align:left;" colSpan=1 >
						<span id="spanIndRelaListGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
			<Div id= "div4" align="center" style= "display: '' ">
				<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage5.firstPage();"> 
		  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage5.previousPage();"> 					
		  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage5.nextPage();"> 
		  	<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage5.lastPage();">  
			</Div>
  	</Div>
		<Div  id= "divGrpPart2" style= "display:none;">
  	  <table  class= common>
  	   	<tr  class= common>
  	   		<td style="text-align:left;" colSpan=1 >
						<span id="spanRelaListGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
			<Div id= "div4" align="center" style= "display: '' ">
				<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage3.firstPage();"> 
		  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage3.previousPage();"> 					
		  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage3.nextPage();"> 
		  	<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage3.lastPage();">  
			</Div>
  	</Div>
  </Div>
  <Div id= "divRelaDel" style= "display: '' ">
  	<Table>
  		<TR>
  			<TD>
  				<INPUT VALUE="�ӹ�������ɾ��" name='DeleteSelButton' class=cssButton TYPE=button onclick="DeleteRelaList();"> 
  			</TD>
  			<TD>
  				<INPUT VALUE="&nbsp;ɾ��ȫ������&nbsp;" name='DeleteAllButton' class=cssButton TYPE=button onclick="DeleteRelaAll();"> 
				</TD>
				<TD>
				</TD>
			</TR>
		</Table>
	</Div>
	<hr><br>
	<INPUT VALUE="&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;"class="cssButton"  TYPE=button onclick="parent.close();">
	<Div id= "divHidden" style= "display:none;">
  	CalFeeType 	<Input class=common name=CalFeeType type='text'> 
  	CalFeeTerm 	<Input class=common name=CalFeeTerm type='text'>
  	OperateNo 	<Input class=common name=OperateNo type='text'> 
  	OperateType <Input class=common name=OperateType type='text'>
DeTailFlag_1������2������<input type="text" name="DeTailFlag">
  	SerialNo 		<input type="text" name="SerialNo">
  	RIPolno <Input class=common name="RIPolno" type='text'>
  </Div">
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
