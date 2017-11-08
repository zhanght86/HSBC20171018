<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html>

<%
 //程序名称：RiskSaleNameInput.jsp
 //程序功能：险种名称配置入口
 //创建日期：2015-7-3
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
 String flag=request.getParameter("flag");
%>

<head>

<%
String CurrentDate = PubFun.getCurrentDate();
%>
<script type="text/javascript">
	var today00='<%=CurrentDate%>';
	var flag='<%=flag%>';
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="RiskSaleNameInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="RiskSaleNameInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
 
<body onload="initForm();">
<form action="RiskSaleNameSave.jsp" method=post name=fm target="fraSubmit">
<Table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divPrice);">
		</TD>
		<TD class=title5Img>保单险种信息</TD>
	</TR>
</Table>
<div id="divPrice" style="display: ''">
<table class=common>
	<TR class=common>
		<TD  class= title>险种编码</TD>
			<% if("0".equals(flag)){%>
			<TD class=input5><input class="common" name=RiskCode readonly="readonly"><font
			size=1 color='#ff0000'><b> *</b></font>
			</TD>
			<%}else{ %>
		    <TD  class= input ><Input class="codeno" name=RiskCode 
		     ondblclick="showCodeList('riskcode',[this,RiskCodeName],[0,1],null,null,null,1);" 
		     onkeyup="showCodeListKey('riskcode',[this,RiskCodeName],[0,1]);"><input 
		     class="codename" name=RiskCodeName id=RiskCodeName  ><font
			size=1 color='#ff0000'><b> *</b></font>
			</TD>
		     
		     <%} %>
		<TD class=title5>销售渠道</TD>
		<TD class=input5><input class=codeno name=SaleChnl
			ondblclick="return showCodeList('salechnl',[this,SaleChnlName],[0,1]);"
			onkeyup="return showCodeListKey('salechnl',[this,SaleChnlName],[0,1]);"><input
			class=codename name=SaleChnlName readonly="readonly"
			><font
			size=1 color='#ff0000'><b> *</b></font>
		</TD>
		<TD class=title5>管理机构</TD>
		<TD  class= input>
           <Input class=codeno name=ManageCom
            ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);" ><input 
            name=ManageComName class=codename  readonly="readonly" ><font
			size=1 color='#ff0000'><b> *</b></font>
         </TD>

	</TR>
	
	<TR class=common>
		<TD class=title5></TD>
		<TD class=input5><input class="common" name=RiskNameCn><font
			size=1 color='#ff0000'><b> *</b></font>
	    </TD>
	    <TD class=title5></TD>
		 <TD class=input5><input class="common" name=RiskNameEn>
	    </TD>

		<TD class=title5></TD>
	    <TD class=input5><input class="common" name=RiskNameShort >
	    </TD>
	</TR>
	<TR class=common>
		<TD class=title5></TD>
		<TD class=input5><input class="common" name=RiskNameTr>
	    </TD>
	    
	</TR>
	
</table>
<Table>
	<TR>
		<td width="10%"></td>
		<TD><INPUT VALUE="查  询" class=cssbutton TYPE=button
			onclick="easyQueryClick();"></TD>
	</TR>
</Table>
</div>

<Table>
	<TR>
		
		<TD class=title5Img>险种信息查询</TD>
	</TR>
</Table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMullineRiskSaleNameGrid" >
     </span> 
      </td>
   </tr>
   
</table>
<div align=right style='margin-top: 1%;margin-right: 11.5%;'>
    <INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="turnPageN.firstPage();"> 
	<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="turnPageN.previousPage();">      
	<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="turnPageN.nextPage();"> 
	<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="turnPageN.lastPage();">
</div>


<table align=right >
	<tr>
		<td height="30px">&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td><input type="button" class=cssbutton value="删除"
			onclick="deleteClick(<%=flag%>);"></td>
		<td><input type="button" class=cssbutton value="修改"
			onclick="updateClick(<%=flag%>);"></td>
		<td><input type="button" class=cssbutton value="增加"
			onclick="submitFrom(<%=flag%>);"></td>
		<td width="30%"></td>
	</tr>
</table>


</div>
<input type=hidden name=Transact>
<input type=hidden name=Flag>
<input type=hidden name=RiskCode2>
</div>

</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>

</html>
