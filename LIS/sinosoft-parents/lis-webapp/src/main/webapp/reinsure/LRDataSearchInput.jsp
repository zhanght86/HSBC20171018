<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：LRSearchInput.jsp
//程序功能：
//创建日期：2008-2-7
//创建人  ：zhangbin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<head>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="LRDataSearchInput.js"></SCRIPT> 
	<%@include file="LRDataSearchInit.jsp"%>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css> 
</head>

<body onload="initElementtype();initForm();">    
  <form action="" method=post name=fm target="fraSubmit" >
    <div style="width:200">
      <table class="common">
        <tr class="common">
          <td class="common">
          	<img src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessGetData);">
          </td>
          <td class="titleImg"><%="再保数据查询"%></td>
        </tr>
      </table>
  	</div>
	<Div  id= "divCessGetData" style= "display: ''" >
		<Table class= common border=0 width=100%>
			<TR>
	  	      	<td class="title5">被保人编码</td>
	  	    	<td class="input5">
	  	    		<Input class="common" name="InsuredNo" >
	  	    	</td>
	   	  		<TD  class= title5>被保人姓名</TD>
	  	      	<TD  class= input5> 
	  	      		<Input class="common" name="InsuredName" >
	  	      	</TD>
				<TD class= title5></TD>
	  			<TD class= input5 ></TD>
			</TR>
			<TR>
	  	      	<td class="title5">累计风险编号</td>
	  	    	<td class="input5">
	  				<Input class="codeno" name="AccumulateDefNO" 
		       			ondblClick="showCodeList('lraccmucode',[this,AccumulateDefName],[0,1],null,null,null,1);"
		       			onkeyup="showCodeListKey('lraccmucode',[this,AccumulateDefName],[0,1],null,null,null,1);"><Input 
		       			class= codename name= 'AccumulateDefName' >
	  	    	</td>
	  	    	<TD class= title5>业务类型</TD>
	  			<TD class= input5 >
	         			<input class=codeno readonly="readonly" name="EventType" CodeData="0|^01|新单数据|^02|续期数据|^03|保全数据|^04|理赔数据|" 
	         			ondblclick="return showCodeListEx('busynesstype', [this,EventTypeName],[0,1],null,null,null,1);" 
	         			onkeyup="return showCodeListKeyEx('busynesstype', [this,EventTypeName],[0,1],null,null,null,1);" ><input 
	         			class=codename name=EventTypeName  readonly="readonly">
		  		</TD>
		  		<TD class= title5></TD>
	  			<TD class= input5 ></TD>			
		</Table>
	</Div>
	  	
  	<br>
  	<INPUT class=cssButton  VALUE="查询" TYPE=button onClick="queryClick();">	
  	<INPUT class=cssButton  VALUE="业务数据下载" TYPE=button onClick="exportExcel();" style='display:none'>	
  	<INPUT class=cssButton  VALUE="重置" TYPE=button onClick="resetPage();">	
  	<br><br>	  	
  	<div id='divTable3'  style="display:''">
		  <table class="common">		
				<tr class="common">
					<td style="text-align:left;" colSpan=1><span id="spanRIDataGrid"></span></td>
				</tr>
			</table>
			<div align=center>
				<input VALUE="首页" TYPE="button" onclick="turnPage1.firstPage();" class="cssButton">
				<input VALUE="上一页" TYPE="button" onclick="turnPage1.previousPage();" class="cssButton">
				<input VALUE="下一页" TYPE="button" onclick="turnPage1.nextPage();" class="cssButton">
				<input VALUE="尾页" TYPE="button" onclick="turnPage1.lastPage();" class="cssButton">
			</div>
  	</div>
  	<br>
    <div style="width:200">
      <table class="common">
        <tr class="common">
          <td class="common">
          	<img src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessDetail);">
          </td>
          <td class="titleImg">分保明细数据</td>
        </tr>
      </table>
  	</div>
  	<div id='divCessDetail'  style="display:''">
		  <table class="common">		
				<tr class="common">
					<td style="text-align:left;" colSpan=1><span id="spanRIDataDetailGrid"></span></td>
				</tr>
			</table>
			<div align=center>
				<input VALUE="首页" TYPE="button" onclick="turnPage2.firstPage();" class="cssButton">
				<input VALUE="上一页" TYPE="button" onclick="turnPage2.previousPage();" class="cssButton">
				<input VALUE="下一页" TYPE="button" onclick="turnPage2.nextPage();" class="cssButton">
				<input VALUE="尾页" TYPE="button" onclick="turnPage2.lastPage();" class="cssButton">
			</div>
  	</div> 	
  	
	<INPUT class=cssButton   VALUE="明细数据下载"  TYPE=button  onClick="DownLoadExcel();" style="display:''">
    <input type="hidden" name=OperateType value="">
    <input type="hidden" name="Operator" value="<%=Operator%>">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 