<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
	<%
		//程序名称：RIUWTaskQueryInput.jsp
		//程序功能：再保审核任务查询功能
		//创建日期：2008-10-20 
		//创建人  ：caoshuguo
		//更新记录：  更新人    更新日期     更新原因/内容
	%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		
		<SCRIPT src="RIUWTaskQueryInput.js"></SCRIPT>
		<%@include file="RIUWTaskQueryInit.jsp"%>

		<title>再保审核任务查询</title>
	</head>
	<body onload="initElementtype();initForm();" action="" ENCTYPE="multipart/form-data">    
		<form action="" method=post name=fm target="fraSubmit" >
			<Table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSearch);">
					</td>
					<td class=titleImg>查询条件</td>
				</tr>
			</Table><br>

			<Div id="divSearch" style="display: ''">								
				<table class=common>
					<Tr class=common>
						<TD  class= title5>临分状态</TD>
				       	<TD class= input5 >
					       <input class=codeno readonly="readonly" name="nbStateType" CodeData="0|^02|临分审核|^03|<%="临分处理"%>|^04|处理完毕|" 
				          	ondblclick="return showCodeListEx('state1', [this,nbStateName],[0,1],null,null,null,1);" 
				          	onkeyup="return showCodeListKeyEx('state1', [this,nbStateName],[0,1],null,null,null,1);" verify="处理类型|NOTNULL"><input 
				          	class=codename name=nbStateName readonly="readonly" >
				        </TD>
						<TD class=title5>印刷号</TD>
						<TD class=input5><Input class=common name=nbPrtNo></TD>					
						<TD class=title5>合同号</TD>
						<TD class=input5><Input class=common name=ContNo></TD>
					</Tr>
				</table>
			</Div>
			<Div id="divCLSearch" style="display: ''">								
				<table class=common>
					<Tr class=common>
						<TD  class= title5>任务状态</TD>
				       	<TD class= input5 >
					       <input class=codeno readonly="readonly" name="lcStateType" CodeData="0|^02|理赔审核|^03|审核完毕|" 
				          	ondblclick="return showCodeListEx('state2', [this,lcStateName],[0,1],null,null,null,1);" 
				          	onkeyup="return showCodeListKeyEx('state2', [this,lcStateName],[0,1],null,null,null,1);" verify="处理类型|NOTNULL"><input 
				          	class=codename name=lcStateName readonly="readonly" >
				        </TD>
						<TD class=title5>印刷号</TD>
						<TD class=input5><Input class=common name=lcPrtNo></TD>
						<TD class=title5>赔案号</TD>
						<TD class=input5><Input class=common name=ClmNo></TD>
					</Tr>
				</table>
			</Div><br>				
			<INPUT VALUE="查  询" class=cssButton TYPE=button onClick="SearchRecord();">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<INPUT VALUE="重  置" class=cssButton TYPE=button onClick="ResetForm();">
			<br><br>			
			<Table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divTaskLiskGrid);">
					</td>
					<td class=titleImg>任务列表</td>
				</tr>
			</Table>
			<Div id="divTaskLiskGrid" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1>
							<span id="spanTaskLiskGrid"> </span>
						</td>
					</tr>
				</table>
				<Div id="div31" align="center" style="display: '' ">
					<INPUT VALUE="首  页" class=cssButton TYPE=button
						onclick="turnPage.firstPage();">
					<INPUT VALUE="上一页" class=cssButton TYPE=button
						onclick="turnPage.previousPage();">
					<INPUT VALUE="下一页" class=cssButton TYPE=button
						onclick="turnPage.nextPage();">
					<INPUT VALUE="尾  页" class=cssButton TYPE=button
						onclick="turnPage.lastPage();">
				</Div>
			</Div>					
			<br><hr><br>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;"
							OnClick="showPage(this,divAuditInfoGrid);">
					</td>
					<td class=titleImg>
核保/核赔结果列表
					</td>
				</tr>
			</table>
			<Div id="divAuditInfoGrid" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1>
							<span id="spanAuditInfoGrid"> </span>
						</td>
					</tr>
				</table>
			</Div>
			<Div id="div15" align="center" style="display: '' ">
				<INPUT VALUE="首  页" class=cssButton TYPE=button
					onclick="turnPage1.firstPage();">
				<INPUT VALUE="上一页" class=cssButton TYPE=button
					onclick="turnPage1.previousPage();">
				<INPUT VALUE="下一页" class=cssButton TYPE=button
					onclick="turnPage1.nextPage();">
				<INPUT VALUE="尾  页" class=cssButton TYPE=button
					onclick="turnPage1.lastPage();">
			</Div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;"
							OnClick="showPage(this,divRiskInfo);">
					</td>
					<td class=titleImg>任务信息</td>
				</tr>
			</table>
			<Div id="divRiskInfo" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1>&nbsp;
							<span id="spanRiskInfoGrid"> </span>
						</td>
					</tr>
				</table>
				<Div id="div11" align="center" style="display: '' ">
					<INPUT VALUE="首  页" class=cssButton TYPE=button
						onclick="turnPage2.firstPage();">
					<INPUT VALUE="上一页" class=cssButton TYPE=button
						onclick="turnPage2.previousPage();">
					<INPUT VALUE="下一页" class=cssButton TYPE=button
						onclick="turnPage2.nextPage();">
					<INPUT VALUE="尾  页" class=cssButton TYPE=button
						onclick="turnPage2.lastPage();">
				</Div>
			</Div>			
			<br><hr><br>		
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;"
							OnClick="showPage(this,divReInsureTask);">
					</td>
					<td class=titleImg>再保审核任务</td>
				</tr>
			</table>
			<Div id="divReInsureTask" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1>
							<span id="spanReInsureAuditGrid"> </span>
						</td>
					</tr>
				</table>
				<Div id="div14" align="center" style="display: '' ">
					<INPUT VALUE="首  页" class=cssButton TYPE=button
						onclick="turnPage3.firstPage();">
					<INPUT VALUE="上一页" class=cssButton TYPE=button
						onclick="turnPage3.previousPage();">
					<INPUT VALUE="下一页" class=cssButton TYPE=button
						onclick="turnPage3.nextPage();">
					<INPUT VALUE="尾  页" class=cssButton TYPE=button
						onclick="turnPage3.lastPage();">
				</Div>
			</Div>			
			<table>
			  	<tr>
			      	<td class=common>
					    <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSendAnswerInfo);">
			  		</td>
			  		<td class= titleImg>申请/回复意见信息</td>
			  	</tr>
	  		</table>
		  	<Div  id= "divSendAnswerInfo" style= "display: ''">
			  <table> 
			    <tr>   
			      <TD  class= common>
			        <textarea name="SendAnswerRemark" value="" cols="120" rows="3" class="common" verify="审核意见|len<=1000" readonly="readonly" ></textarea>
			      </TD>               
			    </tr>
			  </table>
			</Div> 			
			<input type="hidden" name="PrtNo">
			<input type="hidden" name="OpeFlag">
			<input type="hidden" name="DutyCode">			
			<input type="hidden" name="ProposalNo">
			<input type="hidden" name="AuditType">
			<input type="hidden" name="ContType">
			<input type="hidden" name="DeTailFlag">
		</form>	
		<form action="" method=post name=fmImport target="fraSubmit" ENCTYPE="multipart/form-data">
			<div id="divDiskInput" style="display:''">
				<table class = common>  
				  <TR class = common >
				    <td class = common align="left">
					    <input type="hidden" class= Common name= FilePath value= "">
					    <INPUT VALUE="&nbsp;下&nbsp;&nbsp;载&nbsp;" class=cssButton TYPE=button onclick="DownLoad();">
				    </td>
				  </TR>
				</table>
			</div>
		</form>			
		<span id="spanCode" style="display: none; position:absolute; slategray"></span>
	</body>
</html>
