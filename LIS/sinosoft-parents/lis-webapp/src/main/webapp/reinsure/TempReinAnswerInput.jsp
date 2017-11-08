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
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src="TempReinAnswerInput.js"></SCRIPT>
<%@include file="TempReinAnswerInit.jsp"%>
<title>再保回复</title>
</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" action= "" ENCTYPE="multipart/form-data">
  <%@include file="../common/jsp/InputButton.jsp"%>
  <!--再保审核类型选择-->
  <Div id="divTempCessInter" style="display: ''">
		<Div id="divAuditType" style="display:none;">
			<Table> 
	  		<tr>
	  	    	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRiskInfo);">
	  		   	</td>
	  			  <td class= titleImg>
再保审核类型
	  		   	</td>
	  		</tr>
			</Table>
			<Table class= common>
	 			<TR class= common>
	 				<TD class= title5>
再保审核类型
	 				</TD>
	 				<TD class= input5>
	 					<input class=codeno readonly="readonly" name="AuditType" CodeData="0|^01|新单|^03|保全|^04|理赔|" 
	  	      ondblclick="return showCodeListEx('audittype', [this,AuditTypeName],[0,1],null,null,null,1);" 
	  	      onkeyup="return showCodeListKeyEx('audittype', [this,AuditTypeName],[0,1],null,null,null,1);" ><input 
	  	      class=codename name=AuditTypeName readonly="readonly" elementtype=nacessary>
	 				</TD>
	 				<td class="input5"></td>
	 				<TD class= title5></TD>
	  	    <TD class= input5></TD>
	  	  </TR> 
	  	</Table>
	  </Div>
	  
	  <Table>
		  <tr>
		    <td class=common>
			  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTaskLiskGrid);">
		    </td>
		  	<td class= titleImg>
任务列表
		    </td>
		  </tr>
		</Table>
		<Div  id= "divTaskLiskGrid" style= "display: ''">
		  <table  class= common>
		    	<tr  class= common>
		    		<td style="text-align:left;" colSpan=1 >
					  <span id="spanTaskLiskGrid" >
					  </span> 
				    </td>
			  </tr>
			</table>   
			<Div id= "div31" align="center" style= "display: '' ">
				<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
			  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
			  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
			  <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();">  
			</Div>
		</Div>
	</Div>
	<br><hr><br>
	
  <!--个单信息-->
	<Div id="divInd1" style="display: ''">
			<Div id="divAudit" style="display: ''">
				<table>
				  <tr>
				    <td class=common>
					  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAuditInfoGrid);">
				    </td>
				  	<td class= titleImg>
核保/核赔结果列表
				    </td>
				  </tr>
				</table>
				<Div  id= "divAuditInfoGrid" style= "display: ''">
				  <table  class= common>
				    	<tr  class= common>
				    		<td style="text-align:left;" colSpan=1 >
							  <span id="spanAuditInfoGrid" >
							  </span> 
						    </td>
					  </tr>
					</table>   
					<Div id= "div15" align="center" style= "display: '' ">
						<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage1.firstPage();"> 
					  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage1.previousPage();"> 					
					  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage1.nextPage();"> 
					  <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage1.lastPage();">  
					</Div>
				</div>
			</Div>
			<Div id="divRiskInfoArea" style="display: ''">
				<table>
				  <tr>
				    <td class=common>
					  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRiskInfo);">
				    </td>
				  	<td class= titleImg>
任务信息
				    </td>
				  </tr>
				</table>
				<Div  id= "divRiskInfo" style= "display: ''">
				  <table  class= common>
				    	<tr  class= common>
				    		<td style="text-align:left;" colSpan=1 >
							  <span id="spanRiskInfoGrid" >
							  </span> 
						    </td>
					  </tr>
					</table>   
					<Div id= "div11" align="center" style= "display: '' ">
						<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
					  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 					
					  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
					  <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();">  
					</Div>
				</div>
			</Div>
	</Div>
	
	<!--团体信息-->
	<Div id="divGrpInfo" style="display: ''">
		<table>
			<tr>
		    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpTempInsuList);">
				</td>
				<td class= titleImg>
团体临分申报列表
				</td>
			</tr>
  	</table>
		<Div  id= "divGrpTempInsuList" style= "display: ''">
  	  <table  class= common>
  	   	<tr  class= common>
  	   		<td style="text-align:left;" colSpan=1 >
						<span id="spanGrpTempInsuListGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
			  <Div id= "div12" align="center" style= "display: '' ">
					<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage7.firstPage();"> 
				  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage7.previousPage();"> 					
				  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage7.nextPage();"> 
				  <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage7.lastPage();">  
				</Div>
  	</div> 
		<hr>
		<table>
			<tr>
		    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpUWResult);">
				</td>
				<td class= titleImg>
团单临分自核结果
				</td>
			</tr>
  	</table>
		<Div  id= "divGrpUWResult" style= "display: ''">
  	  <table  class= common>
  	   	<tr  class= common>
  	   		<td style="text-align:left;" colSpan=1 >
						<span id="spanGrpUWResultGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
			  <Div id= "div13" align="center" style= "display: '' ">
					<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage4.firstPage();"> 
				  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage4.previousPage();"> 					
				  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage4.nextPage();"> 
				  <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage4.lastPage();">  
				</Div>
  	</div> 
  	
		<table>
			<tr>
		    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIndUWResult);">
				</td>
				<td class= titleImg>
个人临分自核结果
				</td>
			</tr>
  	</table>
		<Div  id= "divIndUWResult" style= "display: ''">
  	  <table  class= common>
  	   	<tr  class= common>
  	   		<td style="text-align:left;" colSpan=1 >
						<span id="spanIndUWResultGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
			<Div id= "div42" align="center" style= "display: '' ">
				<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage5.firstPage();"> 
		  	<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage5.previousPage();"> 					
		  	<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage5.nextPage();"> 
		  	<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage5.lastPage();">  
			</Div>
  	</Div> 
  	
		<br><hr><br>
		
		<table>
			<tr>
			  	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
				</td>
				<td class= titleImg>
临分申请保单查询条件
				</td>
			</tr>
  	</table>
		<Div  id= "divSearch" style= "display: ''">
			<Table class= common>
  	    <TR>
  	    	<TD class= title5>
集体投保单号
  	 			</TD>
  	 			<TD class= input5 >
  	 				<Input class= common name= GrpContNo readonly="readonly" > 
  	 			</TD>
  	 			<TD class= title5>
保障计划
  	    	</TD>
  	    	<TD class= input5>
							<Input class="codeno" name='ContPlanCode'
				      ondblClick="showCodeList('rigrpcontplan',[this,ContPlanCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
				      onkeyup="showCodeListKey('rigrpcontplan',[this,ContPlanCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);" ><Input 
				      class= codename name='ContPlanCodeName' >
  	    	</TD>
  	    	<td  text-align:right class="title5">
险种代码
  	    	</td>
  	    	<td class="input5">
						<Input class="codeno" name='RiskCode'
				      ondblClick="showCodeList('rigrpcontrisk',[this,RiskCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
				      onkeyup="showCodeListKey('rigrpcontrisk',[this,RiskCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);" ><Input 
				      class= codename name='RiskCodeName'  >
  	    	</td>
  	    </TR>
  	    <TR>
  	    	<TD class= title5>
责任代码
  	 			</TD>
  	 			<TD class= input5 >
  	 				<Input class="codeno" name='DutyCode'
				      ondblClick="showCodeList('rigrpcontduty',[this,DutyCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
				      onkeyup="showCodeListKey('rigrpcontduty',[this,DutyCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);" ><Input 
				      class= codename name='DutyCodeName' >
  	 			</TD>
  	 			<TD class= title5>
被保人号
  	    	</TD>
  	    	<TD class= input5>
  	    		<Input class= common name= InsuredNo > 
  	    	</TD>
  	    	<td  text-align:right class="title5">
被保人姓名	
  	    	</td>
  	    	<td class="input5">
  	    		<Input class= common name= InsuredName> 
  	    	</td>
  	    </TR>
  	  </Table>
  	</Div> 
  	<br>
  	<INPUT VALUE="查  询" class=cssButton TYPE=button onclick="SearchRecord();">  &nbsp;&nbsp;&nbsp;&nbsp;
  	<INPUT VALUE="重  置" class=cssButton TYPE=button onclick="ResetForm();"><br><br>
  	<table>
			<tr>
		    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchResult);">
				</td>
				<td class= titleImg>
查询结果
				</td>
			</tr>
  	</table>
		<Div  id= "divSearchResult" style= "display: ''">
  	  <table  class= common>
  	   	<tr  class= common>
  	   		<td style="text-align:left;" colSpan=1 >
						<span id="spanSearchResultGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
			<Div id= "div41" align="center" style= "display: '' ">
				<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage8.firstPage();"> 
		  	<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage8.previousPage();"> 					
		  	<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage8.nextPage();"> 
		  	<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage8.lastPage();">  
			</Div>
  	</Div> 
  </Div>
  <br><hr><br>
  
  <table>
  	<tr>
    	<td class=common>
	    	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAnswerIdea);">
  		</td>
  		<td class= titleImg>回复意见</td>
  </tr>
  </table>   
  <Div  id= "divAnswerIdea" style= "display: ''">
    <table> 
    	<tr>   
           <TD  class= common>
             <textarea name="Remark" value="在此录入回复意见" cols="120" rows="3" class="common" onfocus=clearData() onBlur=recoverData() verify="回复意见|len<=1000">
             </textarea>
           </TD>               
      </tr>
    </table>
	</Div>
	<input type="hidden" name="ProposalGrpContNo">
</form>
  	
<form action="" method=post name=fmImport target="fraSubmit" ENCTYPE="multipart/form-data">
	<div id="divDiskInput" style="display:''">
	  <table class = common >    	
		  <TR>
		    <TD  width='10%' style="font:9pt">
选择导入的档：
		    </TD>     
		    <TD  width='30%'>
		      	<Input  type="file"　width="80%" name=FileName class= common>
		     		<INPUT VALUE="上载附件" class=cssButton TYPE=hidden onclick="ReInsureUpload();">
		    </TD>
		    <TD  width='20%' style="font:9pt"  align="left">
		     	<INPUT VALUE="回 复 意 见" class=cssButton TYPE=button onclick="SendUWReInsure();">
		    </TD>
		    <TD  width='50%' style="font:9pt"  align="left">
		     	<INPUT VALUE="临 分 结 论" name="ConclusionButton" style= "display:none;" 
		     	class=cssButton TYPE=button onclick="TempCessConclusion();">
		    </TD>
		   </TR>
	  </table>
	</div>
</form>	
<hr>
<form action="" method=post name=fmInfo target="fraSubmit" ENCTYPE="multipart/form-data">
  <table>
	  <tr>
	    	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divReInsureTask);">
	  	</td>
	  	<td class= titleImg>
再保审核任务
	  	</td>
	  </tr>
  </table>
	<Div  id= "divReInsureTask" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align:left;" colSpan=1 >
					<span id="spanReInsureAuditGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<Div id= "div14" align="center" style= "display: '' ">
			<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage6.firstPage();"> 
	    <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage6.previousPage();"> 					
	    <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage6.nextPage();"> 
	    <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage6.lastPage();">  
		</Div>
	  
	  <table>
	  	<tr>
	      	<td class=common>
			    <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSendAnswerInfo);">
	  		</td>
	  		<td class= titleImg>
发送/回复意见信息
	  		</td>
	  	</tr>
	  </table>
	  <Div  id= "divSendAnswerInfo" style= "display: ''">
		  <table> 
		    <tr>   
		      <TD  class= common>
		        <textarea name="SendAnswerRemark" value="" cols="120" rows="3" class="common" verify="审核意见|len<=1000" readonly="readonly" >
		        </textarea>
		      </TD>               
		    </tr>
		  </table>
		</Div>
  </Div>  
  <table class = common>  
	  <TR class = common >
	    <td class = common align="left">
		    <input type="hidden" class= Common name= FilePath value= "">
		    <INPUT VALUE="&nbsp;下&nbsp;&nbsp;载&nbsp;" class=cssButton TYPE=button onclick="DownLoad();">
				<INPUT VALUE="办 结" class=cssButton onclick="ReInsureOver();" type=hidden >
	    </td>
	  </TR>
	</table>
  <input type="hidden" name="PrtNo">
  <input type="hidden" name="OpeFlag">
	<input type="hidden" name="DutyCode">
  <input type="hidden" name="ProposalNo">
  <input type="hidden" name="ContType">
  <input type="hidden" name="DeTailFlag">
</form>  


<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
