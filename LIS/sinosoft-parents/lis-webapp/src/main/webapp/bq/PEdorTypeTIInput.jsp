<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<% 
//程序名称：GEdorTypePG.jsp
//程序功能：账户转换
//创建日期：2007-05-23 16:49:22
//创建人  ：ck
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html> 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>

  <SCRIPT src="./PEdorTypeTI.js"></SCRIPT>
  <%@include file="PEdorTypeTIInit.jsp"%>
  
  <title>投连账户转换</title> 
</head>

<body  onload="initForm();" >
  <form action="" method=post name=fm id=fm target="fraSubmit">    
    <Div class=maxbox1>
    <table class=common>
      <TR  class= common> 
        <TD  class= title > 批单号</TD>
        <TD  class= input > 
          <input class="readonly wid" readonly name=EdorNo id=EdorNo>
        </TD>
        <TD class = title > 批改类型 </TD>
        <TD class = input >
        <Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
        </TD>
       
        <TD class = title >保单号 </TD>
        <TD class = input >
        	<input class="readonly wid" readonly name=ContNo id=ContNo>
        </TD>   
      </TR>
      <TR class=common>
    	<TD class =title>申请日期</TD>
    	<TD class = input>    		
    		<Input class= "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	</TD>
    	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<Input class= "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>
    </TABLE> 
    </div>
        <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
        </td>
        <td class= titleImg>
          选择转换险种
        </td>
      </tr>      
    </table>
    <Div  id= "divQuery" class=maxbox1 style= "display: ''">
    <table class=common>
    <TR  class= common> 
        <TD  class= title > 保单险种号</TD>
        <TD  class= input > 
          <input class="common wid" name=PolNo id=PolNo >
        </TD>
        <TD class = title > 被保人客户号 </TD>
        <TD class = input >
        	<input class="common wid" name=InsuredNo id=InsuredNo>
        </TD>  
        <TD class = title > 姓名 </TD>
        <TD class = input >
        <Input class="common wid"  name=InsuredName id=InsuredName>
        </TD> 
     </TR>
     <TR  class= common> 
      <TD  class= title > 证件类型</TD>
        <TD  class= input > 
          <input class="codeno" name=IDType id=IDType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="showCodeListKeyEx('IDType',[this,IDTypeName],[0,1]);">
          <input class=codename name=IDTypeName id=IDTypeName readonly=true>
        </TD>
        <TD class = title > 证件号码 </TD>
        <TD class = input >
        	<input class="common wid" name=IDNo id=IDNo>
        </TD>  
        <TD class = title > 性别 </TD>
        <TD class = input >
        <Input class="codeno"  name=Sex id=Sex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="showCodeListKeyEx('Sex',[this,SexName],[0,1]);">
        <input class=codename name=SexName id=SexName readonly=true>
        </TD> 
     </TR>
     <TR  class= common> 
     <TD  class= title > 出生日期</TD>
        <TD  class= input > 
          <input name=Birthday class= "coolDatePicker" dateFormat="short" onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
     </TR>
    </TABLE> 
    </div>
    <INPUT class=cssButton id="riskbutton" VALUE="查询保单信息" TYPE=button onClick="queryPol();">
	<Div  id= "divLCInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsuAccGrid" >
  					</span> 
  			</td>
  			</tr>
    	</table>
    	 </Div>
    	<Div  id= "divPage2" align=center style= "display: none ">
        <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">					
  	</div>
	<br>  
<!--	 <Input type=Button value="团体账户转换" class = cssButton onclick="GEdorDetail()">-->
	  <Input type=Button value="个人账户转换" class = cssButton onclick="EdorDetail()">
	  <Input type=Button value="返  回" class = cssButton onclick="returnParent()">
  <!--
	<table>
	      <tr>
	        <td>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDelete);">
	        </td>
	        <td class= titleImg>
	          交易信息
	        </td>
	      </tr>      
	 </table>  
	 
	  	<Div  id= "divLPInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLPInsuAccGrid" >
  					</span> 
  			</td>
  			</tr>
    	</table>
    	 </Div>
	  <Input type=Button value="交易撤销" class = cssButton onclick="cancelGEdor()">  -->

    <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
    <input type=hidden id="CustomerNo" name="CustomerNo">
    <input type=hidden id="ContNoBak" name="ContNoBak">
    <input type=hidden id="CustomerNoBak" name="CustomerNoBak">
    <input type=hidden id="ContType" name="ContType">
    <input type=hidden id="Transact" name="Transact">
    <input type=hidden id="EdorTypeCal" name="EdorTypeCal">
	  
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html>

<script>
  window.focus();
</script>
