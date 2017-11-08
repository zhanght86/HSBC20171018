<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：LDPersonQuery.jsp
//程序功能：客户管理（新增客户查询）
//创建日期：2005-06-20
//创建人  ：wangyan
//更新记录：更新人    更新日期     更新原因/内容
%>

<html>
<head>
<title>客户信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<script src="./LDPersonQuery.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="LDPersonQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<!--登录画面表格-->
<form name=fm id="fm" target=fraSubmit method=post>
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    		</TD>
    		<TD class= titleImg>
    			 请输入查询条件
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "maxbox" class="maxbox" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>客户号码</TD>
          <TD  class= input><Input class= "common wid" name=CustomerNo  id="CustomerNo"> </TD>
          <TD  class= title>姓名</TD>
          <TD  class= input><Input class= "common wid" name=Name id="Name" > </TD>
          <TD  class= title>性别</TD>
          <TD  class= input><Input name=Sex id="Sex" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" value="0" class="code" MAXLENGTH=1 onClick="return showCodeList('Sex',[this]);" onDblClick="return showCodeList('Sex',[this]);" onKeyUp="return showCodeListKey('Sex',[this]);" > </TD>
				</TR>
      	
      	<TR  class= common>
          <TD  class= title>出生日期</TD>
          <TD  class= input><Input class="coolDatePicker" dateFormat="short" name=Birthday id="Birthday" onClick="laydate({elem:'#Birthday'});"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>             </TD>
          <TD  class= title>证件类型</TD>
          <TD  class= input><Input class="code" name=IDType id="IDType" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="return showCodeList('IDType',[this]);" onDblClick="return showCodeList('IDType',[this]);">              </TD>
          <TD  class= title>证件号码</TD>
          <TD  class= input>  <Input class= "common wid" name=IDNo id="IDNo" >   </TD>
        </TR>
   	</Table>
      <INPUT VALUE="查  询" class=cssButton TYPE=button onClick="easyQueryClick();">
      
    <Table>
    	<TR>
      	<TD class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);">
  			</TD>
  			<TD class= titleImg>
  			 	客户信息
  			</TD>
    	</TR>
    </Table>
 <Div  id= "divLDPerson1" style= "display: ''" align = center>
   	<Table  class= common>
      <TR  class= common>
        <TD style=" text-align: left" colSpan=1>
          <span id="spanPersonGrid" ></span>
  			</TD>
      </TR>
    </Table>
     <center>				
      <INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="getLastPage();"> 
    </center>
 </Div>
 <INPUT VALUE="返  回" class=cssButton TYPE=button onClick="returnParent();">
		<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form>
<br><br><br><br><br>
</body>
</html>
