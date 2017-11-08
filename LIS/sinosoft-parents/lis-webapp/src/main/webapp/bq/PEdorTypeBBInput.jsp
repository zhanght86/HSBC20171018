<html> 
<% 
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-11-26
 * @direction: 客户资料变更
 ******************************************************************************/
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
 
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeBB.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeBBInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeBBSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>  
  <TABLE class=common>
    <TR  class= common> 
      <TD  class= title >保全申请号</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title >批改类型</TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>    
      <TD class = title >保单号</TD>
      <TD class = input >
      	<input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>
      <TR class=common>
    	<TD class =title>柜面受理日期</TD>
    	<TD class = input>    		
    		<input class = "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    	</TD>
    	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<input class = "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    	</TD>
    	<TD class =title></TD>
    	<TD class = input></TD>
    </TR>   
    </TR>
  </TABLE>  
  </div>
<table>
			<td class=common>
			  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
			</td>
			<td class= titleImg>
			  客户的信息
			</td>
</table>


<DIV id=DivLCInsured class=maxbox1 STYLE="display:''">
    <table  class= common>
        <TR  class= common>
          <TD  class= title>
            姓名
          </TD>
         <TD  class= input>
         <Input class="wid common"  readonly name=Name id=Name >
         </TD>
         <TD  class= title>
            国籍
          </TD>
	        <TD  class= input>	          
	          <input class="codeno" readonly name="NativePlace" id=NativePlace ><input class=codename name=NativePlaceName id=NativePlaceName >
	        </TD> 
          <TD  class= title>
            户口所在地
          </TD>
          <TD  class= input>
            <Input class="wid common"  readonly name="AppntRgtAddress" id=AppntRgtAddress verify="客户户口所在地|len<=80" >
          </TD>
        </TR>  
        <TR  class= common>        
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="codeno" readonly name="AppntMarriage" id=AppntMarriage elementtype= verify="客户婚姻状况|notnull&code:Marriage" ><input class=codename name=AppntMarriageName id=AppntMarriageName readonly=true elementtype=nacessary>
          </TD>
            <TD class=title>语言</TD>
			<TD CLASS=input>
			<Input class="codeno" name="OldLanguage" id=OldLanguage  verify="语言|CODE:language" verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('language',[this,OldLanguageName],[0,1]);" onkeyup="return showCodeListKey('language',[this,OldLanguageName],[0,1]);"><input class=codename name=OldLanguageName readonly=true elementtype=nacessary>
			</TD>
             <TD  class= title>

          </TD>
          <TD  class= input>
          </TD>
        </TR>        
	</table>
</DIV>

<table>
			<td class=common>
			  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLPInsured);">
			</td>
			<td class= titleImg>
			  客户更改后的信息
			</td>
</table>


<DIV id=DivLPInsured class=maxbox1 STYLE="display:''">
    <table  class= common>
        <TR  class= common>
          <TD  class= title>
            姓名
          </TD>
         <TD  class= input>
         <Input class="wid common" readonly  name=Name_New id=Name_New >
         </TD>
       <TD  class= title>
            国籍
          </TD>
	        <TD  class= input>	          
	          <input class="codeno" name="NativePlace_New" id=NativePlace_New style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('NativePlace',[this,NativePlaceName_New],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName_New],[0,1]);"><input class=codename name=NativePlaceName_New id=NativePlaceName_New >
	        </TD> 
          <TD  class= title>
            户口所在地
          </TD>
          <TD  class= input>
            <Input class="wid common" id=AppntRgtAddress_New name="AppntRgtAddress_New" verify="客户户口所在地|len<=80" >
          </TD>
        </TR> 
         <TR  class= common>         
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="codeno" name="AppntMarriage_New" id=AppntMarriage_New style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" elementtype= verify="客户婚姻状况|notnull&code:Marriage" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName_New],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName_New],[0,1]);"><input class=codename name=AppntMarriageName_New id=AppntMarriageName_New readonly=true elementtype=nacessary>
          </TD>
             <TD class=title>语言</TD>
			<TD CLASS=input>
			<Input class="codeno" name="NewLanguage" id=NewLanguage style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="语言|CODE:language" verifyorder="1" ondblclick="return showCodeList('language',[this,NewLanguageName],[0,1]);" onkeyup="return showCodeListKey('language',[this,NewLanguageName],[0,1]);"><input class=codename name=NewLanguageName id=NewLanguageName readonly=true elementtype=nacessary>
			</TD>
             <TD  class= title>

          </TD>
          <TD  class= input>

          </TD>
        </TR>        
	</table>
</DIV>
<Div id= "divSociaFlag" style="display: none">
    <table  class= common>
        <TR  class= common>
        <TD  class= title > 有无社保标记 </TD>
         <TD  class= input ><input type="checkbox" id="SociaFlag" name="SociaFlag" />
            	</TD>
             <TD  class= title>

          </TD>
          <TD  class= input>
          </TD>
           <TD  class= title>

          </TD>
          <TD  class= input>
          </TD>
        </TR>        
	</table>

</Div>
<br>
<Div id= "divEdorquery" style="display: ''">
	<Input class= cssButton type=Button value=" 保  存 " onclick="edorTypeBBSave()">
	<Input class= cssButton type=Button value=" 返  回 " onclick="returnParent()">
	<input class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">

</Div>
	 <!--input type=hidden id="PolNo" name="PolNo"-->
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <!--客户号-->
	 <input type=hidden id="CustomerNo" name="CustomerNo">	 
	 <input type=hidden id="EdorNo" name="EdorNo">
	 <!--投保人还是被保人,0,投保人，1，被保人，2 两者兼之-->
	 <input type=hidden id="AppntIsInsuredFlag" name="AppntIsInsuredFlag">
	 	 <input type=hidden id="PreSFlag" name="PreSFlag">
	 	 	<input type=hidden id="NewSFlag" name="NewSFlag">
</form>
<br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


