<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：LRSearchInput.jsp
//程序功能：
//创建日期：2007-2-7
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
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css> 
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	
	<SCRIPT src="RIDataImportInput.js"></SCRIPT>
  <%@include file="./RIDataImportInit.jsp"%>
  
</head>
<%
	String currentdate=PubFun.getCurrentDate();
%>
<script type="text/javascript">
var currentDate="<%=currentdate%>"
</script>
<body onload="initElementtype();initForm();">    
  <form action="RIDataImportSave.jsp" method=post name=fm target="fraSubmit" >
    <div style="width:200">
      <table class="common">
        <tr class="common">
          <td class="common"><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessGetData);"></td>
          <td class="titleImg">再保数据迁移</td>
        </tr>
      </table>
  	</div>
    <br>
    <Div  id= "divCessGetData" style= "display: ''" >
	    <table class= common border=0 width=100%>
	      	<TR  class= common>
	          <TD  class= title5>
导入类型
	          </TD>
	          <TD class= input5 >
	       		<input class=codeno readonly="readonly" name="ProcessType" CodeData="0|^21|业务数据导入|^22|分保数据导入|^31|业务数据删除|^32|分保数据删除|" 
          	   ondblclick="return showCodeListEx('state', [this,ProcessTypeName],[0,1],null,null,null,1);" 
          	   onkeyup="return showCodeListKeyEx('state', [this,ProcessTypeName],[0,1],null,null,null,1);" verify="处理类型|NOTNULL"><input 
          	   class=codename name=ProcessTypeName readonly="readonly" elementtype=nacessary>
              </TD>
              <TD  class= title5>
	             <Div  id= "divTitle" style= "display:''">截止日期</Div>
	          </TD>
	          <TD  class= input5>
	            <Div  id= "divEndDate" style= "display:''">
	               <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" dateFormat="short" name=EndDate value =<%=currentdate%>  id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span>
	            </Div>
	          </TD>
              <TD></TD>
            </TR>
	  	</table>
	  	<br>
	  	<INPUT  class=cssButton VALUE="执  行" TYPE=Button onclick="SubmitForm();">
			&nbsp;&nbsp;
		<Div id="divList" style= "display:none;">
	  		<table>
	  	  	<tr>
	  	    	<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTaskList);"></td>
	  				<td class= titleImg>导入数据</td></tr>
	  		</table>
	  		<Div id= "divAccmulate" style= "display:none;">
					<table class="common">		
						<tr class="common">
							<td style="text-align:left;" colSpan=1><span id="spanAccmulateGrid"></span></td>
						</tr>						
					</table>
	  		</Div>
	  		<Div id= "divTaskList" style= "display:none;">
				<table class="common">		
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanTaskListGrid"></span></td>
					</tr>						
				</table>
	 			<table class="common">	
	 				<TR>			
						<TD  width='10%' style="font:9pt">选择导入的文件：</TD>     
					    <TD  width='30%'>
					      	<Input  type="file"　width="80%" name=FileName class= common>
					     	<INPUT VALUE="上载附件" class=cssButton TYPE=hidden onclick="ReInsureUpload();">
					    </TD>
					    <td class= input5></td>
					    <td class= input5></td>
				   </TR> 						
				</table>  				
	  		</Div>
		</Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
