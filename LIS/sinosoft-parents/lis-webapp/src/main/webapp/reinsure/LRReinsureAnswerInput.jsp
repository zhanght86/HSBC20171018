<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：LRReinsureAnswerInput.jsp
//程序功能：再保审核功能
//创建日期：2007-03-21 11:10:36
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
<SCRIPT src="LRReInsureAnswerInput.js"></SCRIPT>
<%@include file="LRReInsureAnswerInit.jsp"%>
<title>再保回复</title>

</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" action= "./ReInsureSave.jsp" ENCTYPE="multipart/form-data">
  <%@include file="../common/jsp/InputButton.jsp"%>
	<div id="divTempCessInter" style="display:''">
	  <table>
	  	<tr>
	      	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRiskInfo);">
	  	   	</td>
	  		  <td class= titleImg>
再保审核类型
	  	   	</td>
	  	</tr>
		</table>
		
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
	</div>

	<table>
	  <tr>
	    <td class=common>
		  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRiskInfo);">
	    </td>
	  	<td class= titleImg>
险种保单信息
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
		<Div id= "div1" align="center" style= "display: '' ">
			<INPUT VALUE="首页" class=cssButton TYPE=button onclick="turnPage1.firstPage();"> 
		  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage1.previousPage();"> 					
		  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage1.nextPage();"> 
		  <INPUT VALUE="尾页" class=cssButton TYPE=button onclick="turnPage1.lastPage();">  
		</Div>
	</div>
	
	<Div  id= "divEdorInfo" style= "display:none;">
		<table  class= common>
	    <tr  class= common>
	  		<td style="text-align:left;" colSpan=1 >
			  <span id="spanEdorInfoGrid" >
			  </span> 
			  </td>
			</tr>
		</table> 
		<Div id= "div1" align="center" style= "display: '' ">
			<INPUT VALUE="首页" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
		  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 					
		  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
		  <INPUT VALUE="尾页" class=cssButton TYPE=button onclick="turnPage2.lastPage();">  
		</Div>
	</Div>
	
	<Div  id= "divClaimInfo" style= "display:none;">
		<table  class= common>
	    <tr  class= common>
	  		<td style="text-align:left;" colSpan=1 >
			  <span id="spanClaimInfoGrid" >
			  </span> 
			  </td>
			</tr>
		</table> 
		<Div id= "div1" align="center" style= "display: '' ">
			<INPUT VALUE="首页" class=cssButton TYPE=button onclick="turnPage3.firstPage();"> 
		  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage3.previousPage();"> 					
		  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage3.nextPage();"> 
		  <INPUT VALUE="尾页" class=cssButton TYPE=button onclick="turnPage3.lastPage();">  
		</Div>
	</Div>
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
             <textarea name="Remark" value="" cols="120" rows="3" class="common" onfocus=clearData() verify="回复意见|len<=1000">
             </textarea>
           </TD>               
      </tr>
    </table>
	</Div>
</form>
  	
<form action="./UpLodeSave.jsp" method=post name=fmImport target="fraSubmit" ENCTYPE="multipart/form-data">
	<div id="divDiskInput" style="display:''">
	  <table class = common >    	
		  <TR>
		    <TD  width='10%' style="font:9pt">
选择导入的文件：
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
		<Div id= "div1" align="center" style= "display: '' ">
			<INPUT VALUE="首页" class=cssButton TYPE=button onclick="turnPage1.firstPage();"> 
	    <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage1.previousPage();"> 					
	    <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage1.nextPage();"> 
	    <INPUT VALUE="尾页" class=cssButton TYPE=button onclick="turnPage1.lastPage();">  
		</Div>
	  
	  <table>
	  	<tr>
	      	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSendAnswerInfo);">
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
  
</form>  


<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>