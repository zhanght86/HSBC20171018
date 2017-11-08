<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：ReInsureInput.jsp
//程序功能：再保审核功能
//创建日期：2006-11-19 11:10:36
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <!--<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>-->
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="ReInsureInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ReInsureInit.jsp"%>
  <title>再保信息</title>
</head>
<body  onload="initForm('<%=tOperate%>', '<%=tPolNo%>');" >
  <form method=post name=fm target="fraSubmit" action= "./ReInsureSave.jsp">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCReInsure);">
    		</td>
    		<td class= titleImg>
再保规则
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCReInsure" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align:left;" colSpan=1 >
					<span id="spanReInsureGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<INPUT type= "hidden" name= "ProposalNoHide" value= "">				
    <INPUT type= "hidden" name= "PolNo" value= "">
    <INPUT type= "hidden" name= "ContNo" value= "">
		<INPUT type= "hidden" name= "RunRuleFlag" value= "">
	</div>
	<table class = common> 
	  <td class = common align="right">    
      <input value="运行再保规则" class=CssButton type=button onclick="AutoReInsure();">				
    </td>
  </table>
  <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divShowInfo);">
    	   	</td>
    		  <td class= titleImg>
责任信息
    	   	</td>
    	</tr>
  </table>
  <Div  id= "divShowInfo" style= "display: ''">
		<Div  id= "divLCDuty1" style= "display: ''">
	    <table  class= common>
	      <tr  class= common>
	      	<td style="text-align:left;" colSpan=1 >
				  	<span id="spanRiskInfoGrid" ></span> 
				  </td>
			  </tr>
			</table>   
	  </div>   
	  <Div  id="divLLClaim" style= "display: ''">  
	    <table  class= common>
	      <tr  class= common>
	    		<td style="text-align:left;" colSpan=1 >
				  	<span id="spanClaimInfoGrid"></span> 
				  </td>
				</tr>
			</table> 
		</Div>
	</Div>
	<Div id= "div1" align="center" style= "display: '' ">
		<INPUT VALUE="首页" class=cssButton TYPE=button onclick="turnPage1.firstPage();"> 
	  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage1.previousPage();"> 					
	  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage1.nextPage();"> 
	  <INPUT VALUE="尾页" class=cssButton TYPE=button onclick="turnPage1.lastPage();">  
	</Div>
  <table>
  	<tr>
      	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDiskInput);">
  		</td>
  		<td class= titleImg>
审核意见
  		</td>
  	</tr>
  </table>  
  <div id="divDiskInput" style="display:''">
  <table>     
     <TD  class= common>
       <textarea name="Remark" cols="120" rows="3" class="common" onfocus=clearData() verify="审核意见|len<=1000">
       </textarea>
     </TD>
  </table>
	</form>
	<form action="./UpLodeSave.jsp" method=post name=fmImport target="fraSubmit" ENCTYPE="multipart/form-data">
	   <TABLE class = common border=0>    	
		  <TR>
		      <TD  width='10%' style="font:9pt">
选择导入的文件：
		      </TD>     
		      <TD  width='50%'>
		        <Input  type="file"　width="80%" name=FileName class= common>
		       <INPUT VALUE="上载附件" class=cssButton TYPE=hidden onclick="ReInsureUpload();">
		    	</TD>
	      <TD class = common align="right" width='40%'>
					<INPUT VALUE="发送再保审核" class=cssButton TYPE=button onclick="SendUWReInsure();">
			    <input type="hidden" class= Common name= FilePath value= "">
					<INPUT VALUE="&nbsp;&nbsp;办&nbsp;&nbsp;&nbsp;&nbsp;结&nbsp;&nbsp;" 
					class=cssButton TYPE=button onclick="ReInsureOver();">
	      </TD>
	    </TR>    
		</TABLE>  
		</div>	
	  <INPUT TYPE=hidden name="OpeData">
	  <INPUT TYPE=hidden name="OpeFlag">
	  
	</form>
	<hr>
	
	<form method=post name=fmInfo target="fraSubmit" action= "">
  <table>
		<tr>
	    	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCReInsureAudit);">
			</td>
			<td class= titleImg>
再保审核任务
			</td>
		</tr>
  </table>
	<Div  id= "divLCReInsureAudit" style= "display: ''">
    <table  class= common>
     	<tr  class= common>
     		<td style="text-align:left;" colSpan=1 >
					<span id="spanReInsureAuditGrid" >
					</span> 
				</td> 
			</tr> 
		</table> 
  </div> 
  <Div id= "div1" align="center" style= "display: '' ">
		<INPUT VALUE="首页" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
	  <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 
	  <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
	  <INPUT VALUE="尾页" class=cssButton TYPE=button onclick="turnPage2.lastPage();"> 
	</Div> 
  <table>
  	<tr>
      	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divShowLCDuty);">
  		</td>
  		<td class= titleImg>
审核/回复信息
  		</td>
  	</tr>
  </table>  
  <div id=divShowLCDuty>
	  <table>     
	     <TD  class= common>
	       <textarea name="RemarkInfo" cols="120" rows="3" readonly="readonly" class="common" verify="审核意见|len<=1000"></textarea>
	     </TD>
	  </table>
	</div>
	<div id=divButton1  align="right">
		<INPUT VALUE="&nbsp;&nbsp;下&nbsp;&nbsp;&nbsp;&nbsp;载&nbsp;&nbsp;" 
						class=cssButton TYPE=button onclick="DownLoad();">
	</div>
  </form>  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  

    
</body>
</html>
