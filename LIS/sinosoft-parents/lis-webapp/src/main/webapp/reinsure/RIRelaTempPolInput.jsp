<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：TempReinAnswerInput.jsp
//程序功能：再保审核功能
//创建日期：2007-09-29 11:10:36
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
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
<title>再保回复</title>
</head>
<body  onload="initElementtype();initForm();" >
<form method=post name=fm target="fraSubmit" action= "" >
  <%@include file="../common/jsp/InputButton.jsp"%>
  <!--个险部分-->
  <Div id= "divIndPart1" style= "display: ''">
  	<table>
			<tr>
				<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIndPolInfo);">
				</td>
				<td class= titleImg>
保单信息
				</td>
			</tr>
  	</table>
		<Div  id= "divIndPolInfo" style= "display: ''">
			<Div id= "divPolInfo" style= "display: ''">
				<Table class= common>
  		    <TR>
  		    	<TD class= title5>
再保合同编号
  		 			</TD>
  		 			<TD class= input5 >
  		 				<Input class=readonly name= RIContNo readonly="readonly" > 
  		 			</TD>
  		 			<TD class= title5>
临分方案编号
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
				<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage4.firstPage();"> 
		  	<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage4.previousPage();"> 					
		  	<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage4.nextPage();"> 
		  	<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage4.lastPage();">  
			</Div>
  	</Div>
  <!--添加按钮-->
  <Div id= "divTable1" style= "display: '' ">
  	<Table>
  		<TR>
  			<TD>
  				<INPUT VALUE="向关联表中添加" class=cssButton TYPE=button onclick="TempConclusionSel();"> 
  			</TD>
  			<TD>
  				<Div id= "divRelaAll" style= "display:none;">
  					<INPUT VALUE="&nbsp;关联查询结果&nbsp;" class=cssButton TYPE=button onclick="TempConclusionAll();" NAME="RelaAll" >
					</Div>
				</TD>
				<TD></TD>
			</TR>
		</Table>
	</Div>
	<!--关联列表-->
  <table>
		<tr>
	    	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRelaList);">
			</td>
			<td class= titleImg>
关联列表
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
				<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage5.firstPage();"> 
		  	<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage5.previousPage();"> 					
		  	<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage5.nextPage();"> 
		  	<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage5.lastPage();">  
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
				<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage3.firstPage();"> 
		  	<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage3.previousPage();"> 					
		  	<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage3.nextPage();"> 
		  	<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage3.lastPage();">  
			</Div>
  	</Div>
  </Div>
  <Div id= "divRelaDel" style= "display: '' ">
  	<Table>
  		<TR>
  			<TD>
  				<INPUT VALUE="从关联表中删除" name='DeleteSelButton' class=cssButton TYPE=button onclick="DeleteRelaList();"> 
  			</TD>
  			<TD>
  				<INPUT VALUE="&nbsp;删除全部关联&nbsp;" name='DeleteAllButton' class=cssButton TYPE=button onclick="DeleteRelaAll();"> 
				</TD>
				<TD>
				</TD>
			</TR>
		</Table>
	</Div>
	<hr><br>
	<INPUT VALUE="&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;"class="cssButton"  TYPE=button onclick="parent.close();">
	<Div id= "divHidden" style= "display:none;">
  	CalFeeType 	<Input class=common name=CalFeeType type='text'> 
  	CalFeeTerm 	<Input class=common name=CalFeeTerm type='text'>
  	OperateNo 	<Input class=common name=OperateNo type='text'> 
  	OperateType <Input class=common name=OperateType type='text'>
DeTailFlag_1到险种2到责任<input type="text" name="DeTailFlag">
  	SerialNo 		<input type="text" name="SerialNo">
  	RIPolno <Input class=common name="RIPolno" type='text'>
  </Div">
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
