<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：menuGrp.jsp
//程序功能：菜单组的输入
//创建日期：2002-10-10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
    GlobalInput tG1 = new GlobalInput();
	tG1=(GlobalInput)session.getValue("GI");
	String Operator = tG1.Operator;;
%>
<html>
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

  <SCRIPT src="menuFunGrp.js"></SCRIPT>
  <script src="treeMenu.js"></script>
  <%@include file="menuFunInit.jsp"%>

</head>
<body  onload="initForm();" >

<form action="./menuFunMan.jsp" method=post name=fm id=fm target="fraSubmit">
<br>
<div class="maxbox1">
   <table  >
    	<tr>
		<TD  class= input style= "display: none">
         <Input class="code" name=Action>
         class= input style= "display: none">
         <Input class="code" name=isChild>
         class= input style= "display: none">
         <Input class="code" name=isChild2>//2005
       </TD>
	   </tr>
      <TR  class= common>
          <TD  class= title5> 菜单节点名称</TD>
          <TD  class= input5><Input class="wid" class=common name=NodeName id=NodeName > </TD>
          <TD class= title5> 映射文件</TD>
          <TD  class= input5> <Input class="wid" class=common name=RunScript id=RunScript  ></TD>
      </TR> 
    </Table></div>

 &nbsp;&nbsp;&nbsp;&nbsp;<input style="vertical-align:middle;" type="checkbox" name="checkbox1" value="1"><span style="vertical-align:middle;  font-family:"宋体";">作为子菜单插入(不选则按照同级菜单插入)</span>
  &nbsp;&nbsp;
  <input style="vertical-align:middle" type="checkbox" name="checkbox2" value="1"><span style="vertical-align:middle; font-family:"宋体";">作为页面权限菜单插入</span>
<br>
    <!-- <INPUT VALUE="查询菜单" TYPE=button class="cssButton" onclick="queryClick()">
     <INPUT VALUE="增加菜单" TYPE=button class="cssButton" onclick="insertClick()">
     <INPUT VALUE="删除菜单" TYPE=button class="cssButton" onclick="deleteClick()">--><br>
     <a href="javascript:void(0);" class="button" onClick="queryClick();">查询菜单</a>
     <a href="javascript:void(0);" class="button" onClick="insertClick();">增加菜单</a>
     <a href="javascript:void(0);" class="button" onClick="deleteClick();">删除菜单</a>


  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
      <td class= titleImg>
    	 菜单列表
      </td>

    </table>
<Div  id= "divPayPlan1" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td><span id="spanQueryGrpGrid"></span></td>
	    </tr>
     </table>
</div>
</Div>
</form>

 <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
