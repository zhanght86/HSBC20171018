<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：bomMain.jsp
//程序功能：BOM对象管理
//创建日期：2008-8-12
//创建人  ：
//更新记录：  
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="bomMain.js"></SCRIPT>
  <%@include file="bomFunInit.jsp"%>

<%
//==============================================================BEGIN
	String BranchTyp = request.getParameter("BranchTyp");
//===============================================================END
%>

</head>
<body  onload="initForm();hiddenButton();" >

<form action="./bomFunMan.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Action>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>BOM对象查询</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox1">
<table class=common align=center>
	<TR class=common>
		<TD class=title5>BOM英文名</TD>
		<TD class=input5><Input class="wid" class=common name=eName id=eName
			elementtype=nacessary verify="BOM英文名|NOTNULL" ></TD>
		<TD class=title5>BOM中文名</TD>
		<TD class=input5><Input class="wid" class=common name=cName id=cName
			elementtype=nacessary verify="BOM中文名|NOTNULL"></TD>

	</TR>
	<TR class=common>
		<TD class=title5>BOM业务模块</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class=codeno
			ondblclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
            onclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsBusiness',[this,BusinessName],[0,1]);"><input
			class=codename name="BusinessName" id="BusinessName" readonly=true></TD>
		<TD class=title5>BOM有效性</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);"><input
			class=codename name="ValidName" id="ValidName" readonly=true></TD>
	</TR>
</Table>
</Div>
  
  <!--<Div id= divCmdButton style="display: ''">-->
    <!--<INPUT VALUE="查询BOM" TYPE=button class="cssButton" onclick="queryClick()">
     <INPUT VALUE="增加BOM" TYPE=button name=ic class="cssButton" onclick="insertClick()">
	<INPUT VALUE="修改BOM" TYPE=button name=uc class="cssButton" onclick="updateClick()">
     <INPUT VALUE="删除BOM" TYPE=button name=dc class="cssButton" onclick="deleteClick()">-->
   <a href="javascript:void(0);" class="button" onClick="queryClick();">查询BOM</a>
     <a href="javascript:void(0);" class="button" onClick="insertClick();">增加BOM</a>
     <a  href="javascript:void(0);" class="button" onClick="updateClick();">修改BOM</a>
     <a  href="javascript:void(0);" class="button" onClick="deleteClick();">删除BOM</a>
<!--  </Div>-->

  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
      <td class= titleImg>
    	 BOM列表
      </td>
    </table>
<Div  id= "divPayPlan1" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanQueryGrpGrid" ></span>
		</td>
		<TD class=input style="display: none">					
			
		</TD>
	</tr>
     </table>
       
</div></Div>
<br/>


 <!-- 词条页面 -->
 <Table>
	<TR>		
		<TD class=input style="display: none">
         <Input class="common" name=itemAction>
       </TD>	
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divItemCondition);"></TD>
		<TD class=titleImg>词条信息</TD>
	</TR>
</Table>

 <!--<Div id=divItemCondition style="display: ''">
     <INPUT VALUE="查询词条" TYPE=button class="cssButton" onclick="itemQuery()">
     <INPUT VALUE="增加词条" TYPE=button name=ii class="cssButton" onclick="itemInsert()">
	<INPUT VALUE="修改词条" TYPE=button name=iu class="cssButton" onclick="itemUpdate()">
     <INPUT VALUE="删除词条" TYPE=button name=id class="cssButton" onclick="itemdelete()">-->
<a href="javascript:void(0);" class="button" onClick="itemQuery();">查询词条</a>
     <a  href="javascript:void(0);" class="button" onClick="itemInsert();">增加词条</a>
     <a  href="javascript:void(0);" class="button" onClick="itemUpdate();">修改词条</a>
     <a  href="javascript:void(0);" class="button" onClick="itemdelete();">删除词条</a>
  </Div>

  <Div  id= "divItemQueryGrp" style= "display: ''">
    <table>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,poop);"></td>
      <td class= titleImg> 词条列表   </td>
    </table>
<Div  id= "poop" style= "display: ''">
     <table  class= common>
	<tr class="common">
		<td><input type=hidden name=BranchTyp value=<%=BranchTyp%>></td>
	</tr>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanItemGrid" ></span>
		</td>
	</tr>
     </table>
       
</div></Div>
</form>

 <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
