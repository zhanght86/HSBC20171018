<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：LRBsnsBillUWInput.jsp
//程序功能：
//创建日期：2007-2-8
//创建人  ：zhangbin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %> 

<head>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="LRBsnsBillUWInput.js"></SCRIPT> 
	<%@include file="LRBsnsBillUWInit.jsp"%>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css> 
</head>

<body onload="initElementtype();initForm();">    
  <form action="" method=post name=fm target="fraSubmit" >
    <div style="width:200">
      <table class="common">
        <tr class="common">
          <td class="common"><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessGetData);"></td>
          <td class="titleImg">业务帐单数据生成</td>
        </tr>
      </table>
  	</div>
    <br>
    <Div  id= "divCessGetData"  style= "display: ''" >
		<table class= common border=0 width=100%>
			<TR  class= common>
				<TD  class= title5>账单编号</TD>
	        	<TD  class= input5> 
	          		<Input  class='common' name=BatchNO > 
	          	</TD> 
				<TD  class= title5>起始日期</TD>
	        	<TD  class= input5> 
	          		<Input name=StartDate class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" dateFormat='short'  id="StartDate">		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<TD  class= title5>终止日期</TD>
				<TD  class= input5> 
					<Input name=EndDate class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" dateFormat='short'  id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
			</TR>    
	 
			<TR  class= common>
		      	<TD  class=title >合同名称</TD>
		        <TD  class=input > 
		        	<Input class="codeno" name="ContNo" 
				      ondblClick="showCodeList('lrcontno',[this,ContName],[0,1],null,null,null,1,260);"
				      onkeyup="showCodeListKey('lrcontno',[this,ContName],[0,1],null,null,null,1,260);" verify="合同名称|NOTNULL"><Input 
				      class= codename name='ContName' elementtype=nacessary>
		        </TD> 
		      	<TD  class= title5 >再保公司</TD>
		        <TD  class= input5 > 
		        	<Input class="codeno" name="RIcomCode" 
				      ondblClick="showCodeList('lrcompanysta',[this,RIcomName],[0,1],null,null,fm.ContNo.value,1,250);"
				      onkeyup="showCodeListKey('lrcompanysta',[this,RIcomName],[0,1],null,null,fm.ContNo.value,1,250);"  verify="再保公司|NOTNULL"><Input 
				      class= codename name= 'RIcomName' elementtype=nacessary>
		        </TD>
		        <TD  class= title5 ></TD>
		        <TD  class= input5 ></TD> 	        
			</TR>
	    </table>
	    <br>
	    <hr>
	    <br>	    	   
		<INPUT class=cssButton  VALUE="查询" TYPE=button onClick="billQuery();">			
	</Div>
	<br>
	<table>
  	  	<tr>
  	    	<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
  	    	OnClick= "showPage(this,divBatchList);"></td>
  		<td class= titleImg>账单列表</td></tr>
	</table>
	<Div id= "divBatchList" style= "display: ''">
		<table class="common">		
			<tr class="common">
				<td style="text-align:left;" colSpan=1><span id="spanBatchListGrid"></span></td>
			</tr>
		</table>
	</Div> 

	<br>
	<table>
  	  	<tr>
  	    	<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
  	    	OnClick= "showPage(this,divBillDetail);"></td>
  		<td class= titleImg>账单明细</td></tr>
	</table>
	<Div id= "divBillDetail" style= "display: ''">
		<table class="common">		
			<tr class="common">
				<td style="text-align:left;" colSpan=1><span id="spanBillDetailGrid"></span></td>
			</tr>
		</table>
	</Div> 
	<br>
	<table>
  	  	<tr>
  	    	<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
  	    	OnClick= "showPage(this,divBillUpdate);"></td>
  		<td class= titleImg>账单明细修改</td></tr>
	</table>
	<Div id= "divBillUpdate" style= "display: ''">
		<table class="common">		
			<tr class="common">
				<td style="text-align:left;" colSpan=1><span id="spanBillUpdateGrid"></span></td>
			</tr>
		</table>
	</Div> 	
	<br>
	<INPUT class=cssButton  VALUE="分保明细数据下载" TYPE=button onClick="billDown();">
	<INPUT class=cssButton  VALUE="账单修改" TYPE=button onClick="billUpdate();">		
	<br><hr>
	<table>
  	  	<tr>
  	    	<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
  	    	OnClick= "showPage(this,divBillDetail);"></td>
  		<td class= titleImg>待审核账单</td></tr>
	</table>
	<Div id= "divBillDetail" style= "display: ''">
		<table class="common">		
			<tr class="common">
				<td style="text-align:left;" colSpan=1><span id="spanAuditBillListGrid"></span></td>
			</tr>
		</table>
	</Div> 
	<table class= common>
		<TR  class= common>
			<TD  class= title5>审核结论</TD>
        	<TD  class= input5> 
	       		<input class=codeno readonly="readonly" name="RIUWReport" CodeData="0|^03|审核修改^04|审核确认" 
		          	ondblclick="return showCodeListEx('RIUWReport', [this,RIUWReportName],[0,1],null,null,null,1);" 
		          	onkeyup="return showCodeListKeyEx('RIUWReport', [this,RIUWReportName],[0,1],null,null,null,1);"><input	class=codename name=RIUWReportName readonly="readonly" >
          	
          	</TD> 
	        <TD  class= title5 ></TD>
        	<TD  class= input5 ></TD> 	
        	<TD  class= title5 ></TD>
        	<TD  class= input5 ></TD> 	         	        	
		</TR>  		
	</table>
	<br>
	<INPUT class=cssButton  VALUE="结论保存" TYPE=button onClick="SaveConclusion();">
    <input type="hidden" name=OperateType value="">
    <input type="hidden" name="Operator" value="<%=Operator%>">   
    <input type="hidden" name=jkbillno value="">
    <input type="hidden" name=jkcontno value="">
    <input type="hidden" name=jkcomno value="">
    <input type="hidden" name=jksdate value="">
    <input type="hidden" name=jkedate value="">
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 