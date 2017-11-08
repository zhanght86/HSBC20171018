<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//程序名称：FinInterfaceCheck.jsp
	//程序功能：凭证核对
	//创建日期：2007-10-23 
	//创建人  ：m
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<script>	
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var comcode = "<%=tGI.ComCode%>";//记录登陆机构
</script>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="FinInterfaceCheck.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FinInterfaceCheckInit.jsp"%>
</head>

<body onload="initForm();">
<form method=post name=fm id=fm target="fraSubmit">
<!-- 流水号码 --> 
<input type=hidden name=serialNo id=serialNo>
<input type=hidden name=ExportExcelSQL id=ExportExcelSQL>
<input type=hidden name=ClassType id=ClassType>
  <table>
    	<tr> 
        	<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSearch);"></td>
    		<td class= titleImg>请输入查询条件</td>   		 
    	</tr>
  </table> 
    
<Div id="divSearch" style="display: ''">
<div class="maxbox1">
	<table class=common>
	<TR class=common>
		<TD class=title>核对类型</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=checkType id=checkType
			CodeData="0|^1|科目|M^2|业务代码|M^3|凭证类型|M^4|凭证号码|M" verify="核对类型|notnull"
			ondblclick="showCodeListEx('checkType',[this,checkTypeName],[0,1]);"
            onMouseDown="showCodeListEx('checkType',[this,checkTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('checkType',[this,checkTypeName],[0,1]);"><input
			class=codename name=checkTypeName id=checkTypeName readonly=true elementtype=nacessary><font color=red>*</font></TD>	
		<TD class=title></TD>	
		<TD class=input></TD>		
		<TD class=title></TD>	
		<TD class=input></TD>			
	</TR>
	</table>
    </div>	
</Div>
	
<Div id="divSearch1" style="display:none"> 	
	<table class=common>
	<TR class=common>
		<TD class=title>科目类型</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=accountCodeType id=accountCodeType
			CodeData="0|^1|资产|M^2|负债|M^3|损益" verify="科目类型|notnull"
			ondblclick="showCodeListEx('accountCodeType',[this,accountCodeTypeName],[0,1]);"
            onMouseDown="showCodeListEx('accountCodeType',[this,accountCodeTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('accountCodeType',[this,accountCodeTypeName],[0,1]);"><input
			class=codename name=accountCodeTypeName id=accountCodeTypeName readonly=true elementtype=nacessary></TD>	
		<TD  class= title>科目</TD>
    	<TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=accountCode id=accountCode verify="科目|NOTNULL" 
      		ondblclick="showCodeList('accountcode',[this,accountName],[0,1],null,[fm.accountCodeType.value],['othersign'],'1',null);" 
            onMouseDown="showCodeList('accountcode',[this,accountName],[0,1],null,[fm.accountCodeType.value],['othersign'],'1',null);" 
      		onkeyup="return showCodeListKey('accountcode',[this,accountName],[0,1],null,[fm.accountCodeType.value],['othersign'],'1',null);"><input
      		class=codename name=accountName id=accountName readonly=true elementtype=nacessary></TD>	
		<TD class=title>借贷标志</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FinItemType id=FinItemType
			CodeData="0|^D|借方|M^C|贷方|M" verify="科目类型|notnull"
			ondblclick="showCodeListEx('FinItemType',[this,FinItemTypeName],[0,1],null,null,null,1,null);"
            onMouseDown="showCodeListEx('FinItemType',[this,FinItemTypeName],[0,1],null,null,null,1,null);"
			onkeyup="showCodeListKeyEx('FinItemType',[this,FinItemTypeName],[0,1],null,null,null,1,null);"><input
			class=codename name=FinItemTypeName id=FinItemTypeName readonly=true elementtype=nacessary></TD>	      			
	</TR>
	<TR class=common>
		<TD  class= title>管理机构</TD>
      	<TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom value="86" id=ManageCom verify="管理机构|NOTNULL" 
      		ondblclick="showCodeList('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);" 
            onMouseDown="showCodeList('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);"
      		onkeyup="return showCodeListKey('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);"><input
      		class=codename name=PolicyComName id=PolicyComName readonly=true elementtype=nacessary></TD>		
		<TD class=title>起始时间</TD>
		<TD class=input>
		<Input  class="wid" onClick="laydate({elem: '#StartDay'});" verify="起始时间|notnull&date" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		<TD class=title>结束时间</TD>
		<TD class=input>
			<Input  class="wid" onClick="laydate({elem: '#EndDay'});" verify="结束时间|notnull&date" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
	</TR>		
	</table>	
</Div>	
 

<Div id="divSearch2" style="display:none"> 	
	<table class=common>  
	<TR class=common>
		<TD class=title>业务号码类型</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ContType id=ContType
			CodeData="0|^1|个人投保印刷号|M^3|个人保单号|M^5|暂交费号|M^6|实付号|M^7|实收号|M^8|个险保全批单号
|M^10|赔案号|M" verify="业务号码类型|notnull"
			ondblclick="showCodeListEx('ContType',[this,ContTypeName],[0,1],null,null,null,1,null);"
            onMouseDown="showCodeListEx('ContType',[this,ContTypeName],[0,1],null,null,null,1,null);"
			onkeyup="showCodeListKeyEx('ContType',[this,ContTypeName],[0,1],null,null,null,1,null);"><input
			class=codename name=ContTypeName id=ContTypeName readonly=true elementtype=nacessary></TD>		
        <TD  class= title>业务号码</TD>
        <TD  class= input><Input class= common name=tNo id=tNo></TD> 
        <TD  class= title></TD>
        <TD  class= input></TD>        			
	</TR>
	</table>	
</Div>

<Div id="divSearch3" style="display:none"> 	
	<table class=common>  
	<TR class=common>
		<TD class=title>凭证大类</TD>
		<TD  class= input>
      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="SClassType" id="SClassType"
      		ondblclick="showCodeListEx('classtype',[this,SClassTypeName],[0,1],null,null,null,1,null);" 
            onMouseDown="showCodeListEx('classtype',[this,SClassTypeName],[0,1],null,null,null,1,null);"
      		onkeyup="return showCodeListKeyEx('classtype',[this,SClassTypeName],[0,1],null,null,null,1,null);"><input
      		class=codename name=SClassTypeName id=SClassTypeName readonly=true elementtype=nacessary></TD>		
    </TD>	
    <TD class=title></TD>	
		<TD class=input></TD>		
		<TD class=title></TD>	
		<TD class=input></TD>	
  </TR>
  <TR> 
		<TD  class= title>管理机构</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno value="86" name=ManageCom1 id=ManageCom1 verify="管理机构|NOTNULL"  
      	 ondblclick="showCodeList('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);" 
         onMouseDown="showCodeList('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);" 
      	 onkeyup="return showCodeListKey('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);"><input
      	 class=codename name=PolicyComName1 id=PolicyComName1 readonly=true elementtype=nacessary>
    </TD>	  	 
		<TD class=title>起始日期</TD>
		<TD  class= input>
      <Input class="wid" onClick="laydate({elem: '#SDate'});" verify="有效开始日期|DATE" dateFormat="short" name=SDate id="SDate"><span class="icon"><a onClick="laydate({elem: '#SDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD class=title>终止日期</TD>
		<TD  class= input>
      <Input class="wid" onClick="laydate({elem: '#EDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
	</TR>
	</table>	
</Div>
<Div id="divSearch4" style="display:none"> 	
	<table class=common>  
	<TR class=common>	
        <TD  class= title>凭证号码</TD>
        <TD  class= input><Input class= common name=AccountID id=AccountID></TD> 
        <TD  class= title></TD>
        <TD  class= input></TD>        		
        <TD  class= title></TD>
        <TD  class= input></TD>  	
	</TR>
	</table>	
</Div>

<!--<INPUT VALUE="核算查询" class=cssButton TYPE=button onclick="CheckQueryData();">
--><a href="javascript:void(0);" class="button" onClick="CheckQueryData();">核算查询</a>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divAllGrid);"></td>
		<td class=titleImg>查询结果</td>
	</tr>
</table>

<Div id="divAllGrid" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><!-- 日结试算生成的批次 --> <span id="spanCheckQueryDataGrid"> </span></td>
	</tr>
</table>
<INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button
	onclick="turnPage.firstPage();"> <INPUT CLASS=cssButton91
	VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> <INPUT
	CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
<INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button
	onclick="turnPage.lastPage();">
</div>

<br>
<!--<INPUT VALUE="导出Excel" class=cssButton TYPE=button onclick="ToExcel();">-->
<a href="javascript:void(0);" class="button" onClick="ToExcel();">导出Excel</a>

<!--  <INPUT VALUE="打印" class=cssButton TYPE=button onclick="printFinInterface();"> -->
<br>
<br>

</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
